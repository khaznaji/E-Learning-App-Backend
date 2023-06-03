package com.esprit.springjwt.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.List;

@Entity
@Data
public class Session implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @OneToMany(mappedBy = "Session")
    private List<Feedback> Feedback;

    private String SessionName ;
    private String Description ;
    private String Date ;
private String StartTime;
    private String EndTime;
    private String GroupSession;



}
