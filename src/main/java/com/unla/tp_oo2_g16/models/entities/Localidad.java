package com.unla.tp_oo2_g16.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Getter
public class Localidad {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localidad")
	private Integer idLocalidad;
    
	private String localidad;

	public Localidad(String localidad) {
		super();
		this.localidad = localidad;
	}

	
	
	
}
