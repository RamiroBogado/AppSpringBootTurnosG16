package com.unla.tp_oo2_g16.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SedeDTO(
    Integer idSede, 
    
    @NotBlank(message = "La dirección es obligatoria")
    String direccion, 
    
    @NotNull(message = "Debe seleccionar una localidad")
    Integer idLocalidad,

    String nombreLocalidad,
    String cpLocalidad
    
) { }
