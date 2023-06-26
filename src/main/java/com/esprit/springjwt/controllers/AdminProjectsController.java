package com.esprit.springjwt.controllers;

import com.esprit.springjwt.entity.AdminProjects;
import com.esprit.springjwt.entity.ProjectOwner;
import com.esprit.springjwt.exception.ResourceNotFoundException;
import com.esprit.springjwt.service.AdminProjectsService;
import com.esprit.springjwt.service.ProjectOwnerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/AdminProjects")
@CrossOrigin("*")
public class AdminProjectsController {
    @Autowired
    private AdminProjectsService service;
    @Autowired
    private ProjectOwnerServices Catservice;
  /*  @PostMapping("/add")
    public AdminProjects create(@RequestParam("file") MultipartFile file,
                                @RequestParam("video") MultipartFile video,
                                @RequestParam("price") Float price,
                                @RequestParam("titre") String titre,
                                @RequestParam("technologies") String technologies,
                                @RequestParam("description") String description,

                                @RequestParam("projectOwnerId") Long projectOwnerId ) {

        try {
            // Vérifier si le fichier vidéo est vide
            if (video.isEmpty()) {
                throw new IllegalArgumentException("Video file is required");
            }

            // Vérifier si le fichier vidéo est au format MP4
            if (!video.getContentType().equals("video/mp4")) {
                throw new IllegalArgumentException("Invalid video file format. Only MP4 files are allowed.");
            }
            AdminProjects food = new AdminProjects();
            food.setTitre(titre);
            food.setDescription(description);
            food.setPrice(price);
            food.setTechnologies(technologies);
// Generate a timestamp for the video filename
            String videoTimestamp = Long.toString(System.currentTimeMillis());

// Set the destination path to save the video file
            String videoDestinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\adminProjects\\";

// Create a new filename using the timestamp and original video filename
            String newVideoFilename = videoTimestamp + "_" + video.getOriginalFilename();

// Save the video file to the disk
            video.transferTo(new File(videoDestinationPath + newVideoFilename));

// Assign the new video filename to the "video" attribute of the AdminProjects object
            food.setVideo(newVideoFilename);

            // Generate a timestamp for the image filename
            String timestamp = Long.toString(System.currentTimeMillis());

            // Set the destination path to save the image
            String destinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\adminProjects\\" ;

            // Create a new filename using the timestamp and original filename
            String newFilename = timestamp + "_" + file.getOriginalFilename();

            // Save the file to the disk
            file.transferTo(new File(destinationPath + newFilename));
            // Assign the new filename to the "image" attribute of the Food object
            food.setImage(newFilename);
            ProjectOwner category = Catservice.findById(projectOwnerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + projectOwnerId));

            // Set the Category object as the category of the Food object
            food.setProjectOwner(category);

            // Set the CatName attribute with the category name
            food.setName(category.getNom());
            // Save the Food object in the database
            return service.save(food);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return an appropriate error response
        }

    }*/
    /*heeeeeeeedhi
  @PostMapping("/add")
  public AdminProjects create(@RequestParam("files") MultipartFile[] files,
                              @RequestParam("video") MultipartFile video,
                              @RequestParam("price") Float price,
                              @RequestParam("titre") String titre,
                              @RequestParam("technologies") String technologies,
                              @RequestParam("description") String description,
                              @RequestParam("projectOwnerId") Long projectOwnerId) {

      try {
          // Vérifier si le fichier vidéo est vide
          if (video.isEmpty()) {
              throw new IllegalArgumentException("Video file is required");
          }

          // Vérifier si le fichier vidéo est au format MP4
          if (!video.getContentType().equals("video/mp4")) {
              throw new IllegalArgumentException("Invalid video file format. Only MP4 files are allowed.");
          }

          AdminProjects food = new AdminProjects();
          food.setTitre(titre);
          food.setDescription(description);
          food.setPrice(price);
          food.setTechnologies(technologies);

          // Generate a timestamp for the video filename
          String videoTimestamp = Long.toString(System.currentTimeMillis());

          // Set the destination path to save the video file
          String videoDestinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\adminProjects\\";

          // Create a new filename using the timestamp and original video filename
          String newVideoFilename = videoTimestamp + "_" + video.getOriginalFilename();

          // Save the video file to the disk
          video.transferTo(new File(videoDestinationPath + newVideoFilename));

          // Assign the new video filename to the "video" attribute of the AdminProjects object
          food.setVideo(newVideoFilename);

          // Generate a timestamp for each image filename
          String imageTimestamp = Long.toString(System.currentTimeMillis());

          // Set the destination path to save the image files
          String imageDestinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\adminProjects\\";

          StringBuilder imageFilenames = new StringBuilder();

          // Iterate over the array of image files and save each one
          for (MultipartFile file : files) {
              // Create a new filename using the timestamp and original filename
              String newFilename = imageTimestamp + "_" + file.getOriginalFilename();

              // Save the file to the disk
              file.transferTo(new File(imageDestinationPath + newFilename));

              // Append the new filename to the string of image filenames
              imageFilenames.append(newFilename).append(",");
          }

          // Remove the trailing comma from the string of image filenames
          if (imageFilenames.length() > 0) {
              imageFilenames.deleteCharAt(imageFilenames.length() - 1);
          }

          // Assign the concatenated image filenames to the "image" attribute of the AdminProjects object
          food.setImage(imageFilenames.toString());

          ProjectOwner category = Catservice.findById(projectOwnerId)
                  .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + projectOwnerId));

          // Set the Category object as the category of the AdminProjects object
          food.setProjectOwner(category);

          // Set the CatName attribute with the category name
          food.setName(category.getNom());

          // Save the AdminProjects object in the database
          return service.save(food);
      } catch (IOException e) {
          e.printStackTrace();
          return null; // Return an appropriate error response
      }
  }*/
  @PostMapping("/add")
  public AdminProjects create(@RequestParam("files") MultipartFile[] files,
                              @RequestParam("video") MultipartFile video,
                              @RequestParam("price") Float price,
                              @RequestParam("titre") String titre,
                              @RequestParam("technologies") String technologies,
                              @RequestParam("description") String description,
                              @RequestParam("projectOwnerId") Long projectOwnerId) {

      try {
          // Vérifier si le fichier vidéo est vide
          if (video.isEmpty()) {
              throw new IllegalArgumentException("Video file is required");
          }

          // Vérifier si le fichier vidéo est au format MP4
          if (!video.getContentType().equals("video/mp4")) {
              throw new IllegalArgumentException("Invalid video file format. Only MP4 files are allowed.");
          }

          AdminProjects food = new AdminProjects();
          food.setTitre(titre);
          food.setDescription(description);
          food.setPrice(price);
          food.setTechnologies(technologies);

          // Generate a timestamp for the video filename
          String videoTimestamp = Long.toString(System.currentTimeMillis());

          // Set the destination path to save the video file
          String videoDestinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\adminProjects\\";

          // Create a new filename using the timestamp and original video filename
          String newVideoFilename = videoTimestamp + "_" + video.getOriginalFilename();

          // Save the video file to the disk
          video.transferTo(new File(videoDestinationPath + newVideoFilename));

          // Assign the new video filename to the "video" attribute of the AdminProjects object
          food.setVideo(newVideoFilename);

          // Generate a timestamp for each image filename
          String imageTimestamp = Long.toString(System.currentTimeMillis());

          // Set the destination path to save the image files
          String imageDestinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\adminProjects\\";

          StringBuilder imageFilenames = new StringBuilder();

          // Iterate over the array of image files and save each one
          for (MultipartFile file : files) {
              // Create a new filename using the timestamp and original filename
              String newFilename = imageTimestamp + "_" + file.getOriginalFilename();

              // Save the file to the disk
              file.transferTo(new File(imageDestinationPath + newFilename));

              // Append the new filename to the string of image filenames
              imageFilenames.append(newFilename).append(",");
          }

          // Remove the trailing comma from the string of image filenames
          if (imageFilenames.length() > 0) {
              imageFilenames.deleteCharAt(imageFilenames.length() - 1);
          }

          // Assign the concatenated image filenames to the "image" attribute of the AdminProjects object
          food.setImage(imageFilenames.toString());

          ProjectOwner category = Catservice.findById(projectOwnerId)
                  .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + projectOwnerId));

