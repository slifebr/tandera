package com.tandera.app.spring;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tandera.core.config.PersistenceConfig;
import com.tandera.core.dao.Repositorio;
import com.tandera.core.dao.RepositorioLancamento;
import com.tandera.core.model.Lancamento;
import com.tandera.core.model.filter.Filtro;

public class Context {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
		RepositorioLancamento dao = context.getBean(RepositorioLancamento.class);
		
		Filtro filtro = new Filtro();
		filtro.setUsuario(1);
		filtro.setPrevisao(true);
		
		List<Lancamento> lista = dao.listarLancamentos(filtro);
		imprimir(lista);
	}

	static void imprimir(List lista) {
		for (Object item : lista) {
			System.out.println(item);
		}
	}
}
