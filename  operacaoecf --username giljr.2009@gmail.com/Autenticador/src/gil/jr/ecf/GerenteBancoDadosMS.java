package gil.jr.ecf;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gil jr Data: 31/10/2009
 *        Deprecado em 25/11/2009 - substituido por 'GerenteConexao'
 */

public class GerenteBancoDadosMS {
	
	private static Logger informante = Logger.getLogger("GerenteBancoDadosMS");
	private static final int SAIDA_ANORMAL = - 1;

	private static File dir_raiz;
	private static String[] bancosDados;
	private static String bancoDados;
	protected static Connection conexao;
	protected static boolean temosConexao = false;

	/**Retorna o primeiro arquivo .MDB (MS Access) do diretório raiz**/
	public static String buscaBdAccessNoDirRaiz() {

		dir_raiz = new File(".");

		bancosDados = dir_raiz.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(".MDB")) {
					return true;
				}
				if (name.endsWith(".mdb")) {
					return true;
				} else {
					return false;
				}
			}
		});
       if (bancosDados.length > 0)
			return bancosDados[0];
		return null;

	}

	/**Carrega o Driver ponte JdbcOdbc para conexao com aplicativos MS, caso haja arquivo .MDB na raiz**/
	public static void carregaDriver() {

		bancoDados = buscaBdAccessNoDirRaiz();
		if (bancoDados != null) {
			
			try {
				String logMsg = "No Diretório raiz: "
						+ dir_raiz.getCanonicalPath();
		        informante.log(Level.INFO, logMsg);
		        
			} catch (IOException e1) {
				String logMsg = "Falha na inicialização do Driver! " +
						"saindo do sistema...";
				informante.log(Level.SEVERE, logMsg);
				e1.printStackTrace();				
				System.exit(SAIDA_ANORMAL);					
			}
			String logMsg0 = "Foi Encontrado Banco de Dados: " + bancoDados +".Carregando Driver";
			informante.log(Level.INFO, logMsg0);
			try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");				
				String logMsg1 = "Driver carregado com sucesso!!!";
				informante.log(Level.INFO, logMsg1);
				
			} catch (Exception e2) {
				String logMsg2 = "Falha no carregamento do Driver! saindo do sistema...";
				informante.log(Level.SEVERE, logMsg2);
				e2.printStackTrace();				
				System.exit(SAIDA_ANORMAL);				
			}

		} else {
			String logMsg3 = "Não há arquivo .MDB na raiz.\nConfigure o Banco de Dados antes..." +
					"\nDriver nao carregado!\nSaindo do sistema...";
			informante.log(Level.SEVERE, logMsg3);
			System.exit(SAIDA_ANORMAL);		
			
		}

	}

	/**Estabelece de devolve a conexão com o banco de dados MS Access na raiz**/
	public static Connection estabeleceConexaoComMsBd() {

		//carregaDriver();
		/*
		 * Testado apenas no Microsoft® Windows Vista™ Business Versão 6.0.6001
		 * Service Pack 1 Compilação 6001
		 */
		try {
			String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
			database += bancoDados.trim() + ";DriverID=22;READONLY=true}";
			conexao = DriverManager.getConnection(database, "", "");
			temosConexao = true;
			String logMsg4 = "Temos conexao!";
			informante.log(Level.INFO, logMsg4);		

		} catch (SQLException e) {
			temosConexao = false;
			String logMsg5 = "Connexao com arquivo .MDB falhou !!! saindo do sistema...";
			informante.log(Level.SEVERE, logMsg5);
			e.printStackTrace();				
			System.exit(SAIDA_ANORMAL);				
		}
		return conexao;

	}

	/**Libera os recursos da conexão**/
	public static void limpaRecursos() {
		if (temosConexao) {
			try {
				conexao.close();
			} catch (SQLException e) {
				String logMsg5 = "Falha ao fechar a conexao com Banco de Dados! Saindo do sistema...";
				informante.log(Level.SEVERE, logMsg5);
				e.printStackTrace();				
				System.exit(SAIDA_ANORMAL);					
			}
		}

	}

}
