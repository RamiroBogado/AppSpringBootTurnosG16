package com.unla.tp_oo2_g16.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.unla.tp_oo2_g16.helpers.ViewRouteHelper;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // Página principal del admin
    @GetMapping("/panel")
    public ModelAndView adminIndex() {
        return new ModelAndView(ViewRouteHelper.ADMIN_PANEL); 
    }
}
