package com.esprit.springjwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
