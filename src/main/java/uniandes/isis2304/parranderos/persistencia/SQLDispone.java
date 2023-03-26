package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Dispone;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto DISPONE de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Sebastian Sanchez
 */

class SQLDispone {
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
	public SQLDispone (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una tupla a DISPONE a la base de datos de Parranderos
	 * @param idServicio - El id del servicio
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas insertadas
	 */
	public long adicionarDispone (PersistenceManager pm, long idServicio, long idAlojamiento) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaDispone() + "(idservicio, idalojamiento) values (?, ?)");
        q.setParameters(idServicio, idAlojamiento);
        return (long) q.executeUnique();
	}

   /**
	 * Crea y ejecuta la sentencia SQL para eliminar una tupla de DISPONE de la base de datos de Parranderos, por sus identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El id del servicio
	 * @param idAlojamiento - El id del alojamiento
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarDispone (PersistenceManager pm, long idServicio, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaDispone() + " WHERE idservicio = ? AND idalojamiento = ?");
        q.setParameters(idServicio, idAlojamiento);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de las tupla de DISPONE de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Dispone
	 */
	public List<Dispone> darDispone (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaDispone ());
		q.setResultClass(Dispone.class);
		List<Dispone> resp = (List<Dispone>) q.execute();
		return resp;
	}
}
