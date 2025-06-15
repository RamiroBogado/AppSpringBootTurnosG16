package com.unla.tp_oo2_g16.models.entities;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;

@Entity
@Table(name = "disponibilidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disponibilidad")
    private Integer idDisponibilidad;
  
    // En vez de usar dia de la semana, usás fecha concreta:
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horarioInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horarioFin;

    @Column(name = "estado", nullable = false)
    private boolean estado = false; // false = disponible, true = ocupado

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    public Disponibilidad(LocalDate fecha, LocalTime horarioInicio, LocalTime horarioFin, Servicio servicio) {
        this.fecha = fecha;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
        this.servicio = servicio;
        this.estado = false; // por defecto disponible
    }
}
