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
 */

public class ConsultasContribuinte {

	private static Logger informante = Logger.getLogger("ConsultasContribuinte");
	private static final int SAIDA_ANORMAL = -1;

	private static PreparedStatement selecionaTodosContribuintes = null;
	private String SELECIONA_TODOS_CONTRIBUINTES = "SELECT * FROM Contribuinte";

	private static PreparedStatement consultaContribuintePorCnpj = null;
	private String CONSULTA_CONTRIBUINTE_POR_CNPJ = "SELECT * FROM Contribuinte WHERE Cnpj = ? ";

	private static PreparedStatement adicionaContribuinte = null;
	private String ADICIONA_CONTRIBUINTE = "INSERT INTO Contribuinte (Rs, Ie, Cnpj, End, Municipio, Uf)" +
			" VALUES (?,?,?,?,?,?)";

	private static boolean temosConexao = false;

	protected GerenteConexao gerente;
	protected Connection conexao;

	/** Construtor **/
	protected ConsultasContribuinte(GerenteConexao gerente) {
		this.gerente = gerente;
		try {
			conexao = gerente.buscaConexaoPeloPoll();
			temosConexao = true;

			consultaContribuintePorCnpj = conexao.prepareStatement(CONSULTA_CONTRIBUINTE_POR_CNPJ);
			selecionaTodosContribuintes = conexao.prepareStatement(SELECIONA_TODOS_CONTRIBUINTES);
			adicionaContribuinte = conexao.prepareStatement(ADICIONA_CONTRIBUINTE);

		} catch (SQLException e) {
			temosConexao = false;
			String logMsg = "Falha no contrutor! saindo do sistema...";
			informante.log(Level.SEVERE, logMsg);
			e.printStackTrace();
			gerente.retornaConexaoParaPoll(conexao);
			System.exit(SAIDA_ANORMAL);
		}
	}

	/** Adiciona Contribuinte a tabela correspondente do MS Access **/
	protected int adiciona(String razaoSocial, String ie, String cnpj,
			String end, String municipio, String uf) throws SQLException {
		int resultado = 0;

		if (temosConexao) {
			int NAO_ENCONTRADO = -1;
			if (NAO_ENCONTRADO == buscaPorCnpj(cnpj)) {
				try {
					adicionaContribuinte.setString(1, razaoSocial);
					adicionaContribuinte.setString(2, ie);
					adicionaContribuinte.setString(3, cnpj);
					adicionaContribuinte.setString(4, end);
					adicionaContribuinte.setString(5, municipio);
					adicionaContribuinte.setString(6, uf);

					resultado = adicionaContribuinte.executeUpdate();

				} catch (SQLException e) {
					String logMsg = "Falha ao adicionar Contribuintes! saindo do sistema...";
					informante.log(Level.SEVERE, logMsg);
					e.printStackTrace();
					System.exit(SAIDA_ANORMAL);

				} finally {
					gerente.retornaConexaoParaPoll(conexao);
				}
			} else {
				String logMsg = "Adicao abortada!!! Saindo do sistema...";
				informante.log(Level.INFO, logMsg);
				gerente.retornaConexaoParaPoll(conexao);
				return NAO_ENCONTRADO;			}
		} else {
			String logMsg = "Não Temos conexao! Saindo do sistema...";
			informante.log(Level.SEVERE, logMsg);
			System.exit(SAIDA_ANORMAL);
		}
		return resultado;
	}

