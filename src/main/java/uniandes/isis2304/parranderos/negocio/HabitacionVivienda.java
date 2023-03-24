package uniandes.isis2304.parranderos.negocio;

public class HabitacionVivienda implements VOHabitacionVivienda {
     /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de las habitaciones de la vivienda
	 */
	private long idAlojamiento; 
	
	/**
	 * El tipo de baño de la habitacion de la vivienda
	 */
	private String tipoBano;

	/**
	 * El tipo de habitacion de la vivienda
	 */
	private String tipoHabitacion;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public HabitacionVivienda() 
    {
    	this.idAlojamiento = 0;
		this.tipoBano = "";
		this.tipoHabitacion = "";
	}

	/**
	 * Constructor con valores
	 * @param idAlojamiento - El id de la habitacion de la vivienda
	 * @param tipoBano - El tipo de baño de la habitacion de la vivienda
	 * @param tipoHabitacion - El tipo de habitacion de la vivienda
	 */
    public HabitacionVivienda(long idAlojamiento, String tipoBano, String tipoHabitacion){
    	this.idAlojamiento = idAlojamiento;
		this.tipoBano = tipoBano;
		this.tipoHabitacion = tipoHabitacion;
	}

    /**
	 * @return El id de la habitacion de la vivienda
	 */
	public long getIdAlojamiento() 
	{
		return idAlojamiento;
	}
	
	/**
	 * @param idAlojamiento - El nuevo id de la habitacion de la vivienda
	 */
	public void setIdAlojamiento(long idAlojamiento) 
	{
		this.idAlojamiento = idAlojamiento;
	}
	
	/**
	 * @return El tipo de baño de la habitacion de la vivienda
	 */
	public String getTipoBano() 
	{
		return tipoBano;
	}
	
	/**
	 * @param TipoBano El nuevo tipo de baño de la habitacion de la vivienda
	 */
	public void setTipoBano(String tipoBano) 
	{
		this.tipoBano = tipoBano;
	}
	
	/**
	 * @return El tipo de habitacion de la habitacion de la vivienda
	 */
	public String getTipoHabitacion() 
	{
		return tipoHabitacion;
	}
	
	/**
	 * @param tipoHabitacion - EL nuevo tipo de habitacion de la habitacion de la vivienda
	 */
	public void setTipoHabitacion(String tipoHabitacion) 
	{
		this.tipoHabitacion = tipoHabitacion;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la habitacion de la vivienda
	 */
	public String toString() 
	{
		return "HabitacionVivienda  [idAlojamiento=" + idAlojamiento + ", tipoBano=" + tipoBano + ", tipoHabitacion=" + tipoHabitacion + "]";
	}
}
