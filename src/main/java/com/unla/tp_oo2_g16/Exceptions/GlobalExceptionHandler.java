package com.unla.tp_oo2_g16.Exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        ClienteNoEncontradoException.class,
        ClienteDuplicadoException.class,
        
    })
    public String manejarExcepcionesPersonalizadas(RuntimeException ex, Model model) {
        model.addAttribute("titulo", "Error en la operación");
        model.addAttribute("mensaje", ex.getMessage());
        return "error/404"; // error.html
    }

    @ExceptionHandler(Exception.class)
    public String manejarExcepcionGeneral(Exception ex, Model model) {
        model.addAttribute("titulo", "Error inesperado");
        model.addAttribute("mensaje", "Ocurrió un error inesperado.");
        return "error/500";
    }
}
