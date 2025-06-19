package com.unla.tp_oo2_g16.services.interfaces;

import com.unla.tp_oo2_g16.models.entities.Localidad;

public interface LocalidadServiceInterface {

    Localidad findById(int id);
    Localidad findByLocalidad(String localidad);

    Localidad save(Localidad localidad);

}
