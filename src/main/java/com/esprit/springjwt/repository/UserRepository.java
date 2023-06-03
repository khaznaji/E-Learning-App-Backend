package com.esprit.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.springjwt.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Boolean existsByUsername(String username);


    @Query(value = "SELECT * FROM user WHERE username = ?1 AND enabled = 1", nativeQuery = true)
    Integer findUserByEnabled(String username);

    //update password by email
    @Query(value = "UPDATE user SET password = ?1 WHERE email = ?2", nativeQuery = true)
    Integer updatePasswordByEmail(String password, String email);


    //Query requette get by typeFormation and status
    @Query(value = "SELECT * FROM user WHERE type_formation = ?1 AND enabled = ?2", nativeQuery = true)
    List<User> findByTypeFormationAndStatus(String typeFormation, Boolean status);

    //Query requette get by role
    @Query(value = "SELECT * FROM user WHERE role = ?1", nativeQuery = true)
    List<User> findByRoles(String role);

    //Query requette get by email
    @Query(value = "SELECT * FROM user WHERE email = ?1", nativeQuery = true)
    User findByEmail(String email);

}
