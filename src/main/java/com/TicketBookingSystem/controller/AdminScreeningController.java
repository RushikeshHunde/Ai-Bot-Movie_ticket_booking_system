package com.TicketBookingSystem.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TicketBookingSystem.model.MovieUpdate;
import com.TicketBookingSystem.model.Show;
import com.TicketBookingSystem.repository.ShowRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminScreeningController {

	@Autowired
    private ShowRepository showRepository; // Use ShowRepository instead

    @GetMapping("/manage-screenings")
    public String showManageScreenings(@RequestParam(value = "keyword", required = false) String keyword,
            HttpSession session, Model model){

        if (session.getAttribute("adminSession") == null) {
            return "redirect:/admin/login";
        }

        List<Show> ongoingShows; // Change type to Show
        if (keyword != null && !keyword.isEmpty()) {
            // You will need to add this method to your ShowRepository
            ongoingShows = showRepository.findByMovieTitleContainingIgnoreCaseOrTargetCityContainingIgnoreCase(keyword, keyword);
        } else {
            ongoingShows = showRepository.findAll();
        }

        model.addAttribute("shows", ongoingShows);
        model.addAttribute("keyword", keyword);
        return "manage-screening";
    }
    
    
    
    @PostMapping("/update-screening-details")
    public String updateScreening(@ModelAttribute MovieUpdate updateDetails, HttpSession session) {
        if (session.getAttribute("adminSession") == null) return "redirect:/admin/login";

        Show existingShow = showRepository.findById(updateDetails.getMovieId()).orElse(null);
        if (existingShow != null) {
            existingShow.setStartTime(updateDetails.getNewShowTime());
            showRepository.save(existingShow);
        }

        // This '?updated=true' is what the HTML looks for
        return "redirect:/admin/manage-screenings?updated=true";
    }
    
    
    
    @PostMapping("/update-timing")
    public String updateTiming(@ModelAttribute MovieUpdate updateDetails, HttpSession session) {
        // 1. Security Check
        if (session.getAttribute("adminSession") == null) return "redirect:/admin/login";

        // 2. Find the show by ID
        Show existingShow = showRepository.findById(updateDetails.getMovieId()).orElse(null);

        if (existingShow != null && updateDetails.getNewShowTime() != null) {
            // 3. Update ONLY the start time
            existingShow.setStartTime(updateDetails.getNewShowTime());
            
            // 4. Save the change
            showRepository.save(existingShow);
        }

        return "redirect:/admin/manage-screenings?updated=true";
    }
    
    
    

}