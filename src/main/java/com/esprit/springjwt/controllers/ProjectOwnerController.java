package com.esprit.springjwt.controllers;

import com.esprit.springjwt.entity.AdminProjects;
import com.esprit.springjwt.entity.ProjectOwner;
import com.esprit.springjwt.exception.ResourceNotFoundException;
import com.esprit.springjwt.repository.AdminProjectsRepository;
import com.esprit.springjwt.service.AdminProjectsService;
import com.esprit.springjwt.service.ProjectOwnerServices;
import com.esprit.springjwt.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ProjectOwner")
@CrossOrigin("*")
public class ProjectOwnerController {
    @Autowired
    ProjectOwnerServices projectOwnerServices;
    @Autowired
    private AdminProjectsService service;
    @Autowired
    private AdminProjectsRepository service2;
    @GetMapping("/All")
    @ResponseBody
    public List<ProjectOwner> getAll(){
        return projectOwnerServices.getAllProjectOwners();
    }

    @PostMapping("/add")
    public ProjectOwner create(@RequestParam("file") MultipartFile file,
                       @RequestParam("nom") String nom,

                               @RequestParam("numtel") int numtel,
                       @RequestParam("email") String email, @RequestParam("prenom") String prenom

                               ) {
        try {
            ProjectOwner food = new ProjectOwner();
            food.setNom(nom);
            food.setPrenom(prenom);
            food.setNumtel(numtel);
            food.setEmail(email);

            // Generate a timestamp for the image filename
            String timestamp = Long.toString(System.currentTimeMillis());

            // Set the destination path to save the image
            String destinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\projectOwner\\";

            // Create a new filename using the timestamp and original filename
            String newFilename = timestamp + "_" + file.getOriginalFilename();

            // Save the file to the disk
            file.transferTo(new File(destinationPath + newFilename));

            // Assign the new filename to the "image" attribute of the Food object
            food.setImage(newFilename);

            // Save the Food object in the database
            return projectOwnerServices.save(food);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle errors while saving the image or the Food object
            return null; // Return an appropriate error response
        }
    }


    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable("id")Long id){
        projectOwnerServices.deleteProjects(id);
    }
    @GetMapping("/catId/{id}")
    public ResponseEntity<ProjectOwner> getEventsById(@PathVariable("id") Long id) {

        ProjectOwner employee = projectOwnerServices.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
        return ResponseEntity.ok().body(employee);

    }

}
