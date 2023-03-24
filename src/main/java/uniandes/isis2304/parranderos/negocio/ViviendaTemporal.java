package uniandes.isis2304.parranderos.negocio;

public class ViviendaTemporal implements VOViviendaTemporal {
     /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la vivienda temporal
	 */
	private long idAlojamiento; 
	
	/**
	 * El numero de habitaciones de la vivienda temporal
	 */
	private int numHabitaciones;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public ViviendaTemporal() 
    {
    	this.idAlojamiento = 0;
		this.numHabitaciones = 0;
	}

	/**
	 * Constructor con valores
	 * @param idAlojamiento - El id de la vivienda temporal
	 * @param numHabitaciones - El numero de habitaciones de la vivienda temporal
	 */
    public ViviendaTemporal(long idAlojamiento, int numHabitaciones){
    	this.idAlojamiento = idAlojamiento;
		this.numHabitaciones = numHabitaciones;
	}

    /**
	 * @return El id de la vivienda temporal
	 */
	public long getIdAlojamiento() 
	{
		return idAlojamiento;
	}
	
	/**
	 * @param idAlojamiento - El nuevo id de la vivienda temporal
	 */
	public void setIdAlojamiento(long idAlojamiento) 
	{
		this.idAlojamiento = idAlojamiento;
	}
	
	/**
	 * @return El numero de habitaciones de la vivienda temporal
	 */
	public int getNumHabitaciones() 
	{
		return numHabitaciones;
	}
	
	/**
	 * @param TipoBano El nuevo numero de habitaciones de la vivienda temporal
	 */
	public void setNumHabitaciones(int numHabitaciones) 
	{
		this.numHabitaciones = numHabitaciones;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la habitacion de la vivienda
	 */
	public String toString() 
	{
		return "ViviendaTemporal  [idAlojamiento=" + idAlojamiento + ", numHabitaciones=" + numHabitaciones + "]";
	}
}
