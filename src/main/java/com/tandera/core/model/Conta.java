package com.tandera.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="conta")
public class Conta implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false,length=50)
	private String nome;
	
	@Column(nullable=false,length=20)
	private String sigla;
	
	@Column(nullable=false,length=9,precision=2)
	private Double saldo;
	
	@Column(nullable=false)
	private boolean excluido;
	
	@Column(name="usuario_id", nullable=false, length=9)
	private Integer usuario;
	
	@Column(name="si_valor",nullable=false,length=9,precision=2)
	private Double siValor;
	
	@Temporal(TemporalType.DATE)
	@Column(name="si_data")
	private Date siData;
	
	
	/*public Conta(String nome, String sigla, Double saldo) {
		super();
		this.nome = nome;
		this.sigla = sigla;
		this.saldo = saldo;
		this.sa
	}*/

	public Conta() {
		saldo=0.0d;
		siValor=0.0d;
		siData=new Date();
	}
	
	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}
	public boolean isExcluido() {
		return excluido;
	}

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
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public Integer getUsuario() {
		return usuario;
	}
	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}
	@Override
	public String toString() {
		return "Conta [nome=" + nome + "]";
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
		Conta other = (Conta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Double getSiValor() {
		return siValor;
	}

	public void setSiValor(Double siValor) {
		this.siValor = siValor;
	}

	public Date getSiData() {
		return siData;
	}

	public void setSiData(Date siData) {
		this.siData = siData;
	}
	
}
