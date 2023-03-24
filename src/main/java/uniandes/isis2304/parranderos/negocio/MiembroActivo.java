package uniandes.isis2304.parranderos.negocio;

public class MiembroActivo implements VOMiembroActivo {
    /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO del miembro activo
	 */
	private long idMiembroActivo; 

	/**
	 * El carnet del miembro activo
	 */
	private String carnet;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public MiembroActivo() 
    {
    	this.idMiembroActivo = 0;
		this.carnet = "";
	}

	/**
	 * Constructor con valores
	 * @param idMiembroActivo - El id del miembro activo
	 * @param carnet - El carnet del miembro activo
	 */
    public MiembroActivo(long idMiembroActivo, String carnet){
    	this.idMiembroActivo = idMiembroActivo;
		this.carnet = carnet;
	}

    /**
	 * @return El id del miembro activo
	 */
	public long getIdMiembroActivo() 
	{
		return idMiembroActivo;
	}
	
	/**
	 * @param idMiembroActivo - El nuevo id del miembro activo
	 */
	public void setIdMiembroActivo(long idMiembroActivo) 
	{
		this.idMiembroActivo = idMiembroActivo;
	}
	
	/**
	 * @return El carnet del miembro activo
	 */
	public String getCarnet() 
	{
		return carnet;
	}
	
	/**
	 * @param carnet - EL nuevo carnet del miembro activo
	 */
	public void setCarnet(String carnet) 
	{
		this.carnet = carnet;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del miembro activo
	 */
	public String toString() 
	{
		return "MiembroActivo  [idMiembroActivo=" + idMiembroActivo + ", carnet=" + carnet + "]";
	}
}
