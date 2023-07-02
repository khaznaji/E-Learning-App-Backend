package com.esprit.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.springjwt.entity.Etudiant;


public interface EtudiantRepository extends JpaRepository<Etudiant, Long>{

}
