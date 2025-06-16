package com.unla.tp_oo2_g16.models.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Cliente extends Persona {
         
    @Column(name = "cuil", nullable = false, unique = true, length = 30)
    private Long cuil;

    @Column(name = "es_concurrente")
    private boolean esConcurrente;
	
	@Override
	public String toString() {
	    return super.toString() + " Cliente [cuil=" + cuil + ", esConcurrente=" + esConcurrente + "]";
	}

    
    
}
