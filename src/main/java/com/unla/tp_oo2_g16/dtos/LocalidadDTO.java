package com.unla.tp_oo2_g16.dtos;

import jakarta.validation.constraints.*;

public record LocalidadDTO (
    Integer idLocalidad,

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    String nombre,

    @NotBlank(message = "El código postal es obligatorio")
    @Pattern(regexp = "\\d{4}", message = "Debe ingresar un valor numérico")
    String cp
) {}
