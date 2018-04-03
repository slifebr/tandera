package com.tandera.core.model;

public enum Categoria {
	REMUNERACAO("Remuneração"),
	DESPESA("Despesa"),
	ALIMENTACAO("Alimentação"),
	HABITACAO("Habitação"),
	FAMILIA("Família"),
	SAUDE("Saúde"),
	EDUCACAO("Educação"),
	HIGIENE("Higiene"),
	ESPORTE("Esporte"),
	LAZER("Lazer"),
	VIAGEM("Viagem"),
	COMUNICACAO("Comunicação"),
	TRANSPORTE("Transporte"),
	PATRIMONIO("Patrimônio"),
	INVESTIMENTO("Investimento"),
	CUSTO_FIXO("Custo Fixo"),
	CUSTO_OPERACIONAL("Custo Operacional"),
	OBRIGACOES("Obrigações"),
	TRIBUTOS("Tributos"),
	TRANSACOES("Transações")
	;
	
	private String nome;
	private Categoria(String nome) {
		this.nome=nome;
	}
	public String getNome() {
		return nome;
	}
	public String getUpper() {
		return nome.toUpperCase();
	}
}
