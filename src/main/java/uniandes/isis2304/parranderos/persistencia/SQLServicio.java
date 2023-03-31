package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Servicio;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SERVICIO de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Sebastian Sanchez
 */

class SQLServicio {
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
	public SQLServicio (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un SERVICIO a la base de datos de Parranderos
	 * @param idServicio - El id del servicio
	 * @param nombre - El nombre del servicio
     * @param costo - El costo del servicio
	 * @return El número de tuplas insertadas
	 */
	public long adicionarServicio (PersistenceManager pm, long idServicio, String nombre, int costo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaServicio() + "(idservicio, nombre, costo) values (?, ?, ?)");
        q.setParameters(idServicio, nombre, costo);
        return (long) q.executeUnique();
	}

    /**
	 * Crea y ejecuta la sentencia SQL para eliminar UN SERVICIO de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El id del servicio
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarServicioPorId (PersistenceManager pm, long idServicio)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServicio() + " WHERE idservicio = ?");
        q.setParameters(idServicio);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN SERVICIO de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El id del servicio
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public Servicio darServicioPorId (PersistenceManager pm, long idServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicio() + " WHERE idservicio = ?");
		q.setResultClass(Servicio.class);
		q.setParameters(idServicio);
		return (Servicio) q.executeUnique();
    }

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS SERVICIO de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos SERVICIO
	 */
	public List<Servicio> darServicios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicio());
		q.setResultClass(Servicio.class);
		return (List<Servicio>) q.executeList();
	}

}
