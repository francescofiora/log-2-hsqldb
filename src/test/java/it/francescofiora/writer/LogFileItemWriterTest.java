package it.francescofiora.writer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.francescofiora.model.EventLog;
import it.francescofiora.model.Message;
import it.francescofiora.service.EventService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test LogFileItemWriter.
 */
@ExtendWith(SpringExtension.class)
class LogFileItemWriterTest {

  private static final String ID_EVENT_1 = "ID1";
  private static final String ID_EVENT_2 = "ID2";

  @Test
  void testWriter() throws Exception {
    EventService service = mock(EventService.class);
    when(service.findById(eq(ID_EVENT_1))).thenReturn(Optional.empty());

    EventLog event = new EventLog();
    event.setId(ID_EVENT_2);
    event.setEstart(10L);
    when(service.findById(eq(ID_EVENT_2))).thenReturn(Optional.of(event));

    LogFileItemWriter itemWriter = new LogFileItemWriter(service);

    List<Message> items = new ArrayList<>();
    Message msg = new Message();
    msg.setId(ID_EVENT_1);
    msg.setState("STARTED");
    msg.setTimestamp(10);
    items.add(msg);

    msg = new Message();
    msg.setId(ID_EVENT_2);
    msg.setState("FINISHED");
    msg.setTimestamp(15);
    items.add(msg);
    itemWriter.write(items);

    verify(service, times(1)).findById(eq(ID_EVENT_1));
    verify(service, times(1)).findById(eq(ID_EVENT_2));
    verify(service, times(2)).save(any(EventLog.class));
  }
}
