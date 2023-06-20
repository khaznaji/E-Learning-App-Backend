package com.esprit.springjwt.controllers;

import com.esprit.springjwt.entity.Formateur;
import com.esprit.springjwt.entity.User;
import com.esprit.springjwt.repository.FormateurRepository;
import com.esprit.springjwt.repository.UserRepository;
import com.esprit.springjwt.security.services.UserDetailsImpl;
import com.esprit.springjwt.service.FormateurService;
import com.esprit.springjwt.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/formateur")
public class FormateurController {
    @Autowired
    private userService userService;
    @Autowired
    FormateurService formateurService;
    @Autowired
    UserRepository userRepository;


    @GetMapping("/all")
    public List<Formateur> getAll()
    {
        return formateurService.getAllFormateurs();
    }
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUserDetails() {
        User currentUser;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            currentUser = userDetails.getUser();
        } else {
            // Utiliser un utilisateur par défaut avec un nom d'utilisateur spécifique
            currentUser = userRepository.findByUsername("coach1@gmail.com"); // Remplacez "user1@gmail.com" par le nom d'utilisateur spécifique
        }

        return ResponseEntity.ok(currentUser);
    }
    @PostMapping("/addImage")
    public ResponseEntity<User> addImage(@RequestParam("file") MultipartFile file) {
        User currentUser;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            currentUser = userDetails.getUser();
            User updatedUser = userService.updateImage(currentUser, file);
            return ResponseEntity.ok(updatedUser);
        } else {
            // Utiliser un utilisateur par défaut avec un nom d'utilisateur spécifique
            User defaultUser = userRepository.findByUsername("coach1@gmail.com");
            if (defaultUser != null) {
                User updatedUser = userService.updateImage(defaultUser, file);
                return ResponseEntity.ok(updatedUser);
            } else {
                throw new IllegalArgumentException("Default user not found");
            }
        }
    }


}
