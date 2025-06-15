package com.unla.tp_oo2_g16.services.interfaces;


import com.unla.tp_oo2_g16.models.entities.Sede;
import com.unla.tp_oo2_g16.models.entities.Turno;

import jakarta.mail.MessagingException;


public interface EmailServiceInterface {
	
	void sendEmail(String toUser, String subject, String message);
	void enviarConfirmacionTurnoHtml(String to, Turno turno, Sede sede) throws MessagingException;

    

}
