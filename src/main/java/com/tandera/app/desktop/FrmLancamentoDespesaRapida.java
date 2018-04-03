package com.tandera.app.desktop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tandera.core.dao.Repositorio;
import com.tandera.core.dao.RepositorioLancamento;
import com.tandera.core.model.DespesaRapida;
import com.tandera.core.model.Lancamento;

import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.util.Imagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmLancamentoDespesaRapida extends Formulario {
	private List<DespesaRapida> lista;
	@Autowired
	private Repositorio dao;
	@Autowired
	private RepositorioLancamento lanctoDao;
	private JPanel panelCampos;

	public FrmLancamentoDespesaRapida() {
		init();
	}

	private void init() {
		// HERANÇA
		super.setTitulo("Despesa Rápida");
		super.setDescricao("Despesas Diárias");
		// IMPORTANTE
		panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		panelCampos.setLayout(gbl_panelCampos);
		getRodape().setVisible(false);
	}

	private void salvar(String id) {
		try {
			if (id != null) {
				if (SSMensagem.pergunta("Lançar Despesa Rápida")) {
					DespesaRapida item = lista.get(Integer.valueOf(id));
					Lancamento entidade = new Lancamento();
					entidade.setValor(item.getValor());
					entidade.setDescricao("QUICK :: " + item.getNatureza().getNome());
					entidade.setConta(item.getConta());
					entidade.setData(new Date());
					entidade.setNatureza(item.getNatureza());
					entidade.setTipoMovimento(item.getNatureza().getTipoMovimento());
					entidade.setUsuario(MDI.getPerfilId());
					lanctoDao.incluirLancamento(entidade);
					SSMensagem.informa("Despesa rápida registrada com sucesso!!");
					super.fechar();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void sair() {
		super.cancelar();
	}
	public void load() {
		lista = dao.listarDespesasRapidas(MDI.getPerfilId());
		int y = 0;
		for (DespesaRapida d : lista) {
			SSBotao cmd = new SSBotao();
			String label = String.format("%s - %.2f", d.getNatureza().getNome(), d.getValor());
			cmd.setText(label);
			cmd.setIcon(Imagem.png("cfip","despesarapida"));
			cmd.setMargin(new Insets(10, 10, 10, 10));
			cmd.setName("" + y);
			cmd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					SSBotao botao = (SSBotao) event.getSource();
					salvar(botao.getName());
				}
			});
			panelCampos.add(cmd, gbc(y));
			y++;
		}
		SSBotao cmd = new SSBotao();
		cmd.setText("Cancelar");
		cmd.setMargin(new Insets(10, 10, 10, 10));
		cmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sair();
			}
		});
		panelCampos.add(cmd, gbc(y));
	}
	private GridBagConstraints gbc(int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.insets = new Insets(2, 2, 2, 2);
		return gbc;
	}
}
