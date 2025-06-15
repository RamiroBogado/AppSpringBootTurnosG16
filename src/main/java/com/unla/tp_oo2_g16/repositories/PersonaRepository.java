package com.unla.tp_oo2_g16.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unla.tp_oo2_g16.models.entities.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

}
