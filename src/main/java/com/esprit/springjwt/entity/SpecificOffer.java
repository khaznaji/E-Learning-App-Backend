package com.esprit.springjwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpecificOffer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String poste ;
    private String skills ;
        private String description ;
    private String experience ;
    private String type ;
    private String education ;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-ss-mm")
    private LocalDateTime date = LocalDateTime.now();
    @Value("#{false}")
    private boolean status;
    private String image;
    private  String nom ;
    private  String descriptionC ;
    private  String adresse ;
    private  String email ;
    private  int numtel ;


}
