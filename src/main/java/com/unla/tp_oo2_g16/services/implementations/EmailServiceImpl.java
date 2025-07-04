package com.unla.tp_oo2_g16.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import com.unla.tp_oo2_g16.models.entities.Sede;
import com.unla.tp_oo2_g16.models.entities.Turno;
import com.unla.tp_oo2_g16.services.interfaces.EmailServiceInterface;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailServiceInterface{
	
	@Value("${EMAIL_SENDER}") 
	private String emailUser;

	@Autowired
	private JavaMailSender mailSender;
		
	@Override
	public void sendEmail(String toUser, String subject, String message) {
		
		SimpleMailMessage  mailMessage = new SimpleMailMessage();
		
		mailMessage.setFrom(emailUser);
		mailMessage.setTo(toUser);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
			
		mailSender.send(mailMessage);
		
	} 

	public void enviarConfirmacionTurnoHtml(String to, Turno turno, Sede sede) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Confirmación de Turno");
        helper.setText(generarCuerpoHtml(turno, sede), true); // `true` indica HTML

        mailSender.send(message);
    }

    private String generarCuerpoHtml(Turno turno, Sede sede) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f5f5f5; padding: 20px;">
                    <div style="background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1);">
                        <h2 style="color: #4A90E2;">Confirmación de Turno</h2>
                        <p>Hola <strong>%s %s</strong>,</p>
                        <p>Tu turno ha sido confirmado con los siguientes datos:</p>
                        <ul>
                            <li><strong>Servicio:</strong> %s</li>
                            <li><strong>Fecha y hora:</strong> %s</li>
                            <li><strong>Código del turno:</strong> <span style="color: red;">%s</span></li>
                        </ul>
                        <p>Dirección sede: <strong>%s - %s</strong></p>
                        <p style="margin-top: 20px;">¡Gracias por elegirnos!</p>
                    </div>
                </body>
                </html>
                """.formatted(
                    turno.getCliente().getNombre(),
                    turno.getCliente().getApellido(),
                    turno.getServicio().getNombre(),
                    turno.getFechaHora().toString(),
                    turno.getCodigoTurno(),
                    sede.getDireccion(),
                    sede.getLocalidad().getNombre()
                );
    }
	
}
