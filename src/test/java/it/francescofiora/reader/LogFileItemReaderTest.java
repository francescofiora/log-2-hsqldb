package it.francescofiora.reader;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import it.francescofiora.model.Message;

/**
 * Test LogFileItemReader.
 * 
 * @author francesco
 *
 */
@RunWith(SpringRunner.class)
public class LogFileItemReaderTest {

	private LogFileItemReader itemReader = new LogFileItemReader();

	@Test
	public void testReader() throws Exception {
		int count = StepScopeTestUtils.doInStepScope(MetaDataInstanceFactory.createStepExecution(), () -> callable());
		Assert.assertEquals(6, count);
	}

	private int callable() throws Exception {
		String file = getClass().getClassLoader().getResource("it/francescofiora/reader/events.log").getFile();
		itemReader.init(file);
		itemReader.open(MetaDataInstanceFactory.createStepExecution().getExecutionContext());
		Message msg = null;
		int count = 0;
		try {
			while ((msg = itemReader.read()) != null) {
				Assert.assertNotNull(msg.getId());
				Assert.assertNotNull(msg.getState());
				Assert.assertNotNull(msg.getTimestamp());
				count++;
			}
		} finally {
			try {
				itemReader.close();
			} catch (ItemStreamException e) {
				Assert.fail(e.toString());
			}
		}

		return count;
	}
}
