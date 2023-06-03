package com.esprit.springjwt.service;



import com.esprit.springjwt.entity.Projects;
import com.esprit.springjwt.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectsService {
    @Autowired
    private ProjectsRepository projectsRepository;
    public Projects addProjects(Projects projects){ return projectsRepository.save(projects);
    }

        public List<Projects> getAllProjects(){return projectsRepository.findAll();
    }

    public Projects updateProjects(Projects projects){return projectsRepository.save(projects);
    }
    public Projects getProjectsById(Long id){return projectsRepository.findById(id).get();
    }
    public void deleteProjects(Long id) {
        projectsRepository.deleteById(id);
    }
}


