package com.TicketBookingSystem.controller; // Ensure this package is correct

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TicketBookingSystem.model.Feedback;
import com.TicketBookingSystem.repository.FeedbackRepository;

import jakarta.servlet.http.HttpSession;



@Controller
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    
    @GetMapping("/user-feedback")
    public String viewPublicFeedback(Model model) {
        // Fetch all feedback from your database
        List<Feedback> allFeedback = feedbackRepository.findAll();
        model.addAttribute("feedbacks", allFeedback);
        return "displayfeedback"; // This refers to user-feedback.html
    }
    
    
    @PostMapping("/submit-feedback")
    public String handleFeedback(
            @RequestParam String userName,
            @RequestParam String email,
            @RequestParam int rating,
            @RequestParam String description) {

        // Map the form data to your Entity
        Feedback feedback = new Feedback();
        feedback.setUserName(userName);
        feedback.setEmail(email);
        feedback.setRating(rating);
        feedback.setDescription(description);


        feedbackRepository.save(feedback);

        // Redirect back home with a success flag
        return "redirect:/?feedbackSuccess=true";
    }

    @GetMapping("/admin/view-feedbacks")
    public String viewFeedbacks(HttpSession session, Model model) {
        // Security Check: Only allow if admin is logged in
        if (session.getAttribute("adminSession") == null) {
            return "redirect:/admin/login";
        }

        // Fetch all feedback from the database
        List<Feedback> feedbacks = feedbackRepository.findAll();
        model.addAttribute("feedbacks", feedbacks);

        return "displayfeedback"; // returns displayfeedback.html
    }

    @GetMapping("/admin/delete-feedback/{id}")
    public String deleteFeedback(@PathVariable("id") Long id, HttpSession session) {
        // Security Check: Ensure only admin can delete
        if (session.getAttribute("adminSession") == null) {
            return "redirect:/admin/login";
        }

        feedbackRepository.deleteById(id);

        // Redirect back to the feedback list after deletion
        return "redirect:/admin/view-feedbacks?deleted=true";
    }
}