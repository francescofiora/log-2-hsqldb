package it.francescofiora.reader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.francescofiora.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.FileSystemResource;

/**
 * LogFile ItemReader.
 */
@Slf4j
public class LogFileItemReader extends FlatFileItemReader<Message> {

  private final ObjectMapper mapper = new ObjectMapper();

  public void init(final String file) {
    setResource(new FileSystemResource(file));
    setLineMapper(this::mapLine);
  }

  private Message mapLine(String line, int lineNumber) throws Exception {
    try {
      return mapper.readValue(line, Message.class);
    } catch (JsonParseException e) {
      log.error("Parsing error at line: {} {} input=[{}]: ", lineNumber, line, e.getMessage());
      var message = new Message();
      message.setSkip(true);
      return message;
    }
  }
}
