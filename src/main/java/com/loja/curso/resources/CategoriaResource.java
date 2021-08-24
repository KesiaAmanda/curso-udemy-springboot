package com.loja.curso.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loja.curso.domain.Categoria;
import com.loja.curso.services.CategoriaService;
import com.loja.curso.services.exception.ObjectNotFoundException;


@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Categoria categoria = categoriaService.findById(id);
		
		return ResponseEntity.ok().body(categoria);
	}
}
