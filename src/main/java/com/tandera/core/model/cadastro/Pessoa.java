package com.tandera.core.model.cadastro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cad_pessoa")
public class Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_seq")
	@SequenceGenerator(name = "pessoa_seq", sequenceName = "pessoa_seq", initialValue = 1, allocationSize = 1)
	private Integer id;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "nome", length = 50, nullable = false)
	private String nome;
	
	@NotNull
	@Size(max = 20)
	@Column(name = "cpf", length = 20, nullable = false)
	private String cpf;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "endereco", length = 50, nullable = false)
	private String endereco;
	
	@NotNull
	@Size(max = 10)
	@Column(name = "numero", length = 10, nullable = false)
	private Integer numero;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "complemento", length = 50, nullable = false)
	private String complemento;
	
	@NotNull
	@Size(max = 30)
	@Column(name = "bairro", length = 30, nullable = false)
	private String bairro;
	
	@NotNull
	@Size(max = 40)
	@Column(name = "cidade", length = 40, nullable = false)
	private String cidade;
	
	@NotNull
	@Size(max = 10)
	@Column(name = "estado", length = 10, nullable = false)
	private String estado;
	
	@NotNull
	@Size(max = 15)
	@Column(name = "cep", length = 15, nullable = false)
	private String cep;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "dados_bancarios", length = 50, nullable = false)
	private String dados_bancarios;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "telefone", length = 50, nullable = false)
	private String telefone;
	
	@NotNull
	@Size(max = 5)
	@Column(name = "fornecedor", length = 5, nullable = false)
	private boolean fornecedor;
	
	@NotNull
	@Size(max = 5)
	@Column(name = "cliente", length = 5, nullable = false)
	private boolean cliente;
	
	@NotNull
	@Size(max = 5)
	@Column(name = "funcionario", length = 5, nullable = false)
	private boolean funcionario;

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getDados_bancarios() {
		return dados_bancarios;
	}

	public void setDados_bancarios(String dados_bancarios) {
		this.dados_bancarios = dados_bancarios;
	}
	

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(boolean fornecedor) {
		this.fornecedor = fornecedor;
	}

	public boolean getCliente() {
		return cliente;
	}

	public void setCliente(boolean cliente) {
		this.cliente = cliente;
	}

	public boolean getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(boolean funcionario) {
		this.funcionario = funcionario;
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
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", endereco=" + endereco + ", numero=" + numero
				+ ", complemento=" + complemento + ", bairro=" + bairro + ", cidade=" + cidade + ", estado=" + estado
				+ ", cep=" + cep + ", dados_bancarios=" + dados_bancarios + ", telefone=" + telefone + ", fornecedor="
				+ fornecedor + ", cliente=" + cliente + ", funcionario=" + funcionario + "]";
	}
	

}
