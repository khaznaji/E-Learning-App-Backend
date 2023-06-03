package com.esprit.springjwt.controllers;

import com.esprit.springjwt.entity.Formation;
import com.esprit.springjwt.service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/formation")
public class FormationController {

    @Autowired
    private FormationService formationService;

    @GetMapping("/all")
    public List<Formation> getAllformation(){
        return formationService.getAllTypeForamtion();
    }

    @PostMapping("/addFormation")
    public Formation addFormation(@RequestBody Formation formation)
    {
        return  this.formationService.addFormation(formation);
    }
    // getFormations By CategorieId
    @GetMapping("/getFormationsByCategorieId/{id}")
    public List<Formation> getFormationsByCategorieId(@PathVariable("id") Long id)
    {
        return formationService.getFormationsByCategorieId(id);
    }

//get training by id
    @GetMapping("/getFormationById/{id}")
    public Formation getFormationById(@PathVariable("id") Long id)
    {
        return formationService.getFormationById(id);
    }
// add Formation by categroy id





}
