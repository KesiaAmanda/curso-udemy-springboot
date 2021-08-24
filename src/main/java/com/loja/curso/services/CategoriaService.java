package com.loja.curso.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loja.curso.domain.Categoria;
import com.loja.curso.repositories.CategoriaRepository;
import com.loja.curso.services.exception.ObjectNotFoundException;


@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria findById(Integer id) throws ObjectNotFoundException {
		Optional<Categoria> optional = categoriaRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:"+ id +" Tipo: "+Categoria.class.getName()));
	}
	
}
