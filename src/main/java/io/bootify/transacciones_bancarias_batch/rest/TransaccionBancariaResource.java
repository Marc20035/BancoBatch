package io.bootify.transacciones_bancarias_batch.rest;

import io.bootify.transacciones_bancarias_batch.model.TransaccionBancariaDTO;
import io.bootify.transacciones_bancarias_batch.service.TransaccionBancariaService;
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
@RequestMapping(value = "/api/transaccionBancarias", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransaccionBancariaResource {

    private final TransaccionBancariaService transaccionBancariaService;

    public TransaccionBancariaResource(
            final TransaccionBancariaService transaccionBancariaService) {
        this.transaccionBancariaService = transaccionBancariaService;
    }

    @GetMapping
    public ResponseEntity<List<TransaccionBancariaDTO>> getAllTransaccionBancarias() {
        return ResponseEntity.ok(transaccionBancariaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionBancariaDTO> getTransaccionBancaria(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(transaccionBancariaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTransaccionBancaria(
            @RequestBody @Valid final TransaccionBancariaDTO transaccionBancariaDTO) {
        final Long createdId = transaccionBancariaService.create(transaccionBancariaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTransaccionBancaria(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final TransaccionBancariaDTO transaccionBancariaDTO) {
        transaccionBancariaService.update(id, transaccionBancariaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTransaccionBancaria(
            @PathVariable(name = "id") final Long id) {
        transaccionBancariaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
