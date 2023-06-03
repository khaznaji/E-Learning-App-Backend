package com.esprit.springjwt.controllers;

import com.esprit.springjwt.entity.User;
import com.esprit.springjwt.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private userService userService;

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

    //getbyemail
    @GetMapping("/getbyemail/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    //update user image by id
    @PatchMapping("/updateUserImageById/{id}")
    public User updateUserImageById(@RequestParam("image") MultipartFile image , @PathVariable Long id) throws IOException {
        return userService.updateUserImageById( image,id);
    }
}
