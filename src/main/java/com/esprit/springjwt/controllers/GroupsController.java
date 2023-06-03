package com.esprit.springjwt.controllers;


import com.esprit.springjwt.entity.Groups;
import com.esprit.springjwt.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Groups")
public class GroupsController {
    @Autowired
   GroupsService GroupsService;
        @GetMapping("/allGroups")
    public List<Groups> getAllGroups() {
            return GroupsService.getAllGroups();
        }
    @PostMapping("/addGroups")
public Groups addGroups(@RequestBody Groups Groups){
        return GroupsService.addGroups(Groups);
    }

        @GetMapping("/getGroupsById/{id}")
        public Groups getGroupsById(@PathVariable("id") Long id)
        {
            return GroupsService.getGroupsById(id);
        }
        @PutMapping("/updateGroups")
    public Groups updateGroups(@RequestBody Groups Groups){
        return GroupsService.updateGroups(Groups);
        }
        @DeleteMapping("/deleteGroups/{id}")
    public void deleteGroups(@PathVariable("id") Long id){
        GroupsService.deleteGroups(id);
        }
}
