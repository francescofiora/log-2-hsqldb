package it.francescofiora.service;

import static org.assertj.core.api.Assertions.assertThat;
import it.francescofiora.model.EventLog;
import it.francescofiora.repository.EventRepository;
import it.francescofiora.service.impl.EventServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test EventService.
 */
@ExtendWith(SpringExtension.class)
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

    Mockito.when(repository.findById(Mockito.eq(ID_EVENT))).thenReturn(Optional.of(event));

    Optional<EventLog> opt = service.findById(ID_EVENT);
    assertThat(opt).isPresent();
    event = opt.get();
    assertThat(event.getId()).isEqualTo(ID_EVENT);

    service.save(event);
    Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(EventLog.class));
  }

  @TestConfiguration
  static class TestContextConfiguration {

    @Bean
    public EventService service(EventRepository repository) {
      return new EventServiceImpl(repository);
    }
  }
}
