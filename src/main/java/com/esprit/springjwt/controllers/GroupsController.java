package com.esprit.springjwt.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.esprit.springjwt.repository.SessionRepository;
import com.esprit.springjwt.service.FormationService;
import com.esprit.springjwt.service.GroupsService;

@RestController
@RequestMapping("/api/groups")
public class GroupsController {
    private final GroupsService groupsService;
    @Autowired
    private FormationService trainingService;
   

    @Autowired
    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @GetMapping("/all")
    public List<Groups> getAllGroups() {
        return groupsService.getAllGroups();
    }
    
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
        String groupName = groups.getGroupName();
        boolean groupNameExists = groupsService.checkIfGroupNameExists(groupName);

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
        return groupsService.updateGroups(groups);
    }

    @DeleteMapping("/{id}")
    public void deleteGroups(@PathVariable("id") Long id) {
        groupsService.deleteGroups(id);
    }
}
