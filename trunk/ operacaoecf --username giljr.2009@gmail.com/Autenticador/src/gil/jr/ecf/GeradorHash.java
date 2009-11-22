package gil.jr.ecf;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @author Gil jr Data: 02/11/2009
 */ 

public class GeradorHash {

	public static byte[] digereMd5(String nomeArquivo) throws Exception {
		InputStream fis = new FileInputStream(nomeArquivo);

		byte[] buffer = new byte[1024];
		MessageDigest completo = MessageDigest.getInstance("MD5");
		int numLido;
		do {
			numLido = fis.read(buffer);
			if (numLido > 0) {
				completo.update(buffer, 0, numLido);
			}
		} while (numLido != -1);
		fis.close();
		return completo.digest();
	}

	public static String md5(String nomeArquivo) throws Exception {
		byte[] b = digereMd5(nomeArquivo);
		String md5 = "";
		for (int i = 0; i < b.length; i++) {
			md5 += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return md5;
	}

	public static byte[] digereSha1(String nomeArquivo) throws Exception {
		InputStream fis = new FileInputStream(nomeArquivo);

		byte[] buffer = new byte[1024];
		MessageDigest completo = MessageDigest.getInstance("SHA");
		int numLido;
		do {
			numLido = fis.read(buffer);
			if (numLido > 0) {
				completo.update(buffer, 0, numLido);
			}
		} while (numLido != -1);
		fis.close();
		return completo.digest();
	}

	public static String sha1(String nomeArquivo) throws Exception {
		byte[] b = digereSha1(nomeArquivo);
		String sha1 = "";
		for (int i = 0; i < b.length; i++) {
			sha1 += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return sha1;
	}

/*	 *
	 * Retorno de Controle:
	 * md5=9ff70c8814e0693e5a730ccef9beb313
     * sha1=4f353479753c4ebe3ddaa6b2b0b64f25218f1b88
	 *
	public static void main(String[] args) throws Exception {
		System.out.println("md5=" + md5("012087_20090821_120311.MFD"));
		System.out.println("sha1=" + sha1("012087_20090821_120311.MFD"));

	}
*/
	
}
