package com.unla.tp_oo2_g16.dtos;

import jakarta.validation.constraints.*;

public record TurnoGestionDTO(
	    Integer idTurno,

	    @NotNull(message = "Debe seleccionar un cliente")
	    Integer idCliente,

	    @NotNull(message = "Debe seleccionar un servicio")
	    Integer idServicio,

	    @NotNull(message = "Debe seleccionar una sede")
	    Integer idSede,              // <-- nuevo campo para la sede

	    @NotBlank(message = "Debe seleccionar fecha y hora")
	    String fechaHora, // formato yyyy-MM-dd'T'HH:mm

	    @NotBlank(message = "El estado es obligatorio")
	    String estado,

	    String codigoTurno,

	    // campos de ayuda para la vista
	    String nombreCliente,
	    String nombreServicio,
	    String nombreSede,
	    String nombreLocalidad         // <-- para mostrar en la vista
	) {}

