package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.HabitacionHotel;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto HABITACION HOTEL de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Sebastian Sanchez
 */

class SQLHabitacionHotel {
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
	public SQLHabitacionHotel (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una HABITACION HOTEL a la base de datos de Parranderos
	 * @param idAlojamiento - El id de la habitacion de hotel
	 * @param tipoHabitacion - El tipo de habitacion de la habitacion de hotel
	 * @return El número de tuplas insertadas
	 */
	public long adicionarHabitacionHotel (PersistenceManager pm, long idAlojamiento, String tipoHabitacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaHabitacionHotel () + "(idalojamiento, tipohabitacion) values (?, ?)");
        q.setParameters(idAlojamiento, tipoHabitacion);
        return (long) q.executeUnique();
	}

    /**
	 * Crea y ejecuta la sentencia SQL para eliminar UNA HABITACION HOTEL de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id de la habitacion de hotel
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarHabitacionHotelPorId (PersistenceManager pm, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHabitacionHotel () + " WHERE id = ?");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de una HABITACION HOTEL de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id de la habitacion de hotel
	 * @return El objeto HABITACION HOTEL que tiene el identificador dado
	 */
	public HabitacionHotel darHabitacionHotelPorId (PersistenceManager pm, long idAlojamiento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacionHotel () + " WHERE id = ?");
		q.setResultClass(HabitacionHotel.class);
		q.setParameters(idAlojamiento);
		return (HabitacionHotel) q.executeUnique();
    }

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de las HABITACIONES DE HOTEL de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos HABITACION HOTEL
	 */
	public List<HabitacionHotel> darHabitacionesHoteles (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacionHotel ());
		q.setResultClass(HabitacionHotel.class);
		return (List<HabitacionHotel>) q.executeList();
	}
}
