package com.tandera.app.desktop;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tandera.core.dao.Repositorio;
import com.tandera.core.dao.RepositorioLancamento;
import com.tandera.core.model.Lancamento;
import com.tandera.core.model.TipoMovimento;

import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoDataHora;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmAmortizar extends Formulario {
	private SSCampoDataHora txtNovaData = new SSCampoDataHora();
	private SSCampoNumero txtValor = new SSCampoNumero();
	private SSCampoNumero txtValorAtual = new SSCampoNumero();
	private JTextArea txtDescricao = new JTextArea();

	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private Lancamento entidade;
	@Autowired
	private Repositorio dao;
	@Autowired
	private RepositorioLancamento lactoDao;

	public FrmAmortizar() {
		init();
	}

	private void init() {
		// HERANÇA
		super.setTitulo("Amortização");
		super.setDescricao("Compensação parcial e atualização de data e valor");
		getRodape().add(cmdSalvar);
		getRodape().add(cmdSair);
		// IMPORTANTE
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		panelCampos.setLayout(gbl_panelCampos);

		GridBagConstraints gbc_txtNovaData = new GridBagConstraints();
		gbc_txtNovaData.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNovaData.anchor = GridBagConstraints.WEST;
		gbc_txtNovaData.insets = new Insets(5, 5, 0, 5);
		gbc_txtNovaData.gridx = 0;
		gbc_txtNovaData.gridy = 0;
		txtNovaData.setColunas(10);
		txtNovaData.setComponenteCorFonte(Color.BLUE);
		txtNovaData.setComponenteNegrito(true);
		txtNovaData.setForeground(Color.BLUE);
		panelCampos.add(txtNovaData, gbc_txtNovaData);

		GridBagConstraints gbc_txtValor = new GridBagConstraints();
		gbc_txtValor.weightx = 2.0;
		gbc_txtValor.insets = new Insets(5, 5, 0, 5);
		gbc_txtValor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValor.gridx = 0;
		gbc_txtValor.gridy = 2;
		txtValor.setComponenteCorFonte(Color.BLUE);
		txtValor.setComponenteNegrito(true);
		panelCampos.add(txtValor, gbc_txtValor);

		GridBagConstraints gbc_txtValorAtual = new GridBagConstraints();
		gbc_txtValorAtual.weightx = 2.0;
		gbc_txtValorAtual.insets = new Insets(5, 5, 0, 5);
		gbc_txtValorAtual.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValorAtual.gridx = 0;
		gbc_txtValorAtual.gridy = 1;
		txtValorAtual.setFormato("#,##0.00");
		txtValorAtual.setEditavel(false);
		txtValorAtual.setRotulo("Valor Atual");
		txtValorAtual.setComponenteCorFonte(Color.BLUE);
		txtValorAtual.setComponenteNegrito(true);
		panelCampos.add(txtValorAtual, gbc_txtValorAtual);

		txtNovaData.setRotulo("Nova Data");
		txtValor.setColunas(10);
		txtValor.setRotulo("Valor");
		txtValor.setFormato(Formato.MOEDA);

		GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
		gbc_txtDescricao.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDescricao.weighty = 1.0;
		gbc_txtDescricao.insets = new Insets(5, 5, 0, 5);
		gbc_txtDescricao.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescricao.gridx = 0;
		gbc_txtDescricao.gridy = 3;
		txtDescricao.setLineWrap(true);
		txtDescricao.setWrapStyleWord(true);
		txtDescricao.setColumns(15);
		txtDescricao.setRows(4);
		// txtDescricao.setEnabled(false);
		txtDescricao.setText("DESCRIÇÃO DOS LANÇAMENTOS PREVISTOS");
		txtDescricao.setForeground(Color.BLUE);
		JScrollPane scrooll = new JScrollPane(txtDescricao);
		scrooll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrooll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelCampos.add(scrooll, gbc_txtDescricao);

		cmdSair.setText("Cancelar");
		cmdSalvar.setText("Confirmar");
		
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
		txtNovaData.requestFocus();
		txtNovaData.setValue(new Date());
		txtValor.setValue(0.0d);
	}

	private void salvar() {
		if (entidade.isTransferencia()) {
			SSMensagem.avisa("Não é possível amortizar transferência");
			return;
		}
		Double valor = entidade.getValor();
		if (valor < 0.0d) {
			valor = valor * -1;
		}
		Double amortizado = txtValor.getDouble();
		if (amortizado <= 0.0d || amortizado >= valor) {
			SSMensagem.avisa("Valor amortizado deve ser maior que zero e menor que ao valor atual");
			return;
		}
		if (SSMensagem.pergunta("Confirma amortizar este lançamento ?")) {
			lactoDao.amortizarLancamento(entidade.getId(), txtNovaData.getDataHora(), amortizado);
			SSMensagem.informa("Lançamento atualizado com sucesso!!");
			super.fechar();
		}

	}

	public void setId(Integer id) {
		entidade = dao.buscar(Lancamento.class, id);
		if (entidade != null) {
			txtNovaData.setDataHora(entidade.getQuitacao());
			txtValor.setRotulo(entidade.getTipoMovimento() + " - R$ Valor");
			txtValorAtual.setValue(entidade.getValor());
			txtDescricao.setText(entidade.getTipoMovimento() + "\n" + entidade.getDescricao());
			if (entidade.getTipoMovimento() == TipoMovimento.DEBITO) {
				txtNovaData.setComponenteCorFonte(Color.RED);
				txtValor.setComponenteCorFonte(Color.RED);
				txtValorAtual.setComponenteCorFonte(Color.RED);
				txtDescricao.setForeground(Color.RED);
			}
		}
	}

	private void sair() {
		super.fechar();
	}

}
