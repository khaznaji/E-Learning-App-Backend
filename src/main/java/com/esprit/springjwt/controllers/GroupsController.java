package com.esprit.springjwt.controllers;


import java.util.List;

import javax.validation.Valid;

import com.esprit.springjwt.entity.AdminProjects;
import com.esprit.springjwt.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.springjwt.entity.Groups;
import com.esprit.springjwt.service.FormationService;
import com.esprit.springjwt.service.GroupsService;
import com.esprit.springjwt.service.SessionService;
import com.esprit.springjwt.entity.User;
import com.esprit.springjwt.service.FormationService;
import com.esprit.springjwt.service.GroupsService;
import com.esprit.springjwt.service.SessionService;
import com.esprit.springjwt.service.userService;

@RestController
@RequestMapping("/api/groups")
public class GroupsController {
    private final GroupsService groupsService;
    @Autowired
    private FormationService trainingService;
    @Autowired
    private  SessionService sessionService;

    @Autowired
    private userService userService;

    @Autowired
    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @GetMapping("/all")
    public List<Groups> getAllGroups() {
        return groupsService.getAllGroups();
    }
    
   /* @GetMapping("/session/{sessionId}")
=======
    @GetMapping("/session/{sessionId}")
>>>>>>> be580cfb8ee2202c856076a4d3b3ebbc07f2dcf5
    public ResponseEntity<List<Groups>> getGroupsBySessionId(@PathVariable Long sessionId) {
        List<Groups> groups = groupsService.getGroupsBySessionId(sessionId);
        if (!groups.isEmpty()) {
            return ResponseEntity.ok(groups);
        }
        return ResponseEntity.noContent().build();
<<<<<<< HEAD
    }*/
   @GetMapping("/session/{sessionId}")
   public ResponseEntity<List<Groups>> getGroupsBySessionId(@PathVariable Long sessionId) {
       List<Groups> groups = groupsService.getGroupsBySessionId(sessionId);
       if (!groups.isEmpty()) {
           return ResponseEntity.ok(groups);
       }
       return ResponseEntity.noContent().build();
   }



    @PostMapping("/add")
    public ResponseEntity<?> addGroups(@Valid @RequestBody Groups groups) {
        String GroupName = groups.getGroupName();
        boolean groupNameExists = groupsService.checkIfGroupNameExists(GroupName);
        groups.setCertificatesGenerated(false);
        // Check if the groupName already exists
        if (groupNameExists) {
            return ResponseEntity.badRequest().body("Group name already exists");
        }

        Groups createdGroup = groupsService.addGroups(groups);
        return ResponseEntity.ok(createdGroup);
    }

    @GetMapping("/{id}")
    public Groups getGroupsById(@PathVariable("id") Long id) {
        return groupsService.getGroupsById(id);
    }

    
    @GetMapping("/by-formation/{id}")
    public List<Groups> getGroupsByFormation(@PathVariable("id") Long Id) {
        return groupsService.getGroupsByFormation(Id);
    }
    

    @PutMapping("/update")
    public Groups updateGroups(@Valid @RequestBody Groups groups) {
        groups.setCertificatesGenerated(false);


        return groupsService.updateGroups(groups);
    }

    @DeleteMapping("/{id}")
    public void deleteGroups(@PathVariable("id") Long id) {
        groupsService.deleteGroups(id);
    }

    @PostMapping("/{groupId}/etudiants/{etudiantId}")
    public ResponseEntity<String> addEtudiantToGroup(@PathVariable Long groupId, @PathVariable Long etudiantId) {
        try {
            groupsService.addEtudiantToGroup(groupId, etudiantId);
            return ResponseEntity.ok("Etudiant added to the group successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{groupId}/etudiants/{etudiantId}")
    public void removeEtudiantFromGroup(@PathVariable Long groupId, @PathVariable Long etudiantId) {
            groupsService.removeEtudiantFromGroup(groupId, etudiantId);           
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<Groups>> getGroupsByUserId(@PathVariable Long userId) {
        User user = userService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Groups> userGroups = user.getGroups();
        return ResponseEntity.ok(userGroups);
    }
    @GetMapping("/by-formateur/{formateurId}")
    public ResponseEntity<List<Groups>> getGroupsByFormateurId(@PathVariable Long formateurId) {
        List<Groups> groups = groupsService.getGroupsByFormateurId(formateurId);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

}
