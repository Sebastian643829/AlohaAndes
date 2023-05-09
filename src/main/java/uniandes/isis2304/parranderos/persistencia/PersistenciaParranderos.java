package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Date;
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
	/**
	 * Atributo para el acceso a la tabla OPERADOR de la base de datos
	 */
	private SQLOperador sqlOperador;
	/**
	 * Atributo para el acceso a la tabla PROPIETARIO de la base de datos
	 */
	private SQLPropietario sqlPropietario;
	/**
	 * Atributo para el acceso a la tabla EMPRESA de la base de datos
	 */
	private SQLEmpresa sqlEmpresa;
	/**
	 * Atributo para el acceso a la tabla CLIENTE de la base de datos
	 */
	private SQLCliente sqlCliente;
	/**
	 * Atributo para el acceso a la tabla MIEMBROACTIVO de la base de datos
	 */
	private SQLMiembroActivo sqlMiembroActivo;
	/**
	 * Atributo para el acceso a la tabla MIEMBROSECUNDARIO de la base de datos
	 */
	private SQLMiembroSecundario sqlMiembroSecundario;
	/**
	 * Atributo para el acceso a la tabla RESERVA de la base de datos
	 */
	private SQLReserva sqlReserva;

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
		sqlOperador= new SQLOperador(this);
		sqlPropietario= new SQLPropietario(this);
		sqlEmpresa= new SQLEmpresa(this);
		sqlCliente= new SQLCliente(this);
		sqlMiembroActivo= new SQLMiembroActivo(this);
		sqlMiembroSecundario= new SQLMiembroSecundario(this);
		sqlReserva= new SQLReserva(this);

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
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Operador
	 * Adiciona entradas al log de la aplicación
	 * @param telefono - El nombre del operador
	 * @param tipoVinculación - La capacidad del operador
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepción
	 */
	public Operador adicionarOperador(String telefono, String tipoVinculacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idOperador = nextval ();
            long tuplasInsertadas = sqlOperador.adicionarOperador(pm, idOperador, telefono, tipoVinculacion);
            tx.commit();
            
            log.trace ("Inserción de operador: " + idOperador + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Operador (idOperador, telefono, tipoVinculacion);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla Operador, dado el identificador del mismo
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El id del operador
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOperadorPorId (long idOperador) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlOperador.eliminarOperadorPorId(pm, idOperador);
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
	 * Método que consulta todas las tuplas en la tabla Operador
	 * @return La lista de objetos Operador, construidos con base en las tuplas de la tabla ALOJAMIENTO
	 */
	public List<Operador> darOperadores ()
	{
		return sqlOperador.darOperadores(pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Operador con un identificador dado
	 * @param idOperador - El id del alojamiento
	 * @return El objeto Operador, construido con base en las tuplas de la tabla Operador con el identificador dado
	 */
	public Operador darOperadorPorId (long idOperador)
	{
		return sqlOperador.darOperadorPorId (pmf.getPersistenceManager(), idOperador);
	}

	 /* ****************************************************************
	 * 			Métodos para manejar los PROPIETARIOS
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Propietario
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El id del propietario
	 * @param identificacion - Número de identificación del propietario
     * @param tipoIdentificacion - Tipo de identificación del propietario
     * @param nombrePropietario - Nombre del propietario
	 * @return El objeto Propietario adicionado. null si ocurre alguna Excepción
	 */
	public Propietario adicionarPropietario(long idOperador, String identificacion, String tipoIdentificacion, String nombrePropietario)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlPropietario.adicionarPropietario(pm, idOperador, identificacion,tipoIdentificacion,nombrePropietario);
            tx.commit();
            
            log.trace ("Inserción de Propietario: " + nombrePropietario + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Propietario (idOperador, identificacion,tipoIdentificacion,nombrePropietario);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla Propietario, dado el identificador del Operador
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El id del propietario
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarPropietarioPorId (long idOperador) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlPropietario.eliminarPropietarioPorId(pm, idOperador);
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
	 * Método que consulta todas las tuplas en la tabla Propietario
	 * @return La lista de objetos Propietario, construidos con base en las tuplas de la tabla PROPIETARIO
	 */
	public List<Propietario> darPropietarios ()
	{
		return sqlPropietario.darPropietarios (pmf.getPersistenceManager());
	}
	/**
	 * Método que consulta todas las tuplas en la tabla Propietario con un identificador dado
	 * @param idOperador - El id del operador
	 * @return El objeto Propietario, construido con base en las tuplas de la tabla Propietario con el identificador dado
	 */
	public Propietario darPropietarioPorId (long idOperador)
	{
		return sqlPropietario.darPropietarioPorId (pmf.getPersistenceManager(), idOperador);
	}

	 /* ****************************************************************
	 * 			Métodos para manejar las EMPRESAS
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Empresa
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El id del empresa
	 * @param nit - Número de NIT del empresa
     * @param nombreEmpresa - Nombre del empresa
	 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
	 */
	public Empresa adicionarEmpresa(long idOperador, String nit, String nombreEmpresa)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlEmpresa.adicionarEmpresa(pm, idOperador, nit,nombreEmpresa);
            tx.commit();
            
            log.trace ("Inserción de Empresa: " + nombreEmpresa + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Empresa (idOperador, nit, nombreEmpresa);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla Empresa, dado el identificador del Operador
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El id del propietario
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarEmpresaPorId (long idOperador) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlEmpresa.eliminarEmpresaPorId(pm, idOperador);
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
	 * Método que consulta todas las tuplas en la tabla Empresa
	 * @return La lista de objetos Empresa, construidos con base en las tuplas de la tabla EMPRESA
	 */
	public List<Empresa> darEmpresas ()
	{
		return sqlEmpresa.darEmpresas (pmf.getPersistenceManager());
	}
	/**
	 * Método que consulta todas las tuplas en la tabla Empresa con un identificador dado
	 * @param idOperador - El id del operador
	 * @return El objeto Empresa, construido con base en las tuplas de la tabla Empresa con el identificador dado
	 */
	public Empresa darEmpresaPorId (long idOperador)
	{
		return sqlEmpresa.darEmpresaPorId (pmf.getPersistenceManager(), idOperador);
	}

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
	public Alojamiento adicionarAlojamiento(String nombre, int capacidad ,String ubicacion, int tamano, int precioNoche, int ocupacionTotal, int numReservas, long idOperador, String estado, String tipo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idAlojamiento = nextval ();
            long tuplasInsertadas = sqlAlojamiento.adicionarAlojamiento(pm, idAlojamiento, nombre, capacidad, ubicacion, tamano, precioNoche, ocupacionTotal, numReservas, idOperador, estado, tipo);
            tx.commit();
            
            log.trace ("Inserción de alojamiento: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Alojamiento (idAlojamiento, nombre, capacidad, ubicacion, tamano, precioNoche, ocupacionTotal, numReservas, idOperador, estado, tipo );
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
	public long revisarAlojamiento (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlAlojamiento.revisarAlojamiento(pm, idAlojamiento);
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

	// RFC1:MOSTRAR EL DINERO RECIBIDO POR CADA PROVEEDOR DE ALOJAMIENTO DURANTE EL AÑO ACTUAL Y EL AÑO CORRIDO
	/**
	 * Método que muestra el indice de ocupación DE CADA UNA DE LAS OFERTAS DE ALOJAMIENTO REGISTRADAS
	 * @return La lista de objetos alojamientos, construidos con base en las tuplas de la tabla ALOJAMIENTO
	 */
	public List<Object []> darDinero ()
	{
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlAlojamiento.darDinero (pmf.getPersistenceManager());
        for ( Object tupla : tuplas)
        {
			Object [] datos = (Object []) tupla;
			long idAlojamiento = ((BigDecimal) datos [0]).longValue ();
			long dinero = ((BigDecimal) datos [1]).longValue ();

			Object [] resp = new Object [2];
			resp [0] = idAlojamiento;
			resp [1] = dinero;
			
			respuesta.add(resp);
        }

		return respuesta;
	}
	// RFC2: MOSTRAR LAS 20 OFERTAS MÁS POPULARES
	/**
	 * Método que consultar los 20 alojamientos mas populares
	 * @return La lista de objetos alojamientos, construidos con base en las tuplas de la tabla ALOJAMIENTO
	 */
	public List<Alojamiento> darOfertasMasPopulares ()
	{
		return sqlAlojamiento.darOfertasMasPopulares(pmf.getPersistenceManager());
	}
	// RFC3: MOSTRAR EL ÍNDICE DE OCUPACIÓN DE CADA UNA DE LAS OFERTAS DE ALOJAMIENTO REGISTRADAS
	/**
	 * Método que muestra el indice de ocupación DE CADA UNA DE LAS OFERTAS DE ALOJAMIENTO REGISTRADAS
	 * @return La lista de objetos alojamientos, construidos con base en las tuplas de la tabla ALOJAMIENTO
	 */
	public List<Object []> darIndiceDeOcupacion ()
	{
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlAlojamiento.darIndiceDeOcupacion (pmf.getPersistenceManager());
        for ( Object tupla : tuplas)
        {
			Object [] datos = (Object []) tupla;
			long idAlojamiento = ((BigDecimal) datos [0]).longValue ();
			String nombreAlojamiento = (String) datos [1];
			long indicesOcupacion = ((BigDecimal) datos [2]).longValue ();

			Object [] resp = new Object [3];
			resp [0] = idAlojamiento;
			resp [1] = nombreAlojamiento;
			resp [2] = indicesOcupacion;
			
			respuesta.add(resp);
        }

		return respuesta;
	}

	// RFC4: MOSTRAR LOS ALOJAMIENTOS DISPONIBLES EN UN RANGO DE FECHAS, QUE CUMPLEN CON UN CONJUNTO DE SERVICIOS
	/**
	 * Método que consultar los alojamientos disponibles en cierto rango de fechas que cumplen con un conjunto de servicios
	 * @return La lista de objetos alojamientos, construidos con base en las tuplas de la tabla ALOJAMIENTO
	 */
	public List<Alojamiento> darAlojamientosDisponibles (Date fecha1, Date fecha2, String nombreServicio)
	{
		return sqlAlojamiento.darAlojamientosDisponibles(pmf.getPersistenceManager(), fecha1, fecha2, nombreServicio);
	}
	// RFC7B:ANALIZAR LA OPERACIÓN DE ALOHANDES- Mayor dinero
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la smeana de mayor dinero recaudado
	 */
	public List<Object []> darMayorDineroSemana (String tipoAlojamiento)
	{
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlAlojamiento.darMayorDineroSemana (pmf.getPersistenceManager(),tipoAlojamiento);
        for ( Object tupla : tuplas)
        {
			Object [] datos = (Object []) tupla;
			Date fechaInicio = new Date(((BigDecimal) datos[0]).longValue());
			long dinero = ((BigDecimal) datos [1]).longValue ();

			Object [] resp = new Object [2];
			resp [0] = fechaInicio;
			resp [1] = dinero;
			
			respuesta.add(resp);
        }

		return respuesta;
	}
	// RFC7A:ANALIZAR LA OPERACIÓN DE ALOHANDES- Mayor Ocupacion
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la smeana de mayor ocupacion
	 */
	public List<Object []> darMayorOcupacionSemana (String tipoAlojamiento)
	{
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlAlojamiento.darMayorOcupacionSemana (pmf.getPersistenceManager(),tipoAlojamiento);
        for ( Object tupla : tuplas)
        {
			Object [] datos = (Object []) tupla;
			Date fechaInicio = new Date(((BigDecimal) datos[0]).longValue());
			long alojamientosOcupados = ((BigDecimal) datos [1]).longValue ();

			Object [] resp = new Object [2];
			resp [0] = fechaInicio;
			resp [1] = alojamientosOcupados;
			
			respuesta.add(resp);
        }

		return respuesta;
	}
	// RFC7C:ANALIZAR LA OPERACIÓN DE ALOHANDES- Menor Ocupacion
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la semana de menor ocupacion
	 */
	public List<Object []> darMenorOcupacionSemana (String tipoAlojamiento)
	{
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlAlojamiento.darMenorOcupacionSemana (pmf.getPersistenceManager(),tipoAlojamiento);
        for ( Object tupla : tuplas)
        {
			Object [] datos = (Object []) tupla;
			Date fechaInicio = new Date(((BigDecimal) datos[0]).longValue());
			long alojamientosOcupados = ((BigDecimal) datos [1]).longValue ();

			Object [] resp = new Object [2];
			resp [0] = fechaInicio;
			resp [1] = alojamientosOcupados;
			
			respuesta.add(resp);
        }

		return respuesta;
	}
	// RFC9 - ENCONTRAR LAS OFERTAS DE ALOJAMIENTO QUE NO TIENEN MUCHA DEMANDA
	/**
	 * Método que consultar los alojamientos con poca demanda que no fueron reservados en el ultimo mes
	 * @return La lista de objetos alojamientos, construidos con base en las tuplas de la tabla ALOJAMIENTO
	 */
	public List<Alojamiento> encontrarOfertasConBajaDemanda ()
	{
		return sqlAlojamiento.encontrarOfertasConBajaDemanda(pmf.getPersistenceManager());
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

	// RF6: RETIRAR UNA OFERTA DE ALOJAMIENTO
	/**
	 * Método que elimina, de manera transaccional, las tuplas de reservas, dado el identificador de un alojamiento
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long retirarOfertaAlojamiento (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlAlojamiento.retirarOfertaAlojamiento(pm, idAlojamiento);
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

    // RF10 - REHABILITAR OFERTA DE ALOJAMIENTO
	/**
	 * Método que rehabilita, de manera transaccional, una tupla en la tabla alojamiento, dado el identificador del alojamiento
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas modificadas. -1 si ocurre alguna Excepción
	 */
	public long rehabilitarAlojamiento (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlAlojamiento.rehabilitarAlojamiento(pm, idAlojamiento);
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
	// RF10 - DESHABILITAR OFERTA DE ALOJAMIENTO
	/**
	 * Método que deshabilita, de manera transaccional, una tupla en la tabla alojamiento, dado el identificador del alojamiento
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas modificadas. -1 si ocurre alguna Excepción
	 */
	public long deshabilitarAlojamiento (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlAlojamiento.deshabilitarAlojamiento(pm, idAlojamiento);
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
	 /**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Cliente
	 * Adiciona entradas al log de la aplicación
	 * @param idCliente - El id del cliente
	 * @param tipoIdentificacion - El tipo de identificación del cliente
	 * @param nombreCliente - El nombre del cliente
     * @param fechaNacimiento - La fecha de nacimiento del cliente
	 * @return El objeto Cliente adicionado. null si ocurre alguna Excepción
	 */
	public Cliente adicionarCliente(long idCliente, String tipoIdentificacion, String nombreCliente , Date fechaNacimiento)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlCliente.adicionarCliente(pm, idCliente, tipoIdentificacion, nombreCliente, fechaNacimiento );
            tx.commit();
            
            log.trace ("Inserción de cliente: " + nombreCliente + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Cliente (idCliente, tipoIdentificacion, nombreCliente, fechaNacimiento);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla Cliente, dado el identificador del Cliente
	 * Adiciona entradas al log de la aplicación
	 * @param idCliente - El id del servicio
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarClientePorId (long idCliente) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlCliente.eliminarClientePorId(pm, idCliente);
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
	 * Método que consulta todas las tuplas en la tabla Cliente
	 * @return La lista de objetos Cliente, construidos con base en las tuplas de la tabla SERVICIO
	 */
	public List<Cliente> darClientes ()
	{
		return sqlCliente.darClientes (pmf.getPersistenceManager());
	}
	/**
	 * Método que consulta todas las tuplas en la tabla Cliente con un identificador dado
	 * @param idCliente - El id del cliente
	 * @return El objeto Cliente, construido con base en las tuplas de la tabla Cliente con el identificador dado
	 */
	public Cliente  darClientePorId (long idCliente )
	{
		return sqlCliente.darClientePorId (pmf.getPersistenceManager(), idCliente );
	}

	// RFC5 - MOSTRAR EL USO DE ALOHANDES PARA CADA TIPO DE USUARIO DE LA COMUNIDAD
	/**
	 * Método que muestra las principales caragteristicas del uso de AlohaAndes, segun el tipo de usuario
	 * @return La lista de objetos con las principales caracteristicas de cada tipo de cliente
	 */
	public List<Object []> darInformacionMiembrosActivos  ()
	{
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlCliente.darInformacionMiembrosActivos (pmf.getPersistenceManager());
        for ( Object tupla : tuplas)
        {
			Object [] datos = (Object []) tupla;

			String tipoUsuario = (String) datos [0];
			long numeroReservas = ((BigDecimal) datos [1]).longValue ();
			long totalNochesReservadas = ((BigDecimal) datos [1]).longValue ();
			long dineroPagado = ((BigDecimal) datos [3]).longValue ();

			Object [] resp = new Object [3];
			resp [0] = tipoUsuario;
			resp [1] = numeroReservas;
			resp [2] = totalNochesReservadas;
			resp [2] = dineroPagado;
			
			respuesta.add(resp);
        }

		return respuesta;
	}

	public List<Object []> darInformacionMiembrosSecundarios  ()
	{
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlCliente.darInformacionMiembrosSecundarios (pmf.getPersistenceManager());
        for ( Object tupla : tuplas)
        {
			Object [] datos = (Object []) tupla;

			String tipoAlojamiento = (String) datos [0];
			long numeroReservas = ((BigDecimal) datos [1]).longValue ();
			long totalNochesReservadas = ((BigDecimal) datos [1]).longValue ();
			long dineroPagado = ((BigDecimal) datos [3]).longValue ();

			Object [] resp = new Object [3];
			resp [0] = tipoAlojamiento;
			resp [1] = numeroReservas;
			resp [2] = totalNochesReservadas;
			resp [2] = dineroPagado;
			
			respuesta.add(resp);
        }

		return respuesta;
	}


	
	// RFC8 - ENCONTRAR LOS CLIENTES FRECUENTES
	/**
	 * Método que consultar los clientes frecuentes de cierto alojamiento
	 * @return La lista de objetos clientes, construidos con base en las tuplas de la tabla CLIENTE
	 */
	public List<Cliente> encontrarClientesFrecuentes(long idAlojamiento)
	{
		return sqlCliente.encontrarClientesFrecuentes(pmf.getPersistenceManager(), idAlojamiento);
	}

	 /* ****************************************************************
	 * 			Métodos para manejar los MIEMBROS ACTIVOS
	 *****************************************************************/
	 /**
	 * Método que inserta, de manera transaccional, una tupla en la tabla MiembroActivo
	 * Adiciona entradas al log de la aplicación
	 * @param idMiembroActivo - El id del Miembro Activo
	 * @param carnet - Número de carnet del Miembro Activo
     * @param tipo - Nombre del Miembro Activo
	 * @return El objeto MiembroActivo adicionado. null si ocurre alguna Excepción
	 */
	public MiembroActivo adicionarMiembroActivo(long idMiembroActivo, String carnet, String tipo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlMiembroActivo.adicionarMiembroActivo(pm, idMiembroActivo, carnet, tipo);
            tx.commit();
            
            log.trace ("Inserción de Miembro Activo: " + idMiembroActivo + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new MiembroActivo (idMiembroActivo, carnet, tipo);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla MiembroActivo, dado el identificador del MiembroActivo
	 * Adiciona entradas al log de la aplicación
	 * @param idMiembroActivo - El id del miembro activo
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarMiembroActivoPorId (long idMiembroActivo) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlMiembroActivo.eliminarMiembroActivoPorId(pm, idMiembroActivo);
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
	 * Método que consulta todas las tuplas en la tabla MiembroActivo
	 * @return La lista de objetos MiembroActivo, construidos con base en las tuplas de la tabla MIEMBRO ACTIVO
	 */
	public List<MiembroActivo> darMiembrosActivos ()
	{
		return sqlMiembroActivo.darMiembrosActivos (pmf.getPersistenceManager());
	}
	/**
	 * Método que consulta todas las tuplas en la tabla MiembroActivo con un identificador dado
	 * @param idMiembroActivo - El id del MiembroActivo
	 * @return El objeto MiembroActivo, construido con base en las tuplas de la tabla MiembroActivo con el identificador dado
	 */
	public MiembroActivo  darMiembroActivoPorId (long idMiembroActivo )
	{
		return sqlMiembroActivo.darMiembroActivoPorId (pmf.getPersistenceManager(), idMiembroActivo );
	}
	 /* ****************************************************************
	 * 			Métodos para manejar los MIEMBROS SECUNDARIOS
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla MiembroSecundario
	 * Adiciona entradas al log de la aplicación
	 * @param idMiembroSecundario - El id del Miembro Secundario
     * @param tipo - Nombre del Miembro Secundario
	 * @return El objeto MiembroSecundario adicionado. null si ocurre alguna Excepción
	 */
	public MiembroSecundario adicionarMiembroSecundario(long idMiembroSecundario, String tipo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlMiembroSecundario.adicionarMiembroSecundario(pm, idMiembroSecundario,tipo);
            tx.commit();
            
            log.trace ("Inserción de Miembro Secundario: " + idMiembroSecundario + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new MiembroSecundario (idMiembroSecundario, tipo);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla MiembroSecundario, dado el identificador del MiembroSecundario
	 * Adiciona entradas al log de la aplicación
	 * @param idMiembroSecundario - El id del miembro Secundario
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarMiembroSecundarioPorId (long idMiembroSecundario) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlMiembroSecundario.eliminarMiembroSecundarioPorId(pm, idMiembroSecundario);
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
	 * Método que consulta todas las tuplas en la tabla MiembroSecundario
	 * @return La lista de objetos MiembroSecundario, construidos con base en las tuplas de la tabla MIEMBRO Secundario
	 */
	public List<MiembroSecundario> darMiembrosSecundarios ()
	{
		return sqlMiembroSecundario.darMiembrosSecundarios (pmf.getPersistenceManager());
	}
	/**
	 * Método que consulta todas las tuplas en la tabla MiembroSecundario con un identificador dado
	 * @param idMiembroSecundario - El id del MiembroSecundario
	 * @return El objeto MiembroSecundario, construido con base en las tuplas de la tabla MiembroSecundario con el identificador dado
	 */
	public MiembroSecundario  darMiembroSecundarioPorId (long idMiembroSecundario )
	{
		return sqlMiembroSecundario.darMiembroSecundarioPorId (pmf.getPersistenceManager(), idMiembroSecundario );
	}

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
	 * Método que inserta, de manera transaccional, una tupla en la tabla Reserva
	 * Adiciona entradas al log de la aplicación
	 * @param idReserva - El id de la reserva
	 * @param idAlojamiento - El id del alojamiento
	 * @param idCliente - El id del cliente
     * @param duracion - Duracion de la reserva
	 * @param fechaInicio - La fecha de inicio de la reserva
     * @param fechaFinal - La fecha final de la reserva
     * @param costoTotal - El costo total de la reserva
     * @param estado - El estado de la reserva
     * @param numPersonas - Numero de personas en la reserva
	 * @return El objeto Reserva adicionado. null si ocurre alguna Excepción
	 */
	public Reserva adicionarReserva(long idAlojamiento, long idCliente, int duracion , Date fechaInicio, Date fechaFinal, long costoTotal, String estado, int numPersonas)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idReserva = nextval ();
			long idReservaColectiva = 0;
            long tuplasInsertadas = sqlReserva.adicionarReserva(pm, idReserva, idAlojamiento, idCliente, duracion , fechaInicio, fechaFinal, costoTotal, estado, numPersonas, idReservaColectiva);
            tx.commit();
            
            log.trace ("Inserción de reserva: " + idReserva + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Reserva (idReserva, idAlojamiento, idCliente, duracion , fechaInicio, fechaFinal, costoTotal, estado, numPersonas, idReservaColectiva);
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
	 * Método que inserta, de manera transaccional, una tupla en la tabla Reserva
	 * Adiciona entradas al log de la aplicación
	 * @param idCliente - El id del cliente
	 * @param fechaInicio - La fecha de inicio de la reserva
     * @param fechaFinal - La fecha final de la reserva
	 * @return El numero de reservas. null si ocurre alguna Excepción
	 */
	public long revisarReserva (long idCliente, Date fechaInicio, Date fechaFinal) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlReserva.revisarReserva(pm, idCliente,fechaInicio,fechaFinal);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla Reserva, dado el identificador de la reserva
	 * Adiciona entradas al log de la aplicación
	 * @param idReserva - El id del Reserva
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarReservaPorId (long idReserva) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlReserva.eliminarReservaPorId(pm, idReserva);
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

	// RF7 - REGISTRAR RESERVA COLECTIVA
	/**
	 * Método que muestra el listado de las reservas disponibles en cierto rango de fechas que sea de cierto tipo de habitacion
	 * @return La informacion de los alojamientos que cumplen las consiciones dadas
	 */
	public List<Object []> RevisarReservaColectiva (String tipoHabitacion, Date fechaInicio, Date fechaFinal)
	{
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlReserva.RevisarReservaColectiva (pmf.getPersistenceManager(), tipoHabitacion, fechaInicio, fechaFinal);
        for ( Object tupla : tuplas)
        {
			Object [] datos = (Object []) tupla;
			long idAlojamientoActual = ((BigDecimal) datos [0]).longValue ();
			long PrecioNocheActual = ((BigDecimal) datos [1]).longValue ();
	

			Object [] resp = new Object [2];
			resp [0] = idAlojamientoActual;
			resp [1] = PrecioNocheActual;
			
			respuesta.add(resp);
        }

		return respuesta;
	}

	public long obtenerIdReservaColectiva()
	{
			long idReservaColectiva = nextval ();      
            return idReservaColectiva ;
	}

	public Reserva RegistrarReservaIndividual( long idAlojamiento, long idCliente, int duracion, Date fechaInicio , Date fechaFinal, long costoTotal, String estado, int numPersonas, long idReservaColectiva)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idReserva = nextval ();
            long tuplasInsertadas = sqlReserva.RegistrarReservaIndividual(pm, idReserva, idAlojamiento, idCliente, duracion , fechaInicio, fechaFinal, costoTotal, estado, numPersonas, idReservaColectiva);
            tx.commit();
            
            log.trace ("Inserción de reserva: " + idReserva + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Reserva (idReserva, idAlojamiento, idCliente, duracion , fechaInicio, fechaFinal, costoTotal, estado, numPersonas, idReservaColectiva);
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


	// RF8 - CANCELAR RESERVA COLECTIVA
	/**
	 * Método que elimina, de manera transaccional, una serie de tuplas en la tabla Reserva, dado el identificador de la reserva colectiva
	 * Adiciona entradas al log de la aplicación
	 * @param idReservaColectiva - El id del Reserva colectiva
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long cancelarReservaColectivaPorId (long idReservaColectiva) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlReserva.cancelarReservaColectivaPorId(pm, idReservaColectiva);
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
	 * Método que cancela, de manera transaccional, una tupla en la tabla Reserva, dado el identificador del tipo de bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idReserva - El id del Reserva
	 * @return El número de tuplas modificadas. -1 si ocurre alguna Excepción
	 */
	public long cancelarReservaPorId (long idReserva) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlReserva.cancelarReservaPorId(pm, idReserva);
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
	 * Método que consulta todas las tuplas en la tabla Reserva
	 * @return La lista de objetos Reserva, construidos con base en las tuplas de la tabla RESERVA
	 */
	public List<Reserva> darReservas ()
	{
		return sqlReserva.darReservas (pmf.getPersistenceManager());
	}
	/**
	 * Método que consulta todas las tuplas en la tabla Reserva con un identificador dado
	 * @param idReserva - El id del Reserva
	 * @return El objeto Reserva, construido con base en las tuplas de la tabla Reserva con el identificador dado
	 */
	public Reserva darReservaPorId (long idReserva)
	{
		return sqlReserva.darReservaPorId (pmf.getPersistenceManager(), idReserva);
	}

	// RFC6 - MOSTRAR EL USO DE ALOHANDES PARA UN USUARIO DADO
	/**
	 * Método que muestra el numero de reservas, el dinero pagado, y el numero de resrvas para un cliente dado
	 * @return La informacion de un cliente especifico, construidos con base en las tuplas de la tabla RESERVA
	 */
	public List<Object []> mostrarUsoPorUsuario (long idCliente)
	{
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlReserva.mostrarUsoPorUsuario (pmf.getPersistenceManager(), idCliente);
        for ( Object tupla : tuplas)
        {
			Object [] datos = (Object []) tupla;
			long idClienteActual = ((BigDecimal) datos [0]).longValue ();
			long numeroReservas = ((BigDecimal) datos [1]).longValue ();
			long numeroNochesReservadas = ((BigDecimal) datos [2]).longValue ();
			long dineroPagado = ((BigDecimal) datos [3]).longValue ();

			Object [] resp = new Object [4];
			resp [0] = idClienteActual;
			resp [1] = numeroReservas;
			resp [2] = numeroNochesReservadas;
			resp [3] = dineroPagado;
			
			respuesta.add(resp);
        }

		return respuesta;
	}




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
