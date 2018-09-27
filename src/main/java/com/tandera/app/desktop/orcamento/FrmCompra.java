package com.tandera.app.desktop.orcamento;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tandera.core.dao.springjpa.CompraRepository;
import com.tandera.core.dao.springjpa.EstadoRepository;
import com.tandera.core.dao.springjpa.MarcaRepository;
import com.tandera.core.dao.springjpa.MarkupRepository;
import com.tandera.core.dao.springjpa.MascaraPrecoRepository;
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
import com.tandera.core.services.CategoriaService;
import com.tandera.core.util.Biblioteca;

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
import edu.porgamdor.util.desktop.ss.evento.ValidacaoEvento;
import edu.porgamdor.util.desktop.ss.evento.ValidacaoListener;

@Component
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmCompra extends Formulario {

	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	private CategoriaService categoriaService;
	private MarcaRepository marcaRepository;
	private TamanhoRepository tamanhoRepository;
	private EstadoRepository estadoRepository;
	private MascaraPrecoRepository mascaraPrecoRepository;
	private MarkupRepository markupRepository;

	private Compra compra;
	private ItemCompra itemCompraSelecionado;
	private MascaraPreco mascaraPreco;

	//CAMPOS COMPRA
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

	//CAMPOS ITEM COMPRA
	private SSCampoTexto txtItem = new SSCampoTexto();
	private SSCaixaCombinacao cboCategoria = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboMarca = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboTamanho = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboQualidade = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboMascara = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboMarkup = new SSCaixaCombinacao();
	private SSCampoNumero txtQtde = new SSCampoNumero();
	private SSCampoNumero txtValor = new SSCampoNumero();

	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private JCheckBox chkNovo = new JCheckBox("Novo?");
	private JCheckBox chkNovoItem = new JCheckBox("Novo?");

	private JScrollPane scroll = new JScrollPane();
	private SSGrade tabela = new SSGrade();
	/**
	 * @wbp.nonvisual location=-40,239
	 */
	// private final JPanel panel = new JPanel();
	private final JPanel panel_grade = new JPanel();
	private final JPanel panel_inclusao = new JPanel();
	private final JButton btnIncluir = new JButton("Incluir");
	private final JButton btnAlterar = new JButton("Alterar");
	private final JButton btnExcluirItem = new JButton("Excluir");
	private final SSBotao salvarItem = new SSBotao();
	private final JPanel panel = new JPanel();
	
	/*
	 *  variaveis de classe
    */
	private Integer vcContadorDeItens;
	private String acao; //NOVO | ALTERAR | EXCLUIR | CONSULTAR
	
	
	@Autowired
	public FrmCompra(CategoriaService categoriaService
					 ,TamanhoRepository tamanhoRepository
					 ,MarcaRepository marcaRepository
					 ,EstadoRepository estadoRepository
					 ,MascaraPrecoRepository mascaraPrecoRepository
					 ,MarkupRepository markupRepository) {

		
		this.categoriaService = categoriaService;
		this.tamanhoRepository = tamanhoRepository;
		this.marcaRepository = marcaRepository;
		this.estadoRepository = estadoRepository;
		this.mascaraPrecoRepository = mascaraPrecoRepository;
		this.markupRepository = markupRepository;
		
		getConteudo().setBackground(Color.YELLOW);
		getConteudo().setLayout(null);
		panel_grade.setBackground(Color.MAGENTA);
		panel_grade.setBounds(0, 190, 940,270); //903, 270);
		getConteudo().add(panel_grade);
		

		configurarCamposTabela();
		configurarConstraintsGrid();
		adicionarScrollGrade();

		init();
		panel_inclusao.setVisible(false);
		setPreferredSize(new Dimension(883, 543));
		//setSize(new Dimension(883, 543));		
	}

	private void init() {
		super.setTitulo("Orçamento");
		super.setDescricao("Lançamentos de Orçamentos");
		super.getRodape().add(chkNovo);
		super.getRodape().add(cmdSalvar);
		super.getRodape().add(cmdSair);
		
		cboStatus.setItens(StatusOrcamento.values());
		cboConsignado.setItens(SimNao.values());
		
		adicionarListner();
		configurarCamposCompra();
		configurarCamposItemCompra();
		load();
	
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

		txtData.setBounds(100, 5, 146, 50);
		panelCampos.add(txtData);
		txtData.setColunas(10);
		txtData.setRotulo("Data");

		cboConsignado.setBounds(300, 5, 100, 50);
		cboConsignado.setRotulo("Consignado");
		panelCampos.add(cboConsignado);	
		
		cboStatus.setBounds(656, 5, 205, 50);
		cboStatus.setRotulo("Status");
		panelCampos.add(cboStatus);

		txtIdPessoa.setBounds(10, 57, 80, 50);
		panelCampos.add(txtIdPessoa);
		txtIdPessoa.setColunas(10);
		txtIdPessoa.setRotulo("Cód. Pessoa");

		txtNome.setBounds(100, 57, 546, 50);
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
		panelCampos.add(txtDeposito);
		txtTroca.getComponente().setBounds(30, 15, 175, 20);
		txtTroca.setRotuloPosicao(PosicaoRotulo.ESQUERDA);

		txtTroca.setBounds(656, 135, 205, 25);
		txtTroca.setRotulo("Troca      ");
		txtTroca.setColunas(10);
		panelCampos.add(txtTroca);
		txtDoacao.setRotuloPosicao(PosicaoRotulo.ESQUERDA);

		txtDoacao.setBounds(656, 161, 205, 25);
		txtDoacao.setRotulo("Doação   ");
		txtDoacao.setColunas(10);
		panelCampos.add(txtDoacao);

		cmdSair.setText("Fechar");
		cmdSalvar.setText("Salvar");

	}
	
	private void configurarCamposItemCompra() {
		FlowLayout fl_panel_inclusao = new FlowLayout(FlowLayout.LEFT, 5, 5);
		panel_inclusao.setLayout(fl_panel_inclusao);
		
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
		txtQtde.setComponenteTamanhoPreferido(new Dimension(60, 20));
		txtQtde.setRotulo("Qtde");
		
		panel_inclusao.add(txtValor);
		txtValor.setComponenteTamanhoPreferido(new Dimension(50, 20));
		txtValor.setRotulo("Valor");
		panel.setPreferredSize(new Dimension(85, 50));
		panel.setMinimumSize(new Dimension(100, 100));
		panel.setBounds(new Rectangle(0, 0, 100, 100));
		panel.setAlignmentY(0.0f);
		panel.setAlignmentX(0.0f);
		
		panel_inclusao.add(panel);
		panel.setLayout(null);
		salvarItem.setBounds(0, 25, 83, 25);
		panel.add(salvarItem);
		salvarItem.setAlignmentY(java.awt.Component.BOTTOM_ALIGNMENT);
		salvarItem.setText("Salvar");
		
		
		chkNovoItem.setBounds(0, 0, 55, 23);
		panel.add(chkNovoItem);
		
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
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				novoItem();
			}
		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				novoItem();
			}
		});
		
		btnExcluirItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				excluirItem();
			}
		});
		
		salvarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adicionarItem();
			}
		});
		
		cboMascara.addValidacaoListener(new ValidacaoListener() {
			
			@Override
			public void validacaoListener(ValidacaoEvento evento) {
				
				buscaValorUnitario();
			}
		}); 
		
		cboMarkup.addValidacaoListener(new ValidacaoListener() {
			
			@Override
			public void validacaoListener(ValidacaoEvento arg0) {
				buscaValorUnitario();
				
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
		//tabela.getModeloColuna().setMascara(8,"##0.00");
		tabela.getModeloColuna().setCampo(9, "valorTotal");
		tabela.getModeloColuna().setAlinhamentoColuna(9, SwingConstants.RIGHT);
		//tabela.getModeloColuna().setMascara(9,"##0.00");

	}

	private void adicionarScrollGrade() {
		
		panel_inclusao.setBackground(Color.GREEN);
		scroll.setViewportBorder(new LineBorder(Color.BLACK));

		scroll.setViewportView(tabela);
		
		JPanel panel_radape = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_radape.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_radape.setBackground(Color.BLUE);
		GroupLayout gl_panel_grade = new GroupLayout(panel_grade);
		gl_panel_grade.setHorizontalGroup(
			gl_panel_grade.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_grade.createSequentialGroup()
				.addGroup(gl_panel_grade.createParallelGroup(Alignment.TRAILING, false)
				.addComponent(scroll, Alignment.LEADING)
				.addComponent(panel_inclusao, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addComponent(panel_radape, GroupLayout.DEFAULT_SIZE, 871, Short.MAX_VALUE)
		);
		gl_panel_grade.setVerticalGroup(
			gl_panel_grade.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_grade.createSequentialGroup()
				.addComponent(panel_inclusao, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(panel_radape, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
				.addContainerGap())
		);
		
		panel_radape.add(btnIncluir);
		panel_radape.add(btnAlterar);
		panel_radape.add(btnExcluirItem);
		panel_grade.setLayout(gl_panel_grade);

	}



	private void buscaValorUnitario() {
		this.mascaraPreco = ((MascaraPreco) cboMascara.getValue());
		BigDecimal valorMascara = BigDecimal.ZERO;
		BigDecimal valorMarkup = BigDecimal.ZERO;
		if (this.cboMarkup.getValue() != null){
			valorMarkup = ((Markup) this.cboMarkup.getValue()).getValor();
			valorMascara = this.mascaraPreco.getValor();
			
			if(valorMarkup.doubleValue() != 0){
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
			txtIdPessoa.setValue(compra.getPessoa());
			txtNome.setValue(compra.getNome());
			txtTelefone.setValue(compra.getTelefone());
			txtObs.setValue(compra.getObs());
			txtDeposito.setValue(compra.getVlDeposito());
			txtTroca.setValue(compra.getVlTroca());
			txtDoacao.setValue(compra.getVlDoacao());
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
			if (this.acao.compareTo("EXCLUIR") == 0) {
				
				SSMensagem.informa("antes de salvar!!");	
				compra.getItemCompra().forEach(System.out::println);
				
				compraRepository.saveAndFlush(compra);
				SSMensagem.informa("Item Excluido com sucesso!!");				
			} else {	
				compra.setData(txtData.getDataHora());
				compra.setStatus((StatusOrcamento) cboStatus.getValue());
				
				Pessoa pessoa = pessoaRepository.findById(txtIdPessoa.getInteger());
				
				compra.setPessoa(pessoa);
				compra.setNome(txtNome.getText());
				compra.setTelefone(txtTelefone.getText());
				compra.setObs(txtObs.getText());
				compra.setVlDeposito(BigDecimal.valueOf(txtDeposito.getDouble()));
				compra.setVlTroca(BigDecimal.valueOf(txtTroca.getDouble()));
				compra.setVlDoacao(BigDecimal.valueOf(txtDoacao.getDouble()));
				compra.setConsignado((SimNao) cboConsignado.getValue());
				
				if (compra.getData() == null || compra.getStatus() == null || compra.getNome() == null
						|| compra.getNome().isEmpty() || compra.getTelefone() == null
						|| compra.getTelefone().isEmpty()) {
					SSMensagem.avisa("Dados incompletos");
					return;
				}
				
				// dao.gravar(operacao, entidade);
				
				compraRepository.save(compra);
				
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
		super.fechar();
	}
	
	private void novoItem() {
		panel_inclusao.setVisible(true);
	}
	
	private void alterarItem() {
		panel_inclusao.setVisible(true);
	}
	
	private void excluirItem() {
		ItemCompra itemCompra = (ItemCompra) tabela.getLinhaSelecionada();
		if (itemCompra == null) {
			SSMensagem.avisa("Selecione um item da lista");
			return;
		}

		if (SSMensagem.confirma("Confirma exclusão do Registro (" + itemCompra.getItem() + "-"+ itemCompra.getCategoria().getDescr()+")?")){
			this.acao = "EXCLUIR";
			SSMensagem.avisa("AQUI: "+tabela.getSelectedRow());
			tabela.getModeloTabela().fireTableRowsDeleted(tabela.getSelectedRow(), tabela.getSelectedRow());
			SSMensagem.avisa("AQUI");
			compra.getItemCompra().remove(itemCompra);
			SSMensagem.avisa("AQUI2");
			//tabela.remove(tabela.getSelectedRow());
			salvar();
			SSMensagem.avisa("AQUI3");
			listaItens();
			SSMensagem.avisa("AQUI4");
			
		}
		
	}
	
	private boolean validaItens() {
		boolean retorno = true;

		if (
		      !Biblioteca.temValorValido(cboMascara) &&
		      !Biblioteca.temValorValido(cboCategoria) &&
		      !Biblioteca.temValorValido(cboMarca) &&
		      !Biblioteca.temValorValido(cboTamanho) &&
		      !Biblioteca.temValorValido(cboQualidade) &&
		      !Biblioteca.temValorValido(cboMarkup) &&
		      !Biblioteca.temValorValido(txtQtde,"I")  &&
		      !Biblioteca.temValorValido(txtValor,"N")  &&
		      !Biblioteca.temValorValido(cboMascara)
		    ) {
			SSMensagem.avisa("Falta dados para ser gravado!" );
			retorno = false;
		}
			
		return retorno;
	}
	
	private void adicionarItem(){
		this.acao = "NOVO";
		List<ItemCompra> listaDeItens = new ArrayList<ItemCompra>();;
		ItemCompra itemCompra;
		
		if (this.acao == "NOVO") {
			vcContadorDeItens++;
			itemCompra = new ItemCompra();
		} else {
			itemCompra = this.itemCompraSelecionado;
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
			itemCompra.setValor(BigDecimal.valueOf(txtValor.getDouble()));
			itemCompra.setCompra(compra);
			itemCompra.setItem(vcContadorDeItens);
			
			BigDecimal mascara = BigDecimal.ZERO;
			mascara = Biblioteca.descriptoStringToBigDecimal(this.mascaraPreco.getMascara());
			itemCompra.setVlMascara(mascara);
			listaDeItens.add(itemCompra);
			this.compra.setItemCompra(listaDeItens);
		}
		
		// limpar campos de adicao/alteração de itens
		limparPainelInclusao();
		listaItens();
		
		if (!chkNovoItem.isSelected()) {		
			panel_inclusao.setVisible(false);
		}
	}
	
	private void limparPainelInclusao() {
		cboCategoria.setValue(null);
		cboMascara.setValue(null);		
		cboMarca.setValue(null);
		cboTamanho.setValue(null);
		cboMarkup.setValue(null);
		txtQtde.setText(null);
		txtValor.setText(null);
	}
	
	//@Override
	public void load() {
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
		
	
	}
	
	private void loadItens() {
		List<ItemCompra> lista = new ArrayList<ItemCompra>();
		vcContadorDeItens = 0;
		try {
			if (compra != null){
				lista = compraRepository.listaItens(compra.getId());
				vcContadorDeItens = lista.size();
			}
			tabela.setValue(lista);
		} catch (Exception e) {
			e.printStackTrace();
			SSMensagem.erro(e.getMessage());
		}
	}	
	
	private void listaItens() {
		loadItens();
	}
}
