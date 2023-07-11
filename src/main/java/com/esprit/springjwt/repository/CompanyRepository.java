package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.ProjectOwner;
import com.esprit.springjwt.entity.company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<company,Long> {
    List<company> findByStatus(boolean status);

}
