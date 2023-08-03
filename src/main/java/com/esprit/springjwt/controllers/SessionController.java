package com.esprit.springjwt.controllers;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.springjwt.entity.Groups;
import com.esprit.springjwt.entity.Session;
import com.esprit.springjwt.repository.GroupsRepository;
import com.esprit.springjwt.service.SessionService;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "*")
public class SessionController {
   
    private SessionService SessionService;
    @Autowired
	private GroupsRepository groupsRepository;
    @Autowired
    public SessionController(SessionService sessionService) {
        this.SessionService = sessionService;
    }

        @GetMapping("/allSession")
    public List<Session> getAllSession() {
            return SessionService.getAllSession();
        }
        @PostMapping("/addSession")
        public Session addSession(@RequestBody Session session, @RequestParam("groupIds") List<Long> groupIds) {
            List<Groups> groups = SessionService.getGroupsByIds(groupIds);
            session.setGroups(groups);
            return SessionService.addSession(session);
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
    public void deleteSession(@PathVariable("id") Long id) {
        SessionService.deleteSession(id);
    }
    @GetMapping("/date/{date}")
    public List<Session> getSessionsByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd")Date date) {
        return SessionService.getSessionsByDate(date);
    }
    @GetMapping("/users/{userId}")
    public List<Session> getSessionsByUserId(@PathVariable Long userId) {
        return SessionService.getSessionsByUserId(userId);
    }
    @GetMapping("/formateur/{formateurId}")
    public List<Session> getSessionsByFormateurId(@PathVariable Long formateurId) {
        return SessionService.getSessionsByFormateurId(formateurId);
    }
    @GetMapping("/byGroupId/{groupId}")
    public List<Session> getSessionsByGroupId(@PathVariable Long groupId) {
        return SessionService.getSessionsByGroupId(groupId);
    }


}
