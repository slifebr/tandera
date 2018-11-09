package com.tandera.app.desktop.orcamento;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tandera.app.desktop.cadastro.FrmPessoas;
import com.tandera.app.spring.SpringDesktopApp;
import com.tandera.core.dao.springjpa.CompraRepository;
import com.tandera.core.dao.springjpa.EstadoRepository;
import com.tandera.core.dao.springjpa.MarcaRepository;
import com.tandera.core.dao.springjpa.MarkupRepository;
import com.tandera.core.dao.springjpa.MascaraPrecoRepository;
import com.tandera.core.dao.springjpa.ParamOrctoRepository;
import com.tandera.core.dao.springjpa.PessoaRepository;
import com.tandera.core.dao.springjpa.TamanhoRepository;
import com.tandera.core.model.cadastro.Pessoa;
import com.tandera.core.model.comercial.Categoria;
import com.tandera.core.model.comercial.Estado;
import com.tandera.core.model.comercial.Marca;
import com.tandera.core.model.comercial.Markup;
import com.tandera.core.model.comercial.MascaraPreco;
import com.tandera.core.model.comercial.Tamanho;
import com.tandera.core.model.enuns.SimNao;
import com.tandera.core.model.enuns.StatusOrcamento;
import com.tandera.core.model.orcamento.Compra;
import com.tandera.core.model.orcamento.ItemCompra;
import com.tandera.core.model.orcamento.ParamOrcto;
import com.tandera.core.services.CategoriaService;
import com.tandera.core.util.Biblioteca;
import com.tandera.core.util.Constantes;

import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.ss.PosicaoRotulo;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCaixaCombinacao;
import edu.porgamdor.util.desktop.ss.SSCampoDataHora;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSCampoTextoArea;
import edu.porgamdor.util.desktop.ss.SSGrade;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.SSToolBar;
import edu.porgamdor.util.desktop.ss.evento.ValidacaoEvento;
import edu.porgamdor.util.desktop.ss.evento.ValidacaoListener;

