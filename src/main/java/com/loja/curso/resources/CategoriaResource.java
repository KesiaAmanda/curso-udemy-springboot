package com.loja.curso.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@GetMapping
	//@RequestMapping(method = RequestMethod.GET)
	public String listar() {
		return "Ok";
	}
}
