package com.esprit.springjwt.controllers;

import com.esprit.springjwt.entity.Formation;
import com.esprit.springjwt.entity.User;
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
    //delete formation
    @DeleteMapping("/deleteFormation/{id}")
    public void deleteFormation(@PathVariable("id") Long id)
    {
        formationService.deleteFormation(id);
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
//get formation by nomformation
    @GetMapping("/getFormationByNomFormation/{nomFormation}")
    public Formation getFormationByNomFormation(@PathVariable("nomFormation") String nomFormation)
    {
        return formationService.getFormationByNomFormation(nomFormation);
    }
    //filtre formation by nomformation
    @GetMapping("/search/{nomFormation}")
    public List<Formation> getFormationsByNomFormationContains(@PathVariable String nomFormation) {
        return formationService.getFormationByNomFormationContains(nomFormation);
    }
    //update formation

    @PutMapping("/updateFormation/{id}")
    public Formation updateFormation(@PathVariable("id") Long id, @RequestBody Formation updatedFormation) {
        Formation formation = formationService.getFormationById(id);
        if (formation == null) {
            throw new IllegalArgumentException("Formation not found for ID: " + id);
        }

        updatedFormation.setId(id);
        Formation updatedFormationObj = formationService.updateFormation(updatedFormation);
        return updatedFormationObj;
    }












}
