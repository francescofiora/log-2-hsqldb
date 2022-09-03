package it.francescofiora.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.francescofiora.model.EventLog;
import it.francescofiora.repository.EventRepository;
import it.francescofiora.service.impl.EventServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test EventService.
 */
@ExtendWith(SpringExtension.class)
class EventServiceTest {

  private static final String ID_EVENT = "test";

  @Test
  void test() {
    EventRepository repository = mock(EventRepository.class);
    EventService service = new EventServiceImpl(repository);

    EventLog event = new EventLog();
    event.setId(ID_EVENT);

    when(repository.findById(eq(ID_EVENT))).thenReturn(Optional.of(event));

    Optional<EventLog> opt = service.findById(ID_EVENT);
    assertThat(opt).isPresent();
    event = opt.get();
    assertThat(event.getId()).isEqualTo(ID_EVENT);

    service.save(event);
    verify(repository, times(1)).save(any(EventLog.class));
  }
}
