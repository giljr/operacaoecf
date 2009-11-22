package gil.jr.ecf;

/**
 * @author Gil Jr Data: 10/11/2009
 *       
 *            Revisões: 15/11/2009 - metodo muda diretorio
 *            
 *Gerencia arquivos .TXT e .XML, 
 *recuperando-os e trocando-os de diretório
 */

import java.io.File;
import java.io.FilenameFilter;


public class GerenteArquivos {

	private static File dir_sitafe;
	static String[] arquivos_a_transformar;
	private static File dir_xml;
	private static String[] arquivos_xml;

	/**Recupera todos os arquivos .TXT do diretorio especificado*/
	public static String[] buscaTodosTxt(String path, String diretorio) {
		String userdir = System.getProperty("user.dir");
		String dirTxt = userdir+File.separator+path+File.separator+File.separator+diretorio;
        dir_sitafe = new File(dirTxt);
		arquivos_a_transformar = dir_sitafe.list(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					if (name.endsWith(".TXT")) {
						return true;
					}
					if (name.endsWith(".txt")) {
						return true;
					} else {
						return false;
					}
				}
			});
		
		if (arquivos_a_transformar.length > 0)
			return arquivos_a_transformar;
		return null;
	}

	/**Recupera todos os arquivos .XML do diretorio especificado*/
	public static String[] buscaTodosXml(String path, String diretorio) {
		String userdir = System.getProperty("user.dir");
		String dirXml = userdir+File.separator+path+File.separator+File.separator+diretorio;   
		dir_xml = new File(dirXml);
		arquivos_xml = dir_xml.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(".XML")) {
					return true;
				}
				if (name.endsWith(".xml")) {
					return true;
				} else {
					return false;
				}
			}
		});
		if (arquivos_xml.length > 0)
			return arquivos_xml;
		return null;
	}
	
    /**muda arquivo a arquivo de diretorio*/
	public static boolean mudaDiretorio(File arquivo, String novoDir) {
		File arqAntigo = arquivo;
		File destino = new File(novoDir);
		return arquivo.renameTo(new File(destino, arqAntigo.getName()));
	}
	
}
