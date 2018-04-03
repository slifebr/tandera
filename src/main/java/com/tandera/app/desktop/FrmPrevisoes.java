package com.tandera.app.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tandera.app.spring.SpringDesktopApp;
import com.tandera.core.dao.Repositorio;
import com.tandera.core.dao.RepositorioLancamento;
import com.tandera.core.model.Conta;
import com.tandera.core.model.Lancamento;
import com.tandera.core.model.Natureza;
import com.tandera.core.model.Saldo;

import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ambiente.Total;
import edu.porgamdor.util.desktop.ss.PosicaoRotulo;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCaixaCombinacao;
import edu.porgamdor.util.desktop.ss.SSCampoDataHora;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSGrade;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.tabela.TipoSelecao;
import edu.porgamdor.util.desktop.ss.util.DataHora;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmPrevisoes extends Formulario {
	// rodape
	private SSBotao cmdCompensar = new SSBotao();
	private SSBotao cmdSelecionados = new SSBotao();
	private SSBotao cmdAmortizar = new SSBotao();
	private SSBotao cmdProrrogar = new SSBotao();
	private SSBotao cmdFechar = new SSBotao();
	private SSBotao cmdBuscar = new SSBotao();
	private SSGrade grid = new SSGrade();
	private JScrollPane scroll = new JScrollPane();
	// DAOs - NAO OFICIAL
	@Autowired
	private Repositorio dao;
	@Autowired
	private RepositorioLancamento lanctoDao;

	private SSCampoDataHora txtDataDe = new SSCampoDataHora();
	private SSCampoDataHora txtDataAte = new SSCampoDataHora();
	private SSCaixaCombinacao cboConta = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboNatureza = new SSCaixaCombinacao();
	private JLabel lblDesc = new JLabel();

	//
	private Saldo saldo;
	private List<Saldo> saldos;
	private Total total = new Total();
	private SSCampoNumero txtDespesas = new SSCampoNumero();
	private SSCampoNumero txtReceitas = new SSCampoNumero();
	private SSCampoNumero txtSaldoAtual = new SSCampoNumero();
	//

	public FrmPrevisoes() {
		init();
	}

	private void init() {
		cboConta.setPreferredWidth(180);
		cboNatureza.setPreferredWidth(150);
		super.setTitulo("Consulta de Previsões");
		super.setDescricao("Registro dos valores à pagar e à receber");
		setAlinhamentoRodape(FlowLayout.LEFT);
		getRodape().add(cmdCompensar);
		getRodape().add(cmdAmortizar);
		getRodape().add(cmdProrrogar);
		getRodape().add(cmdSelecionados);
		getRodape().add(cmdFechar);
		// implementando o conteudo do formulario
		JPanel conteudo = super.getConteudo();
		conteudo.setLayout(new BorderLayout());

		// usando o painel de conteudo
		JPanel painelFiltro = new JPanel();
		conteudo.add(painelFiltro, BorderLayout.NORTH);
		grid.setTipoSelecao(TipoSelecao.SELECAO_MULTIPLA);
		scroll.setViewportView(grid);
		JPanel pnlDesc = new JPanel(new BorderLayout());
		lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesc.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblDesc.setForeground(Color.BLUE);
		lblDesc.setText("SEGURE A TECLA Ctrl PARA SELECIONAR MAIS DE UMA LINHA");
		pnlDesc.add(lblDesc, BorderLayout.NORTH);
		pnlDesc.add(scroll, BorderLayout.CENTER);
		conteudo.add(pnlDesc, BorderLayout.CENTER);
		grid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				exibirDescricao();
			}
		});

		GridBagLayout gbl_painelFiltro = new GridBagLayout();
		painelFiltro.setLayout(gbl_painelFiltro);
		painelFiltro.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbcBuscar = new GridBagConstraints();
		gbcBuscar.anchor = GridBagConstraints.NORTHWEST;
		gbcBuscar.fill = GridBagConstraints.HORIZONTAL;
		gbcBuscar.insets = new Insets(15, 5, 5, 5);
		gbcBuscar.gridx = 4;
		gbcBuscar.gridy = 0;
		painelFiltro.add(cmdBuscar, gbcBuscar);

		// campos da tabela
		grid.getModeloTabela().addColumn("Data");
		grid.getModeloTabela().addColumn("Previsão");
		grid.getModeloTabela().addColumn("Conta");
		grid.getModeloTabela().addColumn("Natureza");
		grid.getModeloTabela().addColumn("Valor");
		grid.getModeloTabela().addColumn("Contato");

		grid.getModeloColuna().getColumn(0).setPreferredWidth(50);
		grid.getModeloColuna().getColumn(1).setPreferredWidth(55);
		grid.getModeloColuna().getColumn(2).setPreferredWidth(170);
		grid.getModeloColuna().getColumn(3).setPreferredWidth(120);
		grid.getModeloColuna().getColumn(4).setPreferredWidth(70);
		grid.getModeloColuna().getColumn(5).setPreferredWidth(100);

		grid.getModeloColuna().setCampo(0, "data");
		grid.getModeloColuna().setFormato(0, "dd/MM/yy");
		grid.getModeloColuna().setCampo(1, "quitacao");
		grid.getModeloColuna().setFormato(1, "dd/MM/yy");
		grid.getModeloColuna().setCampo(2, "conta.nome");
		// grid.getModeloColuna().setCampo(2, "sigla");
		grid.getModeloColuna().setCampo(3, "natureza.nome");
		grid.getModeloColuna().setCampo(4, "valor");
		grid.getModeloColuna().setFormato(4, Formato.MOEDA);
		grid.getModeloColuna().definirPositivoNegativo(4);
		grid.getModeloColuna().setCampo(5, "contato");

		cmdCompensar.setText("Compensar");
		cmdAmortizar.setText("Amortizar");
		cmdProrrogar.setText("Atualizar");
		cmdSelecionados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				compensarSelecionados();
			}
		});
		cmdSelecionados.setText("Selecionados");

		cmdFechar.setText("Fechar");
		cmdBuscar.setText("Buscar");
		cmdCompensar.setIcone("dinheiro");
		cmdProrrogar.setIcone("atualizar");
		cmdAmortizar.setIcone("amortizar");
		cmdSelecionados.setIcone("selecionados");

		GridBagConstraints gbc_txtDataDe = new GridBagConstraints();
		gbc_txtDataDe.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDataDe.insets = new Insets(5, 5, 5, 0);
		gbc_txtDataDe.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDataDe.gridx = 0;
		gbc_txtDataDe.gridy = 0;
		txtDataDe.setColunas(8);
		txtDataDe.setRotulo("De");
		painelFiltro.add(txtDataDe, gbc_txtDataDe);

		GridBagConstraints gbc_txtDataAte = new GridBagConstraints();
		gbc_txtDataAte.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDataAte.insets = new Insets(5, 5, 5, 0);
		gbc_txtDataAte.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDataAte.gridx = 1;
		gbc_txtDataAte.gridy = 0;
		txtDataAte.setColunas(8);
		txtDataAte.setRotulo("Até");
		painelFiltro.add(txtDataAte, gbc_txtDataAte);

		GridBagConstraints gbc_cboConta = new GridBagConstraints();
		gbc_cboConta.weightx = 1.0;
		gbc_cboConta.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboConta.insets = new Insets(5, 5, 5, 0);
		gbc_cboConta.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboConta.gridx = 2;
		gbc_cboConta.gridy = 0;
		cboConta.setRotulo("Conta");
		painelFiltro.add(cboConta, gbc_cboConta);

		GridBagConstraints gbc_cboNatureza = new GridBagConstraints();
		gbc_cboNatureza.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboNatureza.weightx = 1.0;
		gbc_cboNatureza.insets = new Insets(5, 5, 5, 0);
		gbc_cboNatureza.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboNatureza.gridx = 3;
		gbc_cboNatureza.gridy = 0;
		cboNatureza.setRotulo("Natureza");
		painelFiltro.add(cboNatureza, gbc_cboNatureza);
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
		cmdCompensar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compensar();
			}
		});
		cmdAmortizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				amortizar();
			}
		});
		cmdProrrogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prorrogar();
			}
		});

		//
		FlowLayout pnlSaldoLayout = new FlowLayout();
		pnlSaldoLayout.setAlignment(FlowLayout.RIGHT);
		JPanel pnlSaldo = new JPanel(pnlSaldoLayout);
		pnlSaldo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		pnlDesc.add(pnlSaldo, BorderLayout.SOUTH);
		txtDespesas.setComponenteCorFonte(Color.RED);
		txtDespesas.setComponenteNegrito(true);
		txtDespesas.setEditavel(false);
		txtDespesas.setColunas(6);
		txtDespesas.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		txtDespesas.setRotulo("Despesa");
		txtReceitas.setComponenteNegrito(true);
		txtReceitas.setComponenteCorFonte(Color.BLUE);

		txtReceitas.setEditavel(false);
		txtReceitas.setColunas(6);
		txtReceitas.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		txtReceitas.setRotulo("Receita");
		txtSaldoAtual.setComponenteNegrito(true);
		txtSaldoAtual.setComponenteCorFonte(Color.BLUE);

		txtSaldoAtual.setEditavel(false);
		txtSaldoAtual.setColunas(6);
		txtSaldoAtual.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		txtSaldoAtual.setRotulo("Saldo");

		txtDespesas.setFormato(Formato.MOEDA);
		txtSaldoAtual.setFormato(Formato.MOEDA);
		txtReceitas.setFormato(Formato.MOEDA);

		pnlSaldo.add(txtReceitas);
		pnlSaldo.add(txtDespesas);
		pnlSaldo.add(txtSaldoAtual);
		txtReceitas.setComponenteCorFonte(Color.BLUE);
		txtDespesas.setComponenteCorFonte(Color.RED);

	}

	@Override
	public void load() {
		cboConta.setItens(dao.listarContas(MDI.getPerfilId()), "nome");
		cboNatureza.setItens(dao.listarNaturezas(MDI.getPerfilId()), "nome");
		txtDataDe.setDataHora(DataHora.primeiroDiaDoMes());
		txtDataAte.setDataHora(DataHora.ultimoDiaDoMes());

	}

	private void exibirDescricao() {
		try {
			Lancamento l = (Lancamento) grid.getLinhaSelecionada();
			if (l != null) {
				lblDesc.setText(l.getDescricao());
			}
		} catch (java.lang.IndexOutOfBoundsException e) {
			// TODO: handle exception
		}
	}

	private void sair() {
		super.fechar();
	}

	private void prorrogar() {
		Lancamento entidade = (Lancamento) grid.getLinhaSelecionada();
		if (entidade != null) {
			// FrmProrrogar frm = getBean(FrmProrrogar.class);
			FrmAtualizar frm = SpringDesktopApp.getBean(FrmAtualizar.class);
			frm.setId(entidade.getId());
			this.dialogo(frm);
			listar();
		} else
			SSMensagem.avisa("Selecione um item da lista");
	}

	private void amortizar() {
		Lancamento entidade = (Lancamento) grid.getLinhaSelecionada();
		if (entidade != null) {
			FrmAmortizar frm = SpringDesktopApp.getBean(FrmAmortizar.class);
			frm.setId(entidade.getId());
			this.dialogo(frm);
			listar();
		} else
			SSMensagem.avisa("Selecione um item da lista");
	}

	private void compensar() {
		Lancamento entidade = (Lancamento) grid.getLinhaSelecionada();
		if (entidade != null) {
			FrmCompensar frm = SpringDesktopApp.getBean(FrmCompensar.class);
			frm.setId(entidade.getId());
			this.dialogo(frm);
			listar();
		} else
			SSMensagem.avisa("Selecione um item da lista");

	}

	private void compensarSelecionados() {
		if (cboConta.getValue() == null) {
			SSMensagem.avisa("Favor selecione uma conta");
			return;
		}
		if (SSMensagem.pergunta("Confirma compensar os itens selecionados?")) {
			Object[] itens = grid.getLinhasSelecionadas();
			if (itens == null || itens.length == 0) {
				SSMensagem.avisa("Nenhuma linha selecionada");
				return;
			}
			Integer[] ids = new Integer[itens.length];
			for (int x = 0; x < itens.length; x++) {
				Lancamento vo = (Lancamento) itens[x];
				ids[x] = vo.getId();
			}
			lanctoDao.compensarLancamento(new Date(), null, ids);
			SSMensagem.informa("Lançamentos compensados com sucesso!!");
			listar();
		}
	}

	private void listar() {
		List<Lancamento> lista = new ArrayList<Lancamento>();
		try {
			// lista = dao.listarOldLancamentos(getUsuarioId());
			Conta conta = (Conta) cboConta.getValue();
			Natureza nat = (Natureza) cboNatureza.getValue();
			Integer cId = null;
			Integer nId = null;
			if (conta != null)
				cId = conta.getId();

			if (nat != null)
				nId = nat.getId();

			lista = lanctoDao.listarPrevisoes(MDI.getPerfilId(), txtDataDe.getDataHora(), txtDataAte.getDataHora(), cId, nId);
			if (lista.size() == 0)
				SSMensagem.avisa("Nenhum dado encontrado");

			grid.setValue(lista);
			total = lanctoDao.totais(lista);
			txtSaldoAtual.setValue(total.getSaldo());
			txtSaldoAtual.setComponenteCorFonte(total.getSaldo() < 0.0d ? Color.RED : Color.BLUE);
			txtDespesas.setValue(total.getDebito());
			txtReceitas.setValue(total.getCredito());
		} catch (Exception e) {
			e.printStackTrace();
			// Mensagem.erro(e.getMessage());
		}

	}
}
