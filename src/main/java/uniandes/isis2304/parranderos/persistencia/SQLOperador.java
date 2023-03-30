package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Operador;
/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto OPERADOR de AlohaAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author David Santiago Vargas Prada
 */
class SQLOperador {
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
    public SQLOperador (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un OPERADOR a la base de datos de Alohaandes
	 * @param idOperador - El id del operador
	 * @param telefono - El nombre del operador
	 * @param tipoVinculación - La capacidad del operador
	 * @return El número de tuplas insertadas
	 */
    public long adicionarOperador (PersistenceManager pm, long idOperador, String telefono, String tipoVinculacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOperador() + "(idoperador, telefono, tipovinculacion) values (?, ?, ?)");
        q.setParameters(idOperador, telefono, tipoVinculacion);
        return (long) q.executeUnique();
	}
      /**
	 * Crea y ejecuta la sentencia SQL para eliminar UN OPERADOR de la base de datos de Alohaandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idOperador - El id del operador
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarOperadorPorId (PersistenceManager pm, long idOperador)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOperador() + " WHERE id = ?");
        q.setParameters(idOperador);
        return (long) q.executeUnique();
	}
    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN OPERADOR de la 
	 * base de datos de Alohaandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idOperador - El id del operador
	 * @return El objeto OPERADOR que tiene el identificador dado
	 */
	public Operador darOperadorPorId (PersistenceManager pm, long idOperador) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOperador() + " WHERE id = ?");
		q.setResultClass(Operador.class);
		q.setParameters(idOperador);
		return (Operador) q.executeUnique();
    }
    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS OPERADORES de la 
	 * base de datos de Alohaandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos OPERADOR
	 */
	public List<Operador> darOperadors (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOperador() );
		q.setResultClass(Operador.class);
		return (List<Operador>) q.executeList();
	}
}
