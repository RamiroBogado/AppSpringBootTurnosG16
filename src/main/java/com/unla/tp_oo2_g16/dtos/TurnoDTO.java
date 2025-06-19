package com.unla.tp_oo2_g16.dtos;

public record TurnoDTO(
    int servicioId,
    int sedeId,
    String fecha,
    String horario,
    String codigoTurno
) {}
