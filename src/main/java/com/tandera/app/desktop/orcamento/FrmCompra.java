package com.tandera.app.desktop.orcamento;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tandera.core.dao.springjpa.CompraRepository;
import com.tandera.core.dao.springjpa.PessoaRepository;
import com.tandera.core.model.cadastro.Pessoa;
import com.tandera.core.model.enuns.StatusOrcamento;
import com.tandera.core.model.orcamento.Compra;
import com.tandera.core.model.orcamento.ItemCompra;

import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.ss.PosicaoRotulo;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCaixaCombinacao;
import edu.porgamdor.util.desktop.ss.SSCampoDataHora;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSGrade;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.util.Validacao;
import javax.swing.border.LineBorder;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;

@Component
public class FrmCompra extends Formulario {
	
	@Autowired
	private CompraRepository dao;
	
	@Autowired
	private PessoaRepository daoP;
	
	private Compra entidade;
	
	private SSCampoNumero txtCodigo = new SSCampoNumero();
	private SSCampoDataHora txtData = new SSCampoDataHora();
	private SSCaixaCombinacao cboStatus = new SSCaixaCombinacao();
	private SSCampoNumero txtIdPessoa = new SSCampoNumero();
	private SSCampoTexto txtNome = new SSCampoTexto();
	private SSCampoTexto txtTelefone = new SSCampoTexto();
	private SSCampoTexto txtObs = new SSCampoTexto();
	private SSCampoNumero txtDeposito = new SSCampoNumero();
	private SSCampoNumero txtTroca = new SSCampoNumero();
	private SSCampoNumero txtDoacao = new SSCampoNumero();
	
	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private JCheckBox chkNovo = new JCheckBox("Novo?");
	
	private JScrollPane scroll = new JScrollPane();
	private SSGrade tabela = new SSGrade();
	/**
	 * @wbp.nonvisual location=-40,239
	 */
	//private final JPanel panel = new JPanel();
	private final JPanel panel_grade = new JPanel();
	private final JPanel panel = new JPanel();
	
	public FrmCompra() {
		getConteudo().setBackground(Color.YELLOW);
		getConteudo().setLayout(null);
		panel_grade.setBackground(Color.MAGENTA);
		panel_grade.setBounds(0, 190, 704, 270);
		getConteudo().add(panel_grade);
		
		configurarCamposTabela();
		configurarConstraintsGrid();
		adicionarScrollGrade();
		
		init();
	}

	private void init() {
		super.setTitulo("Orçamento");
		super.setDescricao("Lançamentos de Orçamentos");
		super.getRodape().add(chkNovo);
		super.getRodape().add(cmdSalvar);
		super.getRodape().add(cmdSair);
		
		//StatusOrcamento[] status = StatusOrcamento.values();
		
		cboStatus.setItens(StatusOrcamento.values());;
	
		

		// IMPORTANTE
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelCampos.setLayout(null);
		panelCampos.setPreferredSize(new Dimension(250, 250));
		
		txtCodigo.setBounds(10, 5, 80, 50);
		panelCampos.add(txtCodigo);
		txtCodigo.setColunas(10);
		txtCodigo.setEnabled(false);
		txtCodigo.setRotulo("Código");
		
		txtData.setBounds(100, 5, 146, 50);
		panelCampos.add(txtData);
		txtData.setColunas(10);
		txtData.setRotulo("Data");
		
		cboStatus.setBounds(489, 5, 205, 50);
		cboStatus.setRotulo("Status");
		panelCampos.add(cboStatus);
		
		txtIdPessoa.setBounds(10, 57, 80, 50);
		panelCampos.add(txtIdPessoa);
		txtIdPessoa.setColunas(10);
		txtIdPessoa.setRotulo("Cód. Pessoa");
		
		txtNome.setBounds(100, 57, 379, 50);
		txtNome.setRotulo("Nome");
		txtNome.setColunas(10);
		panelCampos.add(txtNome);
		
		txtTelefone.setBounds(489, 57, 205, 50);
		txtTelefone.setRotulo("Telefone");
		txtTelefone.setColunas(10);
		panelCampos.add(txtTelefone);
		txtObs.getComponente().setMinimumSize(new Dimension(6, 60));
		txtObs.getComponente().setPreferredSize(new Dimension(6, 60));
		
		txtObs.setBounds(10, 109, 469, 77);
		txtObs.setRotulo("Obs.");
		txtObs.setColunas(10);
		panelCampos.add(txtObs);
		txtDeposito.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		
		txtDeposito.setBounds(489, 109, 205, 25);
		txtDeposito.setRotulo("Depósito ");
		txtDeposito.setColunas(10);
		panelCampos.add(txtDeposito);
		txtTroca.getComponente().setBounds(30, 15, 175, 20);
		txtTroca.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		
		txtTroca.setBounds(489, 135, 205, 25);
		txtTroca.setRotulo("Troca      ");
		txtTroca.setColunas(10);
		panelCampos.add(txtTroca);
		txtDoacao.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		
		txtDoacao.setBounds(489, 161, 205, 25);
		txtDoacao.setRotulo("Doação   ");
		txtDoacao.setColunas(10);
		panelCampos.add(txtDoacao);

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
		this.entidade = (Compra) entidade;
		if (entidade != null)
			atribuir();
		else
			criar();
	}
	
