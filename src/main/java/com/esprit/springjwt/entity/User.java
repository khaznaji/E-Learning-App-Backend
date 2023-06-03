package com.esprit.springjwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public  class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 80)
  @Column(unique = true)
  private String username;


  @NotBlank
  @Size(max = 120)
  private String password;

  @NotBlank
  @Size(max=50)
  private  String firstName;
  @NotBlank
  @Size(max=50)
  private  String lastName;

  @NotBlank
  @Digits(integer=8, fraction=0, message="Please enter a valid number")

  private  String numeroTel;

  @NotBlank
  @Size(max=50)
  private  String typeFormation;


  @NotBlank
  private  String image;

  @NotBlank
  private String country;

  private Boolean enabled=false;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @JsonIgnore
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private Formateur formateur;


  public User() {
  }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

  public User(String username, String password, String firstName, String lastName, String numeroTel, String typeFormation, String image, String country, Boolean enabled, Set<Role> roles, Formateur formateur) {
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
  }

  public User(String username, String password, String firstName, String lastName, String numeroTel, String image, String country, Boolean enabled, Set<Role> roles, Formateur formateur) {
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

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
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
}
