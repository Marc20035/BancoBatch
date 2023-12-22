package io.bootify.transacciones_bancarias_batch.processor;

import io.bootify.transacciones_bancarias_batch.model.Transaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TransactionItemProcessor implements ItemProcessor<Transaction, Transaction> {

    @Override
    public Transaction process(final Transaction transaction) throws Exception {
        if (transaction.getAmount() < 0) {
            throw new IllegalArgumentException("Monto de transacción inválido: " + transaction.getAmount());
        }
        // Aquí podrías añadir más lógica de procesamiento según sea necesario
        return transaction;
    }
}

