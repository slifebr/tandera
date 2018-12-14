package com.tandera.core.model.enuns;

public enum TipoFinanceiro {
	
	DEPOSITO("Deposito"),
	TROCA("Troca"),
	DOACAO("Doação"),
	MOEDA("Moeda"),
	CONSIGNADO("Consignado");
	
	private String descricao;
	
	TipoFinanceiro(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public String toString() {
		return this.descricao;
	}	
	
}
