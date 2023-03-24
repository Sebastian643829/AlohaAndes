package uniandes.isis2304.parranderos.negocio;

public class Operador implements VOOperador {
    /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de los operadores
	 */
	private long idOperador; 
	
	/**
	 * El telefono del operador
	 */
	private String telefono;

	/**
	 * El tipo de vinculacion del operador
	 */
	private String tipoVinculacion;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Operador() 
    {
    	this.idOperador = 0;
		this.telefono = "";
		this.tipoVinculacion = "";
	}

	/**
	 * Constructor con valores
	 * @param idOperador - El id del operador
	 * @param telefono - El telefono del operador
	 * @param tipoVinculacion - El tipo de vinculacion del operador
	 */
    public Operador(long idOperador, String telefono, String tipoVinculacion) 
    {
    	this.idOperador = idOperador;
		this.telefono = telefono;
		this.tipoVinculacion = tipoVinculacion;
	}

    /**
	 * @return El id del operador
	 */
	public long getIdOperador() 
	{
		return idOperador;
	}
	
	/**
	 * @param idOperador - El nuevo id del operador
	 */
	public void setIdOperador(long idOperador) 
	{
		this.idOperador = idOperador;
	}
	
	/**
	 * @return el telefono del operador
	 */
	public String getTelefono() 
	{
		return telefono;
	}
	
	/**
	 * @param telefono El nuevo telefono del operador
	 */
	public void setTelefono(String telefono) 
	{
		this.telefono = telefono;
	}
	
	/**
	 * @return la tipo de vinculacion del operador
	 */
	public String getTipoVinculacion() 
	{
		return tipoVinculacion;
	}
	
	/**
	 * @param tipoVinculacion - EL nuevo ciudad tipo de vinculacion del operador
	 */
	public void setTipoVinculacion(String tipoVinculacion) 
	{
		this.tipoVinculacion = tipoVinculacion;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del operador
	 */
	public String toString() 
	{
		return "Operador [idOperador=" + idOperador + ", telefono=" + telefono + ", tipoVinculacion=" + tipoVinculacion + "]";
	}
	
    
}
