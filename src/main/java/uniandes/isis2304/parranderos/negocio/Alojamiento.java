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
	private int ocupacionActual;

    /**
	 * El numero de reservas del alojamiento
	 */
	private int numReservas;

	/**
	 * El id del operador
	 */
	private long idOperador; 

	/**
	 *  El estado del alojamiento
	 */
	private String estado;

	/**
	 * El estado del tipo de alojamiento
	 */
	private String tipo;

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
        this.ocupacionActual = 0;
		this.numReservas = 0;
		this.idOperador = 0;
		this.estado = "";
		this.tipo = "";
	}

	/**
	 * Constructor con valores
	 * @param idAlojamiento - El id del alojamiento
	 * @param nombre - El nombre del alojamiento
	 * @param capacidad - La capacidad del alojamiento
     * @param ubicacion - La ubicacion del alojamiento
	 * @param tamano - El tamaño del alojamiento
	 * @param precioNoche - El precio de la noche del alojamiento
     * @param ocupacionActual - La ocupacion total actual del alojamiento
	 * @param numReservas - El numero de reservas del alojamiento
	 * @param idOperador - El id del operador
	 * @param estado - El estado del alojamiento
	 * @param tipo - El estado del tipo de alojamiento
	 */
    public Alojamiento(long idAlojamiento, String nombre, int capacidad ,String ubicacion, int tamano, int precioNoche, int ocupacionActual, int numReservas, long idOperador, String estado, String tipo) 
    {
    	this.idAlojamiento = idAlojamiento;
		this.nombre = nombre;
		this.capacidad = capacidad;
        this.ubicacion = ubicacion;
		this.tamano = tamano;
		this.precioNoche = precioNoche;
        this.ocupacionActual = ocupacionActual;
		this.numReservas = numReservas;
		this.idOperador = idOperador;
		this.estado = estado;
		this.tipo = tipo;
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
	public int getOcupacionActual() 
	{
		return ocupacionActual;
	}
	
	/**
	 * @param ocupacionActual - La nueva ocupacion total actual del alojamiento
	 */
	public void setOcupacionActual(int ocupacionActual) 
	{
		this.ocupacionActual = ocupacionActual;
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
	 * @return El id del operador
	 */
	public long getIdOperador() 
	{
		return idOperador;
	}
	
	/**
	 * @param idOperador - El nuevo id del operador
	 */
	public void setIdOperador(long idOperador) 
	{
		this.idOperador = idOperador;
	}

	/**
	 * @return El estado del alojamiento
	 */
	public String getEstado() 
	{
		return estado;
	}
	
	/**
	 * @param estado El nuevo estado del alojamiento
	 */
	public void setEstado(String estado) 
	{
		this.estado = estado;
	}

	/**
	 * @return El tipo del alojamiento
	 */
	public String getTipo() 
	{
		return tipo;
	}
	
	/**
	 * @param tipo El nuevo tipo del alojamiento
	 */
	public void setTipo(String tipo) 
	{
		this.tipo = tipo;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */ 
	public String toString() 
	{
		return "Alojamiento [idAlojamiento=" + idAlojamiento + ", nombre=" + nombre + ", capacidad=" + capacidad + 
        ", ubicacion=" + ubicacion + ", tamano=" + tamano + ", precioNoche=" + precioNoche 
        + ", ocupacionActual=" + ocupacionActual + ", numReservas=" + numReservas + ", idOperador=" + idOperador + ", estado=" + estado + ", tipo=" + tipo +  "]";
	}
}
