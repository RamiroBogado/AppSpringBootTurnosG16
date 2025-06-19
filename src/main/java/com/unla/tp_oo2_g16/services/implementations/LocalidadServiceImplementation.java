package com.unla.tp_oo2_g16.services.implementations;

import com.unla.tp_oo2_g16.models.entities.Localidad;
import com.unla.tp_oo2_g16.repositories.LocalidadRepository;
import com.unla.tp_oo2_g16.services.interfaces.LocalidadServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalidadServiceImplementation implements LocalidadServiceInterface {

    @Autowired
    private LocalidadRepository localidadRepository;

    @Override
    public List<Localidad> findAll() {
        return localidadRepository.findAll();
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
}
