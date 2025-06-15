package com.unla.tp_oo2_g16.services.interfaces;

import java.util.List;
import java.util.Set;

import com.unla.tp_oo2_g16.models.dto.responses.ServicioResponsesDTO;
import com.unla.tp_oo2_g16.models.entities.Sede;
import com.unla.tp_oo2_g16.models.entities.Servicio;

public interface ServicioServiceInterface {
	
	List<ServicioResponsesDTO> findAll();
	
	Set<Sede> findSedesByServicio(Servicio servicio);
	
    Servicio findById(Integer id);
    Servicio save(Servicio servicio);
    void deleteById(Integer id);


	ServicioResponsesDTO findByIdDTO(int id);

}
