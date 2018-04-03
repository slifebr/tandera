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
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tandera.app.spring.SpringDesktopApp;
import com.tandera.core.dao.Repositorio;
import com.tandera.core.model.DespesaRapida;

import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ss.PosicaoRotulo;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSGrade;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)	
public class FrmDespesasRapidas extends Formulario {
	// rodape
	private SSBotao cmdIncluir = new SSBotao();
	private SSBotao cmdAlterar = new SSBotao();
	private SSBotao cmdFechar = new SSBotao();
	// conteudo - topo - filtro
	private SSCampoTexto txtFiltroNome = new SSCampoTexto();
	private SSBotao cmdBuscar = new SSBotao();
	private SSGrade grid = new SSGrade();
	private JScrollPane scroll = new JScrollPane();
	// DAOs - NAO OFICIAL
	@Autowired
	private Repositorio dao;

	public FrmDespesasRapidas() {
		init();
	}
	private void init() {
		super.setTitulo("Consulta de Despesas Rapidas");
		super.setDescricao("Registro das lançamentos mais comuns");
		super.setAlinhamentoRodape(FlowLayout.LEFT);
		getRodape().add(cmdIncluir);
		getRodape().add(cmdAlterar);
		getRodape().add(cmdFechar);
		// implementando o conteudo do formulario
		JPanel conteudo = super.getConteudo();
		conteudo.setLayout(new BorderLayout());
		// usando o painel de conteudo
		JPanel painelFiltro = new JPanel();
		conteudo.add(painelFiltro, BorderLayout.NORTH);
		scroll.getViewport().add(grid, null);

		conteudo.add(scroll, BorderLayout.CENTER);

		painelFiltro.setLayout(new GridBagLayout());
		painelFiltro.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagConstraints gbcNome = new GridBagConstraints();
		gbcNome.weightx = 1.0;
		gbcNome.insets = new Insets(5, 5, 5, 5);
		gbcNome.fill = GridBagConstraints.HORIZONTAL;
		gbcNome.gridx = 0;
		gbcNome.gridy = 0;
		painelFiltro.add(txtFiltroNome, gbcNome);

		GridBagConstraints gbcBuscar = new GridBagConstraints();
		gbcBuscar.insets = new Insets(0, 0, 0, 5);
		gbcBuscar.gridx = 1;
		gbcBuscar.gridy = 0;
		painelFiltro.add(cmdBuscar, gbcBuscar);

		// campos da tabela
		grid.getModeloTabela().addColumn("Conta");
		grid.getModeloTabela().addColumn("Natureza");
		grid.getModeloTabela().addColumn("Valor");
		grid.getModeloTabela().addColumn("Ord");
		grid.getModeloColuna().getColumn(0).setPreferredWidth(110);
		grid.getModeloColuna().getColumn(1).setPreferredWidth(110);
		grid.getModeloColuna().getColumn(2).setPreferredWidth(50);
		grid.getModeloColuna().getColumn(3).setPreferredWidth(30);
		grid.getModeloColuna().setFormato(2, Formato.MOEDA);
		
		grid.getModeloColuna().setCampo(0, "conta.nome");
		grid.getModeloColuna().setCampo(1, "natureza.nome");
		grid.getModeloColuna().setCampo(2, "valor");
		grid.getModeloColuna().setCampo(3, "ordem");
		
		txtFiltroNome.setRotulo("Conta\\Natureza");
		txtFiltroNome.setRotuloPosicao(PosicaoRotulo.ESQUERDA);

		cmdIncluir.setText("Novo");
		cmdAlterar.setText("Alterar");
		cmdFechar.setText("Fechar");
		cmdBuscar.setText("Buscar");

		cmdBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});

		cmdFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
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

	private void sair() {
		super.fechar();
	}

	
	/*private void excluir() {
		DespesaRapida entidade= (DespesaRapida) grid.getLinhaSelecionada();
		if(entidade==null) {
			SSMensagem.avisa("Selecione um item para a exclusão");
			return;
		}
		if(SSMensagem.pergunta("Confirma excluir o item selecionado")) {
			dao.excluir(entidade.getClass(), entidade.getId());
			SSMensagem.informa("Item excluído com sucesso");
			listar();
		}
	}*/
	private void incluir() {
		exibirCadastro(null);
	}
	private void alterar() {
		DespesaRapida entidade= (DespesaRapida) grid.getLinhaSelecionada();
		exibirCadastro(entidade);
	}

	private void exibirCadastro(DespesaRapida entidade) {
		Formulario frm = SpringDesktopApp.getBean(FrmDespesaRapida.class);
		frm.setEntidade(entidade);
		frm.load();
		this.exibir(frm);
	}
	private void listar() {
		List<DespesaRapida> lista = new ArrayList<DespesaRapida>();
		try {
			lista = dao.listarDespesasRapidas(MDI.getPerfilId());
			if(lista.size()==0)
				SSMensagem.avisa("Nenhum dado encontrado");
			
			grid.setValue(lista);
		} catch (Exception e) {
			e.printStackTrace();
			SSMensagem.erro(e.getMessage());
		}

	}
	
}
