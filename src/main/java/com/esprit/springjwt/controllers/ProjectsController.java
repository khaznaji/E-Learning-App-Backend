package com.esprit.springjwt.controllers;


import com.esprit.springjwt.entity.Projects;
import com.esprit.springjwt.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Projects")
public class ProjectsController {
    @Autowired
    ProjectsService ProjectsService;
    @GetMapping("/allProjects")
    public List<Projects> getAllProjects() {
        return ProjectsService.getAllProjects();
    }
    @PostMapping("/addProjects")
    public Projects addProjects(@RequestBody Projects Projects){
        return ProjectsService.addProjects(Projects);
    }
@PutMapping("/updateProjects")
    public Projects updateProjects(@RequestBody Projects Projects){
        return ProjectsService.updateProjects(Projects);
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
