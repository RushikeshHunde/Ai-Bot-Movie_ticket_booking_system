package com.TicketBookingSystem.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.TicketBookingSystem.model.Booking;
import com.TicketBookingSystem.model.Movie;
import com.TicketBookingSystem.model.Show;
import com.TicketBookingSystem.repository.MovieRepository;
import com.TicketBookingSystem.repository.ShowRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowRepository showRepository;



    @GetMapping("/")
    public String home(@RequestParam(required = false) String city, Model model) {

    	LocalDateTime displayBuffer = LocalDateTime.now().minusHours(24);
        List<Show> allShows = showRepository.findActiveShows(displayBuffer);

        List<Movie> nowShowing = allShows.stream()
                .filter(s -> "ACTIVE".equalsIgnoreCase(s.getStatus()))
                .filter(s -> {
                    // If no city is searched, show everything
                    if (city == null || city.isEmpty()) {
						return true;
					}
                    // Only compare if targetCity exists in DB
                    return s.getTargetCity() != null && s.getTargetCity().equalsIgnoreCase(city);
                })
                .map(Show::getMovie)
                .filter(java.util.Objects::nonNull) // Ensure the show actually has a linked movie
                .distinct()
                .collect(Collectors.toList());

        List<Movie> upcomingMovies = movieRepository.findAll().stream()
                .filter(m -> "Upcoming".equalsIgnoreCase(m.getTheatreName()))
                .collect(Collectors.toList());

        model.addAttribute("shows", allShows);
        model.addAttribute("movies", nowShowing);
        model.addAttribute("selectedCity", city);
        model.addAttribute("upcomingMovies", upcomingMovies);

        return "home";
    }

    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        if (session.getAttribute("admin") != null) {
            return "redirect:/admin/dashboard"; // Don't show login if already logged in
        }
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {

        // Replace this with your actual credentials check
        if ("admin".equals(username) && "admin123".equals(password)) {

            session.setAttribute("adminUser", username);
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid Credentials");
            return "login";
        }
    }


    @GetMapping("/admin/login")
    public String showAdminLogin() {
        return "login";
    }

    @PostMapping("/admin/login")
    public String loginProcess(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String targetUrl,
            HttpSession session,
            Model model) {

        if ("admin@gmail.com".equals(email) && "admin@210803".equals(password)) {
            session.setAttribute("adminSession", "active");
            session.setAttribute("userRole", "ADMIN");

            
            if (targetUrl != null && !targetUrl.equals("/")) {
                return "redirect:" + targetUrl;
            }
            return "redirect:/admin/login?error";
        }

        model.addAttribute("error", "Invalid Credentials!");
        return "login";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }



    @GetMapping("/theatres")
    public String showTheatres(Model model) {
        // Adding real Pune data if your database is empty for testing
        List<Map<String, String>> puneTheatres = List.of(
            Map.of(
                "name", "PVR ICON Pavillion",
                "area", "SB Road, Shivajinagar",
                "img", "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?q=80&w=2070", // Replace with actual inside view
                "desc", "Pune's most premium cinema experience featuring plush recliners and Dolby Atmos.",
                "map", "https://www.google.com/maps/search/PVR+ICON+Pavillion+Pune"
            ),
            Map.of(
                "name", "Cinépolis IMAX",
                "area", "Westend Mall, Aundh",
                "img", "https://images.unsplash.com/photo-1517604401157-538e96834c4f?q=80&w=2070",
                "desc", "Home to Pune's only IMAX screen. Massive projection and bone-shaking sound.",
                "map", "https://www.google.com/maps/search/Cinepolis+Westend+Mall+Aundh"
            ),
            Map.of(
                "name", "Inox Insignia",
                "area", "Amanora Mall, Hadapsar",
                "img", "https://images.unsplash.com/photo-1595769816263-9b910be24d5f?q=80&w=2070",
                "desc", "Ultra-luxury theater with laser projection and a curated gourmet menu.",
                "map", "https://www.google.com/maps/search/Inox+Amanora+Mall+Pune"
            )
        );
        model.addAttribute("theatres", puneTheatres);
        return "theatre";
    }



    @GetMapping("/admin/add")
    public String showAddPage(HttpSession session, Model model) {
        if (session.getAttribute("adminSession") == null) {
            return "redirect:/admin/login";
        }

        // 1. Fetch unique values from BOTH tables to ensure nothing is missed
        List<String> titles = movieRepository.findAll().stream().map(Movie::getTitle).distinct().toList();
        List<String> cities = movieRepository.findAll().stream().map(Movie::getCity).distinct().toList();
        List<String> theatres = movieRepository.findAll().stream().map(Movie::getTheatreName).distinct().toList();

        // 2. Add them to the model for the <datalist> in your HTML
        model.addAttribute("prevTitles", titles);
        model.addAttribute("prevCities", cities);
        model.addAttribute("prevTheatres", theatres);

        Show newShow = new Show();
        newShow.setMovie(new Movie());
        model.addAttribute("show", newShow);
        model.addAttribute("isNew", true);

        return "booking-update";
    }

    @GetMapping("/admin/update/{id}")
    public String showUpdatePage(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("adminSession") == null) {
			return "redirect:/admin/login";
		}
        Movie movie = movieRepository.findById(id).orElse(new Movie());
        model.addAttribute("movie", movie);
        return "booking-update";
    }


    @PostMapping("/admin/save-booking-update")
    public String saveBookingUpdate(@ModelAttribute("show") Show show,
                                    @RequestParam("imageFile") MultipartFile file) throws IOException{
        try {
            // 1. Ensure the Movie object is linked (Important for home.html)
            Movie movie = show.getMovie();
            if (movie == null) {
				movie = new Movie();
			}

            // 2. SYNC: Map form data from the Show object to the Movie object
            // home.html reads these fields from the Movie entity
            movie.setTitle(show.getMovieTitle());
            movie.setCity(show.getTargetCity());
            movie.setRating(show.getMovieRating() != null ? show.getMovieRating() : 0.0);
            movie.setTheatreName(show.getTheatreName());
            movie.setTheatreAddress(show.getTheatreAddress());
            movie.setCinemaType(show.getCinemaType());
            if(show.getMovieDuration() != null) {
				movie.setDurationMinutes(show.getMovieDuration());
			}

            // 3. FILE SAVING: Physically move the file to the static folder
            if (!file.isEmpty()) {
                String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images/";
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

                try (var inputStream = file.getInputStream()) {
                    Files.copy(inputStream, uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    String finalPath = "/images/" + fileName;
                    movie.setImageUrl(finalPath);
                    show.setMovieImageUrl(finalPath);
                }
            }

            // 4. PERSIST: Save Movie first to generate ID, then link to Show
            Movie savedMovie = movieRepository.save(movie);
            show.setMovie(savedMovie);
            show.setStatus("ACTIVE"); // Necessary for home filter

            if(show.getAvailableSeats() == null) {
				show.setAvailableSeats(100);
			}
            if (show.getId() == null || show.getAvailableSeats() == null) {
                show.setAvailableSeats(show.getTotalSeats());
            }

            showRepository.save(show);

            String targetCity = (show.getTargetCity() != null) ? show.getTargetCity() : "";
            return "redirect:/?city=" + java.net.URLEncoder.encode(targetCity, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/add?error=true";
        }
    }




    @GetMapping("/admin/setup-show/{movieId}")
    public String showSetupForm(@ModelAttribute("show")Show show, @PathVariable Long movieId, Model model, HttpSession session) {
        if (session.getAttribute("adminSession") == null) {
            return "redirect:/admin/login";
        }

        Movie movie = movieRepository.findById(movieId).orElseThrow();

        Show newShow = new Show();

        newShow.setMovie(movie);
        newShow.setMovieTitle(movie.getTitle());
        newShow.setHallNumber(show.getHallNumber());
        newShow.setTotalSeats(show.getTotalSeats());
        newShow.setAvailableSeats(show.getTotalSeats()); // Initialize availability
        newShow.setStartTime(show.getStartTime());
        newShow.setTargetCity(movie.getCity());
        model.addAttribute("movie", movie);
        model.addAttribute("show", show);

        return "booking-update"; // This looks for booking-update.html
    }



    @GetMapping("/admin/delete/{id}")
    public String deleteMovie(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("adminSession") == null) {
			return "redirect:/admin/login";
		}
        movieRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/admin/save-show")
    public String saveShow(@ModelAttribute("show") Show show,
    	                   @ModelAttribute("booking") Booking booking,
                           @RequestParam("imageFile") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            // 1. Create the path where the image will sit
            String uploadDir = "src/main/resources/static/uploads/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 2. Save the file with a unique name
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 3. Save the WEB path into the database
            // This is what booking.html will use: e.g., /uploads/12345_poster.jpg
            show.setMovieImageUrl("/uploads/" + fileName);
            booking.setStatus("OPEN_FOR_BOOKING");
        }

        showRepository.save(show);
        return "redirect:/booking"; // Redirect to the user's booking page
    }

    @PostMapping("/admin/verify-and-edit")
    public String quickVerifyAdmin(@RequestParam String email,
                                   @RequestParam String password,
                                   @RequestParam Long showId,
                                   HttpSession session,
                                   RedirectAttributes redirect) {

        // 1. Simple credential check (Replace with your actual service logic)
        if ("admin@cinepass.com".equals(email) && "admin123".equals(password)) {
            // 2. Grant admin session status
            session.setAttribute("adminSession", "active");

            // 3. Redirect to the specific update page
            return "redirect:/admin/edit-show/" + showId;
        } else {
            redirect.addFlashAttribute("error", "Invalid Admin Credentials");
            return "redirect:/booking"; // Return to screenings if failed
        }
    }

    @PostMapping("/admin/delete-show/{id}")
    public String deleteShow(@PathVariable Long id, HttpSession session) {
        // Security Gate: Ensure user is still an admin
        if (session.getAttribute("adminSession") == null) {
            return "redirect:/admin/login";
        }

        // Call your service to delete the show by ID
        showRepository.deleteById(id);

        // Redirect back to the main screenings page with a success message
        return "redirect:/booking?deleted=true";
    }
}


