package com.loja.curso.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.loja.curso.domain.Cliente;
import com.loja.curso.domain.Pedido;


public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default-sender}")
	private String sender;
	
	@Autowired 
	private TemplateEngine engine;
	
	@Autowired 
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage mailMessage = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(mailMessage);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(pedido.getCliente().getEmail());
		mailMessage.setFrom(sender);
		mailMessage.setSubject("Pedido confirmado! Código: " + pedido.getId());
		mailMessage.setSentDate(new Date(System.currentTimeMillis()));
		mailMessage.setText(pedido.toString());
		return mailMessage;
	}
	
	protected String htmlFromTemplatePedido(Pedido pedido) {
		Context context = new Context();
		context.setVariable("pedido", pedido);
		return engine.process("email/confirmacaoPedido", context);
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		MimeMessage message;
		try {
			message = prepareMimeMessageFromPedido(pedido);
			sendHtmlEmail(message);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(pedido);
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(pedido.getCliente().getEmail());
		helper.setFrom(sender);
		helper.setSubject("Pedido confirmado! Código: " + pedido.getId());
		helper.setSentDate(new Date(System.currentTimeMillis()));
		helper.setText(htmlFromTemplatePedido(pedido), true);
		return message;
	}
	
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage simpleMailMessage = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(simpleMailMessage);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(cliente.getEmail());
		mailMessage.setFrom(sender);
		mailMessage.setSubject("Solicitação de nova senha");
		mailMessage.setSentDate(new Date(System.currentTimeMillis()));
		mailMessage.setText("Nova senha: " + newPass);
		return mailMessage;
	}
}
