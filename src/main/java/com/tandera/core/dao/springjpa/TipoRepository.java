package com.tandera.core.dao.springjpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tandera.core.model.comercial.Tipo;

public interface TipoRepository extends JpaRepository<Tipo, Integer> {
	
	public List<Tipo> findByDescrContainingIgnoreCase(String descr);
	
	public Tipo findById(Integer id);

}
