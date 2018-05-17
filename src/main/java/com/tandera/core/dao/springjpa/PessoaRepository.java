package com.tandera.core.dao.springjpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tandera.core.model.cadastro.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

	public List<Pessoa> findByNomeContainingIgnoreCase(String nome);

	public Pessoa findById(Integer id);

}
