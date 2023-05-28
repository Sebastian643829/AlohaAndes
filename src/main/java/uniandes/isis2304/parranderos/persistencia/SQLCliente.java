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

	// RFC5 - MOSTRAR EL USO DE ALOHANDES PARA CADA TIPO DE USUARIO DE LA COMUNIDAD
	/**
	 * Crea y ejecuta la sentencia SQL para mostrar el uso de Alohandes, segun el tipo de usuario 
	 * base de datos de Alohaandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista con las principales carateristicas del uso que se le ha dado a AlohaAndes, segun el tipo de usuario
	 */
	public List<Object> darInformacionMiembrosActivos (PersistenceManager pm)
	{
		String sql = "SELECT A_MiembroActivo.tipo, COUNT(A_Reserva.idCliente) AS Numero_reservas, SUM(A_Reserva.duracion) AS Num_nochesReservadas, SUM(A_Reserva.costoTotal) AS Dinero_pagado";
		sql += " FROM " + pp.darTablaCliente();
		sql += " INNER JOIN " + pp.darTablaMiembroActivo() + " ON A_MiembroActivo.idMiembroActivo = A_cliente.idCliente";
	    sql	+= " LEFT OUTER JOIN " + pp.darTablaReserva() + " ON A_Reserva.idCliente = A_cliente.idCliente";
		sql	+= " GROUP BY A_MiembroActivo.tipo";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}

	public List<Object> darInformacionMiembrosSecundarios (PersistenceManager pm)
	{
		String sql = "SELECT A_MiembroSecundario.tipo, COUNT(A_Reserva.idCliente) AS Numero_reservas, SUM(A_Reserva.duracion) AS Num_nochesReservadas, SUM(A_Reserva.costoTotal) AS Dinero_pagado";
	    sql += " FROM " + pp.darTablaCliente();
		sql += " INNER JOIN " + pp.darTablaMiembroSecundario() + " ON A_MiembroSecundario.idMiembroSecundario = A_cliente.idCliente";
	    sql	+= " LEFT OUTER JOIN " + pp.darTablaReserva() + " ON A_Reserva.idCliente = A_cliente.idCliente";
		sql	+= " GROUP BY A_MiembroSecundario.tipo";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}




	// RFC8 - ENCONTRAR LOS CLIENTES FRECUENTES
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informacion de los clientes fercuentes de un alojamiento.  
	 * @param pm - El manejador de persistencia
	 * @return Una lista de arreglos de Clientes. Los elementos del arreglo corresponden a los datos de los clientes frecuentes del alojamiento dado.
	 */
	public List<Cliente> encontrarClientesFrecuentes (PersistenceManager pm, long idAlojamiento)
	{
	    String sql = "SELECT A_Cliente.*";
	    sql += " FROM " + pp.darTablaCliente();
		sql += " LEFT OUTER JOIN " + pp.darTablaReserva () + " ON A_Cliente.idCliente = A_reserva.idCliente";
	    sql	+= " WHERE A_Reserva.idAlojamiento = ?";
		sql	+= " GROUP BY A_Cliente.idCliente, A_Cliente.tipoIdentificacion, A_Cliente.nombreCliente, A_Cliente.fechaNacimiento";
		sql	+= " HAVING COUNT(A_Reserva.idReserva) >= 3 OR SUM(A_Reserva.duracion) >= 15";

	    Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Cliente.class);
		q.setParameters(idAlojamiento);
		return (List<Cliente>) q.executeList();
	}

	// RFC10 - CONSULTAR CONSUMO EN ALOHANDES
	public List<Cliente> consultarConsumoV1 (PersistenceManager pm, Date fechaInit, Date fechaMax, String tipo, long idAlojamiento)
	{
		//System.out.println(tipo);
		if (tipo.equals("asc")) { 
	    String sql = "SELECT A_Cliente.*";
	    sql += " FROM " + pp.darTablaCliente();
		sql += " INNER JOIN " + pp.darTablaReserva () + " ON A_Cliente.idCliente = A_reserva.idCliente";
	    sql	+= " WHERE ((A_Reserva.fechaInicio BETWEEN ? AND ? ) AND (A_Reserva.fechaFinal BETWEEN ? AND ? ) AND A_Reserva.idAlojamiento = ?)";
		sql	+= " GROUP BY a_cliente.idcliente, a_cliente.tipoidentificacion, a_cliente.nombrecliente, a_cliente.fechanacimiento";
		sql	+= " ORDER BY a_cliente.idCliente ASC";

	    Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Cliente.class);
		q.setParameters(fechaInit, fechaMax, fechaInit, fechaMax, idAlojamiento);
		return (List<Cliente>) q.executeList();
		}

		else if (tipo.equals("desc")){
	    String sql = "SELECT A_Cliente.*";
	    sql += " FROM " + pp.darTablaCliente();
		sql += " INNER JOIN " + pp.darTablaReserva () + " ON A_Cliente.idCliente = A_reserva.idCliente";
	    sql	+= " WHERE ((A_Reserva.fechaInicio BETWEEN ? AND ? ) AND (A_Reserva.fechaFinal BETWEEN ? AND ? ) AND A_Reserva.idAlojamiento = ?)";
		sql	+= " GROUP BY a_cliente.idcliente, a_cliente.tipoidentificacion, a_cliente.nombrecliente, a_cliente.fechanacimiento";
		sql	+= " ORDER BY a_cliente.idCliente DESC";

	    Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Cliente.class);
		q.setParameters(fechaInit, fechaMax, fechaInit, fechaMax, idAlojamiento);
		return (List<Cliente>) q.executeList();
		}
		return null;
	}


	// RFC11 - CONSULTAR CONSUMO EN ALOHANDES VERSION 2
	public List<Cliente> consultarConsumoV2 (PersistenceManager pm, Date fechaInit, Date fechaMax, String tipo, long idAlojamiento)
	{
		if (tipo.equals("asc")) {
	    String sql = "SELECT A_Cliente.*";
	    sql += " FROM " + pp.darTablaCliente();
		sql += " LEFT OUTER JOIN " + pp.darTablaReserva () + " ON A_Cliente.idCliente = A_reserva.idCliente";
	    sql	+= " WHERE a_cliente.idcliente NOT IN (SELECT a_cliente.idCliente";
		sql	+= " FROM a_cliente";
		sql	+= " INNER JOIN a_reserva ON a_cliente.idCliente = a_reserva.idCliente";
		sql	+= " WHERE ((A_Reserva.fechaInicio BETWEEN ? AND ? ) AND (A_Reserva.fechaFinal BETWEEN ? AND ? ) AND A_Reserva.idAlojamiento = ?)";
		sql	+= " GROUP BY a_cliente.idcliente)";
		sql	+= " GROUP BY a_cliente.idcliente, a_cliente.tipoidentificacion, a_cliente.nombrecliente, a_cliente.fechanacimiento";
		sql	+= " ORDER BY a_cliente.idCliente ASC";

	    Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Cliente.class);
		q.setParameters(fechaInit, fechaMax, fechaInit, fechaMax, idAlojamiento);
		return (List<Cliente>) q.executeList();
		}
		else if (tipo.equals("desc")){
			String sql = "SELECT A_Cliente.*";
			sql += " FROM " + pp.darTablaCliente();
			sql += " LEFT OUTER JOIN " + pp.darTablaReserva () + " ON A_Cliente.idCliente = A_reserva.idCliente";
			sql	+= " WHERE a_cliente.idcliente NOT IN (SELECT a_cliente.idCliente";
			sql	+= " FROM a_cliente";
			sql	+= " INNER JOIN a_reserva ON a_cliente.idCliente = a_reserva.idCliente";
			sql	+= " WHERE ((A_Reserva.fechaInicio BETWEEN ? AND ? ) AND (A_Reserva.fechaFinal BETWEEN ? AND ? ) AND A_Reserva.idAlojamiento = ?)";
			sql	+= " GROUP BY a_cliente.idcliente)";
			sql	+= " GROUP BY a_cliente.idcliente, a_cliente.tipoidentificacion, a_cliente.nombrecliente, a_cliente.fechanacimiento";
			sql	+= " ORDER BY a_cliente.idCliente DESC";
	
			Query q = pm.newQuery(SQL, sql);
			q.setResultClass(Cliente.class);
			q.setParameters(fechaInit, fechaMax, fechaInit, fechaMax, idAlojamiento);
			return (List<Cliente>) q.executeList();
		}
		return null;
	}


	// RFC13 - CONSULTAR LOS BUENOS CLIENTES
	public List<Cliente> encontrarBuenosClientes1 (PersistenceManager pm)
	{
	    String sql = "SELECT DISTINCT A_Cliente.*";
	    sql += " FROM " + pp.darTablaCliente();
		sql += " INNER JOIN " + pp.darTablaReserva () + " ON A_Cliente.idCliente = A_reserva.idCliente";
	    sql	+= " INNER JOIN a_alojamiento ON a_reserva.idAlojamiento = a_alojamiento.idAlojamiento";
		sql	+= " WHERE EXISTS (";
		sql	+= " SELECT 1";
		sql	+= " FROM a_reserva";
		sql	+= " WHERE a_cliente.idCliente = a_reserva.idCliente";
		sql	+= " GROUP BY TRUNC(a_reserva.fechaInicio, 'MM')";
		sql	+= "  HAVING COUNT(*) >= 1)";
	
	    Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	}

	public List<Cliente> encontrarBuenosClientes2 (PersistenceManager pm)
	{
		String sql = "SELECT DISTINCT A_Cliente.*";
	    sql += " FROM " + pp.darTablaCliente();
		sql += " INNER JOIN " + pp.darTablaReserva () + " ON A_Cliente.idCliente = A_reserva.idCliente";
	    sql	+= " INNER JOIN a_alojamiento ON a_reserva.idAlojamiento = a_alojamiento.idAlojamiento";
		sql	+= " WHERE EXISTS (";
		sql	+= " SELECT 1";
		sql	+= " FROM a_reserva";
		sql	+= " INNER JOIN a_alojamiento ON a_reserva.idAlojamiento = a_alojamiento.idAlojamiento";
		sql	+= " WHERE a_cliente.idCliente = a_reserva.idCliente AND a_alojamiento.precioNoche >= 650000)";
	
	    Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	    
	}

	public List<Cliente> encontrarBuenosClientes3 (PersistenceManager pm)
	{
	    String sql = "SELECT DISTINCT A_Cliente.*";
	    sql += " FROM " + pp.darTablaCliente();
		sql += " INNER JOIN " + pp.darTablaReserva () + " ON A_Cliente.idCliente = A_reserva.idCliente";
	    sql	+= " INNER JOIN a_alojamiento ON a_reserva.idAlojamiento = a_alojamiento.idAlojamiento";
		sql	+= " WHERE EXISTS (";
		sql	+= " SELECT 1";
		sql	+= " FROM a_reserva";
		sql	+= " INNER JOIN a_alojamiento ON a_reserva.idAlojamiento = a_alojamiento.idAlojamiento";
		sql	+= " INNER JOIN a_habitacionHotel ON a_habitacionHotel.idAlojamiento = a_alojamiento.idAlojamiento";
		sql	+= " WHERE a_cliente.idCliente = a_reserva.idCliente AND a_habitacionHotel.tipoHabitacion = 'Suites')";
	
	    Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	}
    
}
