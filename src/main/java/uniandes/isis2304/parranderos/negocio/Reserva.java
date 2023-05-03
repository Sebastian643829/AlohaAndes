package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;


public class Reserva implements VOReserva {
    /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El id de la reserva
	 */
	private long idReserva;
	/**
	 * El identificador del bebedor que realiza la visita
	 */
	private long idAlojamiento;
	
	/**
	 * El id del cliente
	 */
	private long idCliente;

    /**
	 * La duracion de la reserva
	 */
	private int duracion;
	
	/**
	 * La fecha de inicio de la reserva
	 */
	private Date fechaInicio;

    /**
	 * La fecha de la final de la reserva
	 */
	private Date fechaFinal;

    /**
	 * El costo total de la reserva
	 */
	private long costoTotal;
	
	/**
	 * El estado de la reserva
	 */
	private String estado;

    /**
	 * El numero de personas de la reserva
	 */
	private int numPersonas;

	 /**
	 * El id de la reserva colectiva
	 */
	private long idReservaColectiva;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Reserva() 
	{
		this.idReserva = 0 ;
		this.idAlojamiento = 0;
		this.idCliente = 0;
		this.duracion = 0;
		this.fechaInicio = new java.sql.Date(0);
        this.fechaFinal = new java.sql.Date(0);
        this.costoTotal = 0;
        this.estado = "";
        this.numPersonas = 0;
		this.idReservaColectiva = 0;
	}

	/**
	 * Constructor con valores
	 * @param idReserva - El id de la reserva
	 * @param idAlojamiento - El id del alojamiento
	 * @param idCliente - El id del cliente
	 * @param duracion - La duracion de la reserva
	 * @param fechaInicio - La fecha de inicio de la reserva
     * @param fechaFinal - La fecha de la final de la reserva
	 * @param costoTotal - El costo total de la reserva
	 * @param estado - El estado de la reserva
	 * @param numPersonas - El numero de personas de la reserva
	 * @param idReservaColectiva - El id de la reserva colectiva
	 */
	public Reserva(long idReserva,long idAlojamiento, long idCliente, int duracion, Date fechaInicio, Date fechaFinal, long costoTotal, String estado, int numPersonas, long idReservaColectiva) 
	{
		this.idReserva = idReserva;
		this.idAlojamiento = idAlojamiento;
		this.idCliente = idCliente;
		this.duracion = duracion;
		this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
		this.costoTotal = costoTotal;
		this.estado = estado;
		this.numPersonas = numPersonas;
		this.idReservaColectiva = idReservaColectiva;
	}
	/**
	 * @return El id de la reserva
	 */
	public long getIdReserva() 
	{
		return idReserva;
	}

	/**
	 * @param idReserva - El nuevo id de la reserva
	 */
	public void setIdReserva(long idReserva) 
	{
		this.idReserva = idReserva;
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
	 * @return La duracion de la reserva
	 */
	public int getDuracion() 
	{
		return duracion;
	}

	/**
	 * @param duracion - La nueva duracion de la reserva
	 */
	public void setDuracion(int duracion) 
	{
		this.duracion = duracion;
	}

	/**
	 * @return La fecha de inicio de la reserva
	 */
	public Date getFechaInicio() 
	{
		return fechaInicio;
	}

	/**
	 * @param fechaInicio - La nueva fecha de inicio de la reserva
	 */
	public void setFechaInicio(Date fechaInicio) 
	{
		this.fechaInicio = fechaInicio;
	}

    /**
	 * @return La fecha de la final de la reserva
	 */
	public Date getFechaFinal() 
	{
		return fechaFinal;
	}

	/**
	 * @param fechaVisita - La nueva fecha de la final de la reserva
	 */
	public void setFechaFinal(Date fechaFinal) 
	{
		this.fechaFinal = fechaFinal;
	}

     /**
	 * @return El costo total de la reserva
	 */
	public long getCostoTotal() 
	{
		return costoTotal;
	}

	/**
	 * @param costoTotal - El nuevo costo total de la reserva
	 */
	public void setCostoTotal(int costoTotal) 
	{
		this.costoTotal = costoTotal;
    }

	/**
	 * @return El estado de la reserva
	 */
	public String getEstado() 
	{
		return estado;
	}

	/**
	 * @param horario - El nuevo estado de la reserva
	 */
	public void setEstado(String estado) 
	{
		this.estado = estado;
	}

     /**
	 * @return El numero de personas de la reserva
	 */
	public int getNumPersonas() 
	{
		return numPersonas;
	}

	/**
	 * @param numPersonas - El nuevo numero de personas de la reserva
	 */
	public void setNumPersonas(int numPersonas) 
	{
		this.numPersonas = numPersonas;

    }

	  /**
	 * @return El id de la reserva colectiva
	 */
	public long getIdReservaColectiva() 
	{
		return idReservaColectiva;
	}

	/**
	 * @param idReservaColectiva - El nuevo id de la reserva colectiva
	 */
	public void setIdReservaColectiva(int idReservaColectiva) 
	{
		this.idReservaColectiva = idReservaColectiva;
    }

	/** 
	 * @return Una cadena con la información básica de reserva
	 */
	@Override
	public String toString() 
	{
		return "Reserva [idAlojamiento=" + idAlojamiento + ", idCliente=" + idCliente + ", duracion=" + duracion + ", fechaInicio="
				+ fechaInicio + "fechaFinal=" + fechaFinal + ", costoTotal=" + costoTotal + ", estado=" + estado + ", numPersonas="
				+ numPersonas + ", idReservaColectiva=" + idReservaColectiva + "]";
	}
}
