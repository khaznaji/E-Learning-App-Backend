package com.esprit.springjwt.service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esprit.springjwt.entity.Groups;
import com.esprit.springjwt.entity.Session;
import com.esprit.springjwt.repository.GroupsRepository;
import com.esprit.springjwt.repository.SessionRepository;

@Service
public class GroupsService {
    private final GroupsRepository groupsRepository;
    @Autowired
    private  SessionRepository sessionRepository;
    @Autowired
    public GroupsService(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    public Groups addGroups(Groups groups) {
        return groupsRepository.save(groups);
    }

    public List<Groups> getAllGroups() {
        return groupsRepository.findAll();
    }

    public Groups updateGroups(Groups groups) {
        return groupsRepository.save(groups);
    }

    public Groups getGroupsById(Long id) {
        Optional<Groups> optionalGroups = groupsRepository.findById(id);
        return optionalGroups.orElse(null);
    }

    public void deleteGroups(Long id) {
        groupsRepository.deleteById(id);
    }
    public boolean checkIfGroupNameExists(String groupName) {
        return groupsRepository.existsByGroupNameIgnoreCase(groupName);
    }
    public List<Groups> getGroupsByFormation(Long Id) {
        return groupsRepository.findByFormationId(Id);
    }
    public List<Groups> getGroupsByIds(List<Long> groupIds) {
        return groupsRepository.findAllById(groupIds);
    }
    public List<Groups> getGroupsBySessionId(Long sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            return session.getGroups();
        }
        return Collections.emptyList();
    }
    
  
}
