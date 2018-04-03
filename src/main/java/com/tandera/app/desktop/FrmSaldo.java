package com.tandera.app.desktop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tandera.core.dao.Repositorio;
import com.tandera.core.model.Conta;
import com.tandera.core.model.Saldo;

import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCaixaCombinacao;
import edu.porgamdor.util.desktop.ss.SSCampoDataHora;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.util.Formatador;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmSaldo extends Formulario {
	private SSCaixaCombinacao cboConta = new SSCaixaCombinacao();
	private SSCampoDataHora txtData = new SSCampoDataHora();
	private SSCampoNumero txtValor = new SSCampoNumero();

	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	@Autowired
	private Repositorio dao;
	
	public FrmSaldo() {
		init();
	}

	private void init() {
		// HERANÇA
		super.setTitulo("Cadastro de Naturezas");
		super.setDescricao("Descrição das Entradas e Saidas");
		getRodape().add(cmdSalvar);
		getRodape().add(cmdSair);
		// IMPORTANTE
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		panelCampos.setLayout(gbl_panelCampos);

		GridBagConstraints gbcCboConta = new GridBagConstraints();
		gbcCboConta.weightx = 2.0;
		gbcCboConta.insets = new Insets(5, 5, 0, 5);
		gbcCboConta.fill = GridBagConstraints.HORIZONTAL;
		gbcCboConta.gridx = 0;
		gbcCboConta.gridy = 0;
		cboConta.setRotulo("Conta");
		panelCampos.add(cboConta, gbcCboConta);
		
		GridBagConstraints gbcData = new GridBagConstraints();
		gbcData.fill = GridBagConstraints.HORIZONTAL;
		gbcData.anchor = GridBagConstraints.WEST;
		gbcData.insets = new Insets(5, 5, 5, 5);
		gbcData.gridx = 0;
		gbcData.gridy = 2;
		panelCampos.add(txtData, gbcData);
		txtData.setColunas(5);

		GridBagConstraints gbcValor = new GridBagConstraints();
		gbcValor.weightx = 2.0;
		gbcValor.insets = new Insets(5, 5, 0, 5);
		gbcValor.fill = GridBagConstraints.HORIZONTAL;
		gbcValor.gridx = 0;
		gbcValor.gridy = 1;
		panelCampos.add(txtValor, gbcValor);
		txtValor.setEditavel(false);
		txtData.setRotulo("Data");
		txtValor.setColunas(30);
		txtValor.setRotulo("Valor");
		txtValor.setFormato(Formato.MOEDA);
		cmdSair.setText("Fechar");
		cmdSalvar.setText("Salvar");

		// Listners = Comandos = Eventos
		cmdSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				salvar();
			}
		});
		cmdSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sair();
			}
		});
		cboConta.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		       mostrarValor();
		    }
		});

	}
	public void load() {
		cboConta.setItens(dao.listarContas(MDI.getPerfilId()),"nome");
		txtData.setValue(new Date());
	}
	private void mostrarValor() {
		Conta conta = (Conta) cboConta.getValue();
		txtValor.setValue(0.0d);
		if(conta!=null)
			txtValor.setValue(conta.getSaldo());
	}
	private void salvar() {
		try {
			Conta conta = (Conta) cboConta.getValue();
			if(conta!=null) {
				Saldo entidade = new Saldo();
				entidade.setConta(conta);
				entidade.setData(txtData.getDataHora());;
				entidade.setValor(txtValor.getDouble());
				dao.incluirSaldo(entidade, conta);
			}else
				SSMensagem.avisa("Selecione uma Conta");
				
			SSMensagem.informa("Saldo registrado com sucesso!!");
			fechar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void sair() {
		super.fechar();
	}

}
