package com.unla.tp_oo2_g16.RestControllers;

import com.unla.tp_oo2_g16.dtos.ClienteDTO;
import com.unla.tp_oo2_g16.models.entities.Cliente;
import com.unla.tp_oo2_g16.services.interfaces.ClienteServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteRestController {

    private final ClienteServiceInterface clienteService;

    @Operation(summary = "Obtener todos los clientes")
    @GetMapping
    public List<ClienteDTO> getAllClientes() {
        return clienteService.findAll()
                .stream()
                .map(clienteService::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener un cliente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Integer id) {
        Cliente cliente = clienteService.findById(id);
        return cliente != null
            ? ResponseEntity.ok(clienteService.toDTO(cliente))
            : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear un nuevo cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.toEntity(clienteDTO);
        Cliente savedCliente = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.toDTO(savedCliente));
    }

    @Operation(summary = "Eliminar un cliente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

