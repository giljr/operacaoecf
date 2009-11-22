package gil.jr.ecf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gil Jr Data: 31/10/2009
 * 
 *            Revisões: 20/11/2009 - implementação novo 'GerenteConexao'; evitar duplicações  
 *            (TODO: retornar seleçao classificada por Marca de Ecf; implementar ecfPorEmpresa)       
 */

public class ConsultasEcf {
	private static Logger informante = Logger.getLogger("ConsultasEcf");
	private static final int SAIDA_ANORMAL = -1;

	private static PreparedStatement selecionaTodasEcfs = null;
	private String SELECIONA_TODAS_ECFS = "SELECT * FROM Ecf";

	private static PreparedStatement consultaEcfPorFab = null;
	private String CONSULTA_ECF_POR_FAB = "SELECT * FROM Ecf WHERE NrFabricacao = ? ";

	private static PreparedStatement consultaEcfsPorEmp = null;
	private String CONSULTA_ECFS_POR_EMP = "SELECT * FROM Ecf WHERE CodigoContribuinte = ? ";

	private static PreparedStatement adicionaEcf = null;
	private String ADICIONA_ECF = "INSERT INTO Ecf (Marca, Modelo, NrAto, NrPedido, NrFabricacao, " +
			"NrGerado, NrVersao, CodigoContribuinte) VALUES (?,?,?,?,?,?,?,?)";

	protected GerenteConexao gerente;
	protected Connection conexao;
	private static boolean temosConexao = false;

	/** Construtor **/
	protected ConsultasEcf(GerenteConexao gerente) {
		this.gerente = gerente;
		try {
			conexao = gerente.buscaConexaoPeloPoll();
			temosConexao = true;

			consultaEcfPorFab = conexao.prepareStatement(CONSULTA_ECF_POR_FAB);
			consultaEcfsPorEmp = conexao.prepareStatement(CONSULTA_ECFS_POR_EMP);
			selecionaTodasEcfs = conexao.prepareStatement(SELECIONA_TODAS_ECFS);
			adicionaEcf = conexao.prepareStatement(ADICIONA_ECF);

		} catch (SQLException e) {
			temosConexao = false;
			String logMsg = "Falha no contrutor! saindo do sistema...";
			informante.log(Level.SEVERE, logMsg);
			e.printStackTrace();
			gerente.retornaConexaoParaPoll(conexao);
			System.exit(SAIDA_ANORMAL);
		}
	}

	/** Adiciona Ecf a tabela correspondente do MS Access **/
	protected int adiciona(String marca, String modelo, String nrAto, String nrPedido,
			String nrFab, String nrGerado, String nrVersao,
			int codigoContribuinte) throws SQLException {
		
		int resultado = 0;

		if (temosConexao) {
			int NAO_ENCONTRADA = -1;
			if (NAO_ENCONTRADA == this.buscaPorFab(nrFab)) {
				try {
					adicionaEcf.setString(1, marca);
					adicionaEcf.setString(2, modelo);
					adicionaEcf.setString(3, nrAto);
					adicionaEcf.setString(4, nrPedido);
					adicionaEcf.setString(5, nrFab);
					adicionaEcf.setString(6, nrGerado);
					adicionaEcf.setString(7, nrVersao);
					adicionaEcf.setInt(8, codigoContribuinte);

					resultado = adicionaEcf.executeUpdate();

				} catch (SQLException e) {
					String logMsg = "Falha ao adicionar Ecf! Saindo...";
					informante.log(Level.SEVERE, logMsg);
					e.printStackTrace();
					gerente.retornaConexaoParaPoll(conexao);
					return NAO_ENCONTRADA;			

				} finally {
					gerente.retornaConexaoParaPoll(conexao);
				}
			} else {
				String logMsg = "Adicao abortada!!! Saindo...";
				informante.log(Level.INFO, logMsg);
				gerente.retornaConexaoParaPoll(conexao);
				return NAO_ENCONTRADA;				
			}
		} else {
			String logMsg = "Não Temos conexao! Saindo do sistema...";
			informante.log(Level.SEVERE, logMsg);
			System.exit(SAIDA_ANORMAL);
		}
		return resultado;
	}

	/**
	 * Confere se o ecf já está cadastrado no banco de dados; pesquisa por Nr de
	 * Fabricação e retorna codigo da ecf no banco de dados.
	 **/
	
	protected int buscaPorFab(String _nrFab) throws SQLException {
		String marca = null;
		String nrFab = null;
		int codigoEcf = 0;
		int NAO_ENCONTRADA = -1;
		if (temosConexao) {
			try {
				consultaEcfPorFab.setString(1, _nrFab);
				ResultSet resultSet = consultaEcfPorFab.executeQuery();
				while (resultSet.next()) {
					marca = resultSet.getString("Marca");

					nrFab = resultSet.getString("nrFabricacao");

					codigoEcf = resultSet.getInt("Codigo");

					if (nrFab.matches(_nrFab)) {
						String logMsg = "Ecf já registrada! " + marca + " - "
						+ nrFab;
						informante.log(Level.INFO, logMsg);
						return codigoEcf;
					} else {
						String logMsg = "Erro confirmando Ecf! Saindo do sistema...";
						informante.log(Level.SEVERE, logMsg);						
						System.exit(SAIDA_ANORMAL);
					}

				}
				String logMsg = "Ecf não registrada! nrFab =" + _nrFab;
				informante.log(Level.INFO, logMsg);				
				return NAO_ENCONTRADA;

			} catch (SQLException e) {
				String logMsg = "Falha ao buscar Ecf por Fabricação! Saindo...";
				informante.log(Level.SEVERE, logMsg);
				e.printStackTrace();				
				System.exit(SAIDA_ANORMAL);

			} finally {				
				gerente.retornaConexaoParaPoll(conexao);
			}
		} else {
			String logMsg = "Não Temos conexao! Saindo do sistema...";
			informante.log(Level.SEVERE, logMsg);
			gerente.retornaConexaoParaPoll(conexao);
			System.exit(SAIDA_ANORMAL);
		}
		return NAO_ENCONTRADA;

	}

