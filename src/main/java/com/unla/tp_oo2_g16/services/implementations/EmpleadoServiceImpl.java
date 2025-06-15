package com.unla.tp_oo2_g16.services.implementations;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.unla.tp_oo2_g16.models.entities.Empleado;
import com.unla.tp_oo2_g16.repositories.EmpleadoRepository;
import com.unla.tp_oo2_g16.services.interfaces.EmpleadoServiceInterface;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoServiceInterface {
    
    private final EmpleadoRepository empleadoRepository;
    
    @Override
    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }
    
    @Override
    public Empleado findById(Integer id) {
        return empleadoRepository.findById(id).orElse(null);
    }
    
    @Override
    public Empleado save(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }
    
    @Override
    public void deleteById(Integer id) {
        empleadoRepository.deleteById(id);
    }
    
    @Override
    public Empleado findByDni(String dni) {
        return empleadoRepository.findByDni(dni);
    }
    
    @Override
    public Empleado findByLegajo(String legajo) {
        return empleadoRepository.findByLegajo(legajo);
    }
}
