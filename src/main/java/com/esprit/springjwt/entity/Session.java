package com.esprit.springjwt.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Entity
@Data
public class Session implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String SessionName ;
    private String Description ;
    private Date startDate;
	@Column(unique = true)
	private String GeneratedLink;
    private Date finishDate;
    private String GroupSession;
    
    @ManyToMany
    @JoinTable(
        name = "session_group",
        joinColumns = @JoinColumn(name = "session_id"),
        inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @JsonIgnore
    private List<Groups> groups = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;
    public List<Groups> getGroups() {
        return groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSessionName() {
		return SessionName;
	}

	public void setSessionName(String sessionName) {
		SessionName = sessionName;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	public String getGroupSession() {
		return GroupSession;
	}

	public void setGroupSession(String groupSession) {
		GroupSession = groupSession;
	}




}