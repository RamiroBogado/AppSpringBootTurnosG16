package com.unla.tp_oo2_g16.services.implementations;

import org.springframework.stereotype.Service;

import com.unla.tp_oo2_g16.models.entities.Localidad;
import com.unla.tp_oo2_g16.repositories.LocalidadRepository;
import com.unla.tp_oo2_g16.services.interfaces.LocalidadServiceInterface;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalidadServiceImpl implements LocalidadServiceInterface{

    private final LocalidadRepository localidadRepository;

    @Override
    public Localidad findById(int id){
        return localidadRepository.findById(id);
    }

    @Override
    public Localidad findByLocalidad(String localidad){
        return localidadRepository.findByLocalidad(localidad);
    }

    @Override
    public Localidad save(Localidad localidad){
        return localidadRepository.save(localidad);
    }


}
