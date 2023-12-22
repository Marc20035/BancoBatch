package io.bootify.transacciones_bancarias_batch.rest;

import io.bootify.transacciones_bancarias_batch.model.LoteProcesamientoDTO;
import io.bootify.transacciones_bancarias_batch.service.LoteProcesamientoService;
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
@RequestMapping(value = "/api/loteProcesamientos", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoteProcesamientoResource {

    private final LoteProcesamientoService loteProcesamientoService;

    public LoteProcesamientoResource(final LoteProcesamientoService loteProcesamientoService) {
        this.loteProcesamientoService = loteProcesamientoService;
    }

    @GetMapping
    public ResponseEntity<List<LoteProcesamientoDTO>> getAllLoteProcesamientos() {
        return ResponseEntity.ok(loteProcesamientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoteProcesamientoDTO> getLoteProcesamiento(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(loteProcesamientoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLoteProcesamiento(
            @RequestBody @Valid final LoteProcesamientoDTO loteProcesamientoDTO) {
        final Long createdId = loteProcesamientoService.create(loteProcesamientoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLoteProcesamiento(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LoteProcesamientoDTO loteProcesamientoDTO) {
        loteProcesamientoService.update(id, loteProcesamientoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLoteProcesamiento(@PathVariable(name = "id") final Long id) {
        loteProcesamientoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
