package com.unla.tp_oo2_g16.dtos;

import jakarta.validation.constraints.*;

public record EmpleadoDTO(
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

    @NotBlank(message = "El legajo es obligatorio")
    String legajo,

    @NotBlank(message = "El puesto es obligatorio")
    String puesto
) {}
