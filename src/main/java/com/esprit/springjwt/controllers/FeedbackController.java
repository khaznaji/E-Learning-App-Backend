package com.esprit.springjwt.controllers;


import com.esprit.springjwt.entity.Categorie;
import com.esprit.springjwt.entity.Feedback;
import com.esprit.springjwt.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Feedback")
public class FeedbackController {
    @Autowired
    FeedbackService FeedbackService;

    @GetMapping("/allFeedback")
    public List<Feedback> getAllFeedback() {return FeedbackService.getAllFeedback();
    }
    @PostMapping("/addFeedback/{idSession}")
    public Feedback addFeedback(@RequestBody Feedback Feedback, @PathVariable("idSession") Long idSession){
        return FeedbackService.addFeedback(Feedback, idSession);
    }
    @GetMapping("/getFeedbackById/{id}")
    public Feedback getFeedbackById(@PathVariable("id") Long id)
    {
        return FeedbackService.getFeedbackById(id);
    }

    @PutMapping("/updateFeedback/{idSession}")
    public Feedback updateFeedback(@RequestBody Feedback Feedback, @PathVariable("idSession") Long idSession){
        return FeedbackService.updateFeedback(Feedback, idSession);
    }
    @DeleteMapping("/deleteFeedback/{id}")
    public void deleteFeedback(@PathVariable("id") Long id){
        FeedbackService.deleteFeedback(id);
    }
}
