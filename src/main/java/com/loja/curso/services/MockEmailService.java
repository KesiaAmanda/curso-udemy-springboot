package com.loja.curso.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService{
	private static final Logger LOGGER = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage mailMessage) {
		LOGGER.info("Simulando envio de email.....");
		LOGGER.info(mailMessage.toString());
		LOGGER.info("Email enviado!!");
	}

	@Override
	public void sendHtmlEmail(MimeMessage mimeMessage) {
		LOGGER.info("Simulando envio de email HTML.....");
		LOGGER.info(mimeMessage.toString());
		LOGGER.info("Email enviado!!");
	}
	
}
