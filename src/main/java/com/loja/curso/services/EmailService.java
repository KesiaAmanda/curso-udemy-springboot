package com.loja.curso.services;

import org.springframework.mail.SimpleMailMessage;

import com.loja.curso.domain.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido pedido);

	void sendEmail(SimpleMailMessage mailMessage);
}
