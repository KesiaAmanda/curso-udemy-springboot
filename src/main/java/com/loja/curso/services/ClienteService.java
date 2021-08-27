package com.loja.curso.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.loja.curso.domain.Cliente;
import com.loja.curso.dto.ClienteDTO;
import com.loja.curso.repositories.ClienteRepository;
import com.loja.curso.services.exception.DataIntegrityException;
import com.loja.curso.services.exception.ObjectNotFoundException;


@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente findById(Integer id) throws ObjectNotFoundException {
		Optional<Cliente> optional = clienteRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:"+ id +" Tipo: "+Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		return clienteRepository.save(cliente);
	}
	
	public Cliente update(Cliente clienteAtualizado) {
		Cliente cliente = findById(clienteAtualizado.getId());
		updateData(cliente, clienteAtualizado);
		return clienteRepository.save(cliente);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			clienteRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque existem entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente cliente, Cliente novoCliente) {
		cliente.setNome(novoCliente.getNome());
		cliente.setEmail(novoCliente.getEmail());
	}
}
