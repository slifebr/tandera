package com.tandera.app.desktop.comercial;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tandera.core.dao.springjpa.MascaraPrecoRepository;
import com.tandera.core.model.comercial.MascaraPreco;

import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
public class FrmMascaraPreco extends Formulario {

	@Autowired
	private MascaraPrecoRepository dao;

	private MascaraPreco entidade;

	private SSCampoTexto txtMascara = new SSCampoTexto();
	private SSCampoNumero txtValor = new SSCampoNumero();

	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private JCheckBox chkNovo = new JCheckBox("Novo?");

	public FrmMascaraPreco() {
		init();
	}

	private void init() {
		super.setTitulo("Mascara Preço");
		super.setDescricao("Cadastro de Mascara de Preço");
		super.getRodape().add(chkNovo);
		super.getRodape().add(cmdSalvar);
		super.getRodape().add(cmdSair);

		// IMPORTANTE
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		panelCampos.setLayout(gbl_panelCampos);

		GridBagConstraints gbcTxtMascara = new GridBagConstraints();
		gbcTxtMascara.weightx = 2.0;
		gbcTxtMascara.insets = new Insets(5, 5, 5, 5);
		gbcTxtMascara.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtMascara.gridx = 0;
		gbcTxtMascara.gridy = 0;
		panelCampos.add(txtMascara, gbcTxtMascara);

		txtMascara.setColunas(10);
		txtMascara.setRotulo("Mascara");

		GridBagConstraints gbcTxtValor = new GridBagConstraints();
		gbcTxtValor.insets = new Insets(5, 5, 0, 5);
		gbcTxtValor.fill = GridBagConstraints.BOTH;
		gbcTxtValor.gridx = 0;
		gbcTxtValor.gridy = 1;
		txtValor.setRotulo("Valor");
		txtValor.setFormato(Formato.MOEDA);
		panelCampos.add(txtValor, gbcTxtValor);

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

	// public void setEntidade(Natureza entidade) {
	public void setEntidade(Object entidade) {
		this.entidade = (MascaraPreco) entidade;
		if (entidade != null)
			atribuir();
		else
			criar();
	}

	private void atribuir() {
		try {
			txtMascara.setValue(entidade.getMascara());
			txtValor.setValue(entidade.getValor());
			txtMascara.requestFocus();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void criar() {
		entidade = new MascaraPreco();
		atribuir();
	}

	private void salvar() {
		try {
			entidade.setMascara(txtMascara.getText());
			entidade.setValor(BigDecimal.valueOf(txtValor.getDouble()));

			if (entidade.getMascara() == null || entidade.getMascara().isEmpty() || entidade.getValor() == null
					|| entidade.getValor().equals(BigDecimal.ZERO)) {
				SSMensagem.avisa("Dados incompletos");
				return;
			}

			// dao.gravar(operacao, entidade);
			dao.save(entidade);

			SSMensagem.informa("Natureza registrado com sucesso!!");
			novo();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void novo() {
		if (chkNovo.isSelected()) {
			criar();
		} else
			super.fechar();
	}

	private void sair() {
		super.fechar();
	}

}
