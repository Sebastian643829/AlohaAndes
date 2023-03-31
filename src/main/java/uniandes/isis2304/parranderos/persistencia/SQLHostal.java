package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Hostal;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto HOSTAL de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Sebastian Sanchez
 */

class SQLHostal {
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
	public SQLHostal (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un HOSTAL a la base de datos de Parranderos
	 * @param idAlojamiento - El id de la habitacion del hostal
	 * @param horarioApertura- El horario de apertura del hostal
	 * @param horarioCierre - El horario del cierre del hostal
	 * @return El número de tuplas insertadas
	 */
	public long adicionarHostal (PersistenceManager pm, long idAlojamiento, String horarioApertura, String horarioCierre) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaHostal () + "(idalojamiento, horarioapertura, horariocierre) values (?, ?, ?)");
        q.setParameters(idAlojamiento, horarioApertura, horarioCierre );
        return (long) q.executeUnique();
	}

    /**
	 * Crea y ejecuta la sentencia SQL para eliminar UN HOSTAL de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id del hostal
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarHostalPorId (PersistenceManager pm, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHostal () + " WHERE idalojamiento = ?");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de un HOSTAL de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id del hostal
	 * @return El objeto HOSTAL que tiene el identificador dado
	 */
	public Hostal darHostalPorId (PersistenceManager pm, long idAlojamiento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHostal () + " WHERE idalojamiento = ?");
		q.setResultClass(Hostal.class);
		q.setParameters(idAlojamiento);
		return (Hostal) q.executeUnique();
    }

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de lOS HOSTALES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos HOSTAL
	 */
	public List<Hostal> darHostales (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHostal ());
		q.setResultClass(Hostal.class);
		return (List<Hostal>) q.executeList();
	}
}
