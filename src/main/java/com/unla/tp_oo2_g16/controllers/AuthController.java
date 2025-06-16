package com.unla.tp_oo2_g16.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unla.tp_oo2_g16.helpers.ViewRouteHelper;
import com.unla.tp_oo2_g16.models.entities.Cliente;
import com.unla.tp_oo2_g16.services.implementations.UserServiceImp;
import com.unla.tp_oo2_g16.services.interfaces.ClienteServiceInterface;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
    @GetMapping("/index")
    public String login() {
        return ViewRouteHelper.AUTH_INDEX;
    }

    //LOGIN
    
    //GET auth/login --> Return the view in path authentication/login
    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(required=false) String error,
                        @RequestParam(required=false) String logout) {
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        return ViewRouteHelper.AUTH_LOGIN;
    }
    
    //GET auth/loginSuccess --> Rotorna la vista depende del rol user
    @GetMapping("/loginSuccess")
    public String loginRedirect(Authentication authentication) {
        if (authentication != null) {
            for (GrantedAuthority auth : authentication.getAuthorities()) {
                if (auth.getAuthority().equals("ROLE_ADMIN")) {
                    return "redirect:/admin/panel";
                } else if (auth.getAuthority().equals("ROLE_USER")) {
                    return "redirect:/turno/index";
                }
            }
        }
        
        return "redirect:/auth/login?error";
    }
      
    //REGISTER 
	@Autowired
	ClienteServiceInterface clienteService;
	@Autowired
	UserServiceImp userService;

    @GetMapping("/register")
    public String register(Model model) {
    	
    	Cliente cliente = new Cliente();
    	 	
    	model.addAttribute("cliente", cliente);

    	return ViewRouteHelper.AUTH_RESGISTER;
    	
    }
    
    @PostMapping("/guardar-user")
    public String guardarUser(@Valid @ModelAttribute("cliente") Cliente clienteAux,
                              BindingResult result,
                              Model model) {

        // Validar duplicados
        if (clienteService.existsByDni(clienteAux.getDni())) {
            result.rejectValue("dni", "error.cliente", "El DNI ya está registrado");
        }
        if (clienteService.existsByCuil(clienteAux.getCuil())) {
            result.rejectValue("cuil", "error.cliente", "El CUIL ya está registrado");
        }
        if (userService.existsByEmail(clienteAux.getUser().getEmailUser())) {
            result.rejectValue("user.emailUser", "error.user", "El email ya está registrado");
        }

        if (result.hasErrors()) {
            // Mostrar errores en consola para depuración
            System.out.println("===> Hay errores de validación:");
            result.getFieldErrors().forEach(e -> System.out.println("Campo: " + e.getField() + " - Error: " + e.getDefaultMessage()));
            
            return ViewRouteHelper.AUTH_RESGISTER;
        }

        // Guardar
        clienteService.save(clienteAux);
        return "redirect:/auth/login";
    }




}