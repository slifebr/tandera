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
import com.tandera.core.model.Conta;
import com.tandera.core.model.Saldo;

import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ss.PosicaoRotulo;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCaixaCombinacao;
import edu.porgamdor.util.desktop.ss.SSGrade;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)	
public class FrmSaldos extends Formulario {
	// rodape
	private SSBotao cmdIncluir = new SSBotao();
	private SSBotao cmdAlterar = new SSBotao();
	private SSBotao cmdFechar = new SSBotao();
	// conteudo - topo - filtro
	private SSCaixaCombinacao cboConta = new SSCaixaCombinacao();
	private SSBotao cmdBuscar = new SSBotao();
	private SSGrade grid = new SSGrade();
	private JScrollPane scroll = new JScrollPane();
	// DAOs - NAO OFICIAL
	@Autowired
	private Repositorio dao;

	public FrmSaldos() {
		init();
	}
	private void init() {
		super.setTitulo("Consulta de Saldos");
		super.setDescricao("Exibe os saldos registrados por conta");
		setAlinhamentoRodape(FlowLayout.LEFT);
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
		painelFiltro.add(cboConta, gbcNome);

		GridBagConstraints gbcBuscar = new GridBagConstraints();
		gbcBuscar.insets = new Insets(0, 0, 0, 5);
		gbcBuscar.gridx = 1;
		gbcBuscar.gridy = 0;
		painelFiltro.add(cmdBuscar, gbcBuscar);

		// campos da tabela
		grid.getModeloTabela().addColumn("Data");
		grid.getModeloTabela().addColumn("Valor");
		grid.getModeloColuna().getColumn(0).setPreferredWidth(70);
		grid.getModeloColuna().getColumn(1).setPreferredWidth(155);

		grid.getModeloColuna().setCampo(0, "data");
		grid.getModeloColuna().setCampo(1, "valor");
		grid.getModeloColuna().setFormato(0, "dd/MM/yy");
		grid.getModeloColuna().setFormato(1, Formato.MOEDA);
		
		
		cboConta.setRotulo("Nome");
		cboConta.setRotuloPosicao(PosicaoRotulo.ESQUERDA);

		cmdIncluir.setText("Novo");
		cmdAlterar.setText("Alterar");
		cmdFechar.setText("Fechar");
		cmdBuscar.setText("Buscar");
		cmdAlterar.setVisible(false);
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
				//alterar();
			}
		});
		
	}

	public void load() {
		cboConta.setItens(dao.listarContas(MDI.getPerfilId()),"nome");
	}
	private void sair() {
		super.fechar();
	}

	private void incluir() {
		abrirCadastro();
	}
	
	private void abrirCadastro() {
		Formulario frm = SpringDesktopApp.getBean(FrmSaldo.class);
		frm.load();
		this.exibir(frm);
	}

	private void listar() {
		List<Saldo> lista = new ArrayList<Saldo>();
		try {
			Conta conta = (Conta) cboConta.getValue();
			if(conta!=null) {
				lista = dao.listarSaldos(conta.getId());
				if(lista.size()==0)
					SSMensagem.avisa("Nenhum dado encontrado");
			}else
				SSMensagem.avisa("Selecione uma Conta");
			grid.setValue(lista);
		} catch (Exception e) {
			e.printStackTrace();
			SSMensagem.erro(e.getMessage());
		}

	}
	
}
