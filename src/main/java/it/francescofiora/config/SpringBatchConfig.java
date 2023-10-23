package it.francescofiora.config;

import it.francescofiora.model.Message;
import it.francescofiora.reader.LogFileItemReader;
import it.francescofiora.service.EventService;
import it.francescofiora.writer.LogFileItemWriter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * SpringBatch Config.
 */
@Slf4j
@Configuration
public class SpringBatchConfig {

  @Value("${job.chunk:100}")
  private Integer chunk;

  /**
   * Create Job read.
   *
   * @param jobRepository the JobRepository
   * @param step1 the TaskletStep 1
   * @return the Job
   */
  @Bean
  public Job read(JobRepository jobRepository, TaskletStep step1) {
    return new JobBuilder("read", jobRepository).incrementer(new RunIdIncrementer()).start(step1)
        .build();
  }

  /**
   * Create step1 bean.
   *
   * @param jobRepository the JobRepository
   * @param batchTransactionManager the PlatformTransactionManager
   * @param writer the ItemWriter
   * @return the TaskletStep
   */
  @Bean
  public TaskletStep step1(JobRepository jobRepository,
      PlatformTransactionManager batchTransactionManager, ItemWriter<Message> writer,
      ItemReader<Message> reader) {
    log.debug("Chunk: " + chunk);
    return new StepBuilder("step1", jobRepository)
        .<Message, Message>chunk(chunk, batchTransactionManager).reader(reader).writer(writer)
        .build();
  }

  @Bean
  public ItemWriter<Message> writer(EventService service) {
    return new LogFileItemWriter(service);
  }

  @Value("${file}")
  private String file;

  /**
   * Create ItemReader bean.
   *
   * @return ItemReader
   */
  @Bean
  public ItemReader<Message> reader() {
    log.debug("Load log file: " + file);
    var reader = new LogFileItemReader();
    reader.init(file);
    return reader;
  }

}
