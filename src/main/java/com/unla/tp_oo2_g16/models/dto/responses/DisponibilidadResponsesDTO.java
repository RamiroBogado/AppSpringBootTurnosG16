package com.unla.tp_oo2_g16.models.dto.responses;

import java.time.LocalTime;
import lombok.Data;

@Data
public class DisponibilidadResponsesDTO {
	
    private String dia;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
    private boolean ocupado;

}
