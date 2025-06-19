<<<<<<< HEAD
package com.unla.tp_oo2_g16.services.implementations;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.unla.tp_oo2_g16.dtos.EmpleadoDTO;
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
    
    @Override
    public List<Empleado> buscarFiltro(String filtro) {
        return empleadoRepository.buscarPorFiltro(filtro);
    }
    
    @Override
    public Empleado editado(Empleado empleado) {
        if (empleado.getIdPersona() != null) {
        	Empleado EmpleadoOriginal = empleadoRepository.findById(empleado.getIdPersona()).orElseThrow();

            // Actualizás solo los campos editables
        	EmpleadoOriginal.setNombre(empleado.getNombre());
        	EmpleadoOriginal.setApellido(empleado.getApellido());
        	EmpleadoOriginal.setDni(empleado.getDni());
        	EmpleadoOriginal.setLegajo(empleado.getLegajo());
        	EmpleadoOriginal.setPuesto(empleado.getPuesto());

            // El user ya está asociado y no se toca

            return empleadoRepository.save(EmpleadoOriginal);
        }

        // Es un nuevo cliente
        return empleadoRepository.save(empleado);
    }
    
    public EmpleadoDTO toDTO(Empleado e) {
        if (e == null) return null;

        return new EmpleadoDTO(
            e.getIdPersona(),
            e.getNombre(),
            e.getApellido(),
            e.getDni(),
            e.getLegajo(),
            e.getPuesto()
        );
    }
    
    public Empleado toEntity(EmpleadoDTO dto) {
        if (dto == null) return null;

        Empleado e = new Empleado();
        e.setIdPersona(dto.idPersona());
        e.setNombre(dto.nombre());
        e.setApellido(dto.apellido());
        e.setDni(dto.dni());
        e.setLegajo(dto.legajo());
        e.setPuesto(dto.puesto());

        return e;
    }
    
    @Override
    public boolean existsByDni(String dni) {
        return empleadoRepository.existsByDni(dni);
    }

    @Override
    public boolean existsByLegajo(String legajo) {
        return empleadoRepository.existsByLegajo(legajo);
    }

    @Override
    public boolean existsByDniAndIdPersonaNot(String dni, Integer idPersona) {
        return empleadoRepository.existsByDniAndIdPersonaNot(dni, idPersona);
    }

    @Override
    public boolean existsByLegajoAndIdPersonaNot(String legajo, Integer idPersona) {
        return empleadoRepository.existsByLegajoAndIdPersonaNot(legajo, idPersona);
    }


}
=======
package com.unla.tp_oo2_g16.services.implementations;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.unla.tp_oo2_g16.dtos.EmpleadoDTO;
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
    
    @Override
    public List<Empleado> buscarFiltro(String filtro) {
        return empleadoRepository.buscarPorFiltro(filtro);
    }
    
    @Override
    public Empleado editado(Empleado empleado) {
        if (empleado.getIdPersona() != null) {
        	Empleado EmpleadoOriginal = empleadoRepository.findById(empleado.getIdPersona()).orElseThrow();

            // Actualizás solo los campos editables
        	EmpleadoOriginal.setNombre(empleado.getNombre());
        	EmpleadoOriginal.setApellido(empleado.getApellido());
        	EmpleadoOriginal.setDni(empleado.getDni());
        	EmpleadoOriginal.setLegajo(empleado.getLegajo());
        	EmpleadoOriginal.setPuesto(empleado.getPuesto());

            // El user ya está asociado y no se toca

            return empleadoRepository.save(EmpleadoOriginal);
        }

        // Es un nuevo cliente
        return empleadoRepository.save(empleado);
    }
    
    public EmpleadoDTO toDTO(Empleado e) {
        if (e == null) return null;

        return new EmpleadoDTO(
            e.getIdPersona(),
            e.getNombre(),
            e.getApellido(),
            e.getDni(),
            e.getLegajo(),
            e.getPuesto()
        );
    }
    
    public Empleado toEntity(EmpleadoDTO dto) {
        if (dto == null) return null;

        Empleado e = new Empleado();
        e.setIdPersona(dto.idPersona());
        e.setNombre(dto.nombre());
        e.setApellido(dto.apellido());
        e.setDni(dto.dni());
        e.setLegajo(dto.legajo());
        e.setPuesto(dto.puesto());

        return e;
    }
    
    @Override
    public boolean existsByDni(String dni) {
        return empleadoRepository.existsByDni(dni);
    }

    @Override
    public boolean existsByLegajo(String legajo) {
        return empleadoRepository.existsByLegajo(legajo);
    }

    @Override
    public boolean existsByDniAndIdPersonaNot(String dni, Integer idPersona) {
        return empleadoRepository.existsByDniAndIdPersonaNot(dni, idPersona);
    }

    @Override
    public boolean existsByLegajoAndIdPersonaNot(String legajo, Integer idPersona) {
        return empleadoRepository.existsByLegajoAndIdPersonaNot(legajo, idPersona);
    }


}
>>>>>>> 70bfd25
