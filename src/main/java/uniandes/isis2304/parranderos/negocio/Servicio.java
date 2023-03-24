package uniandes.isis2304.parranderos.negocio;

public class Servicio implements VOServicio {
     /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO del servicio
	 */
	private long idServicio; 

	/**
	 * El nombre del servicio
	 */
	private String nombre;

    /**
	 * El carnet del miembro activo
	 */
	private int costo;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Servicio() 
    {
    	this.idServicio = 0;
		this.nombre = "";
        this.costo = 0;
	}

	/**
	 * Constructor con valores
	 * @param idServicio - El id del servicio
	 * @param nombre - El nombre del servicio
     * @param costo - El costo del servicio
	 */
    public Servicio(long idServicio, String nombre, int costo){
    	this.idServicio = idServicio;
		this.nombre = nombre;
        this.costo = costo;
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
	 * @return El nombre del servicio
	 */
	public String getNombre() 
	{
		return nombre;
	}
	
	/**
	 * @param nombre - EL nuevo nombre del servicio
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

    /**
	 * @return El costo del servicio
	 */
	public int getCosto() 
	{
		return costo;
	}
	
	/**
	 * @param costo - EL nuevo costo del servicio
	 */
	public void setCosto(int costo) 
	{
		this.costo = costo;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del servicio
	 */
	public String toString() 
	{
		return "Servicio  [idServicio=" + idServicio + ", nombre=" + nombre + ", costo=" + costo + "]";
	}
}
