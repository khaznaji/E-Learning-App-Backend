package com.esprit.springjwt.service;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esprit.springjwt.entity.Groups;
import com.esprit.springjwt.entity.Session;
import com.esprit.springjwt.entity.User;
import com.esprit.springjwt.repository.GroupsRepository;
import com.esprit.springjwt.repository.SessionRepository;
import com.esprit.springjwt.repository.UserRepository;

@Service
public class SessionService {
	@Autowired
	private SessionRepository SessionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private GroupsRepository groupsRepository;
	@Autowired
    public SessionService(UserRepository userRepository, GroupsRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupsRepository = groupRepository;
    }
	public Session addSession(Session session) {
	    List<Long> groupIds = session.getGroups().stream()
	            .map(Groups::getId)
	            .collect(Collectors.toList());

	    List<Groups> groups = groupsRepository.findAllById(groupIds);
	    session.setGroups(groups);

	    for (Groups group : groups) {
	        group.getSessions().add(session);
	    }

	    return SessionRepository.save(session);
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

    public void deleteSession(Long sessionId) {
        Session session = SessionRepository.findById(sessionId).orElse(null);
        if (session != null) {
            SessionRepository.delete(session);
        }
    }
    public List<Session> getSessionsByDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate nextDate = localDate.plusDays(1); 

        Date startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(nextDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return SessionRepository.findByStartDateBetween(startDate, endDate);
    }
    public List<Groups> getGroupsByIds(List<Long> groupIds) {
        return groupsRepository.findAllById(groupIds);
    }
    /*public List<Session> getSessionsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        List<Groups> groups = user.getGroups();
        List<Session> sessions = new ArrayList<>();
        for (Groups group : groups) {
            sessions.addAll(group.getSessions());
        }

        return sessions;
    }*/
    public List<Session> getSessionsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }

        List<Groups> groups = groupsRepository.findByEtudiantsContaining(user);
        Set<Session> uniqueSessions = new HashSet<>();
        for (Groups group : groups) {
            uniqueSessions.addAll(group.getSessions());
        }

        return new ArrayList<>(uniqueSessions);
    }
    public List<Session> getSessionsByFormateurId(Long formateurId) {
        User formateur = userRepository.findById(formateurId).orElse(null);
        if (formateur == null) {
            return null;
        }

        List<Groups> groups = groupsRepository.findByFormateur(formateur);
        Set<Session> uniqueSessions = new HashSet<>();
        for (Groups group : groups) {
            uniqueSessions.addAll(group.getSessions());
        }

        return new ArrayList<>(uniqueSessions);
    }
    public List<Session> getSessionsByGroupId(Long groupId) {
        Groups group = groupsRepository.findById(groupId).orElse(null);
        if (group == null) {
            return null;
        }

        Set<Session> uniqueSessions = new HashSet<>(group.getSessions());

        return new ArrayList<>(uniqueSessions);
    }
   
    
    
   
}
