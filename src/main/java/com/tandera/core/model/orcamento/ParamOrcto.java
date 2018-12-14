package com.tandera.core.model.orcamento;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name= "param_orcto")
public class ParamOrcto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;	// o id será informado manualmente, não sendo por sequence
	
	@NotNull
	@Column( nullable = false)
	private BigDecimal troca = BigDecimal.ZERO;
	
	@NotNull
	@Column( nullable = false)
	private BigDecimal doacao = BigDecimal.ZERO;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getTroca() {
		return troca;
	}

	public void setTroca(BigDecimal troca) {
		this.troca = troca;
	}

	public BigDecimal getDoacao() {
		return doacao;
	}

	public void setDoacao(BigDecimal doacao) {
		this.doacao = doacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParamOrcto other = (ParamOrcto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ParamOrcto [id=" + id + ", troca=" + troca + ", doacao=" + doacao + "]";
	}	
	
	

}
