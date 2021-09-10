package com.loja.curso.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loja.curso.domain.Cidade;
import com.loja.curso.domain.Estado;
import com.loja.curso.dto.CidadeDTO;
import com.loja.curso.dto.EstadoDTO;
import com.loja.curso.services.CidadeService;
import com.loja.curso.services.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> estados = estadoService.findAll();
		List<EstadoDTO> estadosDTO = estados.stream().map(EstadoDTO::new).collect(Collectors.toList());
		return ResponseEntity.ok().body(estadosDTO);
	}
	
	@GetMapping("/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidade(@PathVariable Integer estadoId) {
		List<Cidade> cidades = cidadeService.findCidades(estadoId);
		List<CidadeDTO> cidadeDTOs = cidades.stream().map(CidadeDTO::new).collect(Collectors.toList());
		return ResponseEntity.ok().body(cidadeDTOs);
	}
}
