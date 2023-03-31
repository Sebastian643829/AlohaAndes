package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.MiembroActivo;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto MIEMBROACTIVO de AlohaAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author David Santiago Vargas Prada
 */
class SQLMiembroActivo {/* ****************************************************************
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
   public SQLMiembroActivo (PersistenciaParranderos pp)
   {
       this.pp = pp;
   }
   /**
	 * Crea y ejecuta la sentencia SQL para adicionar un MIEMBRO ACTIVO a la base de datos de Parranderos
	 * @param idMiembroActivo - El id del Miembro Activo
	 * @param carnet - Número de carnet del Miembro Activo
     * @param tipo - Nombre del Miembro Activo
	 * @return El número de tuplas insertadas
	 */
	public long adicionarMiembroActivo (PersistenceManager pm, long idMiembroActivo, String carnet, String tipo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaMiembroActivo () + "(idmiembroactivo, carnet, tipo) values (?, ?, ?)");
        q.setParameters(idMiembroActivo, carnet, tipo);
        return (long) q.executeUnique();
	}
    /**
	 * Crea y ejecuta la sentencia SQL para eliminar un MIEMBROACTIVO de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idMiembroActivo - El id del miembro activo
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarMiembroActivoPorId (PersistenceManager pm, long idMiembroActivo)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaMiembroActivo () + " WHERE idmiembroactivo = ?");
        q.setParameters(idMiembroActivo);
        return (long) q.executeUnique();
	}
    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de un MIEMBROACTIVO de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idMiembroActivo - El id del miembro activo
	 * @return El objeto MIEMBROACTIVO que tiene el identificador dado
	 */
	public MiembroActivo darMiembroActivoPorId (PersistenceManager pm, long idMiembroActivo) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaMiembroActivo () + " WHERE idmiembroactivo = ?");
		q.setResultClass(MiembroActivo.class);
		q.setParameters(idMiembroActivo);
		return (MiembroActivo) q.executeUnique();
    }
    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los MIEMBROACTIVOS de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos MIEMBROACTIVO
	 */
	public List<MiembroActivo> darMiembrosActivos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaMiembroActivo ());
		q.setResultClass(MiembroActivo.class);
		return (List<MiembroActivo>) q.executeList();
	}
}
