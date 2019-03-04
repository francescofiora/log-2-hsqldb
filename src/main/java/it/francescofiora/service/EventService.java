package it.francescofiora.service;

import java.util.Optional;

import it.francescofiora.model.EventLog;

/**
 * Event Service.
 * @author francesco
 *
 */
public interface EventService {

	Optional<EventLog> findById(final String id);
	public void save(final EventLog record);
}
