package com.tandera.core.dao.springjpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tandera.core.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado , Integer> {
	
	public List<Estado> findByDescrContainingIgnoreCase(String descr);
	
	public Estado findById(Integer id);
	

}
