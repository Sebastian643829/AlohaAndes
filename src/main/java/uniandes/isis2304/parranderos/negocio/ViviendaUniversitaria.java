package uniandes.isis2304.parranderos.negocio;

public class ViviendaUniversitaria implements VOViviendaUniversitaria {
     /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de las viviendas univesitarias
	 */
	private long idAlojamiento; 
	
	/**
	 * El tipo de habitacion de la vivienda universitaria
	 */
	private String tipoHabitacion;


	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public ViviendaUniversitaria() 
    {
    	this.idAlojamiento = 0;
		this.tipoHabitacion = "";
	}

	/**
	 * Constructor con valores
	 * @param idAlojamiento - El id de la vivienda universitaria
	 * @param tipoHabitacion - El tipo de habitacion de la vivienda universitaria
	 */
    public ViviendaUniversitaria(long idAlojamiento, String tipoHabitacion) 
    {
    	this.idAlojamiento = idAlojamiento;
		this.tipoHabitacion = tipoHabitacion;
	}

    /**
	 * @return  El id de la vivienda universitaria
	 */
	public long getIdAlojamiento() 
	{
		return idAlojamiento;
	}
	
	/**
	 * @param idAlojamiento- El nuevo id de la vivienda universitaria
	 */
	public void setIdAlojamiento(long idAlojamiento) 
	{
		this.idAlojamiento= idAlojamiento;
	}
	
	/**
	 * @return El tipo de habitacion de la vivienda universitariar
	 */
	public String getTipoHabitacion() 
	{
		return tipoHabitacion;
	}
	
	/**
	 * @param TipoHabitacion El nuevo tipo de habitacion de la vivienda universitaria
	 */
	public void setTipoHabitacion(String tipoHabitacion) 
	{
		this.tipoHabitacion = tipoHabitacion;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la vivienda universitaria
	 */
	public String toString() 
	{
		return "ViviendaUniversitaria [idAlojamiento=" + idAlojamiento + ", tipoHabitacion=" + tipoHabitacion + "]";
	}
}
