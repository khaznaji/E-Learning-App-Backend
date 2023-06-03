package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<Groups,Long> {
}
