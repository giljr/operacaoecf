package gil.jr.ecf;


import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Font;
import javax.swing.JCheckBox;
 
/**
 * @author Gil jr 
 * Data: 02/11/2009
 * Versão: 0.1.GUI
 * Usado: Operação Ecf
 */

public class Aplicativo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	/* convenção: (l_*)label, (c_*)combo, (b_*)button */
	private JLabel l_diretorio = null;
	private JLabel l_empresa = null;
	private JComboBox c_empresa = null;	
	private JLabel l_ecf = null;
	private JComboBox c_equipamento = null;
	private JButton b_autenticar = null;
	private JButton b_localizar = null;
	private JFileChooser fc = null;
	private JLabel l_status1 = null;
	private JLabel l_status2 = null;
	protected int codigoEmpresa;
	protected int codigoEcf;
	protected File dir_arq_autenticaveis;
	protected String sha1;
	protected String md5;
	protected ArrayList<Chave> chaveiro = new ArrayList<Chave>();
	private static Logger informante = Logger.getLogger("Aplicativo.informa");
	private static final int SAIDA_ANORMAL =  - 1;
	private JCheckBox gerarZip = null;

	/**
	 * This is the default constructor
	 */
	public Aplicativo() {
		super();
		try {
			initialize();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws SQLException
	 */
	private void initialize() throws SQLException {

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 675;
		int height = 231;
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;

		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.setSize(675, 231);
		this.setBounds(x, y, width, height);
		this.setContentPane(getJContentPane());
		this.setTitle("Autenticador Java - v 0.1.GUI (Data: 02/11/2009)");

		String logMsg = "Aplicativo inicializado!";
		informante.log(Level.INFO, logMsg);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 * @throws SQLException
	 */
	private JPanel getJContentPane() throws SQLException {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridy = 8;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 2;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 7;
			l_status2 = new JLabel();
			l_status2.setText("...");
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.gridy = 6;
			l_status1 = new JLabel();
			l_status1.setText("Informações...");
			l_status1.setForeground(new Color(153, 153, 153));
			l_status1.setFont(new Font("Dialog", Font.ITALIC, 10));
			l_status2.setForeground(new Color(153, 153, 153));
			l_status2.setFont(new Font("Dialog", Font.ITALIC, 10));
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 2;
			gridBagConstraints51.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints51.insets = new Insets(12, 0, 0, 16);
			gridBagConstraints51.gridy = 2;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.insets = new Insets(10, 0, 10, 0);
			gridBagConstraints11.gridy = 8;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 5;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.insets = new Insets(10, 0, 10, 16);
			gridBagConstraints5.gridx = 2;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.insets = new Insets(0, 16, 0, 0);
			gridBagConstraints4.gridy = 5;
			l_ecf = new JLabel();
			l_ecf.setText("Equipamento Fiscal :");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 3;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new Insets(10, 0, 10, 16);
			gridBagConstraints3.gridx = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.insets = new Insets(0, 16, 0, 0);
			gridBagConstraints2.gridy = 3;
			l_empresa = new JLabel();
			l_empresa.setText("Empresa :");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.anchor = GridBagConstraints.CENTER;
			gridBagConstraints.insets = new Insets(12, 16, 0, 0);
			gridBagConstraints.gridy = 2;
			l_diretorio = new JLabel();
			l_diretorio.setText("Diretório :");
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(l_diretorio, gridBagConstraints);
			jContentPane.add(l_empresa, gridBagConstraints2);
			jContentPane.add(getC_empresa(), gridBagConstraints3);
			jContentPane.add(l_ecf, gridBagConstraints4);
			jContentPane.add(getC_equipamento(), gridBagConstraints5);
			jContentPane.add(getB_autenticar(), gridBagConstraints11);
			jContentPane.add(getB_localizar(), gridBagConstraints51);
			jContentPane.add(l_status1, gridBagConstraints6);
			jContentPane.add(l_status2, gridBagConstraints1);
			jContentPane.add(getGerarZip(), gridBagConstraints12);
		}
		return jContentPane;
	}

	/**
	 * This method initializes c_empresa
	 * 
	 * @return javax.swing.JComboBox
	 * @throws SQLException
	 */
	private JComboBox getC_empresa() throws SQLException {
		if (c_empresa == null) {
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			Contribuinte contribuinte = new Contribuinte();
			List<Contribuinte> resultados = new ArrayList<Contribuinte>();
			ConsultasContribuinte consulta = new ConsultasContribuinte();			
			resultados = consulta.buscaTodos();
			for (int i = 0; i < resultados.size(); i++) {
				contribuinte = resultados.get(i);				
				model.addElement(contribuinte.getRazaoSocial() + " # Cod. Nº "
						+ contribuinte.getCodigo());
			}

			c_empresa = new JComboBox(model);
			model.setSelectedItem(null);
			c_empresa.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e1) {

					String empresa = (c_empresa.getSelectedItem()).toString()
							.trim();
					setCodigoEmpresa(Integer.parseInt(empresa.substring(
							empresa.indexOf("º") + 1).trim()));
					l_status1.setText("Código da empresa selecionada: "
							+ codigoEmpresa);
					l_status2.setText("agora, selecione o código da ecf...");

				}
			});

		}
		return c_empresa;
	}

	/**
	 * This method initializes c_equipamento
	 * 
	 * @return javax.swing.JComboBox
	 * @throws SQLException
	 * @throws SQLException
	 */
	
	private JComboBox getC_equipamento() throws SQLException {

		if (c_equipamento == null) {
			DefaultComboBoxModel model2 = new DefaultComboBoxModel();
			     Ecf ecf = new Ecf();
				List<Ecf> resultados2 = new ArrayList<Ecf>();
				ConsultasEcf consulta2 = new ConsultasEcf();				
					
					resultados2 = consulta2.buscaTodas();
				
					for (int i = 0; i < resultados2.size(); i++) {
				    	ecf = resultados2.get(i);
						model2.addElement(ecf.getMarca()+" - "+ecf.getModelo()+" - "+ecf.getnrFab()+ " # Cod. Nº " + ecf.getCodigo());
								     }			
			c_equipamento = new JComboBox(model2);
			model2.setSelectedItem(null);
			c_equipamento
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(
								java.awt.event.ActionEvent e2) {

							
								String empresa = (c_equipamento
										.getSelectedItem()).toString().trim();
								setCodigoEcf(codigoEcf = Integer.parseInt(empresa.substring(
										empresa.indexOf("º") + 1).trim()));								
								l_status1.setText("Código da ecf selecionada: "
										+ codigoEcf);
								l_status2.setText("agora, autentique os arquivos...");
								b_autenticar.setEnabled(true);
							

						}
					});

		}
		return c_equipamento;
	}

	/**
	 * This method initializes b_autenticar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getB_autenticar() {
		if (b_autenticar == null) {
			b_autenticar = new JButton();
			b_autenticar.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent arg0) {
				}
			});
			b_autenticar.setEnabled(false);
			b_autenticar.setText("Autenticar arquivos...");
			b_autenticar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					String logMsg = "botao 'autenticar' clicado!";
					informante.log(Level.INFO, logMsg);                    
					l_status1.setText("Autenticando arquivos...");
					l_status2.setText("...aguarde...");
					
					String[] arquivos_a_autenticar = GerenteArquivosAutenticaveis.busca(dir_arq_autenticaveis);
					try {
						for (int i = 0; i < arquivos_a_autenticar.length; i++) {

							l_status2.setText("Calculando chaves criptograficas : Arquivo " + arquivos_a_autenticar[i]);

							try {
								
								sha1 = GeradorHash.sha1(dir_arq_autenticaveis+"\\"+ arquivos_a_autenticar[i]);
								md5 = GeradorHash.md5(dir_arq_autenticaveis+"\\"+arquivos_a_autenticar[i]);

							} catch (Exception e1) {
								l_status1.setText("ERRO !!!!");
								l_status2.setText("Falha na geração chaves! saindo do sistema...");
								String logMsg2 = "Falha na geração chaves! saindo do sistema...";
								informante.log(Level.SEVERE, logMsg2);
								e1.printStackTrace();				
								System.exit(SAIDA_ANORMAL);		
							}
		  													
							chaveiro.add(new Chave(arquivos_a_autenticar[i],md5,sha1));
							if(gerarZip.isSelected()){
								GeradorZip geradorZip = new GeradorZip(); 
								geradorZip.zip(dir_arq_autenticaveis+"\\"+arquivos_a_autenticar[i]); 
								
							}

						}

					} catch (Exception e2) {
						l_status1.setText("ERRO !!!!");
						l_status2.setText("Falha na geração chaves! saindo do sistema...");
						String logMsg3 = "Falha na geração chaves! saindo do sistema...";
						informante.log(Level.SEVERE, logMsg3);
						e2.printStackTrace();				
						System.exit(SAIDA_ANORMAL);	
					} finally {
						try {
							ConsultasArquivoAutenticado consulta = new  ConsultasArquivoAutenticado();
							l_status2.setText("Adicionando na tabela ArquivoAutenticado...");							
							  consulta.adicionaChaves(chaveiro, getCodigoEmpresa(), getCodigoEcf());
							l_status1.setText("SUCESSO NA AUTENTICAÇÃO !!!! ABRA MS ACCESS E IMPRIMA O TERMO");								
							  l_status2.setText(" Para outra autenticação , feche e reinicie o programa. Obrigado!");
							  b_autenticar.setEnabled(false);
							  b_localizar.setEnabled(false);
							  c_empresa.setEnabled(false);
							  c_equipamento.setEnabled(false);
							
						} catch (Exception e3) {
							l_status1.setText("ERRO !!!!");
							l_status2.setText("Falha na geração chaves! saindo do sistema...");
							String logMsg3 = "Falha na geração chaves! saindo do sistema...";							
							informante.log(Level.SEVERE, logMsg3);
							e3.printStackTrace();				
							System.exit(SAIDA_ANORMAL);
						}
					}
				}
			});
		}
		return b_autenticar;
	}

	/**
	 * This method initializes b_localizar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getB_localizar() {
		if (b_localizar == null) {
			b_localizar = new JButton();
			b_localizar
					.setText("Localize o diretório dos arquivos a autenticar...");
			b_localizar.addActionListener(new java.awt.event.ActionListener() {
				

				public void actionPerformed(java.awt.event.ActionEvent e) {

					String logMsg = "botao 'localizar' clicado!";
					informante.log(Level.INFO, logMsg);

					// fc.showOpenDialog(rootPane);
					// Handle open button action.
					if (e.getSource() == b_localizar) {
						int returnVal = fc.showOpenDialog(rootPane);

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							dir_arq_autenticaveis = fc.getSelectedFile();

							String logMsg1 = "diretório selecionado:"
									+ dir_arq_autenticaveis;
							informante.log(Level.INFO, logMsg1);

							l_status1.setText("Diretório selecionado: "
									+ dir_arq_autenticaveis);
							l_status2.setText("agora, o código da empresa...");
						} else {
							l_status1.setText("ERRO !!!!");
							l_status2.setText("Falha na geração chaves! saindo do sistema...");

						}

					}
				}
			});
		}
		return b_localizar;
	}

	public int getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(int codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	public int getCodigoEcf() {
		return codigoEcf;
	}

	public void setCodigoEcf(int codigoEcf) {
		this.codigoEcf = codigoEcf;
	}

	
	/**
	 * Esse método inicializa gerarZip	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getGerarZip() {
		if (gerarZip == null) {
			gerarZip = new JCheckBox();
			gerarZip.setText("Gerar .zip?");
		}
		return gerarZip;
	}

	public static void main(String[] args) {
		Aplicativo autenticador = new Aplicativo();
		autenticador.setDefaultCloseOperation(EXIT_ON_CLOSE);
		autenticador.setVisible(true);
	}

}
