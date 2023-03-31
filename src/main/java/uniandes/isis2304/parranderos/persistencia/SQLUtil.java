package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que realiza la limpieza a la base de datos de AlohaAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Sebastian Sanchez 
 */
class SQLUtil
{
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
	public SQLUtil (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ pp.darSeqParranderos () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 16 números que indican el número de tuplas borradas en las tablas OPERADOR, EMPRESA, PROPIETARIO, ALOJAMIENTO,
	 * VIVIENDAUNIVERSITARIA, HABITACIONVIVIENDA, APARTAMENTO, HABITACIONHOTEL, HOSTAL, VIVIENDATEMPORAL, CLIENTE, MIEMBROACTIVO, MIEMBROSECUNDARIO,
	 * SERVICIO, DISPONE y RESERVA, respectivamente
	 */
	public long [] limpiarParranderos (PersistenceManager pm)
	{
        Query qReserva = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva ());          
        Query qDispone = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaDispone ());
		Query qPropietario = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPropietario ());
        Query qEmpresa = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEmpresa ());
		Query qViviendaUniversitaria = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaViviendaUniversitaria ());          
        Query qHabitacionVivienda = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHabitacionVivienda ());
        Query qApartamento = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaApartamento ());
        Query qHabitacionHotel = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHabitacionHotel ());
        Query qHostal = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHostal ());
        Query qViviendaTemporal = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaViviendaTemporal ());
        Query qMiembroActivo = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaMiembroActivo ());
		Query qMiembroSecundario= pm.newQuery(SQL, "DELETE FROM " + pp.darTablaMiembroSecundario ());
		Query qCliente = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente ());
        Query qAlojamiento = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlojamiento ());
        Query qOperador = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOperador ());         
        Query qServicio = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServicio ());


        long reservaEliminadas = (long) qReserva.executeUnique ();
        long disponeEliminados = (long) qDispone.executeUnique ();
		long propietarioEliminados = (long) qPropietario.executeUnique ();
        long empresaEliminadas = (long) qEmpresa.executeUnique ();
		long viviendaUniversitariaEliminadas = (long) qViviendaUniversitaria.executeUnique ();
        long habitacionViviendaEliminadas = (long) qHabitacionVivienda.executeUnique ();
        long apartamentoEliminados = (long) qApartamento.executeUnique ();
		long habitacionHotelEliminadas = (long) qHabitacionHotel.executeUnique ();
        long hostalEliminados = (long) qHostal.executeUnique ();
        long viviendaTemporalEliminadas = (long) qViviendaTemporal.executeUnique ();
		long miembroActivoEliminados = (long) qMiembroActivo.executeUnique ();
        long miembroSecundarioEliminados = (long) qMiembroSecundario.executeUnique ();
		long clienteEliminados = (long) qCliente.executeUnique ();
        long alojamientoEliminados = (long) qAlojamiento.executeUnique ();
        long operadorEliminados = (long) qOperador.executeUnique ();
		long servicioEliminados = (long) qServicio.executeUnique ();

        return new long[] {reservaEliminadas, disponeEliminados, propietarioEliminados, empresaEliminadas, viviendaUniversitariaEliminadas, habitacionViviendaEliminadas, apartamentoEliminados, habitacionHotelEliminadas, 
			hostalEliminados, viviendaTemporalEliminadas, miembroActivoEliminados, miembroSecundarioEliminados, clienteEliminados, alojamientoEliminados, 
			operadorEliminados, servicioEliminados};
	}

}
