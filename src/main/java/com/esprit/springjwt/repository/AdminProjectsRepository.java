package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.AdminProjects;
import com.esprit.springjwt.entity.ProjectOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminProjectsRepository extends JpaRepository<AdminProjects,Long> {
    List<AdminProjects> findByProjectOwner(ProjectOwner projectOwner);

}
