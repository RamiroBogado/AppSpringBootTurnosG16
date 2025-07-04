package com.unla.tp_oo2_g16.services.interfaces;

import java.util.List;
import java.util.Set;

import com.unla.tp_oo2_g16.dtos.ServicioDTO;
import com.unla.tp_oo2_g16.models.entities.Sede;
import com.unla.tp_oo2_g16.models.entities.Servicio;

public interface ServicioServiceInterface {

	List<Servicio> findAll();
	Set<Sede> findSedesByServicio(Servicio servicio);
    Servicio findById(Integer id);
    Servicio save(Servicio servicio);
    void deleteById(Integer id);

    ServicioDTO toDTO(Servicio servicio);
    Servicio toEntity(ServicioDTO dto);

}
