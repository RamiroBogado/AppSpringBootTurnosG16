package com.unla.tp_oo2_g16.models.entities;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "turnos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno")
    private Integer idTurno;
     
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;
    
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;
       
    @Column(name = "estado", nullable = false, length = 20)
    private String estado; // Ej: "PENDIENTE", "CONFIRMADO", "CANCELADO", "COMPLETADO"
    
    @Column(name = "codigo_turno", nullable = false, length = 20)
    private String codigoTurno;

    // Constructor sin ID
	public Turno(Cliente cliente, Servicio servicio, LocalDateTime fechaHora) {
		super();
		this.cliente = cliente;
		this.servicio = servicio;
		this.fechaHora = fechaHora;
		this.estado = "PENDIENTE";
		this.codigoTurno = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	}
	
}