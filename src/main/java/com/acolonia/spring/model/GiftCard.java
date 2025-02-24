package com.acolonia.spring.model;


import com.acolonia.spring.model.enums.GiftcardStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "TARJETA_REGALO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftCard {
    //Atributos de la tabla TARJETA_REGALO
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_giftCard", nullable = false)
    private Long idGiftCard;

    @Column(name = "codigo_unico", nullable = false, unique = true)
    private String code;

    @Column(name = "monto", nullable = false)
    private Double amount;

    @Column(name = "fecha_creacion", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "fecha_final", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GiftcardStatus status;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return  "Código: " + code + "\n" +
                "Monto: " + amount + "\n" +
                "Fecha de Creación: " + createdAt +"\n" +
                "Fecha de vencimiento: " + expiresAt +"\n" +
                "Estado: " + status;
    }
}
