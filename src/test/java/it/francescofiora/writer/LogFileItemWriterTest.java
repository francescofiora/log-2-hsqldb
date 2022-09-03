package it.francescofiora.writer;

import it.francescofiora.model.EventLog;
import it.francescofiora.model.Message;
import it.francescofiora.service.EventService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test LogFileItemWriter.
 */
@ExtendWith(SpringExtension.class)
class LogFileItemWriterTest {

  private static final String ID_EVENT_1 = "ID1";
  private static final String ID_EVENT_2 = "ID2";

  private LogFileItemWriter itemWriter;

  @Test
  void testWriter() throws Exception {
    EventService service = Mockito.mock(EventService.class);
    Mockito.when(service.findById(Mockito.eq(ID_EVENT_1))).thenReturn(Optional.empty());

    EventLog event = new EventLog();
    event.setId(ID_EVENT_2);
    event.setEstart(10L);
    Mockito.when(service.findById(Mockito.eq(ID_EVENT_2))).thenReturn(Optional.of(event));

    itemWriter = new LogFileItemWriter(service);

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

    Mockito.verify(service, Mockito.times(1)).findById(Mockito.eq(ID_EVENT_1));
    Mockito.verify(service, Mockito.times(1)).findById(Mockito.eq(ID_EVENT_2));
    Mockito.verify(service, Mockito.times(2)).save(Mockito.any(EventLog.class));
  }
}
