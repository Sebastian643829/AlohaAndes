package uniandes.isis2304.parranderos.negocio;

public class Hostal implements VOHostal {
     /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de las habitaciones de los hostales
	 */
	private long idAlojamiento; 
	
	/**
	 * El horario de apertura del hostal
	 */
	private String horarioApertura;

	/**
	 * El horario del cierre del hostal
	 */
	private String horarioCierre;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Hostal() 
    {
    	this.idAlojamiento = 0;
		this.horarioApertura = "";
		this.horarioCierre = "";
	}

	/**
	 * Constructor con valores
	 * @param idAlojamiento - El id de la habitacion del hostal
	 * @param horarioApertura- El horario de apertura del hostal
	 * @param horarioCierre - El horario del cierre del hostal
	 */
    public Hostal(long idAlojamiento, String horarioApertura, String horarioCierre){
    	this.idAlojamiento = idAlojamiento;
		this.horarioApertura = horarioApertura;
		this.horarioCierre = horarioCierre;
	}

    /**
	 * @return El id de la habitacion del hostal
	 */
	public long getIdAlojamiento() 
	{
		return idAlojamiento;
	}
	
	/**
	 * @param idAlojamiento - El nuevo id de la habitacion del hostal
	 */
	public void setIdAlojamiento(long idAlojamiento) 
	{
		this.idAlojamiento = idAlojamiento;
	}
	
	/**
	 * @return El horario de apertura del hostal
	 */
	public String getHorarioApertura() 
	{
		return horarioApertura;
	}
	
	/**
	 * @param horarioApertura El horario de apertura del hostal
	 */
	public void setHorarioApertura(String horarioApertura) 
	{
		this.horarioApertura = horarioApertura;
	}
	
	/**
	 * @return El horario del cierre del hostal
	 */
	public String getHorarioCierre() 
	{
		return horarioCierre;
	}
	
	/**
	 * @param horarioCierre - EL nuevo horario del cierre del hostal
	 */
	public void setHorarioCierre(String horarioCierre) 
	{
		this.horarioCierre = horarioCierre;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la habitacion del hostal
	 */
	public String toString() 
	{
		return "Hostal  [idAlojamiento=" + idAlojamiento + ", horarioApertura=" + horarioApertura + ", horarioCierre=" + horarioCierre + "]";
	}
}
