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

import com.tandera.core.dao.Repositorio;
import com.tandera.core.dao.RepositorioLancamento;
import com.tandera.core.model.Conta;
import com.tandera.core.model.Lancamento;
import com.tandera.core.model.Saldo;

import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ambiente.Total;
import edu.porgamdor.util.desktop.ss.PosicaoRotulo;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCaixaCombinacao;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSGrade;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)	
public class FrmExtrato extends Formulario {
	// rodape
	private SSBotao cmdAtualizar = new SSBotao();
	
	private SSBotao cmdFechar = new SSBotao();
	private SSGrade grid = new SSGrade();
	private JScrollPane scroll = new JScrollPane();
	// DAOs - NAO OFICIAL
	@Autowired
	private RepositorioLancamento lactoDao;
	@Autowired
	private Repositorio dao;
	
	
	private Conta conta;
	private SSCampoNumero txtSaldoInicial = new SSCampoNumero();
	private SSCampoTexto txtConta = new SSCampoTexto();
	private SSCampoNumero txtSaldoFinal = new SSCampoNumero();
	private SSCaixaCombinacao cboSaldo = new SSCaixaCombinacao();
	private JLabel lblDesc = new JLabel();
	//
	private Saldo saldo;
	private List<Saldo> saldos;
	private Total total=new Total();
	private SSCampoNumero txtDespesas = new SSCampoNumero();
	private SSCampoNumero txtReceitas = new SSCampoNumero();
	private SSCampoNumero txtSaldoAtual = new SSCampoNumero();
	//
	public FrmExtrato(){
		init();
	}
	private void init() {
		cboSaldo.setRotulo("Saldo");
		super.setTitulo("Extrato");
		txtSaldoInicial.setComponenteNegrito(true);
		
		txtSaldoInicial.setEditavel(false);
		txtSaldoInicial.setFormato(Formato.MOEDA);
		super.setDescricao("Movimentações da conta");
		txtSaldoInicial.setColunas(6);
		txtSaldoInicial.setRotulo("Saldo Inicial");
		setAlinhamentoRodape(FlowLayout.LEFT);
		getRodape().add(cmdAtualizar);
		getRodape().add(cmdFechar);
		// implementando o conteudo do formulario
		JPanel conteudo = super.getConteudo();
		conteudo.setLayout(new BorderLayout());

		// usando o painel de conteudo
		JPanel painelFiltro = new JPanel();
		conteudo.add(painelFiltro, BorderLayout.NORTH);
		scroll.setViewportView(grid);
		JPanel pnlDesc= new JPanel(new BorderLayout());
		lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesc.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblDesc.setForeground(Color.BLUE);
		lblDesc.setText("SELECIONE UMA LINHA PARA MAIORES INFORMAÇÕES");
		pnlDesc.add(lblDesc,BorderLayout.NORTH);
		pnlDesc.add(scroll,BorderLayout.CENTER);
		//
		FlowLayout pnlSaldoLayout = new FlowLayout();
		pnlSaldoLayout.setAlignment(FlowLayout.RIGHT);
		JPanel pnlSaldo= new JPanel(pnlSaldoLayout);
		pnlSaldo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		pnlDesc.add(pnlSaldo,BorderLayout.SOUTH);
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
		//
		conteudo.add(pnlDesc, BorderLayout.CENTER);
		grid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent event) {
		        exibirDescricao();
		    }
		});
		GridBagLayout gbl_painelFiltro = new GridBagLayout();
		painelFiltro.setLayout(gbl_painelFiltro);
		painelFiltro.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		// campos da tabela
		grid.getModeloTabela().addColumn("Data");
		grid.getModeloTabela().addColumn("Natureza");
		grid.getModeloTabela().addColumn("Inicial");
		grid.getModeloTabela().addColumn("Valor");
		grid.getModeloTabela().addColumn("Final");
		
		grid.getModeloColuna().getColumn(0).setPreferredWidth(50);
		grid.getModeloColuna().getColumn(1).setPreferredWidth(100);
		grid.getModeloColuna().getColumn(2).setPreferredWidth(70);
		grid.getModeloColuna().getColumn(3).setPreferredWidth(70);
		grid.getModeloColuna().getColumn(4).setPreferredWidth(70);
		
		grid.getModeloColuna().setCampo(0, "data");
		grid.getModeloColuna().setFormato(0, "dd/MM/yy");
		grid.getModeloColuna().setCampo(1, "natureza.nome");
		grid.getModeloColuna().setCampo(2, "saldoInicial");
		grid.getModeloColuna().setFormato(2, Formato.MOEDA);
		grid.getModeloColuna().setCampo(3, "valor");
		grid.getModeloColuna().setFormato(3, Formato.MOEDA);
		grid.getModeloColuna().setCampo(4, "saldoFinal");
		grid.getModeloColuna().setFormato(4, Formato.MOEDA);
		grid.getModeloColuna().definirPositivoNegativo(3);
		cmdAtualizar.setText("Atualizar");
		cmdFechar.setText("Fechar");
		
		
		GridBagConstraints gbc_cboSaldo= new GridBagConstraints();
		gbc_cboSaldo.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboSaldo.insets = new Insets(5, 5, 5, 0);
		gbc_cboSaldo.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboSaldo.gridx = 0;
		gbc_cboSaldo.gridy = 0;
		
		painelFiltro.add(cboSaldo, gbc_cboSaldo);
		GridBagConstraints gbc_txtSaldoInicial = new GridBagConstraints();
		gbc_txtSaldoInicial.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtSaldoInicial.insets = new Insets(5, 5, 5, 0);
		gbc_txtSaldoInicial.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSaldoInicial.gridx = 1;
		gbc_txtSaldoInicial.gridy = 0;
		
		painelFiltro.add(txtSaldoInicial, gbc_txtSaldoInicial);
		
		GridBagConstraints gbc_txtConta = new GridBagConstraints();
		gbc_txtConta.weightx = 1.0;
		gbc_txtConta.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtConta.insets = new Insets(5, 5, 5, 5);
		gbc_txtConta.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtConta.gridx = 2;
		gbc_txtConta.gridy = 0;
		txtConta.setComponenteNegrito(true);
		
		txtConta.setEditavel(false);
		txtConta.setColunas(8);
		txtConta.setRotulo("Conta");
		painelFiltro.add(txtConta, gbc_txtConta);
		
		GridBagConstraints gbc_txtDataAte = new GridBagConstraints();
		gbc_txtDataAte.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDataAte.insets = new Insets(5, 5, 5, 5);
		gbc_txtDataAte.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDataAte.gridx = 3;
		gbc_txtDataAte.gridy = 0;
		txtSaldoFinal.setComponenteNegrito(true);
		txtSaldoFinal.setEditavel(false);
		txtSaldoFinal.setComponenteCorFonte(Color.BLUE);
		txtSaldoFinal.setColunas(6);
		txtSaldoFinal.setRotulo("Saldo Atual");
		txtSaldoFinal.setVisible(false);
		txtSaldoFinal.setFormato(Formato.MOEDA);
		
		painelFiltro.add(txtSaldoFinal, gbc_txtDataAte);

		cmdFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		cmdAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});
		/*cboSaldo.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		       mostrarValor();
		    }
		});*/
		txtConta.setComponenteCorFonte(Color.BLUE);
		txtSaldoInicial.setComponenteCorFonte(Color.BLUE);
		txtReceitas.setComponenteCorFonte(Color.BLUE);
		txtDespesas.setComponenteCorFonte(Color.RED);
		
		
	}
	private void mostrarValor() {
		saldo = (Saldo) cboSaldo.getValue();
		txtSaldoInicial.setValue(0.0d);
		if(saldo!=null) {
			txtSaldoInicial.setValue(saldo.getValor());
		}
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	@Override
	public void load() {
		saldos = dao.listarSaldos(conta.getId());
		cboSaldo.setItens(saldos,"dataFormatada");
		cboSaldo.setValue( saldos.get(0));
		listar();
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
	private void listar() {
		if(conta!=null) {
			mostrarValor();
			//saldo = saldos.get(0);
			cboSaldo.setValue(saldo);
			txtSaldoInicial.setValue(saldo.getValor());
			txtConta.setText(conta.getNome());
			txtSaldoFinal.setValue(conta.getSaldo());
			
			List<Lancamento> lista = new ArrayList<Lancamento>();
			try {
				Saldo saldo = (Saldo) cboSaldo.getValue();
				Date ini = new Date();
				Date fim = new Date();
				if(saldo!=null)
					ini = saldo.getData();
				lista = lactoDao.listarContaLancamentos(MDI.getPerfilId(), ini, fim,conta.getId());
				//if(lista.size()==0)
					//SSMensagem.avisa("Nenhum dado encontrado");
				
				total = lactoDao.totais(lista,true);
				txtSaldoAtual.setValue(total.getSaldo());
				txtSaldoAtual.setComponenteCorFonte(total.getSaldo() < 0.0d ? Color.RED: Color.BLUE);
				txtSaldoFinal.setComponenteCorFonte(total.getSaldo() < 0.0d ? Color.RED: Color.BLUE);
				txtDespesas.setValue(total.getDebito());
				txtReceitas.setValue(total.getCredito());
				grid.setValue(lista);
			} catch (Exception e) {
				e.printStackTrace();
				SSMensagem.erro(e.getMessage());
			}
			//conta = dao.buscar(Conta.class, conta.getId());
		}
	}
	
}
