package com.unla.tp_oo2_g16.services.interfaces;

import java.util.List;

import com.unla.tp_oo2_g16.models.entities.Localidad;

public interface LocalidadServiceInterface {

    Localidad findById(Integer id);
    void deleteById(Integer id);
    List<Localidad> findAll();
    Localidad save(Localidad localidad);

}
