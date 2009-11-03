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

public class ConsultasContribuinte {

	private static Connection NOVA_CONEXAO = null;

	private static Logger informante = Logger.getLogger("ConsultasContribuinte");
	private static final int SAIDA_ANORMAL = -1;

	private static PreparedStatement selecionaTodosContribuintes = null;
	private String SELECIONA_TODOS_CONTRIBUINTES = "SELECT * FROM Contribuinte";

	private static PreparedStatement consultaContribuintePorCnpj = null;
	private String CONSULTA_CONTRIBUINTE_POR_CNPJ = "SELECT * FROM Contribuinte WHERE Cnpj = ? ";

	private static PreparedStatement adicionaContribuinte = null;
	private String ADICIONA_CONTRIBUINTE = "INSERT INTO Contribuinte (Rs, Ie, Cnpj, End, Municipio, Uf) VALUES (?,?,?,?,?,?)";

	private static boolean temosConexao = false;

	/**Contrutor**/
	public ConsultasContribuinte() {
		try {
			NOVA_CONEXAO = GerenteBancoDadosMS.estabeleceConexaoComMsBd();
			temosConexao = true;
			
			consultaContribuintePorCnpj = NOVA_CONEXAO.prepareStatement(CONSULTA_CONTRIBUINTE_POR_CNPJ);
			selecionaTodosContribuintes = NOVA_CONEXAO.prepareStatement(SELECIONA_TODOS_CONTRIBUINTES);
			adicionaContribuinte = NOVA_CONEXAO.prepareStatement(ADICIONA_CONTRIBUINTE);
			
		} catch (SQLException e) {
			temosConexao = false;			
			String logMsg = "Falha no contrutor! saindo do sistema...";
			informante.log(Level.SEVERE, logMsg);
			e.printStackTrace();
			GerenteBancoDadosMS.limpaRecursos();
			System.exit(SAIDA_ANORMAL);
		}
	}

	/**Adiciona Contribuinte a tabela correspondente do MS Access**/
	
	private int adiciona(String razaoSocial, String ie, String cnpj,
			String end, String municipio, String uf) throws SQLException {
		int resultado = 0;

		if (temosConexao) {
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
	 * Confere se o contribuinte já está cadastrado no banco de dados; pesquisa
	 * por cnpj
	 **/
	
	private boolean buscaPorCnpj(String _cnpj) throws SQLException {
		String Cnpj = null;
		String Rs = null;
		if (temosConexao) {
			try {

				consultaContribuintePorCnpj.setString(1, _cnpj);
				ResultSet resultSet = consultaContribuintePorCnpj.executeQuery();
				while (resultSet.next()) {
					Cnpj = resultSet.getString("Cnpj");

					Rs = resultSet.getString("Rs");

					if (Cnpj.matches(_cnpj)) {
						String logMsg = "Contribuinte já registrado! " + Rs;
						informante.log(Level.INFO, logMsg);						
						return true;
					} else {
						String logMsg = "Erro confirmando cnpj...saindo do sistema...";
						informante.log(Level.SEVERE, logMsg);								
						System.exit(SAIDA_ANORMAL);
						return false;

					}

				}
				String logMsg = "Contribuinte não registrado! Cnpj = "+ _cnpj;
				informante.log(Level.INFO, logMsg);			

			} catch (SQLException e) {
				String logMsg = "Falha ao buscar Contribuinte por Cnpj! saindo do sistema...";
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

	/**Seleciona todos os Contribuintes; retorna Lista de Objetos Contribuinte**/
	List<Contribuinte> buscaTodos() throws SQLException {

		ArrayList<Contribuinte> resultados = null;
		if (temosConexao) {
			try {

				ResultSet resultSet = selecionaTodosContribuintes.executeQuery();
				resultados = new ArrayList<Contribuinte>();
				while (resultSet.next()) {
					resultados.add(new Contribuinte(
							resultSet.getInt("Codigo"),
							resultSet.getString("Rs"), 
							resultSet.getString("Ie"), 
							resultSet.getString("Cnpj"), 
							resultSet.getString("End"), 
							resultSet.getString("Municipio"),
							resultSet.getString("Uf")));

				}

			} catch (SQLException e) {
				String logMsg = "Falha na busca de todos contribuintes! saindo do sistema...";
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
		 
		 
		Contribuinte contribuinte = new Contribuinte();
		List<Contribuinte> resultados = new ArrayList<Contribuinte>();
		ConsultasContribuinte consulta = new ConsultasContribuinte();
		System.out.println("Consultando Todos contribuintes...");
		resultados = consulta.buscaTodos();
			for (int i = 0; i < resultados.size(); i++) {
		    	contribuinte = resultados.get(i);
				System.out.println(contribuinte.getRazaoSocial());
						     }
			
		 
		 
	    ConsultasContribuinte consulta2 = new ConsultasContribuinte();
		System.out.println("Buscado contribuinte por Cnpj...");
		boolean existe = false;
		existe = consulta2.buscaPorCnpj("63786222000100");
		System.out.println("Contribuinte existe no Banco de Dados? "+existe);
				
		
		 
		
		ConsultasContribuinte consulta3 = new ConsultasContribuinte();
		System.out.println("Adicionando contribuinte...");
		consulta3.adiciona("JOSIAS DE OLIVEIRA", "00000000187232", "22828099000157", "RUA PRINCIPAL, 200", "URUPA","RO");
	    System.out.println("Contribuinte adicionado com sucesso!!!");
			
		 
		  		
	 }
*/	 

}
