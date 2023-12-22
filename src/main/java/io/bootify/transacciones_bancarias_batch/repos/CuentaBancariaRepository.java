package io.bootify.transacciones_bancarias_batch.repos;

import io.bootify.transacciones_bancarias_batch.domain.Cliente;
import io.bootify.transacciones_bancarias_batch.domain.CuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Long> {

    CuentaBancaria findFirstByClienteId(Cliente cliente);

    CuentaBancaria findFirstByClienteId2(Cliente cliente);

}
