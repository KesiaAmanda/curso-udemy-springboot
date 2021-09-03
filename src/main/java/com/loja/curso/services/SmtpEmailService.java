package com.loja.curso.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SmtpEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage mailMessage) {
		LOGGER.info("Enviando de email.....");
		mailSender.send(mailMessage);
		LOGGER.info("Email enviado!!");
		
	}

	@Override
	public void sendHtmlEmail(MimeMessage mimeMessage) {
		LOGGER.info("Enviando de email.....");
		javaMailSender.send(mimeMessage);
		LOGGER.info("Email enviado!!");
	}

}
