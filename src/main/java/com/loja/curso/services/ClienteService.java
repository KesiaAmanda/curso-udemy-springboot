package com.loja.curso.services;


import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.loja.curso.domain.Cidade;
import com.loja.curso.domain.Cliente;
import com.loja.curso.domain.Endereco;
import com.loja.curso.domain.enums.Perfil;
import com.loja.curso.domain.enums.TipoCliente;
import com.loja.curso.dto.ClienteDTO;
import com.loja.curso.dto.ClienteNewDTO;
import com.loja.curso.repositories.ClienteRepository;
import com.loja.curso.repositories.EnderecoRepository;
import com.loja.curso.security.UserSS;
import com.loja.curso.services.exception.AuthorizationException;
import com.loja.curso.services.exception.DataIntegrityException;
import com.loja.curso.services.exception.ObjectNotFoundException;


@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private S3Service s3Service;
	
	public Cliente findById(Integer id) throws ObjectNotFoundException {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> optional = clienteRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:"+ id +" Tipo: "+Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
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
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromNewDTO(ClienteNewDTO cDTO) {
		Cliente cliente = new Cliente(null, cDTO.getNome(), cDTO.getEmail(), cDTO.getCpfOuCnpj(), TipoCliente.toEnum(cDTO.getTipoCliente()), encoder.encode(cDTO.getSenha()));
		Cidade cidade = new Cidade(cDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, cDTO.getLogradouro(), cDTO.getNumero(), cDTO.getComplemento(), cDTO.getBairro(), cDTO.getCep(), cidade, cliente);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(cDTO.getTelefone1());
		if(cDTO.getTelefone2()!=null) {
			cliente.getTelefones().add(cDTO.getTelefone2());
		}
		if(cDTO.getTelefone3()!=null) {
			cliente.getTelefones().add(cDTO.getTelefone3());
		}
		return cliente;
	}
	
	private void updateData(Cliente cliente, Cliente novoCliente) {
		cliente.setNome(novoCliente.getNome());
		cliente.setEmail(novoCliente.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if ( user == null ) {
			throw new AuthorizationException("Acesso negado");
		}
		
		URI uri = s3Service.uploadFile(multipartFile);
		
		Cliente cliente = clienteRepository.findById(user.getId())
				.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:"+ user.getId() +" Tipo: "+Cliente.class.getName()));
		
		cliente.setUrl(uri.toString());
		
		clienteRepository.save(cliente);
		
		return uri;
	}
}