@Component
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmCompra extends Formulario {

	@Autowired
	private CompraRepository compraRepository;

	Class frmConsulta = FrmPessoas.class;

	@Autowired
	private PessoaRepository pessoaRepository;

	private ParamOrctoRepository paramOrctoRepository;
	private CategoriaService categoriaService;
	private MarcaRepository marcaRepository;
	private TamanhoRepository tamanhoRepository;
	private EstadoRepository estadoRepository;
	private MascaraPrecoRepository mascaraPrecoRepository;
	private MarkupRepository markupRepository;

	private Compra compra;
	private ItemCompra itemCompraSelecionado;
	private MascaraPreco mascaraPreco;
	private ParamOrcto paramOrcto;

	// CAMPOS COMPRA
	private SSCampoNumero txtCodigo = new SSCampoNumero();
	private SSCampoDataHora txtData = new SSCampoDataHora();
	private SSCaixaCombinacao cboConsignado = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboStatus = new SSCaixaCombinacao();
	private SSCampoNumero txtIdPessoa = new SSCampoNumero();
	private SSCampoTexto txtNome = new SSCampoTexto();
	private SSCampoTexto txtTelefone = new SSCampoTexto();
	private SSCampoTextoArea txtObs = new SSCampoTextoArea();
	private SSCampoNumero txtDeposito = new SSCampoNumero();
	private SSCampoNumero txtTroca = new SSCampoNumero();
	private SSCampoNumero txtDoacao = new SSCampoNumero();
	private SSCampoNumero txtTotalReprovados = new SSCampoNumero();

	// CAMPOS ITEM COMPRA
	private SSCampoTexto txtItem = new SSCampoTexto();
	private SSCaixaCombinacao cboCategoria = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboMarca = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboTamanho = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboQualidade = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboMascara = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboMarkup = new SSCaixaCombinacao();
	private SSCampoNumero txtQtde = new SSCampoNumero();
	private SSCampoNumero txtValor = new SSCampoNumero();
	private SSCampoNumero txtValorTotalItem = new SSCampoNumero();
	private SSCampoNumero txtTotalOrcto = new SSCampoNumero();
	private SSCampoNumero txtTotalItensOrcam = new SSCampoNumero();
	private SSCampoNumero txtTotalProdutos = new SSCampoNumero();

	private JScrollPane scroll = new JScrollPane();
	private SSGrade tabela = new SSGrade();
	/**
	 * @wbp.nonvisual location=-40,239
	 */
	// private final JPanel panel = new JPanel();
	private final JPanel panel_grade = new JPanel();
	private final JPanel panel_inclusao = new JPanel();
	private final JPanel panel = new JPanel();
	private final JPanel panelTotal = new JPanel();
	private final SSToolBar toolBar = new SSToolBar();
	private final JPanel panelToolBar1 = new JPanel();

	// botoes
	private SSBotao cmdAprovado = new SSBotao();
	private SSBotao cmdReprovado = new SSBotao();
	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private JCheckBox chkNovo = new JCheckBox("Novo?");
	private JCheckBox chkNovoItem = new JCheckBox("Novo?");

	private final SSBotao cmdIncluir = new SSBotao(); // new JButton("Incluir");
	private final SSBotao cmdAlterar = new SSBotao(); // new JButton("Alterar");
	private final SSBotao cmdExcluir = new SSBotao(); // new JButton("Excluir");
	private final SSBotao cmdSalvarItem = new SSBotao(); // new SSBotao();

	/*
	 * variaveis de classe
	 */

	private Integer vcContadorDeItens;
	private String acao; // NOVO | ALTERAR | EXCLUIR | CONSULTAR
	private final SSBotao btnPessoas = new SSBotao();

	@Autowired
	public FrmCompra(CategoriaService categoriaService, TamanhoRepository tamanhoRepository,
			MarcaRepository marcaRepository, EstadoRepository estadoRepository,
			MascaraPrecoRepository mascaraPrecoRepository, MarkupRepository markupRepository,
			ParamOrctoRepository paramOrctoRepository) {

		this.categoriaService = categoriaService;
		this.tamanhoRepository = tamanhoRepository;
		this.marcaRepository = marcaRepository;
		this.estadoRepository = estadoRepository;
		this.mascaraPrecoRepository = mascaraPrecoRepository;
		this.markupRepository = markupRepository;
		this.paramOrctoRepository = paramOrctoRepository;

		getConteudo().setBackground(SystemColor.control);
		getConteudo().setLayout(null);
		panel_grade.setBounds(0, 0, 0, 0);
		panel_grade.setBackground(SystemColor.control);
		getConteudo().add(panel_grade);

		panel_inclusao.setBounds(0, 190, 924, 69);
		getConteudo().add(panel_inclusao);
		panel_inclusao.setVisible(false);

		configurarCamposTabela();
		configurarConstraintsGrid();
		adicionarScrollGrade();

		init();
		configurarToolBar();
		setPreferredSize(new Dimension(924, 543));
		// setSize(new Dimension(883, 543));
	}

	private void init() {
		super.setTitulo("Orçamento");
		super.setDescricao("Lançamentos de Orçamentos");
		super.getRodape().add(cmdAprovado);
		super.getRodape().add(cmdReprovado);
		super.getRodape().add(chkNovo);
		super.getRodape().add(cmdSalvar);
		super.getRodape().add(cmdSair);

		adicionarListner();
		configurarCamposCompra();
		configurarCamposItemCompra();
		checaStatus();
		load();

	}

	private void configurarToolBar() {
		toolBar.setBounds(0, 385, 924, 66);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		getConteudo().add(toolBar);
		panelToolBar1.setBackground(SystemColor.scrollbar);

		panelToolBar1.add(cmdIncluir);
		panelToolBar1.add(cmdAlterar);
		panelToolBar1.add(cmdExcluir);
		toolBar.add(panelToolBar1);
		panelTotal.setBackground(SystemColor.scrollbar);
		toolBar.add(panelTotal);
	}

	private void configurarCamposCompra() {
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelCampos.setLayout(null);
		panelCampos.setPreferredSize(new Dimension(250, 250));

		txtCodigo.setBounds(10, 5, 80, 50);
		panelCampos.add(txtCodigo);
		txtCodigo.setColunas(10);
		txtCodigo.setEnabled(false);
		txtCodigo.setRotulo("Código");

		txtData.setBounds(120, 5, 127, 50);
		panelCampos.add(txtData);
		txtData.setColunas(10);
		txtData.setRotulo("Data");

		cboConsignado.setBounds(380, 5, 94, 50);
		cboConsignado.setRotulo("Consignado");
		cboConsignado.setItens(SimNao.values());
		panelCampos.add(cboConsignado);

		cboStatus.setBounds(656, 5, 205, 50);
		cboStatus.setRotulo("Status");
		cboStatus.setEnabled(false);
		cboStatus.setItens(StatusOrcamento.values());
		cboStatus.setValue(StatusOrcamento.valueOf("N"));
		panelCampos.add(cboStatus);

		txtIdPessoa.setBounds(10, 57, 80, 50);
		panelCampos.add(txtIdPessoa);
		txtIdPessoa.setColunas(10);
		txtIdPessoa.setRotulo("Cód. Pessoa");
		btnPessoas.setToolTipText("Lista de Pessoas");
		btnPessoas.setText("");
		btnPessoas.setOpaque(false);
		btnPessoas.setMargin(new Insets(0, 0, 0, 0));
		btnPessoas.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPessoas.setBounds(90, 78, 22, 22);
		btnPessoas.setIcone("buscar");
		panelCampos.add(btnPessoas);

		txtNome.setBounds(120, 57, 526, 50);
		txtNome.setRotulo("Nome");
		txtNome.setColunas(10);
		panelCampos.add(txtNome);

		txtTelefone.setBounds(656, 57, 205, 50);
		txtTelefone.setRotulo("Telefone");
		txtTelefone.setColunas(10);
		panelCampos.add(txtTelefone);
		txtObs.getComponente().setMinimumSize(new Dimension(6, 60));
		txtObs.getComponente().setPreferredSize(new Dimension(6, 60));

		txtObs.setBounds(10, 109, 636, 77);
		txtObs.setRotulo("Obs.");
		txtObs.setColunas(10);
		txtObs.setAutoscrolls(true);
		panelCampos.add(txtObs);
		txtDeposito.setRotuloPosicao(PosicaoRotulo.ESQUERDA);

		txtDeposito.setBounds(656, 109, 205, 25);
		txtDeposito.setRotulo("Depósito ");
		txtDeposito.setColunas(10);
		txtDeposito.setEditavel(false);
		panelCampos.add(txtDeposito);

		txtTroca.getComponente().setBounds(30, 15, 175, 20);
		txtTroca.setRotuloPosicao(PosicaoRotulo.ESQUERDA);

		txtTroca.setBounds(656, 135, 205, 25);
		txtTroca.setRotulo("Troca      ");
		txtTroca.setColunas(10);
		txtTroca.setEditavel(false);
		panelCampos.add(txtTroca);

		txtDoacao.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		txtDoacao.setBounds(656, 161, 205, 25);
		txtDoacao.setRotulo("Doação   ");
		txtDoacao.setColunas(10);
		txtDoacao.setEditavel(false);
		panelCampos.add(txtDoacao);

		cmdSair.setText("Fechar");
		cmdSalvar.setText("Salvar");
		cmdAprovado.setText("Aprovar");
		cmdReprovado.setText("Reprovar");
		cmdSalvarItem.setText("Salvar");
		panelToolBar1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		cmdIncluir.setText("Incluir");
		cmdIncluir.setIcone("novo");
		cmdAlterar.setText("Alterar");
		cmdExcluir.setText("Excluir");
		// txtTotalReprovados.getComponente().setEnabled(false);

		txtTotalReprovados.setRotulo("Total Reprovados");
		// txtTotalReprovados.setCampoAtualizavel(false);
		txtTotalReprovados.setComponenteNegrito(true);
		// txtTotalReprovados.setEnabled(false);
		// txtTotalProduto.setComponenteCorFundo(Color.YELLOW);
		txtTotalReprovados.setRotuloPosicao(PosicaoRotulo.TOPO);
		txtTotalReprovados.setColunas(5);
		panelTotal.add(txtTotalReprovados);

		txtTotalItensOrcam.getComponente().setEnabled(false);
		txtTotalItensOrcam.setRotulo("Total Itens Orçados");
		txtTotalItensOrcam.setCampoAtualizavel(false);
		txtTotalItensOrcam.setComponenteNegrito(true);
		txtTotalItensOrcam.setEnabled(false);
		txtTotalItensOrcam.setComponenteCorFundo(Color.YELLOW);
		txtTotalItensOrcam.setRotuloPosicao(PosicaoRotulo.TOPO);
		txtTotalItensOrcam.setColunas(5);
		panelTotal.add(txtTotalItensOrcam);
		txtTotalOrcto.getComponente().setEnabled(false);

		txtTotalProdutos.getComponente().setEnabled(false);
		txtTotalProdutos.setRotulo("Total de Itens");
		txtTotalProdutos.setCampoAtualizavel(false);
		txtTotalProdutos.setComponenteNegrito(true);
		txtTotalProdutos.setEnabled(false);
		txtTotalProdutos.setComponenteCorFundo(Color.YELLOW);
		txtTotalProdutos.setRotuloPosicao(PosicaoRotulo.TOPO);
		txtTotalProdutos.setColunas(5);
		panelTotal.add(txtTotalProdutos);
		txtTotalProdutos.getComponente().setEnabled(false);

		txtTotalOrcto.setRotulo("Total Orçamento");
		txtTotalOrcto.setCampoAtualizavel(false);
		txtTotalOrcto.setComponenteNegrito(true);
		txtTotalOrcto.setEnabled(false);
		txtTotalOrcto.setComponenteCorFundo(Color.YELLOW);
		txtTotalOrcto.setRotuloPosicao(PosicaoRotulo.TOPO);
		txtTotalOrcto.setColunas(10);
		panelTotal.add(txtTotalOrcto);

	}

	private void configurarCamposItemCompra() {

		panel_inclusao.add(txtItem);
		txtItem.setComponenteTamanhoPreferido(new Dimension(40, 20));
		txtItem.setRotulo("Item");
		txtItem.setEnabled(false);

		panel_inclusao.add(cboCategoria);
		cboCategoria.setComponenteTamanhoPreferido(new Dimension(100, 20));
		cboCategoria.setRotulo("Categoria");

		panel_inclusao.add(cboMarca);
		cboMarca.setComponenteTamanhoPreferido(new Dimension(100, 20));
		cboMarca.setRotulo("Marca");

		panel_inclusao.add(cboTamanho);
		cboTamanho.setComponenteTamanhoPreferido(new Dimension(120, 20));
		cboTamanho.setRotulo("Tamanho");

		panel_inclusao.add(cboQualidade);
		cboQualidade.setComponenteTamanhoPreferido(new Dimension(100, 20));
		cboQualidade.setRotulo("Qualidade");

		panel_inclusao.add(cboMascara);
		cboMascara.setComponenteTamanhoPreferido(new Dimension(80, 20));
		cboMascara.setRotulo("Mascara");

		panel_inclusao.add(cboMarkup);
		cboMarkup.setComponenteTamanhoPreferido(new Dimension(60, 20));
		cboMarkup.setRotulo("Peso");

		panel_inclusao.add(txtQtde);
		txtQtde.setComponenteTamanhoPreferido(new Dimension(50, 20));
		txtQtde.setRotulo("Qtde");

		panel_inclusao.add(txtValor);
		txtValor.setComponenteTamanhoPreferido(new Dimension(60, 20));
		txtValor.setRotulo("Vl. Unitário");

		panel_inclusao.add(txtValorTotalItem);
		txtValorTotalItem.setComponenteTamanhoPreferido(new Dimension(60, 20));
		txtValorTotalItem.setRotulo("Total");
		txtValorTotalItem.setBackground(Color.YELLOW);
		txtValorTotalItem.setForeground(Color.BLUE);
		;
		txtValorTotalItem.setCampoAtualizavel(false);

		// configurações do painel de itens de inclusao
		panel.setPreferredSize(new Dimension(85, 50));
		panel.setMinimumSize(new Dimension(100, 100));
		panel.setBounds(new Rectangle(0, 0, 100, 100));
		panel.setAlignmentY(0.0f);
		panel.setAlignmentX(0.0f);

		FlowLayout fl_panel_inclusao = new FlowLayout(FlowLayout.LEFT, 5, 5);
		panel_inclusao.setLayout(fl_panel_inclusao);

		panel_inclusao.add(panel);
		panel.setLayout(null);
		cmdSalvarItem.setBounds(0, 25, 83, 25);
		panel.add(cmdSalvarItem);
		cmdSalvarItem.setAlignmentY(java.awt.Component.BOTTOM_ALIGNMENT);

		chkNovoItem.setBounds(0, 0, 55, 23);
		panel.add(chkNovoItem);

	}

	private void adicionarListner() {
		// Listners = Comandos = Eventos
		cmdSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				salvar();
			}
		});
		cmdSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sair();
			}
		});
		cmdIncluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				novoItem();
			}
		});
		cmdAlterar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				alterarItem();
			}
		});
		cmdExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				excluirItem();
			}
		});
		cboMascara.addValidacaoListener(new ValidacaoListener() {
			@Override
			public void validacaoListener(ValidacaoEvento evento) {
				buscaValorUnitario();
				calculaValorTotalInclusao();
			}
		});
		txtQtde.addValidacaoListener(new ValidacaoListener() {
			@Override
			public void validacaoListener(ValidacaoEvento evento) {
				calculaValorTotalInclusao();
			}
		});
		cboMarkup.addValidacaoListener(new ValidacaoListener() {
			@Override
			public void validacaoListener(ValidacaoEvento arg0) {
				buscaValorUnitario();
				calculaValorTotalInclusao();
			}
		});
		cmdAprovado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				aprovarOrcto();
			}
		});
		cmdReprovado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reprovarOrcto();
			}
		});
		cmdSalvarItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				adicionarItem();
			}
		});
		txtDeposito.addValidacaoListener(new ValidacaoListener() {
			@Override
			public void validacaoListener(ValidacaoEvento arg0) {
				calculaValorTotalOrcto();
				calculaQuantidadeItens();
				calculaTotalItens();
				// calculaValorTotalDesconto();
				// calculaValorTotalProduto();
			}
		});
		txtDoacao.addValidacaoListener(new ValidacaoListener() {
			@Override
			public void validacaoListener(ValidacaoEvento arg0) {
				calculaValorTotalOrcto();
				calculaQuantidadeItens();
				calculaTotalItens();
				// calculaValorTotalDesconto();
				// calculaValorTotalProduto();
			}
		});
		txtTroca.addValidacaoListener(new ValidacaoListener() {
			@Override
			public void validacaoListener(ValidacaoEvento arg0) {
				calculaValorTotalOrcto();
				calculaQuantidadeItens();
				calculaTotalItens();
				// calculaValorTotalDesconto();
				// calculaValorTotalProduto();
			}
		});
		btnPessoas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPessoa();
			}
		});
		txtTotalReprovados.addValidacaoListener(new ValidacaoListener() {
			@Override
			public void validacaoListener(ValidacaoEvento evento) {
				calculaTotalItens();
			}
		});
	}

	// public void setEntidade(Natureza entidade) {
	public void setEntidade(Object entidade) {
		this.compra = (Compra) entidade;
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
		tabela.getModeloTabela().addColumn("Vl. Unit.");
		tabela.getModeloTabela().addColumn("Vl. Total");

		tabela.getModeloColuna().getColumn(0).setPreferredWidth(40);
		tabela.getModeloColuna().getColumn(1).setPreferredWidth(100);
		tabela.getModeloColuna().getColumn(2).setPreferredWidth(80);
		tabela.getModeloColuna().getColumn(3).setPreferredWidth(150);
		tabela.getModeloColuna().getColumn(4).setPreferredWidth(120);
		tabela.getModeloColuna().getColumn(5).setPreferredWidth(80);
		tabela.getModeloColuna().getColumn(6).setPreferredWidth(40);
		tabela.getModeloColuna().getColumn(7).setPreferredWidth(60);
		tabela.getModeloColuna().getColumn(8).setPreferredWidth(60);
		tabela.getModeloColuna().getColumn(9).setPreferredWidth(60);

		tabela.getModeloColuna().setCampo(0, "item");
		tabela.getModeloColuna().setCampo(1, "categoria.descr");
		tabela.getModeloColuna().setCampo(2, "marca.descr");
		tabela.getModeloColuna().setCampo(3, "tamanho.descr");
		tabela.getModeloColuna().setCampo(4, "estado.descr");
		tabela.getModeloColuna().setCampo(5, "mascaraPreco.mascara");
		tabela.getModeloColuna().setCampo(6, "markup.sigla");
		tabela.getModeloColuna().setCampo(7, "qtde");
		tabela.getModeloColuna().setAlinhamentoColuna(7, SwingConstants.RIGHT);
		tabela.getModeloColuna().setCampo(8, "valor");
		tabela.getModeloColuna().setAlinhamentoColuna(8, SwingConstants.RIGHT);
		// tabela.getModeloColuna().setMascara(8,"##0.00");
		tabela.getModeloColuna().setCampo(9, "valorTotal");
		tabela.getModeloColuna().setAlinhamentoColuna(9, SwingConstants.RIGHT);
		// tabela.getModeloColuna().setMascara(9,"##0.00");

	}

	private void posicionaScroll(String posicao) {
		// if (acao == null ||acao.equals(Constantes.ACAO_CONSULTAR)) {
		// posicao = (D)own | (U)p
		if (posicao.equals("U")) {
			scroll.setBounds(0, 190, 924, 119 + (262 - 190));
		} else {
			scroll.setBounds(0, 190, 924, 119);
		}
	}

	private void adicionarScrollGrade() {

		panel_inclusao.setBackground(SystemColor.activeCaptionBorder);
		// scroll.setBounds(0, 262, 924, 119);
		// scroll.setBounds(0, 190, 924, 119 + (262-190));
		posicionaScroll("U");
		getConteudo().add(scroll);
		scroll.setViewportBorder(
				new BevelBorder(BevelBorder.LOWERED, Color.BLUE, Color.YELLOW, Color.GRAY, Color.GREEN));
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabela.setFillsViewportHeight(true);
		tabela.setQuantidadeLinhasVisiveis(15);

		scroll.setViewportView(tabela);

		toolBar.setBackground(SystemColor.scrollbar);
		GroupLayout gl_panel_grade = new GroupLayout(panel_grade);
		gl_panel_grade.setHorizontalGroup(
				gl_panel_grade.createParallelGroup(Alignment.LEADING).addGap(0, 915, Short.MAX_VALUE));
		gl_panel_grade.setVerticalGroup(
				gl_panel_grade.createParallelGroup(Alignment.LEADING).addGap(0, 175, Short.MAX_VALUE));
		panelTotal.setMaximumSize(new Dimension(40, 10));
		panelTotal.setName("painelTotal");
		panelTotal.setMinimumSize(new Dimension(40, 10));
		FlowLayout flowLayout_1 = (FlowLayout) panelTotal.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		// toolBar.add(panelTotal);

		panel_grade.setLayout(gl_panel_grade);
		// panel_grade.setBounds(0, 190, 924, 261); // 903, 270);
		// panel_grade.setSize(new Dimension(924, 261));

	}

	private void buscaValorUnitario() {
		this.mascaraPreco = ((MascaraPreco) cboMascara.getValue());
		BigDecimal valorMascara = BigDecimal.ZERO;
		BigDecimal valorMarkup = BigDecimal.ZERO;
		if (this.cboMarkup.getValue() != null) {
			valorMarkup = ((Markup) this.cboMarkup.getValue()).getValor();
			valorMascara = this.mascaraPreco.getValor();

			if (valorMarkup.doubleValue() != 0) {
				BigDecimal resultado = valorMascara.divide(valorMarkup, 2, RoundingMode.DOWN);
				txtValor.setNumero(resultado);
			}
		}

	}

	private void atribuir() {
		try {
			txtCodigo.setValue(compra.getId());
			txtData.setValue(compra.getData());
			cboStatus.setValue(compra.getStatus());
			if (compra.getPessoa() != null) {
				txtIdPessoa.setValue(compra.getPessoa().getId());
			}
			txtNome.setValue(compra.getNome());
			txtTelefone.setValue(compra.getTelefone());
			txtObs.setValue(compra.getObs());
			txtDeposito.setValue(compra.getVlDeposito());
			txtTroca.setValue(compra.getVlTroca());
			txtDoacao.setValue(compra.getVlDoacao());
			txtTotalReprovados.setValue(compra.getReprovado());
			cboConsignado.setValue(compra.getConsignado());
			txtData.requestFocus();
			loadItens();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void criar() {
		compra = new Compra();
		atribuir();
	}

	private void salvar() {
		try {
			if (this.acao.equals(Constantes.ACAO_EXCLUSAO)) {
				compraRepository.saveAndFlush(compra);
				SSMensagem.informa("Item Excluido com sucesso!!");
			} else {
				compra.setData(txtData.getDataHora());

				if (cboStatus.getText().isEmpty()) {

					cboStatus.setValue(StatusOrcamento.N);
				}

				compra.setStatus((StatusOrcamento) cboStatus.getValue());
				if (!txtIdPessoa.getText().isEmpty()) {
					Pessoa pessoa = pessoaRepository.findById(Integer.parseInt(txtIdPessoa.getText()));
					compra.setPessoa(pessoa);
				}

				compra.setNome(txtNome.getText());

				compra.setTelefone(txtTelefone.getText());
				compra.setObs(txtObs.getText());
				compra.setVlDeposito(BigDecimal.valueOf(txtDeposito.getDouble()));
				compra.setVlTroca(BigDecimal.valueOf(txtTroca.getDouble()));
				compra.setVlDoacao(BigDecimal.valueOf(txtDoacao.getDouble()));
				compra.setReprovado(txtTotalReprovados.getInteger());
				compra.setConsignado((SimNao) cboConsignado.getValue());

				if (compra.getData() == null || compra.getStatus() == null || compra.getNome() == null
						|| compra.getNome().isEmpty() || compra.getTelefone() == null
						|| compra.getTelefone().isEmpty()) {
					SSMensagem.avisa("Dados incompletos");
					return;
				}

				compraRepository.save(compra);
				this.acao = Constantes.ACAO_CONSULTAR;
				SSMensagem.informa("Orçamento de Compra registrado com sucesso!!");
				novo();
			}

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
		if (!this.acao.equals(Constantes.ACAO_CONSULTAR)) {
			if (!SSMensagem.confirma("Existem dados pendentes. Confirme para sair sem salvar")) {
				return;
			}
		}
		this.acao = Constantes.ACAO_CONSULTAR;
		panel_inclusao.setVisible(false);
		// scroll.setBounds(0, 190, 924, 119 + (262-190));
		posicionaScroll("U");
		super.fechar();
	}

	private void novoItem() {
		this.acao = Constantes.ACAO_NOVO;
		chkNovoItem.setEnabled(true);
		scroll.setBounds(0, 262, 924, 119);
		// posicionaScroll("D");
		panel_inclusao.setVisible(true);
		habilitaBotoes(false);

	}

	private void alterarItem() {
		this.itemCompraSelecionado = (ItemCompra) tabela.getLinhaSelecionada();
		if (this.itemCompraSelecionado == null) {
			SSMensagem.avisa("Selecione um item da lista");
			return;
		}
		scroll.setBounds(0, 262, 924, 119);
		// posicionaScroll("D");
		this.acao = Constantes.ACAO_ALTERAR;
		chkNovoItem.setSelected(false);
		chkNovoItem.setEnabled(false);
		carregarItemSelecionado();
		panel_inclusao.setVisible(true);
		habilitaBotoes(false);
	}

	private void carregarItemSelecionado() {
		txtItem.setValue(this.itemCompraSelecionado.getItem());
		cboCategoria.setValue(this.itemCompraSelecionado.getCategoria());
		cboMascara.setValue(this.itemCompraSelecionado.getMascaraPreco());
		cboMarca.setValue(this.itemCompraSelecionado.getMarca());
		cboTamanho.setValue(this.itemCompraSelecionado.getTamanho());
		cboMarkup.setValue(this.itemCompraSelecionado.getMarkup());
		txtQtde.setValue(this.itemCompraSelecionado.getQtde());
		txtValor.setValue(this.itemCompraSelecionado.getValor());
		txtValorTotalItem.setValue(this.itemCompraSelecionado.getValorTotal());
		cboQualidade.setValue(this.itemCompraSelecionado.getEstado());
	}

	private void excluirItem() {
		ItemCompra itemCompra = (ItemCompra) tabela.getLinhaSelecionada();
		if (itemCompra == null) {
			SSMensagem.avisa("Selecione um item da lista");
			return;
		}

		if (SSMensagem.confirma("Confirma exclusão do Registro (" + itemCompra.getItem() + "-"
				+ itemCompra.getCategoria().getDescr() + ")?")) {
			this.acao = Constantes.ACAO_EXCLUSAO;
			compraRepository.deleteItemCompra(itemCompra.getId());
			compra.getItemCompra().remove(itemCompra);
			compra.setItemCompra(numeraItens(compra.getItemCompra()));
			salvar();
			tabela.setValue(compra.getItemCompra());
		}

	}

	private void adicionarItem() {
		List<ItemCompra> listaDeItens = new ArrayList<ItemCompra>();
		ItemCompra itemCompra;

		if (this.acao.equals(Constantes.ACAO_NOVO)) {
			itemCompra = new ItemCompra();
			vcContadorDeItens++; // = tabela.getRowCount() +1;
		} else {
			itemCompra = this.itemCompraSelecionado;
			vcContadorDeItens = itemCompra.getItem();
		}

		if (validaItens()) {
			this.mascaraPreco = ((MascaraPreco) cboMascara.getValue());
			itemCompra.setCategoria((Categoria) cboCategoria.getValue());
			itemCompra.setMarca((Marca) cboMarca.getValue());
			itemCompra.setTamanho((Tamanho) cboTamanho.getValue());
			itemCompra.setEstado((Estado) cboQualidade.getValue());
			itemCompra.setMascaraPreco(this.mascaraPreco);
			itemCompra.setMarkup((Markup) cboMarkup.getValue());
			itemCompra.setQtde(txtQtde.getInteger());
			if (!txtValor.getText().isEmpty()) {
				itemCompra.setValor(BigDecimal.valueOf(txtValor.getDouble()));
			}
			itemCompra.setCompra(compra);

			BigDecimal mascara = BigDecimal.ZERO;
			mascara = Biblioteca.descriptoStringToBigDecimal(this.mascaraPreco.getMascara());
			itemCompra.setVlMascara(mascara);
			listaDeItens = compra.getItemCompra();
			if (this.acao.equals(Constantes.ACAO_NOVO)) {
				itemCompra.setItem(vcContadorDeItens);
				listaDeItens.add(itemCompra);
				listaDeItens = numeraItens(listaDeItens);

			} else {
				listaDeItens.remove(this.itemCompraSelecionado);
				listaDeItens.add(itemCompra);
			}
			this.compra.setItemCompra(listaDeItens);
			tabela.setValue(listaDeItens);
		}

		// limpar campos de adicao/alteração de itens
		limparPainelInclusao();
		loadItens();

		if (!chkNovoItem.isSelected()) {
			panel_inclusao.setVisible(false);
			posicionaScroll("U");
			habilitaBotoes(true);
		}
	}

	private List<ItemCompra> numeraItens(List<ItemCompra> lista) {
		List<ItemCompra> listaRetorno = new ArrayList<ItemCompra>();
		AtomicInteger contador = new AtomicInteger(1);

		// lista.forEach( System.out::println );

		lista.forEach(item -> {
			item.setItem(contador.getAndIncrement());
			// System.out.println(contador);
			// System.out.println(item);
			listaRetorno.add(item);
		});

		return listaRetorno;
	}

	private void limparPainelInclusao() {
		cboCategoria.setValue(null);
		cboMascara.setValue(null);
		cboMarca.setValue(null);
		cboTamanho.setValue(null);
		cboMarkup.setValue(null);
		cboQualidade.setValue(null);
		txtQtde.setValue(null);
		txtValor.setValue(null);
		txtValorTotalItem.setValue(null);
		txtItem.setValue(null);
	}

	public void limparCabecalho() {
		txtIdPessoa.setText(null);
		txtObs.setText(null);
		txtCodigo.setText(null);
		txtData.setText(null);
		txtDeposito.setText(null);
		txtDoacao.setText(null);
		txtTroca.setText(null);
		txtTotalReprovados.setText(null);
		txtNome.setText(null);
		txtTelefone.setText(null);
		txtTotalOrcto.setText(null);
		cboConsignado.setText(null);
		cboStatus.setText(null);
	}

	private void calculaValorTotalInclusao() {
		if (Biblioteca.temValorValido(txtQtde, "I") && Biblioteca.temValorValido(txtValor, "N")) {
			int qtd = Integer.parseInt(txtQtde.getText());

			BigDecimal valorUnitario = txtValor.getBigDecimal();
			BigDecimal resultado = valorUnitario.multiply(new BigDecimal(qtd));
			txtValorTotalItem.setNumero(resultado);
		}
	}

	private void calculaQuantidadeItens() {
		Integer qtde = compra.getItemCompra().stream().map(item -> item.getQtde()).reduce(0, Integer::sum);
		txtTotalItensOrcam.setText(qtde.toString());
	}

	private void calculaValorTotalOrcto() {
		BigDecimal valorOrcto = compra.getItemCompra().stream().map(item -> item.getValorTotal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		/*
		 * BigDecimal valorOrcto = BigDecimal.ZERO;
		 * 
		 * for (ItemCompra item : compra.getItemCompra()){
		 * System.out.println(item.getItem() + " - " + item.getValorTotal()); valorOrcto
		 * = valorOrcto.add(item.getValorTotal()); System.out.println("Valor orcamento!"
		 * + valorOrcto); };
		 */

		/*
		 * BigDecimal desconto = new
		 * BigDecimal(Biblioteca.converteValor(txtDeposito.getText())) .add(new
		 * BigDecimal(Biblioteca.converteValor(txtDoacao.getText()))) .add(new
		 * BigDecimal(Biblioteca.converteValor(txtTroca.getText())));
		 * 
		 * valorOrcto = valorOrcto.subtract(desconto);
		 */
		txtTotalOrcto.setText(valorOrcto.toString());
		txtDeposito.setText(txtTotalOrcto.getText());

		BigDecimal fatorTroca = paramOrcto.getTroca().divide(new BigDecimal("100")).add(new BigDecimal("1"));

		// System.out.println("fator troca: " + fatorTroca);
		txtTroca.setText(valorOrcto.multiply(fatorTroca).setScale(2, RoundingMode.HALF_EVEN).toString());

		BigDecimal fatorDoacao = paramOrcto.getDoacao().divide(new BigDecimal("100")).add(new BigDecimal("1"));

		// System.out.println("fator Doacao: " + fatorDoacao);
		txtDoacao.setText(valorOrcto.multiply(fatorDoacao).setScale(2, RoundingMode.HALF_EVEN).toString());

	}
	
	private void calculaTotalItens() {

		if (Biblioteca.temValorValido(txtTotalReprovados, "I") && 
			Biblioteca.temValorValido(txtTotalItensOrcam, "N")) {
			
			int reprovados =  Integer.parseInt(txtTotalReprovados.getText());
			Integer qtdeTotal = Integer.parseInt(txtTotalItensOrcam.getText());
			Integer resultado = qtdeTotal +reprovados;
			txtTotalProdutos.setValue(resultado);
			
		}

	}

	// @Override
	public void load() {
		this.acao = Constantes.ACAO_CONSULTAR;
		cboCategoria.setItens(categoriaService.listarTodos(), "descr");

		List<Marca> marca = marcaRepository.findAll();
		cboMarca.setItens(marca, "descr");

		List<Tamanho> tamanho = tamanhoRepository.findAll();
		cboTamanho.setItens(tamanho, "descr");

		List<Estado> estado = estadoRepository.findAll();
		cboQualidade.setItens(estado, "descr");

		List<MascaraPreco> mascaraPreco = mascaraPrecoRepository.findAll();
		cboMascara.setItens(mascaraPreco, "mascaraComValor");

		List<Markup> markup = markupRepository.findAll();
		cboMarkup.setItens(markup, "sigla");

		this.paramOrcto = carregaParam();

	}

	private void loadItens() {
		List<ItemCompra> lista = new ArrayList<ItemCompra>();

		try {
			if (compra != null && this.acao.equals(Constantes.ACAO_CONSULTAR)) {
				lista = compraRepository.listaItens(compra.getId());
				vcContadorDeItens = lista.size();
				tabela.setValue(lista);
			}
			calculaValorTotalOrcto();
			calculaQuantidadeItens();
			calculaTotalItens();
			// calculaValorTotalDesconto();
			// calculaValorTotalProduto();
		} catch (Exception e) {
			e.printStackTrace();
			SSMensagem.erro(e.getMessage());
		}
	}

	private boolean validaItens() {
		boolean retorno = true;

		if (!Biblioteca.temValorValido(cboMascara) && !Biblioteca.temValorValido(cboCategoria)
				&& !Biblioteca.temValorValido(cboMarca) && !Biblioteca.temValorValido(cboTamanho)
				&& !Biblioteca.temValorValido(cboQualidade) && !Biblioteca.temValorValido(cboMarkup)
				&& !Biblioteca.temValorValido(txtQtde, "I") && !Biblioteca.temValorValido(txtValor, "N")
				&& !Biblioteca.temValorValido(cboMascara)) {
			SSMensagem.avisa("Falta dados para ser gravado!");
			retorno = false;
		}

		return retorno;
	}

	private void aprovarOrcto() {
		if (SSMensagem.confirma("Confirma Aprovação do Orçamento?")) {
			Pessoa pessoa = null;
//			System.out.println("1");
			if (!txtIdPessoa.getText().isEmpty()) {
				pessoa = pessoaRepository.findOne(Integer.parseInt(txtIdPessoa.getText()));
			}
			if (pessoa == null) {
				pessoa = showPessoa();
			}
			if (pessoa != null) {
				cboStatus.setValue(StatusOrcamento.A);
				salvar();
			} else {
				SSMensagem.informa("Falta cadastrar Pessoa!");
			}
		}
	}

	private void reprovarOrcto() {
		if (SSMensagem.confirma("Confirma Reprovação do Orçamento?")) {
			cboStatus.setValue(StatusOrcamento.R);
			salvar();
		}
	}

	public void checaStatus() {
		StatusOrcamento status = (StatusOrcamento) cboStatus.getValue();
		if (status == StatusOrcamento.R || status == StatusOrcamento.A) {
			habilitaBotoes(false);
		} else {
			habilitaBotoes(true);
		}
	}

	public void habilitaBotoes(boolean status) {
		cmdAprovado.setEnabled(status);
		cmdReprovado.setEnabled(status);
		cmdSalvar.setEnabled(status);
		// cmdSair.setEnabled(status);
		btnPessoas.setEnabled(status);
		chkNovo.setEnabled(status);
		cmdIncluir.setEnabled(status);
		cmdAlterar.setEnabled(status);
		cmdExcluir.setEnabled(status);
		chkNovoItem.setEnabled(!status);
		cmdSalvarItem.setEnabled(!status);

	}

	private Pessoa showPessoa() {
		Formulario frm = SpringDesktopApp.getBean(frmConsulta);
		this.dialogo(frm);
		((FrmPessoas) frm).setNome(txtNome.getText());
		Pessoa pessoa = ((FrmPessoas) frm).getPessoaSelecionada();
		if (pessoa != null) {
			txtIdPessoa.setText(pessoa.getId().toString());
			txtNome.setText(pessoa.getNome());
			txtTelefone.setText(pessoa.getTelefone());
		}
		return pessoa;
	}

	private ParamOrcto carregaParam() {
		ParamOrcto param = (ParamOrcto) paramOrctoRepository.findOne(1);
		if (param == null) {
			param = new ParamOrcto();
			param.setId(1);
			param.setDoacao(new BigDecimal("10.0"));
			param.setTroca(new BigDecimal("50.0"));
			paramOrctoRepository.save(param);
		}

		return param;
	}

}
