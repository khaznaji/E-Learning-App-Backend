package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.ProjectClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectClientRepository extends JpaRepository<ProjectClient, Long> {
    List<ProjectClient> findByAdminProjectsId(Long adminProjectId);

}
