package com.esprit.springjwt.service;

import com.esprit.springjwt.entity.Categorie;
import com.esprit.springjwt.entity.Formation;
import com.esprit.springjwt.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieServices {

    @Autowired
    private CategorieRepository categorieRepository;

    public Categorie addCategorie(Categorie Categorie){
        return categorieRepository.save(Categorie);
    }
    public List<Categorie> getAllCategotries(){
        return categorieRepository.findAll();
    }

    public Categorie updateCategorie(Categorie Categorie){
        return categorieRepository.save(Categorie);
    }

    public Categorie getCategorieById(Long id){
        return categorieRepository.findById(id).get();
    }

    public void deleteCategorie(Long id){
        categorieRepository.deleteById(id);

    }
    public List<Categorie> getCategoriesByNomCateContains(String nomCate) {
        return categorieRepository.findByNomCateContains(nomCate);
    }


   //update categorie
   public Categorie updateCategorie(Long id, Categorie updatedCategorie) {
       // Find the existing category in the database by ID
       Optional<Categorie> existingCategorieOptional = categorieRepository.findById(id);
       if (existingCategorieOptional.isPresent()) {
           Categorie existingCategorie = existingCategorieOptional.get();

           // Update the properties of the existing category with the new values
           existingCategorie.setNomCate(updatedCategorie.getNomCate());
           // Add more properties here as needed

           // Save the updated category back to the database
           return categorieRepository.save(existingCategorie);
       } else {
           // Category with the given ID not found, you can handle this case as needed
           return null;
       }
   }



}
