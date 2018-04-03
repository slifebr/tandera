package com.tandera.core.dao.springjpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tandera.core.model.Conta;

public interface ContaRepositorio extends JpaRepository<Conta, Integer> {
	//1
	public Conta findById(Integer id);
	//2
	public List<Conta> findByUsuarioAndNome(Integer usuario, String nome);
	//3
	@Query(nativeQuery=false, value="SELECT e FROM Conta e WHERE e.excluido = false and e.usuario = :usuario and e.nome like :nome")
	public List<Conta> listar(@Param("usuario") Integer usuario, @Param("nome") String nome);
	//4 e ... - UsuarioRepositorio
}
