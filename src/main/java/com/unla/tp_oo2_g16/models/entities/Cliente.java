package com.unla.tp_oo2_g16.models.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Cliente extends Persona {

    @NotNull(message = "El CUIL es obligatorio")
    @Digits(integer = 11, fraction = 0, message = "El CUIL debe contener hasta 11 dígitos sin decimales")
    @Column(name = "cuil", nullable = false, unique = true, length = 30)
    private Long cuil;

    @Column(name = "es_concurrente")
    private boolean esConcurrente;

    //Prueba
}

