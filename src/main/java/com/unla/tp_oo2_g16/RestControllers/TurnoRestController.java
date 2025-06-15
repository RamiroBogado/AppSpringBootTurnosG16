package com.unla.tp_oo2_g16.RestControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unla.tp_oo2_g16.models.entities.Turno;
import com.unla.tp_oo2_g16.services.interfaces.TurnoServiceInterface;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoRestController {
    
    private final TurnoServiceInterface turnoService;
    
    @GetMapping
    public List<Turno> getAllTurnos() {
        return turnoService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Turno> getTurnoById(@PathVariable Integer id) {
        Turno turno = turnoService.findById(id);
        return turno != null ? ResponseEntity.ok(turno) : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Turno> createTurno(@RequestBody Turno turno) {
        Turno savedTurno = turnoService.save(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTurno);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurno(@PathVariable Integer id) {
        turnoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/cliente/{clienteId}")
    public List<Turno> getTurnosByCliente(@PathVariable Integer clienteId) {
        return turnoService.findByCliente(clienteId);
    }
       
    @GetMapping("/servicio/{servicioId}")
    public List<Turno> getTurnosByServicio(@PathVariable Integer servicioId) {
        return turnoService.findByServicio(servicioId);
    }
    
    @GetMapping("/fecha")
    public List<Turno> getTurnosByFechaRange(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        return turnoService.findByFechaBetween(inicio, fin);
    }
    
    @GetMapping("/estado/{estado}")
    public List<Turno> getTurnosByEstado(@PathVariable String estado) {
        return turnoService.findByEstado(estado);
    }
}