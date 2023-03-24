package uniandes.isis2304.parranderos.negocio;

public class Apartamento implements VOApartamento {
     /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de los departamentos
	 */
	private long idAlojamiento; 

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Apartamento() 
    {
    	this.idAlojamiento = 0;
	}

	/**
	 * Constructor con valores
	 * @param idAlojamiento - El id de la habitacion del apartamento
	 */
    public Apartamento(long idAlojamiento){
    	this.idAlojamiento = idAlojamiento;
	}

    /**
	 * @return El id del apartamento
	 */
	public long getIdAlojamiento() 
	{
		return idAlojamiento;
	}
	
	/**
	 * @param idAlojamiento - El nuevo id del apartamento
	 */
	public void setIdAlojamiento(long idAlojamiento) 
	{
		this.idAlojamiento = idAlojamiento;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del apartamento
	 */
	public String toString() 
	{
		return "Apartamento  [idAlojamiento=" + idAlojamiento + "]";
	}
}
