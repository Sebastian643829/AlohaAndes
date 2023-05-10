package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;
import java.sql.Timestamp;
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
	public long adicionarAlojamiento (PersistenceManager pm, long idAlojamiento, String nombre, int capacidad ,String ubicacion, int tamano, int precioNoche, int ocupacionTotal, int numReservas, long idOperador, String estado, String tipo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaAlojamiento() + "(idalojamiento, nombre, capacidad , ubicacion, tamano, precionoche, ocupacionactual, numreservas, idoperador, estado, tipo) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idAlojamiento, nombre, capacidad , ubicacion, tamano, precioNoche, ocupacionTotal, numReservas, idOperador, estado, tipo);
        return (long) q.executeUnique();
	}
	public long revisarAlojamiento (PersistenceManager pm, long idAlojamiento) 
	{
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva() + " WHERE idalojamiento=? AND estado='Deshabilitado'");
        q.setParameters(idAlojamiento);
		if (q.executeUnique() == null)
		{
			return 0;
		}
		else{
			return (long) q.executeUnique();
		}
	}


    /**
	 * Crea y ejecuta la sentencia SQL para eliminar UN ALOJAMIENTO de la base de datos de Alohaandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id del alojamiento
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarAlojamientoPorId (PersistenceManager pm, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlojamiento() + " WHERE idalojamiento = ?");
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
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlojamiento() + " WHERE idalojamiento = ?");
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
	// RFC1: MOSTRAR EL DINERO RECIBIDO POR CADA PROVEEDOR DE ALOJAMIENTO DURANTE EL AÑO ACTUAL Y EL AÑO CORRIDO

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar las 20 ofertas de alojamientos mas populares 
	 * base de datos de Alohaandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de alojamientos, de tamaño 20. Los elementos del arreglo corresponden a los datos de 
	 * los alojamientos que tienen mas reservas asociadas
	 */
	public List<Object> darDinero (PersistenceManager pm)
	{
		String sql = "SELECT op.IDOPERADOR, COALESCE(SUM(r.COSTOTOTAL), 0) as COSTO_TOTAL_RECIBIDO FROM ";
		sql+= pp.darTablaOperador();
		sql+=" op LEFT JOIN "; 
		sql+= pp.darTablaAlojamiento();
		sql+=" al ON op.IDOPERADOR = al.IDOPERADOR LEFT JOIN ";
		sql+= pp.darTablaReserva();
		sql+= " r ON al.IDALOJAMIENTO = r.IDALOJAMIENTO AND r.ESTADO = 'Finalizada' AND r.FECHAINICIO <= TO_DATE('2023-01-01', 'YYYY-MM-DD') AND r.FECHAFINAL <= TO_DATE('2023-04-09', 'YYYY-MM-DD') GROUP BY op.IDOPERADOR";
		Query q = pm.newQuery(SQL, sql);
		return  q.executeList();
	}

