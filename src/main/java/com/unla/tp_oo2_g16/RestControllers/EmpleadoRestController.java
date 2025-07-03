package com.unla.tp_oo2_g16.RestControllers;

import com.unla.tp_oo2_g16.dtos.EmpleadoDTO;
import com.unla.tp_oo2_g16.models.entities.Empleado;
import com.unla.tp_oo2_g16.services.interfaces.EmpleadoServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoRestController {

    private final EmpleadoServiceInterface empleadoService;

    @Operation(summary = "Obtener todos los empleados")
    @GetMapping
    public List<EmpleadoDTO> getAllEmpleados() {
        return empleadoService.findAll()
                .stream()
                .map(empleadoService::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener un empleado por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> getEmpleadoById(@PathVariable Integer id) {
        Empleado empleado = empleadoService.findById(id);
        return empleado != null
            ? ResponseEntity.ok(empleadoService.toDTO(empleado))
            : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear un nuevo empleado")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Empleado creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<EmpleadoDTO> createEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        Empleado empleado = empleadoService.toEntity(empleadoDTO);
        Empleado savedEmpleado = empleadoService.save(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.toDTO(savedEmpleado));
    }

    @Operation(summary = "Eliminar un empleado por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Empleado eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Integer id) {
        Empleado empleado = empleadoService.findById(id);
        if (empleado == null) {
            return ResponseEntity.notFound().build();
        }
        empleadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
