package uniandes.isis2304.parranderos.negocio;
import java.sql.Date;
import java.sql.Timestamp;

public class Cliente implements VOCliente {
    /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El id del cliente
	 */
	private long idCliente;
	
	/**
	 * El tipo de identificacion del cliente
	 */
	private String tipoIdentificacion;

    /**
	 * El nombre del cliente
	 */
	private String nombreCliente;
	
	/**
	 * La fecha de nacimiento del cliente
	 */
	private Date fechaNacimiento;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Cliente() 
	{
		this.idCliente = 0;
		this.tipoIdentificacion = "";
        this.nombreCliente = "";
		this.fechaNacimiento = new java.sql.Date(0);
	}

	/**
	 * Constructor con valores
	 * @param idCliente - El id del cliente
	 * @param tipoIdentificacion - El tipo de identificacion del cliente
	 * @param nombreCliente - El nombre del cliente
	 * @param fechaNacimiento - La fecha de nacimiento del cliente
	 */
	public Cliente(long idCliente, String tipoIdentificacion, String nombreCliente,  Date fechaNacimiento) 
	{
		this.idCliente = idCliente;
		this.tipoIdentificacion = tipoIdentificacion;
		this.nombreCliente = nombreCliente;
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * @return El id del cliente
	 */
	public long getIdCliente() 
	{
		return idCliente;
	}

	/**
	 * @param idCliente - El nuevo id del cliente
	 */
	public void setIdCliente(long idCliente) 
	{
		this.idCliente = idCliente;
	}

	/**
	 * @return El tipo de identificacion del cliente
	 */
	public String getTipoIdentificacion() 
	{
		return tipoIdentificacion;
	}

	/**
	 * @param tipoIdentificacion - El nuevo tipo de identificacion del cliente
	 */
	public void setTipoIdentificacion(String tipoIdentificacion) 
	{
		this.tipoIdentificacion = tipoIdentificacion;
	}

    /**
	 * @return El nombre del cliente
	 */
	public String getNombreCliente() 
	{
		return nombreCliente;
	}

	/**
	 * @param horario - El nombre del cliente
	 */
	public void setNombreCliente(String nombreCliente) 
	{
		this.nombreCliente = nombreCliente;
	}

	/**
	 * @return La fecha de nacimiento del cliente
	 */
	public Date getFechaNacimiento() 
	{
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento - La nueva fecha de nacimiento del cliente
	 */
	public void setFechaNacimiento(Date fechaNacimiento) 
	{
		this.fechaNacimiento = fechaNacimiento;
	}

	/** 
	 * @return Una cadena con la información básica del cliente
	 */
	@Override
	public String toString() 
	{
		return "Cliente [idCliente=" + idCliente + ", tipoIdentificacion=" + tipoIdentificacion + ", nombreCliente=" + nombreCliente + ", fechaNacimiento="
				+ fechaNacimiento + "]";
	}
}
