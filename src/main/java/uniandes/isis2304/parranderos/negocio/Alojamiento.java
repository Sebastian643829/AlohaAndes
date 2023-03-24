package uniandes.isis2304.parranderos.negocio;

public class Alojamiento implements VOAlojamiento {
     /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de los alojamientos
	 */
	private long idAlojamiento; 
	
	/**
	 * El nombre del alojamiento
	 */
	private String nombre;

	/**
	 * La capacidad del alojamiento
	 */
	private int capacidad;

    /**
	 * La ubicacion del alojamiento
	 */
	private String ubicacion;

	/**
	 * El tamaño del alojamiento
	 */
	private int tamano;

    /**
	 * El precio de la noche del alojamiento
	 */
	private int precioNoche;

	/**
	 * La ocupacion total actual del alojamiento
	 */
	private int ocupacionTotal;

    /**
	 * El numero de reservas del alojamiento
	 */
	private int numReservas;

	/**
	 * El identificador ÚNICO de los alojamientos
	 */
	private long idOperador; 

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Alojamiento() 
    {
    	this.idAlojamiento = 0;
		this.nombre = "";
		this.capacidad = 0;
        this.ubicacion = "";
		this.tamano = 0;
		this.precioNoche = 0;
        this.ocupacionTotal = 0;
		this.numReservas = 0;
		this.idOperador = 0;
	}

	/**
	 * Constructor con valores
	 * @param idAlojamiento - El id del alojamiento
	 * @param nombre - El nombre del alojamiento
	 * @param capacidad - La capacidad del alojamiento
     * @param ubicacion - La ubicacion del alojamiento
	 * @param tamano - El tamaño del alojamiento
	 * @param precioNoche - El precio de la noche del alojamiento
     * @param ocupacionTotal - La ocupacion total actual del alojamiento
	 * @param numReservas - El numero de reservas del alojamiento
	 * @param idOperador - El id del alojamiento
	 */
    public Alojamiento(long idAlojamiento, String nombre, int capacidad ,String ubicacion, int tamano, int precioNoche, int ocupacionTotal, int numReservas, long idOperador) 
    {
    	this.idAlojamiento = idAlojamiento;
		this.nombre = nombre;
		this.capacidad = capacidad;
        this.ubicacion = ubicacion;
		this.tamano = tamano;
		this.precioNoche = precioNoche;
        this.ocupacionTotal = ocupacionTotal;
		this.numReservas = numReservas;
		this.idOperador = idOperador;
	}

    /**
	 * @return El id del alojamiento
	 */
	public long getIdAlojamiento() 
	{
		return idAlojamiento;
	}
	
	/**
	 * @param idAlojamiento - El nuevo id del alojamiento
	 */
	public void setIdAlojamiento(long idAlojamiento) 
	{
		this.idAlojamiento = idAlojamiento;
	}
	
	/**
	 * @return El nombre del alojamiento
	 */
	public String getNombre() 
	{
		return nombre;
	}
	
	/**
	 * @param nombre El nuevo nombre del alojamiento
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	
	/**
	 * @return La capacidad del alojamiento
	 */
	public int getCapacidad() 
	{
		return capacidad;
	}
	
	/**
	 * @param capacidad - La nueva capacidad del alojamientor
	 */
	public void setCapacidad(int capacidad) 
	{
		this.capacidad = capacidad;
	}

    /**
	 * @return La ubicacion del alojamiento
	 */
	public String getUbicacion() 
	{
		return ubicacion;
	}
	
	/**
	 * @param ubicacion - La nueva ubicacion del alojamiento
	 */
	public void setUbicacion(String ubicacion) 
	{
		this.ubicacion = ubicacion;
	}

    /**
	 * @return El tamaño del alojamiento
	 */
	public int getTamano() 
	{
		return tamano;
	}
	
	/**
	 * @param tamano - EL nuevo tamaño del alojamiento
	 */
	public void setTamano(int tamano) 
	{
		this.tamano= tamano;
	}

    /**
	 * @return El precio de la noche del alojamiento
	 */
	public int getPrecioNoche() 
	{
		return precioNoche;
	}
	
	/**
	 * @param precioNoche - EL precio de la noche del alojamiento
	 */
	public void setPrecioNoche(int precioNoche) 
	{
		this.precioNoche = precioNoche;
	}

    /**
	 * @return La ocupacion total actual del alojamiento
	 */
	public int getOcupacionTotal() 
	{
		return ocupacionTotal;
	}
	
	/**
	 * @param ocupacionTotal - La nueva ocupacion total actual del alojamiento
	 */
	public void setOcupacionTotal(int ocupacionTotal) 
	{
		this.ocupacionTotal = ocupacionTotal;
	}

    /**
	 * @return El numero de reservas del alojamiento
	 */
	public int getNumReservas() 
	{
		return numReservas;
	}
	
	/**
	 * @param numReservas - EL nuevo numero de reservas del alojamientodor
	 */
	public void setNumReservas(int numReservas) 
	{
		this.numReservas = numReservas;
	}

	/**
	 * @return El id del alojamiento
	 */
	public long getIdOperador() 
	{
		return idOperador;
	}
	
	/**
	 * @param idOperador - El nuevo id del alojamiento
	 */
	public void setIdOperador(long idOperador) 
	{
		this.idOperador = idOperador;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */ 
	public String toString() 
	{
		return "Alojamiento [idAlojamiento=" + idAlojamiento + ", nombre=" + nombre + ", capacidad=" + capacidad + 
        ", ubicacion=" + ubicacion + ", tamano=" + tamano + ", precioNoche=" + precioNoche 
        + ", ocupacionTotal=" + ocupacionTotal + ", numReservas=" + numReservas + ", idOperador=" + idOperador + "]";
	}
}
