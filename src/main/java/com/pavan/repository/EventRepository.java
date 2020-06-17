package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Events;

@Repository
public interface EventRepository extends JpaRepository<Events, Long> {

	List<Events> findByEventType(String eventType);

	@Query(value = "from Events e order by month(eventDate) asc,date(eventDate) asc")
	List<Events> getEventsOrderByDateAsc();

	Events findByEventName(String eventName);

}
