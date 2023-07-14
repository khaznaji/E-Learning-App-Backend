package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.Candidacy;
import com.esprit.springjwt.entity.Offers;
import com.esprit.springjwt.entity.company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffersRepository extends JpaRepository<Offers,Long> {
    List<Offers> findByCompany(company projectOwner);
    List<Offers> findAllByOrderByDateAsc(); // for ascending order

    List<Offers> findAllByOrderByDateDesc(); // for descending order
}
