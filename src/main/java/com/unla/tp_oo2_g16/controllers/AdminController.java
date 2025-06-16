package com.unla.tp_oo2_g16.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // Página principal del admin
    @GetMapping("/panel")
    public String adminIndex() {
        return "admin/index";  // Thymeleaf: src/main/resources/templates/admin/index.html
    }

    // Ejemplo: listar todos los turnos (podés agregar más servicios para admin)
    @GetMapping("/turnos")
    public String listarTurnos() {
        // acá cargarías lista de turnos y los agregas al model para la vista
        return "admin/turnos"; // plantilla admin/turnos.html
    }

    // Más métodos para CRUD de servicios, usuarios, sedes, etc.
}
