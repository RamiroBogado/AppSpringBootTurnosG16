package com.unla.tp_oo2_g16.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "localidad")
@Data
@NoArgsConstructor
@Getter
public class Localidad {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localidad")
	private Integer idLocalidad;
    
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "codigo_postal")
	private String cp;

	public Localidad(String nombre, String cp) {
		super();
		this.nombre = nombre;
		this.cp = cp;
	}

	
	
	
}
