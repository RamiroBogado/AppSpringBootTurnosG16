package com.unla.tp_oo2_g16.RestControllers;

import com.unla.tp_oo2_g16.dtos.TurnoGestionDTO;
import com.unla.tp_oo2_g16.models.entities.Turno;
import com.unla.tp_oo2_g16.services.interfaces.TurnoServiceInterface;

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
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoRestController {

    private final TurnoServiceInterface turnoService;

    @Operation(summary = "Obtener todos los turnos")
    @GetMapping
    public List<TurnoGestionDTO> getAllTurnos() {
        return turnoService.findAll()
                .stream()
                .map(turnoService::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener un turno por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Turno encontrado"),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TurnoGestionDTO> getTurnoById(@PathVariable Integer id) {
        Turno turno = turnoService.findById(id);
        return turno != null
            ? ResponseEntity.ok(turnoService.toDTO(turno))
            : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear un nuevo turno")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Turno creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<TurnoGestionDTO> createTurno(@Valid @RequestBody TurnoGestionDTO dto) {
        Turno turno = turnoService.toEntity(dto);
        Turno saved = turnoService.save(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.toDTO(saved));
    }

    @Operation(summary = "Eliminar un turno por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Turno eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurno(@PathVariable Integer id) {
        Turno turno = turnoService.findById(id);
        if (turno == null) {
            return ResponseEntity.notFound().build();
        }
        turnoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
