package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.SpecificOffer;
import com.esprit.springjwt.entity.company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecificOfferRepository extends JpaRepository<SpecificOffer,Long> {
    List<SpecificOffer> findAllByOrderByDateAsc(); // for ascending order

    List<SpecificOffer> findAllByOrderByDateDesc(); // for descending order

}
