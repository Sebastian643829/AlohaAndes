package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Apartamento;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto APARTAMENTO de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Sebastian Sanchez
 */

class SQLApartamento {
    
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
	public SQLApartamento (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un APARTAMENTO a la base de datos de Parranderos
	 * @param idAlojamiento - El id del apartamento
	 * @return El número de tuplas insertadas
	 */
	public long adicionarApartamento (PersistenceManager pm, long idAlojamiento) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaApartamento () + "(idalojamiento) values (?)");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
	}

    /**
	 * Crea y ejecuta la sentencia SQL para eliminar UN APARTAMENTO de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id del apartamento
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarApartamentoPorId (PersistenceManager pm, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaApartamento () + " WHERE id = ?");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de un APARTAMENTO de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id del apartamento
	 * @return El objeto APARTAMENTO que tiene el identificador dado
	 */
	public Apartamento darApartamentoPorId (PersistenceManager pm, long idAlojamiento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaApartamento () + " WHERE id = ?");
		q.setResultClass(Apartamento.class);
		q.setParameters(idAlojamiento);
		return (Apartamento) q.executeUnique();
    }

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los APARTAMENTO de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos APARTAMENTO
	 */
	public List<Apartamento> darApartamentos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaApartamento ());
		q.setResultClass(Apartamento.class);
		return (List<Apartamento>) q.executeList();
	}

}
