package uniandes.isis2304.parranderos.negocio;

public class HabitacionHotel implements VOHabitacionHotel {
     /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de las habitaciones del hotel
	 */
	private long idAlojamiento; 

	/**
	 * El tipo de habitacion del hotel
	 */
	private String tipoHabitacion;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public HabitacionHotel() 
    {
    	this.idAlojamiento = 0;
		this.tipoHabitacion = "";
	}

	/**
	 * Constructor con valores
	 * @param idAlojamiento - El id de la habitacion del hotel
	 * @param tipoHabitacion - El tipo de habitacion del hotel
	 */
    public HabitacionHotel(long idAlojamiento, String tipoHabitacion){
    	this.idAlojamiento = idAlojamiento;
		this.tipoHabitacion = tipoHabitacion;
	}

    /**
	 * @return El id de la habitacion del hotel
	 */
	public long getIdAlojamiento() 
	{
		return idAlojamiento;
	}
	
	/**
	 * @param idAlojamiento - El nuevo id de la habitacion del hotel
	 */
	public void setIdAlojamiento(long idAlojamiento) 
	{
		this.idAlojamiento = idAlojamiento;
	}
	
	/**
	 * @return El tipo de habitacion de la habitacion del hotel
	 */
	public String getTipoHabitacion() 
	{
		return tipoHabitacion;
	}
	
	/**
	 * @param tipoHabitacion - EL nuevo tipo de habitacion del hotel
	 */
	public void setTipoHabitacion(String tipoHabitacion) 
	{
		this.tipoHabitacion = tipoHabitacion;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la habitacion del hotel
	 */
	public String toString() 
	{
		return "HabitacionHotel  [idAlojamiento=" + idAlojamiento + ", tipoHabitacion=" + tipoHabitacion + "]";
	}
}
