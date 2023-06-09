package uniandes.isis2304.parranderos.persistencia;


import java.sql.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reserva;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto RESERVA de AlohaAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author David Santiago Vargas Prada
 */
class SQLReserva {
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
	public SQLReserva (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
     /**
	 * Crea y ejecuta la sentencia SQL para adicionar una RESERVA a la base de datos de Alohaandes
	 * @param idReserva - El ide de la reserva
	 * @param idAlojamiento - El id del alojamiento
	 * @param idCliente - El id del cliente
     * @param duracion - Duracion de la reserva
	 * @param fechaInicio - La fecha de inicio de la reserva
     * @param fechaFinal - La fecha final de la reserva
     * @param costoTotal - El costo total de la reserva
     * @param estado - El estado de la reserva
     * @param numPersonas - Numero de personas en la reserva
	 */
	public long adicionarReserva (PersistenceManager pm, long idReserva,long idAlojamiento, long idCliente, int duracion , Date fechaInicio, Date fechaFinal, long costoTotal, String estado, int numPersonas, long idReservaColectiva) 
	{
		
		SQLAlojamiento sqlAlojamiento = new SQLAlojamiento(pp);
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReserva() + "(idreserva, idalojamiento, idcliente, duracion , fechainicio, fechafinal, costototal, estado, numpersonas, idreservacolectiva) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idReserva, idAlojamiento, idCliente, duracion , fechaInicio, fechaFinal, costoTotal, estado, numPersonas);
		sqlAlojamiento.aumentarNumeroReservasAlojamiento(pm, idAlojamiento);
        return (long) q.executeUnique();
		
	}
	public long revisarReserva (PersistenceManager pm, long idCliente, Date fechaInicio, Date fechaFinal) 
	{
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva() + " WHERE idcliente=? AND ((? BETWEEN fechainicio AND fechafinal) OR (? BETWEEN fechainicio AND fechafinal))");
        q.setParameters(idCliente, fechaInicio, fechaFinal);
		if (q.executeUnique() == null)
		{
			return 0;
		}
		else{
			return (long) q.executeUnique();
		}
	}
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UNA RESERVA de la base de datos de Alohaandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReserva - El id de la reserva
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarReservaPorId (PersistenceManager pm, long idReserva)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva() + " WHERE idreserva = ?");
        q.setParameters(idReserva);
        return (long) q.executeUnique();
	}
	/**
	 * Crea y ejecuta la sentencia SQL para cancelar UNA RESERVA de la base de datos de Alohaandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReserva - El id de la reserva
	 * @return EL número de tuplas modificadas
	 */
	public long cancelarReservaPorId (PersistenceManager pm, long idReserva)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaReserva() + " SET Estado='Cancelada' WHERE idreserva = ? AND Estado='Activa'");
        q.setParameters(idReserva);
        return (long) q.executeUnique();
	}
	public Reserva darReservaPorId (PersistenceManager pm, long idReserva) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva() + " WHERE idreserva = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(idReserva);
		return (Reserva) q.executeUnique();
    }

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS RESERVAS de la 
	 * base de datos de Alohaandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos RESERVA
	 */
	public List<Reserva> darReservas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva() );
		q.setResultClass(Reserva.class);
		return (List<Reserva>) q.executeList();
	}

	// RF7 - REGISTRAR RESERVA COLECTIVA
	/**
	 * Crea y ejecuta la sentencia SQL para obterner los alojamientos disponibles para la reserva colectiva
	 * @param pm - El manejador de persistencia
	 * @return Una lista de alojamientos disponibles en el rango de fechas dado y que sea del tipo de habitacion especificada.
	 */
	public List<Object> RevisarReservaColectiva (PersistenceManager pm, String tipoHabitacion, Date fechaInicio, Date fechaFinal)
	{
		String sql = "SELECT a_alojamiento.idalojamiento, a_alojamiento.precionoche";
		sql+= " FROM " + pp.darTablaAlojamiento();
		sql+= " LEFT OUTER JOIN " + pp.darTablaReserva() + " ON A_alojamiento.idAlojamiento = A_reserva.idAlojamiento";
		sql+=" WHERE A_alojamiento.estado = ? AND A_alojamiento.tipo = ?";
		sql+=" AND A_alojamiento.idAlojamiento NOT IN ( SELECT A_reserva.idAlojamiento";
		sql+=" FROM A_reserva";
		sql+=" WHERE NOT((A_Reserva.fechaInicio IS NULL) OR ((? > A_Reserva.fechaInicio  AND ? > A_Reserva.fechaFinal OR (A_Reserva.estado = 'Cancelada')) OR (? < A_Reserva.fechaInicio  AND  ? < A_Reserva.fechaFinal OR (A_Reserva.estado = 'Cancelada')))))";
		sql+=" GROUP BY a_alojamiento.idalojamiento, a_alojamiento.precionoche";
		
		Query q = pm.newQuery(SQL, sql);
		q.setParameters("Habilitado",tipoHabitacion, fechaInicio, fechaInicio, fechaFinal, fechaFinal);
		return  q.executeList();
	}                            
                                     

	public long RegistrarReservaIndividual(PersistenceManager pm, long idReserva, long idAlojamiento, long idCliente, int duracion, Date fechaInicio , Date fechaFinal, long costoTotal, String estado, int numPersonas, long idReservaColectiva)
	{
        SQLAlojamiento sqlAlojamiento = new SQLAlojamiento(pp);
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReserva() + "(idreserva, idalojamiento, idcliente, duracion , fechainicio, fechafinal, costototal, estado, numpersonas, idreservacolectiva) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idReserva, idAlojamiento, idCliente, duracion , fechaInicio, fechaFinal, costoTotal, estado, numPersonas, idReservaColectiva);
		sqlAlojamiento.aumentarNumeroReservasAlojamiento(pm, idAlojamiento);
        return (long) q.executeUnique();	
	}


	// RF8 - CANCELAR RESERVA COLECTIVA
	/**
	 * Crea y ejecuta la sentencia SQL para cancelar una reserva colectiva de la base de datos de Alohaandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReservaColectiva - El id de la reserva colectiva
	 * @return EL número de tuplas modificadas
	 */
	public long cancelarReservaColectivaPorId (PersistenceManager pm, long idReservaColectiva)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaReserva() + " SET A_reserva.estado = 'Cancelada' WHERE A_reserva.idreservacolectiva = ?");
        q.setParameters(idReservaColectiva);
        return (long) q.executeUnique();
	}
	

	// RFC6 - MOSTRAR EL USO DE ALOHANDES PARA UN USUARIO DADO
	/**
	 * Crea y ejecuta la sentencia SQL para moestrar el numero de reservas, el dinero pagado y el numero de noches reservadas de un cliente
	 * @param pm - El manejador de persistencia
	 * @return Una lista de alojamientos, de tamaño 20. Los elementos del arreglo corresponden a los datos de 
	 * los alojamientos que tienen mas reservas asociadas
	 */
	public List<Object> mostrarUsoPorUsuario (PersistenceManager pm, long idcliente)
	{
		String sql = "SELECT A_Reserva.idCliente, COUNT(A_Reserva.idCliente) AS Numero_reservas, SUM(A_Reserva.duracion) AS Num_nochesReservadas, SUM(A_Reserva.costoTotal) AS Dinero_pagado ";
		sql+= " FROM " + pp.darTablaReserva();
		sql+=" WHERE A_Reserva.idCliente = ?";
		sql+=" GROUP BY A_Reserva.idCliente";
		
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idcliente);
		return  q.executeList();
	}

	// RFC12 - CONSULTAR FUNCIONAMIENTO
	public List<Object> consultarFuncionamiento1 (PersistenceManager pm)
	{
		String sql = " SELECT fechaInicial,";
	    sql += " MAX(CASE WHEN OCCUPATION = MAX_OCUPACION THEN idAlojamiento END) AS ID_MAX_OCUPACION,";
		sql += " MIN(CASE WHEN OCCUPATION = MIN_OCUPACION THEN idAlojamiento END) AS ID_MIN_OCUPACION";
		sql += " FROM( SELECT a_reserva.idAlojamiento, ";
		sql += " TRUNC(a_reserva.fechaInicio, 'IW') fechaInicial,";
		sql += " SUM(a_reserva.numPersonas) AS OCCUPATION,";
		sql += " MAX(SUM(a_reserva.numPersonas)) OVER (PARTITION BY TRUNC(a_reserva.fechaInicio, 'IW')) AS MAX_OCUPACION,";
		sql += " MIN(SUM(a_reserva.numPersonas)) OVER (PARTITION BY TRUNC(a_reserva.fechaInicio, 'IW')) AS MIN_OCUPACION";
		sql += " FROM " + pp.darTablaReserva();
		sql += " GROUP BY a_reserva.idAlojamiento, TRUNC(a_reserva.fechaInicio, 'IW')";
		sql += " ) subquery";
		sql += " GROUP BY fechaInicial";
		sql += " ORDER BY fechaInicial";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}

	public List<Object> consultarFuncionamiento2 (PersistenceManager pm)
	{
		String sql = " SELECT fechaInicial,";
	    sql += " MAX(CASE WHEN SOLICITADOS = MAX_SOLICITADOS THEN idOperador END) AS ID_MAX_SOLICITADOS,";
		sql += " MIN(CASE WHEN SOLICITADOS = MIN_SOLICITADOS THEN idOperador END) AS ID_MIN_SOLICITADOS";
		sql += " FROM( SELECT a_alojamiento.idOperador, TRUNC(a_reserva.fechaInicio, 'IW') fechaInicial, ";
		sql += " COUNT(a_alojamiento.idOperador) AS SOLICITADOS,";
		sql += " MAX(COUNT(a_alojamiento.idOperador)) OVER (PARTITION BY TRUNC(a_reserva.fechaInicio, 'IW')) AS MAX_SOLICITADOS,";
		sql += " MIN(COUNT(a_alojamiento.idOperador)) OVER (PARTITION BY TRUNC(a_reserva.fechaInicio, 'IW')) AS MIN_SOLICITADOS";
		sql += " FROM " + pp.darTablaReserva();
		sql += " INNER JOIN " + pp.darTablaAlojamiento() + " a_alojamiento ON a_alojamiento.idalojamiento = a_reserva.idalojamiento";
		sql += " GROUP BY a_alojamiento.idOperador, TRUNC(a_reserva.fechaInicio, 'IW')";
		sql += " ) subquery";
		sql += " GROUP BY fechaInicial";
		sql += " ORDER BY fechaInicial";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();

	}

}
