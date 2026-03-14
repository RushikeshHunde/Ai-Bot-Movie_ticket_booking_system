package com.TicketBookingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket {
	@Id
	private String ticketId;

	@OneToOne
	@JoinColumn(name = "booking_id")
	private Booking booking;

	// Flat data for easy MySQL viewing and searching
	private String customerName;
	private String customerEmail;
	private String displayMovieTitle;
	private String displayShowTime;
	private String displayHall;
	private String seatNumbers;
	private double totalAmount;
	private String movieImageUrl;

	// Getters and Setters (Generate these in your IDE)
	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getDisplayMovieTitle() {
		return displayMovieTitle;
	}

	public void setDisplayMovieTitle(String displayMovieTitle) {
		this.displayMovieTitle = displayMovieTitle;
	}

	public String getDisplayShowTime() {
		return displayShowTime;
	}

	public void setDisplayShowTime(String displayShowTime) {
		this.displayShowTime = displayShowTime;
	}

	public String getDisplayHall() {
		return displayHall;
	}

	public void setDisplayHall(String displayHall) {
		this.displayHall = displayHall;
	}

	public String getSeatNumbers() {
		return seatNumbers;
	}

	public void setSeatNumbers(String seatNumbers) {
		this.seatNumbers = seatNumbers;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getMovieImageUrl() {
		return movieImageUrl;
	}

	public void setMovieImageUrl(String movieImageUrl) {
		this.movieImageUrl = movieImageUrl;
	}
}