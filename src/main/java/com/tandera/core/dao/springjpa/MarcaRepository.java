package com.tandera.core.dao.springjpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tandera.core.model.comercial.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {

	public List<Marca> findByDescrContainingIgnoreCase(String descr);

	public Marca findById(Integer id);

}
