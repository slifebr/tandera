package com.tandera.core.model.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tandera.core.model.cadastro.Pessoa;
import com.tandera.core.model.enuns.TipoFinanceiro;
import com.tandera.core.model.orcamento.Compra;

@Entity
@Table(name = "cta_corrente")
public class CtaCorrente implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cta_corrente_seq")
	@SequenceGenerator(name = "cta_corrente_seq", sequenceName = "cta_corrente_seq",initialValue = 1, allocationSize = 1)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dt;
	
	@NotNull
	@Column(name = "valor", nullable = false)
	private BigDecimal valor = BigDecimal.ZERO;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date vencto;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoFinanceiro tipo;
	
	@Size(max =500)
	@Column(name="dados_bancario", length=500)
	private String dadosBancarios;
	
	@Size(max =500)
	@Column(name="observacao", length=500)	
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name = "pessoa_id", foreignKey = @ForeignKey(name="fk01_ctacorrente"))
	private Pessoa pessoa;
	
	@ManyToOne
	@JoinColumn(name = "compra_id", foreignKey = @ForeignKey(name="fk02_ctacorrente"))
	private Compra compra;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Date getVencto() {
		return vencto;
	}
	public void setVencto(Date vencto) {
		this.vencto = vencto;
	}
	public TipoFinanceiro getTipo() {
		return tipo;
	}
	public void setTipo(TipoFinanceiro tipo) {
		this.tipo = tipo;
	}
	public String getDadosBancarios() {
		return dadosBancarios;
	}
	public void setDadosBancarios(String dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
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
		CtaCorrente other = (CtaCorrente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CtaCorrente [id=" + id + ", dt=" + dt + ", valor=" + valor + ", vencto=" + vencto + ", tipo=" + tipo
				+ ", dadosBancarios=" + dadosBancarios + ", observacao=" + observacao + "]";
	}
	
	
	
	
	

}