	/**Confere se contribuinte está cadastrado no banco de dados; pesquisa por cnpj
	 * retorna CodigoContribuinte**/
	protected int buscaPorCnpj(String _cnpj) throws SQLException {
		String Cnpj = null;
		String Rs = null;
		int codigoContribuinte = 0;
		int NAO_ENCONTRADO = -1;
		if (temosConexao) {
			try {
				consultaContribuintePorCnpj.setString(1, _cnpj);
				ResultSet resultSet = consultaContribuintePorCnpj
						.executeQuery();
				while (resultSet.next()) {
					Cnpj = resultSet.getString("Cnpj");

					Rs = resultSet.getString("Rs");

					if (Cnpj.matches(_cnpj)) {
						codigoContribuinte = resultSet.getInt("Codigo");
						String logMsg = "Contribuinte registrado! " + Rs;
						informante.log(Level.INFO, logMsg);
						return codigoContribuinte;
					} else {
						String logMsg = "Erro buscando por cnpj...Saindo do sistema...";
						informante.log(Level.SEVERE, logMsg);
						System.exit(SAIDA_ANORMAL);						
					}

				}
				String logMsg = "Contribuinte não registrado! Cnpj = " + _cnpj;
				informante.log(Level.INFO, logMsg);
				return NAO_ENCONTRADO;

			} catch (SQLException e) {
				String logMsg = "Falha ao buscar Contribuinte por Cnpj! Saindo...";
				informante.log(Level.SEVERE, logMsg);
				e.printStackTrace();
				System.exit(SAIDA_ANORMAL);

			} finally {
				gerente.retornaConexaoParaPoll(conexao);
			}
		} else {
			String logMsg = "Não Temos conexao! Saindo do sistema...";
			informante.log(Level.SEVERE, logMsg);
			System.exit(SAIDA_ANORMAL);
		}
		return codigoContribuinte;
	}

	/** Seleciona todos os Contribuintes; retorna Lista de Objetos Contribuinte **/
	protected List<Contribuinte> buscaTodos() throws SQLException {
		ArrayList<Contribuinte> resultados = null;
		if (temosConexao) {
			try {
				ResultSet resultSet = selecionaTodosContribuintes
						.executeQuery();
				resultados = new ArrayList<Contribuinte>();
				while (resultSet.next()) {
					resultados.add(new Contribuinte(resultSet.getInt("Codigo"),
							resultSet.getString("Rs"), resultSet
									.getString("Ie"), resultSet
									.getString("Cnpj"), resultSet
									.getString("End"), resultSet
									.getString("Municipio"), resultSet
									.getString("Uf")));
				}

			} catch (SQLException e) {
				String logMsg = "Falha na busca de todos contribuintes! Saindo...";
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
 
      //Instruções: use consulta separada, ou todas, simultaneamente, bastando
	  //usar um gerente e limpar recursos no final

	  Contribuinte contribuinte = new Contribuinte(); 
	  List<Contribuinte> resultados = new ArrayList<Contribuinte>(); 
	  GerenteConexao gerente = new GerenteConexao();
	  ConsultasContribuinte consulta = new  ConsultasContribuinte(gerente);
	  System.out.println("Consultando Todos contribuintes..."); 
	  resultados = consulta.buscaTodos(); if (resultados.size() > 0) { 
		  for (int i = 0; i < resultados.size(); i++) {
			  contribuinte = resultados.get(i);
		  }
		  System.out.println(contribuinte.getRazaoSocial());
		  gerente.limpaRecursos();

	  } else {
		  System.out.println("Banco de dados vazio!! nenhum Contribuinte!");
		  gerente.limpaRecursos(); 
	  }

	  
	  GerenteConexao gerente = new GerenteConexao(); 
	  ConsultasContribuinte  consulta2 = new ConsultasContribuinte(gerente);
	  System.out.println("Buscado contribuinte por Cnpj...");
	  int codigoContribuinte = -1; 
	  codigoContribuinte = consulta2.buscaPorCnpj("22828099000157");
	  System.out.println("Contribuinte existe no Banco de Dados? Codigo: " + codigoContribuinte); 
	  gerente.limpaRecursos();
	  	  
	  
	  GerenteConexao gerente = new GerenteConexao(); 
	  ConsultasContribuinte consulta3 = new ConsultasContribuinte(gerente);
	  System.out.println("Adicionando contribuinte...");
	  consulta3.adiciona("JOSIAS DE OLIVEIRA", "00000000187232",
	  "22828099000157", "RUA PRINCIPAL, 200", "URUPA", "RO");
	  System.out.println("Contribuinte adicionado com sucesso!!!");
	  gerente.limpaRecursos();
	  
	  }
 */

}
