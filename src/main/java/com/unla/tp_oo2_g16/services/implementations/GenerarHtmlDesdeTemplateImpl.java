package com.unla.tp_oo2_g16.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.unla.tp_oo2_g16.models.entities.Turno;
import com.unla.tp_oo2_g16.services.interfaces.GenerarHtmlDesdeTemplateInterface;

@Service
public class GenerarHtmlDesdeTemplateImpl implements GenerarHtmlDesdeTemplateInterface{
	
	@Autowired
	private SpringTemplateEngine templateEngine;

	public String generarHtmlDesdeTemplate(Turno turno) {
	    Context context = new Context();
	    context.setVariable("turno", turno); // El objeto que usás en la vista
	    
	    return templateEngine.process("test", context); // nombre del template sin ".html"
	}


}
