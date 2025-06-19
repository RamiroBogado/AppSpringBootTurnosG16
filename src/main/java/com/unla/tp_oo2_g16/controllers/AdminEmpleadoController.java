package com.unla.tp_oo2_g16.controllers;

import com.unla.tp_oo2_g16.dtos.EmpleadoDTO;
import com.unla.tp_oo2_g16.helpers.ViewRouteHelper;
import com.unla.tp_oo2_g16.models.entities.Empleado;
import com.unla.tp_oo2_g16.services.interfaces.EmpleadoServiceInterface;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/empleado")
public class AdminEmpleadoController {

    @Autowired
    private EmpleadoServiceInterface empleadoService;

    @GetMapping("/index")
    public ModelAndView listarEmpleados(@RequestParam(required = false) String filtro) {
        ModelAndView mav = new ModelAndView(ViewRouteHelper.EMPLEADO_INDEX);
        List<Empleado> empleados;

        if (filtro == null || filtro.isEmpty()) {
            empleados = empleadoService.findAll();
        } else {
            empleados = empleadoService.buscarFiltro(filtro);
        }

        // Convertir la lista de entidades a DTOs
        List<EmpleadoDTO> empleadosDTO = empleados.stream()
            .map(empleadoService::toDTO)
            .collect(Collectors.toList());

        mav.addObject("empleados", empleadosDTO);
        mav.addObject("filtro", filtro);
        return mav;
    }

    
    @GetMapping("/nuevo")
    public ModelAndView nuevoEmpleado() {
        ModelAndView mav = new ModelAndView(ViewRouteHelper.EMPLEADO_REGISTER);
        mav.addObject("empleado", new EmpleadoDTO(null, "", "", "", "", ""));
        return mav;
    }


    @PostMapping("/guardar")
    public ModelAndView guardarEmpleado(@Valid @ModelAttribute("empleado") EmpleadoDTO empleadoDTO,
                                       BindingResult result) {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView(ViewRouteHelper.EMPLEADO_REGISTER);
            mav.addObject("empleado", empleadoDTO);
            return mav;
        }

        // Si es nuevo (idPersona null) verificamos duplicados
        if (empleadoDTO.idPersona() == null) {
            if (empleadoService.existsByDni(empleadoDTO.dni())) {
                result.rejectValue("dni", "error.empleado", "Ya existe un empleado con este DNI");
            }
            if (empleadoService.existsByLegajo(empleadoDTO.legajo())) {
                result.rejectValue("legajo", "error.empleado", "Ya existe un empleado con este Legajo");
            }
        } else {
            // Si es edición, validar que no existan duplicados en otro registro distinto al actual
            if (empleadoService.existsByDniAndIdPersonaNot(empleadoDTO.dni(), empleadoDTO.idPersona())) {
                result.rejectValue("dni", "error.empleado", "Ya existe un empleado con este DNI");
            }
            if (empleadoService.existsByDniAndIdPersonaNot(empleadoDTO.legajo(), empleadoDTO.idPersona())) {
                result.rejectValue("legajo", "error.empleado", "Ya existe un empleado con este Legajo");
            }
        }

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView(ViewRouteHelper.EMPLEADO_REGISTER);
            mav.addObject("empleado", empleadoDTO);
            return mav;
        }

        Empleado empleado = empleadoService.toEntity(empleadoDTO);
        empleadoService.editado(empleado);

        return new ModelAndView("redirect:/empleado/index");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarEmpleado(@PathVariable("id") Integer idAux) {
        Empleado empleado = empleadoService.findById(idAux);
        if (empleado == null) {
            return new ModelAndView("redirect:/empleado/index");
        }
        EmpleadoDTO dto = empleadoService.toDTO(empleado);
        ModelAndView mav = new ModelAndView(ViewRouteHelper.EMPLEADO_REGISTER);
        mav.addObject("empleado", dto);
        return mav;
    }


    @GetMapping("/eliminar/{id}")
    public ModelAndView eliminarEmpleado(@PathVariable("id") Integer idAux) {
        empleadoService.deleteById(idAux);
        return new ModelAndView("redirect:/empleado/index");
    }
}
