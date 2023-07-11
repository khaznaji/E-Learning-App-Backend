package com.esprit.springjwt.repository;

import com.esprit.springjwt.entity.Offers;
import com.esprit.springjwt.entity.company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffersRepository extends JpaRepository<Offers,Long> {
}
