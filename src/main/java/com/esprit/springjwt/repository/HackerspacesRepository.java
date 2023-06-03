package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.Hackerspaces;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HackerspacesRepository extends JpaRepository<Hackerspaces,Long> {
}
