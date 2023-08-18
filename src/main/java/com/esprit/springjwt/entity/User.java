package com.esprit.springjwt.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"hibernateLazyInitializer"})

public  class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 80)
  @Column(unique = true)
  private String username;



  @Size(max = 120)
  private String password;

  @Size(max=50)
  private  String firstName;
  @Size(max=50)
  private  String lastName;
  @Size(min = 8, max = 40,message="Please enter a valid number")


  private  String numeroTel;

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Note> notes;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  private String about;
  @JsonIgnore
  @OneToMany
  private Set<Feedback> feedbacks;
  @Size(max=50)
  private  String typeFormation;



  private  String image;


  private String country;







  private int enabled=0;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @JsonIgnore
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private Formateur formateur;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @JsonIgnore

  private List<Projects> projets;

  @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
  @JsonIgnore
  public List<Certificat> certificats;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "etudiant_groups",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "groups_id")
  )
  @JsonIgnore
  private List<Groups> groups = new ArrayList<>();
  
  public List<Groups> getGroups() {
	return groups;
}
  public List<Certificat> getCertificats() {
    return certificats;
  }



public void setGroups(List<Groups> groups) {
	this.groups = groups;
}
public void setCertificats(List<Certificat> certificats) {
    this.certificats = certificats;
  }


public List<Projects> getProjets() {
    return projets;
  }
  



  public void setProjets(List<Projects> projets) {
    this.projets = projets;
  }
  public User() {
  }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

  public User(String username, String password, String firstName, String lastName, String numeroTel, String typeFormation, String image, String country, int enabled, Set<Role> roles, Formateur formateur,String about) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.numeroTel = numeroTel;
    this.typeFormation = typeFormation;
    this.image = image;
    this.country = country;
    this.enabled = enabled;
    this.roles = roles;
    this.formateur = formateur;
    this.about=about;

  }

  public User(String username, String password, String firstName, String lastName, String numeroTel, String image, String country, int enabled, Set<Role> roles, Formateur formateur) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.numeroTel = numeroTel;
    this.image = image;
    this.country = country;
    this.enabled = enabled;
    this.roles = roles;
    this.formateur = formateur;
  }

  public int getEnabled() {
    return enabled;
  }

  public void setEnabled(int enabled) {
    this.enabled = enabled;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getNumeroTel() {
    return numeroTel;
  }

  public void setNumeroTel(String numeroTel) {
    this.numeroTel = numeroTel;
  }

  public String getTypeFormation() {
    return typeFormation;
  }

  public void setTypeFormation(String typeFormation) {
    this.typeFormation = typeFormation;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Formateur getFormateur() {
    return formateur;
  }

  public void setFormateur(Formateur formateur) {
    this.formateur = formateur;
  }

    public String getAbout() {
    return about;
    }

    public void setAbout(String about) {
    this.about = about;
    }
  public void addNote(Note note) {
    notes.add(note);
    note.setUser(this);
  }

  public void removeNote(Note note) {
    notes.remove(note);
    note.setUser(null);
  }
}
