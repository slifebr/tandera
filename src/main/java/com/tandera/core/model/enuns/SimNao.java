package com.tandera.core.model.enuns;

public enum SimNao {
	N("Não"),
	S("Sim");
	
	private String descricao;
	
	SimNao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public String toString() {
		return this.descricao;
	}		
}

