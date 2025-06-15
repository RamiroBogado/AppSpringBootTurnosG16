package com.unla.tp_oo2_g16.models.dto.responses;

import java.util.Set;

import lombok.*;

@Data
public class ServicioResponsesDTO {

    private Long id;
    private String nombre;
    private Set<DisponibilidadResponsesDTO> disponibilidades;

}
