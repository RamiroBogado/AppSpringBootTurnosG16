package com.unla.tp_oo2_g16.services.implementations;

import org.springframework.stereotype.Service;
import com.unla.tp_oo2_g16.models.entities.Persona;
import com.unla.tp_oo2_g16.repositories.PersonaRepository;
import com.unla.tp_oo2_g16.services.interfaces.PersonaServiceInterface;

import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaServiceInterface {
    
    private PersonaRepository personaRepository;
    
    @Override
    public List<Persona> findAll() {
        return personaRepository.findAll();
    }
    
    @Override
    public Persona findById(Integer id) {
        return personaRepository.findById(id).orElse(null);
    }
    
    @Override
    public Persona save(Persona persona) {
        return personaRepository.save(persona);
    }
    
    @Override
    public void deleteById(Integer id) {
        personaRepository.deleteById(id);
    }
    
}
