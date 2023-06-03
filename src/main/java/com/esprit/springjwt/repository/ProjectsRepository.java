package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectsRepository extends JpaRepository<Projects,Long> {
}
