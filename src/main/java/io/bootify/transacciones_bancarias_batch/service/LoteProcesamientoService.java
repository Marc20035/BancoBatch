package io.bootify.transacciones_bancarias_batch.service;

import io.bootify.transacciones_bancarias_batch.domain.LoteProcesamiento;
import io.bootify.transacciones_bancarias_batch.domain.TransaccionBancaria;
import io.bootify.transacciones_bancarias_batch.model.LoteProcesamientoDTO;
import io.bootify.transacciones_bancarias_batch.repos.LoteProcesamientoRepository;
import io.bootify.transacciones_bancarias_batch.repos.TransaccionBancariaRepository;
import io.bootify.transacciones_bancarias_batch.util.NotFoundException;
import io.bootify.transacciones_bancarias_batch.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LoteProcesamientoService {

    private final LoteProcesamientoRepository loteProcesamientoRepository;
    private final TransaccionBancariaRepository transaccionBancariaRepository;

    public LoteProcesamientoService(final LoteProcesamientoRepository loteProcesamientoRepository,
            final TransaccionBancariaRepository transaccionBancariaRepository) {
        this.loteProcesamientoRepository = loteProcesamientoRepository;
        this.transaccionBancariaRepository = transaccionBancariaRepository;
    }

    public List<LoteProcesamientoDTO> findAll() {
        final List<LoteProcesamiento> loteProcesamientoes = loteProcesamientoRepository.findAll(Sort.by("id"));
        return loteProcesamientoes.stream()
                .map(loteProcesamiento -> mapToDTO(loteProcesamiento, new LoteProcesamientoDTO()))
                .toList();
    }

    public LoteProcesamientoDTO get(final Long id) {
        return loteProcesamientoRepository.findById(id)
                .map(loteProcesamiento -> mapToDTO(loteProcesamiento, new LoteProcesamientoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LoteProcesamientoDTO loteProcesamientoDTO) {
        final LoteProcesamiento loteProcesamiento = new LoteProcesamiento();
        mapToEntity(loteProcesamientoDTO, loteProcesamiento);
        return loteProcesamientoRepository.save(loteProcesamiento).getId();
    }

    public void update(final Long id, final LoteProcesamientoDTO loteProcesamientoDTO) {
        final LoteProcesamiento loteProcesamiento = loteProcesamientoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(loteProcesamientoDTO, loteProcesamiento);
        loteProcesamientoRepository.save(loteProcesamiento);
    }

    public void delete(final Long id) {
        loteProcesamientoRepository.deleteById(id);
    }

    private LoteProcesamientoDTO mapToDTO(final LoteProcesamiento loteProcesamiento,
            final LoteProcesamientoDTO loteProcesamientoDTO) {
        loteProcesamientoDTO.setId(loteProcesamiento.getId());
        loteProcesamientoDTO.setEstado(loteProcesamiento.getEstado());
        loteProcesamientoDTO.setCantidadtransacciones(loteProcesamiento.getCantidadtransacciones());
        return loteProcesamientoDTO;
    }

    private LoteProcesamiento mapToEntity(final LoteProcesamientoDTO loteProcesamientoDTO,
            final LoteProcesamiento loteProcesamiento) {
        loteProcesamiento.setEstado(loteProcesamientoDTO.getEstado());
        loteProcesamiento.setCantidadtransacciones(loteProcesamientoDTO.getCantidadtransacciones());
        return loteProcesamiento;
    }

    public String getReferencedWarning(final Long id) {
        final LoteProcesamiento loteProcesamiento = loteProcesamientoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final TransaccionBancaria loteProcesamientoIdTransaccionBancaria = transaccionBancariaRepository.findFirstByLoteProcesamientoId(loteProcesamiento);
        if (loteProcesamientoIdTransaccionBancaria != null) {
            return WebUtils.getMessage("loteProcesamiento.transaccionBancaria.loteProcesamientoId.referenced", loteProcesamientoIdTransaccionBancaria.getId());
        }
        final TransaccionBancaria loteProcesamientoId3TransaccionBancaria = transaccionBancariaRepository.findFirstByLoteProcesamientoId3(loteProcesamiento);
        if (loteProcesamientoId3TransaccionBancaria != null) {
            return WebUtils.getMessage("loteProcesamiento.transaccionBancaria.loteProcesamientoId3.referenced", loteProcesamientoId3TransaccionBancaria.getId());
        }
        return null;
    }

}
