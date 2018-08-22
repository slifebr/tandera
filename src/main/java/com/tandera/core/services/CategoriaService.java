package com.tandera.core.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tandera.core.dao.springjpa.CategoriaRepository;
import com.tandera.core.model.comercial.Categoria;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> listarTodos(){
		return categoriaRepository.findAll();
	};
	
	public Categoria salvar(Categoria categoria){
		return categoriaRepository.save(categoria);
	}
	
}
