package com.esprit.springjwt.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Formation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String nomFormation;

    @ManyToOne
    @JoinColumn(name = "idCategorie")
    private Categorie categorie;
    @OneToMany(mappedBy = "formation")

    private List<Chapters> chapters;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private int nbChapters;
    private int nbProjects;
    private int nbExercices;
    private int nbMeetings;
    private String Description;
	
    
}
