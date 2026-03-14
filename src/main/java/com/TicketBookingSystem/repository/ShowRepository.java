package com.TicketBookingSystem.repository;

import java.time.LocalDateTime;
import java.util.List;

// 1. MUST use the JPA version, NOT the JDBC version
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.TicketBookingSystem.model.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long>{

	List<Show> findByMovieTitleContainingIgnoreCaseOrTargetCityContainingIgnoreCase(String title, String city);

    // 2. Ensure this is exactly as written here
    @Query("SELECT s FROM Show s WHERE s.startTime >= :bufferTime ORDER BY s.startTime ASC")
    List<Show> findActiveShows(@Param("bufferTime") LocalDateTime bufferTime);
}