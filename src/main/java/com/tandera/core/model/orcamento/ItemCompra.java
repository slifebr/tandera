package com.tandera.core.model.orcamento;

import java.io.Serializable;
import java.math.BigDecimal;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tandera.core.model.comercial.Categoria;
import com.tandera.core.model.comercial.Estado;
import com.tandera.core.model.comercial.Marca;
import com.tandera.core.model.comercial.Markup;
import com.tandera.core.model.comercial.MascaraPreco;
import com.tandera.core.model.comercial.Tamanho;

@Entity
@Table(name = "orcam_item_compra")
public class ItemCompra implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_compra_seq")
	@SequenceGenerator(name = "item_compra_seq", sequenceName = "item_compra_seq", initialValue = 1, allocationSize = 1)
	private Integer id;
	
	@NotNull
	private Integer item;
	
	@ManyToOne
	@JoinColumn(name="categoria_id",foreignKey = @ForeignKey(name="fk01_itemCompra"))
	private Categoria categoria;
	
	@ManyToOne
	@JoinColumn(name="marca_id",foreignKey = @ForeignKey(name="fk02_itemCompra"))
	private Marca marca;
	
	@ManyToOne
	@JoinColumn(name="tamanho_id",foreignKey = @ForeignKey(name="fk03_itemCompra"))
	private Tamanho tamanho;
	
	@ManyToOne
	@JoinColumn(name="estado_id",foreignKey = @ForeignKey(name="fk04_itemCompra"))
	private Estado estado;
	
	@ManyToOne
	@JoinColumn(name="mascara_id",foreignKey = @ForeignKey(name="fk05_itemCompra"))
	private MascaraPreco mascaraPreco;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "vl_mascara", length = 50, nullable = false)
	private BigDecimal vlMascara;
	
	@ManyToOne
	@JoinColumn(name="markup_id",foreignKey = @ForeignKey(name="fk06_itemCompra"))
	private Markup markup;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "qtde", length = 50, nullable = false)
	private Integer qtde;
	
	@NotNull
	@Column(name = "valor", nullable = false)
	private BigDecimal valor = BigDecimal.ZERO;
	
	@ManyToOne
	@JoinColumn(name="compra_id",foreignKey = @ForeignKey(name="fk07_itemCompra"))
	private Compra compra;
	
	@Transient
	private BigDecimal valorTotal;
	

	public BigDecimal getValorTotal() {
		return getValor().multiply(BigDecimal.valueOf(getQtde()));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItem() {
		return item;
	}
	
	public void setItem(Integer item) {
		this.item = item;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Tamanho getTamanho() {
		return tamanho;
	}

	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Markup getMarkup() {
		return markup;
	}

	public void setMarkup(Markup markup) {
		this.markup = markup;
	}

	public Integer getQtde() {
		return qtde;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	
	public void setVlMascara(BigDecimal vlMascara) {
		this.vlMascara = vlMascara;
	}
	
	public BigDecimal getVlMascara() {
		return vlMascara;
	}
	

	public MascaraPreco getMascaraPreco() {
		return mascaraPreco;
	}

	public void setMascaraPreco(MascaraPreco mascaraPreco) {
		this.mascaraPreco = mascaraPreco;
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
		ItemCompra other = (ItemCompra) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemCompra [id=" + id + ", categoria=" + categoria + ", marca=" + marca + ", tamanho=" + tamanho
				+ ", estado=" + estado + ", mascara=" + mascaraPreco + ", vlMascara=" + vlMascara + ", markup=" + markup
				+ ", qtde=" + qtde + ", valor=" + valor + ", compra=" + compra + "]";
	}


}
