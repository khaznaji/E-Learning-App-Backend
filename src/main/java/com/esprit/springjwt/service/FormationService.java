package com.esprit.springjwt.service;

import com.esprit.springjwt.entity.Categorie;
import com.esprit.springjwt.entity.Chapters;
import com.esprit.springjwt.entity.Formation;
import com.esprit.springjwt.repository.CategorieRepository;
import com.esprit.springjwt.repository.ChaptersRepository;
import com.esprit.springjwt.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // Retrieve the Categorie object by its ID
        Categorie categorie = categorieRepository.findById(formation.getCategorie().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Categorie ID: " + formation.getCategorie().getId()));

        // Set the Categorie object in the Formation object
        formation.setCategorie(categorie);

        // Save the Formation object
        Formation savedFormation = formationRepository.save(formation);

        // Set the Formation object in each Chapters object
        for (Chapters chapters : savedFormation.getChapters()) {
            chapters.setFormation(savedFormation);
            chaptersRepository.save(chapters);
        }

        return savedFormation;
    }


    public List<Formation> getAllTypeForamtion() {
        return formationRepository.findAll();
    }

    public void deleteFormationById(Long id) {
        formationRepository.deleteById(id);
    }

    public Formation updateFormation(Formation formation) {
        Categorie categorie = categorieRepository.findById(formation.getCategorie().getId()).get();
        formation.setCategorie(categorie);
        return formationRepository.save(formation);
    }

    public Formation getFormationById(Long id) {
        return formationRepository.findById(id).get();
    }

    public List<Formation> getAllFormation() {
        return formationRepository.findAll();
    }

    public List<Formation> getFormationsByCategorieId(Long id) {
        return formationRepository.findByCategorieId(id);
    }
}
