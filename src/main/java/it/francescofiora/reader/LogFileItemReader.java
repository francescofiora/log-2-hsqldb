package it.francescofiora.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.core.io.FileSystemResource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.francescofiora.model.Message;

/**
 * LogFile ItemReader.
 * @author francesco
 *
 */
public class LogFileItemReader extends FlatFileItemReader<Message> {

    private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void init(final String file) {
		setResource(new FileSystemResource(file));

		setLineMapper(new LineMapper<Message>() {

			private ObjectMapper mapper = new ObjectMapper();
			
			@Override
			public Message mapLine(String line, int lineNumber) throws Exception {
				try {
					return mapper.readValue(line, Message.class);
				} catch (JsonParseException e) {
					log.error("Parsing error at line: " + lineNumber + " input=[" + line + "]: " + e.getMessage());
					Message message = new Message();
					message.setSkip(true);
					return message;
				}
			}

		});
	}
}
