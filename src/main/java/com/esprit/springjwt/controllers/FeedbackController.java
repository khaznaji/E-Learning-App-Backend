package com.esprit.springjwt.controllers;


import com.esprit.springjwt.entity.Categorie;
import com.esprit.springjwt.entity.Feedback;
import com.esprit.springjwt.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {
    @Autowired
    FeedbackService FeedbackService;

    @GetMapping("/allFeedback")
    public List<Feedback> getAllFeedback() {return FeedbackService.getAllFeedback();
    }
    @PostMapping("/addFeedback")
    public Feedback addFeedback(@RequestBody Feedback Feedback){
        return FeedbackService.addFeedback(Feedback);
    }
    @GetMapping("/getFeedbackById/{id}")
    public Feedback getFeedbackById(@PathVariable("id") Long id)
    {
        return FeedbackService.getFeedbackById(id);
    }

    @GetMapping("/getFeedbackByFormation/{formation}")
    public List <Feedback> getFeedbackByFormation(@PathVariable("formation") String formation)
    {
        return FeedbackService.getFeedbackByFormation(formation);
    }

    @PutMapping("/updateFeedback/{idSession}")
    public Feedback updateFeedback(@RequestBody Feedback Feedback, @PathVariable("idSession") Long idSession){
        return FeedbackService.updateFeedback(Feedback, idSession);
    }
    @DeleteMapping("/deleteFeedback/{id}")
    public void deleteFeedback(@PathVariable("id") Long id){
        FeedbackService.deleteFeedback(id);
    }


    // get feedback by posted true
    @GetMapping("/getFeedbackByPosted")
    public List<Feedback> getFeedbackByPosted(){
        return FeedbackService.getFeedbackPosted();
    }

    // update feedback by posted
    @PatchMapping("/updateFeedbackPosted/{id}")
    public Feedback updateFeedbackPosted(@PathVariable("id") Long id){
        return FeedbackService.updateFeedbackPosted(id);
    }

}
