package com.tandera.app.desktop;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
public class FrmAtualizar extends Formulario {
	private SSCampoDataHora txtData = new SSCampoDataHora();
	private SSCampoDataHora txtDataPagamento = new SSCampoDataHora();
	private SSCampoNumero txtValorAtualizado = new SSCampoNumero();
	private SSCampoNumero txtValorDocumento = new SSCampoNumero();
	private JTextArea txtDescricao = new JTextArea();

	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private Lancamento entidade;
	@Autowired
	private Repositorio dao;
	@Autowired
	private RepositorioLancamento lanctoDao;
	private SSCampoNumero txtAliqMulta = new SSCampoNumero();
	private SSCampoNumero txtValorMulta = new SSCampoNumero();
	private SSCampoNumero txtAliqJuros = new SSCampoNumero();
	private SSCampoNumero txtValorJuros = new SSCampoNumero();
	private SSCampoNumero txtAliqDesconto = new SSCampoNumero();
	private SSCampoNumero txtValorDesconto = new SSCampoNumero();
	private SSCampoNumero txtAliqAtualizacao = new SSCampoNumero();
	private SSCampoNumero txtValorAtualizacao = new SSCampoNumero();
	
	public FrmAtualizar() {
		init();
	}

	private void init() {
		// HERANÇA
		super.setTitulo("Atualização");
		super.setDescricao("Realiza atualização dos lançamentos");
		getRodape().add(cmdSalvar);
		getRodape().add(cmdSair);
		// IMPORTANTE
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		panelCampos.setLayout(gbl_panelCampos);
		
		txtDataPagamento.setRotulo("Pagamento");

		GridBagConstraints gbc_txtData = new GridBagConstraints();
		gbc_txtData.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtData.anchor = GridBagConstraints.NORTHEAST;
		gbc_txtData.insets = new Insets(5, 5, 0, 0);
		gbc_txtData.gridx = 0;
		gbc_txtData.gridy = 0;
		txtData.setEditavel(false);
		txtData.setColunas(5);
		txtData.setComponenteCorFonte(Color.BLUE);
		txtData.setComponenteNegrito(true);
		txtData.setForeground(Color.BLUE);
		panelCampos.add(txtData, gbc_txtData);

		GridBagConstraints gbc_txtValorAtualizado = new GridBagConstraints();
		gbc_txtValorAtualizado.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtValorAtualizado.insets = new Insets(5, 5, 0, 0);
		gbc_txtValorAtualizado.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValorAtualizado.gridx = 0;
		gbc_txtValorAtualizado.gridy = 5;
		txtValorAtualizado.setEditavel(false);
		txtValorAtualizado.setComponenteCorFonte(Color.BLUE);
		txtValorAtualizado.setComponenteNegrito(true);
		panelCampos.add(txtValorAtualizado, gbc_txtValorAtualizado);
		
		GridBagConstraints gbc_txtDataPagamento = new GridBagConstraints();
		gbc_txtDataPagamento.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDataPagamento.weightx = 1.0;
		gbc_txtDataPagamento.insets = new Insets(5, 5, 0, 5);
		gbc_txtDataPagamento.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDataPagamento.gridx = 1;
		gbc_txtDataPagamento.gridy = 5;
		txtDataPagamento.setComponenteNegrito(true);
		panelCampos.add(txtDataPagamento, gbc_txtDataPagamento);

		GridBagConstraints gbc_txtValorDocumento = new GridBagConstraints();
		gbc_txtValorDocumento.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtValorDocumento.weightx = 1.0;
		gbc_txtValorDocumento.insets = new Insets(5, 5, 0, 5);
		gbc_txtValorDocumento.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValorDocumento.gridx = 1;
		gbc_txtValorDocumento.gridy = 0;
		txtValorDocumento.setFormato("#,##0.00");
		txtValorDocumento.setEditavel(false);
		txtValorDocumento.setRotulo("R$ Documento");
		txtValorDocumento.setComponenteCorFonte(Color.BLUE);
		txtValorDocumento.setComponenteNegrito(true);
		panelCampos.add(txtValorDocumento, gbc_txtValorDocumento);

		txtData.setRotulo("Data");
		txtValorAtualizado.setRotulo("R$ Atualizado");
		txtValorAtualizado.setFormato(Formato.MOEDA);

		GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
		gbc_txtDescricao.gridwidth = 2;
		gbc_txtDescricao.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDescricao.weighty = 1.0;
		gbc_txtDescricao.insets = new Insets(5, 5, 0, 0);
		gbc_txtDescricao.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescricao.gridx = 0;
		gbc_txtDescricao.gridy = 6;
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
		
		GridBagConstraints gbc_txtAliqMulta = new GridBagConstraints();
		gbc_txtAliqMulta.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtAliqMulta.insets = new Insets(5, 5, 0, 0);
		gbc_txtAliqMulta.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAliqMulta.gridx = 0;
		gbc_txtAliqMulta.gridy = 1;
		txtAliqMulta.setFormato("#,##0.00");
		txtAliqMulta.setEditavel(false);
		txtAliqMulta.setRotulo("%");
		txtAliqMulta.setColunas(3);
		panelCampos.add(txtAliqMulta, gbc_txtAliqMulta);
		
		GridBagConstraints gbc_txtAliqJuros = new GridBagConstraints();
		gbc_txtAliqJuros.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtAliqJuros.insets = new Insets(5, 5, 0, 0);
		gbc_txtAliqJuros.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAliqJuros.gridx = 0;
		gbc_txtAliqJuros.gridy = 2;
		txtAliqJuros.setFormato("#,##0.00");
		txtAliqJuros.setEditavel(false);
		txtAliqJuros.setRotulo("%");
		txtAliqJuros.setColunas(3);
		panelCampos.add(txtAliqJuros, gbc_txtAliqJuros);
		
		GridBagConstraints gbc_txtAliqAtualizacao = new GridBagConstraints();
		gbc_txtAliqAtualizacao.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtAliqAtualizacao.insets = new Insets(5, 5, 0, 0);
		gbc_txtAliqAtualizacao.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAliqAtualizacao.gridx = 0;
		gbc_txtAliqAtualizacao.gridy = 3;
		txtAliqAtualizacao.setFormato("#,##0.00");
		txtAliqAtualizacao.setEditavel(false);
		txtAliqAtualizacao.setRotulo("%");
		txtAliqAtualizacao.setColunas(3);
		panelCampos.add(txtAliqAtualizacao, gbc_txtAliqAtualizacao);
		
		GridBagConstraints gbc_txtAliqDesconto = new GridBagConstraints();
		gbc_txtAliqDesconto.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtAliqDesconto.insets = new Insets(5, 5, 0, 0);
		gbc_txtAliqDesconto.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAliqDesconto.gridx = 0;
		gbc_txtAliqDesconto.gridy = 4;
		txtAliqDesconto.setFormato("#,##0.00");
		txtAliqDesconto.setEditavel(false);
		txtAliqDesconto.setRotulo("%");
		txtAliqDesconto.setColunas(3);
		panelCampos.add(txtAliqDesconto, gbc_txtAliqDesconto);
		
		
		GridBagConstraints gbc_txtValorMulta = new GridBagConstraints();
		gbc_txtValorMulta.weightx = 1.0;
		gbc_txtValorMulta.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtValorMulta.insets = new Insets(5, 5, 0, 5);
		gbc_txtValorMulta.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValorMulta.gridx = 1;
		gbc_txtValorMulta.gridy = 1;
		txtValorMulta.setFormato("#,##0.00");
		txtValorMulta.setRotulo("Multa");
		txtValorMulta.setColunas(8);
		panelCampos.add(txtValorMulta, gbc_txtValorMulta);
		
		GridBagConstraints gbc_txtValorJuros = new GridBagConstraints();
		gbc_txtValorJuros.weightx = 1.0;
		gbc_txtValorJuros.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtValorJuros.insets = new Insets(5, 5, 0, 5);
		gbc_txtValorJuros.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValorJuros.gridx = 1;
		gbc_txtValorJuros.gridy = 2;
		txtValorJuros.setFormato("#,##0.00");
		txtValorJuros.setRotulo("Juros");
		txtValorJuros.setColunas(8);
		panelCampos.add(txtValorJuros, gbc_txtValorJuros);
		
		GridBagConstraints gbc_txtValorAtualizacao = new GridBagConstraints();
		gbc_txtValorAtualizacao.weightx = 1.0;
		gbc_txtValorAtualizacao.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtValorAtualizacao.insets = new Insets(5, 5, 0, 5);
		gbc_txtValorAtualizacao.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValorAtualizacao.gridx = 1;
		gbc_txtValorAtualizacao.gridy = 3;
		txtValorAtualizacao.setFormato("#,##0.00");
		txtValorAtualizacao.setRotulo("Atualização");
		txtValorAtualizacao.setColunas(8);
		panelCampos.add(txtValorAtualizacao, gbc_txtValorAtualizacao);
		
		GridBagConstraints gbc_txtValorDesconto = new GridBagConstraints();
		gbc_txtValorDesconto.weightx = 1.0;
		gbc_txtValorDesconto.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtValorDesconto.insets = new Insets(5, 5, 0, 5);
		gbc_txtValorDesconto.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValorDesconto.gridx = 1;
		gbc_txtValorDesconto.gridy = 4;
		txtValorDesconto.setFormato("#,##0.00");
		txtValorDesconto.setRotulo("Desconto");
		txtValorDesconto.setColunas(8);
		panelCampos.add(txtValorDesconto, gbc_txtValorDesconto);
		
		txtData.requestFocus();
		txtData.setValue(new Date());

		txtValorMulta.addFocusListener(calcularEvento());
		txtValorJuros.addFocusListener(calcularEvento());
		txtValorAtualizacao.addFocusListener(calcularEvento());
		txtValorDesconto.addFocusListener(calcularEvento());
		txtDataPagamento.addFocusListener(calcularEvento());
		
	}
	private FocusListener calcularEvento() {
		return new FocusListener() {
			public void focusGained(FocusEvent e) {
				calcularValor();
			};

			public void focusLost(FocusEvent e) {
				calcularValor();
			}
		};
	}
	private void salvar() {
		if (SSMensagem.pergunta("Confirma atualizar este lançamento ?")) {
			entidade.setQuitacao(txtDataPagamento.getDataHora());
			dao.alterar(entidade);
			SSMensagem.informa("Lançamento atualizado com sucesso!");
			super.fechar();
		}
	}
	public void setId(Integer id) {
		entidade = dao.buscar(Lancamento.class, id);
		if (entidade != null) {
			txtData.setDataHora(entidade.getQuitacao());
			super.setTitulo("Atualização de " + entidade.getTipoMovimento());
			
			txtValorDocumento.setValue(entidade.getValorPrincipal());
			txtValorAtualizado.setValue(entidade.getValor());
			
			txtDescricao.setText(entidade.getTipoMovimento() + ":" + entidade.getDescricao());
			txtAliqAtualizacao.setValue(entidade.getAtualizacaoAliquota());
			txtAliqJuros.setValue(entidade.getJurosAliquota());
			txtAliqMulta.setValue(entidade.getMultaAliquota());
			txtAliqDesconto.setValue(entidade.getDescontoAliquota());
			txtValorAtualizacao.setValue(entidade.getAtualizacaoValor());
			txtValorMulta.setValue(entidade.getMultaValor());
			txtValorJuros.setValue(entidade.getJurosValor());
			txtValorDesconto.setValue(entidade.getDescontoValor());
			
			if (entidade.getTipoMovimento() == TipoMovimento.DEBITO) {
				txtData.setComponenteCorFonte(Color.RED);
				txtValorAtualizado.setComponenteCorFonte(Color.RED);
				txtValorDocumento.setComponenteCorFonte(Color.RED);
				txtDescricao.setForeground(Color.RED);
			}
		}
	}
	private void calcularValor() {
		entidade.setValorPrincipal(txtValorDocumento.getDoubleOuZero());
		entidade.setMultaValor(txtValorMulta.getDoubleOuZero());
		entidade.setJurosValor(txtValorJuros.getDoubleOuZero());
		entidade.setAtualizacaoValor(txtValorAtualizacao.getDoubleOuZero());
		entidade.setDescontoValor(txtValorDesconto.getDoubleOuZero());
		entidade.calcularValorAtual();
		txtValorAtualizado.setValue(entidade.getValor());
	}
	private void sair() {
		super.fechar();
	}

}
