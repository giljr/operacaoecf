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
 * @author Gil jr 
 * Data: 31/10/2009
 * Versão: 0.1.GUI
 * Usado: Operação Ecf
 *  
 *  TODO: SQLException Duplicate key
 */

	
public class ConsultasEcf {
		private static Connection NOVA_CONEXAO = null;

		private static Logger informante = Logger.getLogger("ConsultasEcf");
		private static final int SAIDA_ANORMAL = - 1;

		private static PreparedStatement selecionaTodasEcfs = null;
		private String SELECIONA_TODAS_ECFS = "SELECT * FROM Ecf";

		private static PreparedStatement consultaEcfPorFab = null;
		private String CONSULTA_ECF_POR_FAB = "SELECT * FROM Ecf WHERE NrFabricacao = ? ";
		
		private static PreparedStatement consultaEcfsPorEmp = null;
		private String CONSULTA_ECFS_POR_EMP = "SELECT * FROM Ecf WHERE CodigoContribuinte = ? ";

		private static PreparedStatement adicionaEcf = null;
		private String ADICIONA_ECF = "INSERT INTO Ecf (Marca, Modelo, NrAto, NrPedido, NrFabricacao, NrGerado, NrVersao, CodigoContribuinte) VALUES (?,?,?,?,?,?,?,?)";

		private static boolean temosConexao = false;

		/**Contrutor**/
		public ConsultasEcf() {
			try {
				NOVA_CONEXAO = GerenteBancoDadosMS.estabeleceConexaoComMsBd();
				temosConexao = true;
				
				consultaEcfPorFab = NOVA_CONEXAO.prepareStatement(CONSULTA_ECF_POR_FAB);
				consultaEcfsPorEmp = NOVA_CONEXAO.prepareStatement(CONSULTA_ECFS_POR_EMP);
				selecionaTodasEcfs = NOVA_CONEXAO.prepareStatement(SELECIONA_TODAS_ECFS);
				adicionaEcf = NOVA_CONEXAO.prepareStatement(ADICIONA_ECF);
				
			} catch (SQLException e) {
				temosConexao = false;			
				String logMsg = "Falha no contrutor! saindo do sistema...";
				informante.log(Level.SEVERE, logMsg);
				e.printStackTrace();
				GerenteBancoDadosMS.limpaRecursos();
				System.exit(SAIDA_ANORMAL);
			}
		}

