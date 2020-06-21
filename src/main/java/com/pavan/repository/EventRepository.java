package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Events;
import com.pavan.modal.User;

@Repository
public interface EventRepository extends JpaRepository<Events, Long> {

	@Query(value = "from Events e where e.user=:user order by month(eventDate) asc,date(eventDate) asc")
	public List<Events> getUserEventsOrderByDateAsc(@Param("user") User user);

	@Query(value = "from Events e where e.user=:user and e.eventType=:eventType order by month(eventDate) asc,date(eventDate) asc")
	public List<Events> getUserEventsByTypeOrderByDateAsc(@Param("user") User user,
			@Param("eventType") String eventType);

	public Events findByUserAndEventName(User currentUser, String eventName);

	@Modifying
	@Query("delete from Events e where e.user=:user")
	public void deleteByUser(@Param("user") User user);

}
