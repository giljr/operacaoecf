package gil.jr.ecf;

/**
 * @author Gil jr Data: 12/11/2009
 * 
 * Gerencia a conexao com o banco de dados, 
 * buscando-o, e implementando `Pool' de conexao 
 */

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GerenteConexao {
	private static Logger informante = Logger.getLogger("GerenteConexao");
	private static final int SAIDA_ANORMAL = - 1;
	
    private String[] bancosDados;
	//@SuppressWarnings("unused")
    String path = "";
    String diretorio = ".";
	private String nomeBancoDados = this.buscaBdAccess(path, diretorio);	
	private String databaseUrl = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="+ nomeBancoDados.trim() + ";DriverID=22;READONLY=true}" ;
	private String userNome = "" ;
	private String password = "" ;

	@SuppressWarnings("unchecked")
	protected Vector connectionPool = new Vector();
	private int MAX_POOL_SIZE = 10;
	protected Connection connection;

	public GerenteConexao() {
		inicializa();
	}

		
	public GerenteConexao(String nomeBancoDados,
			String databaseUrl, 
				String userNome, String password) {			   
			this.nomeBancoDados = nomeBancoDados;
			this.databaseUrl = databaseUrl;
			this.userNome = userNome;
			this.password = password;
			inicializa();
		}
	/**busca banco de dados .mdb do diretorio especificado(Pre-requisito: só há um banco de dados)*/
	private String buscaBdAccess(String path, String diretorio) {
		String userdir = System.getProperty("user.dir");
		String dirBd = userdir+File.separator+path+File.separator+File.separator+diretorio;
		File dir = new File(dirBd);

		bancosDados = dir.list(new FilenameFilter() {
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

	private void inicializa() {
		/**Aqui voce inicializa toda informacao necessaria*/
		inicializaConexaoPool();		
	}

	@SuppressWarnings("unchecked")
	private void inicializaConexaoPool() {
		while (!ChecaSeConexaoPoolEstaPlena()) {
			String logMsg = "O Pool de conexão não esta pleno. Preenchendo...";
			informante.log(Level.INFO, logMsg);
			/**Adicionando conexoes até preencher todo Pool*/
			connectionPool.addElement(criaNovaConexaoParaPool());
		}
		String logMsg = "Pool de Conexão pleno. Todas conexões disponíveis!";
		informante.log(Level.INFO, logMsg);			
	}

	private synchronized boolean ChecaSeConexaoPoolEstaPlena() {
		

		/**Checa o tamanho do Pool*/
		if (connectionPool.size() < MAX_POOL_SIZE) {
			return false;
		}

		return true;
	}

	/**cria nova conexao*/
	private Connection criaNovaConexaoParaPool() {
		Connection connection = null;

		try {			
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			connection = DriverManager.getConnection(databaseUrl, userNome,
					password);
			String logMsg = "conexao: " + connection;
			informante.log(Level.INFO, logMsg);
			
		} catch (SQLException sqle) {			
			String logMsg = "Falha no carregamento do Driver!";
			informante.log(Level.SEVERE, logMsg);
			sqle.printStackTrace();							
			return null;
			
		} catch (ClassNotFoundException cnfe) {			
			String logMsg = "Classe do Driver nao encontrada!";
			informante.log(Level.SEVERE, logMsg);
			System.err.println("ClassNotFoundException: " + cnfe);
			return null;
		}

		return connection;
	}

	public synchronized Connection buscaConexaoPeloPoll() {
		Connection connection = null;

		/**Checa se há conexão disponiveis; haverá tempo em que a(s) conexão(ões) não esstarão livres;*/ 
		if (connectionPool.size() > 0) {
			connection = (Connection) connectionPool.firstElement();
			connectionPool.removeElementAt(0);
		}
		/**Entregando a conexao do Pool*/
		return connection;
	}

	@SuppressWarnings("unchecked")
	public synchronized void retornaConexaoParaPoll(Connection connection) {
		// devolvendo a conexao ao Pool vinda do cliente
		connectionPool.addElement(connection);
	}
	
	/**Libera os recursos do Pool de conexão(oes)*/
	public void limpaRecursos() {
		
		if (connectionPool != null) {
			while (connectionPool.size() > 0) {
				connection = (Connection) connectionPool.firstElement();
				try {
					connection.close();
				} catch (SQLException e) {
					String logMsg5 = "Falha ao fechar a conexao com Banco de Dados! Saindo do sistema...";
					informante.log(Level.SEVERE, logMsg5);
					e.printStackTrace();
					System.exit(SAIDA_ANORMAL);
				}
				connectionPool.removeElementAt(0);
			}
			String logMsg = "Pool de Conexão vazia. Recursos Liberados!";
			informante.log(Level.INFO, logMsg);
		}	
		
	}

/*	public static void main(String args[]) {
		String nomeBancoDados = "testes1.mdb";
		String databaseUrl = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="+ nomeBancoDados.trim() + ";DriverID=22;READONLY=true}" ;
		String userName = "";
		String password = "";
		GerenteConexao bdGerenteConexao = new GerenteConexao(nomeBancoDados,databaseUrl,userName, password);
		GerenteConexao bdGerenteConexao = new GerenteConexao();
		bdGerenteConexao.limpaRecursos();
	}
*/
	
}
