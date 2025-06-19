package com.unla.tp_oo2_g16.services.interfaces;

import com.unla.tp_oo2_g16.models.entities.Localidad;
import java.util.List;

public interface LocalidadServiceInterface {
    List<Localidad> findAll();
    Localidad findById(Integer id);
    Localidad save(Localidad localidad);
    void deleteById(Integer id);
}
