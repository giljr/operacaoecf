package gil.jr.ecf;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Gil Jr Data: 15/11/2009
 *   (TODO: implementar auto-scroll)
 *   
 * Aplicativo 'front-end' GUI, responsável por rodar 
 * as rotinas de leitura de todos os .TXT, transformá-los
 * em .XML e inserir informações pertinentes
 * no banco de dados
 * 
 */

public class LerArquivosTexto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton botaoCarregar = null;
	private JScrollPane jScrollPane = null;
	private JTextArea status = null;


	protected String path = "Administracao";	
	protected String dirTxt= "Sitafe";
	protected String dirLidos = "Lidos";
	protected String dirXml= "Xml";
	private static Logger informante = Logger.getLogger("AlimentadorDados");
	private static final int SAIDA_ANORMAL = -1;
	protected static final int SAIDA_NORMAL = 1;
	protected int codigoContribuinte;
	protected String[] arquivos_xml;
	protected String[] arquivos_txt;
	private static GerenteConexao gerenteConexao;

	/**constructor*/
	public LerArquivosTexto() {
		super();
		initialize();
	}

	private void initialize() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 438;
		int height = 200;
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		
		this.setSize(438, 200);
		this.setBounds(x, y, width, height);
		this.setContentPane(getJContentPane());
		this.setTitle("LeitorTxt v.0.1.GUI Data: 18/11/1009");
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getBotaoCarregar(), BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}
		
	private JButton getBotaoCarregar() {
		if (botaoCarregar == null) {

			gerenteConexao = new GerenteConexao();
			String logMsg = "GerenteConexao inicializado!";
			informante.log(Level.INFO, logMsg);

			botaoCarregar = new JButton();
			botaoCarregar.setText("Carregar");
			botaoCarregar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String logMsg1 = "Botão carregar acionado!";								
					informante.log(Level.INFO, logMsg1);
					status.append("Carregando arquivos do diretório: " + dirTxt +"\n");
					botaoCarregar.setEnabled(false);
					botaoCarregar.setText("Processando...Aguarde!!!");
					/**Vamos fazer algo que demanda longo tempo, então abrimos um
					thread extra; atualizaremos a interface somente quando 
					terminarmos a terefa [feita no 'dispatch thread event'].*/
					Thread trabalhador = new Thread() {
						@SuppressWarnings("unchecked")
						public void run() {							 
							/**busca todos arquivos txt do diretorio especificado*/
							status.append("buscando os arquvos .TXT...\n");
							arquivos_txt = GerenteArquivos.buscaTodosTxt(path, dirTxt);
							if (arquivos_txt != null) {
								for (int i = 0; i < arquivos_txt.length; i++) {
									GeradorXml.converter(arquivos_txt[i]);
									/*transfere arquivos .txt para diretorio Lidos*/
									String arquivoTxt = path + File.separator + dirTxt
									+ File.separator + arquivos_txt[i];
									String pathCompletoLidos = path + File.separator + dirLidos;
									boolean sucesso = GerenteArquivos.mudaDiretorio(new File(arquivoTxt), 
											pathCompletoLidos);
									if (sucesso) {
										status.append("Arquivos .TXT transformados em .XML com sucesso!\n");
										status.append("Transferido-os para "+ dirLidos+"...\n");
										String logMsg3 = "Sucesso ao transferir arquivos do diretorio: "
											+ dirTxt + " para: " + dirLidos + "!";							
										informante.log(Level.INFO, logMsg3);								
									}
								}
							} else {
								botaoCarregar.setText("ERRO!!!");
								status.append("ERRO!!!\n");
								status.append("Erro ao mudar arquivos do diretorio "
										+ dirTxt + " para " + dirLidos
										+ "\nSaindo do sistema...\n");
								status.append("Solucoes provaveis:\n > Confira se existe o diretório de destino: "
										+ dirLidos
										+ ";\n > Confira se existe arquivo para importar no diretório: "
										+ dirTxt
										+ "\n > Confira se o arquivo ja foi importado, consultando o diretorio: "
										+ dirLidos+"\n");
								String logMsg = "Erro ao mudar arquivos do diretorio "
									+ dirTxt + " para " + dirLidos
									+ "\nSaindo do sistema...";
								informante.log(Level.SEVERE, logMsg);
								String logMsg2 = "Solucoes provaveis:\n > Confira se existe o diretório de destino: "
									+ dirLidos
									+ ";\n > Confira se existe arquivo para importar no diretório: "
									+ dirTxt
									+ "\n > Confira se o arquivo ja foi importado, consultando o diretorio: "
									+ dirLidos;
								informante.log(Level.INFO, logMsg2);
								try {
									gerenteConexao.limpaRecursos();
									Thread.sleep(10000);
								} catch (InterruptedException e) {									
									e.printStackTrace();
								}
								
								System.exit(SAIDA_ANORMAL);
							}
							/**busca todos arquivos xml do diretorio especificado*/
							String[] arquivos_xml = GerenteArquivos.buscaTodosXml(path, dirXml);							
							
							/**armazena as ecfs para posterior insercao no banco de dados*/
							HashMap ecfs = new HashMap();

							/**armazena os contribuintes para posterior insercao no banco de dados*/
							List<Contribuinte> contribuintes = new ArrayList<Contribuinte>();

							/**lê cada arquivo .xml*/
							for (int i = 0; i < arquivos_xml.length; i++) {
								String pathCompleto = path + File.separator + dirXml
								+ File.separator;
								String arquivo_xml = arquivos_xml[i];
								RelatorioSitafe relatorioSitafe = LeitorXml
								.criaRelatorioSitafeDoXml(pathCompleto,
										arquivo_xml);

								Contribuinte contribuinte = new Contribuinte();

								contribuinte.setRazaoSocial(relatorioSitafe.getRs());
								contribuinte.setIe(relatorioSitafe.getIe());
								contribuinte.setCnpj(relatorioSitafe.getCnpj());
								contribuinte.setEnd(relatorioSitafe.getEnd());
								contribuinte.setMunicipio(relatorioSitafe.getMunicipio());
								contribuinte.setUf("RO");

								contribuintes.add(contribuinte);

								List<RelatorioSitafe.Equipamentos.Ecf> list = relatorioSitafe.
								equipamentos.getEcf();
								Iterator<RelatorioSitafe.Equipamentos.Ecf> iter = list
								.iterator();
								RelatorioSitafe.Equipamentos.Ecf ecfXml = null;
								try {
									while (iter.hasNext()) {
										int j = 0;
										Ecf ecf = new Ecf();
										ecfXml = iter.next();
										ecf.setMarca(ecfXml.getMarca());
										ecf.setModelo(ecfXml.getModelo());
										ecf.setNrAto(ecfXml.getNrato());
										ecf.setNrPedido(ecfXml.getNrpedido());
										ecf.setnrFab(ecfXml.getNrfab());
										ecf.setNrGerado(ecfXml.getNrusoCessacao());
										ecf.setNrVersao("");
										/*usa sal no hash map, para evitar conflitos*/
										long sal = Math.round(100000 * Math.random());
										ecfs.put(sal + "-" + relatorioSitafe.getCnpj(),
												ecf);
										j++;
									}
								} catch (Exception e1) {
									botaoCarregar.setText("ERRO!!!");
									status.append("ERRO !!!!\n");
									status.append("Falha na adicao de novo ecf! saindo...\n");
									String logMsg3 = "Falha na adicao de novo ecf! saindo...";							
									informante.log(Level.SEVERE, logMsg3);
									e1.printStackTrace();											
									System.exit(SAIDA_ANORMAL);
								}
							}
							
							/**deleta todos os arquivos .XML do diretorio especificado*/
							for(int i = 0; i<arquivos_xml.length; i++){
								String pathCompleto = path + File.separator + dirXml
								+ File.separator;
								String arquivo_xml = arquivos_xml[i];
								new File(pathCompleto,arquivo_xml).delete();								
							}

							ConsultasContribuinte cc = null;
							/**acessar banco de dados para inserir contribuintes*/
							status.append("Acessando  banco de dados...\n");
							status.append("Adicionando Contribuintes e ECfs...." +
									"\nOperação demorada...Favor aguardar...\n");
							for (int m = 0; m < contribuintes.size(); m++) {
								Contribuinte contribuinte = new Contribuinte();
								contribuinte = contribuintes.get(m);
								cc = new ConsultasContribuinte(gerenteConexao);
								try {									
									cc.adiciona(contribuinte.getRazaoSocial(), 
											contribuinte.getIe(),
											contribuinte.getCnpj(), 
											contribuinte.getEnd(),
											contribuinte.getMunicipio(), "RO");									
								} catch (SQLException e1) {
									botaoCarregar.setText("ERRO!!!");
									status.append("ERRO !!!!\n");
									status.append("Falha na adicao de novo contribuinte! saindo...\n");
									String logMsg3 = "Falha na adicao de novo contribuinte! saindo...\n";							
									informante.log(Level.SEVERE, logMsg3);
									e1.printStackTrace();											
								} 
							}

							Set set = ecfs.entrySet();
							Iterator iterator = set.iterator();
							for (int j = 0; iterator.hasNext(); j++) {
								Map.Entry me = (Map.Entry) iterator.next();
								Ecf ecf = new Ecf();
								/**separa o sal para resgatar o cnpj*/
								String  cnpjSal= me.getKey().toString();
								String cnpj = cnpjSal.substring(cnpjSal.indexOf("-")+1);				

								try {
									//status.append("Buscando codigo do contribuinte...\n");
									codigoContribuinte = cc.buscaPorCnpj(cnpj);

								} catch (SQLException e1) {
									botaoCarregar.setText("ERRO!!!");
									status.append("Erro na busca do Cnpjs!!!Saindo...\n");
									String logMsg3 = "Erro na busca do cnpjs!!!";								
									informante.log(Level.SEVERE, logMsg3);									
									System.exit(SAIDA_ANORMAL);
								} 

								try {
									ecf = ((Ecf)me.getValue());
									ConsultasEcf ce = new ConsultasEcf(gerenteConexao);
									int sucesso = ce.adiciona(ecf.getMarca(),
											ecf.getModelo(), ecf.getNrAto(), 
											ecf.getNrPedido(),ecf.getnrFab(),
											ecf.getNrGerado(),ecf.getNrVersao(), 
											codigoContribuinte);									
									if(sucesso>0){
										String logMsg4 = "sucesso na adicao das ecfs!!!";								
										informante.log(Level.INFO, logMsg4);								
									} else {
										status.append("--> ECF DUPLICADA!!!\n");									
										String logMsg5 = "--> ECF DUPLICADA!!!";								
										informante.log(Level.INFO, logMsg5);										
									}
								} catch (SQLException e1) {
									String logMsg6 = "Erro na busca do cnpj!!!";								
									informante.log(Level.SEVERE, logMsg6);
									System.exit(SAIDA_ANORMAL);
								}
							} 
							/**reportar o resultado usando invokeAndWait()*/
							try {
								SwingUtilities.invokeAndWait(new Runnable() {
									@Override
									public void run() {									
										status.append("Sucesso da operação!\n");
										status.append("Banco de dados carregado...Parabens!!!\n");
										botaoCarregar.setText("Operação bem-sucedida. Feche o programa!!!");
									}
								});
							} catch (InterruptedException e) {
								String logMsg6 = "ERRO IRRECUPERÁVEL!!! SAINDO..";								
								informante.log(Level.SEVERE, logMsg6);								
								e.printStackTrace();
								System.exit(SAIDA_ANORMAL);
							} catch (InvocationTargetException e) {
								String logMsg6 = "ERRO IRRECUPERÁVEL!!! SAINDO..";								
								informante.log(Level.SEVERE, logMsg6);								
								e.printStackTrace();
								e.printStackTrace();
								System.exit(SAIDA_ANORMAL);								
							}
						}
					};
					trabalhador.start(); // para não atrasar o 'dispatch thread' 	
				}
			});

		}
		return botaoCarregar;
	}
	
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setAutoscrolls(true);
			jScrollPane.setViewportView(getStatus());
		}
		return jScrollPane;
	}
	
	private JTextArea getStatus() {
		if (status == null) {
			status = new JTextArea();
			status.setEditable(false);			
			status.setText("Bem-vindo!\n"+"\nEsse programa carrega arquivos .TXT do diretorio 'Sitafe' "
					+"\npara o Banco de Dados encontrado no diretorio raiz.\n"+
					"\nObrigado por usar o LeitorTxt!!");
			//status.setAutoscrolls(true);
		}
		return status;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LerArquivosTexto thisClass = new LerArquivosTexto();
				thisClass.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				thisClass.addWindowListener(new WindowAdapter() {
					public void windowOpened(WindowEvent e) {
						String logMsg = "Abrindo Janela...";
						informante.log(Level.INFO, logMsg);
					}
					public void windowClosing(WindowEvent e) {				    	 
						gerenteConexao.limpaRecursos();
						System.exit(0);
					}
				});
				thisClass.setVisible(true);
			}
		});
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
