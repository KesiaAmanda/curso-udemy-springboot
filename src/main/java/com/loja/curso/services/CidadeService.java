package com.loja.curso.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loja.curso.domain.Cidade;
import com.loja.curso.repositories.CidadeRepository;
import com.loja.curso.services.exception.ObjectNotFoundException;

@Service
public class CidadeService {
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public List<Cidade> findCidades(Integer estadoId) throws ObjectNotFoundException {
		List<Cidade> cidades = cidadeRepository.findCidades(estadoId);
		return cidades;
	}

}
