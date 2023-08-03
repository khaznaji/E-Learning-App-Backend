package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    //query method get feedback by formation


    @Query("select f from Feedback f where f.formation like %?1%")
    List<Feedback> findByFormation(String formation);
}
