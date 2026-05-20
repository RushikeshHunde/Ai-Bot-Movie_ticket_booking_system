package com.TicketBookingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "theaters")
public class Theater {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCinemaType() {
		return cinemaType;
	}
	public void setCinemaType(String cinemaType) {
		this.cinemaType = cinemaType;
	}
	public String getPovFrontUrl() {
		return povFrontUrl;
	}
	public void setPovFrontUrl(String povFrontUrl) {
		this.povFrontUrl = povFrontUrl;
	}
	public String getPovBackUrl() {
		return povBackUrl;
	}
	public void setPovBackUrl(String povBackUrl) {
		this.povBackUrl = povBackUrl;
	}
	public String getPovLeftUrl() {
		return povLeftUrl;
	}
	public void setPovLeftUrl(String povLeftUrl) {
		this.povLeftUrl = povLeftUrl;
	}
	public String getPovRightUrl() {
		return povRightUrl;
	}
	public void setPovRightUrl(String povRightUrl) {
		this.povRightUrl = povRightUrl;
	}
	public String getPovCenterUrl() {
		return povCenterUrl;
	}
	public void setPovCenterUrl(String povCenterUrl) {
		this.povCenterUrl = povCenterUrl;
	}
	private String address;
    private String city;
    private String cinemaType; // IMAX, 4DX, etc.

    // POV Images moved here
    private String povFrontUrl;
    private String povBackUrl;
    private String povLeftUrl;
    private String povRightUrl;
    private String povCenterUrl;

}
