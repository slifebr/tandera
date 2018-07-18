package com.tandera.core.dao.springjpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tandera.core.model.comercial.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria , Integer> {
	
	public List<Categoria> findByDescrContainingIgnoreCase(String descr);

}
