package com.unla.tp_oo2_g16.models.dto.responses;

import com.unla.tp_oo2_g16.models.entities.Localidad;

import lombok.*;

@Data
public class UbicacionResponsesDTO {
	
    private Long id;
    private String direccion;
    private Localidad localidad;

}
