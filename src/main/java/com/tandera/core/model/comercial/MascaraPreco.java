package com.tandera.core.model.comercial;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tandera.core.model.orcamento.ItemCompra;


@Entity
@Table(name = "com_mascara_preco")
public class MascaraPreco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mascara_preco_seq")
	@SequenceGenerator(name = "mascara_preco_seq", sequenceName = "mascara_preco_seq", initialValue = 1, allocationSize = 1)
	private Integer id;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "mascara", length = 50, nullable = false)
	private String mascara;
	
	@NotNull
	@Column(name = "valor", nullable = false)
	private BigDecimal valor;
	
	@OneToMany(mappedBy = "mascaraPreco", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
	private List<ItemCompra>itemCompra = new ArrayList<ItemCompra>(); 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		//if (mascara == null) {
		/*if(this.getValor().doubleValue() >= 0) {
			this.mascara =  Biblioteca.criptoBigDecimalToString(this.getValor());
		}
		else {
			this.mascara = mascara;
		}*/
		this.mascara = mascara;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
		MascaraPreco other = (MascaraPreco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MascaraPreco [id=" + id + ", mascara=" + mascara + ", valor=" + valor + "]";
	}
	
	

}
