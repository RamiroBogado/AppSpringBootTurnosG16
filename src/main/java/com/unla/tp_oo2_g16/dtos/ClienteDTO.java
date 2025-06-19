package com.unla.tp_oo2_g16.dtos;

import jakarta.validation.constraints.*;

public record ClienteDTO(

    Integer idPersona,

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    String nombre,

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100)
    String apellido,

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{7,8}", message = "El DNI debe tener entre 7 y 8 números")
    String dni,

    @NotNull(message = "El CUIL es obligatorio")
    @Digits(integer = 11, fraction = 0)
    Long cuil,

    boolean esConcurrente,

    @Email(message = "Email inválido")
    String emailUser,

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    String passwordUser
) {}
