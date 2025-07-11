package com.unla.tp_oo2_g16.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
    @NotBlank(message = "El usuario es obligatorio")
    String username,

    @NotBlank(message = "La contraseña es obligatoria")
    String password
) {}