	/** Seleciona todos os Ecfs; retorna Lista de Objetos Ecf **/
	protected List<Ecf> buscaTodas() throws SQLException {

		ArrayList<Ecf> resultados = null;
		if (temosConexao) {
			try {

				ResultSet resultSet = selecionaTodasEcfs.executeQuery();
				resultados = new ArrayList<Ecf>();
				while (resultSet.next()) {
					resultados.add(new Ecf(resultSet.getInt("Codigo"),
							resultSet.getString("Marca"), resultSet
									.getString("Modelo"), resultSet
									.getString("NrAto"), resultSet
									.getString("NrPedido"), resultSet
									.getString("NrFabricacao"), resultSet
									.getString("NrGerado"), resultSet
									.getString("NrVersao"), resultSet
									.getInt("codigoContribuinte")));

				}

			} catch (SQLException e) {
				String logMsg = "Falha na busca de todas Ecfs! Saindo...";
				informante.log(Level.SEVERE, logMsg);				
				e.printStackTrace();
				return null;				
			} finally {				
				gerente.retornaConexaoParaPoll(conexao);
			}
		} else {
			String logMsg = "Não Temos conexao! Saindo do sistema...";
			informante.log(Level.SEVERE, logMsg);
			System.exit(SAIDA_ANORMAL);
		}
		return resultados;

	}

	/** retorna uma lista de Ecf por codigo de empresa*/
	protected List<Ecf> buscaTodasPorEmp(int codigoContribuinte) throws SQLException {

		ArrayList<Ecf> resultados = null;
		if (temosConexao) {
			try {
				consultaEcfsPorEmp.setInt(1, codigoContribuinte);
				ResultSet resultSet = consultaEcfsPorEmp.executeQuery();
				resultados = new ArrayList<Ecf>();
				while (resultSet.next()) {
					resultados.add(new Ecf(resultSet.getInt("Codigo"),
							resultSet.getString("Marca"), resultSet
									.getString("Modelo"), resultSet
									.getString("NrAto"), resultSet
									.getString("NrPedido"), resultSet
									.getString("NrFabricacao"), resultSet
									.getString("NrGerado"), resultSet
									.getString("NrVersao"), resultSet
									.getInt("codigoContribuinte")));
				}

			} catch (SQLException e) {
				String logMsg = "Falha na busca de todas Ecfs por empresa! Saindo...";
				informante.log(Level.SEVERE, logMsg);				
				e.printStackTrace();
				return null;				
			} finally {				
				gerente.retornaConexaoParaPoll(conexao);
			}
		} else {
			String logMsg = "Não Temos conexao! Saindo do sistema...";
			informante.log(Level.SEVERE, logMsg);
			System.exit(SAIDA_ANORMAL);
		}
		return resultados;

	}
/*
	public static void main(String[] args) throws SQLException {
		
		  //Instruções: use consulta individual, ou todas simultaneamente,
		  //bastando usar um gerente e limpar recursos no final
		  
		  Ecf ecf = new Ecf();
		  List<Ecf> resultados = new ArrayList<Ecf>();
		  GerenteConexao gerente = new GerenteConexao(); 
		  ConsultasEcf consulta = new ConsultasEcf(gerente);
		  
		  System.out.println("Consultando Todas Ecfs..."); 
		  resultados = consulta.buscaTodas(); 
		  for (int i = 0; i < resultados.size(); i++) {
		  ecf = resultados.get(i);
		  System.out.println(ecf.getMarca()+" - "+ecf.getnrFab()); 
		      }
		  	  
		  
		  GerenteConexao gerente = new GerenteConexao(); 
		  ConsultasEcf consulta2 = new ConsultasEcf(gerente);		  
		  System.out.println("Buscado Ecf por nr Fabricação..."); 
		  int codigoEcf = -1; 
		  codigoEcf = consulta2.buscaPorFab("53270");
		  System.out.println("Ecf existe no Banco de Dados? codigo = "+codigoEcf);
		  
		  
		  
		  
		  GerenteConexao gerente = new GerenteConexao(); 
		  ConsultasEcf consulta3 = new ConsultasEcf(gerente);		  
		  System.out.println("Adicionando ECF...");
		  consulta3.adiciona("TERMOPRINTER", "TP 8960", "24/2009","2261/09",
		  "TP0208BR000000142356", "2008002078","01.00.00", 15);
		  System.out.println("Ecf adicionada com sucesso!!!");
		  
		 

		  Ecf ecf = new Ecf();
		  List<Ecf> resultados = new ArrayList<Ecf>();
		  GerenteConexao gerente = new GerenteConexao();
		  ConsultasEcf consulta4 = new ConsultasEcf(gerente);
		  System.out.println("Consultando Todas Ecfs da empresa...");
		  resultados = consulta4.buscaTodasPorEmp(52);
		  for (int i = 0; i < resultados.size(); i++) {
			  ecf = resultados.get(i);
			  System.out.println(ecf.getMarca() + " - " + ecf.getnrFab());
		  }

		  gerente.limpaRecursos();
	}
*/
}
