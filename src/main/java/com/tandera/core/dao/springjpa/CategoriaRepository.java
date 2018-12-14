package com.tandera.core.dao.springjpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tandera.core.model.comercial.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria , Integer> {
	
	public List<Categoria> findByDescrContainingIgnoreCase(String descr);
	
	//public Categoria findByDescr(String descr);

}
