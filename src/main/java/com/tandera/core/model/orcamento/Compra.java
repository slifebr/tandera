package com.tandera.core.model.orcamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tandera.core.model.cadastro.Pessoa;
import com.tandera.core.model.enuns.StatusOrcamento;

@Entity
@Table(name = "orcam_compra")
public class Compra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compra_seq")
	@SequenceGenerator(name = "compra_seq", sequenceName = "compra_seq", initialValue = 1, allocationSize = 1)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date data;
	
	@Enumerated(EnumType.STRING)
	private StatusOrcamento status;
	
	@ManyToOne
	@JoinColumn(name="pessoa_id",foreignKey = @ForeignKey(name="fk01_compra"))
	private Pessoa pessoa;
	
	@NotNull
	@Size(max = 100)
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "telefone", length = 50, nullable = false)
	private String telefone;
	
	@NotNull
	@Size(max = 500)
	@Column(name = "obs", length = 500, nullable = false)
	private String obs;

	@NotNull
	@Column(name = "vl_deposito", nullable = false)
	private BigDecimal vlDeposito = BigDecimal.ZERO;
	
	@NotNull
	@Column(name = "vl_troca", nullable = false)
	private BigDecimal vlTroca = BigDecimal.ZERO;
	
	@NotNull
	@Column(name = "vl_doacao", nullable = false)
	private BigDecimal vlDoacao = BigDecimal.ZERO;
	
	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<ItemCompra>itemCompra = new ArrayList<ItemCompra>(); 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public StatusOrcamento getStatus() {
		return status;
	}

	public void setStatus(StatusOrcamento status) {
		this.status = status;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public BigDecimal getVlDeposito() {
		return vlDeposito;
	}

	public void setVlDeposito(BigDecimal vlDeposito) {
		this.vlDeposito = vlDeposito;
	}

	public BigDecimal getVlTroca() {
		return vlTroca;
	}

	public void setVlTroca(BigDecimal vlTroca) {
		this.vlTroca = vlTroca;
	}

	public BigDecimal getVlDoacao() {
		return vlDoacao;
	}

	public void setVlDoacao(BigDecimal vlDoacao) {
		this.vlDoacao = vlDoacao;
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
		Compra other = (Compra) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + ", data=" + data + ", status=" + status + ", pessoa=" + pessoa + ", nome=" + nome
				+ ", telefone=" + telefone + ", obs=" + obs + ", vlDeposito=" + vlDeposito + ", vlTroca=" + vlTroca
				+ ", vlDoacao=" + vlDoacao + "]";
	}

}
