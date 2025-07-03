package com.unla.tp_oo2_g16.RestControllers;

import com.unla.tp_oo2_g16.dtos.ServicioDTO;
import com.unla.tp_oo2_g16.models.entities.Servicio;
import com.unla.tp_oo2_g16.services.interfaces.ServicioServiceInterface;

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
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioRestController {

    private final ServicioServiceInterface servicioService;

    @Operation(summary = "Obtener todos los servicios")
    @GetMapping
    public List<ServicioDTO> getAllServicios() {
        return servicioService.findAll()
                .stream()
                .map(servicioService::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener un servicio por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servicio encontrado"),
        @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServicioDTO> getServicioById(@PathVariable Integer id) {
        Servicio servicio = servicioService.findById(id);
        return servicio != null
            ? ResponseEntity.ok(servicioService.toDTO(servicio))
            : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear un nuevo servicio")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Servicio creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ServicioDTO> createServicio(@Valid @RequestBody ServicioDTO servicioDTO) {
        Servicio servicio = servicioService.toEntity(servicioDTO);
        Servicio savedServicio = servicioService.save(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.toDTO(savedServicio));
    }

    @Operation(summary = "Eliminar un servicio por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Servicio eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Integer id) {
        Servicio servicio = servicioService.findById(id);
        if (servicio == null) {
            return ResponseEntity.notFound().build();
        }
        servicioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
