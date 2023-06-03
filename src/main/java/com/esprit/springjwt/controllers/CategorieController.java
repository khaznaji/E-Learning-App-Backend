package com.esprit.springjwt.controllers;

import com.esprit.springjwt.entity.Categorie;
import com.esprit.springjwt.service.CategorieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categorie")
public class CategorieController {

    @Autowired
    CategorieServices categorieServices;


    @GetMapping("/allCategories")
    public List<Categorie> getAllCategories()
    {
        return categorieServices.getAllCategotries();
    }

    @PostMapping("/addCategories")
    public Categorie addCategories(@RequestBody Categorie categorie){
        return categorieServices.addCategorie(categorie);
    }

    @GetMapping("/getCategoriesById/{id}")
    public Categorie getCategorieById(@PathVariable("id") Long id)
    {
        return categorieServices.getCategorieById(id);
    }

    @PutMapping("/updateCategories")
    public Categorie updateCategories(@RequestBody Categorie categorie){
        return categorieServices.updateCategorie(categorie);
    }

    @DeleteMapping("/deleteCategories/{id}")
    public void deleteCategories(@PathVariable("id") Long id){
         categorieServices.deleteCategorie(id);
    }





}
