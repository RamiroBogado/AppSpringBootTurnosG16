package com.unla.tp_oo2_g16.controllers;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import com.unla.tp_oo2_g16.dtos.LocalidadDTO;
import com.unla.tp_oo2_g16.dtos.SedeDTO;
import com.unla.tp_oo2_g16.helpers.ViewRouteHelper;
import com.unla.tp_oo2_g16.models.entities.Sede;
import com.unla.tp_oo2_g16.services.interfaces.LocalidadServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.SedeServiceInterface;

import jakarta.validation.Valid;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/sede")
public class SedeController {

    @Autowired
    private SedeServiceInterface sedeService;
    @Autowired
    LocalidadServiceInterface localidadService;

    @GetMapping("/index")
    public ModelAndView listarSedes(@RequestParam(required=false) String filtro){
        ModelAndView mav = new ModelAndView(ViewRouteHelper.SEDE_INDEX);
        List<Sede> sedes;

        if(filtro==null || filtro.isEmpty()) {
            sedes = sedeService.findAllByOrderByDireccionAsc();
        } else {
            sedes = sedeService.buscarPorDireccionOLocalidad(filtro);
        }

        List<SedeDTO> sedesDTO = sedes.stream().map(sedeService::toDTO).collect(Collectors.toList());

        mav.addObject("sedes", sedesDTO);
        mav.addObject("filtro", filtro);
        return mav;
    }

    @GetMapping("/nuevo")
    public ModelAndView nuevaSede(){
        ModelAndView mav = new ModelAndView(ViewRouteHelper.SEDE_REGISTER);
        mav.addObject("sede", new SedeDTO(null, null, null, null, null));

        List<LocalidadDTO> localidadesDTO = localidadService.findAll().stream()
                            .map(localidad -> new LocalidadDTO(
                                localidad.getIdLocalidad(), 
                                localidad.getNombre(), 
                                localidad.getCp()))
                                .sorted(Comparator.comparing(LocalidadDTO::nombre))
                                .collect(Collectors.toList());


        mav.addObject("localidades", localidadesDTO);
        return mav;
    }

    @PostMapping("/guardar")
    public ModelAndView guardarSede(@Valid @ModelAttribute("sede") SedeDTO sedeDTO, BindingResult result, RedirectAttributes redirectAttributes){
        
        if(result.hasErrors()){
            ModelAndView mav = new ModelAndView(ViewRouteHelper.SEDE_REGISTER);
            mav.addObject("sede", sedeDTO);
            return mav;
        }

        try {
            sedeService.guardarSede(sedeDTO);
            redirectAttributes.addFlashAttribute("success", "Sede guardada exitosamente");
            return new ModelAndView("redirect:/sede/index");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al guardar la sede: " + e.getMessage());
            return new ModelAndView("redirect:/sede/nuevo");
        }

    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarSede(@PathVariable("id") int idAux){
        Sede sede = sedeService.findById(idAux);
        if(sede==null) return new ModelAndView("redirect:/sede/index");

        ModelAndView mav = new ModelAndView(ViewRouteHelper.SEDE_FORM);
        mav.addObject("sede", sedeService.findById(idAux));
        return mav;
    }
    
    @GetMapping("/eliminar/{id}")
    public ModelAndView eliminarSede(@PathVariable("id") Integer idAux) {
        sedeService.deleteById(idAux);
        return new ModelAndView("redirect:/sede/index");
    }


}
