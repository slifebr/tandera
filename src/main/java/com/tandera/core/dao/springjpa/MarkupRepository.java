package com.tandera.core.dao.springjpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tandera.core.model.comercial.Markup;

public interface MarkupRepository extends JpaRepository<Markup, Integer> {
	
	public List<Markup> findBySiglaContainingIgnoreCase(String sigla);
	
	public Markup findById(Integer id);

}
