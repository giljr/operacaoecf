package gil.jr.ecf;

/**
 * @author Gil jr Data: 02/11/2009
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

	public Contribuinte() {
		super();		
	}

	/**
	 * @param codigo chave primaria
	 * @param razaoSocial nome da Razão Social
	 * @param ie Inscrição Estadual com 14 dígitos (sem formatação)
	 * @param cnpj Cnpj co 14 dígitos (sem formatação)
	 * @param end Endereço incluindo número
	 * @param municipio Municipio
	 * @param uf Unidade da Federação
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
