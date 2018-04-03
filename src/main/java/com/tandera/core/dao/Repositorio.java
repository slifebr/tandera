package com.tandera.core.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tandera.core.model.Categoria;
import com.tandera.core.model.Conta;
import com.tandera.core.model.Contato;
import com.tandera.core.model.DespesaRapida;
import com.tandera.core.model.Natureza;
import com.tandera.core.model.Saldo;
import com.tandera.core.model.TipoMovimento;
import com.tandera.core.model.Usuario;

import edu.porgamdor.util.desktop.ambiente.TipoOperacao;

@Repository
public class Repositorio {
	private static Logger LOG = Logger.getLogger(Repositorio.class.getName());
	@PersistenceContext(unitName = "PU_CFIP")
	private EntityManager manager;

	@Transactional
	public void incluir(Object entidade) {
		manager.persist(entidade);
	}

	@Transactional
	public Object alterar(Object entidade) {
		return manager.merge(entidade);
	}
	@Transactional
	public Object incluirSaldo(Saldo saldo, Conta conta) {
		manager.persist(saldo);
		conta.setSiValor(saldo.getValor());
		conta.setSiData(saldo.getData());
		return manager.merge(conta);
	}

	@Transactional
	public void gravar(TipoOperacao operacao, Object entidade) {
		if (TipoOperacao.INCLUSAO == operacao)
			manager.persist(entidade);
		else
			manager.merge(entidade);
	}

	public <T> T buscar(Class entidade, Integer id) {
		return (T) manager.find(entidade, id);
	}

	public List<Conta> listarContas(Integer usuario) {
		Query query = manager
				.createQuery("SELECT e FROM Conta e WHERE e.excluido = false and e.usuario = :usuario ORDER BY e.nome");
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}

	public List<Conta> listarContas(Integer usuario, String nome) {
		Query query = manager.createQuery(
				"SELECT e FROM Conta e WHERE e.excluido = false and e.usuario = :usuario and e.nome like :nome");
		query.setParameter("usuario", usuario);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}

	public List<Conta> listarContas(Integer usuario,Integer id) {
		StringBuilder sql = new StringBuilder("SELECT e FROM Conta e WHERE e.excluido = false AND saldo >0 AND  e.usuario = :usuario");
		if(id!=null)
			sql.append(" AND e.id = :id");
		sql.append(" ORDER BY e.nome");
		Query query = manager.createQuery(sql.toString());
		query.setParameter("usuario", usuario);
		if(id!=null)
			query.setParameter("id", id);
		
		return query.getResultList();
	}
	public List<Natureza> listarNaturezas(Integer usuario, String nome) {
		Query query = manager.createQuery(
				"SELECT e FROM Natureza e WHERE e.excluido = false and e.usuario = :usuario AND e.nome LIKE :nome ORDER BY e.nome");
		query.setParameter("usuario", usuario);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}

	public List<Natureza> listarNaturezas(Integer usuario) {
		Query query = manager.createQuery(
				"SELECT e FROM Natureza e WHERE e.excluido = false and e.usuario = :usuario ORDER BY e.tipoMovimento, e.nome");
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}

	public List<Natureza> listarNaturezas(Integer usuario, TipoMovimento tipo) {
		Query query = manager.createQuery(
				"SELECT e FROM Natureza e WHERE e.excluido = false AND e.usuario = :usuario AND e.tipoMovimento=:tipoMovto ORDER BY e.nome");
		query.setParameter("usuario", usuario);
		query.setParameter("tipoMovto", tipo);
		return query.getResultList();
	}

	public List<Contato> listarContatos(Integer usuario, String nome) {
		Query query = manager.createQuery(
				"SELECT e FROM Contato e WHERE e.excluido = false and e.usuario = :usuario and e.nome like :nome");
		query.setParameter("usuario", usuario);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}

	public List<Contato> listarContatos(Integer usuario) {
		Query query = manager.createQuery(
				"SELECT e FROM Contato e WHERE e.excluido = false and e.usuario = :usuario ORDER BY e.nome");
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}

	public List<DespesaRapida> listarDespesasRapidas(Integer usuario) {
		Query query = manager.createQuery(
				"SELECT e FROM DespesaRapida e WHERE e.excluido = false AND e.usuario = :usuario ORDER BY e.ordem");
		query.setParameter("usuario", usuario);
		return query.getResultList();
	}

	public List<Saldo> listarSaldos(Integer conta) {
		Query query = manager.createQuery("SELECT e FROM Saldo e WHERE e.conta.id = :conta ORDER BY e.data DESC");
		query.setParameter("conta", conta);
		return query.getResultList();
	}
	

	@Transactional
	public Usuario incluirUsuario(Usuario usuario) {
		manager.persist(usuario);
		// INCLUSAO DAS CONTAS
		Conta conta = new Conta();
		conta.setNome("CARTEIRA");
		conta.setSigla("CTR");
		conta.setUsuario(usuario.getId());
		conta = manager.merge(conta);
		Saldo saldo = new Saldo();
		saldo.setConta(conta);
		manager.persist(saldo);
		
		conta = new Conta();
		conta.setNome("CONTA CORRENTE");
		conta.setSigla("CCR");
		conta.setUsuario(usuario.getId());
		conta = manager.merge(conta);
		saldo = new Saldo();
		saldo.setConta(conta);
		manager.persist(saldo);
		
		conta = new Conta();
		conta.setNome("CONTA POUPANCA");
		conta.setSigla("CPA");
		conta.setUsuario(usuario.getId());
		conta = manager.merge(conta);
		saldo = new Saldo();
		saldo.setConta(conta);
		manager.persist(saldo);
		
		// INCLUSAO DAS NATUREZAS
		Natureza natureza = new Natureza();
		natureza.setDescricao("SALDO INICIAL");
		natureza.setNome("SALDO INICIAL");
		natureza.setUsuario(usuario.getId());
		natureza.setTipoMovimento(TipoMovimento.CREDITO);
		natureza.setCategoria(Categoria.REMUNERACAO);
		manager.persist(natureza);

		natureza = new Natureza();
		natureza.setDescricao("SALARIO");
		natureza.setNome("SALARIO");
		natureza.setUsuario(usuario.getId());
		natureza.setTipoMovimento(TipoMovimento.CREDITO);
		natureza.setCategoria(Categoria.REMUNERACAO);
		manager.persist(natureza);

		natureza = new Natureza();
		natureza.setDescricao("DESPEAS");
		natureza.setNome("DESPESAS");
		natureza.setUsuario(usuario.getId());
		natureza.setTipoMovimento(TipoMovimento.DEBITO);
		natureza.setCategoria(Categoria.DESPESA);
		manager.persist(natureza);

		natureza = new Natureza();
		natureza.setDescricao("TRANSFERENCIA");
		natureza.setNome("TRANSFERENCIA");
		natureza.setUsuario(usuario.getId());
		natureza.setTipoMovimento(TipoMovimento.TRANSFERENCIA);
		natureza.setCategoria(Categoria.TRANSACOES);
		manager.persist(natureza);

		natureza = new Natureza();
		natureza.setDescricao("ESTORNO DA TRANSFERENCIA");
		natureza.setNome("ESTORNO");
		natureza.setUsuario(usuario.getId());
		natureza.setTipoMovimento(TipoMovimento.TRANSFERENCIA);
		manager.persist(natureza);

		return usuario;
	}
}
