package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.HabitacionVivienda;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto HABITACION VIVIENDA de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Sebastian Sanchez
 */

class SQLHabitacionVivienda {
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
	public SQLHabitacionVivienda (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una HABITACION VIVIENDA a la base de datos de Parranderos
	 * @param idAlojamiento - El id de la habitacion de la vivienda
	 * @param tipoBano - El tipo de baño de la habitacion de la vivienda
	 * @param tipoHabitacion - El tipo de habitacion de la vivienda
	 * @return El número de tuplas insertadas
	 */
	public long adicionarHabitacionVivienda (PersistenceManager pm, long idAlojamiento, String tipoBano, String tipoHabitacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaHabitacionVivienda () + "(idalojamiento, tipobano , tipohabitacion) values (?, ?, ?)");
        q.setParameters(idAlojamiento, tipoHabitacion);
        return (long) q.executeUnique();
	}

    /**
	 * Crea y ejecuta la sentencia SQL para eliminar una HABITACION VIVIENDA de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id de la habitacion vivienda
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarHabitacionViviendaPorId (PersistenceManager pm, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHabitacionVivienda () + " WHERE id = ?");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de una HABITACION VIVIENDA de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id de la habitacion vivienda
	 * @return El objeto HABITACION VIVIENDA que tiene el identificador dado
	 */
	public HabitacionVivienda darHabitacionViviendaPorId (PersistenceManager pm, long idAlojamiento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacionVivienda () + " WHERE id = ?");
		q.setResultClass(HabitacionVivienda.class);
		q.setParameters(idAlojamiento);
		return (HabitacionVivienda) q.executeUnique();
    }

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de las HABITACIONES DE LAS VIVIENDAS de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos HABITACION VIVIENDA
	 */
	public List<HabitacionVivienda> darHabitacionesViviendas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacionVivienda ());
		q.setResultClass(HabitacionVivienda.class);
		return (List<HabitacionVivienda>) q.executeList();
	}
 
}
