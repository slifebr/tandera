package com.tandera.app.desktop;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tandera.app.spring.SpringDesktopApp;
import com.tandera.core.dao.Repositorio;
import com.tandera.core.model.Conta;

import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
public class FrmConta extends Formulario {
	@Autowired
	private Repositorio dao;
	private SSCampoTexto txtNome = new SSCampoTexto();
	private SSCampoTexto txtSigla = new SSCampoTexto();
	private SSCampoNumero txtSaldo = new SSCampoNumero();

	// bototes
	private SSBotao cmdFechar = new SSBotao();
	private SSBotao cmdSalvar = new SSBotao();
	
	private Conta entidade;
	public FrmConta() {
		init();
	}
	
	private void init() {
		// CABECALHO
		setTitulo("Formulario Conta");
		setDescricao("Cadastro das contas do sistema");
				txtNome.setRotulo("Nome");
		txtSigla.setRotulo("Sigla");
		txtSaldo.setRotulo("Saldo");

		cmdSalvar.setText("Salvar");
		cmdFechar.setText("Fechar");
		txtSaldo.setFormato(Formato.MOEDA);
		txtSaldo.setEditavel(false);

		//
		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.insets = new Insets(5, 5, 0, 5);
		gbc_txtNome.fill = GridBagConstraints.BOTH;
		gbc_txtNome.gridx = 0;
		gbc_txtNome.gridy = 0;
		getConteudo().add(txtNome, gbc_txtNome);

		//
		GridBagConstraints gbc_txtSigla = new GridBagConstraints();
		gbc_txtSigla.insets = new Insets(5, 5, 0, 5);
		gbc_txtSigla.fill = GridBagConstraints.BOTH;
		gbc_txtSigla.gridx = 0;
		gbc_txtSigla.gridy = 1;
		getConteudo().add(txtSigla, gbc_txtSigla);

		//
		GridBagConstraints gbc_txtSaldo = new GridBagConstraints();
		gbc_txtSaldo.weightx = 1.0;
		gbc_txtSaldo.insets = new Insets(5, 5, 5, 5);
		gbc_txtSaldo.fill = GridBagConstraints.BOTH;
		gbc_txtSaldo.gridx = 0;
		gbc_txtSaldo.gridy = 2;
		getConteudo().add(txtSaldo, gbc_txtSaldo);

		// rodape
		getRodape().add(cmdSalvar);
		getRodape().add(cmdFechar);
		// métodos
		cmdFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		cmdSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
	}
	@Override
	public void setEntidade(Object conta) {
		this.entidade=(Conta) conta;
		if(entidade==null) 
			novo();
		else
			atribuir();
	}
	private void atribuir() {
		try {
			txtNome.requestFocus();
			txtNome.setValue(entidade.getNome());
			txtSigla.setText(entidade.getSigla());
			txtSaldo.setValue(entidade.getSaldo());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void novo() {
		entidade = new Conta();
		atribuir();
	}
	private void sair() {
		super.cancelar();
	}
	private void salvar() {
		try {
			if (entidade == null) {
				entidade = new Conta();
			}
			entidade.setNome(txtNome.getText());
			entidade.setSigla(txtSigla.getText());
			entidade.setUsuario(MDI.getPerfilId());

			if (entidade.getNome() == null || entidade.getNome().isEmpty() || entidade.getSigla() == null
					|| entidade.getSigla().isEmpty()) {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			
			if(entidade.getId()==null)
				dao.incluir(entidade);
			else
				dao.alterar(entidade);
			
			SSMensagem.informa("Conta registrada com sucesso!!");
			novo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
