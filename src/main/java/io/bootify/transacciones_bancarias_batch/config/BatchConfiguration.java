package io.bootify.transacciones_bancarias_batch.config;

import io.bootify.transacciones_bancarias_batch.model.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PlatformTransactionManager transactionManager;

    // Constructor para inyección de dependencias
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, PlatformTransactionManager transactionManager) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Step step1(ItemReader<Transaction> reader, ItemProcessor<Transaction, Transaction> processor, ItemWriter<Transaction> writer, StepExecutionListener stepListener) {
        return stepBuilderFactory.get("step1")
                .<Transaction, Transaction> chunk(5000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(stepListener)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Job importTransactionJob(JobExecutionListener listener, Step step1) {
        return jobBuilderFactory.get("importTransactionJob")
                .listener(listener)
                .start(step1)
                .build();
    }
}
