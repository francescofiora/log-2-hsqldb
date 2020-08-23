package it.francescofiora.reader;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.francescofiora.model.Message;

/**
 * Test LogFileItemReader.
 * 
 * @author francesco
 *
 */
@ExtendWith(SpringExtension.class)
public class LogFileItemReaderTest {

	private LogFileItemReader itemReader = new LogFileItemReader();

	@Test
	public void testReader() throws Exception {
		int count = StepScopeTestUtils.doInStepScope(MetaDataInstanceFactory.createStepExecution(), () -> callable());
		assertThat(count).isEqualTo(6);
	}

	private int callable() throws Exception {
		String file = getClass().getClassLoader().getResource("it/francescofiora/reader/events.log").getFile();
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
