package com.tandera.app.desktop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tandera.core.dao.Repositorio;
import com.tandera.core.model.Conta;
import com.tandera.core.model.DespesaRapida;
import com.tandera.core.model.Natureza;
import com.tandera.core.model.TipoMovimento;

import edu.porgamdor.util.desktop.Formato;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ambiente.TipoOperacao;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCaixaCombinacao;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmDespesaRapida extends Formulario {
	private SSCaixaCombinacao cboConta = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboNatureza = new SSCaixaCombinacao();
	private final SSCampoNumero txtValor = new SSCampoNumero();
	private final SSCampoNumero txtOrdem = new SSCampoNumero();

	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private DespesaRapida entidade;
	private TipoOperacao operacao;
	@Autowired
	private Repositorio dao;
	private JCheckBox chkNovo = new JCheckBox("Novo?");
	public FrmDespesaRapida() {
		init();
	}
	private void init() {
		super.setTitulo("Cadastro de Despesa Rapidas");
		super.setDescricao("Registro de lançamentos comuns");
		getRodape().add(chkNovo);
		getRodape().add(cmdSalvar);
		getRodape().add(cmdSair);
		// IMPORTANTE
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		panelCampos.setLayout(gbl_panelCampos);

		GridBagConstraints gbcTxtCodigo = new GridBagConstraints();
		gbcTxtCodigo.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtCodigo.anchor = GridBagConstraints.WEST;
		gbcTxtCodigo.insets = new Insets(5, 5, 0, 5);
		gbcTxtCodigo.gridx = 0;
		gbcTxtCodigo.gridy = 0;
		panelCampos.add(cboConta, gbcTxtCodigo);
		
		GridBagConstraints gbcTxtNome = new GridBagConstraints();
		gbcTxtNome.weightx = 2.0;
		gbcTxtNome.insets = new Insets(5, 5, 5, 5);
		gbcTxtNome.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtNome.gridx = 0;
		gbcTxtNome.gridy = 1;
		panelCampos.add(cboNatureza, gbcTxtNome);

		cboConta.setRotulo("Conta");
		cboNatureza.setRotulo("Natureza");

		GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
		gbc_txtDescricao.insets = new Insets(5, 5, 0, 5);
		gbc_txtDescricao.fill = GridBagConstraints.BOTH;
		gbc_txtDescricao.gridx = 0;
		gbc_txtDescricao.gridy = 2;
		txtValor.setRotulo("Valor");
		txtValor.setFormato(Formato.MOEDA);
		panelCampos.add(txtValor, gbc_txtDescricao);

		GridBagConstraints gbc_cboTipoMovto = new GridBagConstraints();
		gbc_cboTipoMovto.weighty = 1.0;
		gbc_cboTipoMovto.insets = new Insets(5, 5, 5, 5);
		gbc_cboTipoMovto.fill = GridBagConstraints.BOTH;
		gbc_cboTipoMovto.gridx = 0;
		gbc_cboTipoMovto.gridy = 3;
		txtOrdem.setRotulo("Ordem");
		panelCampos.add(txtOrdem, gbc_cboTipoMovto);
		cmdSair.setText("Fechar");
		cmdSalvar.setText("Salvar");

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
		txtValor.setValue(0.0d);

	}
	public void setEntidade(Object entidade) {
		this.entidade = (DespesaRapida) entidade;
		if (entidade != null)
			operacao = TipoOperacao.ALTERACAO;
		else
			criar();
		
		atribuir();
		
	}
	private void atribuir() {
		try {
			cboConta.setValue(entidade.getConta());
			cboNatureza.setValue(entidade.getNatureza());
			txtValor.setValue(entidade.getValor());
			txtOrdem.setValue(entidade.getOrdem());
			cboConta.requestFocus();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void criar() {
		operacao = TipoOperacao.INCLUSAO;
		entidade = new DespesaRapida();
		atribuir();
	}
	private void salvar() {
		try {
			entidade.setNatureza((Natureza) cboNatureza.getValue());
			entidade.setConta((Conta) cboConta.getValue());
			entidade.setValor(txtValor.getDouble());
			entidade.setOrdem(txtOrdem.getInteger());
			entidade.setUsuario(MDI.getPerfilId());
			dao.gravar(operacao, entidade);
			SSMensagem.informa("Despesa Rápida registrado com sucesso!!");
			novo();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void novo() {
		if(chkNovo.isSelected()) {
			criar();
		}else
			super.fechar();
	}
	private void sair() {
		super.cancelar();
	}
	//quando quiser executar uma ação ao abrir o formulario
	public void load() {
		List<Conta> contas = dao.listarContas(MDI.getPerfilId());
		cboConta.setItens(contas,"nome");
		cboNatureza.setItens(dao.listarNaturezas(MDI.getPerfilId(),TipoMovimento.DEBITO),"nome");
	}
}
