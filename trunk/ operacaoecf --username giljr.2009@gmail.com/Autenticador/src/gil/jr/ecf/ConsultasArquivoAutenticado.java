package gil.jr.ecf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gil Jr Data: 31/10/2009 
 *            
 *            Revisões: 20/11/2009 - implementação novo 'GerenteConexao';  
 */

/**Objeto de acesso a dados (DAO) da tabela ArquivoAutenticado*/
public class ConsultasArquivoAutenticado {

	private static Logger informante = Logger.getLogger("ConsultasArquivoAutenticado");
	private static final int SAIDA_ANORMAL = - 1;

	private static PreparedStatement adicionaChaves = null;
	private String ADICIONA_CHAVES = "INSERT INTO ArquivoAutenticado (NomeArquivo_1, md5_1, sha_1, " +
			"NomeArquivo_2, md5_2, sha_2, NomeArquivo_3, md5_3, sha_3, NomeArquivo_4, md5_4, sha_4, " +
			"CodigoContribuinte, CodigoEcf) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private GerenteConexao gerente;
	private Connection conexao;
	private boolean temosConexao = false;

	/** Construtor **/
	public ConsultasArquivoAutenticado(GerenteConexao gerente) {
		this.gerente = gerente;
		try {
			conexao = gerente.buscaConexaoPeloPoll();
			
			temosConexao = true;

			adicionaChaves = conexao.prepareStatement(ADICIONA_CHAVES);

		} catch (SQLException e) {
			temosConexao = false;
			String logMsg = "Falha no contrutor! saindo do sistema...";
			informante.log(Level.SEVERE, logMsg);
			e.printStackTrace();
			gerente.retornaConexaoParaPoll(conexao);
			System.exit(SAIDA_ANORMAL);
		}
	}

