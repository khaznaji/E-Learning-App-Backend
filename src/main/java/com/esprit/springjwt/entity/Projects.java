package com.esprit.springjwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Projects implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Projectname ;
    private  String Type ;
    @Column(nullable = false)
    private String size;

    private Date Date ;
    @ManyToOne
    @JsonIgnore
    public User user;
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
