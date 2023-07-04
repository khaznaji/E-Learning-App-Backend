package com.esprit.springjwt.service;

import com.esprit.springjwt.entity.Projects;
import com.esprit.springjwt.entity.User;
import com.esprit.springjwt.repository.UserRepository;
import com.esprit.springjwt.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public static String UPLOAD_DOCUMENTS = "C:\\Users\\Wale\\Desktop\\Final Design\\bridge\\src\\assets\\Documents\\";

    //get All Users

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User changeEnabledUser(Long id) {
        User user = userRepository.findById(id).get();
        if (user.getEnabled()) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
      return  userRepository.save(user);
    }


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

    public User updateImage(User currentUser, MultipartFile file) {
        try {
            // Vérifier si le fichier est de type ZIP, RAR, docx, jpg, jpeg, png, ppt ou pdf

            // Utiliser le nom du fichier comme base pour le nom d'image
            String fileName = file.getOriginalFilename();

            // Ajouter un timestamp au nom du fichier
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String timestamp = now.format(formatter);
            String timestampedFileName = timestamp + "_" + fileName;

            // Créer le nom du dossier en utilisant le nom et l'ID de l'utilisateur
            String userFolderName = currentUser.getLastName() + "_" + currentUser.getId();

            // Définir le chemin du dossier utilisateur
            String userFolderPath = "C:\\Users\\Wale\\Desktop\\Final Design\\bridge\\src\\assets\\profile\\" + userFolderName;

            // Créer le dossier utilisateur
            File userFolder = new File(userFolderPath);
            if (!userFolder.exists()) {
                boolean created = userFolder.mkdirs();
                if (!created) {
                    throw new RuntimeException("Failed to create user folder");
                }
            }
            //get user by id


            // Définir le chemin complet de la nouvelle image dans le dossier utilisateur
            String imagePath = userFolderPath + "\\" + timestampedFileName;
            Path destinationPath = Paths.get(imagePath);

            // Copier le fichier de l'image dans le dossier utilisateur
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // Mettre à jour le chemin de l'image de l'utilisateur
            currentUser.setImage(userFolderName + "/" + timestampedFileName);

            // Enregistrer les modifications de l'utilisateur
            User updatedUser = userRepository.save(currentUser);

            return updatedUser;
        } catch (IOException e) {
            // Gérer les erreurs lors de la lecture du fichier
            e.printStackTrace();
            throw new RuntimeException("Error processing file");
        }
    }}
