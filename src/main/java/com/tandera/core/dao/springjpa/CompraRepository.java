package com.tandera.core.dao.springjpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tandera.core.model.orcamento.Compra;
import com.tandera.core.model.orcamento.ItemCompra;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
	
	public List<Compra> findByNomeContainingIgnoreCase(String nome);
	
	@Query(value = " SELECT c from ItemCompra c where c.compra.id = :compra_id")
	public List<ItemCompra> listaItens (@Param("compra_id")Integer compraId);
	
//	@Query(value = "from compra where data > :ano or (ano = :ano and semestre >= :semestre)")
//	List<Compra> findPeriodosPosteriores(@Param("ano") Integer ano, @Param("semestre") Integer semestre);

}
