package com.tandera.core.model.orcamento;

import java.util.Calendar;

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

import com.tandera.core.model.cadastro.LiberacaoUsuario;

@Entity
@Table(name="compra_log")
public class CompraLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compra_log_seq")
	@SequenceGenerator(name = "compra_log_seq", sequenceName = "compra_log_seq", initialValue = 1, allocationSize = 1)	
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data;
	
	private String historico;
	
	@ManyToOne
	@JoinColumn(name="liberacaoUsuario_id", foreignKey = @ForeignKey(name="fk01_compralog"))
	private LiberacaoUsuario liberacaoUsuario;
	
	@ManyToOne
	@JoinColumn(name="compra_id", foreignKey = @ForeignKey(name="fk02_compralog"))	
	private Compra compra;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}
		
	public LiberacaoUsuario getLiberacaoUsuario() {
		return liberacaoUsuario;
	}

	public void setLiberacaoUsuario(LiberacaoUsuario liberacaoUsuario) {
		this.liberacaoUsuario = liberacaoUsuario;
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
		CompraLog other = (CompraLog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CompraLog [id=" + id + ", data=" + data + ", liberacaoUsuario=" + liberacaoUsuario + ", compra="
				+ compra + ", Historico= " + historico + "]";
	}


	
	
}
