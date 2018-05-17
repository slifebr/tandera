package com.tandera.core.dao.springjpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tandera.core.model.comercial.MascaraPreco;

public interface MascaraPrecoRepository extends JpaRepository<MascaraPreco, Integer> {

	public List<MascaraPreco> findByMascaraContainingIgnoreCase(String mascara);

	public MascaraPreco findById(Integer id);

}
