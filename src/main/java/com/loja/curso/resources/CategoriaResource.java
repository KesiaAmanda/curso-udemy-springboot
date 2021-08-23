package com.loja.curso.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loja.curso.domain.Categoria;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@GetMapping
	//@RequestMapping(method = RequestMethod.GET)
	public List<Categoria> listar() {
		Categoria categoria1 = new Categoria(1, "Informática");
		Categoria categoria2 = new Categoria(2, "Escritório");
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		categorias.add(categoria1);
		categorias.add(categoria2);
		
		return categorias;
	}
}
