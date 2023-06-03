package com.esprit.springjwt.controllers;


import com.esprit.springjwt.entity.Feedback;
import com.esprit.springjwt.entity.Session;
import com.esprit.springjwt.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Session")
@CrossOrigin(origins = "*")
public class SessionController {
    @Autowired
    SessionService SessionService;
        @GetMapping("/allSession")
    public List<Session> getAllSession() {
            return SessionService.getAllSession();
        }
    @PostMapping("/addSession")
    public Session addSession(@RequestBody Session Session){
        return SessionService.addSession(Session);
    }

        @GetMapping("/getSessionById/{id}")
        public Session getSessionById(@PathVariable("id") Long id)
        {
            return SessionService.getSessionById(id);
        }

    @PutMapping("/updateSession")
    public Session updateSession(@RequestBody Session Session){
        return SessionService.updateSession(Session);
    }
    @DeleteMapping("/deleteSession/{id}")
    public void deleteSession(@PathVariable("id") Long id){
        SessionService.deleteSession(id);
    }


}
