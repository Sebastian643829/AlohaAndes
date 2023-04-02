package uniandes.isis2304.parranderos.persistencia;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reserva;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto RESERVA de AlohaAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author David Santiago Vargas Prada
 */
class SQLReserva {
    /* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaParranderos.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaParranderos pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLReserva (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
     /**
	 * Crea y ejecuta la sentencia SQL para adicionar una RESERVA a la base de datos de Alohaandes
	 * @param idReserva - El ide de la reserva
	 * @param idAlojamiento - El id del alojamiento
	 * @param idCliente - El id del cliente
     * @param duracion - Duracion de la reserva
	 * @param fechaInicio - La fecha de inicio de la reserva
     * @param fechaFinal - La fecha final de la reserva
     * @param costoTotal - El costo total de la reserva
     * @param estado - El estado de la reserva
     * @param numPersonas - Numero de personas en la reserva
	 */
	public long adicionarReserva (PersistenceManager pm, long idReserva,long idAlojamiento, long idCliente, int duracion , Date fechaInicio, Date fechaFinal, long costoTotal, String estado, int numPersonas) 
	{
		SQLAlojamiento sqlAlojamiento = new SQLAlojamiento(pp);
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReserva() + "(idreserva, idalojamiento, idcliente, duracion , fechainicio, fechafinal, costototal, estado, numpersonas) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idReserva, idAlojamiento, idCliente, duracion , fechaInicio, fechaFinal, costoTotal, estado, numPersonas);
		sqlAlojamiento.aumentarNumeroReservasAlojamiento(pm, idAlojamiento);
        return (long) q.executeUnique();
	}
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN ALOJAMIENTO de la base de datos de Alohaandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReserva - El id del alojamiento
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarReservaPorId (PersistenceManager pm, long idReserva)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva() + " WHERE idreserva = ?");
        q.setParameters(idReserva);
        return (long) q.executeUnique();
	}
	public Reserva darReservaPorId (PersistenceManager pm, long idReserva) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva() + " WHERE idreserva = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(idReserva);
		return (Reserva) q.executeUnique();
    }

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS RESERVAS de la 
	 * base de datos de Alohaandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos RESERVA
	 */
	public List<Reserva> darReservas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva() );
		q.setResultClass(Reserva.class);
		return (List<Reserva>) q.executeList();
	}
}
