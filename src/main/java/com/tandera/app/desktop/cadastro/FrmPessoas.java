package com.tandera.app.desktop.cadastro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tandera.app.spring.SpringDesktopApp;
import com.tandera.core.dao.springjpa.PessoaRepository;
import com.tandera.core.model.cadastro.Pessoa;

import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.FormularioConsulta;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSGrade;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.util.Validacao;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmPessoas extends FormularioConsulta {
	
	@Autowired
	PessoaRepository dao;
	
	private String nome; // utilizado pela chamada de formularios de consulta,  podendo ser utulizado para inclusão
	private Pessoa pessoaSelecionada;

	

	Class formInclusao = FrmPessoa.class;

	// JA PODERIA VIR DE FormularioConsulta
	private JPanel filtro = new JPanel();
	private JScrollPane scroll = new JScrollPane();
	private SSGrade tabela = new SSGrade();

	private SSCampoTexto txtFiltro = new SSCampoTexto();
	private SSBotao cmdBuscar = new SSBotao();

	private SSBotao cmdSelecionar = new SSBotao();
	private SSBotao cmdIncluir = new SSBotao();
	private SSBotao cmdAlterar = new SSBotao();
	private SSBotao cmdFechar = new SSBotao();
	
	
	public FrmPessoas() {
		// JA PODERIA VIR DE FormularioConsulta
		setTitulo("Consulta de Pessoas");
		setDescricao("Listagem das Pessoas");
		setConteudoLayout(new BorderLayout());
		setAlinhamentoRodape(FlowLayout.LEFT);
		filtro.setLayout(new GridBagLayout());

		configurarCamposPesquisa();
		configurarCamposTabela();
		configurarConstraintsGrid();
		adicionarComponentesNoConteudo();
		adicionarActionListener();
	}

	private void configurarCamposPesquisa() {
		
		txtFiltro.setRotulo("Nome");
		txtFiltro.setColunas(50);
		cmdBuscar.setText("Buscar");

		cmdIncluir.setText("Incluir");
		cmdIncluir.setIcone("novo");
		cmdAlterar.setText("Alterar");
		cmdFechar.setText("Fechar");
		cmdSelecionar.setText("Selecionar");
		cmdSelecionar.setIcone("confirmar");
		
		txtFiltro.setColunas(30);
	}

	private void configurarCamposTabela() {
		// campos da tabela
		// BASICAMENTE O QUE VC TERÁ QUE MUDAR ENTRE FORMULARIOS
		tabela.getModeloTabela().addColumn("Id");
		tabela.getModeloTabela().addColumn("Nome");
		tabela.getModeloTabela().addColumn("CPF");

		tabela.getModeloColuna().getColumn(0).setPreferredWidth(30);
		tabela.getModeloColuna().getColumn(1).setPreferredWidth(250);
		tabela.getModeloColuna().getColumn(2).setPreferredWidth(120);

		tabela.getModeloColuna().setCampo(0, "id");
		tabela.getModeloColuna().setCampo(1, "nome");
		tabela.getModeloColuna().setCampo(2, "cpf");

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

		// adicionando componentes aos seus Naturezainers
		filtro.add(txtFiltro, gbcTxtFiltro);
		filtro.add(cmdBuscar, gbcCmdBuscar);
	}

	private void adicionarComponentesNoConteudo() {

		scroll.setViewportView(tabela);

		getConteudo().add(filtro, BorderLayout.NORTH);
		getConteudo().add(scroll, BorderLayout.CENTER);

		getRodape().add(cmdSelecionar);
		getRodape().add(cmdIncluir);
		getRodape().add(cmdAlterar);
		getRodape().add(cmdFechar);
	}

	private void adicionarActionListener() {
		// métodos
		cmdFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		cmdBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});
		cmdIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incluir();
			}
		});
		cmdAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		cmdSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionar();
			}
		});
	}

	public JPanel getFiltro() {
		return filtro;
	}

	private void sair() {
		super.fechar();
	}

	private void listar() {
		List<Pessoa> lista = new ArrayList<Pessoa>();
		try {
			String nome = txtFiltro.getText();
			if (Validacao.vazio(nome)) {
				lista = dao.findAll();

			} else {
				lista = dao.findByNomeContainingIgnoreCase(nome);
			}
			if (lista.size() == 0)
				SSMensagem.avisa("Nenhum dado encontrado");

			tabela.setValue(lista);
		} catch (Exception e) {
			e.printStackTrace();
			SSMensagem.erro(e.getMessage());
		}
	}

	private void incluir() {
		exibirCadastro(null);
	}

	private void alterar() {
		Pessoa entidade = (Pessoa) tabela.getLinhaSelecionada();
		if (entidade == null) {
			SSMensagem.avisa("Selecione um item da lista");
			return;
		}
		exibirCadastro(entidade);
	}

	private void selecionar() {
		setPessoaSelecionada( (Pessoa) tabela.getLinhaSelecionada());
		if (pessoaSelecionada == null) {
			SSMensagem.avisa("Selecione um item da lista");
			return;
		}
		sair();
	}	
	
	private void exibirCadastro(Pessoa entidade) {
		Formulario frm = SpringDesktopApp.getBean(formInclusao);
		frm.setEntidade(entidade);
		if (!this.isDialogo(this) ) {
			this.exibir(frm);
		} else {
			this.dialogo(frm);
			txtFiltro.setText(((FrmPessoa) frm).getEntidade().getNome());
			listar();
			
		}
	}
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pessoa getPessoaSelecionada() {
		return pessoaSelecionada;
	}

	public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
		this.pessoaSelecionada = pessoaSelecionada;
	}
	

}
