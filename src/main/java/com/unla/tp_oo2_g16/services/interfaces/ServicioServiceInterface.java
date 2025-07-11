package com.unla.tp_oo2_g16.services.interfaces;

import java.util.List;
import java.util.Set;
import com.unla.tp_oo2_g16.dtos.ServicioDTO;
import com.unla.tp_oo2_g16.models.entities.Sede;
import com.unla.tp_oo2_g16.models.entities.Servicio;

public interface ServicioServiceInterface {

	List<Servicio> findAll();
    List<Servicio> findAllByOrderByNombreAsc();
	Set<Sede> findSedesByServicio(Servicio servicio);
    Servicio findById(Integer id);
    Servicio save(Servicio servicio);
    void deleteById(Integer id);
    Servicio editado(Servicio servicio);

    boolean existsByNombre(String nombre);
    boolean existsByNombreAndIdServicioNot(String nombre, Integer idServicio);

    ServicioDTO toDTO(Servicio servicio);
    Servicio toEntity(ServicioDTO dto);

    List<Servicio> findByIdWithSedes(Integer id);


}
