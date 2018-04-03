package com.tandera.core.model;

import java.io.Serializable;

public enum TipoMovimento implements Serializable {
	CREDITO(false), 
	DEBITO(false), 
	TRANSFERENCIA(true); 
	
	private boolean tranferencia;
	private TipoMovimento(boolean tranferencia) {
		this.tranferencia=tranferencia;
	}
	public boolean isTranferencia() {
		return tranferencia;
	}
}
