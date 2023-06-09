package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;
import java.util.LinkedList; 
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;
import uniandes.isis2304.parranderos.persistencia.PersistenciaParranderos;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Juan Sebastian Sanchez y Santiago Vargas Prada
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
	/**
	 * Adiciona de manera persistente un operador
	 * Adiciona entradas al log de la aplicación
	 * @param telefono - El nombre del operador
	 * @param tipoVinculación - La capacidad del operador
	 * @return El objeto Operador adicionado. null si ocurre alguna Excepción
	 */
	public Operador adicionarOperador(String telefono, String tipoVinculacion)
	{
        log.info ("Adicionando Operador: " );
        Operador operador = pp.adicionarOperador(telefono, tipoVinculacion);		
        log.info ("Adicionando Operador: " + operador);
        return operador;
	}
	/**
	 * Elimina un operador por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El id del alojamiento
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarOperadorPorId (long idOperador)
	{
		log.info ("Eliminando Operador por id: " + idOperador);
        long resp = pp.eliminarOperadorPorId (idOperador);		
        log.info ("Eliminando Operador por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	/**
	 * Encuentra todos los operadores en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos operador con todos los operadores que conoce la aplicación, llenos con su información básica
	 */
	public List<Operador> darOperadores ()
	{
		log.info ("Consultando Operadores");
        List<Operador> operadores = pp.darOperadores ();	
        log.info ("Consultando Operadors: " + operadores.size() + " existentes");
        return operadores;
	}
	/**
	 * Encuentra todos los tipos de bebida en Alohaandes y los devuelve como una lista de VOOperador
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOOperador con todos los operadores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOOperador> darVOOperadores ()
	{
		log.info ("Generando los VO de Operadores");        
        List<VOOperador> voOperadores = new LinkedList<VOOperador> ();
        for (Operador tb : pp.darOperadores ())
        {
        	voOperadores.add (tb);
        }
        log.info ("Generando los VO de Operadores: " + voOperadores.size() + " existentes");
        return voOperadores;
	}
	/**
	 * Encuentra un operador y su información básica, según su identificador
	 * @param idOperador - El id del operador
	 * @return Un objeto Operador que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un operador con dicho identificador no existe
	 */
	public Operador darOperadorPorId (long idOperador)
	{
        log.info ("Dar información de un operador por id: " + idOperador);
        Operador operador = pp.darOperadorPorId (idOperador);
        log.info ("Buscando operador por Id: " + operador != null ? operador : "NO EXISTE");
        return operador;
	}


	 /* ****************************************************************
	 * 			Métodos para manejar los PROPIETARIOS
	 *****************************************************************/
/**
	 * Adiciona de manera persistente un propietario
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El id del propietario
	 * @param identificacion - Número de identificación del propietario
     * @param tipoIdentificacion - Tipo de identificación del propietario
     * @param nombrePropietario - Nombre del propietario
	 * @return El objeto Propietario adicionado. null si ocurre alguna Excepción
	 */
	public Propietario adicionarPropietario(long idOperador, String identificacion, String tipoIdentificacion, String nombrePropietario)
	{
        log.info ("Adicionando Propietario: " + idOperador);
        Propietario propietario = pp.adicionarPropietario(idOperador, identificacion,tipoIdentificacion,nombrePropietario);		
        log.info ("Adicionando Propietario: " + propietario);
        return propietario;
	}
	/**
	 * Elimina un propietario por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El id del alojamiento
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarPropietarioPorId (long idOperador)
	{
		log.info ("Eliminando Propietario por id: " + idOperador);
        long resp = pp.eliminarPropietarioPorId (idOperador);		
        log.info ("Eliminando Propietario por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	/**
	 * Encuentra todos los propietarios en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos propietario con todos los propietarios que conoce la aplicación, llenos con su información básica
	 */
	public List<Propietario> darPropietarios ()
	{
		log.info ("Consultando Propietarios");
        List<Propietario> propietarios = pp.darPropietarios ();	
        log.info ("Consultando Propietarios: " + propietarios.size() + " existentes");
        return propietarios;
	}
	/**
	 * Encuentra todos los tipos de bebida en Alohaandes y los devuelve como una lista de VOPropietario
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOPropietario con todos los propietarios que conoce la aplicación, llenos con su información básica
	 */
	public List<VOPropietario> darVOPropietarios ()
	{
		log.info ("Generando los VO de Propietarios");        
        List<VOPropietario> voPropietarios = new LinkedList<VOPropietario> ();
        for (Propietario tb : pp.darPropietarios ())
        {
        	voPropietarios.add (tb);
        }
        log.info ("Generando los VO de Propietarioes: " + voPropietarios.size() + " existentes");
        return voPropietarios;
	}
	/**
	 * Encuentra un propietario y su información básica, según su identificador
	 * @param idOperador - El id del operador
	 * @return Un objeto Propietario que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un propietario con dicho identificador no existe
	 */
	public Propietario darPropietarioPorId (long idOperador)
	{
        log.info ("Dar información de un propietario por id: " + idOperador);
        Propietario propietario = pp.darPropietarioPorId (idOperador);
        log.info ("Buscando propietario por Id: " + propietario != null ? propietario : "NO EXISTE");
        return propietario;
	}

	 /* ****************************************************************
	 * 			Métodos para manejar las EMPRESAS
	 *****************************************************************/
