package com.unla.tp_oo2_g16.models.entities;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sedes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sede {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede")
    private Integer idSede;
    
    @Column(name = "direccion")
    private String direccion;
    
    @ManyToOne
    @JoinColumn(name = "id_localidad")
    private Localidad localidad;
    
    //Relación ManyToMany con Servicio
    @ManyToMany(mappedBy = "sedes")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Servicio> servicios;

	public Sede(String direccion, Localidad localidad) {
		super();
		this.direccion = direccion;
		this.localidad = localidad;
		this.servicios = new HashSet<>();
	}   
}
