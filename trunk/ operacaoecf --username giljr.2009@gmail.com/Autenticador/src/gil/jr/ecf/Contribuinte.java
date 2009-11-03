package gil.jr.ecf;

/**
 * @author Gil jr 
 * Data: 02/11/2009
 * Versão: 0.1.GUI
 * Usado: Operação Ecf
 *  
 */

public class Contribuinte {
	
	private int codigo;
	private String razaoSocial;
	private String ie;
	private String cnpj;
	private String end;
	private String municipio;
	private String uf;
	
	
	
	/**
	 * 
	 */
	public Contribuinte() {
		super();		
	}


	/**
	 * @param codigo
	 * @param razaoSocial
	 * @param ie
	 * @param cnpj
	 * @param end
	 * @param municipio
	 * @param uf
	 */
	public Contribuinte(int codigo, String razaoSocial, String ie, String cnpj,
			String end, String municipio, String uf) {
		super();
		this.codigo = codigo;
		this.razaoSocial = razaoSocial;
		this.ie = ie;
		this.cnpj = cnpj;
		this.end = end;
		this.municipio = municipio;
		this.uf = uf;
	}





	public int getCodigo() {
		return codigo;
	}



	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}



	public String getRazaoSocial() {
		return razaoSocial;
	}



	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}



	

	public String getIe() {
		return ie;
	}



	public void setIe(String ie) {
		this.ie = ie;
	}



	public String getCnpj() {
		return cnpj;
	}



	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}



	public String getEnd() {
		return end;
	}



	public void setEnd(String end) {
		this.end = end;
	}



	public String getMunicipio() {
		return municipio;
	}



	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}



	public String getUf() {
		return uf;
	}



	public void setUf(String uf) {
		this.uf = uf;
	}
    

}
