package it.francescofiora.config;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import it.francescofiora.service.EventService;

/**
 * Test SpringBatchConfig.
 * @author francesco
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = {SpringBatchConfigTest.BatchTestConfig.class})
public class SpringBatchConfigTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void testHelloWorldJob() throws Exception {
		String file = getClass().getClassLoader()
				.getResource("it/francescofiora/reader/events.log").getFile();

		Map<String, JobParameter> map = new HashMap<>();
		map.put("file", new JobParameter(file));
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParameters(map));
		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
	}

	@Configuration
	@Import({SpringBatchConfig.class})
	static class BatchTestConfig {

		@Autowired
		private Job job;

		@Bean
		public EventService service() {
			return Mockito.mock(EventService.class);
		} 
		
		@Bean
		public JobLauncherTestUtils jobLauncherTestUtils() throws NoSuchJobException {
			JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
			jobLauncherTestUtils.setJob(job);

			return jobLauncherTestUtils;
		}
	}
}