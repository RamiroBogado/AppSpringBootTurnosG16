package com.unla.tp_oo2_g16.services.implementations;

import lombok.RequiredArgsConstructor;

import com.unla.tp_oo2_g16.dtos.ServicioDTO;
import com.unla.tp_oo2_g16.models.entities.Sede;
import com.unla.tp_oo2_g16.models.entities.Servicio;
import com.unla.tp_oo2_g16.repositories.ServicioRepository;
import com.unla.tp_oo2_g16.services.interfaces.ServicioServiceInterface;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioServiceImpl implements ServicioServiceInterface {
    
    private final ServicioRepository servicioRepository;
    
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    
    @Override
    public Servicio findById(Integer id) {
        return servicioRepository.findById(id).orElse(null);
    }
      
    @Override
    public Servicio save(Servicio servicio) {
        return servicioRepository.save(servicio);
    }
    
    @Override
    public void deleteById(Integer id) {
        servicioRepository.deleteById(id);
    }

    @Override
    public Set<Sede> findSedesByServicio(Servicio servicio) {
        return servicio.getSedes();
    }


    @Override
    public ServicioDTO toDTO(Servicio servicio) {
        if (servicio == null) return null;
        return new ServicioDTO(servicio.getIdServicio(), servicio.getNombre());
    }

    @Override
    public Servicio toEntity(ServicioDTO dto) {
        if (dto == null) return null;
        Servicio servicio = new Servicio();
        servicio.setIdServicio(dto.idServicio());
        servicio.setNombre(dto.nombre());
        return servicio;
    }


    
}
