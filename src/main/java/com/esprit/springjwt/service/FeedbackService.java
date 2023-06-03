package com.esprit.springjwt.service;


import com.esprit.springjwt.entity.Categorie;
import com.esprit.springjwt.entity.Chapters;
import com.esprit.springjwt.entity.Feedback;
import com.esprit.springjwt.entity.Training;
import com.esprit.springjwt.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository FeedbackRepository;
    @Autowired
    private SessionService sessionService;

    public Feedback addFeedback(Feedback Feedback, Long idSession){
        Feedback.setSession(sessionService.getSessionById(idSession));
        return FeedbackRepository.save(Feedback);
    }
    public List<Feedback> getAllFeedback(){
        return FeedbackRepository.findAll();
    }

  //updqte Feedback with checking if the Session exist

    public Feedback updateFeedback(Feedback Feedback, Long idSession){
        Feedback.setSession(sessionService.getSessionById(idSession));
        return FeedbackRepository.save(Feedback);




    }
    public Feedback getFeedbackById(Long id){
        return FeedbackRepository.findById(id).get();
    }

    public void deleteFeedback(Long id) {
        FeedbackRepository.deleteById(id);


    }



}
