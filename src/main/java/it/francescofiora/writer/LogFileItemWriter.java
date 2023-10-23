package it.francescofiora.writer;

import it.francescofiora.model.EventLog;
import it.francescofiora.model.Message;
import it.francescofiora.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

/**
 * LogFileItem Writer.
 */
@Slf4j
@RequiredArgsConstructor
public class LogFileItemWriter implements ItemWriter<Message> {

  private final EventService service;

  @Override
  public void write(Chunk<? extends Message> items) throws Exception {
    log.debug("received {} messages", items.size());

    int count = 0;
    for (var msg : items) {
      if (msg.isSkip()) {
        continue;
      }
      var opt = service.findById(msg.getId());
      var event = opt.isPresent() ? opt.get() : createEvent(msg);
      if ("STARTED".equals(msg.getState())) {
        event.setEstart(msg.getTimestamp());
      } else {
        event.setEend(msg.getTimestamp());
      }
      if (event.getEstart() != null && event.getEend() != null) {
        event.setDuration(event.getEend() - event.getEstart());
        event.setAlert(event.getDuration() > 4 ? "T" : "F");
        count++;
      }
      service.save(event);
    }
    log.debug("saved {} events from {} messages", count, items.size());
  }

  private EventLog createEvent(final Message msg) {
    var event = new EventLog();
    event.setId(msg.getId());
    event.setEtype(msg.getType());
    event.setHost(msg.getHost());
    return event;
  }
}
