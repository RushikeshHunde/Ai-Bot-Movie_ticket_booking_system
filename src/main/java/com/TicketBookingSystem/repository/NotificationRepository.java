package com.TicketBookingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TicketBookingSystem.model.MovieNotification;

@Repository
public interface NotificationRepository extends JpaRepository<MovieNotification, Long> {

    // Find all users waiting for a specific movie
    List<MovieNotification> findByMovieId(Long movieId);

    // Check if a user has already signed up
    boolean existsByEmailAndMovieId(String email, Long movieId);
}