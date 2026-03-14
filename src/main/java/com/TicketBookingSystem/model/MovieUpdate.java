package com.TicketBookingSystem.model;

import java.time.LocalDateTime;

public class MovieUpdate {
    private Long movieId;
    private Double newPrice;
    private LocalDateTime newShowTime;
    private String updatedByAdminId;
	public Long getMovieId() {
		return movieId;
	}
	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
	public Double getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}
	public LocalDateTime getNewShowTime() {
		return newShowTime;
	}
	public void setNewShowTime(LocalDateTime newShowTime) {
		this.newShowTime = newShowTime;
	}
	public String getUpdatedByAdminId() {
		return updatedByAdminId;
	}
	public void setUpdatedByAdminId(String updatedByAdminId) {
		this.updatedByAdminId = updatedByAdminId;
	}
}