package uniandes.isis2304.parranderos.negocio;

public class Empresa implements VOEmpresa {
     /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de las empresas
	 */
	private long idOperador; 
	
	/**
	 * El NIT de la empresa
	 */
	private String nit;

	/**
	 * El nombre de la empresa
	 */
	private String nombreEmpresa;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Empresa() 
    {
    	this.idOperador = 0;
		this.nit = "";
		this.nombreEmpresa = "";
	}

	/**
	 * Constructor con valores
	 * @param idOperador - El id de la empresa
	 * @param nit - El NIT de la empresa
	 * @param nombreEmpresa - El nombre de la empresa
	 */
    public Empresa(long idOperador, String nit, String nombreEmpresa) 
    {
    	this.idOperador = idOperador;
		this.nit = nit;
		this.nombreEmpresa = nombreEmpresa;
	}

    /**
	 * @return El id de la empresa
	 */
	public long getIdOperador() 
	{
		return idOperador;
	}
	
	/**
	 * @param idOperador - El nuevo id de la empresa
	 */
	public void setIdOperador(long idOperador) 
	{
		this.idOperador = idOperador;
	}
	
	/**
	 * @return el NIT de la empresa
	 */
	public String getNit() 
	{
		return nit;
	}
	
	/**
	 * @param nit El nuevo telefono del operador
	 */
	public void setNit(String nit) 
	{
		this.nit = nit;
	}
	
	/**
	 * @return el nombre de la empresa
	 */
	public String getNombreEmpresa() 
	{
		return nombreEmpresa;
	}
	
	/**
	 * @param nombreEmpresa - EL nuevo nombre de la empresa
	 */
	public void setTipoVinculacion(String nombreEmpresa) 
	{
		this.nombreEmpresa = nombreEmpresa;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la empresa
	 */
	public String toString() 
	{
		return "Empresa [idOperador=" + idOperador + ", nit=" + nit + ", nombreEmpresa=" + nombreEmpresa + "]";
	}
}
