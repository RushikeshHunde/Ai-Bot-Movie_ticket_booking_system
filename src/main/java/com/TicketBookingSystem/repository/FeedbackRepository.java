package com.TicketBookingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TicketBookingSystem.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>{

}
