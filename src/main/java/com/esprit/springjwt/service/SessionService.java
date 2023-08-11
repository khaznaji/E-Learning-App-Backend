package com.esprit.springjwt.service;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
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

        session.setGeneratedLink(this.generateRandomWord());

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


    public static String generateRandomWord() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder word = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(characters.length());
            word.append(characters.charAt(randomIndex));
        }

        return word.toString();
    }
   
}
