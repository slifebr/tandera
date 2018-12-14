package com.tandera.core.model.enuns;

public enum StatusOrcamento {
	
	N("Negociação"),
	A("Aprovado Deposito"),
	AD("Aprovado Doação"),
	AT("Aprovado Troca"),
	R("Reprovado");
	
	private String descricao;

	StatusOrcamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public String toString() {
		return this.descricao;
	}	
	

}
