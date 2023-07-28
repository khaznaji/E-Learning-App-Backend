package com.esprit.springjwt.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.springjwt.entity.Session;

public interface SessionRepository extends JpaRepository<Session,Long> {
	List<Session> findByStartDate(Date date);
	List<Session> findByStartDateBetween(Date startDate, Date endDate);
	 List<Session> findByGroups_Id(Long groupId);
}
