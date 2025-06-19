package com.unla.tp_oo2_g16.services.interfaces;

import com.unla.tp_oo2_g16.models.entities.Localidad;
<<<<<<< HEAD

public interface LocalidadServiceInterface {

    Localidad findById(int id);
    Localidad findByLocalidad(String localidad);

    Localidad save(Localidad localidad);

=======
import java.util.List;

public interface LocalidadServiceInterface {
    List<Localidad> findAll();
    Localidad findById(Integer id);
    Localidad save(Localidad localidad);
    void deleteById(Integer id);
>>>>>>> ca97c3b
}
