package com.unla.tp_oo2_g16.services.interfaces;

import java.util.List;
import com.unla.tp_oo2_g16.models.entities.Persona;

public interface PersonaServiceInterface {
    List<Persona> findAll();
    Persona findById(Integer id);
    Persona save(Persona persona);
    void deleteById(Integer id);

}
