package com.tandera.core.dao.springjpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tandera.core.model.cadastro.LiberacaoUsuario;

public interface LiberacaoUsuarioRepository extends JpaRepository<LiberacaoUsuario, Integer>{
	
	public LiberacaoUsuario findByChaveIgnoreCase(String chave);

}
