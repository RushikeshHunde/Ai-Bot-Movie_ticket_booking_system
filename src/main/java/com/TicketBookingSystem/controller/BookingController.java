package com.TicketBookingSystem.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.TicketBookingSystem.model.Booking;
import com.TicketBookingSystem.model.MovieNotification;
import com.TicketBookingSystem.model.Show;
import com.TicketBookingSystem.model.Ticket;
import com.TicketBookingSystem.model.User;
import com.TicketBookingSystem.repository.BookingRepository;
import com.TicketBookingSystem.repository.NotificationRepository;
import com.TicketBookingSystem.repository.ShowRepository;
import com.TicketBookingSystem.repository.TicketRepository;
import com.TicketBookingSystem.repository.UserRepository;
import com.TicketBookingSystem.services.EmailService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class BookingController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private EmailService emailService;


	@GetMapping("/info")
	public String showInfoPage() {
	    return "info"; // matches info.html
	}

	@GetMapping("/booking")
	public String showBookingPage(Model model) {

		LocalDateTime safetyBuffer = LocalDateTime.now().minusHours(24);

	    List<Show> availableShows = showRepository.findActiveShows(safetyBuffer);

	    if (availableShows.isEmpty()) {
	        model.addAttribute("noShows", true);
	    }

	    model.addAttribute("shows", availableShows);
	    return "booking";
	}


	@GetMapping("/all-bookings")
	public String showAllBookingsPage(Model model) {

		List<Booking> allBookings = bookingRepository.findAll();
		model.addAttribute("bookings", allBookings);
		return "booking";
	}

	@GetMapping("/admin/setup-booking")
	public String showBookingUpdateForm(Model model, HttpSession session) {
		if (session.getAttribute("adminSession") == null) {
			return "redirect:/admin/login";
		}

		// This 'show' object is required so th:field in the HTML has something to bind
		// to
		model.addAttribute("show", new Show());

		// This returns the file: templates/booking-update.html
		return "show-update";
	}

	@GetMapping("/book/{id}")
	public String showBookingPage(@PathVariable("id") Long id, Model model) {
		// Fetch the show by ID from the repository
		Show show = showRepository.findById(id).orElseThrow(() -> new RuntimeException("Show not found for ID: " + id));

		model.addAttribute("show", show);

		return "booking"; // Matches your booking.html file name
	}

	@Transactional
	@PostMapping("/book-ticket")
	public String processBooking(@RequestParam Long showId, @RequestParam String selectedSeats,
			@RequestParam double totalAmount, HttpSession session) {

		// 1. Get current user from session
		User user = (User) session.getAttribute("loggedInUser");

		// 2. Find the show
		Show show = showRepository.findById(showId).orElseThrow();

		// 3. Create Booking
		Booking booking = new Booking();
		booking.setUser(user);
		booking.setShow(show);
		booking.setSelectedSeats(selectedSeats);
		booking.setTotalAmount(totalAmount);
		booking.setBookingTime(LocalDateTime.now());
		booking.setStatus("CONFIRMED");

		bookingRepository.save(booking);
		return "redirect:/booking-success";
	}

	@PostMapping("/checkout")
	public String proceedToCheckout(@RequestParam Long showId,
	                                @RequestParam String userName,
	                                @RequestParam String email,
	                                @RequestParam String selectedSeats,
	                                @RequestParam double totalAmount,
	                                Model model) {

	    Show show = showRepository.findById(showId).orElseThrow();

	    // 1. Calculate seats count
	    String[] seatsArray = selectedSeats.split(",");
	    int numberOfSeatsBooked = seatsArray.length;

	    // 2. NULL-SAFE Check for availability
	    Integer currentAvailable = show.getAvailableSeats();
	    if (currentAvailable == null) {
	        currentAvailable = show.getTotalSeats() != null ? show.getTotalSeats() : 50;
	    }

	    // 3. Validation
	    if (currentAvailable < numberOfSeatsBooked) {
	        return "redirect:/booking?error=not_enough_seats";
	    }

	    // Note: We DO NOT subtract seats here.
	    // We only subtract once the payment is actually CONFIRMED.

	    User user = userRepository.findByEmail(email).orElse(new User());
	    user.setName(userName);
	    user.setEmail(email);
	    userRepository.save(user);

	    Booking booking = new Booking();
	    booking.setShow(show);
	    booking.setUser(user);
	    booking.setSeatsBooked(selectedSeats);
	    booking.setTotalAmount(totalAmount);
	    booking.setStatus("PENDING");

	    Booking savedBooking = bookingRepository.save(booking);

	    model.addAttribute("bookingId", savedBooking.getBookingId());
	    model.addAttribute("totalAmount", totalAmount);
	    model.addAttribute("email", email);

	    return "checkout";
	}


	@PostMapping("/confirm-payment")
	@Transactional
	public String confirmPayment(@RequestParam Long bookingId, Model model) {

	    Booking booking = bookingRepository.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

	    Show show = booking.getShow();
	    if (show == null) {
			throw new RuntimeException("No Show linked to this booking!");
		}

	    // 1. NULL-SAFE Seat Update (The fix for Line 190)
	    Integer currentAvailable = show.getAvailableSeats();
	    if (currentAvailable == null) {
	        currentAvailable = show.getTotalSeats() != null ? show.getTotalSeats() : 50;
	    }

	    String newlyBooked = booking.getSeatsBooked();
	    int seatsCount = (newlyBooked != null && !newlyBooked.isEmpty()) ? newlyBooked.split(",").length : 0;

	    // 2. Perform the subtraction
	    show.setAvailableSeats(currentAvailable - seatsCount);

	    // 3. Update Booked Seats string for the UI
	    String alreadyBooked = show.getBookedSeats() != null ? show.getBookedSeats() : "";
	    if (alreadyBooked.isEmpty()) {
	        show.setBookedSeats(newlyBooked);
	    } else {
	        show.setBookedSeats(alreadyBooked + "," + newlyBooked);
	    }

	    showRepository.save(show);

	    // 4. Update Booking Status
	    booking.setStatus("PAID");
	    bookingRepository.save(booking);

	    // 5. Generate Ticket
	    Ticket ticket = new Ticket();
	    ticket.setTicketId("CP-" + System.currentTimeMillis());
	    ticket.setBooking(booking);
	    ticket.setCustomerName(booking.getUser().getName());
	    ticket.setCustomerEmail(booking.getUser().getEmail());
	    ticket.setDisplayMovieTitle(show.getMovieTitle());
	    ticket.setDisplayShowTime(show.getStartTime().toString());
	    ticket.setDisplayHall("Hall " + show.getHallNumber());
	    ticket.setSeatNumbers(newlyBooked);
	    ticket.setTotalAmount(booking.getTotalAmount());
	    ticket.setMovieImageUrl(show.getMovieImageUrl());

	    ticketRepository.save(ticket);

	    model.addAttribute("t", ticket);
	    return "ticket";
	}

	@PostMapping("/my-bookings/search")
	public String searchBookings(@RequestParam String email, @RequestParam String bookingId, Model model) {
	    // Note: 'bookingId' from the form maps to 'ticketId' in our new Ticket table
	    Optional<Ticket> ticketOpt = ticketRepository.findByTicketIdAndCustomerEmail(bookingId, email);

	    if (ticketOpt.isPresent()) {
	        model.addAttribute("t", ticketOpt.get());
	        return "ticket"; // This goes to ticket.html
	    }

	    model.addAttribute("error", "No ticket found for this ID and Email combination.");
	    return "my-bookings";
	}



	@GetMapping("/admin/manage-shows")
	public String listAllShows(Model model, HttpSession session) {
		// Security Check
		if (session.getAttribute("adminSession") == null) {
			return "redirect:/admin/login";
		}

		List<Show> allShows = showRepository.findAll();
		model.addAttribute("allShows", allShows);

		return "show-list";
	}

	@PostMapping("/movie/notify")
	@ResponseBody
	public ResponseEntity<String> notifyMe(@RequestParam String email, @RequestParam Long movieId) {

		// Check if the user already registered for this specific movie
		if (notificationRepository.existsByEmailAndMovieId(email, movieId)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("You are already on the list for this movie!");
		}

		MovieNotification notification = new MovieNotification();
		notification.setEmail(email);
		notification.setMovieId(movieId);
		notification.setRequestedAt(LocalDateTime.now());

		notificationRepository.save(notification);

		return ResponseEntity.ok("Success! We will notify you when tickets are available.");
	}


	@GetMapping("/download-ticket/{id}")
	public ResponseEntity<ByteArrayResource> downloadTicket(@PathVariable Long id) {
	    Booking booking = bookingRepository.findById(id).orElseThrow();
	    byte[] pdfBytes = emailService.generatePdfBytes(booking);

	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Ticket_" + id + ".pdf")
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new ByteArrayResource(pdfBytes));
	}



	@PostMapping("/confirm-ticket")
	public String confirmTicket(@ModelAttribute("booking") Booking booking, Model model) {
	    // Logic to save booking to DB if not already done
	    model.addAttribute("t", booking);
	    return "ticket"; // This returns the HTML view only
	}



	@PostMapping("/view-my-ticket")
	public String viewMyTicket(@RequestParam Long bookingId, @RequestParam String email, Model model,
			RedirectAttributes redirectAttributes) {

		// Find booking by ID
		Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

		// SECURITY CHECK: Does booking exist AND does the phone number match?
		if (bookingOpt.isPresent() && bookingOpt.get().getUser().getEmail().equals(email)) {
			model.addAttribute("t", bookingOpt.get());
			return "ticket"; // Redirect to your beautiful ticket.html page
		} else {
			redirectAttributes.addFlashAttribute("error", "Invalid Booking ID or Phone Number");
			return "redirect:/"; // Go back to home with an error
		}
	}



	@GetMapping("/my-bookings")
	public String showMyBookingsPage() {
	    // This returns the name of your HTML file: my-bookings.html
	    return "my-bookings";
	}

}