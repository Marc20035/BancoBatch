package io.bootify.transacciones_bancarias_batch.repos;

import io.bootify.transacciones_bancarias_batch.domain.CuentaBancaria;
import io.bootify.transacciones_bancarias_batch.domain.LoteProcesamiento;
import io.bootify.transacciones_bancarias_batch.domain.TransaccionBancaria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransaccionBancariaRepository extends JpaRepository<TransaccionBancaria, Long> {

    TransaccionBancaria findFirstByCuentaBancariaId(CuentaBancaria cuentaBancaria);

    TransaccionBancaria findFirstByLoteProcesamientoId(LoteProcesamiento loteProcesamiento);

    TransaccionBancaria findFirstByCuentaBancariaId2(CuentaBancaria cuentaBancaria);

    TransaccionBancaria findFirstByLoteProcesamientoId3(LoteProcesamiento loteProcesamiento);

}
