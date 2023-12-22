package io.bootify.transacciones_bancarias_batch.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoteProcesamientoDTO {

    private Long id;

    @Size(max = 255)
    private String estado;

    private Integer cantidadtransacciones;

}
