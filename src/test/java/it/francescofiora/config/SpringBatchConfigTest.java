package it.francescofiora.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import it.francescofiora.service.EventService;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test SpringBatchConfig.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SpringBatchConfigTest.BatchTestConfig.class})
class SpringBatchConfigTest {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Test
  void testHelloWorldJob() throws Exception {
    var file =
        getClass().getClassLoader().getResource("it/francescofiora/reader/events.log").getFile();

    var map = Map.of("file", new JobParameter(file));
    JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParameters(map));
    assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
  }

  @Configuration
  @Import({SpringBatchConfig.class})
  static class BatchTestConfig {

    @Bean
    public EventService service() {
      return mock(EventService.class);
    }

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils(Job job) throws NoSuchJobException {
      var jobLauncherTestUtils = new JobLauncherTestUtils();
      jobLauncherTestUtils.setJob(job);

      return jobLauncherTestUtils;
    }
  }
}
