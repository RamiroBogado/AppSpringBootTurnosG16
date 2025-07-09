package com.unla.tp_oo2_g16.services.interfaces;

import java.util.List;

import com.unla.tp_oo2_g16.dtos.LocalidadDTO;
import com.unla.tp_oo2_g16.models.entities.Localidad;

public interface LocalidadServiceInterface {

    Localidad findById(Integer id);
    void deleteById(Integer id);
    List<Localidad> findAll();
    List<Localidad> findAllByOrderByCpAsc();
    Localidad save(Localidad localidad);
    Localidad editado(Localidad localidad);
    void borrarLocalidad(Integer idLocalidad);
    void verificarYEliminarLocalidad(Integer idLocalidad);

    boolean existsByCp(String cp);
    boolean existsByCpAndIdLocalidadNot(String cp, Integer idLocalidad);
    boolean tieneSedesAsociadas(Integer idLocalidad);

    LocalidadDTO toDTO(Localidad l);
    Localidad toEntity(LocalidadDTO dto);

}
