package it.francescofiora.service.impl;

import it.francescofiora.model.EventLog;
import it.francescofiora.repository.EventRepository;
import it.francescofiora.service.EventService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EventService Implementation.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepository repository;

  @Override
  @Transactional(readOnly = true)
  public Optional<EventLog> findById(final String id) {
    return repository.findById(id);
  }

  @Override
  public void save(final EventLog record) {
    repository.save(record);
  }
}
