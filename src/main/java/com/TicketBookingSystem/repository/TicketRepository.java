package com.TicketBookingSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TicketBookingSystem.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

	Optional<Ticket> findByTicketIdAndCustomerEmail(String ticketId, String customerEmail);
}