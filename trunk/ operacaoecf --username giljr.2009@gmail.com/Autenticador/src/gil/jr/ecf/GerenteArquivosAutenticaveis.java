package gil.jr.ecf;

/**
 * @author Gil jr 
 * Data: 02/11/2009
 * Vers�o: 0.1.GUI
 * Usado: Opera��o Ecf
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GerenteArquivosAutenticaveis {


	private static Logger informante = Logger.getLogger("GerenteArquivosAutenticaveis");
	private static final int SAIDA_ANORMAL = -1;
	//private static File dir_arq_autenticaveis;
	private static String[] arq_autenticaveis;


	/**Retorna o primeiro arquivo .MDB (MS Access) do diret�rio raiz**/
	public static String[] busca(File diretorio) {

		//dir_arq_autenticaveis = new File(diretorio);

		arq_autenticaveis = diretorio.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(".MFD")) {
					return true;
				}
				if (name.endsWith(".mfd")) {
					return true;
				}
				if (name.endsWith(".BIN")) {
					return true;
				}
				if (name.endsWith(".bin")) {
					return true;
				}
				if (name.endsWith(".MF")) {
					return true;
				}
				if (name.endsWith(".mf")) {
					return true;
				}
				if (name.endsWith(".TXT")) {
					return true;
				} else {
					return false;
				}
			}
		});
       if (arq_autenticaveis.length > 0)
    	   
			return arq_autenticaveis;
       
		try {
			throw new FileNotFoundException();
		} catch (FileNotFoundException e) {		
			String logMsg3 = "n�o h� arquivos no diret�rio escolhido...saindo do sistema!";
			informante.log(Level.SEVERE, logMsg3);
			e.printStackTrace();				
			System.exit(SAIDA_ANORMAL);	
			
			 
			}
		return arq_autenticaveis;
		
	}
		
}
