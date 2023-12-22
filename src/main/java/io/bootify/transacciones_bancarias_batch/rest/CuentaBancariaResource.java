package io.bootify.transacciones_bancarias_batch.rest;

import io.bootify.transacciones_bancarias_batch.model.CuentaBancariaDTO;
import io.bootify.transacciones_bancarias_batch.service.CuentaBancariaService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/cuentaBancarias", produces = MediaType.APPLICATION_JSON_VALUE)
public class CuentaBancariaResource {

    private final CuentaBancariaService cuentaBancariaService;

    public CuentaBancariaResource(final CuentaBancariaService cuentaBancariaService) {
        this.cuentaBancariaService = cuentaBancariaService;
    }

    @GetMapping
    public ResponseEntity<List<CuentaBancariaDTO>> getAllCuentaBancarias() {
        return ResponseEntity.ok(cuentaBancariaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaBancariaDTO> getCuentaBancaria(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(cuentaBancariaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCuentaBancaria(
            @RequestBody @Valid final CuentaBancariaDTO cuentaBancariaDTO) {
        final Long createdId = cuentaBancariaService.create(cuentaBancariaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCuentaBancaria(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CuentaBancariaDTO cuentaBancariaDTO) {
        cuentaBancariaService.update(id, cuentaBancariaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCuentaBancaria(@PathVariable(name = "id") final Long id) {
        cuentaBancariaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
