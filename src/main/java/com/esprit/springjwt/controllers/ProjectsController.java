package com.esprit.springjwt.controllers;


import com.esprit.springjwt.entity.Projects;
import com.esprit.springjwt.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/Projects")
@CrossOrigin("*")
public class ProjectsController {
    @Autowired
    ProjectsService ProjectsService;
    @GetMapping("/allProjects")
    public List<Projects> getAllProjects() {
        return ProjectsService.getAllProjects();
    }

  @PostMapping("/add")
  public Projects addProjectWithFile(@RequestParam("file") MultipartFile file) {
      return ProjectsService.addProjects(file);
  }

    @PostMapping("/{projectId}/update")
    public Projects updateProjectWithFile(@PathVariable("projectId") Long projectId, @RequestParam("file") MultipartFile file) {
        return ProjectsService.updateProjectWithFile(projectId, file);
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

}
