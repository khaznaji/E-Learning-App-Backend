package com.esprit.springjwt.service;


import com.esprit.springjwt.entity.Session;
import com.esprit.springjwt.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {
    @Autowired
private SessionRepository SessionRepository;
    public Session addSession(Session Session){
        return SessionRepository.save(Session);
    }
    public List<Session> getAllSession(){
        return SessionRepository.findAll();
    }

    public Session updateSession(Session Session){
        return SessionRepository.save(Session);
    }
    public Session getSessionById(Long id){
        return SessionRepository.findById(id).get();
    }

    public void deleteSession(Long id) {
        SessionRepository.deleteById(id);
    }

}
