package com.loja.curso.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loja.curso.domain.Cliente;
import com.loja.curso.repositories.ClienteRepository;
import com.loja.curso.services.exception.ObjectNotFoundException;


@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente findById(Integer id) throws ObjectNotFoundException {
		Optional<Cliente> optional = clienteRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:"+ id +" Tipo: "+Cliente.class.getName()));
	}
	
}
