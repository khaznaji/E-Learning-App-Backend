package com.esprit.springjwt.service;



import com.esprit.springjwt.entity.Projects;
import com.esprit.springjwt.entity.User;
import com.esprit.springjwt.repository.ProjectsRepository;
import com.esprit.springjwt.repository.UserRepository;
import com.esprit.springjwt.security.services.UserDetailsImpl;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ProjectsService {
    @Autowired
    private ProjectsRepository projectsRepository;
    @Autowired
    private UserRepository userRepository;
   /* public Projects addProjects(Projects projects){ return projectsRepository.save(projects);
    }*/

    public List<Projects> getProjectsByUser(User user) {
        return projectsRepository.findByUser(user);
    }

    public List<Projects> getAllProjects() {
        return projectsRepository.findAll();
    }
    public List<Projects> getAllProjectsStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;

        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            username = userDetails.getUsername();
        } else {
            // Utiliser une valeur par défaut si aucun utilisateur n'est passé
            username = "fddd";
        }

        // Récupérer l'utilisateur à partir du username (à adapter selon votre implémentation)
        User user = userRepository.findByUsername(username);
        if (user == null) {
            // Gérer le cas où l'utilisateur n'existe pas
            throw new IllegalArgumentException("User not found");
        }

        return projectsRepository.findByUser(user);
    }

    public Projects addProject(MultipartFile file) {
        // Vérifier si le fichier est de type ZIP, RAR, docx, jpg, jpeg, png, ppt ou pdf
        String contentType = file.getContentType();
        if (contentType != null && (
                contentType.equals("application/zip") ||
                        contentType.equals("application/x-rar-compressed") ||
                        contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
                        contentType.equals("image/jpeg") ||
                        contentType.equals("image/jpg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("application/vnd.ms-powerpoint") ||
                        contentType.equals("application/pdf")
        )) {
            try {
                // Récupérer le contenu du fichier
                byte[] content = file.getBytes();

                String username;
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication.getPrincipal() instanceof UserDetailsImpl) {
                    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                    username = userDetails.getUsername();
                } else {
                    // Utiliser une valeur par défaut ou une chaîne vide si le principal n'est pas disponible
                    username = "fddd";
                }

                // Récupérer l'utilisateur à partir du username (à adapter selon votre implémentation)
                User user = userRepository.findByUsername(username);
                if (user == null) {
                    // Gérer le cas où l'utilisateur n'existe pas
                    throw new IllegalArgumentException("User not found");
                }

                // Créer un nouveau projet
                Projects project = new Projects();

                // Utiliser le nom du fichier comme nom de projet
                String fileName = file.getOriginalFilename();
                project.setProjectname(fileName);

                // Définir le type du projet en fonction de l'extension du fichier
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                if (fileExtension.equals("zip")) {
                    project.setType("ZIP");
                } else if (fileExtension.equals("rar")) {
                    project.setType("RAR");
                } else if (fileExtension.equals("docx")) {
                    project.setType("DOCX");
                } else if (fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("png")) {
                    project.setType("IMAGE");
                } else if (fileExtension.equals("ppt")) {
                    project.setType("PPT");
                } else if (fileExtension.equals("pdf")) {
                    project.setType("PDF");
                } else {
                    // Gérer le cas où l'extension n'est pas autorisée
                    throw new IllegalArgumentException("Invalid file extension");
                }

                // Définir la date du projet comme la date actuelle
                project.setDate(new Date());

                // Définir la taille du projet
                long fileSize = file.getSize();

                // Convertir la taille en un format lisible par l'homme
                String size;
                if (fileSize < 1024) {
                    size = fileSize + " B";
                } else if (fileSize < 1024 * 1024) {
                    size = fileSize / 1024 + " KB";
                } else {
                    size = fileSize / (1024 * 1024) + " MB";
                }
                project.setSize(size);

                // Définir le chemin du fichier
                String filePath = "C:\\Users\\DELL\\Desktop\\9antraFormation-Front\\9antraFormationFront\\src\\assets\\projects\\" + fileName; // Remplacez par votre chemin de fichier souhaité

                Path destinationPath = Paths.get(filePath);
                Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                // Assigner l'utilisateur au projet
                project.setUser(user);
                project.setNom(user.getLastName());
                project.setPrenom(user.getFirstName());
                project.setMail(user.getUsername());
                project.setTypeF(user.getTypeFormation());
                project.setImage(user.getImage());



                // Enregistrer le projet
                Projects savedProject = projectsRepository.save(project);

                return savedProject;
            } catch (IOException e) {
                // Gérer les erreurs lors de la lecture du fichier
                e.printStackTrace();
                throw new RuntimeException("Error processing file");
            }
        } else {
            // Gérer les erreurs si le fichier n'est pas autorisé
            throw new IllegalArgumentException("Invalid file type");
        }}
    public Projects updateProject(Long projectId, MultipartFile file) {
        // Vérifier si le projet existe
        Projects project = projectsRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        // Vérifier si le fichier est de type ZIP, RAR, docx, jpg, jpeg, png, ppt ou pdf
        String contentType = file.getContentType();
        if (contentType != null && (
                contentType.equals("application/zip") ||
                        contentType.equals("application/x-rar-compressed") ||
                        contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
                        contentType.equals("image/jpeg") ||
                        contentType.equals("image/jpg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("application/vnd.ms-powerpoint") ||
                        contentType.equals("application/pdf")
        )) {
            try {
                // Récupérer le contenu du fichier
                byte[] content = file.getBytes();

                // Utiliser le nom du fichier comme nom de projet
                String fileName = file.getOriginalFilename();
                project.setProjectname(fileName);

                // Définir le type du projet en fonction de l'extension du fichier
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                if (fileExtension.equals("zip")) {
                    project.setType("ZIP");
                } else if (fileExtension.equals("rar")) {
                    project.setType("RAR");
                } else if (fileExtension.equals("docx")) {
                    project.setType("DOCX");
                } else if (fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("png")) {
                    project.setType("IMAGE");
                } else if (fileExtension.equals("ppt")) {
                    project.setType("PPT");
                } else if (fileExtension.equals("pdf")) {
                    project.setType("PDF");
                } else {
                    // Gérer le cas où l'extension n'est pas autorisée
                    throw new IllegalArgumentException("Invalid file extension");
                }

                // Définir la date du projet comme la date actuelle
                project.setDate(new Date());

                // Définir la taille du projet
                long fileSize = file.getSize();

                // Convertir la taille en un format lisible par l'humain
                String size;
                if (fileSize < 1024) {
                    size = fileSize + " B";
                } else if (fileSize < 1024 * 1024) {
                    size = fileSize / 1024 + " KB";
                } else {
                    size = fileSize / (1024 * 1024) + " MB";
                }
                project.setSize(size);

                // Définir le chemin du fichier
                String filePath = "C:\\Users\\DELL\\Desktop\\9antraFormation-Front\\9antraFormationFront\\src\\assets\\projects\\" + fileName; // Remplacez par votre chemin de fichier souhaité

                Path destinationPath = Paths.get(filePath);
                Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                // Enregistrer les modifications du projet
                Projects updatedProject = projectsRepository.save(project);

                return updatedProject;
            } catch (IOException e) {
                // Gérer les erreurs lors de la lecture du fichier
                e.printStackTrace();
                throw new RuntimeException("Error processing file");
            }
        } else {
            // Gérer les erreurs si le fichier n'est pas autorisé
            throw new IllegalArgumentException("Invalid file type");
        }
    }


   /* public Projects addProjects(MultipartFile file) {
        // Vérifier si le fichier est de type ZIP, RAR, docx, jpg, jpeg, png, ppt ou pdf
        String contentType = file.getContentType();
        if (contentType != null && (
                contentType.equals("application/zip") ||
                        contentType.equals("application/x-rar-compressed") ||
                        contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
                        contentType.equals("image/jpeg") ||
                        contentType.equals("image/jpg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("application/vnd.ms-powerpoint") ||
                        contentType.equals("application/pdf")
        )) {
            try {
                // Récupérer le contenu du fichier
                byte[] content = file.getBytes();
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                String username = userDetails.getUsername();

// Récupérer l'utilisateur à partir du username (à adapter selon votre implémentation)
                User user = userRepository.findByUsername(username);
                // Créer un nouveau projet
                Projects project = new Projects();

                // Utiliser le nom du fichier comme nom de projet
                String fileName = file.getOriginalFilename();
                project.setProjectname(fileName);

                // Définir le type du projet en fonction de l'extension du fichier
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                if (fileExtension.equals("zip")) {
                    project.setType("ZIP");
                } else if (fileExtension.equals("rar")) {
                    project.setType("RAR");
                } else if (fileExtension.equals("docx")) {
                    project.setType("DOCX");
                } else if (fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("png")) {
                    project.setType("IMAGE");
                } else if (fileExtension.equals("ppt")) {
                    project.setType("PPT");
                } else if (fileExtension.equals("pdf")) {
                    project.setType("PDF");
                }

                // Définir la date du projet comme la date actuelle
                project.setDate(new Date());

                // Définir la taille du projet
                long fileSize = file.getSize();

                // Convertir la taille en un format lisible par l'homme
                String size;
                if (fileSize < 1024) {
                    size = fileSize + " B";
                } else if (fileSize < 1024 * 1024) {
                    size = fileSize / 1024 + " KB";
                } else {
                    size = fileSize / (1024 * 1024) + " MB";
                }
                project.setSize(size);
                String filePath = "C:\\Users\\DELL\\Desktop\\9antraFormation-Front\\9antraFormationFront\\src\\assets\\projects\\" + fileName; // Replace with your desired file path

                Path destinationPath = Paths.get(filePath);
                Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                if (user != null) {
                    // Assigner l'utilisateur au projet
                    project.setUser(user);

                    // Enregistrer le projet
                    Projects savedProject = projectsRepository.save(project);
                }
            } catch (IOException e) {
                // Gérer les erreurs lors de la lecture du fichier
                e.printStackTrace();
            }
        } else {
            // Gérer les erreurs si le fichier n'est pas autorisé
        }

        return null;
    }*/

    /*
    public Projects addProjects(MultipartFile file) {
        // Vérifier si le fichier est de type ZIP, RAR, docx, jpg, jpeg, png, ppt ou pdf
        String contentType = file.getContentType();
        if (contentType != null && (
                contentType.equals("application/zip") ||
                        contentType.equals("application/x-rar-compressed") ||
                        contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
                        contentType.equals("image/jpeg") ||
                        contentType.equals("image/jpg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("application/vnd.ms-powerpoint") ||
                        contentType.equals("application/pdf")
        )) {
            try {
                // Récupérer le contenu du fichier
                byte[] content = file.getBytes();

                // Créer un nouveau projet
                Projects project = new Projects();

                // Utiliser le nom du fichier comme nom de projet
                String fileName = file.getOriginalFilename();
                project.setProjectname(fileName);

                // Définir le type du projet en fonction de l'extension du fichier
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                if (fileExtension.equals("zip")) {
                    project.setType("ZIP");
                } else if (fileExtension.equals("rar")) {
                    project.setType("RAR");
                } else if (fileExtension.equals("docx")) {
                    project.setType("DOCX");
                } else if (fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("png")) {
                    project.setType("IMAGE");
                } else if (fileExtension.equals("ppt")) {
                    project.setType("PPT");
                } else if (fileExtension.equals("pdf")) {
                    project.setType("PDF");
                }

                // Définir la date du projet comme la date actuelle
                project.setDate(new Date());

                // Définir la taille du projet
                long fileSize = file.getSize();

                // Convertir la taille en un format lisible par l'homme
                String size;
                if (fileSize < 1024) {
                    size = fileSize + " B";
                } else if (fileSize < 1024 * 1024) {
                    size = fileSize / 1024 + " KB";
                } else {
                    size = fileSize / (1024 * 1024) + " MB";
                }
                project.setSize(size);
                String filePath = "C:\\Users\\DELL\\Desktop\\9antraFormation-Front\\9antraFormationFront\\src\\assets\\projects\\" + fileName; // Replace with your desired file path

                Path destinationPath = Paths.get(filePath);
                Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                // Enregistrer le projet
                return projectsRepository.save(project);
            } catch (IOException e) {
                // Gérer les erreurs lors de la lecture du fichier
                e.printStackTrace();
            }
        } else {
            // Gérer les erreurs si le fichier n'est pas autorisé
        }

        return null;
    }
*/
    public Projects getProjectsById(Long id){return projectsRepository.findById(id).get();
    }
    public void deleteProjects(Long id) {
        projectsRepository.deleteById(id);
    }

}


