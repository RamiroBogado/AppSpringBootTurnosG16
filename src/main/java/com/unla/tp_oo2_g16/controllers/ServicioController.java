package com.unla.tp_oo2_g16.controllers;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.tp_oo2_g16.dtos.ServicioDTO;
import com.unla.tp_oo2_g16.helpers.ViewRouteHelper;
import com.unla.tp_oo2_g16.models.entities.Servicio;
import com.unla.tp_oo2_g16.services.interfaces.ServicioServiceInterface;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/servicio")
public class ServicioController{

    @Autowired
    ServicioServiceInterface servicioService;

    @GetMapping("/index")
    public ModelAndView listarServicios(@RequestParam(required=false) String filtro){
        ModelAndView mav = new ModelAndView(ViewRouteHelper.SERVICIO_INDEX);
        List<Servicio> servicios = servicioService.findAllByOrderByNombreAsc();
        
        /*if(filtro==null || filtro.isEmpty()){
            servicios = servicioService.findAll();
        }*/

        List<ServicioDTO> serviciosDTO = servicios.stream().map(servicioService::toDTO).collect(Collectors.toList());

        mav.addObject("servicios", serviciosDTO);
        mav.addObject("filtro", filtro);
        return mav;
    }

    @GetMapping("/nuevo")
    public ModelAndView nuevoServicio(){
        ModelAndView mav = new ModelAndView(ViewRouteHelper.SERVICIO_REGISTER);
        mav.addObject("servicio", new ServicioDTO(null, "", "", null));
        mav.addObject("duraciones", ServicioDTO.getOpcionesDuracion());
        return mav;
    }
    
    @PostMapping("/guardar")
    public ModelAndView guardarServicio(@Valid @ModelAttribute("servicio") ServicioDTO servicioDTO, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            ModelAndView mav = new ModelAndView(ViewRouteHelper.SERVICIO_REGISTER);
            mav.addObject("servicio", servicioDTO);
            return mav;
        }

        if(servicioDTO.idServicio()==null){
            if(servicioService.existsByNombre(servicioDTO.nombre())) {
                result.rejectValue("nombre", "error.servicio.nombre", "Ya existe un servicio con ese nombre");
            }
        } else {
            if(servicioService.existsByNombreAndIdServicioNot(servicioDTO.nombre(), servicioDTO.idServicio())){
                result.rejectValue("nombre", "error.servicio.nombre", "Ya existe un servicio con ese nombre");
            }
        }

        if(result.hasErrors()){
            ModelAndView mav = new ModelAndView(ViewRouteHelper.SERVICIO_REGISTER);
            mav.addObject("servicio", servicioDTO);
            return mav;
        }

        Servicio servicio = servicioService.toEntity(servicioDTO);
        servicioService.save(servicio);

        return new ModelAndView("redirect:/servicio/index");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarServicio(@PathVariable("id") int idAux){
        Servicio servicio = servicioService.findById(idAux);
        if(servicio==null) return new ModelAndView("redirect:/servicio/index");

        ServicioDTO dto = servicioService.toDTO(servicio);
        ModelAndView mav = new ModelAndView(ViewRouteHelper.SERVICIO_FORM);
        mav.addObject("servicio", dto);
        mav.addObject("duraciones", ServicioDTO.getOpcionesDuracion());
        return mav;
    }
    
    @GetMapping("/eliminar/{id}")
    public ModelAndView eliminarServicio(@PathVariable("id") int idAux, RedirectAttributes redirectAttributes){
        servicioService.deleteById(idAux);
        ModelAndView mav = new ModelAndView("redirect:/servicio/index");
        redirectAttributes.addFlashAttribute("success", "Servicio eliminado correctamente");
        return mav;
    }
    
    
    
}