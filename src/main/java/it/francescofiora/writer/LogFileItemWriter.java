package it.francescofiora.writer;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import it.francescofiora.model.EventLog;
import it.francescofiora.model.Message;
import it.francescofiora.service.EventService;

/**
 * LogFileItem Writer.
 * @author francesco
 *
 */
public class LogFileItemWriter implements ItemWriter<Message> {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private EventService service;

	public LogFileItemWriter(final EventService service) {
		this.service = service;
	}

	@Override
	public void write(List<? extends Message> items) throws Exception {
		log.debug("received " + items.size() + "messages");
		
		int count = 0;
		for (Message msg: items) {
			if (msg.isSkip()) {
				continue;
			}
			Optional<EventLog> opt = service.findById(msg.getId());
			EventLog event = opt.isPresent() ? opt.get() : createEvent(msg);
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
		log.debug("saved " + count + " events from " + items.size() + "messages");
	}

	private EventLog createEvent(final Message msg) {
		EventLog event = new EventLog();
		event.setId(msg.getId());
		event.setEtype(msg.getType());
		event.setHost(msg.getHost());
		return event;
	}
}
