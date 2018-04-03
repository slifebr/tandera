package com.tandera.app.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.Zip;
import edu.porgamdor.util.desktop.ss.PosicaoRotulo;
import edu.porgamdor.util.desktop.ss.SSBotao;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.util.Formatador;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmBackup extends Formulario {
	// http://www.java2s.com/Tutorial/Java/0240__Swing/ChangingtheTextoftheApproveButtoninaJFileChooserDialog.htm
	// https://stackoverflow.com/questions/17034282/jfilechooser-regarding-the-open-and-cancel-buttons-java
	private static Logger LOG = Logger.getLogger(FrmBackup.class.getName());
	private SSBotao cmdAbrir = new SSBotao();
	private JFileChooser chooser;
	private SSCampoTexto txtOrigem = new SSCampoTexto();
	private JLabel lblAviso = new JLabel("Após a operação, consulte seu e-mail");

	public FrmBackup() {
		lblAviso.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAviso.setForeground(Color.BLUE);
		init();
	}

	private void init() {
		super.setTitulo("Backup");
		super.setDescricao("Realiza o backup da base de dados");
		getRodape().add(lblAviso);
		JPanel conteudo = super.getConteudo();
		
		setSize(new Dimension(483, 329));
		cmdAbrir.setText("");
		cmdAbrir.setIcone("pasta");
		chooser = new JFileChooser("/cfip/backup");
		chooser.setBorder(new TitledBorder(null, "Destino", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		chooser.setDialogTitle("Backup");
		chooser.setApproveButtonText("Backup");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setPreferredSize(new Dimension(500, 280));

		conteudo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbc_txtDiretorio = new GridBagConstraints();
		gbc_txtDiretorio.weightx = 1.0;
		gbc_txtDiretorio.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDiretorio.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDiretorio.insets = new Insets(5, 10, 0, 0);
		gbc_txtDiretorio.gridx = 0;
		gbc_txtDiretorio.gridy = 0;
		txtOrigem.setRotuloPosicao(PosicaoRotulo.ESQUERDA);
		txtOrigem.setEditavel(false);
		txtOrigem.setComponenteCorFonte(new Color(178, 34, 34));
		txtOrigem.setComponenteNegrito(true);
		txtOrigem.setForeground(Color.BLUE);
		conteudo.add(txtOrigem, gbc_txtDiretorio);

		GridBagConstraints gbc_cmdAbrir = new GridBagConstraints();
		gbc_cmdAbrir.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmdAbrir.anchor = GridBagConstraints.NORTHWEST;
		gbc_cmdAbrir.insets = new Insets(5, 0, 0, 5);
		gbc_cmdAbrir.gridx = 1;
		gbc_cmdAbrir.gridy = 0;
		conteudo.add(cmdAbrir, gbc_cmdAbrir);
		cmdAbrir.setMargin(new Insets(0, 0, 0, 0));

		GridBagConstraints gbc_txtChooser = new GridBagConstraints();
		gbc_txtChooser.gridwidth = 2;
		gbc_txtChooser.weighty = 1.0;
		gbc_txtChooser.weightx = 1.0;
		gbc_txtChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtChooser.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtChooser.insets = new Insets(5, 5, 0, 5);
		gbc_txtChooser.gridx = 0;
		gbc_txtChooser.gridy = 1;
		conteudo.add(chooser, gbc_txtChooser);

		txtOrigem.setRotulo("Origem da base");
		// txtDiretorio.requestFocus();
		txtOrigem.setTudoMaiusculo(false);
		

		cmdAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrir();
			}
		});

		chooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				execute(evt);
			}
		});
		txtOrigem.setText(new File("/cfip/database").getAbsolutePath());
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
			txtOrigem.setText(dir.getAbsolutePath());
		}
	}

	private void execute(ActionEvent e) {
		try {
			if (e.getActionCommand().equals(javax.swing.JFileChooser.APPROVE_SELECTION)) {
				if(!MDI.getAmbiente().equals("LOCAL")) {
					SSMensagem.avisa("O banco de dados não é LOCAL");
					return;
				}
				if (SSMensagem.pergunta("Confirmar fazer o backup")) {

					File destino = chooser.getSelectedFile();
					String time = Formatador.formatar(new Date(), "yyyyMMdd-HHmmss");
					String zipName = "backup_" + time + ".zip";
					File src = new File(txtOrigem.getText());
					if (!src.exists()) {
						SSMensagem.avisa("Diretório da base de dados inválido");
						return;
					}
					Zip.execute(src, destino.getAbsolutePath(), zipName);
					/*Email email = new Email();
					email.setSubject("CFIP Backup - " + time);
					LOG.info("Enviando e-mail para o usuario: " + getUsuario().getEmail());
					email.setEmails(getUsuario().getEmail());
					email.setAttach(destino.getAbsolutePath(), zipName);
					email.send();
					*/
					SSMensagem.informa("Backup realizado com sucesso!!");
					fechar();
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
