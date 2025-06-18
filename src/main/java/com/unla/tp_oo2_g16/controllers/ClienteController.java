
package com.unla.tp_oo2_g16.controllers;

import com.unla.tp_oo2_g16.helpers.ViewRouteHelper;
import com.unla.tp_oo2_g16.models.entities.Cliente;
import com.unla.tp_oo2_g16.services.interfaces.ClienteServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteServiceInterface clienteService;

    @GetMapping("/index")
    public ModelAndView listarClientes(@RequestParam(required = false) String filtro) {
        ModelAndView mav = new ModelAndView(ViewRouteHelper.CLIENTE_INDEX);
        if (filtro == null || filtro.isEmpty()) {
            mav.addObject("clientes", clienteService.findAll());
        } else {
            mav.addObject("clientes", clienteService.buscarPorNombreODniOCuil(filtro));
        }
        mav.addObject("filtro", filtro);
        return mav;
    }
    
    @GetMapping("/nuevo")
    public ModelAndView nuevoCliente() {
        ModelAndView mav = new ModelAndView(ViewRouteHelper.CLIENTE_REGISTER);
        mav.addObject("cliente", new Cliente());
        return mav;
    }


    @PostMapping("/guardar")
    public ModelAndView guardarCliente(@ModelAttribute("cliente") Cliente clienteAux) {
    	   	
        clienteService.editado(clienteAux);
        return new ModelAndView("redirect:/cliente/index");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarCliente(@PathVariable("id") Integer idAux) {
        ModelAndView mav = new ModelAndView(ViewRouteHelper.CLIENTE_FORM);
        mav.addObject("cliente", clienteService.findById(idAux));
        return mav;
    }

    @GetMapping("/eliminar/{id}")
    public ModelAndView eliminarCliente(@PathVariable("id") Integer idAux) {
        clienteService.deleteById(idAux);
        return new ModelAndView("redirect:/cliente/index");
    }
}
