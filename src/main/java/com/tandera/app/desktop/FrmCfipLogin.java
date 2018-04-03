package com.tandera.app.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tandera.app.spring.SpringDesktopApp;
import com.tandera.core.dao.springjpa.UsuarioRepositorio;
import com.tandera.core.model.Usuario;

import edu.porgamdor.util.desktop.ambiente.FrmLogin;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmCfipLogin extends FrmLogin {
	@Autowired
	private UsuarioRepositorio repositorio;

	public FrmCfipLogin() {
		super.logar(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logarAction();
			}
		});
	}

	private void logarAction() {
		try {
			Usuario perfil = repositorio.login(getLogin(), getSenhaMD5());
			MDICfip mdi = SpringDesktopApp.getBean(MDICfip.class);
			FrmUsuario frm = SpringDesktopApp.getBean(FrmUsuario.class);
			if(!iniciarAplicacao(mdi, perfil))
				abrirCadastroPerfil(frm, new Usuario());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
