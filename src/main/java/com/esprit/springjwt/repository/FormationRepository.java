package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormationRepository extends JpaRepository<Formation,Long> {


    List<Formation> findByCategorieId(Long id);
}
