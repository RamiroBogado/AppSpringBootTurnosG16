package com.unla.tp_oo2_g16.RestControllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unla.tp_oo2_g16.models.entities.Persona;
import com.unla.tp_oo2_g16.services.interfaces.PersonaServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaRestController {
    
    private PersonaServiceInterface personaService;
    
    @GetMapping
    public List<Persona> getAllPersonas() {
        return personaService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Integer id) {
        Persona persona = personaService.findById(id);
        return persona != null ? ResponseEntity.ok(persona) : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public Persona createPersona(@RequestBody Persona persona) {
        return personaService.save(persona);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Integer id) {
        personaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
