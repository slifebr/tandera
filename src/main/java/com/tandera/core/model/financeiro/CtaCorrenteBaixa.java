package com.tandera.core.model.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name = "cta_corrente_baixa")
public class CtaCorrenteBaixa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cta_corrente_baixa_seq")
	@SequenceGenerator(name = "cta_corrente_baixa_seq", sequenceName = "cta_corrente_baixa_seq",initialValue = 1, allocationSize = 1)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_baixa", nullable = false)
	private Date dtBaixa;
	
	@NotNull
	@Column(name = "valor_pago", nullable = false)
	private BigDecimal valorPago = BigDecimal.ZERO;

	@Size(max =500)
	@Column(name="observacao", length=500)	
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name = "ctacorrente_id", foreignKey = @ForeignKey(name="fk01_ctacorrente_baixa"))
	private CtaCorrente ctaCorrente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDtBaixa() {
		return dtBaixa;
	}

	public void setDtBaixa(Date dtBaixa) {
		this.dtBaixa = dtBaixa;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public CtaCorrente getCtaCorrente() {
		return ctaCorrente;
	}

	public void setCtaCorrente(CtaCorrente ctaCorrente) {
		this.ctaCorrente = ctaCorrente;
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
		CtaCorrenteBaixa other = (CtaCorrenteBaixa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CtaCorrenteBaixa [id=" + id + ", dtBaixa=" + dtBaixa + ", valorPago=" + valorPago + ", observacao="
				+ observacao + ", ctaCorrente=" + ctaCorrente + "]";
	}
	
	
	
		
}
