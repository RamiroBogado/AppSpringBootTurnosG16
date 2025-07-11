package com.unla.tp_oo2_g16.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServicioDTO(
    
    Integer idServicio,
    
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    
    String descripcion,
    
    @NotNull(message = "Debe seleccionar una duración")
    Integer duracionMinutos/*,

    @Nullable EmpleadoDTO empleado,
    @Nullable Set<SedeDTO> sedes,
    @Nullable Set<DisponibilidadDTO> disponibilidades,

    @Nullable Set<Integer> sedesIds*/
) {
    public static List<DuracionOption> getOpcionesDuracion(){
        return List.of(
            new DuracionOption(15, "15 minutos"),
            new DuracionOption(30, "30 minutos"),
            new DuracionOption(45, "45 minutos"),
            new DuracionOption(60, "1 hora")
        );
    }

    public record DuracionOption(Integer minutos, String texto){}
}