/**
	 * Adiciona de manera persistente un empresa
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El id del empresa
	 * @param nit - Número de NIT del empresa
     * @param nombreEmpresa - Nombre del empresa
	 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
	 */
	public Empresa adicionarEmpresa(long idOperador, String nit, String nombreEmpresa)
	{
        log.info ("Adicionando Empresa: " + idOperador);
        Empresa empresa = pp.adicionarEmpresa(idOperador, nit,nombreEmpresa);		
        log.info ("Adicionando Empresa: " + empresa);
        return empresa;
	}
	/**
	 * Elimina un empresa por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El id del alojamiento
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEmpresaPorId (long idOperador)
	{
		log.info ("Eliminando Empresa por id: " + idOperador);
        long resp = pp.eliminarEmpresaPorId (idOperador);		
        log.info ("Eliminando Empresa por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	/**
	 * Encuentra todos los empresas en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos empresa con todos los empresas que conoce la aplicación, llenos con su información básica
	 */
	public List<Empresa> darEmpresas ()
	{
		log.info ("Consultando Empresas");
        List<Empresa> empresas = pp.darEmpresas ();	
        log.info ("Consultando Empresas: " + empresas.size() + " existentes");
        return empresas;
	}
	/**
	 * Encuentra todos los tipos de bebida en Alohaandes y los devuelve como una lista de VOEmpresa
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOEmpresa con todos los empresas que conoce la aplicación, llenos con su información básica
	 */
	public List<VOEmpresa> darVOEmpresas ()
	{
		log.info ("Generando los VO de Empresas");        
        List<VOEmpresa> voEmpresas = new LinkedList<VOEmpresa> ();
        for (Empresa tb : pp.darEmpresas ())
        {
        	voEmpresas.add (tb);
        }
        log.info ("Generando los VO de Empresaes: " + voEmpresas.size() + " existentes");
        return voEmpresas;
	}
	/**
	 * Encuentra un empresa y su información básica, según su identificador
	 * @param idOperador - El id del operador
	 * @return Un objeto Empresa que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un empresa con dicho identificador no existe
	 */
	public Empresa darEmpresaPorId (long idOperador)
	{
        log.info ("Dar información de un empresa por id: " + idOperador);
        Empresa empresa = pp.darEmpresaPorId (idOperador);
        log.info ("Buscando empresa por Id: " + empresa != null ? empresa : "NO EXISTE");
        return empresa;
	}

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
	public Alojamiento adicionarAlojamiento (String nombre, int capacidad ,String ubicacion, int tamano, int precioNoche, int ocupacionTotal, int numReservas, long idOperador, String estado, String tipo)
	{
        log.info ("Adicionando Alojamiento: " + nombre);
        Alojamiento alojamiento = pp.adicionarAlojamiento (nombre, capacidad, ubicacion, tamano, precioNoche, ocupacionTotal, numReservas, idOperador, estado,tipo);		
        log.info ("Adicionando Alojamiento: " + alojamiento);
        return alojamiento;
	}
	public long revisarAlojamiento (long idAlojamiento) 
	{
		log.info ("Revisando estado del Alojamiento: " + idAlojamiento);
        long resp = pp.revisarAlojamiento (idAlojamiento);		
        log.info ("Alojamientos encontrados " + resp );
        return resp;
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

	// RF10 - REHABILITAR OFERTA DE ALOJAMIENTO
	/** 
	* Modifica el estado de una ofera de alojamiento a Habilitado
	* Adiciona entradas al log de la aplicación
	* @param idAlojamiento - El id del alojamiento
	* @return El número de tuplas modificadas
	*/
   public long rehabilitarAlojamiento (long idAlojamiento)
   {
	   log.info ("Rehabilitar un alojamiento por id: " + idAlojamiento);
	   long resp = pp.rehabilitarAlojamiento(idAlojamiento);		
	   log.info ("Rehabilitar un alojamiento por id: " + resp + " tuplas modificadas");
	   return resp;
   }
   // RF9 - DESHABILITAR OFERTA DE ALOJAMIENTO
	/** 
	* Modifica el estado de una ofera de alojamiento a Habilitado
	* Adiciona entradas al log de la aplicación
	* @param idAlojamiento - El id del alojamiento
	* @return El número de tuplas modificadas
	*/
	public long deshabilitarAlojamiento (long idAlojamiento)
	{
		log.info ("Deshabilitar un alojamiento por id: " + idAlojamiento);
		long resp = pp.deshabilitarAlojamiento(idAlojamiento);		
		log.info ("Deshabilitar un alojamiento por id: " + resp + " tuplas modificadas");
		return resp;
	}

	// RFC1: MOSTRAR EL ÍNDICE DE OCUPACIÓN DE CADA UNA DE LAS OFERTAS DE ALOJAMIENTO REGISTRADAS
	/**
	 * Encuentra los alojamientos mas populares en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos alojamiento con todos los alojamientos mas populares dentro de la aplicación, llenos con su información básica
	 */
	public List<Object[]> darDinero ()
	{
		log.info ("Consultando indice de ocupación de los Alojamientos");
        List<Object[]> dinero = pp.darDinero ();	
        log.info ("Consultando indice de ocupación de los Alojamientos: Listo!");
        return dinero;
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
	// RFC3: MOSTRAR EL ÍNDICE DE OCUPACIÓN DE CADA UNA DE LAS OFERTAS DE ALOJAMIENTO REGISTRADAS
	/**
	 * Encuentra los alojamientos mas populares en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos alojamiento con todos los alojamientos mas populares dentro de la aplicación, llenos con su información básica
	 */
	public List<Object[]> darIndiceDeOcupacion ()
	{
		log.info ("Consultando indice de ocupación de los Alojamientos");
        List<Object[]> indicesOcupacion = pp.darIndiceDeOcupacion ();	
        log.info ("Consultando indice de ocupación de los Alojamientos: Listo!");
        return indicesOcupacion ;
	}
	public List<Object[]> darMayorDineroSemana (String tipoAlojamiento)
	{
		log.info ("Consultando fechas de mayores ingresos ");
        List<Object[]> indicesOcupacion = pp.darMayorDineroSemana (tipoAlojamiento);	
        log.info ("Consultando fechas de mayores ingresos: Listo!");
        return indicesOcupacion ;
	}
	public List<Object[]> darMayorOcupacionSemana (String tipoAlojamiento)
	{
		log.info ("Consultando fechas de mayor ocupación ");
        List<Object[]> indicesOcupacion = pp.darMayorOcupacionSemana (tipoAlojamiento);	
        log.info ("Consultando fechas de mayor ocupación: Listo!");
        return indicesOcupacion ;
	}
	public List<Object[]> darMenorOcupacionSemana (String tipoAlojamiento)
	{
		log.info ("Consultando fechas de menor ocupación ");
        List<Object[]> indicesOcupacion = pp.darMenorOcupacionSemana (tipoAlojamiento);	
        log.info ("Consultando fechas de menor ocupación: Listo!");
        return indicesOcupacion ;
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
        for (Alojamiento tb : pp.darOfertasMasPopulares ())
        {
        	voAlojamientos.add (tb);
        }
        log.info ("Generando los VO de Alojamientos: " + voAlojamientos.size() + " existentes");
        return voAlojamientos;
	}

	// RFC4: MOSTRAR LOS ALOJAMIENTOS DISPONIBLES EN UN RANGO DE FECHAS, QUE CUMPLEN CON UN CONJUNTO DE SERVICIOS

	/**
	 * Encuentra todos alojamientos en Alohaandes y los devuelve como una lista de VOAlojamiento
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOAlojamiento con todos los alojamiento que conoce la aplicación, llenos con su información básica
	 */
	public List<VOAlojamiento> darVOAlojamientosDisponibles (Date fecha1, Date fecha2, String nombreServicio)
	{
		log.info ("Generando los VO de Alojamientos");        
        List<VOAlojamiento> voAlojamientos = new LinkedList<VOAlojamiento> ();
        for (Alojamiento tb : pp.darAlojamientosDisponibles (fecha1, fecha2, nombreServicio))
        {
        	voAlojamientos.add (tb);
        }
        log.info ("Generando los VO de Alojamientos: " + voAlojamientos.size() + " existentes");
        return voAlojamientos;
	}

	// RFC9 - ENCONTRAR LAS OFERTAS DE ALOJAMIENTO QUE NO TIENEN MUCHA DEMANDA

	/**
	 * Encuentra todos alojamientos en Alohaandes y los devuelve como una lista de VOAlojamiento
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOAlojamiento con todos los alojamiento que conoce la aplicación, llenos con su información básica
	 */
	public List<VOAlojamiento> encontrarOfertasConBajaDemanda ()
	{
		log.info ("Generando los VO de Alojamientos");        
        List<VOAlojamiento> voAlojamientos = new LinkedList<VOAlojamiento> ();
        for (Alojamiento tb : pp.encontrarOfertasConBajaDemanda ())
        {
        	voAlojamientos.add (tb);
        }
        log.info ("Generando los VO de Alojamientos: " + voAlojamientos.size() + " existentes");
        return voAlojamientos;
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

	// RF6: RETIRAR UNA OFERTA DE ALOJAMIENTO
	/**
	 * Retirar la ofertas de un alojamiento un alojamiento por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El id del alojamiento
	 * @return El número de tuplas eliminadas
	 */
	public long retirarOfertaAlojamiento (long idAlojamiento)
	{
		log.info ("Eliminando ofertas de Alojamiento por id: " + idAlojamiento);
        long resp = pp.retirarOfertaAlojamiento (idAlojamiento);		
        log.info ("Eliminando ofertas de Alojamiento por id: " + resp + " tuplas eliminadas");
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
	/**
	 * Adiciona de manera persistente un cliente
	 * Adiciona entradas al log de la aplicación
	 * @param idCliente - El id del cliente
	 * @param tipoIdentificacion - El tipo de identificación del cliente
	 * @param nombreCliente - El nombre del cliente
     * @param fechaNacimiento - La fecha de nacimiento del cliente
	 * @return El objeto Cliente adicionado. null si ocurre alguna Excepción
	 */
	public Cliente adicionarCliente (long idCliente, String tipoIdentificacion, String nombreCliente , Date fechaNacimiento)
	{
        log.info ("Adicionando Cliente: " + idCliente);
        Cliente cliente = pp.adicionarCliente (idCliente, tipoIdentificacion, nombreCliente, fechaNacimiento);		
        log.info ("Adicionando Cliente: " + cliente);
        return cliente;
	}

	/**
	 * Elimina un cliente por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idCliente - El id del cliente
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarClientePorId (long idCliente)
	{
		log.info ("Eliminando Cliente por id: " + idCliente);
        long resp = pp.eliminarClientePorId (idCliente);		
        log.info ("Eliminando Cliente por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los clientes en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos cliente con todos los clientes que conoce la aplicación, llenos con su información básica
	 */
	public List<Cliente> darClientes ()
	{
		log.info ("Consultando Clientes");
        List<Cliente> clientes = pp.darClientes ();	
        log.info ("Consultando Clientes: " + clientes.size() + " existentes");
        return clientes;
	}

	/**
	 * Encuentra todos los tipos de bebida en Alohaandes y los devuelve como una lista de VOCliente
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOCliente con todos los cliente que conoce la aplicación, llenos con su información básica
	 */
	public List<VOCliente> darVOClientes ()
	{
		log.info ("Generando los VO de Clientes");        
        List<VOCliente> voClientes = new LinkedList<VOCliente> ();
        for (Cliente tb : pp.darClientes ())
        {
        	voClientes.add (tb);
        }
        log.info ("Generando los VO de Clientes: " + voClientes.size() + " existentes");
        return voClientes;
	}

	/**
	 * Encuentra un cliente y su información básica, según su identificador
	 * @param idCliente - El id del cliente
	 * @return Un objeto Cliente que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un cliente con dicho identificador no existe
	 */
	public Cliente darClientePorId (long idCliente)
	{
        log.info ("Dar información de un cliente por id: " + idCliente);
        Cliente cliente = pp.darClientePorId (idCliente);
        log.info ("Buscando cliente por Id: " + cliente != null ? cliente : "NO EXISTE");
        return cliente;
	}

	// RFC5 - MOSTRAR EL USO DE ALOHANDES PARA CADA TIPO DE USUARIO DE LA COMUNIDAD
	/**
	 * Encuentra las principales caracteristicas de cada tipo de usuario existente
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos con las principales carecteristicas, segun el tipo de usuario
	 */
	public List<Object[]> darInformacionMiembrosActivos ()
	{
		log.info ("Consultando las principales caracteristicas de los diferentes tipos de miembros activos");
        List<Object[]> informacionMiembrosActivos = pp.darInformacionMiembrosActivos ();	
        log.info ("Consultando las principales caracteristicas de los diferentes tipos de miembros activos: Listo!");
        return informacionMiembrosActivos;
	}

	public List<Object[]> darInformacionMiembrosSecundarios ()
	{
		log.info ("Consultando las principales caracteristicas de los diferentes tipos de miembros secundarios");
        List<Object[]> informacionMiembrosSecundarios = pp.darInformacionMiembrosSecundarios ();	
        log.info ("Consultando las principales caracteristicas de los diferentes tipos de miembros secundarios: Listo!");
        return informacionMiembrosSecundarios;
	}

	// RFC8 - ENCONTRAR LOS CLIENTES FRECUENTES
	/**
	 * Encuentra los clintes frecuentes en el alojamiento dado
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOCliente con los clientes frecuentes de la aplicación, llenos con su información básica
	 */
	public List<VOCliente> encontrarClientesFrecuentes(long idAlojamiento)
	{
		log.info ("Generando los VO de Clientes");        
        List<VOCliente> voClientes = new LinkedList<VOCliente> ();
        for (Cliente cl : pp.encontrarClientesFrecuentes (idAlojamiento))
        {
        	voClientes.add (cl);
        }
        log.info ("Generando los VO de Clientes: " + voClientes.size() + " existentes");
        return voClientes;
	}

	// RFC10 - CONSULTAR CONSUMO EN ALOHANDES
	public List<VOCliente> consultarConsumoV1 (Date fechaInit, Date fechaMax, String tipo)
	{
		log.info ("Generando los VO de Clientes con al menos una Reserva");    
        List<VOCliente> voClientes = new LinkedList<VOCliente> ();
        for (Cliente cl : pp.consultarConsumoV1 (fechaInit, fechaMax, tipo))
        {
        	voClientes.add (cl);
        }
        log.info ("Generando los VO de Clientes con al menos una Reserva: " + voClientes.size() + " existentes");
        return voClientes;
	}


	// RFC11 - CONSULTAR CONSUMO EN ALOHANDES VERSION 2
	public List<VOCliente> consultarConsumoV2 (Date fechaInit, Date fechaMax, String tipo)
	{
		log.info ("Generando los VO de Clientes sin al menos una Reserva");        
        List<VOCliente> voClientes = new LinkedList<VOCliente> ();
        for (Cliente cl : pp.consultarConsumoV2 (fechaInit, fechaMax, tipo))
        {
        	voClientes.add (cl);
        }
        log.info ("Generando los VO de Clientes sin al menos una Reserva: " + voClientes.size() + " existentes");
        return voClientes;
	}


	// RFC13 - CONSULTAR LOS BUENOS CLIENTES
	public List<VOCliente> encontrarBuenosClientes1 ()
	{
		log.info ("Generando los VO de Clientes buenos que hacen al menos una resrva al mes");        
        List<VOCliente> voClientes = new LinkedList<VOCliente> ();
        for (Cliente cl : pp.encontrarBuenosClientes1 ())
        {
        	voClientes.add (cl);
        }
        log.info ("Generando los VO de Clientes buenos que hacen al menos una resrva al mes: " + voClientes.size() + " existentes");
        return voClientes;
	}

	public List<VOCliente> encontrarBuenosClientes2 ()
	{
		log.info ("Generando los VO de Clientes que solo reservan alojamientos que valen mas de 150$ por noche");        
        List<VOCliente> voClientes = new LinkedList<VOCliente> ();
        for (Cliente cl : pp.encontrarBuenosClientes2 ())
        {
        	voClientes.add (cl);
        }
        log.info ("Generando los VO de Clientes que solo reservan alojamientos que valen mas de 150$ por noche: " + voClientes.size() + " existentes");
        return voClientes;
	}

	public List<VOCliente> encontrarBuenosClientes3 ()
	{
		log.info ("Generando los VO de Clientes que solo reservan suites cuando van a hoteles");        
        List<VOCliente> voClientes = new LinkedList<VOCliente> ();
        for (Cliente cl : pp.encontrarBuenosClientes3 ())
        {
        	voClientes.add (cl);
        }
        log.info ("Generando los VO de Clientes que solo reservan suites cuando van a hoteles: " + voClientes.size() + " existentes");
        return voClientes;
	}


	 /* ****************************************************************
	 * 			Métodos para manejar los MIEMBROS ACTIVOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un miembroactivo
	 * Adiciona entradas al log de la aplicación
	 * @param idMiembroActivo - El id del Miembro Activo
	 * @param carnet - Número de carnet del Miembro Activo
     * @param tipo - Nombre del Miembro Activo
	 * @return El objeto MiembroActivo adicionado. null si ocurre alguna Excepción
	 */
	public MiembroActivo adicionarMiembroActivo (long idMiembroActivo, String carnet, String tipo)
	{
        log.info ("Adicionando MiembroActivo: " + idMiembroActivo);
        MiembroActivo miembroActivo = pp.adicionarMiembroActivo (idMiembroActivo, carnet, tipo);		
        log.info ("Adicionando MiembroActivo: " + miembroActivo);
        return miembroActivo;
	}

	/**
	 * Elimina un miembroactivo por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idMiembroActivo - El id del miembroactivo
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarMiembroActivoPorId (long idMiembroActivo)
	{
		log.info ("Eliminando MiembroActivo por id: " + idMiembroActivo);
        long resp = pp.eliminarMiembroActivoPorId (idMiembroActivo);		
        log.info ("Eliminando MiembroActivo por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los miembrosactivos en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos miembroactivo con todos los miembrosactivos que conoce la aplicación, llenos con su información básica
	 */
	public List<MiembroActivo> darMiembrosActivos ()
	{
		log.info ("Consultando MiembroActivos");
        List<MiembroActivo> miembroactivos = pp.darMiembrosActivos ();	
        log.info ("Consultando MiembroActivos: " + miembroactivos.size() + " existentes");
        return miembroactivos;
	}

	/**
	 * Encuentra todos los tipos de bebida en Alohaandes y los devuelve como una lista de VOMiembroActivo
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOMiembroActivo con todos los miembroactivo que conoce la aplicación, llenos con su información básica
	 */
	public List<VOMiembroActivo> darVOMiembrosActivos ()
	{
		log.info ("Generando los VO de MiembroActivos");        
        List<VOMiembroActivo> voMiembrosActivos = new LinkedList<VOMiembroActivo> ();
        for (MiembroActivo tb : pp.darMiembrosActivos ())
        {
        	voMiembrosActivos.add (tb);
        }
        log.info ("Generando los VO de Miembros Activos: " + voMiembrosActivos.size() + " existentes");
        return voMiembrosActivos;
	}

	/**
	 * Encuentra un miembroactivo y su información básica, según su identificador
	 * @param idMiembroActivo - El id del miembroactivo
	 * @return Un objeto MiembroActivo que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un miembroactivo con dicho identificador no existe
	 */
	public MiembroActivo darMiembroActivoPorId (long idMiembroActivo)
	{
        log.info ("Dar información de un miembroactivo por id: " + idMiembroActivo);
        MiembroActivo miembroActivo = pp.darMiembroActivoPorId (idMiembroActivo);
        log.info ("Buscando miembro activo por Id: " + miembroActivo != null ? miembroActivo : "NO EXISTE");
        return miembroActivo;
	}

	 /* ****************************************************************
	 * 			Métodos para manejar los MIEMBROS SECUNDARIOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un miembro secundario
	 * Adiciona entradas al log de la aplicación
	 * @param idMiembroSecundario - El id del Miembro Secundario
     * @param tipo - Nombre del Miembro Secundario
	 * @return El objeto MiembroSecundario adicionado. null si ocurre alguna Excepción
	 */
	public MiembroSecundario adicionarMiembroSecundario (long idMiembroSecundario, String tipo)
	{
        log.info ("Adicionando MiembroSecundario: " + idMiembroSecundario);
        MiembroSecundario miembroSecundario = pp.adicionarMiembroSecundario (idMiembroSecundario, tipo);		
        log.info ("Adicionando MiembroSecundario: " + miembroSecundario);
        return miembroSecundario;
	}

	/**
	 * Elimina un miembrosecundario por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idMiembroSecundario - El id del miembrosecundario
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarMiembroSecundarioPorId (long idMiembroSecundario)
	{
		log.info ("Eliminando MiembroSecundario por id: " + idMiembroSecundario);
        long resp = pp.eliminarMiembroSecundarioPorId (idMiembroSecundario);		
        log.info ("Eliminando MiembroSecundario por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los miembrossecundarios en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos miembrosecundario con todos los miembrossecundarios que conoce la aplicación, llenos con su información básica
	 */
	public List<MiembroSecundario> darMiembrosSecundarios ()
	{
		log.info ("Consultando MiembroSecundarios");
        List<MiembroSecundario> miembrosecundarios = pp.darMiembrosSecundarios ();	
        log.info ("Consultando MiembroSecundarios: " + miembrosecundarios.size() + " existentes");
        return miembrosecundarios;
	}

	/**
	 * Encuentra todos los tipos de bebida en Alohaandes y los devuelve como una lista de VOMiembroSecundario
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOMiembroSecundario con todos los miembrosecundario que conoce la aplicación, llenos con su información básica
	 */
	public List<VOMiembroSecundario> darVOMiembrosSecundarios ()
	{
		log.info ("Generando los VO de MiembroSecundarios");        
        List<VOMiembroSecundario> voMiembrosSecundarios = new LinkedList<VOMiembroSecundario> ();
        for (MiembroSecundario tb : pp.darMiembrosSecundarios ())
        {
        	voMiembrosSecundarios.add (tb);
        }
        log.info ("Generando los VO de Miembros Secundarios: " + voMiembrosSecundarios.size() + " existentes");
        return voMiembrosSecundarios;
	}

	/**
	 * Encuentra un miembrosecundario y su información básica, según su identificador
	 * @param idMiembroSecundario - El id del miembrosecundario
	 * @return Un objeto MiembroSecundario que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un miembrosecundario con dicho identificador no existe
	 */
	public MiembroSecundario darMiembroSecundarioPorId (long idMiembroSecundario)
	{
        log.info ("Dar información de un miembrosecundario por id: " + idMiembroSecundario);
        MiembroSecundario miembroSecundario = pp.darMiembroSecundarioPorId (idMiembroSecundario);
        log.info ("Buscando miembro secundario por Id: " + miembroSecundario != null ? miembroSecundario : "NO EXISTE");
        return miembroSecundario;
	}


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
	/**
	 * Adiciona de manera persistente un reserva
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
        log.info ("Adicionando Reserva: " );
        Reserva reserva = pp.adicionarReserva(idAlojamiento, idCliente, duracion , fechaInicio, fechaFinal, costoTotal, estado, numPersonas);		
        log.info ("Adicionando Reserva: " + reserva);
        return reserva;
	}
	/**
	 * Elimina un reserva por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idReserva - El id de la reserva
	 * @return El número de tuplas modificadas
	 */
	public long cancelarReservaPorId (long idReserva)
	{
		log.info ("Cancelar Reserva por id: " + idReserva);
        long resp = pp.cancelarReservaPorId (idReserva);		
        log.info ("Cancelando Reserva por id: " + resp + " tuplas modificadas");
        return resp;
	}

	// RF7 - REGISTRAR RESERVA COLECTIVA
	/**
	 * Encuentra aquellas reservas del tipo de habitacion requrido que pueden ser reservadas
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista con los alojamientos disponibles de este tipo de habitacion 
	 */
	public List<Object[]> RevisarReservaColectiva (String TipodeAlojamiento ,Date fechaInicio, Date fechaFinal)
	{
		log.info ("Consultando informacion acerca de los alojamientos de este tipo de habitacion disponibles");
        List<Object[]> alojamientosPorTipoHabitacionDisponibles = pp.RevisarReservaColectiva (TipodeAlojamiento, fechaInicio, fechaFinal);	
        log.info ("Consultando informacion acerca de los alojamientos de este tipo de habitacion disponibles: Listo!");
        return alojamientosPorTipoHabitacionDisponibles ;
	}

	public long obtenerIdReservaColectiva ()
	{
		log.info ("Consultando el id de la reserva colectiva");
        long idReservaColectivaActual = pp.obtenerIdReservaColectiva ();	
        log.info ("Consultando el id de la reserva colectiva: Listo!");
        return idReservaColectivaActual ;
	}

	public Reserva RegistrarReservaIndividual( long idAlojamiento, long idCliente, int duracion, Date fechaInicio , Date fechaFinal, long costoTotal, String estado, int numPersonas, long idReservaColectiva)
	{
        log.info ("Adicionando Reserva individual: " );
        Reserva reserva = pp.RegistrarReservaIndividual(idAlojamiento, idCliente, duracion , fechaInicio, fechaFinal, costoTotal, estado, numPersonas, idReservaColectiva);		
        log.info ("Adicionando Reserva individual: " + reserva);
        return reserva;
	}



	// RF8 - CANCELAR RESERVA COLECTIVA
	/**
	 * Cancelar una reserva colectiva por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idReservaColectiva - El id de la reserva Colectiva
	 * @return El número de tuplas modificadas
	 */
	public long cancelarReservaColectivaPorId (long idReservaColectiva)
	{
		log.info ("Cancelar Reserva colectiva por id: " + idReservaColectiva);
        long resp = pp.cancelarReservaColectivaPorId (idReservaColectiva);		
        log.info ("Cancelando Reserva colectiva por id: " + resp + " tuplas modificadas");
        return resp;
	}

	/**
	 * Revisa las reservas del cliente en ceirtas fechas
	 * Adiciona entradas al log de la aplicación
	 * @param idCliente - El id del cliente
	 * @param fechaInicio - La fecha de inicio de la reserva
     * @param fechaFinal - La fecha final de la reserva
	 * @return El número de tuplas encontradas
	 */
	public long revisarReserva (long idCliente, Date fechaInicio, Date fechaFinal) 
	{
		log.info ("Revisando reservas del cliente: " + idCliente);
        long resp = pp.revisarReserva (idCliente,fechaInicio,fechaFinal);		
        log.info ("Reservas encontradas: " + resp );
        return resp;
	}
	/**
	 * Elimina un reserva por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idReserva - El id de la reserva
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarReservaPorId (long idReserva)
	{
		log.info ("Eliminando Reserva por id: " + idReserva);
        long resp = pp.eliminarReservaPorId (idReserva);		
        log.info ("Eliminando Reserva por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	/**
	 * Encuentra todos los reservas en Alohaandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos reserva con todos los reservas que conoce la aplicación, llenos con su información básica
	 */
	public List<Reserva> darReservas ()
	{
		log.info ("Consultando Reservas");
        List<Reserva> reservas = pp.darReservas ();	
        log.info ("Consultando Reservas: " + reservas.size() + " existentes");
        return reservas;
	}
	/**
	 * Encuentra todos los tipos de bebida en Alohaandes y los devuelve como una lista de VOReserva
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOReserva con todos los reservas que conoce la aplicación, llenos con su información básica
	 */
	public List<VOReserva> darVOReservas ()
	{
		log.info ("Generando los VO de Reservas");        
        List<VOReserva> voReservas = new LinkedList<VOReserva> ();
        for (Reserva tb : pp.darReservas ())
        {
        	voReservas.add (tb);
        }
        log.info ("Generando los VO de Reservas: " + voReservas.size() + " existentes");
        return voReservas;
	}
	/**
	 * Encuentra un reserva y su información básica, según su identificador
	 * @param idReserva - El id del reserva
	 * @return Un objeto Reserva que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un reserva con dicho identificador no existe
	 */
	public Reserva darReservaPorId (long idReserva)
	{
        log.info ("Dar información de un reserva por id: " + idReserva);
        Reserva reserva = pp.darReservaPorId (idReserva);
        log.info ("Buscando reserva por Id: " + reserva != null ? reserva : "NO EXISTE");
        return reserva;
	}

	// RFC6 - MOSTRAR EL USO DE ALOHANDES PARA UN USUARIO DADO
	/**
	 * Encuentra las principales caracteristicas (coches reservadas, dinero pagado y numero de reservas)
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos reserva con las caracteristicas principales de un cliente dado
	 */
	public List<Object[]> mostrarUsoPorUsuario (long idcliente)
	{
		log.info ("Consultando informacion acerca de la reservas a nombre de ese cliente");
        List<Object[]> caracteristicasReservasCliente = pp.mostrarUsoPorUsuario (idcliente);	
        log.info ("Consultando informacion acerca de la reservas a nombre de ese cliente: Listo!");
        return caracteristicasReservasCliente ;
	}

	// RFC12 - CONSULTAR FUNCIONAMIENTO
	public List<Object[]> consultarFuncionamiento1 ()
	{
		log.info ("Consultando informacion acerca del funcionamiento por semana de Alohaandes (ocupacion)");
        List<Object[]> funcionamiento = pp.consultarFuncionamiento1 ();	
        log.info ("Consultando informacion acerca del funcionamiento por semana de Alohaandes (ocupacion): Listo!");
        return funcionamiento ;
	}

	public List<Object[]> consultarFuncionamiento2 ()
	{
		log.info ("Consultando informacion acerca del funcionamiento por semana de Alohaandes (operadores solicitados)");
        List<Object[]> funcionamiento = pp.consultarFuncionamiento2 ();	
        log.info ("Consultando informacion acerca del funcionamiento por semana de Alohaandes (operadores solicitados): Listo!");
        return funcionamiento ;
	}

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
