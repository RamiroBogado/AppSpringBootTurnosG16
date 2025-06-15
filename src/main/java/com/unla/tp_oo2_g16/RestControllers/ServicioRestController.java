package com.unla.tp_oo2_g16.RestControllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.unla.tp_oo2_g16.models.entities.Servicio;
import com.unla.tp_oo2_g16.services.interfaces.ServicioServiceInterface;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioRestController {
    
    private final ServicioServiceInterface servicioService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable Integer id) {
        Servicio servicio = servicioService.findById(id);
        return servicio != null ? ResponseEntity.ok(servicio) : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Servicio> createServicio(@RequestBody Servicio servicio) {
        Servicio savedServicio = servicioService.save(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedServicio);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Integer id) {
        servicioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
