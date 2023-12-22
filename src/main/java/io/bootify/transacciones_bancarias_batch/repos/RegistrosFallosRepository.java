package io.bootify.transacciones_bancarias_batch.repos;

import io.bootify.transacciones_bancarias_batch.domain.RegistrosFallos;
import io.bootify.transacciones_bancarias_batch.domain.TransaccionBancaria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegistrosFallosRepository extends JpaRepository<RegistrosFallos, Long> {

    RegistrosFallos findFirstByTransaccionBancariaId(TransaccionBancaria transaccionBancaria);

    boolean existsByTransaccionBancariaIdId(Long id);

}
