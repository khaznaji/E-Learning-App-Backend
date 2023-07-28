package com.esprit.springjwt.service;


import com.esprit.springjwt.entity.Hackerspaces;
import com.esprit.springjwt.entity.Progress;
import com.esprit.springjwt.repository.HackerspacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class HackerspacesService {
    @Autowired
    private HackerspacesRepository hackerspacesRepository;

    //add hackerspace with upload it photo
   // public static String UPLOAD_DOCUMENTS = "C:\\Users\\Wale\\Desktop\\Final Design\\bridge\\src\\assets\\Documents\\";
    public static String UPLOAD_DOCUMENTS = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\Documents\\";

    public Hackerspaces addHackerspaces(
            String Region,
            String Location,
            Integer Phone,
            String Email,
            String Description,
            String Adresse,
            MultipartFile photo
    ) throws IOException {
        String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String filesName = currentDate + photo.getOriginalFilename();

        byte[] bytes1 = filesName.getBytes();
        Path path1 = Paths.get(UPLOAD_DOCUMENTS + filesName);
        Files.write(path1, bytes1);
        String attributeName = Region.replaceAll("\\s+", "");
        ;

        Hackerspaces hackerspaces = new Hackerspaces();
        hackerspaces.setRegion(attributeName);
        hackerspaces.setLocation(Location);
        hackerspaces.setPhone(Phone);
        hackerspaces.setEmail(Email);
        hackerspaces.setAdresse(Adresse);
        hackerspaces.setDescription(Description);
        hackerspaces.setPhoto(filesName);
        return hackerspacesRepository.save(hackerspaces);

    }


    public List<Hackerspaces> getAllHackerspaces() {
        return hackerspacesRepository.findAll();
    }

    public Hackerspaces updateHackerspaces(Hackerspaces hackerspaces) {
        return hackerspacesRepository.save(hackerspaces);
    }

    public Hackerspaces getHackerspacesById(Long id) {
        return hackerspacesRepository.findById(id).get();
    }

    public void deleteHackerspaces(Long id) {
        hackerspacesRepository.deleteById(id);
    }

    public Hackerspaces getHackerspacesByRegion(String region) {
        return hackerspacesRepository.getHackerspacesByRegion(region);
    }

}
