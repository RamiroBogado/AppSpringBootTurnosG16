package com.unla.tp_oo2_g16.controllers;

import com.unla.tp_oo2_g16.dtos.TurnoGestionDTO;
import com.unla.tp_oo2_g16.models.entities.Turno;
import com.unla.tp_oo2_g16.services.interfaces.ClienteServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.ServicioServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.TurnoServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/adminTurno")
public class AdminTurnoController {

    @Autowired
    private TurnoServiceInterface turnoService;

    @Autowired
    private ClienteServiceInterface clienteService;

    @Autowired
    private ServicioServiceInterface servicioService;

    // LISTAR - ya tenés este método
    @GetMapping("/index")
    public ModelAndView listarTurnos(
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) String estado) {
        ModelAndView mav = new ModelAndView("adminTurno/index");

        List<Turno> turnos = turnoService.buscarPorFiltroYEstado(filtro, estado);
        List<TurnoGestionDTO> turnosDTO = turnos.stream()
                .map(turnoService::toDTO)
                .toList();

        mav.addObject("turnos", turnosDTO);
        mav.addObject("filtro", filtro);
        mav.addObject("estado", estado);
        return mav;
    }

    // NUEVO - muestra el formulario vacío para crear un turno
    @GetMapping("/nuevo")
    public ModelAndView nuevoTurno() {
        ModelAndView mav = new ModelAndView("adminTurno/register");

        TurnoGestionDTO turnoDTO = new TurnoGestionDTO(
            null,         // idTurno
            null,         // idCliente
            null,         // idServicio
            null,         // idSede
            "",           // fechaHora
            "PENDIENTE",  // estado
            null,         // codigoTurno
            "",           // nombreCliente
            "",           // nombreServicio
            "",           // nombreSede
            ""            // nombreLocalidad
        );

        mav.addObject("turno", turnoDTO);
        mav.addObject("clientes", clienteService.findAll());
        mav.addObject("servicios", servicioService.findAll());
        return mav;
    }

    @PostMapping("/guardar")
    public ModelAndView guardarTurno(
        @Valid @ModelAttribute("turno") TurnoGestionDTO turnoDTO,
        BindingResult bindingResult) {

        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {
            // Si hay errores, volvemos al formulario y mostramos mensajes
            mav.setViewName("adminTurno/register");
            mav.addObject("clientes", clienteService.findAll());
            mav.addObject("servicios", servicioService.findAll());

            // Si tenés sedes para elegir, también agregalas
            // Por ahora asumimos que la sede viene solo para mostrar, no para elegir
            return mav;
        }

        // Convertir DTO a entidad
        Turno turno = turnoService.toEntity(turnoDTO);
        turnoService.save(turno);

        // Redirigir al index
        mav.setViewName("redirect:/adminTurno/index");
        return mav;
    }



    // EDITAR - muestra el formulario con datos cargados para editar
    @GetMapping("/editar/{id}")
    public ModelAndView editarTurno(@PathVariable Integer id) {
        Turno turno = turnoService.findById(id);
        if (turno == null) {
            return new ModelAndView("redirect:/adminTurno/index");
        }
        TurnoGestionDTO dto = turnoService.toDTO(turno);

        ModelAndView mav = new ModelAndView("adminTurno/register");
        mav.addObject("turno", dto);
        mav.addObject("clientes", clienteService.findAll());
        mav.addObject("servicios", servicioService.findAll());
        return mav;
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public ModelAndView eliminarTurno(@PathVariable Integer id) {
        turnoService.deleteById(id);
        return new ModelAndView("redirect:/adminTurno/index");
    }
}
