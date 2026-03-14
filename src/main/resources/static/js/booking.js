let movieTotals = {};

    function toggleSeat(btn, showId, price) {
        if (!movieTotals[showId]) {
            movieTotals[showId] = { total: 0, seats: [] };
        }

        const isSelected = btn.classList.toggle('bg-amber-500');
        btn.classList.toggle('text-black', isSelected);
        
        const seatNumber = btn.innerText.trim();

        if (isSelected) {
            movieTotals[showId].total += price;
            movieTotals[showId].seats.push(seatNumber);
        } else {
            movieTotals[showId].total -= price;
            movieTotals[showId].seats = movieTotals[showId].seats.filter(s => s !== seatNumber);
        }

        document.getElementById('input-seats-' + showId).value = movieTotals[showId].seats.join(',');
        document.getElementById('input-amount-' + showId).value = movieTotals[showId].total;
        document.getElementById('count-' + showId).innerText = movieTotals[showId].seats.length;
        document.getElementById('total-' + showId).innerText = movieTotals[showId].total;
    }
    
    
    function updateShowAvailability() {
        const now = new Date();
        
        // Find every show card on the page
        document.querySelectorAll('.show-card').forEach(card => {
            const startTime = new Date(card.getAttribute('data-start-time'));
            
            if (now >= startTime) {
                // If time has hit, replace the booking form with the error message
                const form = card.querySelector('.booking-form');
                const errorMsg = card.querySelector('.error-message');
                
                if (form) form.classList.add('hidden');
                if (errorMsg) errorMsg.classList.remove('hidden');
            }
        });
    }
    setInterval(updateShowAvailability, 1000);
    
    function syncShowsWithClock() {
        const now = new Date();

        document.querySelectorAll('.show-card').forEach(card => {
            const showId = card.id.replace('show-card-', '');
            const startTime = new Date(card.getAttribute('data-start-time'));
            
            const bookingSection = document.getElementById('booking-section-' + showId);
            const expiredMsg = document.getElementById('expired-msg-' + showId);
            const timerLabel = document.getElementById('timer-' + showId);

            if (now >= startTime) {
                // TIME HIT: Hide booking, show message
                if (bookingSection) bookingSection.classList.add('hidden');
                if (expiredMsg) expiredMsg.classList.remove('hidden');
                if (timerLabel) timerLabel.innerText = "CLOSED";
            } else {
                // FUTURE: Show countdown
                const diff = startTime - now;
                const mins = Math.floor(diff / 1000 / 60);
                const secs = Math.floor((diff / 1000) % 60);
                if (timerLabel) timerLabel.innerText = `Starts in ${mins}m ${secs}s`;
            }
        });
    }
	
	
	document.addEventListener('DOMContentLoaded', function() {
	    // Run the seat disabling logic
	    disableBookedSeats();
	    
	    // Start the clock/timer sync
	    setInterval(syncShowsWithClock, 1000);
	});

	function disableBookedSeats() {
	    // Find all seat buttons on the page
	    document.querySelectorAll('.seat-btn').forEach(btn => {
	        const bookedString = btn.getAttribute('data-booked-list'); // Get "1,5,10"
	        const seatNum = btn.getAttribute('data-seat-num'); // Get "5"

	        if (bookedString) {
	            // Split the string and check if this specific seat number exists in it
	            const bookedArray = bookedString.split(',');
	            
	            if (bookedArray.includes(seatNum)) {
	                btn.disabled = true;
	                // Add your visual "Blurred/Disabled" styles
	                btn.classList.add('opacity-20', 'grayscale', 'cursor-not-allowed');
	                btn.classList.remove('hover:border-purple-500', 'hover:border-amber-500');
	                // Remove the click event entirely for safety
	                btn.removeAttribute('onclick');
	            }
	        }
	    });
	}
	
	
	document.addEventListener('DOMContentLoaded', function() {
	    // 1. Get the string from the hidden field or model
	    // Assuming you have: <input type="hidden" id="booked-seats-data" th:value="${show.bookedSeats}">
	    const bookedSeatsString = document.getElementById('booked-seats-data').value;
	    
	    if (bookedSeatsString) {
	        const bookedArray = bookedSeatsString.split(',');

	        // 2. Find every seat button
	        document.querySelectorAll('.seat-btn').forEach(btn => {
	            const seatNum = btn.innerText.trim();
	            
	            if (bookedArray.includes(seatNum)) {
	                // 3. Make it "Blurred" and unclickable
	                btn.disabled = true;
	                btn.classList.add('opacity-20', 'cursor-not-allowed', 'grayscale');
	                btn.classList.remove('hover:bg-amber-500', 'bg-slate-800'); // Remove interaction
	                btn.onclick = null; // Disable the toggle function
	            }
	        });
	    }
	});
	function openAdminModal(showId) {
	        // Set the ID so the controller knows which show we are targeting
	        document.getElementById('targetShowId').value = showId;
	        toggleAdminAuthModal();
	    }

	    function toggleAdminAuthModal() {
	        const modal = document.getElementById('adminAuthModal');
	        modal.classList.toggle('hidden');
	    }
    // Run every second
    setInterval(syncShowsWithClock, 1000);