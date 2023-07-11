package com.esprit.springjwt.controllers;
import com.esprit.springjwt.entity.AdminProjects;
import com.esprit.springjwt.entity.ProjectOwner;
import com.esprit.springjwt.entity.company;
import com.esprit.springjwt.exception.ResourceNotFoundException;
import com.esprit.springjwt.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/Company")
@CrossOrigin("*")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping("/All")
    @ResponseBody
    public List<company> getAll(){
        return companyService.getAll();
    }

    @PostMapping("/add")
    public company create(@RequestParam("file") MultipartFile file,
                          @RequestParam("nom") String nom,

                          @RequestParam("numtel") int numtel,
                          @RequestParam("email") String email, @RequestParam("description") String description,
                          @RequestParam("adresse") String adresse

    ) {
        try {
            company food = new company();
            food.setNom(nom);
            food.setAdresse(adresse);
            food.setDescription(description);

            food.setNumtel(numtel);
            food.setEmail(email);
            food.setStatus(true);

            // Generate a timestamp for the image filename
            String timestamp = Long.toString(System.currentTimeMillis());

            // Set the destination path to save the image
            String destinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\Company\\";

            // Create a new filename using the timestamp and original filename
            String newFilename = timestamp + "_" + file.getOriginalFilename();

            // Save the file to the disk
            file.transferTo(new File(destinationPath + newFilename));

            // Assign the new filename to the "image" attribute of the Food object
            food.setImage(newFilename);

            // Save the Food object in the database
            return companyService.save(food);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle errors while saving the image or the Food object
            return null; // Return an appropriate error response
        }
    }
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable("id")Long id){
        companyService.delete(id);
    }
    @GetMapping("/catId/{id}")
    public ResponseEntity<company> getEventsById(@PathVariable("id") Long id) {

        company employee = companyService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
        return ResponseEntity.ok().body(employee);

    }
    /*
    @PutMapping("/update/{id}")
    public ProjectOwner updateEmployee(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nom") String nom,

            @RequestParam("numtel") int numtel,
            @RequestParam("email") String email, @RequestParam("description") String description,
            @RequestParam("adresse") String adresse , @PathVariable("id") Long id
    ) throws ResourceNotFoundException {
        company projectOwner = companyService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id: " + id));

        try {
            company food = new company();
            food.setNom(nom);
            food.setAdresse(adresse);
            food.setDescription(description);

            food.setNumtel(numtel);
            food.setEmail(email);
            food.setStatus(true);


            if (file != null && !file.isEmpty()) {
                // User has uploaded a new image, generate a timestamp for the filename
                String timestamp = Long.toString(System.currentTimeMillis());

                // Set the destination path to save the image
                String destinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\Company\\";

                // Create a new filename using the timestamp and original filename
                String newFilename = timestamp + "_" + file.getOriginalFilename();

                // Save the file to the disk
                file.transferTo(new File(destinationPath + newFilename));

                // Delete the previous image file (optional)
                String oldFilename = projectOwner.getImage();
                if (oldFilename != null) {
                    File oldFile = new File(destinationPath + oldFilename);
                    oldFile.delete();
                }

                // Assign the new filename to the "image" attribute of the ProjectOwner object
                projectOwner.setImage(newFilename);

                // Update the ownerImage attribute of related AdminProjects
                List<AdminProjects> adminProjects = service2.findByProjectOwner(projectOwner);
                for (AdminProjects adminProject : adminProjects) {
                    adminProject.setOwnerImage(newFilename);
                    companyService.save(adminProject);
                }

                System.out.println("nnnnnnn");

            } else if (file == null || file.isEmpty()) {
                // No new file is selected, keep the existing filename
                String existingFilename = projectOwner.getImage();
                projectOwner.setImage(existingFilename);
                System.out.println("eeeeeeeeeee");
            }

            // Save the ProjectOwner object in the database
            return projectOwnerServices.save(projectOwner);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle errors while saving the image or the ProjectOwner object
            return null; // Return an appropriate error response
        }*/
    @PostMapping("/addC")
    public company createC(@RequestParam("file") MultipartFile file,
                          @RequestParam("nom") String nom,

                          @RequestParam("numtel") int numtel,
                          @RequestParam("email") String email, @RequestParam("description") String description,
                          @RequestParam("adresse") String adresse

    ) {
        try {
            company food = new company();
            food.setNom(nom);
            food.setAdresse(adresse);
            food.setDescription(description);
            food.setNumtel(numtel);
            food.setEmail(email);
            food.setStatus(false);

            // Generate a timestamp for the image filename
            String timestamp = Long.toString(System.currentTimeMillis());

            // Set the destination path to save the image
            String destinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\Company\\";

            // Create a new filename using the timestamp and original filename
            String newFilename = timestamp + "_" + file.getOriginalFilename();

            // Save the file to the disk
            file.transferTo(new File(destinationPath + newFilename));

            // Assign the new filename to the "image" attribute of the Food object
            food.setImage(newFilename);

            // Save the Food object in the database
            return companyService.save(food);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle errors while saving the image or the Food object
            return null; // Return an appropriate error response
        }
    }

    @PutMapping ("/updateStatus/{id}")
    public ResponseEntity<Void> UpdateComplaintAdmin(@PathVariable Long id, @RequestParam boolean status) {
        companyService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/getStatus/{status}")
    public ResponseEntity<List<company>> getClaimsByStatus(@PathVariable boolean status) {
        List<company> claims = companyService.getClaimsByStatus(status);
        return new ResponseEntity<>(claims, HttpStatus.OK);
    }
/*    @PutMapping("/update/{id}")
    public company updateEmployee(
            @PathVariable(value = "id") Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("nom") String nom,

            @RequestParam("numtel") int numtel,
            @RequestParam("email") String email, @RequestParam("description") String description,
            @RequestParam("adresse") String adresse
    ) throws ResourceNotFoundException {
        company food = companyService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id: " + id));

        try {
            food.setNom(nom);
            food.setAdresse(adresse);
            food.setDescription(description);
            food.setNumtel(numtel);
            food.setEmail(email);
            food.setStatus(false);

            if (file != null && !file.isEmpty()) {
                // User has uploaded a new image, generate a timestamp for the filename
                String timestamp = Long.toString(System.currentTimeMillis());

                // Set the destination path to save the image
                String destinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\Company\\";

                // Create a new filename using the timestamp and original filename
                String newFilename = timestamp + "_" + file.getOriginalFilename();

                // Save the file to the disk
                file.transferTo(new File(destinationPath + newFilename));

                // Delete the previous image file (optional)
                String oldFilename = food.getImage();
                if (oldFilename != null) {
                    File oldFile = new File(destinationPath + oldFilename);
                    oldFile.delete();
                }

                // Assign the new filename to the "image" attribute of the ProjectOwner object
                food.setImage(newFilename);

                // Update the ownerImage attribute of related AdminProjects
              /*  List<AdminProjects> adminProjects = service2.findByProjectOwner(projectOwner);
                for (AdminProjects adminProject : adminProjects) {
                    adminProject.setOwnerImage(newFilename);
                    service.save(adminProject);
                }*/

            /*    System.out.println("nnnnnnn");

            } else if (file == null || file.isEmpty()) {
                // No new file is selected, keep the existing filename
                String existingFilename = food.getImage();
                food.setImage(existingFilename);
                System.out.println("eeeeeeeeeee");
            }

            // Save the ProjectOwner object in the database
            return companyService.save(food);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle errors while saving the image or the ProjectOwner object
            return null; // Return an appropriate error response
        }
    }*/
}
