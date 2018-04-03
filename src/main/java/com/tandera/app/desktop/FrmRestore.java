package com.tandera.app.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tandera.app.spring.SpringDesktopApp;

import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.Unzip;
import edu.porgamdor.util.desktop.ss.PosicaoRotulo;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmRestore extends Formulario {
	private static Logger LOG = Logger.getLogger(FrmRestore.class.getName());
	private SSBotao cmdAbrir = new SSBotao();
	private JFileChooser chooserOrigem;
	private SSCampoTexto txtDestino = new SSCampoTexto();
	private JLabel lblAviso = new JLabel("O arquivo *.zip tem a pasta database que será extraída no destino");

	public FrmRestore() {
		lblAviso.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAviso.setForeground(Color.BLUE);
		init();
	}

	private void init() {
		super.setTitulo("Restore");
		super.setDescricao("Realiza a restauração da base de dados");
		getRodape().add(lblAviso);
		JPanel conteudo = super.getConteudo();
		
		setSize(new Dimension(483, 329));
		cmdAbrir.setText("");
		cmdAbrir.setIcone("pasta");
		
		
		
		chooserOrigem = new JFileChooser("/cfip/backup");
		chooserOrigem.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Origem (arquivo .zip)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		chooserOrigem.setDialogTitle("Restore");
		chooserOrigem.setApproveButtonText("Restore");
		chooserOrigem.setPreferredSize(new Dimension(500, 280));
		chooserOrigem.setFileFilter(new FileNameExtensionFilter(null, "zip"));
		
		
		conteudo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_txtDestino = new GridBagConstraints();
		gbc_txtDestino.weightx = 1.0;
		gbc_txtDestino.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDestino.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDestino.insets = new Insets(5, 10, 0, 0);
		gbc_txtDestino.gridx = 0;
		gbc_txtDestino.gridy = 1;
		txtDestino.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		txtDestino.setEditavel(false);
		txtDestino.setComponenteCorFonte(new Color(178, 34, 34));
		txtDestino.setComponenteNegrito(true);
		txtDestino.setForeground(Color.BLUE);
		conteudo.add(txtDestino, gbc_txtDestino);

		GridBagConstraints gbc_cmdAbrir = new GridBagConstraints();
		gbc_cmdAbrir.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmdAbrir.anchor = GridBagConstraints.NORTHWEST;
		gbc_cmdAbrir.insets = new Insets(5, 0, 0, 5);
		gbc_cmdAbrir.gridx = 1;
		gbc_cmdAbrir.gridy = 1;
		conteudo.add(cmdAbrir, gbc_cmdAbrir);
		cmdAbrir.setMargin(new Insets(0, 0, 0, 0));

		GridBagConstraints gbc_chooserOrigem = new GridBagConstraints();
		gbc_chooserOrigem.gridwidth = 2;
		gbc_chooserOrigem.weighty = 1.0;
		gbc_chooserOrigem.weightx = 1.0;
		gbc_chooserOrigem.fill = GridBagConstraints.HORIZONTAL;
		gbc_chooserOrigem.anchor = GridBagConstraints.NORTHWEST;
		gbc_chooserOrigem.insets = new Insets(5, 5, 0, 5);
		gbc_chooserOrigem.gridx = 0;
		gbc_chooserOrigem.gridy = 0;
		conteudo.add(chooserOrigem, gbc_chooserOrigem);

		txtDestino.setRotulo("Destino da Base de Dados: ");
		// txtDiretorio.requestFocus();
		txtDestino.setTudoMaiusculo(false);
		

		cmdAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrir();
			}
		});

		chooserOrigem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				execute(evt);
			}
		});
		txtDestino.setText(new File("/cfip/").getAbsolutePath());
	}

	private void abrir() {
		JFileChooser chooser = new JFileChooser("/cfip/");
		chooser.setBorder(new TitledBorder(null, "Origem", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		chooser.setDialogTitle("Origem do Arquivo");
		chooser.setApproveButtonText("Selecionar");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = chooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File dir = chooser.getSelectedFile();
			txtDestino.setText(dir.getAbsolutePath());
		}
	}

	private void execute(ActionEvent e) {
		try {
			if (e.getActionCommand().equals(javax.swing.JFileChooser.APPROVE_SELECTION)) {
				if (SSMensagem.pergunta("Esta operação encerra a aplicação\nDeseja prosseguir?")) {
					SpringDesktopApp.closeContext();
					File origem = chooserOrigem.getSelectedFile();
					File destino = new File(txtDestino.getText());
					if (!destino.exists()) {
						SSMensagem.avisa("Diretório da base de dados inválido");
						return;
					}
					Unzip unZip = new Unzip();
					unZip.unZipIt(origem.getAbsolutePath(), destino.getAbsolutePath());
					SSMensagem.informa("Restore realizado com sucesso\nAcesse o sistema");
					System.exit(0);
				}
			} else {
				fechar();
			}

		} catch (Exception ex) {
			SSMensagem.avisa("Erro ao tentar realizar o backup");
			LOG.error("Erro ao tentar realizar o backup --> " + ex.getMessage());
		}
	}

}
