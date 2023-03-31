package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.ViviendaTemporal;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto VIVIENDA TEMPORAL de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Sebastian Sanchez
 */

class SQLViviendaTemporal {
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
	public SQLViviendaTemporal (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una VIVIENDA TEMPORAL a la base de datos de Parranderos
	 * @param idAlojamiento - El id de la vivienda temporal
	 * @param numHabitaciones - El numero de habitaciones de la vivienda temporal
	 * @return El número de tuplas insertadas
	 */
	public long adicionarViviendaTemporal(PersistenceManager pm, long idAlojamiento, int numHabitaciones) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaViviendaTemporal () + "(idalojamiento, numhabitaciones) values (?, ?)");
        q.setParameters(idAlojamiento, numHabitaciones);
        return (long) q.executeUnique();
	}

    /**
	 * Crea y ejecuta la sentencia SQL para eliminar UNA VIVIENDA TEMPORAL de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id de la vivienda temporal
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarViviendaTemporalPorId (PersistenceManager pm, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaViviendaTemporal () + " WHERE idalojamiento = ?");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de una VIVIENDA TEMPORAL de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id de la vivienda temporal
	 * @return El objeto VIVIENDA TEMPORAL que tiene el identificador dado
	 */
	public ViviendaTemporal darViviendaTemporalPorId (PersistenceManager pm, long idAlojamiento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaViviendaTemporal () + " WHERE idalojamiento = ?");
		q.setResultClass(ViviendaTemporal.class);
		q.setParameters(idAlojamiento);
		return (ViviendaTemporal) q.executeUnique();
    }

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de las VIVIENDAS TEMPORALES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos VIVIENDA TEMPORAL 
	 */
	public List<ViviendaTemporal> darViviendasTemporales (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaViviendaTemporal ());
		q.setResultClass(ViviendaTemporal.class);
		return (List<ViviendaTemporal>) q.executeList();
	}
}
