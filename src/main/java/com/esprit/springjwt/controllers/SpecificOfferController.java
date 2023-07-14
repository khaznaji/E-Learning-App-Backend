package com.esprit.springjwt.controllers;

import com.esprit.springjwt.entity.*;
import com.esprit.springjwt.exception.ResourceNotFoundException;
import com.esprit.springjwt.service.SpecificOfferSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/SpecificOffer")
@CrossOrigin("*")
public class SpecificOfferController {
    @Autowired
    private SpecificOfferSerivce service;
    @PostMapping("/add")
    public SpecificOffer create(@RequestParam(value = "poste", required = false) String poste,
                         @RequestParam(value = "skills", required = false) String skills,
                         @RequestParam(value = "description", required = false) String description,
                         @RequestParam(value = "experience", required = false) String experience,
                         @RequestParam(value = "type", required = false) String type,
                         @RequestParam(value = "education", required = false) String education,
                         @RequestParam(value = "file", required = false) MultipartFile file,
                         @RequestParam(value = "nom", required = false) String nom,
                         @RequestParam(value = "numtel", required = false) Integer numtel,
                         @RequestParam(value = "email", required = false) String email,
                         @RequestParam(value = "descriptionC", required = false) String descriptionC,
                         @RequestParam(value = "adresse", required = false) String adresse) {
        try {
            SpecificOffer food = new SpecificOffer();
            food.setPoste(poste);
            food.setSkills(skills);
            food.setDescription(description);
            food.setExperience(experience);
            food.setType(type);
            food.setStatus(false);
            food.setEducation(education);
            food.setNom(nom);
            food.setNumtel(numtel);
            food.setEmail(email);
            food.setDescriptionC(descriptionC);
            food.setAdresse(adresse);

            // Generate a timestamp for the image filename
            String timestamp = Long.toString(System.currentTimeMillis());

            // Set the destination path to save the image
            String destinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\SpecificCompany\\";

            // Create a new filename using the timestamp and original filename
            String newFilename = timestamp + "_" + file.getOriginalFilename();

            // Save the file to the disk
            file.transferTo(new File(destinationPath + newFilename));
            // Assign the new filename to the "image" attribute of the Food object
            food.setImage(newFilename);


            return service.save(food);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return an appropriate error response
        }
    }
    @GetMapping("/All")
    @ResponseBody
    public List<SpecificOffer> getAll(){
        return service.getAll();
    }
    @GetMapping("/catId/{id}")
    public ResponseEntity<SpecificOffer> getEventsById(@PathVariable("id") Long id) {

        SpecificOffer employee = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
        return ResponseEntity.ok().body(employee);
    }
    @GetMapping("/getSortedByDate/{order}")
    public ResponseEntity<List<SpecificOffer>> getClaimsSortedByDate(@PathVariable String order) {
        List<SpecificOffer> claims;
        if (order.equalsIgnoreCase("asc")) {
            claims = service.getAllClaimsSortedByDateAsc();
        } else if (order.equalsIgnoreCase("desc")) {
            claims = service.getAllClaimsSortedByDateDesc();
        } else {
            // Handle invalid order parameter
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(claims, HttpStatus.OK);
    }
}
