package it.francescofiora.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import it.francescofiora.service.EventService;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.DatabaseType;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Test SpringBatchConfig.
 */
@ExtendWith(SpringExtension.class)
@Sql(scripts = {"classpath:org/springframework/batch/core/schema-h2.sql"})
@SpringBootTest(classes = {SpringBatchConfigTest.BatchTestConfig.class})
@TestPropertySource(locations = {"classpath:application_test.properties"})
class SpringBatchConfigTest {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Test
  void testHelloWorldJob() throws Exception {
    JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParameters());
    assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
  }

  @Configuration
  @Import({SpringBatchConfig.class, JpaConfiguration.class, DataSourceAutoConfiguration.class,
      DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
      JpaRepositoriesAutoConfiguration.class})
  public static class BatchTestConfig {

    @Bean
    public EventService service() {
      return mock(EventService.class);
    }

    @Bean
    public JobRepository jobRepository(DataSource dataSource,
        PlatformTransactionManager transactionManager) throws Exception {
      var factory = new JobRepositoryFactoryBean();
      factory.setDataSource(dataSource);
      factory.setDatabaseType(DatabaseType.H2.name());
      factory.setTransactionManager(transactionManager);
      factory.afterPropertiesSet();
      return factory.getObject();
    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
      var jobLauncher = new TaskExecutorJobLauncher();
      jobLauncher.setJobRepository(jobRepository);
      jobLauncher.afterPropertiesSet();
      return jobLauncher;
    }

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils(Job job, JobLauncher jobLauncher,
        JobRepository jobRepository) {
      var jobLauncherTestUtils = new JobLauncherTestUtils();
      jobLauncherTestUtils.setJob(job);
      jobLauncherTestUtils.setJobLauncher(jobLauncher);
      jobLauncherTestUtils.setJobRepository(jobRepository);

      return jobLauncherTestUtils;
    }
  }
}
