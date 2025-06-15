package com.unla.tp_oo2_g16.services.interfaces;

import java.util.List;
import com.unla.tp_oo2_g16.models.entities.Empleado;

public interface EmpleadoServiceInterface {
	
	List<Empleado> findAll();
    Empleado findById(Integer id);
    Empleado save(Empleado empleado);
    void deleteById(Integer id);
    Empleado findByDni(String dni);
    Empleado findByLegajo(String legajo);

}
