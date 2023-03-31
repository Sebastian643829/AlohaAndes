package uniandes.isis2304.parranderos.persistencia;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.MiembroSecundario;
/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto MIEMBROSECUNDARIO de AlohaAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author David Santiago Vargas Prada
 */
class SQLMiembroSecundario {
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
   public SQLMiembroSecundario (PersistenciaParranderos pp)
   {
       this.pp = pp;
   }
   /**
	 * Crea y ejecuta la sentencia SQL para adicionar un MIEMBRO SECUNDARIO a la base de datos de Parranderos
	 * @param idMiembroSecundario - El id del Miembro Secundario
     * @param tipo - Nombre del Miembro Secundario
	 * @return El número de tuplas insertadas
	 */
	public long adicionarMiembroSecundario (PersistenceManager pm, long idMiembroSecundario, String tipo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaMiembroSecundario () + "(idmiembrosecundario, tipo) values (?, ?, ?)");
        q.setParameters(idMiembroSecundario, tipo);
        return (long) q.executeUnique();
	}
    /**
	 * Crea y ejecuta la sentencia SQL para eliminar un MIEMBROSECUNDARIO de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idMiembroSecundario - El id del miembro secundario
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarMiembroSecundarioPorId (PersistenceManager pm, long idMiembroSecundario)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaMiembroSecundario () + " WHERE idmiembrosecundario = ?");
        q.setParameters(idMiembroSecundario);
        return (long) q.executeUnique();
	}
    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de un MIEMBROSECUNDARIO de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idMiembroSecundario - El id del miembro secundario
	 * @return El objeto MIEMBROSECUNDARIO que tiene el identificador dado
	 */
	public MiembroSecundario darMiembroSecundarioPorId (PersistenceManager pm, long idMiembroSecundario) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaMiembroSecundario () + " WHERE idmiembrosecundario = ?");
		q.setResultClass(MiembroSecundario.class);
		q.setParameters(idMiembroSecundario);
		return (MiembroSecundario) q.executeUnique();
    }
    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los MIEMBROSECUNDARIOS de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos MIEMBROSECUNDARIO
	 */
	public List<MiembroSecundario> darMiembrosSecundarios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaMiembroSecundario ());
		q.setResultClass(MiembroSecundario.class);
		return (List<MiembroSecundario>) q.executeList();
	}
    
}
