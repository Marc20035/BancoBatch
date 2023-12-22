package io.bootify.transacciones_bancarias_batch.repos;

import io.bootify.transacciones_bancarias_batch.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
