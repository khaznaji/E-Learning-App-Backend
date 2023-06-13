package com.esprit.springjwt.service;



import com.esprit.springjwt.entity.Projects;
import com.esprit.springjwt.repository.ProjectsRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
   /* public Projects addProjects(Projects projects){ return projectsRepository.save(projects);
    }*/



    public List<Projects> getAllProjects(){return projectsRepository.findAll();
    }

    public Projects updateProjectWithFile(Long projectId, MultipartFile file) {
        // Vérifier si le projet existe dans la base de données
        Projects existingProject = projectsRepository.findById(projectId).orElse(null);
        if (existingProject != null) {
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

                    // Mettre à jour les propriétés du projet
                    existingProject.setProjectname(file.getOriginalFilename());

                    // Définir le type du projet en fonction de l'extension du fichier
                    String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
                    if (fileExtension.equals("zip")) {
                        existingProject.setType("ZIP");
                    } else if (fileExtension.equals("rar")) {
                        existingProject.setType("RAR");
                    } else if (fileExtension.equals("docx")) {
                        existingProject.setType("DOCX");
                    } else if (fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("png")) {
                        existingProject.setType("IMAGE");
                    } else if (fileExtension.equals("ppt")) {
                        existingProject.setType("PPT");
                    } else if (fileExtension.equals("pdf")) {
                        existingProject.setType("PDF");
                    }

                    // Définir la date de mise à jour du projet comme la date actuelle
                    existingProject.setDate(new Date());

                    // Enregistrer les modifications du projet
                    return projectsRepository.save(existingProject);
                } catch (IOException e) {
                    // Gérer les erreurs lors de la lecture du fichier
                    e.printStackTrace();
                }
            } else {
                // Gérer les erreurs si le fichier n'est pas autorisé
            }
        } else {
            // Gérer les erreurs si le projet n'existe pas
        }

        return null;
    }

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

    public Projects getProjectsById(Long id){return projectsRepository.findById(id).get();
    }
    public void deleteProjects(Long id) {
        projectsRepository.deleteById(id);
    }
}


