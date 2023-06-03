package com.esprit.springjwt.service;

import com.esprit.springjwt.entity.Categorie;
import com.esprit.springjwt.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
