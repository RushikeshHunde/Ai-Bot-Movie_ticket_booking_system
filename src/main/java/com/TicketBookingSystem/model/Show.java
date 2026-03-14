package com.TicketBookingSystem.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "shows")
public class Show {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "movie_id", referencedColumnName = "id") // Explicitly point to Movie ID
	private Movie movie;

	@OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Booking> bookings;


	@PrePersist
	public void prePersist() {
	    if (this.totalSeats == null) {
	        this.totalSeats = 50; // Default fallback
	    }
	    if (this.AvailableSeats == null) {
	        this.AvailableSeats = this.totalSeats;
	    }
	}


	// Show Details
	private String hallNumber;
	private Integer totalSeats = 100;
	private Integer AvailableSeats = 100;
	private String bookedSeats;
	private LocalDateTime startTime;
	private String status;
	private String movieTitle;
	private Double movieRating;
	private Integer movieDuration;
	private String movieImageUrl;
	private String theatreName;
	private String theatreAddress;
	private String cinemaType;
	private String targetCity;

	public String getTheatreName() {
		return theatreName;
	}

	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}

	public String getTheatreAddress() {
		return theatreAddress;
	}

	public void setTheatreAddress(String theatreAddress) {
		this.theatreAddress = theatreAddress;
	}

	public String getCinemaType() {
		return cinemaType;
	}

	public void setCinemaType(String cinemaType) {
		this.cinemaType = cinemaType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHallNumber() {
		return hallNumber;
	}

	public void setHallNumber(String hallNumber) {
		this.hallNumber = hallNumber;
	}

	public Integer getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}

	public Integer getAvailableSeats() {
		return AvailableSeats;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.AvailableSeats = availableSeats;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public Double getMovieRating() {
		return movieRating;
	}

	public void setMovieRating(Double movieRating) {
		this.movieRating = movieRating;
	}

	public Integer getMovieDuration() {
		return movieDuration;
	}

	public void setMovieDuration(Integer movieDuration) {
		this.movieDuration = movieDuration;
	}

	public String getMovieImageUrl() {
		return movieImageUrl;
	}

	public void setMovieImageUrl(String movieImageUrl) {
		this.movieImageUrl = movieImageUrl;
	}

	public String getTargetCity() {
		return targetCity;
	}

	public void setTargetCity(String targetCity) {
		this.targetCity = targetCity;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public String getBookedSeats() {
		return bookedSeats;
	}

	public void setBookedSeats(String bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
}