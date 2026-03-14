package com.TicketBookingSystem.model;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User {

    private String employeeId;

    public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getPermissionsLevel() {
		return permissionsLevel;
	}
	public void setPermissionsLevel(String permissionsLevel) {
		this.permissionsLevel = permissionsLevel;
	}
	private String permissionsLevel;
}