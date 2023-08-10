package com.esprit.springjwt.service;


import com.esprit.springjwt.entity.Chapters;
import com.esprit.springjwt.entity.Formation;
import com.esprit.springjwt.repository.ChaptersRepository;
import com.esprit.springjwt.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ChaptersService {
    @Autowired
    private ChaptersRepository ChaptersRepository;
    @Autowired
    private FormationRepository trainingRepository;

    public Chapters addChapters(Chapters chapters, Long id ) {
        Optional<Formation> optionalFormation = trainingRepository.findById(id);
        if (optionalFormation.isPresent()) {
            Formation formation = optionalFormation.get();
            chapters.setFormation(formation);
            chapters.setCreatedAt(LocalDateTime.now());
            return ChaptersRepository.save(chapters);
        } else {
            throw new NoSuchElementException("No Formation found with id: " + chapters.getFormation().getId());
        }
    }

    public List<Chapters> getAllChapters() {
        return ChaptersRepository.findAll();
    }

    public void deleteChaptersById(Long id) {
        ChaptersRepository.deleteById(id);
    }



    public Chapters getChaptersById(Long id) {
        return ChaptersRepository.findById(id).get();
    }

    public List<Formation> getAllTraining() {
        return trainingRepository.findAll();
    }
    // get chapters by id formation
    //public List<Chapters> getChaptersByFormationId(Long id) {
       // return ChaptersRepository.findByFormationId(id);
    //}
    //get chapters by nomformation
    public List<Chapters> getChaptersByNomFormation(String nomFormation) {
        return ChaptersRepository.findByFormationNomFormation(nomFormation);
    }
//delete chapters
public void deleteChapters(Long id) {
    ChaptersRepository.deleteById(id);
}



}






