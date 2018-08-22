package com.tandera.core.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tandera.core.model.comercial.Categoria;

@Repository
public class RepositorioOrcamento {
	private static Logger LOG = Logger.getLogger(RepositorioOrcamento.class.getName());
	@PersistenceContext(unitName = "PU_CFIP")
	private EntityManager manager;
	
	public List<Categoria> listarCategoria() {
		Query query = manager
				.createQuery("SELECT c FROM Categoria c ORDER BY c.descr");
		//query.setParameter("id", id);
		return query.getResultList();
	}

}
