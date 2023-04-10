package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;


public interface VOReserva {
    /* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * @return El id de la reserva
	 */
	public long getIdReserva();
	/**
	 * @return El id del alojamiento
	 */
	public long getIdAlojamiento();

	/**
	 * @return El id del cliente
	 */
	public long getIdCliente();

    /**
	 * @return La duracion de la reserva
	 */
	public int getDuracion();

	/**
	 * @return La fecha inicial de la reserva
	 */
	public Date getFechaInicio();

    /**
	 * @return La fecha final de la reserva
	 */
	public Date getFechaFinal();

    /**
	 * @return El costo total de la reserva
	 */
	public long getCostoTotal();

	/**
	 * @return El estado de la reserva
	 */
	public String getEstado();

    /**
	 * @return El numero de personas de la reserva
	 */
	public int getNumPersonas();

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();
}
