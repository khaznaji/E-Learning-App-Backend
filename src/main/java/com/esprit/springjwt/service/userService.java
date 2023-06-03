package com.esprit.springjwt.service;

import com.esprit.springjwt.entity.User;
import com.esprit.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
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

}
