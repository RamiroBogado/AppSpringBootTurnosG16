package com.unla.tp_oo2_g16.configurations.mail;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {
	
	@Value("${EMAIL_SENDER}") 
	private String emailUser;
	
	@Value("${EMAIL_PASSWORD}")
	private String password;
	
	@Bean
	JavaMailSender getJavaMailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername(emailUser);
		mailSender.setPassword(password);
		
		Properties props = mailSender.getJavaMailProperties();
		
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

		
		return mailSender;
		
	}

}
