package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList; 
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;
import uniandes.isis2304.parranderos.persistencia.PersistenciaParranderos;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Juan Sebastian Sanchez
 */
public class Parranderos 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Parranderos.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaParranderos pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Parranderos ()
	{
		pp = PersistenciaParranderos.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Parranderos (JsonObject tableConfig)
	{
		pp = PersistenciaParranderos.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
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
	 * Adiciona de manera persistente un alojamiento
	 * Adiciona entradas al log de la aplicación
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
	public Alojamiento adicionarAlojamiento (String nombre, int capacidad ,String ubicacion, int tamano, int precioNoche, int ocupacionTotal, int numReservas, long idOperador)
	{
        log.info ("Adicionando Alojamiento: " + nombre);
        Alojamiento alojamiento = pp.adicionarAlojamiento (nombre, capacidad, ubicacion, tamano, precioNoche, ocupacionTotal, numReservas, idOperador);		
        log.info ("Adicionando Alojamiento: " + alojamiento);
        return alojamiento;
	}

	/**
	 * Elimina un alojamiento por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarAlojamientoPorId (long idAlojamiento)
	{
		log.info ("Eliminando Alojamiento por id: " + idAlojamiento);
        long resp = pp.eliminarAlojamientoPorId (idAlojamiento);		
        log.info ("Eliminando Alojamiento por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los alojamientos en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos alojamiento con todos los alojamientos que conoce la aplicación, llenos con su información básica
	 */
	public List<Alojamiento> darAlojamientos ()
	{
		log.info ("Consultando Alojamientos");
        List<Alojamiento> alojamientos = pp.darAlojamientos ();	
        log.info ("Consultando Alojamientos: " + alojamientos.size() + " existentes");
        return alojamientos;
	}

	/**
	 * Encuentra todos los tipos de bebida en Alohaandes y los devuelve como una lista de VOAlojamiento
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOAlojamiento con todos los alojamiento que conoce la aplicación, llenos con su información básica
	 */
	public List<VOAlojamiento> darVOAlojamientos ()
	{
		log.info ("Generando los VO de Alojamientos");        
        List<VOAlojamiento> voAlojamientos = new LinkedList<VOAlojamiento> ();
        for (Alojamiento tb : pp.darAlojamientos ())
        {
        	voAlojamientos.add (tb);
        }
        log.info ("Generando los VO de Alojamientos: " + voAlojamientos.size() + " existentes");
        return voAlojamientos;
	}

	// RFC2: MOSTRAR LAS 20 OFERTAS MÁS POPULARES
	/**
	 * Encuentra los alojamientos mas populares en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos alojamiento con todos los alojamientos mas populares dentro de la aplicación, llenos con su información básica
	 */
	public List<Alojamiento> darOfertasMasPopulares ()
	{
		log.info ("Consultando Alojamientos mas populares");
        List<Alojamiento> alojamientosPopulares = pp.darOfertasMasPopulares ();	
        log.info ("Consultando Alojamientos mas populares: Listo!");
        return alojamientosPopulares ;
	}

	// RFC4: MOSTRAR LOS ALOJAMIENTOS DISPONIBLES EN UN RANGO DE FECHAS, QUE CUMPLEN CON UN CONJUNTO DE SERVICIOS
	/**
	 * Encuentra todos los alojamientos disponibles en el rango de fechas dado y que cumplan con ciertos servicios
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de los alojamientos que cumplen las condiciones dadas
	 */
	public List<Object []> darAlojamientosDisponibles (Date fecha1, Date fecha2, String nombreServicio)
	{
        log.info ("Listando Alojamientos que cumplen las condiciones dadas");
        List<Object []> tuplas = pp.darAlojamientosDisponibles (fecha1, fecha2, nombreServicio);
        log.info ("Listando Alojamientos que cumplen las condiciones dadas: Listo!");
        return tuplas;
	}


	/**
	 * Encuentra un alojamiento y su información básica, según su identificador
	 * @param idAlojamiento - El id del alojamiento
	 * @return Un objeto Alojamiento que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un alojamiento con dicho identificador no existe
	 */
	public Alojamiento darAlojamientoPorId (long idAlojamiento)
	{
        log.info ("Dar información de un alojamiento por id: " + idAlojamiento);
        Alojamiento alojamiento = pp.darAlojamientoPorId (idAlojamiento);
        log.info ("Buscando alojamiento por Id: " + alojamiento != null ? alojamiento : "NO EXISTE");
        return alojamiento;
	}

	/**
	 * Aumenta en 1 la ocupacion actual de un alojamiento
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas actualizadas
	 */
	public long aumentarOcupacionTotalAlojamiento (long idAlojamiento)
	{
        log.info ("Aumentando ocupacion actual de un alojamiento: " + idAlojamiento);
        long resp = pp.aumentarOcupacionTotalAlojamiento (idAlojamiento);
        log.info ("Aumentando ocupacion actual de un alojamiento: " + resp + " tuplas actualizadas");
        return resp;
	}

	/**
	 * Aumenta en 1 el número de reservas de un alojamiento
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas actualizadas
	 */
	public long aumentarNumeroReservasAlojamiento (long idAlojamiento)
	{
        log.info ("Aumentando número de reservas de un alojamiento: " + idAlojamiento);
        long resp = pp.aumentarNumeroReservasAlojamiento (idAlojamiento);
        log.info ("Aumentando número de reservas de un alojamiento: " + resp + " tuplas actualizadas");
        return resp;
	}

	
	/* ****************************************************************
	 * 			Métodos para manejar las VIVIENDAS UNIVERSITARIAS
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un ViviendaUniversitaria
	 * Adiciona entradas al log de la aplicación
	 * @param tipoHabitacion - El tipo de habitacion de la vivienda universitaria
	 * @return El objeto ViviendaUniversitaria adicionado. null si ocurre alguna Excepción
	 */
	public ViviendaUniversitaria adicionarViviendaUniversitaria (long idAlojamiento,String tipoHabitacion)
	{
        log.info ("Adicionando ViviendaUniversitaria: ");
        ViviendaUniversitaria viviendaUniversitaria = pp.adicionarViviendaUniversitaria (idAlojamiento, tipoHabitacion);		
        log.info ("Adicionando ViviendaUniversitaria: ");
        return viviendaUniversitaria;
	}

	/**
	 * Elimina un ViviendaUniversitaria por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id de la vivienda universitaria
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarViviendaUniversitariaPorId (long idAlojamiento)
	{
		log.info ("Eliminando ViviendaUniversitaria por id: " + idAlojamiento);
        long resp = pp.eliminarViviendaUniversitariaPorId (idAlojamiento);		
        log.info ("Eliminando ViviendaUniversitaria por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los ViviendasUniversitarias en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos ViviendaUniversitaria con todos los ViviendasUniversitarias que conoce la aplicación, llenos con su información básica
	 */
	public List<ViviendaUniversitaria> darViviendasUniversitarias ()
	{
		log.info ("Consultando ViviendasUniversitarias");
        List<ViviendaUniversitaria> ViviendasUniversitarias = pp.darViviendasUniversitarias ();	
        log.info ("Consultando ViviendasUniversitarias: " + ViviendasUniversitarias.size() + " existentes");
        return ViviendasUniversitarias;
	}

	/**
	 * Encuentra todos los ViviendasUniversitarias en Alohaandes y los devuelve como una lista de VOViviendaUniversitaria
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOViviendaUniversitaria con todos los ViviendasUniversitarias que conoce la aplicación, llenos con su información básica
	 */
	public List<VOViviendaUniversitaria> darVOViviendasUniversitarias ()
	{
		log.info ("Generando los VO de ViviendasUniversitarias");        
        List<VOViviendaUniversitaria> voViviendasUniversitarias = new LinkedList<VOViviendaUniversitaria> ();
        for (ViviendaUniversitaria tb : pp.darViviendasUniversitarias ())
        {
        	voViviendasUniversitarias.add (tb);
        }
        log.info ("Generando los VO de ViviendasUniversitarias: " + voViviendasUniversitarias.size() + " existentes");
        return voViviendasUniversitarias;
	}

	/**
	 * Encuentra un ViviendaUniversitaria y su información básica, según su identificador
	 * @param idAlojamiento - El id de la vivienda universitaria
	 * @return Un objeto ViviendaUniversitaria que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un alojamiento con dicho identificador no existe
	 */
	public ViviendaUniversitaria darViviendaUniversitariaPorId (long idAlojamiento)
	{
        log.info ("Dar información de una ViviendaUniversitaria por id: " + idAlojamiento);
        ViviendaUniversitaria viviendaUniversitaria = pp.darViviendaUniversitariaPorId (idAlojamiento);
        log.info ("Buscando ViviendaUniversitaria por Id: " + viviendaUniversitaria != null ? viviendaUniversitaria : "NO EXISTE");
        return viviendaUniversitaria;
	}


	 /* ****************************************************************
	 * 			Métodos para manejar las HABITACIONES DE VIVIENDAS
	 *****************************************************************/

	 /**
	 * Adiciona de manera persistente un HabitacionVivienda
	 * Adiciona entradas al log de la aplicación
	 * @param tipoBano - El tipo de baño de la habitacion de la vivienda
	 * @param tipoHabitacion - El tipo de habitacion de la vivienda
	 * @return El objeto HabitacionVivienda adicionado. null si ocurre alguna Excepción
	 */
	public HabitacionVivienda adicionarHabitacionVivienda (long idAlojamiento, String tipoBano, String tipoHabitacion)
	{
        log.info ("Adicionando HabitacionVivienda: ");
        HabitacionVivienda habitacionVivienda = pp.adicionarHabitacionVivienda (idAlojamiento, tipoBano, tipoHabitacion);		
        log.info ("Adicionando HabitacionVivienda: ");
        return habitacionVivienda;
	}

	/**
	 * Elimina un HabitacionVivienda por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id de la habitacion de la vivienda
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarHabitacionViviendaPorId (long idAlojamiento)
	{
		log.info ("Eliminando HabitacionVivienda por id: " + idAlojamiento);
        long resp = pp.eliminarHabitacionViviendaPorId (idAlojamiento);		
        log.info ("Eliminando HabitacionVivienda por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los HabitacionesViviendas en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos HabitacionVivienda con todos los HabitacionesViviendas que conoce la aplicación, llenos con su información básica
	 */
	public List<HabitacionVivienda> darHabitacionesViviendas ()
	{
		log.info ("Consultando HabitacionesViviendas");
        List<HabitacionVivienda> habitacionesViviendas = pp.darHabitacionesViviendas ();	
        log.info ("Consultando HabitacionesViviendas: " + habitacionesViviendas.size() + " existentes");
        return habitacionesViviendas;
	}

	/**
	 * Encuentra todos los HabitacionesViviendas en Alohaandes y los devuelve como una lista de VOHabitacionVivienda
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOHabitacionVivienda con todos los HabitacionesViviendas que conoce la aplicación, llenos con su información básica
	 */
	public List<VOHabitacionVivienda> darVOHabitacionesViviendas ()
	{
		log.info ("Generando los VO de HabitacionesViviendas");        
        List<VOHabitacionVivienda> vohabitacionesViviendas = new LinkedList<VOHabitacionVivienda> ();
        for (HabitacionVivienda tb : pp.darHabitacionesViviendas ())
        {
        	vohabitacionesViviendas.add (tb);
        }
        log.info ("Generando los VO de HabitacionesViviendas: " + vohabitacionesViviendas.size() + " existentes");
        return vohabitacionesViviendas;
	}

	/**
	 * Encuentra un HabitacionVivienda y su información básica, según su identificador
	 * @param idAlojamiento - El id de la habitacion de la vivienda
	 * @return Un objeto HabitacionVivienda que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un HabitacionVivienda con dicho identificador no existe
	 */
	public HabitacionVivienda darHabitacionViviendaPorId (long idAlojamiento)
	{
        log.info ("Dar información de una HabitacionVivienda por id: " + idAlojamiento);
        HabitacionVivienda habitacionVivienda = pp.darHabitacionViviendaPorId (idAlojamiento);
        log.info ("Buscando HabitacionVivienda por Id: " + habitacionVivienda != null ? habitacionVivienda : "NO EXISTE");
        return habitacionVivienda;
	}


	 /* ****************************************************************
	 * 			Métodos para manejar los APARTAMENTOS
	 *****************************************************************/

	 /**
	 * Adiciona de manera persistente un Apartamento
	 * @return El objeto Apartamento adicionado. null si ocurre alguna Excepción
	 */
	public Apartamento adicionarApartamento (long idAlojamiento)
	{
        log.info ("Adicionando Apartamento: ");
        Apartamento apartamento = pp.adicionarApartamento (idAlojamiento);		
        log.info ("Adicionando Apartamento: ");
        return apartamento;
	}

	/**
	 * Elimina un Apartamento por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id del apartamento
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarApartamentoPorId (long idAlojamiento)
	{
		log.info ("Eliminando Apartamento por id: " + idAlojamiento);
        long resp = pp.eliminarApartamentoPorId (idAlojamiento);		
        log.info ("Eliminando Apartamento por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los Apartamentos en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Apartamento con todos los Apartamentos que conoce la aplicación, llenos con su información básica
	 */
	public List<Apartamento> darApartamentos ()
	{
		log.info ("Consultando Apartamentos");
        List<Apartamento> apartamentos = pp.darApartamentos ();	
        log.info ("Consultando Apartamentos: " + apartamentos.size() + " existentes");
        return apartamentos;
	}

	/**
	 * Encuentra todos los Apartamentos en Alohaandes y los devuelve como una lista de VOApartamento
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOApartamento con todos los Apartamentos que conoce la aplicación, llenos con su información básica
	 */
	public List<VOApartamento> darVOApartamentos ()
	{
		log.info ("Generando los VO de Apartamentos");        
        List<VOApartamento> voApartamentos = new LinkedList<VOApartamento> ();
        for (Apartamento tb : pp.darApartamentos ())
        {
        	voApartamentos.add (tb);
        }
        log.info ("Generando los VO de Apartamento: " + voApartamentos.size() + " existentes");
        return voApartamentos;
	}

	/**
	 * Encuentra un Apartamento y su información básica, según su identificador
	 * @param idAlojamiento - El id del apartamento
	 * @return Un objeto Apartamento que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un Apartamento con dicho identificador no existe
	 */
	public Apartamento darApartamentoPorId (long idAlojamiento)
	{
        log.info ("Dar información de una Apartamento por id: " + idAlojamiento);
        Apartamento apartamento = pp.darApartamentoPorId (idAlojamiento);
        log.info ("Buscando Apartamento por Id: " + apartamento != null ? apartamento : "NO EXISTE");
        return apartamento;
	}


	 /* ****************************************************************
	 * 			Métodos para manejar las HABITACIONES DE HOTEL
	 *****************************************************************/

	  /**
	 * Adiciona de manera persistente un HabitacionHotel
	 * Adiciona entradas al log de la aplicación:
	 * @param tipoHabitacion - El tipo de habitacion de la habitacion de hotel
	 * @return El objeto HabitacionHotel adicionado. null si ocurre alguna Excepción
	 */
	public HabitacionHotel adicionarHabitacionHotel (long idAlojamiento,String tipoHabitacion)
	{
        log.info ("Adicionando HabitacionHotel: ");
        HabitacionHotel habitacionHotel = pp.adicionarHabitacionHotel (idAlojamiento, tipoHabitacion);		
        log.info ("Adicionando HabitacionHotel: ");
        return habitacionHotel;
	}

	/**
	 * Elimina un HabitacionHotel por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id de la habitacion de hotel
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarHabitacionHotelPorId (long idAlojamiento)
	{
		log.info ("Eliminando HabitacionHotel por id: " + idAlojamiento);
        long resp = pp.eliminarHabitacionHotelPorId (idAlojamiento);		
        log.info ("Eliminando HabitacionHotel por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los HabitacionesHoteles en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos HabitacionHotel con todos los HabitacionesHoteles que conoce la aplicación, llenos con su información básica
	 */
	public List<HabitacionHotel> darHabitacionesHoteles ()
	{
		log.info ("Consultando HabitacionHotel");
        List<HabitacionHotel> habitacionesHoteles = pp.darHabitacionesHoteles ();	
        log.info ("Consultando HabitacionHotel: " + habitacionesHoteles.size() + " existentes");
        return habitacionesHoteles;
	}

	/**
	 * Encuentra todos los HabitacionesHoteles en Alohaandes y los devuelve como una lista de VOHabitacionHotel
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOHabitacionHotel con todos los HabitacionesHoteles que conoce la aplicación, llenos con su información básica
	 */
	public List<VOHabitacionHotel> darVOHabitacionesHoteles ()
	{
		log.info ("Generando los VO de HabitacionHotel");        
        List<VOHabitacionHotel> voHabitacionesHoteles = new LinkedList<VOHabitacionHotel> ();
        for (HabitacionHotel tb : pp.darHabitacionesHoteles ())
        {
        	voHabitacionesHoteles.add (tb);
        }
        log.info ("Generando los VO de HabitacionHotel: " + voHabitacionesHoteles.size() + " existentes");
        return voHabitacionesHoteles;
	}

	/**
	 * Encuentra un HabitacionHotel y su información básica, según su identificador
	 * @param idAlojamiento - El id de la habitacion de hotel
	 * @return Un objeto HabitacionHotel que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un HabitacionHotel con dicho identificador no existe
	 */
	public HabitacionHotel darHabitacionHotelPorId (long idAlojamiento)
	{
        log.info ("Dar información de una HabitacionHotel por id: " + idAlojamiento);
        HabitacionHotel habitacionHotel = pp.darHabitacionHotelPorId (idAlojamiento);
        log.info ("Buscando HabitacionHotel por Id: " + habitacionHotel != null ? habitacionHotel : "NO EXISTE");
        return habitacionHotel;
	}


	 /* ****************************************************************
	 * 			Métodos para manejar los HOSTALES
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un Hostal
	 * Adiciona entradas al log de la aplicación:
	 * @param horarioApertura- El horario de apertura del hostal
	 * @param horarioCierre - El horario del cierre del hostal
	 * @return El objeto Hostal adicionado. null si ocurre alguna Excepción
	 */
	public Hostal adicionarHostal (long idAlojamiento, String horarioApertura, String horarioCierre)
	{
        log.info ("Adicionando Hostal: ");
        Hostal hostal = pp.adicionarHostal (idAlojamiento, horarioApertura, horarioCierre);		
        log.info ("Adicionando Hostal: ");
        return hostal;
	}

	/**
	 * Elimina un Hostal por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id de la habitacion del hostal
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarHostalPorId (long idAlojamiento)
	{
		log.info ("Eliminando Hostal por id: " + idAlojamiento);
        long resp = pp.eliminarHostalPorId (idAlojamiento);		
        log.info ("Eliminando Hostal por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los Hostales en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Hostal con todos los Hostales que conoce la aplicación, llenos con su información básica
	 */
	public List<Hostal> darHostal ()
	{
		log.info ("Consultando Hostales");
        List<Hostal> hostales = pp.darHostales ();	
        log.info ("Consultando Hostal: " + hostales.size() + " existentes");
        return hostales;
	}

	/**
	 * Encuentra todos los Hostales en Alohaandes y los devuelve como una lista de VOHostal
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOHostal con todos los Hostales que conoce la aplicación, llenos con su información básica
	 */
	public List<VOHostal> darVOHostales ()
	{
		log.info ("Generando los VO de Hostal");        
        List<VOHostal> voHostales = new LinkedList<VOHostal> ();
        for (Hostal tb : pp.darHostales ())
        {
        	voHostales.add (tb);
        }
        log.info ("Generando los VO de Hostal: " + voHostales.size() + " existentes");
        return voHostales;
	}

	/**
	 * Encuentra un Hostal y su información básica, según su identificador
	 * @param idAlojamiento - El id de la habitacion del hostal
	 * @return Un objeto Hostal que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un Hostal con dicho identificador no existe
	 */
	public Hostal darHostalPorId (long idAlojamiento)
	{
        log.info ("Dar información de un Hostal por id: " + idAlojamiento);
        Hostal hostal = pp.darHostalPorId (idAlojamiento);
        log.info ("Buscando Hostal por Id: " + hostal != null ? hostal : "NO EXISTE");
        return hostal;
	}


	  /* ****************************************************************
	 * 			Métodos para manejar las VIVIENDAS TEMPORALES
	 *****************************************************************/

	  /**
	 * Adiciona de manera persistente un ViviendaTemporal
	 * Adiciona entradas al log de la aplicación:
	 * @param idAlojamiento - El id de la vivienda temporal
	 * @param numHabitaciones - El numero de habitaciones de la vivienda temporal
	 * @return El objeto ViviendaTemporal adicionado. null si ocurre alguna Excepción
	 */
	public ViviendaTemporal adicionarViviendaTemporal (long idAlojamiento, int numHabitaciones)
	{
        log.info ("Adicionando ViviendaTemporal: ");
        ViviendaTemporal viviendaTemporal = pp.adicionarViviendaTemporal (idAlojamiento, numHabitaciones);		
        log.info ("Adicionando ViviendaTemporal: ");
        return viviendaTemporal;
	}

	/**
	 * Elimina un ViviendaTemporal por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id de la vivienda temporal
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarViviendaTemporalId (long idAlojamiento)
	{
		log.info ("Eliminando ViviendaTemporal por id: " + idAlojamiento);
        long resp = pp.eliminarViviendaTemporalPorId (idAlojamiento);		
        log.info ("Eliminando ViviendaTemporal por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los ViviendasTemporales en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos ViviendaTemporal con todos los ViviendasTemporales que conoce la aplicación, llenos con su información básica
	 */
	public List<ViviendaTemporal> darViviendasTemporales ()
	{
		log.info ("Consultando ViviendasTemporales");
        List<ViviendaTemporal> viviendasTemporales = pp.darViviendasTemporales ();	
        log.info ("Consultando ViviendaTemporal: " + viviendasTemporales.size() + " existentes");
        return viviendasTemporales;
	}

	/**
	 * Encuentra todos los ViviendasTemporales en Alohaandes y los devuelve como una lista de VOViviendaTemporal
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOViviendaTemporal con todos los ViviendasTemporales que conoce la aplicación, llenos con su información básica
	 */
	public List<VOViviendaTemporal> darVOViviendasTemporales ()
	{
		log.info ("Generando los VO de ViviendaTemporal");        
        List<VOViviendaTemporal> voViviendasTemporales = new LinkedList<VOViviendaTemporal> ();
        for (ViviendaTemporal tb : pp.darViviendasTemporales ())
        {
        	voViviendasTemporales.add (tb);
        }
        log.info ("Generando los VO de ViviendaTemporal: " + voViviendasTemporales.size() + " existentes");
        return voViviendasTemporales;
	}

	/**
	 * Encuentra un ViviendaTemporal y su información básica, según su identificador
	 * @param idAlojamiento - El id de la vivienda temporal
	 * @return Un objeto ViviendaTemporal que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un ViviendaTemporal con dicho identificador no existe
	 */
	public ViviendaTemporal darViviendaTemporalPorId (long idAlojamiento)
	{
        log.info ("Dar información de una ViviendaTemporal por id: " + idAlojamiento);
        ViviendaTemporal viviendaTemporal = pp.darViviendaTemporalPorId (idAlojamiento);
        log.info ("Buscando ViviendaTemporal por Id: " + viviendaTemporal != null ? viviendaTemporal : "NO EXISTE");
        return viviendaTemporal;
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
	 * Adiciona de manera persistente un servicio
	 * Adiciona entradas al log de la aplicación
	 * @param idServicio - El id del servicio
	 * @param nombre - El nombre del servicio
     * @param costo - El costo del servicio
	 * @return El objeto Servicio adicionado. null si ocurre alguna Excepción
	 */
	public Servicio adicionarServicio (String nombre, int costo)
	{
        log.info ("Adicionando Servicio: " + nombre);
        Servicio servicio = pp.adicionarServicio (nombre, costo);		
        log.info ("Adicionando Servicio: " + servicio);
        return servicio;
	}

	/**
	 * Elimina un servicio por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idServicio - El id del servicio
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarServicioPorId (long idServicio)
	{
		log.info ("Eliminando Servicio por id: " + idServicio);
        long resp = pp.eliminarServicioPorId (idServicio);		
        log.info ("Eliminando Servicio por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los servicios en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos servicio con todos los servicios que conoce la aplicación, llenos con su información básica
	 */
	public List<Servicio> darServicios ()
	{
		log.info ("Consultando Servicios");
        List<Servicio> servicios = pp.darServicios ();	
        log.info ("Consultando Servicio: " + servicios.size() + " existentes");
        return servicios;
	}

	/**
	 * Encuentra todos los Servicios en Alohaandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOServicio con todos los Servicios que conoce la aplicación, llenos con su información básica
	 */
	public List<VOServicio> darVOServicios ()
	{
		log.info ("Generando los VO de Servicios");        
        List<VOServicio> voServicios = new LinkedList<VOServicio> ();
        for (Servicio tb : pp.darServicios ())
        {
        	voServicios.add (tb);
        }
        log.info ("Generando los VO de Servicios: " + voServicios.size() + " existentes");
        return voServicios;
	}

	/**
	 * Encuentra un Servicio y su información básica, según su identificador
	 * @param idServicio - El id del servicio
	 * @return Un objeto Servicio que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un alojamiento con dicho identificador no existe
	 */
	public Servicio darServicioPorId (long idServicio)
	{
        log.info ("Dar información de un Servicio por id: " + idServicio);
        Servicio servicio = pp.darServicioPorId (idServicio);
        log.info ("Buscando alojamiento por Id: " + servicio != null ? servicio : "NO EXISTE");
        return servicio;
	}


	 /* ****************************************************************
	 * 			Métodos para manejar la tabla de DISPONE
	 *****************************************************************/

	 /**
	 * Adiciona de nuevo Servicio a un Alojamiento
	 * Adiciona entradas al log de la aplicación
	 * @param idServicio - El id del servicio
	 * @param idAlojamiento - El id del alojamiento
	 * @return Un objeto Dispone con los valores dados
	 */
	public Dispone adicionarDispone (long idServicio, long idAlojamiento)
	{
        log.info ("Adicionando Dispone [" + idServicio + ", " + idAlojamiento + "]");
        Dispone resp = pp.adicionarDispone (idServicio, idAlojamiento);
        log.info ("Adicionando Dispone: " + resp + " tuplas insertadas");
        return resp;
	}
	
	/**
	 * Elimina la relacion entre un Servicio y un Alojamiento
	 * Adiciona entradas al log de la aplicación
	 * @param idServicio - El id del servicio
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarDispone (long idServicio, long idAlojamiento)
	{
        log.info ("Eliminando Dispone");
        long resp = pp.eliminarDispone (idServicio, idAlojamiento);
        log.info ("Eliminando Dispone: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los Dispone en Alohanades
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Dispone con todos los DISPONE que conoce la aplicación, llenos con su información básica
	 */
	public List<Dispone> darDispone ()
	{
        log.info ("Listando Dispone");
        List<Dispone> dispone = pp.darDispone ();	
        log.info ("Listando Dispone: " + dispone.size());
        return dispone;
	}

	/**
	 * Encuentra todos los Dispone en Alohaandes y los devuelve como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Dispone con todos los DISPONE que conoce la aplicación, llenos con su información básica
	 */
	public List<VODispone> darVODispone ()
	{
		log.info ("Generando los VO de Dispone");
		List<VODispone> voDispone = new LinkedList<VODispone> ();
		for (VODispone bar: pp.darDispone ())
		{
			voDispone.add (bar);
		}
		log.info ("Generando los VO de Dispone: " + voDispone.size () + " Dispone existentes");
		return voDispone;
	}


	/* ****************************************************************
	 * 			Métodos para manejar las RESERVAS
	 *****************************************************************/


	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de AlohaAndes
	 * @return Un arreglo con 16 números que indican el número de tuplas borradas en las tablas OPERADOR, EMPRESA, PROPIETARIO, ALOJAMIENTO,
	 * VIVIENDAUNIVERSITARIA, HABITACIONVIVIENDA, APARTAMENTO, HABITACIONHOTEL, HOSTAL, VIVIENDATEMPORAL, CLIENTE, MIEMBROACTIVO, MIEMBROSECUNDARIO,
	 * SERVICIO, DISPONE y RESERVA, respectivamente
	 */
	public long [] limpiarParranderos ()
	{
        log.info ("Limpiando la BD de AlohaAndes ");
        long [] borrrados = pp.limpiarParranderos();	
        log.info ("Limpiando la BD de AlohaAndes: Listo!");
        return borrrados;
	}
}
