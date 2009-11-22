package gil.jr.ecf;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Gil Jr Data: 15/11/2009
 * 
 * Responsavel por ler .XML e tranforma-lo (JAXB)
 */

public class LeitorXml {

	private static RelatorioSitafe relatorioSitafe = null;

	public static RelatorioSitafe criaRelatorioSitafeDoXml(String path, String arquivo_xml) {
		JAXBContext ctx = null;
		try {
			ctx = JAXBContext.newInstance(RelatorioSitafe.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		Unmarshaller unmarshaller = null;
		try {
			unmarshaller = ctx.createUnmarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		try {
			relatorioSitafe = (RelatorioSitafe) unmarshaller.unmarshal(new File(path, arquivo_xml));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return relatorioSitafe;
	}

}
