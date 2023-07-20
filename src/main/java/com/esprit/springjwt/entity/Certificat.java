package com.esprit.springjwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Certificat")
public class Certificat {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idCertificat;

}
