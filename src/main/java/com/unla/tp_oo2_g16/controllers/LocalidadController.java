package com.unla.tp_oo2_g16.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unla.tp_oo2_g16.dtos.LocalidadDTO;
import com.unla.tp_oo2_g16.helpers.ViewRouteHelper;
import com.unla.tp_oo2_g16.models.entities.Localidad;
import com.unla.tp_oo2_g16.services.interfaces.LocalidadServiceInterface;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/localidad")
public class LocalidadController {
    
    @Autowired
    private LocalidadServiceInterface localidadService;

    @GetMapping("/index")
    public ModelAndView listarLocalidades(@RequestParam(required = false) String filtro){
        ModelAndView mav = new ModelAndView(ViewRouteHelper.LOCALIDAD_INDEX);
        if(filtro == null || filtro.isEmpty()){
            mav.addObject("localidades", localidadService.findAllByOrderByCpAsc());
        } else {
            // Continuar 
        }

        mav.addObject("filtro", filtro);
        return mav;
    }

    @GetMapping("/nuevo")
    public ModelAndView nuevaLocalidad() {
        ModelAndView mav = new ModelAndView(ViewRouteHelper.LOCALIDAD_REGISTER);
        mav.addObject("localidad", new LocalidadDTO(null, "", ""));
        return mav;
    }

    @PostMapping("/guardar")
    public ModelAndView guardarLocalidad(@Valid @ModelAttribute("localidad") LocalidadDTO localidadDTO, BindingResult result){
        
        if(result.hasErrors()){
            ModelAndView mav = new ModelAndView(ViewRouteHelper.LOCALIDAD_REGISTER);
            mav.addObject("localidad", localidadDTO);
            return mav;
        }

        // Si es una nueva localidad, verificamos que no se repita el código postal
        if(localidadDTO.idLocalidad() == null){
            if(localidadService.existsByCp(localidadDTO.cp())) {
                result.rejectValue("cp", "error.localidad", "Ya existe una localidad con ese código");
            }
        } else {
            // Si es edición, validar que no existan duplicados en otro registro distinto al actual
            if(localidadService.existsByCpAndIdLocalidadNot(localidadDTO.cp(), localidadDTO.idLocalidad())){
                result.rejectValue("cp", "error.localidad", "Ya existe una localidad con ese código postal");
            }
        }

        if(result.hasErrors()) {
            ModelAndView mav = new ModelAndView(ViewRouteHelper.LOCALIDAD_REGISTER);
            mav.addObject("localidad", localidadDTO);
            return mav;
        }

        Localidad localidad = localidadService.toEntity(localidadDTO);
        localidadService.editado(localidad);

        return new ModelAndView("redirect:/localidad/index");
    }
    
    @GetMapping("/editar/{id}")
    public ModelAndView editarLocalidad(@PathVariable("id") Integer idAux) {
        Localidad localidad = localidadService.findById(idAux);
        if(localidad == null) return new ModelAndView("redirect:/localidad/index");
        LocalidadDTO dto = localidadService.toDTO(localidad);
        ModelAndView mav = new ModelAndView(ViewRouteHelper.LOCALIDAD_REGISTER);
        mav.addObject("localidad", dto);
        return mav;
    }

    @PostMapping("/eliminar/{id}")  // Cambiado a POST para mejor práctica
    public ModelAndView eliminarLocalidad(
        @PathVariable Integer id, 
        RedirectAttributes redirectAttributes) {
        
        ModelAndView mav = new ModelAndView("redirect:/localidad/index");  // Corregido el redirect
        
        try {
            // Verificar y eliminar
            localidadService.verificarYEliminarLocalidad(id);
            redirectAttributes.addFlashAttribute("success", "Localidad eliminada correctamente");
            
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", 
                "No se puede eliminar la localidad porque tiene sedes asociadas");
                
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", 
                "Localidad no encontrada");
                
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al eliminar la localidad: " + e.getMessage());
        }
        
        return mav;
    }
    
    
    
    

}