          // Set the Category object as the category of the AdminProjects object
          food.setProjectOwner(category);

          // Set the CatName attribute with the category name
          food.setName(category.getNom());
          food.setOwnerImage(category.getImage());

          // Save the AdminProjects object in the database
          return service.save(food);
      } catch (IOException e) {
          e.printStackTrace();
          return null; // Return an appropriate error response
      }
  }
    @GetMapping("/All")
    @ResponseBody
    public List<AdminProjects> getAll(){
        return service.getAll();
    }

    @PutMapping("/update/{id}")
    public AdminProjects update(@PathVariable("id") Long id,
                                @RequestParam(value = "files", required = false) MultipartFile[] files,
                                @RequestParam(value = "video", required = false) MultipartFile video,
                                @RequestParam(value = "price", required = false) Float price,
                                @RequestParam(value = "titre", required = false) String titre,
                                @RequestParam(value = "technologies", required = false) String technologies,
                                @RequestParam(value = "description", required = false) String description,
                                @RequestParam(value = "projectOwnerId", required = false) Long projectOwnerId) {
        try {
            AdminProjects existingProject = service.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("AdminProject not found with id: " + id));

            // Update the fields if provided
            if (titre != null) {
                existingProject.setTitre(titre);
            }
            if (description != null) {
                existingProject.setDescription(description);
            }
            if (price != null) {
                existingProject.setPrice(price);
            }
            if (technologies != null) {
                existingProject.setTechnologies(technologies);
            }
            if (projectOwnerId != null) {
                ProjectOwner category = Catservice.findById(projectOwnerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + projectOwnerId));
                existingProject.setProjectOwner(category);
                existingProject.setName(category.getNom());
            }

            // Update the video file if provided
            if (video != null) {
                if (video.isEmpty()) {
                    throw new IllegalArgumentException("Video file is required");
                }
                if (!video.getContentType().equals("video/mp4")) {
                    throw new IllegalArgumentException("Invalid video file format. Only MP4 files are allowed.");
                }

                String videoTimestamp = Long.toString(System.currentTimeMillis());
                String videoDestinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\adminProjects\\";
                String newVideoFilename = videoTimestamp + "_" + video.getOriginalFilename();
                video.transferTo(new File(videoDestinationPath + newVideoFilename));
                existingProject.setVideo(newVideoFilename);
            }

            // Update the image files if provided
            if (files != null && files.length > 0) {
                String imageTimestamp = Long.toString(System.currentTimeMillis());
                String imageDestinationPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\adminProjects\\";
                StringBuilder imageFilenames = new StringBuilder();

                for (MultipartFile file : files) {
                    String newFilename = imageTimestamp + "_" + file.getOriginalFilename();
                    file.transferTo(new File(imageDestinationPath + newFilename));
                    imageFilenames.append(newFilename).append(",");
                }

                if (imageFilenames.length() > 0) {
                    imageFilenames.deleteCharAt(imageFilenames.length() - 1);
                }

                existingProject.setImage(imageFilenames.toString());
            }

            return service.save(existingProject);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return an appropriate error response
        }
    }
    @GetMapping("/catId/{id}")
    public ResponseEntity<AdminProjects> getEventsById(@PathVariable("id") Long id) {

        AdminProjects employee = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
        return ResponseEntity.ok().body(employee);

    }
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable("id")Long id){
        service.deleteProjects(id);
    }
}
