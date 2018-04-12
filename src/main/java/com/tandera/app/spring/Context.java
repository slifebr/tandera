package com.tandera.app.spring;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tandera.core.config.PersistenceConfig;
import com.tandera.core.dao.springjpa.TamanhoRepository;
import com.tandera.core.model.comercial.Tamanho;

public class Context {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
		//RepositorioLancamento dao = context.getBean(RepositorioLancamento.class);
		TamanhoRepository dao = context.getBean(TamanhoRepository.class);
		List <Tamanho> tamanhos = dao.findByDescrContainingIgnoreCase("grande");
		imprimir(tamanhos);
		/*
		Filtro filtro = new Filtro();
		filtro.setUsuario(1);
		filtro.setPrevisao(true);
		
		List<Lancamento> lista = dao.listarLancamentos(filtro);
		imprimir(lista);
		*/
	}

	static void imprimir(List lista) {
		System.out.println("aui");
		for (Object item : lista) {
			System.out.println(item);
		}
	}
}
