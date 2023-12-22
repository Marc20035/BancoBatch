package io.bootify.transacciones_bancarias_batch.service;

import io.bootify.transacciones_bancarias_batch.domain.CuentaBancaria;
import io.bootify.transacciones_bancarias_batch.domain.LoteProcesamiento;
import io.bootify.transacciones_bancarias_batch.domain.RegistrosFallos;
import io.bootify.transacciones_bancarias_batch.domain.TransaccionBancaria;
import io.bootify.transacciones_bancarias_batch.model.TransaccionBancariaDTO;
import io.bootify.transacciones_bancarias_batch.repos.CuentaBancariaRepository;
import io.bootify.transacciones_bancarias_batch.repos.LoteProcesamientoRepository;
import io.bootify.transacciones_bancarias_batch.repos.RegistrosFallosRepository;
import io.bootify.transacciones_bancarias_batch.repos.TransaccionBancariaRepository;
import io.bootify.transacciones_bancarias_batch.util.NotFoundException;
import io.bootify.transacciones_bancarias_batch.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TransaccionBancariaService {

    private final TransaccionBancariaRepository transaccionBancariaRepository;
    private final CuentaBancariaRepository cuentaBancariaRepository;
    private final LoteProcesamientoRepository loteProcesamientoRepository;
    private final RegistrosFallosRepository registrosFallosRepository;

    public TransaccionBancariaService(
            final TransaccionBancariaRepository transaccionBancariaRepository,
            final CuentaBancariaRepository cuentaBancariaRepository,
            final LoteProcesamientoRepository loteProcesamientoRepository,
            final RegistrosFallosRepository registrosFallosRepository) {
        this.transaccionBancariaRepository = transaccionBancariaRepository;
        this.cuentaBancariaRepository = cuentaBancariaRepository;
        this.loteProcesamientoRepository = loteProcesamientoRepository;
        this.registrosFallosRepository = registrosFallosRepository;
    }

    public List<TransaccionBancariaDTO> findAll() {
        final List<TransaccionBancaria> transaccionBancarias = transaccionBancariaRepository.findAll(Sort.by("id"));
        return transaccionBancarias.stream()
                .map(transaccionBancaria -> mapToDTO(transaccionBancaria, new TransaccionBancariaDTO()))
                .toList();
    }

    public TransaccionBancariaDTO get(final Long id) {
        return transaccionBancariaRepository.findById(id)
                .map(transaccionBancaria -> mapToDTO(transaccionBancaria, new TransaccionBancariaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TransaccionBancariaDTO transaccionBancariaDTO) {
        final TransaccionBancaria transaccionBancaria = new TransaccionBancaria();
        mapToEntity(transaccionBancariaDTO, transaccionBancaria);
        return transaccionBancariaRepository.save(transaccionBancaria).getId();
    }

    public void update(final Long id, final TransaccionBancariaDTO transaccionBancariaDTO) {
        final TransaccionBancaria transaccionBancaria = transaccionBancariaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(transaccionBancariaDTO, transaccionBancaria);
        transaccionBancariaRepository.save(transaccionBancaria);
    }

    public void delete(final Long id) {
        transaccionBancariaRepository.deleteById(id);
    }

    private TransaccionBancariaDTO mapToDTO(final TransaccionBancaria transaccionBancaria,
            final TransaccionBancariaDTO transaccionBancariaDTO) {
        transaccionBancariaDTO.setId(transaccionBancaria.getId());
        transaccionBancariaDTO.setDetalles(transaccionBancaria.getDetalles());
        transaccionBancariaDTO.setEstadoprocesamiento(transaccionBancaria.getEstadoprocesamiento());
        transaccionBancariaDTO.setCuentaBancariaId(transaccionBancaria.getCuentaBancariaId() == null ? null : transaccionBancaria.getCuentaBancariaId().getId());
        transaccionBancariaDTO.setLoteProcesamientoId(transaccionBancaria.getLoteProcesamientoId() == null ? null : transaccionBancaria.getLoteProcesamientoId().getId());
        transaccionBancariaDTO.setCuentaBancariaId2(transaccionBancaria.getCuentaBancariaId2() == null ? null : transaccionBancaria.getCuentaBancariaId2().getId());
        transaccionBancariaDTO.setLoteProcesamientoId3(transaccionBancaria.getLoteProcesamientoId3() == null ? null : transaccionBancaria.getLoteProcesamientoId3().getId());
        return transaccionBancariaDTO;
    }

    private TransaccionBancaria mapToEntity(final TransaccionBancariaDTO transaccionBancariaDTO,
            final TransaccionBancaria transaccionBancaria) {
        transaccionBancaria.setDetalles(transaccionBancariaDTO.getDetalles());
        transaccionBancaria.setEstadoprocesamiento(transaccionBancariaDTO.getEstadoprocesamiento());
        final CuentaBancaria cuentaBancariaId = transaccionBancariaDTO.getCuentaBancariaId() == null ? null : cuentaBancariaRepository.findById(transaccionBancariaDTO.getCuentaBancariaId())
                .orElseThrow(() -> new NotFoundException("cuentaBancariaId not found"));
        transaccionBancaria.setCuentaBancariaId(cuentaBancariaId);
        final LoteProcesamiento loteProcesamientoId = transaccionBancariaDTO.getLoteProcesamientoId() == null ? null : loteProcesamientoRepository.findById(transaccionBancariaDTO.getLoteProcesamientoId())
                .orElseThrow(() -> new NotFoundException("loteProcesamientoId not found"));
        transaccionBancaria.setLoteProcesamientoId(loteProcesamientoId);
        final CuentaBancaria cuentaBancariaId2 = transaccionBancariaDTO.getCuentaBancariaId2() == null ? null : cuentaBancariaRepository.findById(transaccionBancariaDTO.getCuentaBancariaId2())
                .orElseThrow(() -> new NotFoundException("cuentaBancariaId2 not found"));
        transaccionBancaria.setCuentaBancariaId2(cuentaBancariaId2);
        final LoteProcesamiento loteProcesamientoId3 = transaccionBancariaDTO.getLoteProcesamientoId3() == null ? null : loteProcesamientoRepository.findById(transaccionBancariaDTO.getLoteProcesamientoId3())
                .orElseThrow(() -> new NotFoundException("loteProcesamientoId3 not found"));
        transaccionBancaria.setLoteProcesamientoId3(loteProcesamientoId3);
        return transaccionBancaria;
    }

    public String getReferencedWarning(final Long id) {
        final TransaccionBancaria transaccionBancaria = transaccionBancariaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final RegistrosFallos transaccionBancariaIdRegistrosFallos = registrosFallosRepository.findFirstByTransaccionBancariaId(transaccionBancaria);
        if (transaccionBancariaIdRegistrosFallos != null) {
            return WebUtils.getMessage("transaccionBancaria.registrosFallos.transaccionBancariaId.referenced", transaccionBancariaIdRegistrosFallos.getId());
        }
        return null;
    }

}
