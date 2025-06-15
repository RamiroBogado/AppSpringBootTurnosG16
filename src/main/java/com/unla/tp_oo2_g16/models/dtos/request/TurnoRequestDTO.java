package com.unla.tp_oo2_g16.models.dtos.request;

import java.time.LocalDateTime;

import com.unla.tp_oo2_g16.models.entities.Servicio;

import lombok.Data;

@Data
public class TurnoRequestDTO {
	
    private Integer idTurno;
    private ClienteRequestDTO cliente;
    private Servicio servicio; 
    private LocalDateTime fechaHora;
    private String observaciones;
    private String estado;
    private String codigoTurno;

}
