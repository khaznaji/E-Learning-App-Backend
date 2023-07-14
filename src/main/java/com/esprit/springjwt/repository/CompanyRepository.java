package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<company,Long> {
    List<company> findByStatus(boolean status);
    List<company> findAllByOrderByDateAsc(); // for ascending order

    List<company> findAllByOrderByDateDesc(); // for descending order


}
