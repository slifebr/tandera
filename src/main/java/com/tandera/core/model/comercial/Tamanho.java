package com.tandera.core.model.comercial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tandera.core.model.orcamento.ItemCompra;

@Entity
public class Tamanho implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tamanho_seq")
	@SequenceGenerator(name = "tamanho_seq", sequenceName = "tamanho_seq", initialValue = 1, allocationSize = 1)
	private Integer id;

	@NotNull
	@Size(max = 5)
	@Column(name = "sigla", length = 5, nullable = false)
	private String sigla;

	@NotNull
	@Size(max = 50)
	@Column(name = "descr", length = 50, nullable = false)
	private String descr;
	
	@OneToMany(mappedBy = "tamanho", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
	private List<ItemCompra>itemCompra = new ArrayList<ItemCompra>(); 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public Tamanho sigla(String sigla) {
		this.sigla = sigla;
		return this;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescr() {
		return descr;
	}

	public Tamanho descr(String descr) {
		this.descr = descr;
		return this;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Tamanho tamanho = (Tamanho) o;
		if (tamanho.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), tamanho.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Tamanho{" + "id=" + getId() + ", sigla='" + getSigla() + "'" + ", descr='" + getDescr() + "'" + "}";
	}

}
