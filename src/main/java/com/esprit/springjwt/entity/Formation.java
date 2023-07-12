package com.esprit.springjwt.entity;

<<<<<<< HEAD
=======
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
>>>>>>> wale

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<< HEAD
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


=======
>>>>>>> wale
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
<<<<<<< HEAD
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getNomFormation() {
		return nomFormation;
	}
	public void setNomFormation(String nomFormation) {
		this.nomFormation = nomFormation;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public List<Chapters> getChapters() {
		return chapters;
	}
	public void setChapters(List<Chapters> chapters) {
		this.chapters = chapters;
	}
	public int getNbChapters() {
		return nbChapters;
	}
	public void setNbChapters(int nbChapters) {
		this.nbChapters = nbChapters;
	}
	public int getNbProjects() {
		return nbProjects;
	}
	public void setNbProjects(int nbProjects) {
		this.nbProjects = nbProjects;
	}
	public int getNbExercices() {
		return nbExercices;
	}
	public void setNbExercices(int nbExercices) {
		this.nbExercices = nbExercices;
	}
	public int getNbMeetings() {
		return nbMeetings;
	}
	public void setNbMeetings(int nbMeetings) {
		this.nbMeetings = nbMeetings;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
=======
	
>>>>>>> wale
    
}
