package com.esprit.springjwt.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Feedback implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String formation ;

    private String Title ;
    private String Comment;

    @ManyToOne
    private User user;

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    @Temporal(TemporalType.DATE)
    private Date Date ;

    public Feedback() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public Feedback(Long id) {
        this.id = id;
    }

    public Feedback(String title, String comment, java.util.Date date) {
        Title = title;
        Comment = comment;
        Date = date;
    }

    public Feedback(Long id, String title, String comment, java.util.Date date) {
        this.id = id;
        Title = title;
        Comment = comment;
        Date = date;

    }

    public void setSession(com.esprit.springjwt.entity.Session sessionById) {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

