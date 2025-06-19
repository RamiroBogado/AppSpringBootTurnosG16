<<<<<<< HEAD
package com.unla.tp_oo2_g16.services.interfaces;

import java.util.List;

import com.unla.tp_oo2_g16.dtos.EmpleadoDTO;
import com.unla.tp_oo2_g16.models.entities.Empleado;

public interface EmpleadoServiceInterface {
	
	List<Empleado> findAll();
    Empleado findById(Integer id);
    Empleado save(Empleado empleado);
    void deleteById(Integer id);
    Empleado findByDni(String dni);
    Empleado findByLegajo(String legajo);
	List<Empleado> buscarFiltro(String filtro);
	Empleado editado(Empleado empleado);
	
	EmpleadoDTO toDTO(Empleado e);
	Empleado toEntity(EmpleadoDTO dto);
	
	boolean existsByDni(String dni);

    boolean existsByLegajo(String legajo);

    boolean existsByDniAndIdPersonaNot(String dni, Integer idPersona);

    boolean existsByLegajoAndIdPersonaNot(String legajo, Integer idPersona);

}
=======
package com.unla.tp_oo2_g16.services.interfaces;

import java.util.List;

import com.unla.tp_oo2_g16.dtos.EmpleadoDTO;
import com.unla.tp_oo2_g16.models.entities.Empleado;

public interface EmpleadoServiceInterface {
	
	List<Empleado> findAll();
    Empleado findById(Integer id);
    Empleado save(Empleado empleado);
    void deleteById(Integer id);
    Empleado findByDni(String dni);
    Empleado findByLegajo(String legajo);
	List<Empleado> buscarFiltro(String filtro);
	Empleado editado(Empleado empleado);
	
	EmpleadoDTO toDTO(Empleado e);
	Empleado toEntity(EmpleadoDTO dto);
	
	boolean existsByDni(String dni);

    boolean existsByLegajo(String legajo);

    boolean existsByDniAndIdPersonaNot(String dni, Integer idPersona);

    boolean existsByLegajoAndIdPersonaNot(String legajo, Integer idPersona);

}
>>>>>>> 70bfd25
