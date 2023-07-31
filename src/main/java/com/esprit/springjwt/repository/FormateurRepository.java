package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormateurRepository extends JpaRepository<Formateur, Long> {
}
