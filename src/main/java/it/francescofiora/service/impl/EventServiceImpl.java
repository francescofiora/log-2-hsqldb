package it.francescofiora.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.francescofiora.model.EventLog;
import it.francescofiora.repository.EventRepository;
import it.francescofiora.service.EventService;


/**
 * EventService Implementation.
 * @author francesco
 *
 */
@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Optional<EventLog> findById(final String id) {
		return repository.findById(id);
	}

	  @Override
	  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	  public void save(final EventLog record) {
	    repository.save(record);
	  }

}
