package com.unla.tp_oo2_g16.controllers;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.unla.tp_oo2_g16.helpers.ViewRouteHelper;
import com.unla.tp_oo2_g16.models.entities.Sede;
import com.unla.tp_oo2_g16.services.interfaces.SedeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/sede")
public class SedeController {

    @Autowired
    private SedeServiceInterface sedeService;

    @GetMapping("/index")
    public ModelAndView listarSedes(@RequestParam(required=false) String filtro){
        ModelAndView mav = new ModelAndView(ViewRouteHelper.SEDE_INDEX);
        if(filtro==null || filtro.isEmpty()) {
            mav.addObject("sedes", sedeService.findAll());
        } else {
            //mav.addObject("sedes", sedeService.buscarPorDireccionOLocalidad(filtro));
        }
        mav.addObject("filtro", filtro);
        return mav;
    }

    @GetMapping("/nuevo")
    public ModelAndView nuevaSede(){
        ModelAndView mav = new ModelAndView(ViewRouteHelper.SEDE_REGISTER);
        mav.addObject("sede", new Sede());
        return mav;
    }

    @PostMapping("/guardar")
    public ModelAndView guardarSede(@ModelAttribute("sede") Sede sedeAux){
        sedeService.editado(sedeAux);
        return new ModelAndView("redirect:/sede/index");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarSede(@PathVariable("id") int idAux){
        ModelAndView mav = new ModelAndView(ViewRouteHelper.SEDE_FORM);
        mav.addObject("sede", sedeService.findById(idAux));
        return mav;
    }
    

}
