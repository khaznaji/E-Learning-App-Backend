package com.esprit.springjwt.service;

import com.esprit.springjwt.entity.Formateur;
import com.esprit.springjwt.repository.FormateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormateurService {


    @Autowired
    private FormateurRepository formateurRepository;

    public Formateur addFormateur(Formateur formateur){
       return formateurRepository.save(formateur);
    }

    public Formateur updateFormateur(Formateur formateur){
        return formateurRepository.save(formateur);
    }

    public void deleteFormateur(Long id){
        formateurRepository.deleteById(id);
    }

    public Formateur getFormateur(Long id){
        return formateurRepository.findById(id).get();
    }

    public List<Formateur> getAllFormateurs(){
        return formateurRepository.findAll();
    }
}
