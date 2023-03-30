package uniandes.isis2304.parranderos.persistencia;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Empresa;
/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto EMPRESA de AlohaAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author David Santiago Vargas Prada
 */
class SQLEmpresa {
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
	public SQLEmpresa (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
    /**
	 * Crea y ejecuta la sentencia SQL para adicionar un EMPRESA a la base de datos de Parranderos
	 * @param idOperador - El id del empresa
	 * @param nit - Número de NIT del empresa
     * @param nombreEmpresa - Nombre del empresa
	 * @return El número de tuplas insertadas
	 */
	public long adicionarEmpresa (PersistenceManager pm, long idOperador, String nit, String nombreEmpresa) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaEmpresa () + "(idoperador, nit, nombreempresa) values (?, ?, ?)");
        q.setParameters(idOperador, nit, nombreEmpresa);
        return (long) q.executeUnique();
	}
    /**
	 * Crea y ejecuta la sentencia SQL para eliminar un EMPRESA de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idOperador - El id del empresa
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEmpresaPorId (PersistenceManager pm, long idOperador)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEmpresa () + " WHERE id = ?");
        q.setParameters(idOperador);
        return (long) q.executeUnique();
	}
    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de un EMPRESA de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idOperador - El id del empresa
	 * @return El objeto EMPRESA que tiene el identificador dado
	 */
	public Empresa darEmpresaPorId (PersistenceManager pm, long idOperador) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEmpresa () + " WHERE id = ?");
		q.setResultClass(Empresa.class);
		q.setParameters(idOperador);
		return (Empresa) q.executeUnique();
    }
    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los EMPRESAS de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos EMPRESA
	 */
	public List<Empresa> darViviendasUniversitarias (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEmpresa ());
		q.setResultClass(Empresa.class);
		return (List<Empresa>) q.executeList();
	}
    
}
