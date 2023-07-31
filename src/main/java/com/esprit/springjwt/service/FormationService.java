package com.esprit.springjwt.service;

import com.esprit.springjwt.entity.Categorie;
import com.esprit.springjwt.entity.Chapters;
import com.esprit.springjwt.entity.Formation;
import com.esprit.springjwt.entity.User;
import com.esprit.springjwt.repository.CategorieRepository;
import com.esprit.springjwt.repository.ChaptersRepository;
import com.esprit.springjwt.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FormationService {

    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private ChaptersRepository chaptersRepository;

    public Formation addFormation(Formation formation) {
        Long categoryId = formation.getCategorie().getId();
        // Make sure the categoryId is not null
        if (categoryId == null) {
            throw new IllegalArgumentException("Invalid Categorie ID: " + categoryId);
        }

        // Retrieve the Categorie object by its ID
        Categorie categorie = categorieRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Categorie ID: " + categoryId));

        // Set the Categorie object in the Formation object
        formation.setCategorie(categorie);
        //display the date now
        formation.setCreatedAt(LocalDateTime.now());
        // Save the Formation object
        Formation savedFormation = formationRepository.save(formation);

        // Set the Formation object in each Chapters object


        return savedFormation;
    }

    public List<Formation> getAllTypeForamtion() {
        return formationRepository.findAll();
    }


   //update Formation




    public Formation getFormationById(Long id) {
        return formationRepository.findById(id).get();
    }

    public List<Formation> getAllFormation() {
        return formationRepository.findAll();
    }

    public List<Formation> getFormationsByCategorieId(Long id) {
        return formationRepository.findByCategorieId(id);
    }

    public Formation getFormationByNomFormation(String nomFormation) {
        return formationRepository.findByNomFormation(nomFormation);
    }

    //delete formation
    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }
    //filtre formation by nomformation
    public List<Formation> getFormationByNomFormationContains(String nomFormation) {
        return formationRepository.findByNomFormationContains(nomFormation);
    }
// update formation
public Formation updateFormation(Formation updatedFormation) {
    Long formationId = updatedFormation.getId();
    // Make sure the formationId is not null
    if (formationId == null) {
        throw new IllegalArgumentException("Invalid Formation ID: " + formationId);
    }

    // Retrieve the existing Formation object by its ID
    Formation existingFormation = formationRepository.findById(formationId)
            .orElseThrow(() -> new IllegalArgumentException("Formation not found for ID: " + formationId));

    // Update the relevant fields of the existing Formation object
    existingFormation.setNomFormation(updatedFormation.getNomFormation());
    existingFormation.setDescription(updatedFormation.getDescription());
    existingFormation.setNbChapters(updatedFormation.getNbChapters());

    // Save the updated Formation object
    Formation updatedFormationObj = formationRepository.save(existingFormation);

    return updatedFormationObj;
}



}


