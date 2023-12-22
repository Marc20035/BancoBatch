package io.bootify.transacciones_bancarias_batch.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransaccionBancariaDTO {

    private Long id;

    @Size(max = 255)
    private String detalles;

    @Size(max = 255)
    private String estadoprocesamiento;

    private Long cuentaBancariaId;

    private Long loteProcesamientoId;

    private Long cuentaBancariaId2;

    private Long loteProcesamientoId3;

}
