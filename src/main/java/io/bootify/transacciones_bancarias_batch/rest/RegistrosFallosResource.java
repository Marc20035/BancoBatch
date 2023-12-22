package io.bootify.transacciones_bancarias_batch.rest;

import io.bootify.transacciones_bancarias_batch.model.RegistrosFallosDTO;
import io.bootify.transacciones_bancarias_batch.service.RegistrosFallosService;
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
@RequestMapping(value = "/api/registrosFalloss", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrosFallosResource {

    private final RegistrosFallosService registrosFallosService;

    public RegistrosFallosResource(final RegistrosFallosService registrosFallosService) {
        this.registrosFallosService = registrosFallosService;
    }

    @GetMapping
    public ResponseEntity<List<RegistrosFallosDTO>> getAllRegistrosFalloss() {
        return ResponseEntity.ok(registrosFallosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistrosFallosDTO> getRegistrosFallos(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(registrosFallosService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRegistrosFallos(
            @RequestBody @Valid final RegistrosFallosDTO registrosFallosDTO) {
        final Long createdId = registrosFallosService.create(registrosFallosDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateRegistrosFallos(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final RegistrosFallosDTO registrosFallosDTO) {
        registrosFallosService.update(id, registrosFallosDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRegistrosFallos(@PathVariable(name = "id") final Long id) {
        registrosFallosService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
