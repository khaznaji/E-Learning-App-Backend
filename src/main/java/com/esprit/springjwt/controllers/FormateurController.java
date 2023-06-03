package com.esprit.springjwt.controllers;

import com.esprit.springjwt.entity.Formateur;
import com.esprit.springjwt.service.FormateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/formateur")
public class FormateurController {

    @Autowired
    FormateurService formateurService;

    @GetMapping("/all")
    public List<Formateur> getAll()
    {
        return formateurService.getAllFormateurs();
    }
}
