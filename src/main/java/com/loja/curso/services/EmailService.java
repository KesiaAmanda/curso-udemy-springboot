package com.loja.curso.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.loja.curso.domain.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido pedido);

	void sendEmail(SimpleMailMessage mailMessage);
	
	void sendOrderConfirmationHtmlEmail(Pedido pedido);
	
	void sendHtmlEmail(MimeMessage mimeMessage);
}
