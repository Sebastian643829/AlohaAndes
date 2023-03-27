package uniandes.isis2304.parranderos.persistencia;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.Operador;
import uniandes.isis2304.parranderos.negocio.Propietario;
import uniandes.isis2304.parranderos.negocio.Empresa;
import uniandes.isis2304.parranderos.negocio.Alojamiento;
import uniandes.isis2304.parranderos.negocio.ViviendaUniversitaria;
import uniandes.isis2304.parranderos.negocio.HabitacionVivienda;
import uniandes.isis2304.parranderos.negocio.Apartamento;
import uniandes.isis2304.parranderos.negocio.HabitacionHotel;
import uniandes.isis2304.parranderos.negocio.Hostal;
import uniandes.isis2304.parranderos.negocio.ViviendaTemporal;
import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.MiembroActivo;
import uniandes.isis2304.parranderos.negocio.MiembroSecundario;
import uniandes.isis2304.parranderos.negocio.Servicio;
import uniandes.isis2304.parranderos.negocio.Dispone;
import uniandes.isis2304.parranderos.negocio.Reserva;

/**
 * Clase para el manejador de persistencia del proyecto Alohaandes
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLOperador, SQLPropietario, SQLEmpresa, SQLAlojamiento, SQLViviendaUniversitaria, SQLHabitacionVivienda, SQLApartamento, SQLHabitacionHotel, 
 * SQLHostal, SQLViviendaTemporal, SQLCliente, SQLMiembroActivo, SQLMiembroSecundario, SQLServicio, SQLDispone y SQLReserva, que son 
 * las que realizan el acceso a la base de datos
 * 
 * @author Juan Sebastian Sanchez
 */
