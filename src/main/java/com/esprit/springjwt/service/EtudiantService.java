package com.esprit.springjwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esprit.springjwt.entity.Etudiant;
import com.esprit.springjwt.repository.EtudiantRepository;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    public Etudiant addEtudiant(Etudiant etudiant){
        return etudiantRepository.save(etudiant);
    }

    public Etudiant updateEtudiant(Etudiant etudiant){
        return etudiantRepository.save(etudiant);
    }

    public void deleteEtudiant(Long id){
        etudiantRepository.deleteById(id);
    }

    public Etudiant getEtudiant(Long id){
        return etudiantRepository.findById(id).get();
    }

    public List<Etudiant> getAllEtudiants(){
        return etudiantRepository.findAll();
    }
}
