package com.unla.tp_oo2_g16.dtos;

import com.unla.tp_oo2_g16.models.entities.Localidad;

import jakarta.validation.constraints.NotBlank;

public record SedeDTO(
    Integer idSede, 
    
    @NotBlank(message = "La dirección es obligatoria")
    String direccion, 
    
    @NotBlank(message = "La localidad es obligatoria")
    Localidad localidad
    
) { }
