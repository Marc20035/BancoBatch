package io.bootify.transacciones_bancarias_batch.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "TransaccionBancarias")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class TransaccionBancaria {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column
    private String detalles;

    @Column
    private String estadoprocesamiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_bancaria_id_id")
    private CuentaBancaria cuentaBancariaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lote_procesamiento_id_id")
    private LoteProcesamiento loteProcesamientoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_bancaria_id2id")
    private CuentaBancaria cuentaBancariaId2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lote_procesamiento_id3id")
    private LoteProcesamiento loteProcesamientoId3;

    @OneToOne(
            mappedBy = "transaccionBancariaId",
            fetch = FetchType.LAZY
    )
    private RegistrosFallos registroFallos;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
