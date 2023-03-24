package uniandes.isis2304.parranderos.negocio;

public class Propietario implements VOPropietario {
     /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de los propietarios
	 */
	private long idOperador; 
	
	/**
	 * La identificacion del propietario
	 */
	private String identificacion;

	/**
	 * El tipo de identificacion del propietario
	 */
	private String tipoIdentificacion;

    /**
	 * El nombre del propietario
	 */
	private String nombrePropietario;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Propietario() 
    {
    	this.idOperador = 0;
		this.identificacion = "";
		this.tipoIdentificacion = "";
        this.nombrePropietario = "";
	}

	/**
	 * Constructor con valores
	 * @param idOperador - El id del propietario
	 * @param identificacion - La identificacion del propietario
	 * @param tipoIdentificacion - El tipo de identificacion del propietario
     * @param nombrePropietario - El nombre del propietario
	 */
    public Propietario(long idOperador, String identificacion, String tipoIdentificacion, String nombrePropietario) 
    {
    	this.idOperador = idOperador;
		this.identificacion = identificacion;
		this.tipoIdentificacion = tipoIdentificacion;
        this.nombrePropietario = nombrePropietario;
	}

    /**
	 * @return El id del propietario
	 */
	public long getIdOperador() 
	{
		return idOperador;
	}
	
	/**
	 * @param idOperador - El nuevo id del propietario
	 */
	public void setIdOperador(long idOperador) 
	{
		this.idOperador = idOperador;
	}
	
	/**
	 * @return La identificacion del propietario
	 */
	public String getIdentificacion() 
	{
		return identificacion;
	}
	
	/**
	 * @param identificacion la nueva identificacion del propietario
	 */
	public void setIdentificacion(String identificacion) 
	{
		this.identificacion = identificacion;
	}
	
	/**
	 * @return El tipo de identificacion del propietario
	 */
	public String getTipoIdentificacion() 
	{
		return tipoIdentificacion;
	}
	
	/**
	 * @param tipoIdentificacion- EL nuevo tipo de identificacion del propietario
	 */
	public void setTipoIdentificacion(String tipoIdentificacion) 
	{
		this.tipoIdentificacion = tipoIdentificacion;
	}

    /**
	 * @return El nombre del propietario
	 */
	public String getNombrePropietario() 
	{
		return nombrePropietario;
	}
	
	/**
	 * @param nombrePropietario- EL nuevo nombre del propietario
	 */
	public void setNombrePropietario(String nombrePropietario) 
	{
		this.nombrePropietario = nombrePropietario;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del propietario
	 */
	public String toString() 
	{
		return "Propietario [idOperador=" + idOperador + ", telefono=" + identificacion + ", tipoIdentificacion=" + tipoIdentificacion + 
        ", nombrePropietario=" + nombrePropietario + "]";
	}
	
}
