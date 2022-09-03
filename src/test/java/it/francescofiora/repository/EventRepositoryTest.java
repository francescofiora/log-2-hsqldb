package it.francescofiora.repository;

import static org.assertj.core.api.Assertions.assertThat;
import it.francescofiora.model.EventLog;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test EventRepository.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(
    value = {"spring.batch.job.enabled=false", "spring.datasource.url=jdbc:h2:mem:testdb"})
@Transactional
class EventRepositoryTest {

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
  void testCrud() {
    EventLog event = new EventLog();
    event.setId(ID_EVENT);
    event.setEtype(EVENT_TYPE);
    event.setHost(EVENT_HOST);
    event.setEstart(EVENT_START);
    repository.save(event);
    Optional<EventLog> opt = repository.findById(ID_EVENT);
    assertThat(opt).isPresent();
    event = opt.get();
    assertThat(event.getHost()).isEqualTo(EVENT_HOST);
    assertThat(event.getEtype()).isEqualTo(EVENT_TYPE);
    assertThat(event.getEstart()).isEqualTo(EVENT_START);

    event.setEend(EVENT_END);
    event.setDuration(EVENT_DURATION);
    repository.save(event);
    opt = repository.findById(ID_EVENT);
    assertThat(opt).isPresent();
    event = opt.get();
    assertThat(event.getHost()).isEqualTo(EVENT_HOST);
    assertThat(event.getEtype()).isEqualTo(EVENT_TYPE);
    assertThat(event.getEstart()).isEqualTo(EVENT_START);
    assertThat(event.getEend()).isEqualTo(EVENT_END);
    assertThat(event.getDuration()).isEqualTo(EVENT_DURATION);
  }
}
