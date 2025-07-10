package com.unla.tp_oo2_g16.controllers;

import com.unla.tp_oo2_g16.dtos.TurnoGestionDTO;
import com.unla.tp_oo2_g16.helpers.ViewRouteHelper;
import com.unla.tp_oo2_g16.models.entities.Turno;
import com.unla.tp_oo2_g16.services.interfaces.ClienteServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.ServicioServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.SedeServiceInterface;
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

    @Autowired
    private SedeServiceInterface sedeService;

    @GetMapping("/index")
    public ModelAndView listarTurnos(
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) String estado) {
        ModelAndView mav = new ModelAndView(ViewRouteHelper.ADMINTURNO_INDEX);

        List<Turno> turnos = turnoService.buscarPorFiltroYEstado(filtro, estado);
        List<TurnoGestionDTO> turnosDTO = turnos.stream()
                .map(turnoService::toDTO)
                .toList();

        mav.addObject("turnos", turnosDTO);
        mav.addObject("filtro", filtro);
        mav.addObject("estado", estado);
        return mav;
    }

    @GetMapping("/nuevo")
    public ModelAndView nuevoTurno() {
        ModelAndView mav = new ModelAndView(ViewRouteHelper.ADMINTURNO_REGISTER);

        TurnoGestionDTO turnoDTO = new TurnoGestionDTO(
            null, null, null, null, "", "PENDIENTE", null, "", "", "", ""
        );

        mav.addObject("turno", turnoDTO);
        mav.addObject("clientes", clienteService.findAll());
        mav.addObject("servicios", servicioService.findAll());
        mav.addObject("sedes", sedeService.findAll());

        return mav;
    }

    @PostMapping("/guardar")
    public ModelAndView guardarTurno(
        @Valid @ModelAttribute("turno") TurnoGestionDTO turnoDTO,
        BindingResult bindingResult) {

        ModelAndView mav = new ModelAndView();
        
        Turno turno = turnoService.toEntity(turnoDTO);
        
        if(turno.getEstado() == null) {
        	
        	turno.setEstado("PENDIENTE");
        }
        turnoService.save(turno);

        mav.setViewName("redirect:/adminTurno/index");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarTurno(@PathVariable Integer id) {
        Turno turno = turnoService.findById(id);
        if (turno == null) {
            return new ModelAndView("redirect:/adminTurno/index");
        }
        TurnoGestionDTO dto = turnoService.toDTO(turno);

        ModelAndView mav = new ModelAndView(ViewRouteHelper.ADMINTURNO_FORM);
        mav.addObject("turno", dto);
        mav.addObject("clientes", clienteService.findAll());
        mav.addObject("servicios", servicioService.findAll());
        mav.addObject("sedes", sedeService.findAll());
        return mav;
    }

    @GetMapping("/eliminar/{id}")
    public ModelAndView eliminarTurno(@PathVariable Integer id) {
        turnoService.deleteById(id);
        return new ModelAndView("redirect:/adminTurno/index");
    }
}
