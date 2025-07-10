package com.unla.tp_oo2_g16.services.interfaces;


import java.util.List;
import java.util.Set;

import com.unla.tp_oo2_g16.dtos.SedeDTO;
import com.unla.tp_oo2_g16.models.entities.Sede;

public interface SedeServiceInterface {

	List<Sede> findAll();
	Set<Sede> findAllSet();
    Sede findById(Integer id);
    Sede save(Sede ubicacion);
    void deleteById(Integer id);
    List<Sede> buscarPorDireccionOLocalidad(String filtro);
    Sede editado(Sede sede);

    List<Sede> findAllByOrderByDireccionAsc();

    void crearSede(SedeDTO sedeDTO);

    SedeDTO toDTO(Sede s);
    Sede toEntity(SedeDTO dto);
    void guardarSede(SedeDTO sedeDTO);
    void eliminarSede(Integer id);

}
