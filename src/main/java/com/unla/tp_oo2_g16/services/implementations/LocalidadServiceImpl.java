package com.unla.tp_oo2_g16.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.unla.tp_oo2_g16.dtos.LocalidadDTO;
import com.unla.tp_oo2_g16.models.entities.Localidad;
import com.unla.tp_oo2_g16.repositories.LocalidadRepository;
import com.unla.tp_oo2_g16.repositories.SedeRepository;
import com.unla.tp_oo2_g16.services.interfaces.LocalidadServiceInterface;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalidadServiceImpl implements LocalidadServiceInterface{


    @Autowired
    private LocalidadRepository localidadRepository;
    @Autowired
    private SedeRepository sedeRepository;

    @Override
    public List<Localidad> findAll() {
        return localidadRepository.findAll();
    }

    @Override
    public List<Localidad> findAllByOrderByCpAsc(){
        return localidadRepository.findAllByOrderByCpAsc();
    }

    @Override
    public Localidad findById(Integer id) {
        return localidadRepository.findById(id).orElse(null);
    }

    @Override
    public Localidad save(Localidad localidad) {
        return localidadRepository.save(localidad);
    }

    @Override
    public void deleteById(Integer id) {
        localidadRepository.deleteById(id);
    }

    @Override
    public Localidad editado(Localidad localidad){
        if(localidad.getIdLocalidad() != null){
            Localidad LocalidadOriginal = localidadRepository.findById(localidad.getIdLocalidad()).orElseThrow();

            // Actualizar campos editables
            LocalidadOriginal.setCp(localidad.getCp());
            LocalidadOriginal.setNombre(localidad.getNombre());

            return localidadRepository.save(LocalidadOriginal);
        }

        // Si es nuevo
        return localidadRepository.save(localidad);
    }

    @Override
    public boolean existsByCp(String cp){
        return localidadRepository.existsByCp(cp);
    }

    @Override
    public boolean existsByCpAndIdLocalidadNot(String cp, Integer idLocalidad){
        return localidadRepository.existsByCpAndIdLocalidadNot(cp, idLocalidad);
    }

    public void borrarLocalidad(Integer idLocalidad){
        Localidad l = localidadRepository.findById(idLocalidad).orElseThrow(() -> new EntityNotFoundException("Localidad no encontrada"));
        if(tieneSedesAsociadas(idLocalidad)) throw new IllegalStateException("No se puede eliminar la localidad porque tiene sedes asociadas");
        localidadRepository.delete(l);
    }

    public boolean tieneSedesAsociadas(Integer idLocalidad){
        return sedeRepository.existsByLocalidad_IdLocalidad(idLocalidad);
    }

    public LocalidadDTO toDTO(Localidad l){
        if(l == null) return null;
        return new LocalidadDTO(l.getIdLocalidad(), l.getNombre(), l.getCp());
    }

    public Localidad toEntity(LocalidadDTO dto){
        if(dto == null) return null;

        Localidad l = new Localidad();
        l.setIdLocalidad(dto.idLocalidad());
        l.setNombre(dto.nombre());
        l.setCp(dto.cp());

        return l;
    }

    public void verificarYEliminarLocalidad(Integer idLocalidad) {
        // Verificar si existe la localidad
        Localidad localidad = localidadRepository.findById(idLocalidad)
            .orElseThrow(() -> new EntityNotFoundException("Localidad no encontrada"));
        
        // Verificar si tiene sedes asociadas
        if (sedeRepository.existsByLocalidad_IdLocalidad(idLocalidad)) {
            throw new DataIntegrityViolationException(
                "Existen sedes asociadas a esta localidad");
        }
        
        // Si pasa las validaciones, eliminar
        localidadRepository.delete(localidad);
    }

}
