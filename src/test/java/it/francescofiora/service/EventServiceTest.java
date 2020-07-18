package it.francescofiora.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import it.francescofiora.model.EventLog;
import it.francescofiora.repository.EventRepository;
import it.francescofiora.service.impl.EventServiceImpl;

/**
 * Test EventService.
 * @author francesco
 *
 */
@RunWith(SpringRunner.class)
public class EventServiceTest {

	@Autowired
	private EventService service;

	@MockBean
	private EventRepository repository;

	private static final String ID_EVENT = "test";

	@Test
	public void test() {
		EventLog event = new EventLog();
		event.setId(ID_EVENT);

		Mockito.when(repository.findById(Mockito.eq(ID_EVENT)))
			.thenReturn(Optional.of(event));
		
		Optional<EventLog> opt = service.findById(ID_EVENT);
		Assert.assertTrue(opt.isPresent());
		event = opt.get();
		Assert.assertEquals(ID_EVENT, event.getId());
		
		service.save(event);
		Mockito.verify(repository, Mockito.times(1))
			.save(Mockito.any(EventLog.class));
	}
	
	@TestConfiguration
	static class TestContextConfiguration {

		@Bean
		public EventService service(EventRepository repository) {
			return new EventServiceImpl(repository);
		}
	}
}
