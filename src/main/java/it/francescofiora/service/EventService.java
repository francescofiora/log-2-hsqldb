package it.francescofiora.service;

import it.francescofiora.model.EventLog;
import java.util.Optional;

/**
 * Event Service.
 */
public interface EventService {

  Optional<EventLog> findById(final String id);

  public void save(final EventLog record);
}
