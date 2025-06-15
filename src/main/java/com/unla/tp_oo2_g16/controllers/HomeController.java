package com.unla.tp_oo2_g16.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.unla.tp_oo2_g16.helpers.ViewRouteHelper;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToAuthIndex() {
        return ViewRouteHelper.AUTH_INDEX;
    }
}

