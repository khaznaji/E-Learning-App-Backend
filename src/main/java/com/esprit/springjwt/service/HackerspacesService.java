package com.esprit.springjwt.service;


import com.esprit.springjwt.entity.Hackerspaces;
import com.esprit.springjwt.entity.Progress;
import com.esprit.springjwt.repository.HackerspacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HackerspacesService {
    @Autowired
    private HackerspacesRepository hackerspacesRepository;
    public Hackerspaces addHackerspaces(Hackerspaces hackerspaces){
        return hackerspacesRepository.save(hackerspaces);

    }
    public List<Hackerspaces> getAllHackerspaces(){return hackerspacesRepository.findAll(); }
    public Hackerspaces updateHackerspaces(Hackerspaces hackerspaces){
        return hackerspacesRepository.save(hackerspaces);
    }
    public Hackerspaces getHackerspacesById(Long id){
        return hackerspacesRepository.findById(id).get();
    }
    public void deleteHackerspaces(Long id){
        hackerspacesRepository.deleteById(id);
    }

}
