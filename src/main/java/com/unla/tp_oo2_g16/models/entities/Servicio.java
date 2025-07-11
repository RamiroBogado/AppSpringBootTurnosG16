package com.unla.tp_oo2_g16.models.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "servicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Integer idServicio;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracion; // en minutos
    
    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Disponibilidad> disponibilidades;
    
    // Relación ManyToMany con Sede 
    //@NotNull <---------
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "servicio_sedes",
        joinColumns = @JoinColumn(name = "id_servicio"),
        inverseJoinColumns = @JoinColumn(name = "id_sede")
    )
    private Set<Sede> sedes;
    
    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = true)
    private Empleado empleado;

    
    public Servicio(String nombre, String descripcion, Integer duracion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.empleado = null;
        this.sedes = new HashSet<>();
        this.disponibilidades = new HashSet<>();
    }
       
}