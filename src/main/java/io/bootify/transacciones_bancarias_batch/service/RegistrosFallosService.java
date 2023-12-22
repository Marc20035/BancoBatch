package io.bootify.transacciones_bancarias_batch.service;

import io.bootify.transacciones_bancarias_batch.domain.RegistrosFallos;
import io.bootify.transacciones_bancarias_batch.domain.TransaccionBancaria;
import io.bootify.transacciones_bancarias_batch.model.RegistrosFallosDTO;
import io.bootify.transacciones_bancarias_batch.repos.RegistrosFallosRepository;
import io.bootify.transacciones_bancarias_batch.repos.TransaccionBancariaRepository;
import io.bootify.transacciones_bancarias_batch.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RegistrosFallosService {

    private final RegistrosFallosRepository registrosFallosRepository;
    private final TransaccionBancariaRepository transaccionBancariaRepository;

    public RegistrosFallosService(final RegistrosFallosRepository registrosFallosRepository,
            final TransaccionBancariaRepository transaccionBancariaRepository) {
        this.registrosFallosRepository = registrosFallosRepository;
        this.transaccionBancariaRepository = transaccionBancariaRepository;
    }

    public List<RegistrosFallosDTO> findAll() {
        final List<RegistrosFallos> registrosFalloses = registrosFallosRepository.findAll(Sort.by("id"));
        return registrosFalloses.stream()
                .map(registrosFallos -> mapToDTO(registrosFallos, new RegistrosFallosDTO()))
                .toList();
    }

    public RegistrosFallosDTO get(final Long id) {
        return registrosFallosRepository.findById(id)
                .map(registrosFallos -> mapToDTO(registrosFallos, new RegistrosFallosDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RegistrosFallosDTO registrosFallosDTO) {
        final RegistrosFallos registrosFallos = new RegistrosFallos();
        mapToEntity(registrosFallosDTO, registrosFallos);
        return registrosFallosRepository.save(registrosFallos).getId();
    }

    public void update(final Long id, final RegistrosFallosDTO registrosFallosDTO) {
        final RegistrosFallos registrosFallos = registrosFallosRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(registrosFallosDTO, registrosFallos);
        registrosFallosRepository.save(registrosFallos);
    }

    public void delete(final Long id) {
        registrosFallosRepository.deleteById(id);
    }

    private RegistrosFallosDTO mapToDTO(final RegistrosFallos registrosFallos,
            final RegistrosFallosDTO registrosFallosDTO) {
        registrosFallosDTO.setId(registrosFallos.getId());
        registrosFallosDTO.setDescripcion(registrosFallos.getDescripcion());
        registrosFallosDTO.setTransaccion(registrosFallos.getTransaccion());
        registrosFallosDTO.setTransaccionBancariaId(registrosFallos.getTransaccionBancariaId() == null ? null : registrosFallos.getTransaccionBancariaId().getId());
        return registrosFallosDTO;
    }

    private RegistrosFallos mapToEntity(final RegistrosFallosDTO registrosFallosDTO,
            final RegistrosFallos registrosFallos) {
        registrosFallos.setDescripcion(registrosFallosDTO.getDescripcion());
        registrosFallos.setTransaccion(registrosFallosDTO.getTransaccion());
        final TransaccionBancaria transaccionBancariaId = registrosFallosDTO.getTransaccionBancariaId() == null ? null : transaccionBancariaRepository.findById(registrosFallosDTO.getTransaccionBancariaId())
                .orElseThrow(() -> new NotFoundException("transaccionBancariaId not found"));
        registrosFallos.setTransaccionBancariaId(transaccionBancariaId);
        return registrosFallos;
    }

    public boolean transaccionBancariaIdExists(final Long id) {
        return registrosFallosRepository.existsByTransaccionBancariaIdId(id);
    }

}
