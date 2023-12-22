package io.bootify.transacciones_bancarias_batch.service;

import io.bootify.transacciones_bancarias_batch.domain.Cliente;
import io.bootify.transacciones_bancarias_batch.domain.CuentaBancaria;
import io.bootify.transacciones_bancarias_batch.domain.TransaccionBancaria;
import io.bootify.transacciones_bancarias_batch.model.CuentaBancariaDTO;
import io.bootify.transacciones_bancarias_batch.repos.ClienteRepository;
import io.bootify.transacciones_bancarias_batch.repos.CuentaBancariaRepository;
import io.bootify.transacciones_bancarias_batch.repos.TransaccionBancariaRepository;
import io.bootify.transacciones_bancarias_batch.util.NotFoundException;
import io.bootify.transacciones_bancarias_batch.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CuentaBancariaService {

    private final CuentaBancariaRepository cuentaBancariaRepository;
    private final ClienteRepository clienteRepository;
    private final TransaccionBancariaRepository transaccionBancariaRepository;

    public CuentaBancariaService(final CuentaBancariaRepository cuentaBancariaRepository,
            final ClienteRepository clienteRepository,
            final TransaccionBancariaRepository transaccionBancariaRepository) {
        this.cuentaBancariaRepository = cuentaBancariaRepository;
        this.clienteRepository = clienteRepository;
        this.transaccionBancariaRepository = transaccionBancariaRepository;
    }

    public List<CuentaBancariaDTO> findAll() {
        final List<CuentaBancaria> cuentaBancarias = cuentaBancariaRepository.findAll(Sort.by("id"));
        return cuentaBancarias.stream()
                .map(cuentaBancaria -> mapToDTO(cuentaBancaria, new CuentaBancariaDTO()))
                .toList();
    }

    public CuentaBancariaDTO get(final Long id) {
        return cuentaBancariaRepository.findById(id)
                .map(cuentaBancaria -> mapToDTO(cuentaBancaria, new CuentaBancariaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CuentaBancariaDTO cuentaBancariaDTO) {
        final CuentaBancaria cuentaBancaria = new CuentaBancaria();
        mapToEntity(cuentaBancariaDTO, cuentaBancaria);
        return cuentaBancariaRepository.save(cuentaBancaria).getId();
    }

    public void update(final Long id, final CuentaBancariaDTO cuentaBancariaDTO) {
        final CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cuentaBancariaDTO, cuentaBancaria);
        cuentaBancariaRepository.save(cuentaBancaria);
    }

    public void delete(final Long id) {
        cuentaBancariaRepository.deleteById(id);
    }

    private CuentaBancariaDTO mapToDTO(final CuentaBancaria cuentaBancaria,
            final CuentaBancariaDTO cuentaBancariaDTO) {
        cuentaBancariaDTO.setId(cuentaBancaria.getId());
        cuentaBancariaDTO.setNumerocuenta(cuentaBancaria.getNumerocuenta());
        cuentaBancariaDTO.setCliente(cuentaBancaria.getCliente());
        cuentaBancariaDTO.setSaldo(cuentaBancaria.getSaldo());
        cuentaBancariaDTO.setClienteId(cuentaBancaria.getClienteId() == null ? null : cuentaBancaria.getClienteId().getId());
        cuentaBancariaDTO.setClienteId2(cuentaBancaria.getClienteId2() == null ? null : cuentaBancaria.getClienteId2().getId());
        return cuentaBancariaDTO;
    }

    private CuentaBancaria mapToEntity(final CuentaBancariaDTO cuentaBancariaDTO,
            final CuentaBancaria cuentaBancaria) {
        cuentaBancaria.setNumerocuenta(cuentaBancariaDTO.getNumerocuenta());
        cuentaBancaria.setCliente(cuentaBancariaDTO.getCliente());
        cuentaBancaria.setSaldo(cuentaBancariaDTO.getSaldo());
        final Cliente clienteId = cuentaBancariaDTO.getClienteId() == null ? null : clienteRepository.findById(cuentaBancariaDTO.getClienteId())
                .orElseThrow(() -> new NotFoundException("clienteId not found"));
        cuentaBancaria.setClienteId(clienteId);
        final Cliente clienteId2 = cuentaBancariaDTO.getClienteId2() == null ? null : clienteRepository.findById(cuentaBancariaDTO.getClienteId2())
                .orElseThrow(() -> new NotFoundException("clienteId2 not found"));
        cuentaBancaria.setClienteId2(clienteId2);
        return cuentaBancaria;
    }

    public String getReferencedWarning(final Long id) {
        final CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final TransaccionBancaria cuentaBancariaIdTransaccionBancaria = transaccionBancariaRepository.findFirstByCuentaBancariaId(cuentaBancaria);
        if (cuentaBancariaIdTransaccionBancaria != null) {
            return WebUtils.getMessage("cuentaBancaria.transaccionBancaria.cuentaBancariaId.referenced", cuentaBancariaIdTransaccionBancaria.getId());
        }
        final TransaccionBancaria cuentaBancariaId2TransaccionBancaria = transaccionBancariaRepository.findFirstByCuentaBancariaId2(cuentaBancaria);
        if (cuentaBancariaId2TransaccionBancaria != null) {
            return WebUtils.getMessage("cuentaBancaria.transaccionBancaria.cuentaBancariaId2.referenced", cuentaBancariaId2TransaccionBancaria.getId());
        }
        return null;
    }

}
