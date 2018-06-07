package com.tandera.app.desktop.cadastro;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tandera.core.dao.springjpa.PessoaRepository;
import com.tandera.core.model.cadastro.Pessoa;

import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
public class FrmPessoa extends Formulario {

	@Autowired
	private PessoaRepository dao;

	private Pessoa entidade;

	private SSCampoTexto txtNome = new SSCampoTexto();
	private SSCampoTexto txtCpf = new SSCampoTexto();
	private SSCampoTexto txtEndereco = new SSCampoTexto();
	private SSCampoNumero txtNumero = new SSCampoNumero();
	private SSCampoTexto txtComplemento = new SSCampoTexto();
	private SSCampoTexto txtBairro = new SSCampoTexto();
	private SSCampoTexto txtCidade = new SSCampoTexto();
	private SSCampoTexto txtEstado = new SSCampoTexto();
	private SSCampoTexto txtCep = new SSCampoTexto();
	private SSCampoTexto txtDadosBancarios = new SSCampoTexto();
	private SSCampoTexto txtTelefone = new SSCampoTexto();
	private JCheckBox txtFornecedor = new JCheckBox("Fornecedor");
	private JCheckBox txtCliente = new JCheckBox("Cliente");
	private JCheckBox txtFuncionario = new JCheckBox("Funcionário");

	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private JCheckBox chkNovo = new JCheckBox("Novo?");

	public FrmPessoa() {
		setPreferredSize(new Dimension(570, 434));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0 };
		gridBagLayout.rowHeights = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] {};
		gridBagLayout.rowWeights = new double[] {};
		getConteudo().setLayout(gridBagLayout);
		init();
	}

	private void init() {
		super.setTitulo("Pessoa");
		super.setDescricao("Cadastro de Pessoa");
		super.getRodape().add(chkNovo);
		super.getRodape().add(cmdSalvar);
		super.getRodape().add(cmdSair);
		super.getConteudo().setPreferredSize(new Dimension(570, 400));
		// IMPORTANTE
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelCampos.setLayout(null);
		
		txtNome.setBounds(10, 11, 382, 50);
		panelCampos.add(txtNome);
		txtNome.setColunas(10);
		txtNome.setRotulo("Nome");
		
		txtCpf.setBounds(402, 11, 158, 50);
		txtCpf.setRotulo("CPF");
		txtCpf.setColunas(10);
		panelCampos.add(txtCpf);
		
		txtEndereco.setBounds(10, 72, 461, 50);
		txtEndereco.setRotulo("Endereço");
		txtEndereco.setColunas(10);
		panelCampos.add(txtEndereco);
		
		txtNumero.setBounds(481, 72, 79, 50);
		txtNumero.setRotulo("Número");
		txtNumero.setColunas(10);
		panelCampos.add(txtNumero);
		
		txtComplemento.setBounds(10, 133, 382, 50);
		txtComplemento.setRotulo("Complemento");
		txtComplemento.setColunas(10);
		panelCampos.add(txtComplemento);
		
		txtBairro.setBounds(402, 133, 158, 50);
		txtBairro.setRotulo("Bairro");
		txtBairro.setColunas(10);
		panelCampos.add(txtBairro);
		
		txtCidade.setBounds(10, 194, 300, 50);
		txtCidade.setRotulo("Cidade");
		txtCidade.setColunas(10);
		panelCampos.add(txtCidade);
		
		txtEstado.setBounds(320, 194, 72, 50);
		txtEstado.setRotulo("Estado");
		txtEstado.setColunas(10);
		panelCampos.add(txtEstado);
		
		txtCep.setBounds(402, 194, 158, 50);
		txtCep.setRotulo("CEP");
		txtCep.setColunas(10);
		panelCampos.add(txtCep);
		
		txtDadosBancarios.setBounds(10, 255, 300, 50);
		txtDadosBancarios.setRotulo("Dados Bancários");
		txtDadosBancarios.setColunas(10);
		panelCampos.add(txtDadosBancarios);
		
		txtTelefone.setBounds(320, 255, 240, 50);
		txtTelefone.setRotulo("Telefone");
		txtTelefone.setColunas(10);
		panelCampos.add(txtTelefone);
		
		txtFornecedor.setBounds(10, 312, 87, 23);
		panelCampos.add(txtFornecedor);
		
		txtFuncionario.setBounds(99, 312, 87, 23);
		panelCampos.add(txtFuncionario);
		
		txtCliente.setBounds(188, 312, 87, 23);
		panelCampos.add(txtCliente);

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
		this.entidade = (Pessoa) entidade;
		if (entidade != null)
			atribuir();
		else
			criar();
	}

	private void atribuir() {
		try {
			txtNome.setValue(entidade.getNome());
			txtCpf.setValue(entidade.getCpf());
			txtEndereco.setValue(entidade.getEndereco());
			txtNumero.setValue(entidade.getNumero());
			txtComplemento.setValue(entidade.getComplemento());
			txtBairro.setValue(entidade.getBairro());
			txtCidade.setValue(entidade.getCidade());
			txtEstado.setValue(entidade.getEstado());
			txtCep.setValue(entidade.getCep());
			txtDadosBancarios.setValue(entidade.getDados_bancarios());
			txtTelefone.setValue(entidade.getTelefone());
			txtCliente.setSelected(entidade.getCliente());
			txtFornecedor.setSelected(entidade.getFornecedor());
			txtFuncionario.setSelected(entidade.getFuncionario());
			txtNome.requestFocus();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void criar() {
		entidade = new Pessoa();
		atribuir();
	}

	private void salvar() {
		try {
			entidade.setNome(txtNome.getText());
			entidade.setCpf(txtCpf.getText());
			entidade.setEndereco(txtEndereco.getText());
			entidade.setNumero(txtNumero.getInteger());
			entidade.setComplemento(txtComplemento.getText());
			entidade.setBairro(txtBairro.getText());
			entidade.setCidade(txtCidade.getText());
			entidade.setEstado(txtEstado.getText());
			entidade.setCep(txtCep.getText());
			entidade.setDados_bancarios(txtDadosBancarios.getText());
			entidade.setTelefone(txtTelefone.getText());
			entidade.setCliente(txtCliente.isSelected());
			entidade.setFornecedor(txtFornecedor.isSelected());
			entidade.setFuncionario(txtFuncionario.isSelected());
			// entidade.setValor(BigDecimal.valueOf(txtValor.getDouble()));

			if (entidade.getNome() == null || entidade.getNome().isEmpty() || entidade.getCpf() == null
					|| entidade.getCpf().isEmpty() || entidade.getEndereco() == null
					|| entidade.getEndereco().isEmpty() || entidade.getDados_bancarios() == null 
					|| entidade.getDados_bancarios().isEmpty() || entidade.getTelefone() == null 
					|| entidade.getTelefone().isEmpty()) {
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
