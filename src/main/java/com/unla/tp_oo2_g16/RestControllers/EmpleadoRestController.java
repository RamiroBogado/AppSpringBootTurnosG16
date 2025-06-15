package com.unla.tp_oo2_g16.RestControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unla.tp_oo2_g16.models.entities.Empleado;
import com.unla.tp_oo2_g16.services.interfaces.EmpleadoServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoRestController {
    
    private final EmpleadoServiceInterface empleadoService;
    
    @GetMapping
    public List<Empleado> getAllEmpleados() {
        return empleadoService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Integer id) {
        Empleado empleado = empleadoService.findById(id);
        return empleado != null ? ResponseEntity.ok(empleado) : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Empleado> createEmpleado(@RequestBody Empleado empleado) {
        Empleado savedEmpleado = empleadoService.save(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmpleado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Integer id) {
        empleadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/dni/{dni}")
    public ResponseEntity<Empleado> getEmpleadoByDni(@PathVariable String dni) {
        Empleado empleado = empleadoService.findByDni(dni);
        return empleado != null ? ResponseEntity.ok(empleado) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/legajo/{legajo}")
    public ResponseEntity<Empleado> getEmpleadoByLegajo(@PathVariable String legajo) {
        Empleado empleado = empleadoService.findByLegajo(legajo);
        return empleado != null ? ResponseEntity.ok(empleado) : ResponseEntity.notFound().build();
    }
}
