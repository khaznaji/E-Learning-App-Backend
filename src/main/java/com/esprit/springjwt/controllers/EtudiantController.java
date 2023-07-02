package com.esprit.springjwt.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.springjwt.entity.ERole;
import com.esprit.springjwt.entity.Etudiant;
import com.esprit.springjwt.entity.Formateur;
import com.esprit.springjwt.entity.Role;
import com.esprit.springjwt.entity.User;
import com.esprit.springjwt.payload.response.MessageResponse;
import com.esprit.springjwt.repository.EtudiantRepository;
import com.esprit.springjwt.repository.RoleRepository;
import com.esprit.springjwt.repository.UserRepository;
import com.esprit.springjwt.security.jwt.JwtUtils;
import com.esprit.springjwt.service.EmailServiceImpl;
import com.esprit.springjwt.service.EtudiantService;
import com.esprit.springjwt.service.userService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/etudiant")
public class EtudiantController {
	 	@Autowired
	    private userService userService;
	    @Autowired
	    EtudiantService etudiantService;
	    @Autowired
	    UserRepository userRepository;
	    @Autowired
	    EmailServiceImpl emailService;
	    @Autowired
	    AuthenticationManager authenticationManager;
	    @Autowired
	    EtudiantRepository etudantRepository;
	    @Autowired
	    RoleRepository roleRepository;
	    @Autowired
	    PasswordEncoder encoder;
	    @Autowired
	    JwtUtils jwtUtils;

	    @GetMapping("/all")
	    public List<Etudiant> getAll()
	    {
	        return etudiantService.getAllEtudiants();
	    }
	    @PostMapping("/addEtudiant")
    public ResponseEntity<?> registerUserStudent(@RequestParam("username") String username,
                                               @RequestParam("password") String password,
                                               @RequestParam("firstName") String firstName,
                                               @RequestParam("lastName") String lastName,
                                               @RequestParam("numeroTel") String numeroTel
                                               //@RequestParam("CV") MultipartFile files,
                                               //@RequestParam("typeFormation") String typeFormation,
                                               //@RequestParam("Github") String GithubLink,
                                               //@RequestParam("country") String country,
                                                  //@RequestParam("skills") String skills,
                                              /* @RequestParam("Linkedin") String LinkedinLink*/) throws IOException {

        String msj = "Bonjour " + firstName + " " + lastName + " votre compte a été crée avec succés";
        String subject = "Bienvenue sur 9antraTraining";

        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        // Create new user's account
        String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        //String filesName = currentDate + files.getOriginalFilename();
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNumeroTel(numeroTel);
        //user.setTypeFormation(typeFormation);
        user.setImage("profile-img.jpg");

        /*byte[] bytes1 = files.getBytes();
        Path path1 = Paths.get(UPLOAD_DOCUMENTS + filesName);
        Files.write(path1, bytes1);*/

        Set<Role> roles = new HashSet<>();
        Optional<Role> roleOptional = roleRepository.findByName(ERole.ETUDIANT);
        if (roleOptional.isPresent()) {
            roles.add(roleOptional.get());
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Role not found!"));
        }
        user.setRoles(roles);
        //user.setCountry(country);
        userRepository.save(user);

        Etudiant etudiant = new Etudiant();
        //formateur.setCV(filesName);
        //formateur.setGithub(GithubLink);
        //formateur.setLinkedin(LinkedinLink);
        //formateur.setSkills(skills);
        etudiant.setStatus(Etudiant.ProductStatus.UNPAID);
        etudiant.setUser(user);
        etudantRepository.save(etudiant);
        emailService.sendSimpleMail(username, subject, msj);
        return ResponseEntity.ok(new MessageResponse("Formateur registered successfully!"));
    }

}
