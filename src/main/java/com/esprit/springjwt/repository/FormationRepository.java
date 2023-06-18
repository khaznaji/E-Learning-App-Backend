package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FormationRepository extends JpaRepository<Formation,Long> {


    List<Formation> findByCategorieId(Long id);

    @Query("SELECT f FROM Formation f WHERE f.nomFormation = ?1")
    Formation findByNomFormation(String nomFormation);
}
