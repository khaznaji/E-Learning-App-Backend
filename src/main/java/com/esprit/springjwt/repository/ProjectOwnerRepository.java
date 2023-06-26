package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.ProjectOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectOwnerRepository extends JpaRepository<ProjectOwner,Long> {
}
