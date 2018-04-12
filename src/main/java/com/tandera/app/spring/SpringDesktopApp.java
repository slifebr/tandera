package com.tandera.app.spring;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tandera.app.desktop.FrmCfipLogin;
import com.tandera.app.spring.config.AppConfig;
import com.tandera.core.config.PersistenceConfig;

import edu.porgamdor.util.desktop.DesktopApp;

public class SpringDesktopApp extends DesktopApp {
	/**
	 * 1 CADASTRO DE USUARIO - PERFIL VIA APLICAÇÃO
	 * 1 CONHECER AS CLASSES UTEIS DO NOSSO AMBIENTE
	 * 
	 * 2 CONFIGURAR DATA SOURCE DINAMICAMENTE
	 * 3 PERFIL E SESSAO
	 * 3 REGISTRAR O USUARIO LOGADO NA NOSSA SESSÃO
	 * 
	 * E POR FIM EXPLORAR A FERRAMENTA COMO UM USUÁRIO
	 */
	
	//3 public static int USUARIO = 1;
	
	private static AnnotationConfigApplicationContext context;
	public static void main(String[] args) {
		try {
			if (DesktopApp.iniciarAplicacao()) {
				context = new AnnotationConfigApplicationContext(AppConfig.class, PersistenceConfig.class);
				FrmCfipLogin frm = context.getBean(FrmCfipLogin.class);
				frm.exibir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static AnnotationConfigApplicationContext getContext() {
		return context;
	}

	public static <T> T getBean(Class bean) {
		return (T) context.getBean(bean);
	}

	public static <T> T getBean(String bean) {
		return (T) context.getBean(bean);
	}
	public static void closeContext() {
		((ConfigurableApplicationContext)context ).close();
	}
}