	/** Adiciona as chaves a tabela ArquivoAutenticadodo banco de dados MS Access **/
	 int adicionaChaves(ArrayList<Chave> chaveiro,
			int codigoEmpresa, int codigoEcf) throws SQLException {
		int SAIDA_NORMAL = 1;
		int SAIDA_ANORMAL = -1;

		int UM_ARQUIVO = 1;
		int DOIS_ARQUIVOS = 2;
		int TRES_ARQUIVOS = 3;
		int QUATRO_ARQUIVOS = 4;

		if (temosConexao) {

			try {
				if (chaveiro.isEmpty()) {
					return SAIDA_ANORMAL;
				} else if (chaveiro.size() == QUATRO_ARQUIVOS) {					
					adicionaChaves.setString(1, chaveiro.get(0).getNomeArquivo());
					adicionaChaves.setString(2, chaveiro.get(0).getMd5());
					adicionaChaves.setString(3, chaveiro.get(0).getSha1());
					adicionaChaves.setString(4, chaveiro.get(1).getNomeArquivo());
					adicionaChaves.setString(5, chaveiro.get(1).getMd5());
					adicionaChaves.setString(6, chaveiro.get(1).getSha1());
					adicionaChaves.setString(7, chaveiro.get(2).getNomeArquivo());
					adicionaChaves.setString(8, chaveiro.get(2).getMd5());
					adicionaChaves.setString(9, chaveiro.get(2).getSha1());
					adicionaChaves.setString(10, chaveiro.get(3).getNomeArquivo());
					adicionaChaves.setString(11, chaveiro.get(3).getMd5());
					adicionaChaves.setString(12, chaveiro.get(3).getSha1());
					adicionaChaves.setInt(13, codigoEmpresa);
					adicionaChaves.setInt(14, codigoEcf);
					SAIDA_NORMAL = adicionaChaves.executeUpdate();
					
				} else if (chaveiro.size() == TRES_ARQUIVOS) {					
					adicionaChaves.setString(1, chaveiro.get(0).getNomeArquivo());
					adicionaChaves.setString(2, chaveiro.get(0).getMd5());
					adicionaChaves.setString(3, chaveiro.get(0).getSha1());
					adicionaChaves.setString(4, chaveiro.get(1).getNomeArquivo());
					adicionaChaves.setString(5, chaveiro.get(1).getMd5());
					adicionaChaves.setString(6, chaveiro.get(1).getSha1());
					adicionaChaves.setString(7, chaveiro.get(2).getNomeArquivo());
					adicionaChaves.setString(8, chaveiro.get(2).getMd5());
					adicionaChaves.setString(9, chaveiro.get(2).getSha1());
					adicionaChaves.setString(10, "N/A");
					adicionaChaves.setString(11, "N/A");
					adicionaChaves.setString(12, "N/A");
					adicionaChaves.setInt(13, codigoEmpresa);
					adicionaChaves.setInt(14, codigoEcf);
					SAIDA_NORMAL = adicionaChaves.executeUpdate();

				} else if (chaveiro.size() == DOIS_ARQUIVOS) {
					adicionaChaves.setString(1, chaveiro.get(0).getNomeArquivo());
					adicionaChaves.setString(2, chaveiro.get(0).getMd5());
					adicionaChaves.setString(3, chaveiro.get(0).getSha1());
					adicionaChaves.setString(4, chaveiro.get(1).getNomeArquivo());
					adicionaChaves.setString(5, chaveiro.get(1).getMd5());
					adicionaChaves.setString(6, chaveiro.get(1).getSha1());
					adicionaChaves.setString(7, "N/A");
					adicionaChaves.setString(8, "N/A");
					adicionaChaves.setString(9, "N/A");
					adicionaChaves.setString(10, "N/A");
					adicionaChaves.setString(11, "N/A");
					adicionaChaves.setString(12, "N/A");
					adicionaChaves.setInt(13, codigoEmpresa);
					adicionaChaves.setInt(14, codigoEcf);
					SAIDA_NORMAL = adicionaChaves.executeUpdate();

				} else if (chaveiro.size() == UM_ARQUIVO) {
					adicionaChaves.setString(1, chaveiro.get(0).getNomeArquivo());
					adicionaChaves.setString(2, chaveiro.get(0).getMd5());
					adicionaChaves.setString(3, chaveiro.get(0).getSha1());
					adicionaChaves.setString(4, "N/A");
					adicionaChaves.setString(5, "N/A");
					adicionaChaves.setString(6, "N/A");
					adicionaChaves.setString(7, "N/A");
					adicionaChaves.setString(8, "N/A");
					adicionaChaves.setString(9, "N/A");
					adicionaChaves.setString(10, "N/A");
					adicionaChaves.setString(11, "N/A");
					adicionaChaves.setString(12, "N/A");
					adicionaChaves.setInt(13, codigoEmpresa);
					adicionaChaves.setInt(14, codigoEcf);
					SAIDA_NORMAL = adicionaChaves.executeUpdate();

				}

			} catch (SQLException e) {
				String logMsg = "Falha ao adicionar na tabela ArquivoAutenticados!" +
						" Saindo...";
				informante.log(Level.SEVERE, logMsg);
				e.printStackTrace();
				return SAIDA_ANORMAL;

			} finally {
				gerente.retornaConexaoParaPoll(conexao);
			}
		} else {
			String logMsg = "Não Temos conexao! Saindo do sistema...";
			informante.log(Level.SEVERE, logMsg);
			System.exit(SAIDA_ANORMAL);
		}
		return SAIDA_NORMAL;

	}

/*
  public static void main(String[] args) throws SQLException {
	  	  
	  ConsultasArquivoAutenticado consulta3 = new
	  ConsultasArquivoAutenticado();
	  System.out.println("Adicionando na tabela ArquivoAutenticado...");
	  ArrayList<Chave> chaveiro = new ArrayList<Chave>();
	  chaveiro.add(new Chave("Arq_1","md5_1","sha_1"));
	  chaveiro.add(new Chave("Arq_2","md5_2","sha_2"));
	  chaveiro.add(new Chave("Arq_3","md5_3","sha_3"));
	  chaveiro.add(new Chave("Arq_4","md5_4","sha_4"));
	  consulta3.adicionaChaves(chaveiro, 2, 50);
	  System.out.println("Chaves adicionadas com sucesso na tabela ArquivoAutenticado!!!");
	  	  
	  }
	 
*/
	
}
