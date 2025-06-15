package com.unla.tp_oo2_g16.services.implementations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
