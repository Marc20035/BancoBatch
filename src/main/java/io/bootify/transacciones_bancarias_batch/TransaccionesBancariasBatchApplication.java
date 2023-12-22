package io.bootify.transacciones_bancarias_batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

@SpringBootApplication
@EnableScheduling
public class TransaccionesBancariasBatchApplication {

    private final JobLauncher jobLauncher;
    private final Job job;

    public TransaccionesBancariasBatchApplication(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    public static void main(String[] args) {
        SpringApplication.run(TransaccionesBancariasBatchApplication.class, args);
    }

    @Scheduled(cron = "0 0 1 * * ?") // Programado para ejecutarse a la 1 AM todos los días
    public void perform() throws Exception {
        jobLauncher.run(job, new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
    }
}
