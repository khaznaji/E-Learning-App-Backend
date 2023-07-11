package com.esprit.springjwt.service;

import com.esprit.springjwt.entity.ProjectOwner;
import com.esprit.springjwt.entity.company;
import com.esprit.springjwt.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    public company save(company food) {
        return companyRepository.save(food);
    }
    public List<company> getAll() {
        return companyRepository.findAll();
    }
    public Optional<company > findById(Long id) {
        return companyRepository.findById(id);
    }

    public void     delete(Long id) {
        company project = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        String fileName = project.getImage();

        // Supprimer le fichier du dossier utilisateur
        String userFolderPath = "C:\\Users\\DELL\\Desktop\\The Bridge Front\\9antraFormationFrant\\src\\assets\\Company\\" ;
        String filePath = userFolderPath + "\\" + fileName;
        Path projectPath = Paths.get(filePath);

        try {
            Files.deleteIfExists(projectPath);
        } catch (IOException e) {
            // Gérer les erreurs lors de la suppression du fichier
            e.printStackTrace();
            throw new RuntimeException("Error deleting file");
        }

        // Supprimer l'entrée de la base de données
        companyRepository.deleteById(id);
    }
    public List<company> getClaimsByStatus(Boolean status) {
        return companyRepository.findByStatus(status);
    }
    public void updateStatus(Long id, boolean newValue) {
        // Recherche de l'objet MyClass correspondant à l'identifiant "id"
        company myObject = companyRepository.findById(id).orElse(null);

        // Vérification que l'objet a été trouvé
        if (myObject != null) {
            // Modification de la propriété "property2"
            myObject.setStatus(newValue);
            companyRepository.save(myObject);
        }

    }
}
