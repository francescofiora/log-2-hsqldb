package it.francescofiora.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import it.francescofiora.model.Message;
import it.francescofiora.reader.LogFileItemReader;
import it.francescofiora.service.EventService;
import it.francescofiora.writer.LogFileItemWriter;

/**
 * SpringBatch Config.
 * @author francesco
 */
@Configuration
@EnableBatchProcessing
@PropertySource("classpath:/batch.properties")
public class SpringBatchConfig {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Value("${job.chunk:100}") 
    private Integer chunk;
    
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
 
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
 
    @Autowired
    public EventService service;

    @Bean
    public Job read() {
        return jobBuilderFactory.get("read")
                .incrementer(new RunIdIncrementer())
                .start(step1()).build();
    }

    @Bean
    public TaskletStep step1() {
    	log.debug("Chunk: " + chunk);
        return stepBuilderFactory.get("step1")
        		.<Message, Message>chunk(chunk)
                .reader(reader(null))
                .writer(writer())
                .build();
    }

	@Bean
    public ItemWriter<Message> writer() {
		return new LogFileItemWriter(service);
    }

	@Bean
    @StepScope
    public FlatFileItemReader<Message> reader(@Value("#{jobParameters['file']}") String file) {
		log.debug("Load log file: " + file);
		LogFileItemReader reader = new LogFileItemReader();
		reader.init(file);
		return reader;
	}
    
}
