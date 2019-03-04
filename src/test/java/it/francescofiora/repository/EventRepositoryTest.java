package it.francescofiora.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import it.francescofiora.model.EventLog;

/**
 * Test EventRepository.
 * @author francesco
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(value = { 
		"spring.batch.job.enabled=false",
		"spring.datasource.url=jdbc:hsqldb:mem:testdb;sql.enforce_strict_size=true"})
@Transactional
public class EventRepositoryTest {

	@Autowired
	private EventRepository repository;

	private static final String ID_EVENT = "test";
	private static final String EVENT_HOST = "host";
	private static final String EVENT_TYPE = "type";
	private static final Long EVENT_START = 10L;
	private static final Long EVENT_END = 15L;
	private static final Long EVENT_DURATION = EVENT_END - EVENT_START;
	
	/**
	 * test Create/Read/Update/Delete.
	 */
	@Test
	@Rollback
	public void testCrud() {
		EventLog event = new EventLog();
		event.setId(ID_EVENT);
		event.setEtype(EVENT_TYPE);
		event.setHost(EVENT_HOST);
		event.setEstart(EVENT_START);
		repository.save(event);
		Optional<EventLog> opt = repository.findById(ID_EVENT);
		Assert.assertTrue(opt.isPresent());
		event = opt.get();
		Assert.assertEquals(EVENT_HOST, event.getHost());
		Assert.assertEquals(EVENT_TYPE, event.getEtype());
		Assert.assertEquals(EVENT_START, event.getEstart());
		
		event.setEend(EVENT_END);
		event.setDuration(EVENT_DURATION);
		repository.save(event);
		opt = repository.findById(ID_EVENT);
		Assert.assertTrue(opt.isPresent());
		event = opt.get();
		Assert.assertEquals(EVENT_HOST, event.getHost());
		Assert.assertEquals(EVENT_TYPE, event.getEtype());
		Assert.assertEquals(EVENT_START, event.getEstart());
		Assert.assertEquals(EVENT_END, event.getEend());
		Assert.assertEquals(EVENT_DURATION, event.getDuration());
	}
	
}
