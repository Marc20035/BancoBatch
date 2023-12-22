package io.bootify.transacciones_bancarias_batch.service;

import io.bootify.transacciones_bancarias_batch.domain.Cliente;
import io.bootify.transacciones_bancarias_batch.domain.CuentaBancaria;
import io.bootify.transacciones_bancarias_batch.model.ClienteDTO;
import io.bootify.transacciones_bancarias_batch.repos.ClienteRepository;
import io.bootify.transacciones_bancarias_batch.repos.CuentaBancariaRepository;
import io.bootify.transacciones_bancarias_batch.util.NotFoundException;
import io.bootify.transacciones_bancarias_batch.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final CuentaBancariaRepository cuentaBancariaRepository;

    public ClienteService(final ClienteRepository clienteRepository,
            final CuentaBancariaRepository cuentaBancariaRepository) {
        this.clienteRepository = clienteRepository;
        this.cuentaBancariaRepository = cuentaBancariaRepository;
    }

    public List<ClienteDTO> findAll() {
        final List<Cliente> clientes = clienteRepository.findAll(Sort.by("id"));
        return clientes.stream()
                .map(cliente -> mapToDTO(cliente, new ClienteDTO()))
                .toList();
    }

    public ClienteDTO get(final Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> mapToDTO(cliente, new ClienteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ClienteDTO clienteDTO) {
        final Cliente cliente = new Cliente();
        mapToEntity(clienteDTO, cliente);
        return clienteRepository.save(cliente).getId();
    }

    public void update(final Long id, final ClienteDTO clienteDTO) {
        final Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(clienteDTO, cliente);
        clienteRepository.save(cliente);
    }

    public void delete(final Long id) {
        clienteRepository.deleteById(id);
    }

    private ClienteDTO mapToDTO(final Cliente cliente, final ClienteDTO clienteDTO) {
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setDatoscontacto(cliente.getDatoscontacto());
        return clienteDTO;
    }

    private Cliente mapToEntity(final ClienteDTO clienteDTO, final Cliente cliente) {
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setDatoscontacto(clienteDTO.getDatoscontacto());
        return cliente;
    }

    public String getReferencedWarning(final Long id) {
        final Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final CuentaBancaria clienteIdCuentaBancaria = cuentaBancariaRepository.findFirstByClienteId(cliente);
        if (clienteIdCuentaBancaria != null) {
            return WebUtils.getMessage("cliente.cuentaBancaria.clienteId.referenced", clienteIdCuentaBancaria.getId());
        }
        final CuentaBancaria clienteId2CuentaBancaria = cuentaBancariaRepository.findFirstByClienteId2(cliente);
        if (clienteId2CuentaBancaria != null) {
            return WebUtils.getMessage("cliente.cuentaBancaria.clienteId2.referenced", clienteId2CuentaBancaria.getId());
        }
        return null;
    }

}
