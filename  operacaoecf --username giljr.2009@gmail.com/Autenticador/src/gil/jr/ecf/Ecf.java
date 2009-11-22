package gil.jr.ecf;

/**
 * @author Gil jr Data: 02/11/2009
 */
public class Ecf {

	int codigo;
	String marca;
	String modelo;
	String nrAto;
	String nrPedido;	
	String nrFab;
	String nrGerado;
	String nrVersao;
	int codigoContribuinte;
		
	public Ecf() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	/**
	 * @param codigo chave primária
	 * @param marca Marca do Equipamento emissor 
	 * @param modelo Modelo 
	 * @param nrAto nº ato Cotepe
	 * @param nrPedido nº pedido uso/cessaão
	 * @param nrFab nº fabricação do equipamento
	 * @param nrGerado nº gerado pelo sitafe
	 * @param nrVersao nº da versão do equipamento no momento da fiscalização
	 */
	public Ecf(int codigo, String marca, String modelo, String nrAto,
			String nrPedido, String nrFab, String nrGerado, String nrVersao, int codigoContribuinte) {
		super();
		this.codigo = codigo;
		this.marca = marca;
		this.modelo = modelo;
		this.nrAto = nrAto;
		this.nrPedido = nrPedido;
		this.nrFab = nrFab;
		this.nrGerado = nrGerado;
		this.nrVersao = nrVersao;
		this.codigoContribuinte = codigoContribuinte;
		
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getMarca() {
		return marca;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public String getNrAto() {
		return nrAto;
	}
	
	public void setNrAto(String nrAto) {
		this.nrAto = nrAto;
	}
	
	public String getNrPedido() {
		return nrPedido;
	}
	
	public void setNrPedido(String nrPedido) {
		this.nrPedido = nrPedido;
	}
	
	public String getnrFab() {
		return nrFab;
	}
	
	public void setnrFab(String nrFab) {
		this.nrFab = nrFab;
	}
	
	public String getNrGerado() {
		return nrGerado;
	}
	
	public void setNrGerado(String nrGerado) {
		this.nrGerado = nrGerado;
	}
	
	public String getNrVersao() {
		return nrVersao;
	}
	
	public void setNrVersao(String nrVersao) {
		this.nrVersao = nrVersao;
	}
	
	public int getCodigoContribuinte() {
		return codigoContribuinte;
	}

	public void setCodigoContribuinte(int codigoContribuinte) {
		this.codigoContribuinte = codigoContribuinte;
	}

}
