package com.unla.tp_oo2_g16.services.implementations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.tp_oo2_g16.dtos.SedeDTO;
import com.unla.tp_oo2_g16.models.entities.Localidad;
import com.unla.tp_oo2_g16.models.entities.Sede;
import com.unla.tp_oo2_g16.repositories.LocalidadRepository;
import com.unla.tp_oo2_g16.repositories.SedeRepository;
import com.unla.tp_oo2_g16.services.interfaces.SedeServiceInterface;

@Service
public class SedeServiceImpl implements SedeServiceInterface {

    @Autowired
    private SedeRepository sedeRepository;
    @Autowired
    private LocalidadRepository localidadRepository;

    @Override
    public List<Sede> findAll() {
        return sedeRepository.findAll();
    }
    
    public Set<Sede> findAllSet(){
    	
    	return new HashSet<>(sedeRepository.findAll());
    	
    }

    @Override
    public Sede findById(Integer id) {
        return sedeRepository.findById(id).orElse(null);
    }

    @Override
    public Sede save(Sede sede) {
        return sedeRepository.save(sede);
    }

    @Override
    public void deleteById(Integer id) {
        sedeRepository.deleteById(id);
    }

    @Override
    public List<Sede> buscarPorDireccionOLocalidad(String filtro){
        return sedeRepository.buscarPorFiltro(filtro);
    }

    @Override
    public Sede editado(Sede sede) {
        if (sede.getIdSede() != null) {
        	Sede sedeOriginal = sedeRepository.findById(sede.getIdSede()).orElseThrow();

            // Actualizás solo los campos editables
        	sedeOriginal.setDireccion(sede.getDireccion());
        	sedeOriginal.setLocalidad(sede.getLocalidad());

            return sedeRepository.save(sedeOriginal);
        }

        // Es un nuevo cliente
        return sedeRepository.save(sede);
    }

    public List<Sede> findAllByOrderByDireccionAsc(){
        return sedeRepository.findAllByOrderByDireccionAsc();
    }

    public void crearSede(SedeDTO sedeDTO){
        Localidad l = localidadRepository.findById(sedeDTO.idLocalidad()).orElseThrow(() -> new IllegalArgumentException("Localidad inválida"));
        Sede sede = new Sede();
        sede.setDireccion(sedeDTO.direccion());
        sede.setLocalidad(l);
        sedeRepository.save(sede);
    }

    public SedeDTO toDTO(Sede s){
        if(s == null) return null;

        SedeDTO dto = new SedeDTO(s.getIdSede(), s.getDireccion(), s.getLocalidad().getIdLocalidad(), s.getLocalidad().getNombre(), s.getLocalidad().getCp());
        return dto;
    }

    public Sede toEntity(SedeDTO dto){
        if(dto == null) return null;

        Sede s = new Sede();
        s.setIdSede(dto.idSede());
        s.setDireccion(dto.direccion());
        if (dto.idLocalidad() != null) {
            Localidad localidad = new Localidad();
            localidad.setIdLocalidad(dto.idLocalidad());
            s.setLocalidad(localidad);
        }
        return s;
    }

    public void guardarSede(SedeDTO sedeDTO) {
        Localidad localidad = localidadRepository.findById(sedeDTO.idLocalidad())
                .orElseThrow(() -> new IllegalArgumentException("Localidad no encontrada"));
        
        Sede sede = new Sede();
        sede.setIdSede(sedeDTO.idSede());
        sede.setDireccion(sedeDTO.direccion());
        sede.setLocalidad(localidad);
        
        sedeRepository.save(sede);
    }

    public void eliminarSede(Integer id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));
        
        // Verificación adicional para restricciones de integridad
        /*if (tieneRegistrosAsociados(id)) {
            throw new DataIntegrityViolationException(
                "Existen registros asociados a esta sede");
        }*/
        
        sedeRepository.delete(sede);
    }


}
