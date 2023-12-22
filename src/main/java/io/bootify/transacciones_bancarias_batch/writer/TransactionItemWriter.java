package io.bootify.transacciones_bancarias_batch.writer;

import io.bootify.transacciones_bancarias_batch.model.Transaction;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class TransactionItemWriter {
    @Bean
    public JdbcBatchItemWriter<Transaction> transactionItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Transaction>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO transactions (id, amount, transaction_date, transaction_type) VALUES (:id, :amount, :transactionDate, :transactionType)")
                .dataSource(dataSource)
                .build();
    }
}

