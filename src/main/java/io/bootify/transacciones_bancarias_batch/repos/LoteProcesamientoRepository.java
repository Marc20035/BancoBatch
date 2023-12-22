package io.bootify.transacciones_bancarias_batch.repos;

import io.bootify.transacciones_bancarias_batch.domain.LoteProcesamiento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoteProcesamientoRepository extends JpaRepository<LoteProcesamiento, Long> {
}
