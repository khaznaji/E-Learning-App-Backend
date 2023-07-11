package com.esprit.springjwt.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Companies implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  String nom ;
    private  String image ;
    private  String description ;
    private  String adresse ;
    private  String email ;
    private  int numtel ;
    @Value("#{false}")
    private boolean status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-ss-mm")
    private LocalDateTime date = LocalDateTime.now();

}
