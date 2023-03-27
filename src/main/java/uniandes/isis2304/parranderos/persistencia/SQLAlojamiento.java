package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Alojamiento;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto ALOJAMIENTO de Alohaandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Sebastian Sanchez
 */

class SQLAlojamiento {
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
	public SQLAlojamiento (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un ALOJAMIENTO a la base de datos de Alohaandes
	 * @param idAlojamiento - El id del alojamiento
	 * @param nombre - El nombre del alojamiento
	 * @param capacidad - La capacidad del alojamiento
     * @param ubicacion - La ubicacion del alojamiento
	 * @param tamano - El tamaño del alojamiento
	 * @param precioNoche - El precio de la noche del alojamiento
     * @param ocupacionTotal - La ocupacion total actual del alojamiento
	 * @param numReservas - El numero de reservas del alojamiento
	 * @param idOperador - El id del operador
	 * @return El número de tuplas insertadas
	 */
	public long adicionarAlojamiento (PersistenceManager pm, long idAlojamiento, String nombre, int capacidad ,String ubicacion, int tamano, int precioNoche, int ocupacionTotal, int numReservas, long idOperador) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaAlojamiento() + "(idalojamiento, nombre, capacidad , ubicacion, tamano, precionoche, ocupacionactual, numreservas, idoperador) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idAlojamiento, nombre, capacidad , ubicacion, tamano, precioNoche, ocupacionTotal, numReservas, idOperador);
        return (long) q.executeUnique();
	}

    /**
	 * Crea y ejecuta la sentencia SQL para eliminar UN ALOJAMIENTO de la base de datos de Alohaandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id del alojamiento
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarAlojamientoPorId (PersistenceManager pm, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlojamiento() + " WHERE id = ?");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN ALOJAMIENTO de la 
	 * base de datos de Alohaandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id del alojamiento
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public Alojamiento darAlojamientoPorId (PersistenceManager pm, long idAlojamiento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlojamiento() + " WHERE id = ?");
		q.setResultClass(Alojamiento.class);
		q.setParameters(idAlojamiento);
		return (Alojamiento) q.executeUnique();
    }

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS ALOJAMIENTOS de la 
	 * base de datos de Alohaandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos ALOJAMIENTO
	 */
	public List<Alojamiento> darAlojamientos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlojamiento() );
		q.setResultClass(Alojamiento.class);
		return (List<Alojamiento>) q.executeList();
	}

    // PENDIENTE: Verificar los siguientes metodos

	/**
	 * Crea y ejecuta la sentencia SQL para aumentar en uno la ocupacion actual de los alojamientos de la 
	 * base de datos de Alohaandes
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas modificadas
	 */
	public long aumentarOcupacionTotalAlojamiento (PersistenceManager pm, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaAlojamiento() + " SET ocupacionactual = ocupacionactual + 1 WHERE idalojamiento = ?");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
	}

    /**
	 * Crea y ejecuta la sentencia SQL para aumentar en uno el número de reservas de los alojamientos de la 
	 * base de datos de Alohaandes
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas modificadas
	 */
	public long aumentarNumeroReservasAlojamiento (PersistenceManager pm, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaAlojamiento() + " SET numreservas = numreservas + 1 WHERE idalojamiento = ?");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
	}

}