	private void configurarConstraintsGrid() {
		// constraints - grid bag layout
		GridBagConstraints gbcTxtFiltro = new GridBagConstraints();
		gbcTxtFiltro.weightx = 1.0;
		gbcTxtFiltro.anchor = GridBagConstraints.NORTHWEST;
		gbcTxtFiltro.insets = new Insets(5, 5, 5, 5);
		gbcTxtFiltro.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtFiltro.gridx = 0;
		gbcTxtFiltro.gridy = 0;

		GridBagConstraints gbcCmdBuscar = new GridBagConstraints();
		gbcCmdBuscar.anchor = GridBagConstraints.SOUTHWEST;
		gbcCmdBuscar.fill = GridBagConstraints.HORIZONTAL;
		gbcCmdBuscar.insets = new Insets(0, 0, 5, 5);
		gbcCmdBuscar.gridx = 1;
		gbcCmdBuscar.gridy = 0;
	}
	
	private void configurarCamposTabela() {
		// campos da tabela
		// BASICAMENTE O QUE VC TERÁ QUE MUDAR ENTRE FORMULARIOS
		tabela.getModeloTabela().addColumn("Item");
		tabela.getModeloTabela().addColumn("Categoria");
		tabela.getModeloTabela().addColumn("Marca");
		tabela.getModeloTabela().addColumn("Tamanho");
		tabela.getModeloTabela().addColumn("Qualidade");
		tabela.getModeloTabela().addColumn("Mascara");
		tabela.getModeloTabela().addColumn("Peso");
		tabela.getModeloTabela().addColumn("Qtde");
		tabela.getModeloTabela().addColumn("Valor");

		tabela.getModeloColuna().getColumn(0).setPreferredWidth(50);
		tabela.getModeloColuna().getColumn(1).setPreferredWidth(50);
		tabela.getModeloColuna().getColumn(2).setPreferredWidth(50);
		tabela.getModeloColuna().getColumn(3).setPreferredWidth(50);
		tabela.getModeloColuna().getColumn(4).setPreferredWidth(50);
		tabela.getModeloColuna().getColumn(5).setPreferredWidth(50);
		tabela.getModeloColuna().getColumn(6).setPreferredWidth(50);
		tabela.getModeloColuna().getColumn(7).setPreferredWidth(50);
		tabela.getModeloColuna().getColumn(8).setPreferredWidth(50);

		tabela.getModeloColuna().setCampo(0, "item");
		tabela.getModeloColuna().setCampo(1, "categoria");
		tabela.getModeloColuna().setCampo(2, "marca");
		tabela.getModeloColuna().setCampo(3, "tamanho");
		tabela.getModeloColuna().setCampo(4, "estado");
		tabela.getModeloColuna().setCampo(5, "mascaraPreco");
		tabela.getModeloColuna().setCampo(6, "markup");
		tabela.getModeloColuna().setCampo(7, "qtde");
		tabela.getModeloColuna().setCampo(8, "valor");

	}
	
	private void adicionarScrollGrade() {
		panel_grade.setLayout(null);
		scroll.setBounds(0, 51, 694, 194);
		scroll.setViewportBorder(new LineBorder(Color.BLACK));
		
				scroll.setViewportView(tabela);
						panel_grade.add(scroll);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBounds(0, 0, 694, 40);
		panel.setBackground(Color.GREEN);
		panel_grade.add(panel);

	}
	
	private void listar() {
		List<ItemCompra> lista = new ArrayList<ItemCompra>();
		try {
			//String nome = txtFiltro.getText();
			if (!Validacao.vazio(txtCodigo.getText())) {
				lista = dao.listaItens(txtCodigo.getInteger());
			}

			tabela.setValue(lista);
		} catch (Exception e) {
			e.printStackTrace();
			SSMensagem.erro(e.getMessage());
		}
	}

	private void atribuir() {
		try {
			txtCodigo.setValue(entidade.getId());
			txtData.setValue(entidade.getData());
			cboStatus.setValue(entidade.getStatus());
			txtIdPessoa.setValue(entidade.getPessoa());
			txtNome.setValue(entidade.getNome());
			txtTelefone.setValue(entidade.getTelefone());
			txtObs.setValue(entidade.getObs());
			txtDeposito.setValue(entidade.getVlDeposito());
			txtTroca.setValue(entidade.getVlTroca());
			txtDoacao.setValue(entidade.getVlDoacao());
			txtData.requestFocus();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void criar() {
		entidade = new Compra();
		atribuir();
	}

	private void salvar() {
		try {
			entidade.setData(txtData.getDataHora());
			entidade.setStatus((StatusOrcamento) cboStatus.getValue());
			
			Pessoa pessoa = daoP.findById(txtIdPessoa.getInteger());
			
			entidade.setPessoa(pessoa);
			entidade.setNome(txtNome.getText());
			entidade.setTelefone(txtTelefone.getText());
			entidade.setObs(txtObs.getText());
			entidade.setVlDeposito(BigDecimal.valueOf(txtDeposito.getDouble()));
			entidade.setVlTroca(BigDecimal.valueOf(txtTroca.getDouble()));
			entidade.setVlDoacao(BigDecimal.valueOf(txtDoacao.getDouble()));

			if (entidade.getData() == null || entidade.getStatus() == null || entidade.getNome() == null || entidade.getNome().isEmpty()
					|| entidade.getTelefone() == null || entidade.getTelefone().isEmpty()) {
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
