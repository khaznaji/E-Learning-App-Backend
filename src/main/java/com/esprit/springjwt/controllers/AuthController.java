package com.esprit.springjwt.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import com.esprit.springjwt.entity.*;
import com.esprit.springjwt.repository.FormateurRepository;
import com.esprit.springjwt.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.esprit.springjwt.payload.request.LoginRequest;
import com.esprit.springjwt.payload.request.SignupRequest;
import com.esprit.springjwt.payload.response.JwtResponse;
import com.esprit.springjwt.payload.response.MessageResponse;
import com.esprit.springjwt.repository.RoleRepository;
import com.esprit.springjwt.repository.UserRepository;
import com.esprit.springjwt.security.jwt.JwtUtils;
import com.esprit.springjwt.security.services.UserDetailsImpl;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    EmailServiceImpl emailService;


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

@Autowired
FormateurRepository formateurRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        //get the id of the user

        if (userRepository.findUserByEnabled(loginRequest.getUsername())==0) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User Inactive!"));
        }


        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }


    public static String UPLOAD_DOCUMENTS = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\Documents\\";
   // public static String UPLOAD_DOCUMENTS = "C:\\Users\\Wale\\Desktop\\Final Design\\bridge\\src\\assets\\Documents\\";
   // public static String UPLOAD_DOCUMENTS = "C:\\Users\\zied1\\OneDrive\\Bureau\\L\\9antraFormationBack\\src\\main\\java\\com\\esprit\\springjwt\\files\\";


    @PostMapping("/signup")
    public ResponseEntity<?> registerUserCoach(@RequestParam("username") String username,
                                               @RequestParam("password") String password,
                                               @RequestParam("firstName") String firstName,
                                               @RequestParam("lastName") String lastName,
                                               @RequestParam("numeroTel") String numeroTel,
                                               @RequestParam("CV") MultipartFile files,
                                               @RequestParam("typeFormation") String typeFormation,
                                               @RequestParam("Github") String GithubLink,
                                               @RequestParam("country") String country,
                                                  @RequestParam("skills") String skills,
                                               @RequestParam("Linkedin") String LinkedinLink
                                           /*    @RequestParam("about") String about*/

                                               ) throws IOException {

        String msj = "Bonjour " + firstName + " " + lastName + " votre compte a été crée avec succés";
        String subject = "Bienvenue sur 9antraTraining";

        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        // Create new user's account
        String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String filesName = currentDate + files.getOriginalFilename();
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNumeroTel(numeroTel);
        user.setTypeFormation(typeFormation);
    /*    user.setAbout(about);*/

        user.setImage("avatarCoach.png");

        byte[] bytes1 = files.getBytes();
        Path path1 = Paths.get(UPLOAD_DOCUMENTS + filesName);
        Files.write(path1, bytes1);

        Set<Role> roles = new HashSet<>();
        Optional<Role> roleOptional = roleRepository.findByName(ERole.FORMATEUR);
        if (roleOptional.isPresent()) {
            roles.add(roleOptional.get());
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Role not found!"));
        }
        user.setRoles(roles);
        user.setCountry(country);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Formateur formateur = new Formateur();
        formateur.setCV(filesName);
        formateur.setGithub(GithubLink);
        formateur.setLinkedin(LinkedinLink);
        formateur.setSkills(skills);
        formateur.setUser(user);
        formateurRepository.save(formateur);
emailService.sendSimpleMail(username, subject, msj);
        return ResponseEntity.ok(new MessageResponse("Formateur registered successfully!"));
    }
    private static final String DEFAULT_IMAGE_PATH = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\img\\profile-img.jpg";
   // private static final String DEFAULT_IMAGE_PATH = "C:\\Users\\Wale\\Desktop\\Final Design\\bridge\\src\\assets\\Documents\\";




    @PostMapping("/signupstudent")
    public ResponseEntity<?> registerUserStudent(@RequestParam("username") String username,
                                               @RequestParam("password") String password,
                                               @RequestParam("firstName") String firstName,
                                               @RequestParam("lastName") String lastName,
                                               @RequestParam("numeroTel") String numeroTel,
                                               @RequestParam("typeFormation") String typeFormation,
                                                 @RequestParam("country") String country,
                                                 @RequestParam("roles") Set<String> strRoles,
                                                 @RequestParam("about") String about

                                             ) throws IOException {
        String msj = "Hi " + firstName + " " + lastName + " Welcome to The-Bridge " +
                "Thank you for your request for information regarding "+ typeFormation + "Training" +
                "Our team will contact you to finalize your registration to support you in your project, within 48 hours." +
                "In the meantime, and for any additional information, do not hesitate to contact our team of educational advisers on 20 000 000."

                ;






        String subject = "Bienvenue sur 9antraTraining";



        if (userRepository.existsByUsername(username)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: email is already taken!"));
        }


        String imagePath = DEFAULT_IMAGE_PATH;

        // Create new user's account

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNumeroTel(numeroTel);
        user.setTypeFormation(typeFormation);
        user.setAbout(about);
        user.setCountry(country);

        user.setImage("avatarStudent.png");




        Set<Role> roles = new HashSet<>();
        //set role of stroles
        strRoles.forEach(role -> {
            switch (role) {
                case "ADMINISTRATEUR":
                    Role adminRole = roleRepository.findByName(ERole.ADMINISTRATEUR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);

                    break;
                case "ETUDIANT":
                    Role etudeRole = roleRepository.findByName(ERole.ETUDIANT)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(etudeRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(ERole.ETUDIANT)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);

            }

        });



        user.setRoles(roles);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);



        emailService.sendSimpleMail(username, subject, msj);
        return ResponseEntity.ok(new MessageResponse("Formateur registered successfully!"));




    }



}
