package com.loja.curso.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loja.curso.domain.Pedido;
import com.loja.curso.repositories.PedidoRepository;
import com.loja.curso.services.exception.ObjectNotFoundException;


@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido findById(Integer id) throws ObjectNotFoundException {
		Optional<Pedido> optional = pedidoRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:"+ id +" Tipo: "+Pedido.class.getName()));
	}
	
}
