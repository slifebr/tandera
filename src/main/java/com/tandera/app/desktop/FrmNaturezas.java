package com.tandera.app.desktop;

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
import com.tandera.core.dao.Repositorio;
import com.tandera.core.model.Natureza;

import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSGrade;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.util.Validacao;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)	
public class FrmNaturezas extends Formulario {
	@Autowired
	private Repositorio dao;
	
	//JA PODERIA VIR DE FormularioConsulta
	private JPanel filtro = new JPanel();
	private JScrollPane scroll = new JScrollPane();
	private SSGrade tabela = new SSGrade();
	
	private SSCampoTexto txtFiltro = new SSCampoTexto();
	private SSBotao cmdBuscar = new SSBotao();
	
	private SSBotao cmdIncluir = new SSBotao();
	private SSBotao cmdAlterar = new SSBotao();
	private SSBotao cmdFechar = new SSBotao();
	
	public FrmNaturezas() {
		//JA PODERIA VIR DE FormularioConsulta
		setTitulo("Consulta de Naturezas");
		setDescricao("Listagem das Naturezas");
		setConteudoLayout(new BorderLayout());
		setAlinhamentoRodape(FlowLayout.LEFT);
		filtro.setLayout(new GridBagLayout());
		
		txtFiltro.setRotulo("Nome");
		txtFiltro.setColunas(30);
		cmdBuscar.setText("Buscar");
		
		cmdIncluir.setText("Incluir");
		cmdIncluir.setIcone("novo");
		cmdAlterar.setText("Alterar");
		cmdFechar.setText("Fechar");
		txtFiltro.setColunas(30);
		
		// campos da tabela
		//BASICAMENTE O QUE VC TERÁ QUE MUDAR ENTRE FORMULARIOS
		tabela.getModeloTabela().addColumn("Nome");
		tabela.getModeloTabela().addColumn("Tipo Movto");
		tabela.getModeloTabela().addColumn("Categoria");
		
		tabela.getModeloColuna().getColumn(0).setPreferredWidth(130);
		tabela.getModeloColuna().getColumn(1).setPreferredWidth(100);
		tabela.getModeloColuna().getColumn(2).setPreferredWidth(110);
		
		tabela.getModeloColuna().setCampo(0, "nome");
		tabela.getModeloColuna().setCampo(1, "tipoMovimento");
		tabela.getModeloColuna().setCampo(2, "categoria");

		//constraints - grid bag layout
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
		
		
		//adicionando componentes aos seus Naturezainers
		filtro.add(txtFiltro, gbcTxtFiltro);
		filtro.add(cmdBuscar, gbcCmdBuscar);
		
		scroll.setViewportView(tabela);
		
		getConteudo().add(filtro,BorderLayout.NORTH);
		getConteudo().add(scroll,BorderLayout.CENTER);
		
		getRodape().add(cmdIncluir);
		getRodape().add(cmdAlterar);
		getRodape().add(cmdFechar);
		
		//métodos
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
	}
	public JPanel getFiltro() {
		return filtro;
	}
	private void sair() {
		super.fechar();
	}
	private void listar() {
		List<Natureza> lista = new ArrayList<Natureza>();
		try {
			String nome = txtFiltro.getText();
			if (Validacao.vazio(nome)) {
				lista = dao.listarNaturezas(MDI.getPerfilId());

			} else {
				lista = dao.listarNaturezas(MDI.getPerfilId(), nome);
			}
			if(lista.size()==0)
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
		Natureza entidade= (Natureza) tabela.getLinhaSelecionada();
		if(entidade==null) {
			SSMensagem.avisa("Selecione um item da lista");
			return;
		}
		exibirCadastro(entidade);
	}
	private void exibirCadastro(Natureza entidade) {
		Formulario frm = SpringDesktopApp.getBean(FrmNatureza.class);
		frm.setEntidade(entidade);
		this.exibir(frm);
	}
	
}
