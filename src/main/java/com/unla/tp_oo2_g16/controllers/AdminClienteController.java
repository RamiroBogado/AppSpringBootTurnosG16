package com.unla.tp_oo2_g16.controllers;

import com.unla.tp_oo2_g16.dtos.ClienteDTO;
import com.unla.tp_oo2_g16.helpers.ViewRouteHelper;
import com.unla.tp_oo2_g16.models.entities.Cliente;
import com.unla.tp_oo2_g16.services.interfaces.ClienteServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cliente")
public class AdminClienteController {

    @Autowired
    private ClienteServiceInterface clienteService;

    

    @GetMapping("/index")
    public ModelAndView listarClientes(@RequestParam(required = false) String filtro,
                                       @RequestParam(required = false) String concurrente) {
        ModelAndView mav = new ModelAndView(ViewRouteHelper.CLIENTE_INDEX);
        List<Cliente> clientes;

        if ((filtro == null || filtro.isEmpty()) && concurrente == null) {
            clientes = clienteService.findAll();
        } else {
            clientes = clienteService.buscarPorFiltroYConcurrente(filtro, concurrente);
        }

        List<ClienteDTO> clientesDTO = clientes.stream()
                                               .map(clienteService::toDTO)
                                               .collect(Collectors.toList());

        mav.addObject("clientes", clientesDTO);
        mav.addObject("filtro", filtro);
        mav.addObject("concurrente", concurrente);
        return mav;
    }



    @GetMapping("/nuevo")
    public String nuevoCliente(Model model) {
        model.addAttribute("cliente", new ClienteDTO(null, "", "", "", null, false, "", ""));
        return ViewRouteHelper.CLIENTE_REGISTER;
    }

    @PostMapping("/guardar")
    public String guardarCliente(@Valid @ModelAttribute("cliente") ClienteDTO clienteDTO, BindingResult result, Model model) {

        // Validación de campos del DTO
        if (result.hasErrors()) {
            return ViewRouteHelper.CLIENTE_REGISTER;
        }

        // Validación manual de duplicados
        if (clienteService.existsByDni(clienteDTO.dni()) && clienteDTO.idPersona() == null) {
            model.addAttribute("error", "Ya existe un cliente con ese DNI.");
            return ViewRouteHelper.CLIENTE_REGISTER;
        }

        if (clienteService.existsByCuil(clienteDTO.cuil()) && clienteDTO.idPersona() == null) {
            model.addAttribute("error", "Ya existe un cliente con ese CUIL.");
            return ViewRouteHelper.CLIENTE_REGISTER;
        }

        if (clienteService.existsByEmail(clienteDTO.emailUser()) && clienteDTO.idPersona() == null) {
            model.addAttribute("error", "Ya existe un usuario con ese email.");
            return ViewRouteHelper.CLIENTE_REGISTER;
        }

        // Persistencia
        Cliente cliente = clienteService.toEntity(clienteDTO);
        clienteService.editado(cliente);
        return "redirect:/cliente/index";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Integer idAux) {
        clienteService.deleteById(idAux);
        return "redirect:/cliente/index";
    }
}
