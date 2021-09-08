package com.loja.curso.domain.enums;

public enum Perfil {
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Perfil tipoCliente : Perfil.values()) {
			if(cod.equals(tipoCliente.getCod())) {
				return tipoCliente;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
	
}
