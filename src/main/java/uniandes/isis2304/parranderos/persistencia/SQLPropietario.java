package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Propietario;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PROPIETARIO de AlohaAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author David Santiago Vargas Prada
 */

class SQLPropietario {
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
	public SQLPropietario (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
    /**
	 * Crea y ejecuta la sentencia SQL para adicionar un PROPIETARIO a la base de datos de Parranderos
	 * @param idOperador - El id del propietario
	 * @param identificacion - Número de identificación del propietario
     * @param tipoIdentificacion - Tipo de identificación del propietario
     * @param nombrePropietario - Nombre del propietario
	 * @return El número de tuplas insertadas
	 */
	public long adicionarPropietario (PersistenceManager pm, long idOperador, String identificacion, String tipoIdentificacion, String nombrePropietario) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPropietario () + "(idoperador, identificacion, tipoidentificacion, nombrepropietario) values (?, ?, ?, ?)");
        q.setParameters(idOperador, identificacion, tipoIdentificacion, nombrePropietario);
        return (long) q.executeUnique();
	}
    /**
	 * Crea y ejecuta la sentencia SQL para eliminar un PROPIETARIO de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idOperador - El id del propietario
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPropietarioPorId (PersistenceManager pm, long idOperador)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPropietario () + " WHERE id = ?");
        q.setParameters(idOperador);
        return (long) q.executeUnique();
	}
    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de un PROPIETARIO de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idOperador - El id del propietario
	 * @return El objeto PROPIETARIO que tiene el identificador dado
	 */
	public Propietario darPropietarioPorId (PersistenceManager pm, long idOperador) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPropietario () + " WHERE id = ?");
		q.setResultClass(Propietario.class);
		q.setParameters(idOperador);
		return (Propietario) q.executeUnique();
    }
    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los PROPIETARIOS de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PROPIETARIO
	 */
	public List<Propietario> darViviendasUniversitarias (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPropietario ());
		q.setResultClass(Propietario.class);
		return (List<Propietario>) q.executeList();
	}
}
