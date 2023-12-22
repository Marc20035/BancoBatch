package io.bootify.transacciones_bancarias_batch.reader;

import io.bootify.transacciones_bancarias_batch.model.Transaction;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionItemReader {

    @Bean
    public FlatFileItemReader<Transaction> transactionItemReader() {
        return new FlatFileItemReaderBuilder<Transaction>()
                .name("transactionItemReader")
                .resource(new ClassPathResource("Banco.csv"))
                .delimited()
                .names(new String[]{"id", "amount", "transactionDate", "transactionType"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Transaction>() {{
                    setTargetType(Transaction.class);
                }})
                .build();
    }
}
