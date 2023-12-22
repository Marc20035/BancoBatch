package io.bootify.transacciones_bancarias_batch.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CuentaBancariaDTO {

    private Long id;

    @Size(max = 255)
    private String numerocuenta;

    @Size(max = 255)
    private String cliente;

    private Double saldo;

    private Long clienteId;

    private Long clienteId2;

}
