package com.esprit.springjwt.service;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esprit.springjwt.entity.Groups;
import com.esprit.springjwt.entity.Session;
import com.esprit.springjwt.repository.GroupsRepository;
import com.esprit.springjwt.repository.SessionRepository;

@Service
public class SessionService {
	@Autowired
	private SessionRepository SessionRepository;

	@Autowired
	private GroupsRepository groupsRepository;

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

    public void deleteSession(Long id) {
        SessionRepository.deleteById(id);
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
    

}
