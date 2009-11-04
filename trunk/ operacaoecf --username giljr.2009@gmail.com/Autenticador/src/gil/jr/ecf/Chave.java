package gil.jr.ecf;


/**
 * @author Gil jr 
 * Data: 02/11/2009
 * Vers�o: 0.1.GUI
 * Usado: Opera��o Ecf
 */
/**Armazena o nome do arquivo autenticado, as chaves md5 e sha1**/
public class Chave {

	/**
	 * @param args
	 */
	String NomeArquivo;
	String md5;
	String sha1;
	

	
	
	

	/**
	 * @param nomeArquivo
	 * @param md5
	 * @param sah
	 */
	public Chave(String nomeArquivo, String md5, String sha1) {
		super();
		NomeArquivo = nomeArquivo;
		this.md5 = md5;
		this.sha1 = sha1;
	}

   
	
	
	public String getNomeArquivo() {
		return NomeArquivo;
	}



	public String getMd5() {
		return md5;
	}



	public String getSha1() {
		return sha1;
	}

   


	@Override
	public String toString() {
		return "Chave [NomeArquivo=" + NomeArquivo + ", md5=" + md5
				+ ", sha1=" + sha1 + "]";
	}


/*
	public static void main(String[] args) {
		String nomeArquivo = "BE050875610000020579_20090903_094611.MFD";
		String md5 = "b2e17bf4f0cd3384d3ef1f6021a5a77d";
		String sha1 = "8b578a52353a2d431069121c45470c69796c32d9";
		Chave chave = new Chave(nomeArquivo, md5, sha1);
		System.out.println(chave);

	}
	
*/
}