		/**Adiciona Ecf a tabela correspondente do MS Access**/
		@SuppressWarnings("unused")
		private int adiciona(String marca, String modelo, String nrAto,
				String nrPedido, String nrFab, String nrGerado, String nrVersao, int codigoContribuinte ) throws SQLException {
			int resultado = 0;

			if (temosConexao) {
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
					String logMsg = "Falha ao adicionar Ecf! saindo do sistema...";
					informante.log(Level.SEVERE, logMsg);
					e.printStackTrace();
					GerenteBancoDadosMS.limpaRecursos();
					System.exit(SAIDA_ANORMAL);

				} finally {
					GerenteBancoDadosMS.limpaRecursos();
				}
			} else {
				String logMsg = "Não Temos conexao! Saindo do sistema...";
				informante.log(Level.SEVERE, logMsg);
				System.exit(SAIDA_ANORMAL);
			}
			return resultado;
		}

		/**
		 * Confere se o ecf já está cadastrado no banco de dados; pesquisa
		 * por Nr de Fabricação.
		 **/
		@SuppressWarnings("unused")
		private boolean buscaPorFab(String _nrFab) throws SQLException {
			String marca = null;
			String nrFab = null;
			if (temosConexao) {
				try {

					consultaEcfPorFab.setString(1, _nrFab);
					ResultSet resultSet = consultaEcfPorFab.executeQuery();
					while (resultSet.next()) {
						marca = resultSet.getString("Marca");

						nrFab = resultSet.getString("nrFabricacao");

						if (nrFab.matches(_nrFab)) {
							String logMsg = "Ecf já registrada! " + marca +" - " +nrFab;
							informante.log(Level.INFO, logMsg);							
							return true;
						} else {
							String logMsg = "Erro confirmando Ecf...saindo do sistema...";
							informante.log(Level.SEVERE, logMsg);							
							System.exit(SAIDA_ANORMAL);
							return false;

						}

					}
					String logMsg = "Ecf não registrado! nrFab ="+_nrFab;
					informante.log(Level.INFO, logMsg);					

				} catch (SQLException e) {
					String logMsg = "Falha ao buscar Ecf por Fabricação! saindo do sistema...";
					informante.log(Level.SEVERE, logMsg);
					e.printStackTrace();
					GerenteBancoDadosMS.limpaRecursos();
					System.exit(SAIDA_ANORMAL);
					
				} finally {
					GerenteBancoDadosMS.limpaRecursos();
				}
			} else {
				String logMsg = "Não Temos conexao! Saindo do sistema...";
				informante.log(Level.SEVERE, logMsg);
				System.exit(SAIDA_ANORMAL);
			}
			return false;

		}

		/**Seleciona todos os Ecfs; retorna Lista de Objetos Ecf**/
		 List<Ecf> buscaTodas() throws SQLException {

			ArrayList<Ecf> resultados = null;
			if (temosConexao) {
				try {

					ResultSet resultSet = selecionaTodasEcfs.executeQuery();
					resultados = new ArrayList<Ecf>();
					while (resultSet.next()) {
						resultados.add(new Ecf(
								resultSet.getInt("Codigo"),
								resultSet.getString("Marca"), 
								resultSet.getString("Modelo"), 
								resultSet.getString("NrAto"), 
								resultSet.getString("NrPedido"), 
								resultSet.getString("NrFabricacao"),
								resultSet.getString("NrGerado"),
								resultSet.getString("NrVersao"),
								resultSet.getInt("codigoContribuinte")));

					}

				} catch (SQLException e) {
					String logMsg = "Falha na busca de todas Ecfs! saindo do sistema...";
					informante.log(Level.SEVERE, logMsg);
					e.printStackTrace();
					System.exit(SAIDA_ANORMAL);
				} finally {
					GerenteBancoDadosMS.limpaRecursos();
				}
			} else {
				String logMsg = "Não Temos conexao! Saindo do sistema...";
				informante.log(Level.SEVERE, logMsg);
				System.exit(SAIDA_ANORMAL);
			}
			return resultados;

		}
		
		List<Ecf> buscaTodasPorEmp(int codigoContribuinte) throws SQLException {

			ArrayList<Ecf> resultados = null;
			if (temosConexao) {
				try {

					consultaEcfsPorEmp.setInt(1, codigoContribuinte);
					ResultSet resultSet = consultaEcfsPorEmp.executeQuery();
					resultados = new ArrayList<Ecf>();
					while (resultSet.next()) {
						resultados.add(new Ecf(
								resultSet.getInt("Codigo"),
								resultSet.getString("Marca"), 
								resultSet.getString("Modelo"), 
								resultSet.getString("NrAto"), 
								resultSet.getString("NrPedido"), 
								resultSet.getString("NrFabricacao"),
								resultSet.getString("NrGerado"),
								resultSet.getString("NrVersao"),
								resultSet.getInt("codigoContribuinte")));

					}

				} catch (SQLException e) {
					String logMsg = "Falha na busca de todas Ecfs por empresa! saindo do sistema...";
					informante.log(Level.SEVERE, logMsg);
					e.printStackTrace();
					System.exit(SAIDA_ANORMAL);
				} finally {
					GerenteBancoDadosMS.limpaRecursos();
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
			 
			 
			Ecf ecf = new Ecf();
			List<Ecf> resultados = new ArrayList<Ecf>();
			ConsultasEcf consulta = new ConsultasEcf();
			System.out.println("Consultando Todas Ecfs...");
			resultados = consulta.buscaTodas();
				for (int i = 0; i < resultados.size(); i++) {
			    	ecf = resultados.get(i);
					System.out.println(ecf.getMarca()+" - "+ecf.getnrFab());
							     }
				
			 
			 
		    ConsultasEcf consulta2 = new ConsultasEcf();
			System.out.println("Buscado Ecf por nr Fabricação...");
			boolean existe = false;
			existe = consulta2.buscaPorFab("DR0208BR000000141933");
			System.out.println("Ecf existe no Banco de Dados? "+existe);
					
			
			 
			
			 
			ConsultasEcf consulta3 = new ConsultasEcf();
			System.out.println("Adicionando ECF...");
			consulta3.adiciona("TERMOPRINTER", "TP 8960", "24/2009","2261/09", "TP0208BR000000142356", "2008002078","01.00.00", 15);
			System.out.println("Ecf adicionada com sucesso!!!");
				
			 
			 Ecf ecf = new Ecf();
			List<Ecf> resultados = new ArrayList<Ecf>();
			ConsultasEcf consulta = new ConsultasEcf();
			System.out.println("Consultando Todas Ecfs...");
			resultados = consulta.buscaTodasPorEmp(15);
				for (int i = 0; i < resultados.size(); i++) {
			    	ecf = resultados.get(i);
					System.out.println(ecf.getMarca()+" - "+ecf.getnrFab());
							     }
			  		
		 }
*/
		
}
