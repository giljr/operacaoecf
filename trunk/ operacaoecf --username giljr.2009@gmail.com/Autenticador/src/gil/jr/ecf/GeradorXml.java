package gil.jr.ecf;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.unidex.xflat.XflatException;
import com.unidex.xflat.XmlConvert;

/**
 * @author Gil jr Data: 20/11/2009
 * 
 * Converte arquivos .XML dos .TXT para leitura futura
 * Usa a biblioteca 'com.unidex.xFlat'
 */ 

public class GeradorXml {
	//assume os seguintes path e diretorios:
	private static String path = "Administracao";
	private static String dirArquivosTxt = "Sitafe";
	private static String dirArquivosXml = "Xml";
	
	static void converter(String arquivo_a_transformar) {
		String userdir = System.getProperty("user.dir");
		String arquivo_schema = "schema.xfl";
		String arquivo_txt = userdir + File.separator + path + File.separator +  dirArquivosTxt +File.separator+ arquivo_a_transformar;
		String arquivo_xml =userdir + File.separator + path + File.separator +  dirArquivosXml +File.separator+ arquivo_a_transformar.substring(0, 14) + ".xml";
		final boolean suppress_startup_message = true;
		try {
			// Cria uma instancia XmlConverter.
			XmlConvert xml_convert = new XmlConvert(new FileReader(
					arquivo_schema), suppress_startup_message);

			// Usa o método flatToXml do objeto xmlConverter
			// para converter o arquivo texto em XML.
			xml_convert.flatToXml(new FileReader(arquivo_txt), new FileWriter(
					arquivo_xml));
		} catch (Exception e) {
			if (e instanceof XflatException) {
				System.err.println(e.getMessage());
			} else {
				System.err.println(e.toString());
			}
		}

	}

}
