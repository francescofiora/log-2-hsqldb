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
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;

/**
 * Test LogFileItemWriter.
 */
class LogFileItemWriterTest {

  private static final String ID_EVENT_1 = "ID1";
  private static final String ID_EVENT_2 = "ID2";

  @Test
  void testWriter() throws Exception {
    var service = mock(EventService.class);
    when(service.findById(eq(ID_EVENT_1))).thenReturn(Optional.empty());

    var event = new EventLog();
    event.setId(ID_EVENT_2);
    event.setEstart(10L);
    when(service.findById(eq(ID_EVENT_2))).thenReturn(Optional.of(event));

    var msgEvent1 = new Message();
    msgEvent1.setId(ID_EVENT_1);
    msgEvent1.setState("STARTED");
    msgEvent1.setTimestamp(10);

    var msgEvent2 = new Message();
    msgEvent2.setId(ID_EVENT_2);
    msgEvent2.setState("FINISHED");
    msgEvent2.setTimestamp(15);

    var itemWriter = new LogFileItemWriter(service);
    itemWriter.write(Chunk.of(msgEvent1, msgEvent2));

    verify(service, times(1)).findById(eq(ID_EVENT_1));
    verify(service, times(1)).findById(eq(ID_EVENT_2));
    verify(service, times(2)).save(any(EventLog.class));
  }
}
