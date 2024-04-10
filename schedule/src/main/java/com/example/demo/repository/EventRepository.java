package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Event;
import com.example.demo.usecase.EventCount;

public interface EventRepository extends JpaRepository<Event, Integer>{
	@Query(value = " SELECT "
			+ " DATE_FORMAT(event_date,'%Y%m%d') as eventDate, "
			+ " COUNT(id) as eventCount"
			+ " FROM "
			+ " events"
			+ " WHERE "
			+ " event_date > :satartDate"
			+ " and event_date < :endDate"
			+ " and is_delete = 0 "
			+ " GROUP BY event_date; ", nativeQuery = true)
	public List<EventCount> findMonthlyEvent(String satartDate, @Param("endDate") String endDate);
	
	public List<Event> findByEventDateAndIsDeleteNotOrderByUserId(String eventDate, Integer isDelete);
}
