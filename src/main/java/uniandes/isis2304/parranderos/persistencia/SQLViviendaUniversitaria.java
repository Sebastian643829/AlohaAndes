package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.ViviendaUniversitaria;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto VIVIENDA UNIVERSITARIA de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Sebastian Sanchez
 */


class SQLViviendaUniversitaria {
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
	public SQLViviendaUniversitaria (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una VIVIENDA UNIVERSITARIA a la base de datos de Parranderos
	 * @param idAlojamiento - El id de la vivienda universitaria
	 * @param tipoHabitacion - El tipo de habitacion de la vivienda universitaria
	 * @return El número de tuplas insertadas
	 */
	public long adicionarViviendaUniversitaria (PersistenceManager pm, long idAlojamiento, String tipoHabitacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaViviendaUniversitaria () + "(idalojamiento, tipohabitacion) values (?, ?)");
        q.setParameters(idAlojamiento, tipoHabitacion);
        return (long) q.executeUnique();
	}

    /**
	 * Crea y ejecuta la sentencia SQL para eliminar UNA VIVIENDA UNIVERSITARIA de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id de la vivienda universitaria
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarViviendaUniversitariaPorId (PersistenceManager pm, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaViviendaUniversitaria () + " WHERE idalojamiento = ?");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de una VIVIENDA UNIVERSITARIA de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id de la vivienda universitaria
	 * @return El objeto VIVIENDA UNIVERSITARIA que tiene el identificador dado
	 */
	public ViviendaUniversitaria darViviendaUniversitariaPorId (PersistenceManager pm, long idAlojamiento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaViviendaUniversitaria () + " WHERE idalojamiento = ?");
		q.setResultClass(ViviendaUniversitaria.class);
		q.setParameters(idAlojamiento);
		return (ViviendaUniversitaria) q.executeUnique();
    }

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de las VIVIENDAS UNIVERSITARIAS de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos VIVIENDA UNIVERSITARIA
	 */
	public List<ViviendaUniversitaria> darViviendasUniversitarias (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaViviendaUniversitaria ());
		q.setResultClass(ViviendaUniversitaria.class);
		return (List<ViviendaUniversitaria>) q.executeList();
	}
 
}