// RFC2: MOSTRAR LAS 20 OFERTAS MÁS POPULARES

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar las 20 ofertas de alojamientos mas populares 
	 * base de datos de Alohaandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de alojamientos, de tamaño 20. Los elementos del arreglo corresponden a los datos de 
	 * los alojamientos que tienen mas reservas asociadas
	 */
	public List<Alojamiento> darOfertasMasPopulares (PersistenceManager pm)
	{
		String sql = "SELECT *";
        sql += " FROM " + pp.darTablaAlojamiento();
       	sql	+= " ORDER BY numreservas DESC";
		sql	+= " FETCH FIRST 20 ROWS ONLY";
		Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Alojamiento.class);
		return (List<Alojamiento>) q.executeList();
	}

	// RFC3: MOSTRAR EL ÍNDICE DE OCUPACIÓN DE CADA UNA DE LAS OFERTAS DE ALOJAMIENTO REGISTRADAS
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar las 20 ofertas de alojamientos mas populares 
	 * base de datos de Alohaandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de alojamientos, de tamaño 20. Los elementos del arreglo corresponden a los datos de 
	 * los alojamientos que tienen mas reservas asociadas
	 */
	public List<Object> darIndiceDeOcupacion (PersistenceManager pm)
	{
		String sql = "SELECT IDALOJAMIENTO, NOMBRE,(OCUPACIONACTUAL / CAPACIDAD)*100 INDICEOCUPACION FROM ";
		sql+=pp.darTablaAlojamiento();
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}
	
	// RFC4: MOSTRAR LOS ALOJAMIENTOS DISPONIBLES EN UN RANGO DE FECHAS, QUE CUMPLEN CON UN CONJUNTO DE SERVICIOS
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informacion de los Alojamientos disponibles en un rango de fechas que cuenten con
	 * ciertos servicios de la base de datos de AlohaAndes.  
	 * @param pm - El manejador de persistencia
	 * @return Una lista de arreglos de objetos. Los elementos del arreglo corresponden a los datos del aloojamientos disponibless.
	 */
	public List<Alojamiento> darAlojamientosDisponibles (PersistenceManager pm, Date fecha1, Date fecha2, String nombreServicio)
	{
	    String sql = "SELECT A_Alojamiento.*";
	    sql += " FROM " + pp.darTablaAlojamiento ();
	    sql += " LEFT OUTER JOIN " + pp.darTablaDispone () + " ON A_Alojamiento.idalojamiento = A_Dispone.idalojamiento";
		sql += " LEFT OUTER JOIN " + pp.darTablaServicio () + " ON A_Servicio.idservicio = A_Dispone.idservicio";
		sql += " LEFT OUTER JOIN " + pp.darTablaReserva () + " ON A_Reserva.idalojamiento = A_Alojamiento.idalojamiento";
	    sql	+= " WHERE (((A_Reserva.fechainicio NOT BETWEEN ? AND ?) AND (A_Reserva.fechafinal NOT BETWEEN ? AND ?))";
	    sql	+= " OR ((A_Reserva.fechainicio BETWEEN ? AND ?) OR (A_Reserva.fechafinal BETWEEN ? AND ?)";
		sql	+= " AND A_Reserva.estado = 'Cancelada'))";
	    sql	+= " AND A_Servicio.nombre = ?";
		sql	+= " AND A_Alojamiento.estado = 'Habilitado'";
		sql	+= " GROUP BY A_Alojamiento.idalojamiento, A_Alojamiento.nombre, A_Alojamiento.capacidad , A_Alojamiento.ubicacion, A_Alojamiento.tamano, A_Alojamiento.precionoche, A_Alojamiento.ocupacionactual, A_Alojamiento.numreservas, A_Alojamiento.idoperador, A_Alojamiento.estado, A_Alojamiento.tipo";

	    Query q = pm.newQuery(SQL, sql);
		q.setParameters(fecha1, fecha2, fecha1, fecha2, fecha1, fecha2, fecha1, fecha2, nombreServicio);
		q.setResultClass(Alojamiento.class);
		return (List<Alojamiento>) q.executeList();
	}

	// RFC9 - ENCONTRAR LAS OFERTAS DE ALOJAMIENTO QUE NO TIENEN MUCHA DEMANDA
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informacion de los alojamientos que no tienen mucha demanda que no hayan sido reservados en el ultmos mes.  
	 * @param pm - El manejador de persistencia
	 * @return Una lista de arreglos de objetos. Los elementos del arreglo corresponden a los datos de los alojamientos con poca demanda.
	 */
	public List<Alojamiento> encontrarOfertasConBajaDemanda (PersistenceManager pm)
	{
	    String sql = "SELECT A_Alojamiento.*";
	    sql += " FROM " + pp.darTablaAlojamiento ();
		sql += " LEFT OUTER JOIN " + pp.darTablaReserva () + " ON A_Reserva.idalojamiento = A_Alojamiento.idalojamiento";
	    sql	+= " WHERE A_alojamiento.idAlojamiento NOT IN ( SELECT A_reserva.idAlojamiento";
		sql	+= " FROM A_reserva";
		sql	+= " WHERE NOT((A_Reserva.fechaInicio IS NULL) OR ((CURRENT_DATE - 30) > A_Reserva.fechaInicio  AND  (CURRENT_DATE - 30) > A_Reserva.fechaFinal OR (A_Reserva.estado = 'Cancelada')) OR (CURRENT_DATE < A_Reserva.fechaInicio  AND  CURRENT_DATE < A_Reserva.fechaFinal OR (A_Reserva.estado = 'Cancelada'))))";
		sql	+= " GROUP BY A_Alojamiento.idalojamiento, A_Alojamiento.nombre, A_Alojamiento.capacidad , A_Alojamiento.ubicacion, A_Alojamiento.tamano, A_Alojamiento.precionoche, A_Alojamiento.ocupacionactual, A_Alojamiento.numreservas, A_Alojamiento.idoperador, A_Alojamiento.estado, A_Alojamiento.tipo";
		sql	+= " ORDER BY A_Alojamiento.idalojamiento";

	    Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Alojamiento.class);
		return (List<Alojamiento>) q.executeList();
	}
	

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


	// RF6: RETIRAR UNA OFERTA DE ALOJAMIENTO
	 /**
	 * Crea y ejecuta la sentencia SQL para eliminar UN ALOJAMIENTO de la base de datos de Alohaandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id del alojamiento
	 * @return EL número de tuplas eliminadas
	 */
	public long retirarOfertaAlojamiento (PersistenceManager pm, long idAlojamiento)
	{
		String sql = "DELETE FROM " + pp.darTablaReserva() + " WHERE idalojamiento = ?";
		sql	+= " AND current_date > ALL(SELECT fechafinal";
	    sql	+= " FROM " + pp.darTablaReserva() ;
		sql	+= " LEFT OUTER JOIN "  + pp.darTablaAlojamiento() + " ON A_Reserva.idAlojamiento = A_Alojamiento.idAlojamiento";
	    sql	+= " WHERE A_Alojamiento.idalojamiento = ? AND A_Reserva.estado = 'Activa')";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(idAlojamiento, idAlojamiento);
        return (long) q.executeUnique();
	}

	// RF10 - REHABILITAR OFERTA DE ALOJAMIENTO
	/**
	 * Crea y ejecuta la sentencia SQL para rehabilitar una reserva de la base de datos de Alohaandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El id del alojamiento
	 * @return EL número de tuplas modificadas
	 */
	public long rehabilitarAlojamiento( PersistenceManager pm, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaAlojamiento() + " SET A_alojamiento.estado = 'Habilitado' WHERE A_alojamiento.idAlojamiento = ? AND A_alojamiento.estado = 'Deshabilitado'");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
	}
	// RFC7B:ANALIZAR LA OPERACIÓN DE ALOHANDES- Mayor dinero
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la smeana de mayor dinero recaudado
	 */
	public List<Object> darMayorDineroSemana (PersistenceManager pm,String tipoAlojamiento)
	{
		String sql = "WITH ReservasPorSemana AS (SELECT TRUNC(FECHAINICIO, 'IW') AS Semana, SUM(COSTOTOTAL) AS Ingresos FROM ";
		sql+=pp.darTablaReserva()+" R JOIN "+pp.darTablaAlojamiento()+" A ON R.IDALOJAMIENTO = A.IDALOJAMIENTO WHERE R.ESTADO = 'Finalizada' AND A.TIPO = ? GROUP BY TRUNC(FECHAINICIO, 'IW')) ";
		sql+="SELECT Semana, Ingresos FROM ReservasPorSemana WHERE Ingresos = (SELECT MAX(Ingresos) FROM ReservasPorSemana)";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(tipoAlojamiento);
		return q.executeList();
	}
	// RFC7A:ANALIZAR LA OPERACIÓN DE ALOHANDES- Mayor Ocupacion
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la smeana de mayor ocupación
	 */
	public List<Object> darMayorOcupacionSemana (PersistenceManager pm,String tipoAlojamiento)
	{
		String sql = "WITH ReservasPorSemana AS (SELECT TRUNC(FECHAINICIO, 'IW') AS Semana, COUNT(IDRESERVA) AS Ocupacion FROM ";
		sql+=pp.darTablaReserva()+" R JOIN "+pp.darTablaAlojamiento()+" A ON R.IDALOJAMIENTO = A.IDALOJAMIENTO WHERE TIPO = ? AND (R.ESTADO = 'Finalizada') GROUP BY TRUNC(FECHAINICIO, 'IW')) ";
		sql+="SELECT Semana, Ocupacion FROM ReservasPorSemana WHERE Ocupacion = (SELECT MAX(Ocupacion) FROM ReservasPorSemana)";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(tipoAlojamiento);
		return q.executeList();
	}
	// RFC7C:ANALIZAR LA OPERACIÓN DE ALOHANDES- Menor ocupacion
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la smeana de mayor ocupación
	 */
	public List<Object> darMenorOcupacionSemana (PersistenceManager pm,String tipoAlojamiento)
	{
		String sql = "WITH ReservasPorSemana AS (SELECT TRUNC(FECHAINICIO, 'IW') AS Semana, COUNT(IDRESERVA) AS Ocupacion FROM ";
		sql+=pp.darTablaReserva()+" R JOIN "+pp.darTablaAlojamiento()+" A ON R.IDALOJAMIENTO = A.IDALOJAMIENTO WHERE TIPO = ? AND (R.ESTADO = 'Finalizada') GROUP BY TRUNC(FECHAINICIO, 'IW')) ";
		sql+="SELECT Semana, Ocupacion FROM ReservasPorSemana WHERE Ocupacion = (SELECT MIN(Ocupacion) FROM ReservasPorSemana)";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(tipoAlojamiento);
		return q.executeList();
	}
	
	public long deshabilitarAlojamiento( PersistenceManager pm, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaAlojamiento() + " SET A_alojamiento.estado = 'Deshabilitado' WHERE A_alojamiento.idAlojamiento = ? AND A_alojamiento.estado = 'Habilitado'");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
	}
}
