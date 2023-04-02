package uniandes.isis2304.parranderos.persistencia;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cliente;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CLIENTE de AlohaAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author David Santiago Vargas Prada
 */
class SQLCliente {
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
	public SQLCliente (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
    /**
	 * Crea y ejecuta la sentencia SQL para adicionar un ALOJAMIENTO a la base de datos de Alohaandes
	 * @param idCliente - El id del cliente
	 * @param tipoIdentificacion - El tipo de identificación del cliente
	 * @param nombreCliente - El nombre del cliente
     * @param fechaNacimiento - La fecha de nacimiento del cliente
	 */
	public long adicionarCliente (PersistenceManager pm, long idCliente, String tipoIdentificacion, String nombreCliente , Date fechaNacimiento) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCliente() + "(idcliente, tipoidentificacion, nombrecliente , fechanacimiento) values (?, ?, ?, ?)");
        q.setParameters(idCliente, tipoIdentificacion, nombreCliente , fechaNacimiento);
        return (long) q.executeUnique();
	}
    /**
	 * Crea y ejecuta la sentencia SQL para eliminar un CLIENTE de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El id del cliente
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarClientePorId (PersistenceManager pm, long idCliente)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente () + " WHERE idcliente = ?");
        q.setParameters(idCliente);
        return (long) q.executeUnique();
	}
    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de un CLIENTE de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El id del cliente
	 * @return El objeto CLIENTE que tiene el identificador dado
	 */
	public Cliente darClientePorId (PersistenceManager pm, long idCliente) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente () + " WHERE idcliente = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(idCliente);
		return (Cliente) q.executeUnique();
    }
    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los CLIENTES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CLIENTE
	 */
	public List<Cliente> darClientes (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente ());
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	}
}
