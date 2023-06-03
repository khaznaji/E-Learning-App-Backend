package com.esprit.springjwt.service;


import com.esprit.springjwt.entity.Feedback;
import com.esprit.springjwt.entity.Groups;
import com.esprit.springjwt.repository.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupsService {
    @Autowired
    private GroupsRepository GroupsRepository;
    public Groups addGroups(Groups Groups){
        return GroupsRepository.save(Groups);
    }
    public List<Groups> getAllGroups(){
        return GroupsRepository.findAll();
    }

public Groups updateGroups(Groups Groups){
        return GroupsRepository.save(Groups);
    }
    public Groups getGroupsById(Long id){ return GroupsRepository.findById(id).get();
    }
    public  void deleteGroups(Long id) {
        GroupsRepository.deleteById(id);
    }

}
