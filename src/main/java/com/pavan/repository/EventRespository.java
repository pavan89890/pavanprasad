package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Events;

@Repository
public interface EventRespository extends JpaRepository<Events, Long> {

	public List<Events> findByEventType(String eventType);

	@Query(value = "from Events e order by eventDate desc")
	List<Events> getEventsOrderByDateDesc();

}
