package com.TicketBookingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TicketBookingSystem.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	List<Movie> findByTitleContainingIgnoreCaseOrCityContainingIgnoreCase(String title, String city);

 
}