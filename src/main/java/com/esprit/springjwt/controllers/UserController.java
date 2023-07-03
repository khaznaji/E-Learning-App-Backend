package com.esprit.springjwt.controllers;

import com.esprit.springjwt.entity.Projects;
import com.esprit.springjwt.entity.User;
import com.esprit.springjwt.repository.UserRepository;
import com.esprit.springjwt.security.services.UserDetailsImpl;
import com.esprit.springjwt.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private userService userService;
    @Autowired
    private UserRepository userRepository;
    //get All Users
    @RequestMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/changeEnabledUser/{id}")
    public User changeEnabledUser(@PathVariable Long id) {
        return userService.changeEnabledUser(id);
    }

    @GetMapping("/finduserbyid/{id}")
    public User getUserByid(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @GetMapping("/findByTypeFormationAndStatus/{typeFormation}/{status}")
    public List<User> findByTypeFormationAndStatus(@PathVariable String typeFormation, @PathVariable Boolean status) {
        return userService.findByTypeFormationAndStatus(typeFormation, status);
    }

    @GetMapping("/getbyemail/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    //update user image by id
    @PatchMapping("/updateUserImageById/{id}")
    public User updateUserImageById(@RequestParam("image") MultipartFile image , @PathVariable Long id) throws IOException {
        return userService.updateUserImageById( image,id);
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
            currentUser = userRepository.findByUsername("user1@gmail.com"); // Remplacez "user1@gmail.com" par le nom d'utilisateur spécifique
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
            User defaultUser = userRepository.findByUsername("user1@gmail.com");
            if (defaultUser != null) {
                User updatedUser = userService.updateImage(defaultUser, file);
                return ResponseEntity.ok(updatedUser);
            } else {
                throw new IllegalArgumentException("Default user not found");
            }
        }
    }

}
