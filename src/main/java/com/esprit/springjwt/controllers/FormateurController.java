package com.esprit.springjwt.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.esprit.springjwt.entity.ERole;
import com.esprit.springjwt.entity.Formateur;
import com.esprit.springjwt.entity.Role;
import com.esprit.springjwt.entity.User;
import com.esprit.springjwt.payload.request.LoginRequest;
import com.esprit.springjwt.payload.response.JwtResponse;
import com.esprit.springjwt.payload.response.MessageResponse;
import com.esprit.springjwt.repository.FormateurRepository;
import com.esprit.springjwt.repository.RoleRepository;
import com.esprit.springjwt.repository.UserRepository;
import com.esprit.springjwt.security.jwt.JwtUtils;
import com.esprit.springjwt.security.services.UserDetailsImpl;
import com.esprit.springjwt.service.EmailServiceImpl;
import com.esprit.springjwt.service.FormateurService;
import com.esprit.springjwt.service.userService;

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
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    FormateurRepository formateurRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;


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


    public static String UPLOAD_DOCUMENTS = "C:\\Users\\zied1\\OneDrive\\Bureau\\9antra\\9antraFormationBack\\src\\main\\java\\com\\esprit\\springjwt\\files\\documents\\";

    @PostMapping("/addFormateur")
    public ResponseEntity<?> registerUserCoach(@RequestParam("username") String username,
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
        Optional<Role> roleOptional = roleRepository.findByName(ERole.FORMATEUR);
        if (roleOptional.isPresent()) {
            roles.add(roleOptional.get());
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Role not found!"));
        }
        user.setRoles(roles);
        //user.setCountry(country);
        userRepository.save(user);

        Formateur formateur = new Formateur();
        //formateur.setCV(filesName);
        //formateur.setGithub(GithubLink);
        //formateur.setLinkedin(LinkedinLink);
        //formateur.setSkills(skills);
        formateur.setUser(user);
        formateurRepository.save(formateur);
        emailService.sendSimpleMail(username, subject, msj);
        return ResponseEntity.ok(new MessageResponse("Formateur registered successfully!"));
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
