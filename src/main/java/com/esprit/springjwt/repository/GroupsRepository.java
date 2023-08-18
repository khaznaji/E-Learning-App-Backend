package com.esprit.springjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esprit.springjwt.entity.Groups;
import com.esprit.springjwt.entity.User;

public interface GroupsRepository extends JpaRepository<Groups,Long> {
	boolean existsByGroupNameIgnoreCase(String groupName);
	List<Groups> findByEtudiantsContaining(User user);
    @Query("SELECT g FROM Groups g WHERE g.formation.Id = :formationId")
    List<Groups> findByFormationId(@Param("formationId") Long formationId);
    List<Groups> findByFormateur(User formateur);

    List<Groups> findByFormateurId(Long formateurId);

    
}
