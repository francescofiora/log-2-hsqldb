package it.francescofiora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.francescofiora.model.EventLog;

/**
 * Event Repository.
 * @author francesco
 *
 */
public interface EventRepository extends JpaRepository<EventLog, String> {

}
