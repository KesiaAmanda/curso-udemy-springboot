package com.loja.curso.dto;

import java.io.Serializable;

import com.loja.curso.domain.Estado;

public class EstadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public EstadoDTO() {
	}
	
	public EstadoDTO(Estado estado) {
		id = estado.getId();
		nome = estado.getNome();
	}
	
	
	
}
