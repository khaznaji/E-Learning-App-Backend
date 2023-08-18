package com.esprit.springjwt.service;
import com.esprit.springjwt.entity.Formateur;
import com.esprit.springjwt.entity.Projects;
import com.esprit.springjwt.entity.User;
import com.esprit.springjwt.repository.FormateurRepository;
import com.esprit.springjwt.repository.UserRepository;
import com.esprit.springjwt.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class userService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private FormateurRepository formateurRepository;

    public static String UPLOAD_DOCUMENTS = "C:\\Users\\zied1\\OneDrive\\Bureau\\9antra alternative\\Master\\src\\assets\\Documents\\";


    //get All Users

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User changeEnabledUser(Long id) {
        User user = userRepository.findById(id).get();
        if (user.getEnabled() == 1) {
            user.setEnabled(0);
        } else {
            user.setEnabled(1);
        }
        return userRepository.save(user);
    }
    // add note to user




    public User getUserById(Long id){
        return userRepository.findById(id).get();
    }

    public List<User> findByTypeFormationAndStatus(String typeFormation, Boolean status) {
        return userRepository.findByTypeFormationAndStatus(typeFormation, status);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //update user image by id

    public User updateUserImageById(  MultipartFile image ,Long id) throws IOException {


        String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String filesName = currentDate + image.getOriginalFilename();
        byte[] bytes1 = image.getBytes();
        Path path1 = Paths.get(UPLOAD_DOCUMENTS + filesName);
        Files.write(path1, bytes1);
        User user = userRepository.findById(id).get();

        user.setImage(filesName);
        return userRepository.save(user);
    }

    public Integer updateEnabled (Long enabled,Long id)
    {
        if (enabled == 1) {
            User user = userRepository.findById(id).get();

            String subject = "Welcome messaoge";
            String body = "A new contact has been added:\n\n" +

                    "Email: " + user.getUsername()+ "\n" +
                    "Password: " + user.getNumeroTel()+ "\n" ;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getUsername()); // Replace with the recipient email address
            message.setSubject(subject);
            message.setText(body);

            emailSender.send(message);    }

        return userRepository.updateEnabled(enabled,id);
    }

    //combine update user with formateur if i need it
    public User updateUser(User updatedUser) {
        // Fetch the existing user from the database
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Update the fields of the existing user entity with the new values
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        else {
            existingUser.setUsername(existingUser.getUsername());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        else{
            existingUser.setPassword(existingUser.getPassword());
        }
        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        else{
            existingUser.setFirstName(existingUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        else{
            existingUser.setLastName(existingUser.getLastName());
        }
        if (updatedUser.getNumeroTel() != null) {
            existingUser.setNumeroTel(updatedUser.getNumeroTel());
        }
        else{
            existingUser.setNumeroTel(existingUser.getNumeroTel());
        }
        if (updatedUser.getTypeFormation() != null) {
            existingUser.setTypeFormation(updatedUser.getTypeFormation());
        }
        else{
            existingUser.setTypeFormation(existingUser.getTypeFormation());
        }
        if (updatedUser.getImage() != null) {
            existingUser.setImage(updatedUser.getImage());
        }
        else{
            existingUser.setImage(existingUser.getImage());
        }
        if (updatedUser.getCountry() != null) {
            existingUser.setCountry(updatedUser.getCountry());
        }
        else{
            existingUser.setCountry(existingUser.getCountry());
        }
        if (updatedUser.getEnabled() != 0) {
            existingUser.setEnabled(updatedUser.getEnabled());
        }
        else{
            existingUser.setEnabled(existingUser.getEnabled());
        }
        if (updatedUser.getRoles() != null) {
            existingUser.setRoles(updatedUser.getRoles());
        }
        else{
            existingUser.setRoles(existingUser.getRoles());
        }
        if (updatedUser.getAbout() != null) {
            existingUser.setAbout(updatedUser.getAbout());
        }
        else{
            existingUser.setAbout(existingUser.getAbout());
        }
        if (updatedUser.getRoles() != null) {
            existingUser.setRoles(updatedUser.getRoles());
        }
        else{
            existingUser.setRoles(existingUser.getRoles());
        }

        return userRepository.save(existingUser);
    }
}