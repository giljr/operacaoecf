package gil.jr.ecf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 @author Gil jr 
 * Data: 05/11/2009
 * Versão: 0.1.GUI
 * Usado: Operação Ecf
 *  
 * Gera  .zip
 */
public class GeradorZip {

	public void zip(String nomeArquivo) throws Exception {
		try {
			FileOutputStream fos = new FileOutputStream(nomeArquivo + ".zip");

			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));

			int size = 0;
			byte[] buffer = new byte[1024];

			System.out.println("Comprimindo: " + nomeArquivo);

			FileInputStream fis = new FileInputStream(nomeArquivo);
			new BufferedInputStream(fis, buffer.length);

			
			ZipEntry zipEntry = new ZipEntry(nomeArquivo);
			zos.putNextEntry(zipEntry);

			
			while ((size = fis.read(buffer, 0, buffer.length)) > 0) {
				zos.write(buffer, 0, size);
			}

			zos.closeEntry();
			fis.close();

			zos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/*
	public static void main(String[] args) throws Exception {
		GeradorZip geradorZip = new GeradorZip();
		geradorZip.zip("012087_20090821_120311.MFD");
	}
*/
}
