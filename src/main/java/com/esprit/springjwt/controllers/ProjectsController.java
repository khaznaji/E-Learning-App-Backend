package com.esprit.springjwt.controllers;


import com.esprit.springjwt.entity.Projects;
import com.esprit.springjwt.entity.User;
import com.esprit.springjwt.repository.ProjectsRepository;
import com.esprit.springjwt.service.ProjectsService;
import com.esprit.springjwt.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/Projects")
@CrossOrigin("*")
public class ProjectsController {
    @Autowired
    ProjectsService ProjectsService;
    @Autowired
    ProjectsRepository projectsRepository;
    @Autowired
    userService userDetailsService;
    @GetMapping("/allProjects")
    public Set<User> getUsersWithProjects() {
        List<Projects> projects = ProjectsService.getAllProjects();
        Set<User> users = new HashSet<>();

        for (Projects project : projects) {
            users.add(project.getUser());
        }

        return users;
    }
    @GetMapping("/StudentProjects")
    public List<Projects> StudentProjects() {
        return ProjectsService.getAllProjectsStudent();
    }
    @GetMapping("/user/{userId}/projects")
    public List<Projects> getProjectsByUser(@PathVariable("userId") Long userId) {
        User user = userDetailsService.getUserById(userId); // Obtenez l'utilisateur à partir de votre service en utilisant l'ID

        if (user != null) {
            return ProjectsService.getProjectsByUser(user); // Obtenez les projets pour l'utilisateur spécifié
        }

        return Collections.emptyList(); // Si l'utilisateur n'est pas trouvé, renvoyez une liste vide ou une réponse appropriée selon vos besoins
    }

  @PostMapping("/add")
  public Projects addProjectWithFile(@RequestParam("file") MultipartFile file) {
      return ProjectsService.addProject(file);
  }

    @PutMapping("/{projectId}/update")
    public Projects updateProjectWithFile(@PathVariable("projectId") Long projectId, @RequestParam("file") MultipartFile file) {
        return ProjectsService.updateProject(projectId, file);
    }



    @GetMapping("/getProjectsById/{id}")
    public Projects getProjectsById(@PathVariable("id") Long id)
    {
        return ProjectsService.getProjectsById(id);
    }
    @DeleteMapping("/deleteProjects/{id}")
    public void deleteProjects(@PathVariable("id") Long id){
        ProjectsService.deleteProjects(id);
    }
    @PostMapping("/{projectId}/remark")
    public ResponseEntity<?> addRemarkToProject(@PathVariable Long projectId, @RequestBody String remark) {
        try {
            // Retrieve the project from the database
            Projects project = projectsRepository.findById(projectId)
                    .orElseThrow(() -> new IllegalArgumentException("Project not found"));
            // Définir la remarque par défaut "No remark" si aucune remarque n'est fournie
            if (project.getRemark() == null || project.getRemark().isEmpty()) {
                project.setRemark("No remark");
            }
            // Set the remark for the project
            project.setRemark(remark);

            // Save the updated project in the database
            Projects updatedProject = projectsRepository.save(project);

            return ResponseEntity.ok(updatedProject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }   
    }
}
