package it.francescofiora.reader;

import static org.assertj.core.api.Assertions.assertThat;

import it.francescofiora.model.Message;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;

/**
 * Test LogFileItemReader.
 */
class LogFileItemReaderTest {

  @Test
  void testReader() throws Exception {
    var count = StepScopeTestUtils
        .doInStepScope(MetaDataInstanceFactory.createStepExecution(), this::callable);
    assertThat(count).isEqualTo(6);
  }

  private int callable() throws Exception {
    var itemReader = new LogFileItemReader();

    var file =
        getClass().getClassLoader().getResource("it/francescofiora/reader/events.log").getFile();
    itemReader.init(file);
    itemReader.open(MetaDataInstanceFactory.createStepExecution().getExecutionContext());
    Message msg = null;
    int count = 0;
    try {
      while ((msg = itemReader.read()) != null) {
        assertThat(msg.getId()).isNotNull();
        assertThat(msg.getState()).isNotNull();
        assertThat(msg.getTimestamp()).isNotNull();
        count++;
      }
    } finally {
      try {
        itemReader.close();
      } catch (ItemStreamException e) {
        throw new RuntimeException(e.getMessage());
      }
    }

    return count;
  }
}
