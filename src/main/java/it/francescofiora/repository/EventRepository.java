package it.francescofiora.repository;

import it.francescofiora.model.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Event Repository.
 */
public interface EventRepository extends JpaRepository<EventLog, String> {

}
