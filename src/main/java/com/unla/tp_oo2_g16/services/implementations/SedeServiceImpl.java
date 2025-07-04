package com.unla.tp_oo2_g16.services.implementations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.tp_oo2_g16.dtos.SedeDTO;
import com.unla.tp_oo2_g16.models.entities.Sede;
import com.unla.tp_oo2_g16.repositories.SedeRepository;
import com.unla.tp_oo2_g16.services.interfaces.SedeServiceInterface;

@Service
public class SedeServiceImpl implements SedeServiceInterface {

    @Autowired
    private SedeRepository sedeRepository;

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

    public SedeDTO toDTO(Sede s){
        if(s == null) return null;

        return new SedeDTO(s.getIdSede(), s.getDireccion(), s.getLocalidad());
    }

    public Sede toEntity(SedeDTO dto){
        if(dto == null) return null;
        Sede s = new Sede();
        s.setIdSede(dto.idSede());
        s.setDireccion(dto.direccion());
        s.setLocalidad(dto.localidad());
        return s;
    }
}
