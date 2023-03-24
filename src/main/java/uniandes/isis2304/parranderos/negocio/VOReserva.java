package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public interface VOReserva {
    /* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
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
	public Timestamp getFechaInicio();

    /**
	 * @return La fecha final de la reserva
	 */
	public Timestamp getFechaFinal();

    /**
	 * @return El costo total de la reserva
	 */
	public int getCostoTotal();

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
