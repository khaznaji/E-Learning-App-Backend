package com.esprit.springjwt.controllers;


import com.esprit.springjwt.entity.Hackerspaces;
import com.esprit.springjwt.entity.Projects;
import com.esprit.springjwt.service.HackerspacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Hackerspaces")
public class HackerspacesController {
    @Autowired
    HackerspacesService HackerspacesService;

    @GetMapping("/allHackerspaces")
    public List<Hackerspaces> getAllHackerspaces() {
        return HackerspacesService.getAllHackerspaces();

    }
    @PostMapping("/addHackerspaces")
    public Hackerspaces addHackerspaces(@RequestBody Hackerspaces Hackerspaces){
        return HackerspacesService.addHackerspaces(Hackerspaces);
    }
    @PutMapping("/updateHackerspaces")
    public Hackerspaces updateHackerspaces(@RequestBody Hackerspaces Hackerspaces){
        return HackerspacesService.updateHackerspaces(Hackerspaces);
    }
    @GetMapping("/getHackerspacesById/{id}")
    public Hackerspaces getHackerspacesById(@PathVariable("id") Long id)
    {
        return HackerspacesService.getHackerspacesById(id);
    }
    @GetMapping("/deleteHackerspaces/{id}")
    public void deleteHackerspaces(@PathVariable("id") Long id){
        HackerspacesService.deleteHackerspaces(id);
    }

}