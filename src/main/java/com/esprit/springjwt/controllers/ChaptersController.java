package com.esprit.springjwt.controllers;


import com.esprit.springjwt.entity.Chapters;
import com.esprit.springjwt.service.ChaptersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/chapters")
public class ChaptersController {
    @Autowired
    ChaptersService ChaptersService;

    @GetMapping("/allChapters")
    public List<Chapters> getAllChapters()
    {
        return ChaptersService.getAllChapters();
    }
    //add chapters by id formation
@PostMapping("/addChapters/{id}")
    public Chapters addChapters(@RequestBody Chapters Chapters,@PathVariable("id") Long id){
        return ChaptersService.addChapters(Chapters,id);
    }
    @GetMapping("/getChaptersById/{id}")
    public Chapters getChaptersById(@PathVariable("id") Long id)
    {
        return ChaptersService.getChaptersById(id);
    }
    @PutMapping("/updateChapters")
public Chapters updateChapters(@RequestBody Chapters Chapters){
        return ChaptersService.updateChapters(Chapters);
    }

}
