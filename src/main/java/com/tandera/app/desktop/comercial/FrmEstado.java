package com.tandera.app.desktop.comercial;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tandera.core.dao.springjpa.EstadoRepository;
import com.tandera.core.model.Estado;

import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
public class FrmEstado extends Formulario {
	
	@Autowired
	private EstadoRepository dao;
	
	private Estado entidade;
	
	private SSCampoTexto txtDescr = new SSCampoTexto();
	private SSCampoTexto txtSigla = new SSCampoTexto();

	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private JCheckBox chkNovo = new JCheckBox("Novo?");

	public FrmEstado() {
		init();
	}
	
	private void init() {
		super.setTitulo("Estado");
		super.setDescricao("Cadastro de Estado");
		super.getRodape().add(chkNovo);
		super.getRodape().add(cmdSalvar);
		super.getRodape().add(cmdSair);

		// IMPORTANTE
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		panelCampos.setLayout(gbl_panelCampos);
		
		GridBagConstraints gbcTxtDescr = new GridBagConstraints();
		gbcTxtDescr.weightx = 2.0;
		gbcTxtDescr.insets = new Insets(5, 5, 5, 5);
		gbcTxtDescr.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtDescr.gridx = 0;
		gbcTxtDescr.gridy = 0;
		panelCampos.add(txtDescr, gbcTxtDescr);
		
		txtDescr.setColunas(10);
		txtDescr.setRotulo("Descrição");
		
		GridBagConstraints gbcTxtSigla = new GridBagConstraints();
		gbcTxtSigla.insets = new Insets(5, 5, 0, 5);
		gbcTxtSigla.fill = GridBagConstraints.BOTH;
		gbcTxtSigla.gridx = 0;
		gbcTxtSigla.gridy = 1;
		txtSigla.setRotulo("Sigla");
		panelCampos.add(txtSigla, gbcTxtSigla);
		
		cmdSair.setText("Fechar");
		cmdSalvar.setText("Salvar");
		
		adicionarListner();
	}
	
	private void adicionarListner() {
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
	}
	//public void setEntidade(Natureza entidade) {
			public void setEntidade(Object entidade) {
				this.entidade = (Estado) entidade;
				if (entidade != null)
					atribuir();
				else
					criar();
			}
			private void atribuir() {
				try {
					txtDescr.setValue(entidade.getDescr());
					txtSigla.setText(entidade.getSigla());
					txtDescr.requestFocus();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
			private void criar() {
				entidade = new Estado();
				atribuir();
			}
			private void salvar() {
				try {
					entidade.setDescr(txtDescr.getText());
					entidade.setSigla(txtSigla.getText());

					if (entidade.getDescr() == null || entidade.getDescr().isEmpty() || entidade.getSigla() == null
							|| entidade.getSigla().isEmpty() ) {
						SSMensagem.avisa("Dados incompletos");
						return;
					}

					
					//dao.gravar(operacao, entidade);
					dao.save(entidade);

					SSMensagem.informa("Natureza registrado com sucesso!!");
					novo();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
			private void novo() {
				if(chkNovo.isSelected()) {
					criar();
				}else
					super.fechar();
			}
			private void sair() {
				super.fechar();
			}

}
