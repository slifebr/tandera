package com.tandera.app.desktop;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.stereotype.Component;

import com.tandera.app.desktop.cadastro.FrmPessoas;
import com.tandera.app.desktop.comercial.FrmCategorias;
import com.tandera.app.desktop.comercial.FrmEstados;
import com.tandera.app.desktop.comercial.FrmMarcas;
import com.tandera.app.desktop.comercial.FrmMarkups;
import com.tandera.app.desktop.comercial.FrmMascaraPrecos;
import com.tandera.app.desktop.comercial.FrmTamanhos;
import com.tandera.app.desktop.comercial.FrmTipos;
import com.tandera.app.desktop.orcamento.FrmCompras;
import com.tandera.app.spring.SpringDesktopApp;
import com.tandera.core.model.Usuario;

import edu.porgamdor.util.desktop.DesktopApp;
import edu.porgamdor.util.desktop.Formulario;
import edu.porgamdor.util.desktop.MDI;
import edu.porgamdor.util.desktop.ss.SSMensagem;
import edu.porgamdor.util.desktop.ss.util.Imagem;

@Component
public class MDICfip extends MDI {
	// private AnnotationConfigApplicationContext context;
	public MDICfip() {
		setTitle("TANDERA - Gestão Comercial");
	//	setIconImage(Imagem.icone("cfip","aplicacao","png"));
		JMenu mnCadastros = new JMenu("Cadastros");
		mnCadastros.setIcon(Imagem.png("0cadastros"));
		JMenuItem mnConta = new JMenuItem("Conta");
		mnConta.setIcon(Imagem.png("cfip", "conta"));
		mnConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirConta();
				// exibirBean("frmContas");
			}
		});
		// mnCadastros.add(mnConta);

		JMenuItem mnNatureza = new JMenuItem("Natureza");
		mnNatureza.setIcon(Imagem.png("cfip", "natureza"));
		mnNatureza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirNatureza();
			}
		});

		// mnCadastros.add(mnNatureza);

		JMenuItem mnDespesasRapidas = new JMenuItem("Despesa Rápida");
		mnDespesasRapidas.setIcon(Imagem.png("cfip", "despesarapida"));
		mnDespesasRapidas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirDespesasRapidas();
			}
		});
		// mnCadastros.add(mnDespesasRapidas);

		JMenuItem mntmContato = new JMenuItem("Contato");
		mntmContato.setIcon(Imagem.png("cfip", "contato"));
		mntmContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirContatos();
			}
		});

		JMenuItem mntmFatura = new JMenuItem("Fatura");
		mntmFatura.setIcon(Imagem.png("cfip", "fatura"));
		mntmFatura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// exibirFatura();
			}
		});
		// mnCadastros.add(mntmFatura);
		// mnCadastros.add(mntmContato);

		JMenuItem mnPessoa = new JMenuItem("Cadastro de Pessoas");
		mnPessoa.setIcon(Imagem.png("cfip", "despesarapida"));
		mnPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				showFormulario(FrmPessoas.class);
			}
		});

		mnCadastros.add(mnPessoa);

		// menu comercial
		JMenu mnComercial = new JMenu("Comercial");
		mnComercial.setIcon(Imagem.png("1lancamentos"));

		JMenuItem mnTamanho = new JMenuItem("Tamanhos");
		mnTamanho.setIcon(Imagem.png("cfip", "despesarapida"));
		mnTamanho.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirTamanhos();
			}
		});

		JMenuItem mnEstado = new JMenuItem("Estados");
		mnEstado.setIcon(Imagem.png("cfip", "despesarapida"));
		mnEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				showFormulario(FrmEstados.class);
			}
		});

		JMenuItem mnMarkup = new JMenuItem("Markup");
		mnMarkup.setIcon(Imagem.png("cfip", "despesarapida"));
		mnMarkup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				showFormulario(FrmMarkups.class);
			}
		});

		JMenuItem mnTipo = new JMenuItem("Tipo");
		mnTipo.setIcon(Imagem.png("cfip", "despesarapida"));
		mnTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				showFormulario(FrmTipos.class);
			}
		});

		JMenuItem mnMascaraPreco = new JMenuItem("Mascara Preço");
		mnMascaraPreco.setIcon(Imagem.png("cfip", "despesarapida"));
		mnMascaraPreco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				showFormulario(FrmMascaraPrecos.class);
			}
		});

		JMenuItem mnMarca = new JMenuItem("Marca");
		mnMarca.setIcon(Imagem.png("cfip", "despesarapida"));
		mnMarca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				showFormulario(FrmMarcas.class);
			}
		});
		
		JMenuItem mnCategoria = new JMenuItem("Categoria");
		mnCategoria.setIcon(Imagem.png("cfip", "despesarapida"));
		mnCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				showFormulario(FrmCategorias.class);
			}
		});

		mnComercial.add(mnTamanho);
		mnComercial.add(mnEstado);
		mnComercial.add(mnMarkup);
		mnComercial.add(mnTipo);
		mnComercial.add(mnMascaraPreco);
		mnComercial.add(mnMarca);
		mnComercial.add(mnCategoria);
		// Fim menu Comercial

		// menu Orçamento
		JMenu mnOrcamento = new JMenu("Orçamento");
		mnOrcamento.setIcon(Imagem.png("1lancamentos"));

		JMenuItem mnCompra = new JMenuItem("Compra");
		mnCompra.setIcon(Imagem.png("cfip", "despesarapida"));
		mnCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				showFormulario(FrmCompras.class);
			}
		});
		
		mnOrcamento.add(mnCompra);
		// Fim menu Oçamento

		JMenu mnLancamentos = new JMenu("Lançamentos");
		mnLancamentos.setIcon(Imagem.png("1lancamentos"));

		JMenuItem mnDespesaRapida = new JMenuItem("Despesa Rápida");
		mnDespesaRapida.setIcon(Imagem.png("cfip", "despesarapida"));
		mnDespesaRapida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirLactoDespesaRapida();
			}
		});
		mnLancamentos.add(mnDespesaRapida);

		JMenuItem mnReceitas = new JMenuItem("Receitas");
		mnReceitas.setIcon(Imagem.png("cfip", "receita"));
		mnLancamentos.add(mnReceitas);
		mnReceitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirLactoReceita();
			}
		});

		JMenuItem mnPrevisoes = new JMenuItem("Previsões");
		mnPrevisoes.setIcon(Imagem.png("cfip", "previsao"));
		mnPrevisoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirLanctoPrevisao();
			}
		});

		JMenuItem mnDespesas = new JMenuItem("Despesas");
		mnDespesas.setIcon(Imagem.png("cfip", "despesa"));
		mnLancamentos.add(mnDespesas);
		mnDespesas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirLanctoDespesa();
			}
		});
		JMenuItem mnTranferencias = new JMenuItem("Transferências");
		mnTranferencias.setIcon(Imagem.png("cfip", "transferencia"));
		mnTranferencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirTransferencia();
			}
		});
		mnLancamentos.add(mnTranferencias);
		mnLancamentos.add(mnPrevisoes);

		JMenuItem mnSaldos = new JMenuItem("Saldos");
		mnSaldos.setIcon(Imagem.png("cfip", "novociclo"));
		mnSaldos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirSaldo();
			}
		});
		mnLancamentos.add(mnSaldos);

		JMenuItem mnFaturas = new JMenuItem("Faturas");
		mnFaturas.setIcon(Imagem.png("cfip", "fatura"));
		mnFaturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// exibirFaturas();
			}
		});
		mnLancamentos.add(mnFaturas);

		JMenu mnConsultas = new JMenu("Consultas");
		mnConsultas.setIcon(Imagem.png("2consultas"));

		JMenuItem mnConsultaLancamentos = new JMenuItem("Lançamentos");
		mnConsultaLancamentos.setIcon(Imagem.png("dinheiro"));
		mnConsultaLancamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirConsultaLancamentos();
			}
		});
		mnConsultas.add(mnConsultaLancamentos);

		JMenuItem mnConsultaPrevisoes = new JMenuItem("Previsões");
		mnConsultaPrevisoes.setIcon(Imagem.png("calendario10"));
		mnConsultaPrevisoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirConsultaPrevisoes();
			}
		});

		mnConsultas.add(mnConsultaPrevisoes);

		JMenuItem mnProjecoes = new JMenuItem("Projeções");
		mnProjecoes.setIcon(Imagem.png("cfip", "projecao"));
		mnProjecoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirProjecoes();
			}
		});
		mnConsultas.add(mnProjecoes);

		JMenuItem mnMovimentacoes = new JMenuItem("Movimentações");
		mnMovimentacoes.setIcon(Imagem.png("cfip", "resumo"));
		mnMovimentacoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirMovimentacoes();
			}
		});
		mnConsultas.add(mnMovimentacoes);

		JMenu mnRelatorios = new JMenu("Relatórios");
		mnRelatorios.setIcon(Imagem.png("3relatorios"));

		JMenu mnAjuda = new JMenu("Ajuda");
		mnAjuda.setIcon(Imagem.png("5ajuda"));

		JMenu mnFerramentas = new JMenu("Ajustes");
		mnFerramentas.setIcon(Imagem.png("4ajustes"));

		JMenuItem mntmManual = new JMenuItem("Manual 1.0");
		mntmManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// exibirManual();
			}

		});
		mntmManual.setIcon(Imagem.png("informacao"));
		mnAjuda.add(mntmManual);

		JMenuItem mntmUsurio = new JMenuItem("Usuário");
		mntmUsurio.setIcon(Imagem.png("cardeit"));
		mntmUsurio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirUsuario();
			}
		});
		mnFerramentas.add(mntmUsurio);

		JSeparator separator_2 = new JSeparator();
		mnFerramentas.add(separator_2);

		JMenu mnConexoes = new JMenu("Conexões");
		mnFerramentas.add(mnConexoes);

		JMenuItem mntmConexo = new JMenuItem("Banco Dados");
		mnConexoes.add(mntmConexo);
		mnConexoes.setIcon(Imagem.png("conexao"));
		mntmConexo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exibirConfiguracao();
			}
		});
		mntmConexo.setIcon(Imagem.png("dbconexao"));

		JSeparator separator = new JSeparator();
		mnFerramentas.add(separator);

		JMenu mnBancoDados = new JMenu("Banco Dados");
		mnBancoDados.setIcon(Imagem.png("dbajuste"));
		mnFerramentas.add(mnBancoDados);

		JMenuItem mnBackup = new JMenuItem("Backup");
		mnBancoDados.add(mnBackup);
		mnBackup.setIcon(Imagem.png("backup"));

		JMenuItem mnRestaurar = new JMenuItem("Restore");
		mnRestaurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirRestore();
			}
		});
		mnBancoDados.add(mnRestaurar);
		mnRestaurar.setIcon(Imagem.png("restaurar"));

		JSeparator separator_1 = new JSeparator();
		mnBancoDados.add(separator_1);

		JMenuItem mnSql = new JMenuItem("SQL");
		mnSql.setIcon(Imagem.png("executar"));
		mnSql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				sql();
			}
		});

		mnBancoDados.add(mnSql);
		mnBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirBackup();
			}
		});
		getBarraMenu().add(mnCadastros);
		getBarraMenu().add(mnComercial);
		getBarraMenu().add(mnOrcamento);
		getBarraMenu().add(mnLancamentos);
		getBarraMenu().add(mnConsultas);
		getBarraMenu().add(mnRelatorios);
		getBarraMenu().add(mnFerramentas);
		getBarraMenu().add(mnAjuda);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private void exibirSaldo() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmSaldos.class));
	}

	private void exibirDespesasRapidas() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmDespesasRapidas.class));
	}

	private void exibirConta() {
		// Formulario form = context.getBean(FrmContas.class);
		// exibir(form);
		exibir((Formulario) SpringDesktopApp.getBean(FrmContas.class));
	}

	private void exibirNatureza() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmNaturezas.class));
	}

	private void exibirContatos() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmContatos.class));
	}

	private void exibirTransferencia() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmLancamentoTransferencia.class));
	}

	private void exibirProjecoes() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmProjecoes.class));
	}

	private void exibirMovimentacoes() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmMovimentacoes.class));
	}

	private void exibirLactoDespesaRapida() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmLancamentoDespesaRapida.class));
	}

	private void exibirLactoReceita() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmLancamentoCredito.class));
	}

	private void exibirLanctoDespesa() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmLancamentoDebito.class));
	}

	private void exibirLanctoPrevisao() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmLancamentoPrevisao.class));
	}

	private void exibirConsultaLancamentos() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmLancamentos.class));
	}

	private void exibirConsultaPrevisoes() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmPrevisoes.class));
	}

	private void exibirBackup() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmBackup.class));
	}

	private void exibirRestore() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmRestore.class));
	}

	private void exibirUsuario() {
		Usuario perfil = (Usuario) MDI.getPerfil();
		FrmUsuario frm = SpringDesktopApp.getBean(FrmUsuario.class);
		FrmCfipLogin login = SpringDesktopApp.getBean(FrmCfipLogin.class);
		login.abrirCadastroPerfil(frm, perfil);
	}

	private void exibirConfiguracao() {
		DesktopApp.exibirConfiguracao();
	}

	private void exibirTamanhos() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmTamanhos.class));
	}

	private void exibirEstado() {
		exibir((Formulario) SpringDesktopApp.getBean(FrmTamanhos.class));
	}

	private void showFormulario(Class form) {
		exibir((Formulario) SpringDesktopApp.getBean(form));
	}

	private void sql() {
		// http://www.avajava.com/tutorials/lessons/how-do-i-run-another-application-from-java.html
		try {
			if (SSMensagem.pergunta("Esta operação encerra a aplicação\nDeseja prosseguir?")) {
				SpringDesktopApp.closeContext();
				DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:file:/cfip/database/cfipdb", "--user",
						"sa", "--password", "sa" });
				this.dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void exibir(Formulario formulario) {
		formulario.setMdi(this);
		formulario.load();
		formulario.exibir();
	}

}