public class PersistenciaParranderos 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaParranderos.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaParranderos instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;

	/**
	 * Atributo para el acceso a la tabla ALOJAMIENTO de la base de datos
	 */
	private SQLAlojamiento sqlAlojamiento;
	
	/**
	 * Atributo para el acceso a la tabla SERVICIO de la base de datos
	 */
	private SQLServicio sqlServicio;
	
	/**
	 * Atributo para el acceso a la tabla DISPONE de la base de datos
	 */
	private SQLDispone sqlDispone;
	
	/**
	 * Atributo para el acceso a la tabla VIVIENDAUNIVERSITARIA de la base de datos
	 */
	private SQLViviendaUniversitaria sqlViviendaUniversitaria;
	
	/**
	 * Atributo para el acceso a la tabla HABITACIONVIVIENDA de la base de datos
	 */
	private SQLHabitacionVivienda sqlHabitacionVivienda;
	
	/**
	 * Atributo para el acceso a la tabla APARTAMENTO de la base de datos
	 */
	private SQLApartamento sqlApartamento;
	
	/**
	 * Atributo para el acceso a la tabla HABITACIONHOTEL de la base de datos
	 */
	private SQLHabitacionHotel sqlHabitacionHotel;

	/**
	 * Atributo para el acceso a la tabla HOSTAL de la base de datos
	 */
	private SQLHostal sqlHostal;
	
	/**
	 * Atributo para el acceso a la tabla VIVIENDATEMPORAL de la base de datos
	 */
	private SQLViviendaTemporal sqlViviendaTemporal;

	// PENDIENTE: CREAR ATRIBUTOS DE AERCHIVOS SQL RESTANTES

	
	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaParranderos ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Alohaandes");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("alohaandes_sequence");
		tablas.add ("OPERADOR");
		tablas.add ("PROPIETARIO");
		tablas.add ("EMPRESA");
		tablas.add ("ALOJAMIENTO");
		tablas.add ("VIVIENDAUNIVERSITARIA");
		tablas.add ("HABITACIONVIVIENDA");
		tablas.add ("APARTAMENTO");
		tablas.add ("HABITACIONHOTEL");
		tablas.add ("HOSTAL");
		tablas.add ("VIVIENDATEMPORAl");
		tablas.add ("CLIENTE");
		tablas.add ("MIEMBROACTIVO");
		tablas.add ("MIEMBROSECUNDARIO");
		tablas.add ("SERVICIO");
		tablas.add ("DISPONE");
		tablas.add ("RESERVA");
}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaParranderos (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaAlohaandes existente - Patrón SINGLETON
	 */
	public static PersistenciaParranderos getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaParranderos ();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaParranderos getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaParranderos (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlAlojamiento = new SQLAlojamiento(this);
		sqlServicio = new SQLServicio(this);
		sqlDispone = new SQLDispone(this);
		sqlViviendaUniversitaria = new SQLViviendaUniversitaria(this);
		sqlHabitacionVivienda = new SQLHabitacionVivienda(this);
		sqlApartamento = new SQLApartamento (this);
		sqlHabitacionHotel = new SQLHabitacionHotel(this);
		sqlHostal = new SQLHostal (this);
		sqlViviendaTemporal= new SQLViviendaTemporal(this);

		// PENDIENTE: CREAR CLASES DE SQL RESTANTES

		sqlUtil = new SQLUtil(this); // NO borrar
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de alohaandes
	 */
	public String darSeqParranderos ()
	{
		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Operador de alohaandes
	 */
	public String darTablaOperador ()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Propietario de alohaandes
	 */
	public String darTablaPropietario ()
	{
		return tablas.get (2);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Empresa de alohaandes
	 */
	public String darTablaEmpresa ()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Alojamiento de alohaandes
	 */
	public String darTablaAlojamiento()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de ViviendaUniversitaria de alohaandes
	 */
	public String darTablaViviendaUniversitaria ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de HabitacionVivienda de alohaandes
	 */
	public String darTablaHabitacionVivienda ()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Apartamento de alohaandes
	 */
	public String darTablaApartamento ()
	{
		return tablas.get (7);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de HabitacionHotel de alohaandes
	 */
	public String darTablaHabitacionHotel ()
	{
		return tablas.get (8);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Hostal de alohaandes
	 */
	public String darTablaHostal ()
	{
		return tablas.get (9);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de ViviendaTemporal de alohaandes
	 */
	public String darTablaViviendaTemporal ()
	{
		return tablas.get (10);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Cliente de alohaandes
	 */
	public String darTablaCliente()
	{
		return tablas.get (11);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de MiembroActivo de alohaandes
	 */
	public String darTablaMiembroActivo ()
	{
		return tablas.get (12);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de MiembroSecundario de alohaandes
	 */
	public String darTablaMiembroSecundario ()
	{
		return tablas.get (13);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Servicio de alohaandes
	 */
	public String darTablaServicio ()
	{
		return tablas.get (14);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Dispone de alohaandes
	 */
	public String darTablaDispone()
	{
		return tablas.get (15);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de alohaandes
	 */
	public String darTablaReserva ()
	{
		return tablas.get (16);
	}
	
	/**
	 * Transacción para el generador de secuencia de Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Alohaandes
	 */
	private long nextval ()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}


	/* ****************************************************************
	 * 			Métodos para manejar los OPERADORES
	 *****************************************************************/


	 /* ****************************************************************
	 * 			Métodos para manejar los PROPIETARIOS
	 *****************************************************************/


	 /* ****************************************************************
	 * 			Métodos para manejar las EMPRESAS
	 *****************************************************************/


	 /* ****************************************************************
	 * 			Métodos para manejar los ALOJAMIENTOS
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Alojamiento
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id del alojamiento
	 * @param nombre - El nombre del alojamiento
	 * @param capacidad - La capacidad del alojamiento
     * @param ubicacion - La ubicacion del alojamiento
	 * @param tamano - El tamaño del alojamiento
	 * @param precioNoche - El precio de la noche del alojamiento
     * @param ocupacionTotal - La ocupacion total actual del alojamiento
	 * @param numReservas - El numero de reservas del alojamiento
	 * @param idOperador - El id del operador
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepción
	 */
	public Alojamiento adicionarAlojamiento(String nombre, int capacidad ,String ubicacion, int tamano, int precioNoche, int ocupacionTotal, int numReservas, long idOperador)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idAlojamiento = nextval ();
            long tuplasInsertadas = sqlAlojamiento.adicionarAlojamiento(pm, idAlojamiento, nombre, capacidad, ubicacion, tamano, precioNoche, ocupacionTotal, numReservas, idOperador );
            tx.commit();
            
            log.trace ("Inserción de alojamiento: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Alojamiento (idAlojamiento, nombre, capacidad, ubicacion, tamano, precioNoche, ocupacionTotal, numReservas, idOperador );
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Alojamiento, dado el identificador del tipo de bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarAlojamientoPorId (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlAlojamiento.eliminarAlojamientoPorId(pm, idAlojamiento);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Alojamiento
	 * @return La lista de objetos Alojamiento, construidos con base en las tuplas de la tabla ALOJAMIENTO
	 */
	public List<Alojamiento> darAlojamientos ()
	{
		return sqlAlojamiento.darAlojamientos (pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Alojamiento con un identificador dado
	 * @param idAlojamiento - El id del alojamiento
	 * @return El objeto Alojamiento, construido con base en las tuplas de la tabla Alojamiento con el identificador dado
	 */
	public Alojamiento darAlojamientoPorId (long idAlojamiento)
	{
		return sqlAlojamiento.darAlojamientoPorId (pmf.getPersistenceManager(), idAlojamiento);
	}


	/**
	 * Método que actualiza, de manera transaccional, aumentando en 1 la ocupacion actual de un alojamiento
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas modificadas. -1 si ocurre alguna Excepción
	 */
	public long aumentarOcupacionTotalAlojamiento (long idAlojamiento)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlAlojamiento.aumentarOcupacionTotalAlojamiento(pm, idAlojamiento);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que actualiza, de manera transaccional, aumentando en 1 el número de reservas de un alojamiento
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas modificadas. -1 si ocurre alguna Excepción
	 */
	public long aumentarNumeroReservasAlojamiento (long idAlojamiento)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlAlojamiento.aumentarNumeroReservasAlojamiento(pm, idAlojamiento);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}



	 /* ****************************************************************
	 * 			Métodos para manejar las VIVIENDAS UNIVERSITARIAS
	 *****************************************************************/

	  /**
	 * Método que inserta, de manera transaccional, una tupla en la tabla ViviendaUniversitaria
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id de la vivienda universitaria
	 * @param tipoHabitacion - El tipo de habitacion de la vivienda universitaria
	 * @return El objeto ViviendaUniversitaria adicionado. null si ocurre alguna Excepción
	 */
	public ViviendaUniversitaria adicionarViviendaUniversitaria(long idAlojamiento,String tipoHabitacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlViviendaUniversitaria.adicionarViviendaUniversitaria(pm, idAlojamiento, tipoHabitacion);
            tx.commit();
            
            log.trace ("Inserción de Viviendauniversitaria: " + idAlojamiento + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new ViviendaUniversitaria (idAlojamiento, tipoHabitacion);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla ViviendaUniversitaria, dado el identificador del ViviendaUniversitaria
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id de la vivienda universitaria
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarViviendaUniversitariaPorId (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlViviendaUniversitaria.eliminarViviendaUniversitariaPorId(pm, idAlojamiento);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla ViviendasUniversitarias
	 * @return La lista de objetos ViviendasUniversitarias, construidos con base en las tuplas de la tabla darViviendasUniversitarias
	 */
	public List<ViviendaUniversitaria> darViviendasUniversitarias ()
	{
		return sqlViviendaUniversitaria.darViviendasUniversitarias (pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla ViviendaUniversitaria con un identificador dado
	 * @param idAlojamiento - El id de la vivienda universitaria
	 * @return El objeto ViviendaUniversitaria, construido con base en las tuplas de la tabla ViviendaUniversitaria con el identificador dado
	 */
	public ViviendaUniversitaria darViviendaUniversitariaPorId (long idAlojamiento )
	{
		return sqlViviendaUniversitaria.darViviendaUniversitariaPorId (pmf.getPersistenceManager(), idAlojamiento );
	}


	 /* ****************************************************************
	 * 			Métodos para manejar las HABITACIONES DE VIVIENDAS
	 *****************************************************************/

	 /**
	 * Método que inserta, de manera transaccional, una tupla en la tabla HabitacionVivienda
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id de la habitacion de la vivienda
	 * @param tipoBano - El tipo de baño de la habitacion de la vivienda
	 * @param tipoHabitacion - El tipo de habitacion de la vivienda
	 * @return El objeto HabitacionVivienda adicionado. null si ocurre alguna Excepción
	 */
	public HabitacionVivienda adicionarHabitacionVivienda(long idAlojamiento, String tipoBano, String tipoHabitacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlHabitacionVivienda.adicionarHabitacionVivienda(pm, idAlojamiento, tipoBano, tipoHabitacion);
            tx.commit();
            
            log.trace ("Inserción de HabitacionVivienda: " + idAlojamiento + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new HabitacionVivienda (idAlojamiento, tipoBano, tipoHabitacion);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla HabitacionVivienda, dado el identificador del HabitacionVivienda
	 * Adiciona entradas al log de la aplicación
	* @param idAlojamiento - El id de la habitacion de la vivienda
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarHabitacionViviendaPorId (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlHabitacionVivienda.eliminarHabitacionViviendaPorId(pm, idAlojamiento);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla HabitacionVivienda
	 * @return La lista de objetos HabitacionVivienda, construidos con base en las tuplas de la tabla HabitacionVivienda
	 */
	public List<HabitacionVivienda> darHabitacionesViviendas ()
	{
		return sqlHabitacionVivienda.darHabitacionesViviendas (pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla HabitacionVivienda con un identificador dado
	 * @param idAlojamiento - El id de la habitacion de la vivienda
	 * @return El objeto HabitacionVivienda, construido con base en las tuplas de la tabla HabitacionVivienda con el identificador dado
	 */
	public HabitacionVivienda darHabitacionViviendaPorId (long idAlojamiento )
	{
		return sqlHabitacionVivienda.darHabitacionViviendaPorId (pmf.getPersistenceManager(), idAlojamiento );
	}


	 /* ****************************************************************
	 * 			Métodos para manejar los APARTAMENTOS
	 *****************************************************************/
	
	 /**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Apartamento
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id del apartamento
	 * @return El objeto Apartamento adicionado. null si ocurre alguna Excepción
	 */
	public Apartamento adicionarApartamento(long idAlojamiento)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlApartamento.adicionarApartamento(pm, idAlojamiento);
            tx.commit();
            
            log.trace ("Inserción de Apartamento: " + idAlojamiento + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Apartamento (idAlojamiento);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Apartamento, dado el identificador del Apartamento
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id del apartamento
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarApartamentoPorId (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlApartamento.eliminarApartamentoPorId(pm, idAlojamiento);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Apartamento
	 * @return La lista de objetos Apartamento, construidos con base en las tuplas de la tabla Apartamento
	 */
	public List<Apartamento> darApartamentos ()
	{
		return sqlApartamento.darApartamentos (pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Apartamento con un identificador dado
	 * @param idAlojamiento - El id del apartamento
	 * @return El objeto Apartamento, construido con base en las tuplas de la tabla Apartamento con el identificador dado
	 */
	public Apartamento darApartamentoPorId (long idAlojamiento )
	{
		return sqlApartamento.darApartamentoPorId (pmf.getPersistenceManager(), idAlojamiento );
	}
	 


	 /* ****************************************************************
	 * 			Métodos para manejar las HABITACIONES DE HOTEL
	 *****************************************************************/

	 /**
	 * Método que inserta, de manera transaccional, una tupla en la tabla HabitacionHotel
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id de la habitacion de hotel
	 * @param tipoHabitacion - El tipo de habitacion de la habitacion de hotel
	 * @return El objeto HabitacionHotel adicionado. null si ocurre alguna Excepción
	 */
	public HabitacionHotel adicionarHabitacionHotel(long idAlojamiento, String tipoHabitacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlHabitacionHotel.adicionarHabitacionHotel(pm, idAlojamiento, tipoHabitacion);
            tx.commit();
            
            log.trace ("Inserción de HabitacionHotel: " + idAlojamiento + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new HabitacionHotel (idAlojamiento, tipoHabitacion);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla HabitacionHotel, dado el identificador del HabitacionHotel
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id de la habitacion de hotel
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarHabitacionHotelPorId (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlHabitacionHotel.eliminarHabitacionHotelPorId(pm, idAlojamiento);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla HabitacionHotel
	 * @return La lista de objetos HabitacionHotel, construidos con base en las tuplas de la tabla HabitacionHotel
	 */
	public List<HabitacionHotel> darHabitacionesHoteles ()
	{
		return sqlHabitacionHotel.darHabitacionesHoteles (pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla HabitacionHotel con un identificador dado
	 * @param idAlojamiento - El id de la habitacion de hotel
	 * @return El objeto HabitacionHotel, construido con base en las tuplas de la tabla HabitacionHotel con el identificador dado
	 */
	public HabitacionHotel darHabitacionHotelPorId (long idAlojamiento )
	{
		return sqlHabitacionHotel.darHabitacionHotelPorId (pmf.getPersistenceManager(), idAlojamiento );
	}

	 /* ****************************************************************
	 * 			Métodos para manejar los HOSTALES
	 *****************************************************************/

	 /**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Hostal
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id de la habitacion del hostal
	 * @param horarioApertura- El horario de apertura del hostal
	 * @param horarioCierre - El horario del cierre del hostal
	 * @return El objeto Hostal adicionado. null si ocurre alguna Excepción
	 */
	public Hostal adicionarHostal(long idAlojamiento, String horarioApertura, String horarioCierre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlHostal.adicionarHostal(pm, idAlojamiento, horarioApertura, horarioCierre);
            tx.commit();
            
            log.trace ("Inserción de Hostal: " + idAlojamiento + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Hostal (idAlojamiento, horarioApertura, horarioCierre);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Hostal, dado el identificador del Hostal
	 * @param idAlojamiento - El id de la habitacion del hostal
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarHostalPorId (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlHostal.eliminarHostalPorId(pm, idAlojamiento);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Hostal
	 * @return La lista de objetos Hostal, construidos con base en las tuplas de la tabla Hostal
	 */
	public List<Hostal> darHostales ()
	{
		return sqlHostal.darHostales (pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Hostal con un identificador dado
	 * @param idAlojamiento - El id del hostal
	 * @return El objeto Hostal, construido con base en las tuplas de la tabla Hostal con el identificador dado
	 */
	public Hostal darHostalPorId (long idAlojamiento )
	{
		return sqlHostal.darHostalPorId (pmf.getPersistenceManager(), idAlojamiento );
	}

	 /* ****************************************************************
	 * 			Métodos para manejar las VIVIENDAS TEMPORALES
	 *****************************************************************/

	 /**
	 * Método que inserta, de manera transaccional, una tupla en la tabla ViviendaTemporal
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id de la vivienda temporal
	 * @param numHabitaciones - El numero de habitaciones de la vivienda temporal
	 * @return El objeto ViviendaTemporal adicionado. null si ocurre alguna Excepción
	 */
	public ViviendaTemporal adicionarViviendaTemporal(long idAlojamiento, int numHabitaciones)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlViviendaTemporal.adicionarViviendaTemporal(pm, idAlojamiento, numHabitaciones);
            tx.commit();
            
            log.trace ("Inserción de ViviendaTemporal: " + idAlojamiento + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new ViviendaTemporal (idAlojamiento, numHabitaciones);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla ViviendaTemporal, dado el identificador de la ViviendaTemporal
	 * @param idAlojamiento - El id de la vivienda temporal
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarViviendaTemporalPorId (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlViviendaTemporal.eliminarViviendaTemporalPorId(pm, idAlojamiento);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla ViviendaTemporal
	 * @return La lista de objetos ViviendaTemporal, construidos con base en las tuplas de la tabla ViviendaTemporal
	 */
	public List<ViviendaTemporal> darViviendasTemporales ()
	{
		return sqlViviendaTemporal.darViviendasTemporales (pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla ViviendaTemporal con un identificador dado
	 * @param idAlojamiento - El id de la vivienda temporal
	 * @return El objeto ViviendaTemporal, construido con base en las tuplas de la tabla ViviendaTemporal con el identificador dado
	 */
	public ViviendaTemporal darViviendaTemporalPorId (long idAlojamiento )
	{
		return sqlViviendaTemporal.darViviendaTemporalPorId (pmf.getPersistenceManager(), idAlojamiento );
	}

	 /* ****************************************************************
	 * 			Métodos para manejar los CLIENTES
	 *****************************************************************/


	 /* ****************************************************************
	 * 			Métodos para manejar los MIEMBROS ACTIVOS
	 *****************************************************************/


	 /* ****************************************************************
	 * 			Métodos para manejar los MIEMBROS SECUNDARIOS
	 *****************************************************************/


	 /* ****************************************************************
	 * 			Métodos para manejar los SERVICIOS
	 *****************************************************************/

	 /**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Servicio
	 * Adiciona entradas al log de la aplicación
	 * @param idServicio - El id del servicio
	 * @param nombre - El nombre del servicio
     * @param costo - El costo del servicio
	 * @return El objeto Servicio adicionado. null si ocurre alguna Excepción
	 */
	public Servicio adicionarServicio(String nombre, int costo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idServicio = nextval ();
            long tuplasInsertadas = sqlServicio.adicionarServicio(pm, idServicio, nombre, costo );
            tx.commit();
            
            log.trace ("Inserción de servicio: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Servicio (idServicio, nombre, costo);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Servicio, dado el identificador del Servicio
	 * Adiciona entradas al log de la aplicación
	 * @param idServicio - El id del servicio
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarServicioPorId (long idServicio) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlServicio.eliminarServicioPorId(pm, idServicio);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Servicio
	 * @return La lista de objetos Servicio, construidos con base en las tuplas de la tabla SERVICIO
	 */
	public List<Servicio> darServicios ()
	{
		return sqlServicio.darServicios (pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Servicio con un identificador dado
	 * @param idServicio - El id del servicio
	 * @return El objeto Servicio, construido con base en las tuplas de la tabla Servicio con el identificador dado
	 */
	public Servicio  darServicioPorId (long idServicio )
	{
		return sqlServicio.darServicioPorId (pmf.getPersistenceManager(), idServicio );
	}


	 /* ****************************************************************
	 * 			Métodos para manejar la tabla de DISPONE
	 *****************************************************************/

	 /**
	 * Método que inserta, de manera transaccional, una tupla en la tabla DISPONE
	 * Adiciona entradas al log de la aplicación
	 * @param idServicio - El id del servicio
	 * @param idAlojamiento - El id del alojamiento
	 * @return Un objeto DISPONE con la información dada. Null si ocurre alguna Excepción
	 */
	public Dispone adicionarDispone (long idServicio, long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlDispone.adicionarDispone (pmf.getPersistenceManager(), idServicio, idAlojamiento);
    		tx.commit();

            log.trace ("Inserción de dispone: [" + idServicio + ", " + idAlojamiento + "]. " + tuplasInsertadas + " tuplas insertadas");

            return new Dispone (idServicio, idAlojamiento );
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
 
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla DISPONE, dados los identificadores de servicio y alojamiento
	 * @param idServicio - El id del servicio
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarDispone (long idServicio, long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
	        Transaction tx=pm.currentTransaction();
	        try
	        {
	            tx.begin();
	            long resp = sqlDispone.eliminarDispone (pm, idServicio, idAlojamiento);	            
	            tx.commit();

	            return resp;
	        }
	        catch (Exception e)
	        {
//	        	e.printStackTrace();
	        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
	        	return -1;
	        }
	        finally
	        {
	            if (tx.isActive())
	            {
	                tx.rollback();
	            }
	            pm.close();
	        }
	}
 
	/**
	 * Método que consulta todas las tuplas en la tabla DISPONE
	 * @return La lista de objetos DISPONE, construidos con base en las tuplas de la tabla DISPONE
	 */
	public List<Dispone> darDispone ()
	{
		return sqlDispone.darDispone (pmf.getPersistenceManager());
	}

 
	 /* ****************************************************************
	 * 			Métodos para manejar las RESERVAS
	 *****************************************************************/




	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 16 números que indican el número de tuplas borradas en las tablas OPERADOR, EMPRESA, PROPIETARIO, ALOJAMIENTO,
	 * VIVIENDAUNIVERSITARIA, HABITACIONVIVIENDA, APARTAMENTO, HABITACIONHOTEL, HOSTAL, VIVIENDATEMPORAL, CLIENTE, MIEMBROACTIVO, MIEMBROSECUNDARIO,
	 * SERVICIO, DISPONE y RESERVA, respectivamente
	 */
	public long [] limpiarParranderos ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlUtil.limpiarParranderos (pm);
            tx.commit ();
            log.info ("Borrada la base de datos");
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}
	

 }
