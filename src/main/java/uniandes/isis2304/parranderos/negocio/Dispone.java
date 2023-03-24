package uniandes.isis2304.parranderos.negocio;

public class Dispone implements VODispone {
    /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO del servicio
	 */
	private long idServicio; 

	/**
	 * El id del alojamiento
	 */
	private long idAlojamiento;


	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Dispone() 
    {
    	this.idServicio = 0;
		this.idAlojamiento = 0;
	}

	/**
	 * Constructor con valores
	 * @param idServicio - El id del servicio
	 * @param nombre - El id del alojamiento
	 */
    public Dispone(long idServicio, long idAlojamiento){
    	this.idServicio = idServicio;
		this.idAlojamiento = idAlojamiento;
	}

    /**
	 * @return El id del servicio
	 */
	public long getIdServicio() 
	{
		return idServicio;
	}
	
	/**
	 * @param idServicio - El nuevo id del servicio
	 */
	public void setIdServicio(long idServicio) 
	{
		this.idServicio = idServicio;
	}
	
	/**
	 * @return El id del alojamiento
	 */
	public long getIdAlojamiento() 
	{
		return idAlojamiento;
	}
	
	/**
	 * @param idAlojamiento - EL nuevo id del alojamiento
	 */
	public void setIdAlojamiento(long idAlojamiento) 
	{
		this.idAlojamiento = idAlojamiento;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la tabla de dispone
	 */
	public String toString() 
	{
		return "Dispone  [idServicio=" + idServicio + ", idAlojamiento=" + idAlojamiento + "]";
	}
}
