package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FormationRepository extends JpaRepository<Formation,Long> {



    @Query("SELECT f FROM Formation f WHERE f.nomFormation = ?1")
    Formation findByNomFormation(String nomFormation);

    List<Formation> findByNomFormationContains(String nomFormation);

    //get formation by categorie id

    @Query("SELECT f FROM Formation f WHERE f.categorie.id = ?1")
    List<Formation> findByCategorieId(Long id);
}
