package com.unla.tp_oo2_g16.controllers;

import com.unla.tp_oo2_g16.helpers.ViewRouteHelper;
import com.unla.tp_oo2_g16.models.entities.Empleado;
import com.unla.tp_oo2_g16.services.interfaces.EmpleadoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoServiceInterface empleadoService;

    @GetMapping("/index")
    public ModelAndView listarEmpleados(@RequestParam(required = false) String filtro) {
        ModelAndView mav = new ModelAndView(ViewRouteHelper.EMPLEADO_INDEX);
        if (filtro == null || filtro.isEmpty()) {
            mav.addObject("empleados", empleadoService.findAll());
        } else {
            mav.addObject("empleados", empleadoService.buscarPorNombreODniOCuil(filtro));
        }
        mav.addObject("filtro", filtro);
        return mav;
    }
    
    @GetMapping("/nuevo")
    public ModelAndView nuevoEmpleado() {
        ModelAndView mav = new ModelAndView(ViewRouteHelper.EMPLEADO_REGISTER);
        mav.addObject("empleado", new Empleado());
        return mav;
    }

    @PostMapping("/guardar")
    public ModelAndView guardarEmpleado(@ModelAttribute("empleado") Empleado empleadoAux) {
        empleadoService.editado(empleadoAux);
        return new ModelAndView("redirect:/empleado/index");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarEmpleado(@PathVariable("id") Integer idAux) {
        ModelAndView mav = new ModelAndView(ViewRouteHelper.EMPLEADO_FORM);
        mav.addObject("empleado", empleadoService.findById(idAux));
        return mav;
    }

    @GetMapping("/eliminar/{id}")
    public ModelAndView eliminarEmpleado(@PathVariable("id") Integer idAux) {
        empleadoService.deleteById(idAux);
        return new ModelAndView("redirect:/empleado/index");
    }
}
