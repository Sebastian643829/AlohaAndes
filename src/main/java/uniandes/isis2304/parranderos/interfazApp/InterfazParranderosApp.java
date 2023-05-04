package uniandes.isis2304.parranderos.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.datanucleus.enhancer.methods.NewInstance1;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.Parranderos;
import uniandes.isis2304.parranderos.negocio.Alojamiento;
import uniandes.isis2304.parranderos.negocio.Credenciales;
import uniandes.isis2304.parranderos.negocio.VOAlojamiento;
import uniandes.isis2304.parranderos.negocio.VOViviendaUniversitaria;
import uniandes.isis2304.parranderos.negocio.VOOperador;
import uniandes.isis2304.parranderos.negocio.VOPropietario;
import uniandes.isis2304.parranderos.negocio.VOEmpresa;
import uniandes.isis2304.parranderos.negocio.VOHabitacionVivienda;
import uniandes.isis2304.parranderos.negocio.VOApartamento;
import uniandes.isis2304.parranderos.negocio.VOHabitacionHotel;
import uniandes.isis2304.parranderos.negocio.VOHostal;
import uniandes.isis2304.parranderos.negocio.VOViviendaTemporal;
import uniandes.isis2304.parranderos.negocio.VOCliente;
import uniandes.isis2304.parranderos.negocio.VOMiembroActivo;
import uniandes.isis2304.parranderos.negocio.VOMiembroSecundario;
import uniandes.isis2304.parranderos.negocio.VOServicio;
import uniandes.isis2304.parranderos.negocio.VODispone;
import uniandes.isis2304.parranderos.negocio.VOReserva;

/**
 * Clase principal de la interfaz
 * @author Juan Sebastian Sanchez
 */
@SuppressWarnings("serial")

public class InterfazParranderosApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazParranderosApp.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private Parranderos parranderos;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;


	// indica si ya el usuario se registro
	boolean sesionEnCurso = false;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazParranderosApp( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        parranderos = new Parranderos (tableConfig);
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "Alohaandes APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }

	/* ****************************************************************
	 * 			Funciones de SESION
	 *****************************************************************/
	public void iniciarSesion( )
    {
		String usuario = JOptionPane.showInputDialog (this, "Usuario: ", "Inicio de sesion", JOptionPane.QUESTION_MESSAGE);
		String contrasena = JOptionPane.showInputDialog (this, "Contraseña: ", "Inicio de sesion", JOptionPane.QUESTION_MESSAGE);
		String resultado = "";

		Credenciales credenciales = new Credenciales();
		Map<String, String> credencialesActuales = credenciales.getCredenciales();

		for (String key : credencialesActuales.keySet()) {
			if (sesionEnCurso){
				resultado = "Ya hay una sesion iniciada \n";
				resultado += "Si quiere iniciar sesion con otra cuenta primero cierre sesion";
				panelDatos.actualizarInterfaz(resultado);
			}
			else if (usuario.equals(key) && contrasena.equals(credencialesActuales.get(key))){
				sesionEnCurso = true;
				resultado = "Se inicio sesion de manera EXITOSA \n";
				resultado += "Ahora si cuenta con los permisos para el uso del sistema";
				panelDatos.actualizarInterfaz(resultado);
				break;
			}
		}
		if (sesionEnCurso == false){
			resultado = "No se pudo iniciar sesion \n\n";
			resultado += "Vuelva a intentar de nuevo";
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void cerrarSesion( )
    {
		sesionEnCurso = false;
		String resultado = "Se ha cerrado la sesion correctamente \n";
		panelDatos.actualizarInterfaz(resultado);
	}

	

	/* ****************************************************************
	 * 			CRUD de Operador
	 *****************************************************************/
    /**
     * Adiciona un Operador con la información dada por el usuario
     * Se crea una nueva tupla de Operador en la base de datos, si un Operador con ese nombre no existía
     */

	public void adicionarOperador( )
    {
		try 
    	{
    		String telefono = JOptionPane.showInputDialog (this, "Teléfono?", "Adicionar nuevo operador", JOptionPane.QUESTION_MESSAGE);
			String tipoVinculacion = JOptionPane.showInputDialog (this, "Cual es el tipo de vinculacion?", "Adicionar nuevo operador", JOptionPane.QUESTION_MESSAGE);

    		if (telefono != null && tipoVinculacion != null && sesionEnCurso)
    		{
        		VOOperador tb = parranderos.adicionarOperador (telefono,tipoVinculacion);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear el operador ");
        		}
        		String resultado = "En adicionarOperador\n\n";
        		resultado += "Operador adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	 /* ****************************************************************
	 * 			CRUD de Propietario
	 *****************************************************************/
    /**
     * Adiciona un Propietario con la información dada por el usuario
     * Se crea una nueva tupla de Propietario en la base de datos, si un Propietario con ese nombre no existía
     */

	 public void adicionarPropietario( )
	 {
		try 
    	{
			String idOperador = JOptionPane.showInputDialog (this, "ID del operador (ya existente)?", "Adicionar nuevo propietario", JOptionPane.QUESTION_MESSAGE);
    		String identificación = JOptionPane.showInputDialog (this, "Identificación del propietario?", "Adicionar nuevo propietario", JOptionPane.QUESTION_MESSAGE);
			String tipoVinculacion = JOptionPane.showInputDialog (this, "Tipo de vinculación?", "Adicionar nuevo propietario", JOptionPane.QUESTION_MESSAGE);
			String nombrePropietario = JOptionPane.showInputDialog (this, "Nombre Propietario?", "Adicionar nuevo propietario", JOptionPane.QUESTION_MESSAGE);
			

    		if (idOperador != null && tipoVinculacion != null && identificación != null && nombrePropietario != null && sesionEnCurso)
    		{
        		VOPropietario tb = parranderos.adicionarPropietario (Long.parseLong(idOperador),identificación,tipoVinculacion,nombrePropietario);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear el propietario ");
        		}
        		String resultado = "En adicionarPropietario\n\n";
        		resultado += "Propietario adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
 
	 }


	/* ****************************************************************
	 * 			CRUD de Empresa
	 *****************************************************************/
    /**
     * Adiciona una Empresa con la información dada por el usuario
     * Se crea una nueva tupla de Empresa en la base de datos, si una Empresa con ese nombre no existía
     */

	 public void adicionarEmpresa( )
	 {
		try 
    	{
			String idOperador = JOptionPane.showInputDialog (this, "ID del operador (ya existente)?", "Adicionar nueva empresa", JOptionPane.QUESTION_MESSAGE);
    		String nit = JOptionPane.showInputDialog (this, "Nit de la empresa?", "Adicionar nueva empresa", JOptionPane.QUESTION_MESSAGE);
			String nombreEmpresa = JOptionPane.showInputDialog (this, "Nombre Empresa?", "Adicionar nueva empresa", JOptionPane.QUESTION_MESSAGE);
			

    		if (idOperador != null && nit != null && nombreEmpresa != null && sesionEnCurso)
    		{
        		VOEmpresa tb = parranderos.adicionarEmpresa (Long.parseLong(idOperador),nit,nombreEmpresa);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear la empresa ");
        		}
        		String resultado = "En adicionarEmpresa\n\n";
        		resultado += "Empresa adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
 
	 }

	/* ****************************************************************
	 * 			CRUD de Alojamiento
	 *****************************************************************/
    /**
     * Adiciona un Alojamiento con la información dada por el usuario
     * Se crea una nueva tupla de Alojamiento en la base de datos, si un Alojamiento con ese nombre no existía
     */

	 public void adicionarAlojamiento( )
	 {
		try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre?", "Adicionar nuevo alojamiento", JOptionPane.QUESTION_MESSAGE);
			String capacidad = JOptionPane.showInputDialog (this, "Capacidad (Numero natural)?", "Adicionar nuevo alojamiento", JOptionPane.QUESTION_MESSAGE);
			String ubicacion = JOptionPane.showInputDialog (this, "Ubicacion?", "Adicionar nuevo alojamiento", JOptionPane.QUESTION_MESSAGE);
			String tamano = JOptionPane.showInputDialog (this, "Tamaño?", "Adicionar nuevo alojamiento (Numero natural)", JOptionPane.QUESTION_MESSAGE);
			String precioNoche = JOptionPane.showInputDialog (this, "Precio por noche (Numero natural)?", "Adicionar nuevo alojamiento", JOptionPane.QUESTION_MESSAGE);
			int ocupacionTotal = 0;
			int numReservas = 0;
			String idOperador = JOptionPane.showInputDialog (this, "Id del operador a cargo del alojamiento (Ya existente)?", "Adicionar nuevo alojamiento", JOptionPane.QUESTION_MESSAGE);
			String estado = "Habilitado";
			String tipo = JOptionPane.showInputDialog (this, "Tipo de alojamiento?", "Adicionar nuevo alojamiento", JOptionPane.QUESTION_MESSAGE);			

    		if (nombre != null && capacidad != null && ubicacion != null && tamano != null && precioNoche != null && idOperador != null && sesionEnCurso)
    		{
        		VOAlojamiento tb = parranderos.adicionarAlojamiento (nombre, Integer.parseInt(capacidad), ubicacion, Integer.parseInt(tamano), Integer.parseInt(precioNoche), ocupacionTotal, numReservas, Long.parseLong(idOperador), estado, tipo);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un alojamiento con el nombre: " + nombre);
        		}
        		String resultado = "En adicionarAlojamiento\n\n";
        		resultado += "Alojamiento adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }
	 // RFC1: MOSTRAR EL ÍNDICE DE OCUPACIÓN DE CADA UNA DE LAS OFERTAS DE ALOJAMIENTO REGISTRADAS
	/**
     * Consulta en la base de datos las 20 ofertas de alojamiento mas populares
     */
    public void darDinero( )
    {
    	try 
    	{
			List <Object[]> lista = parranderos.darDinero();

			if (sesionEnCurso){
			String resultado = "Listando el dinero de cada proveedor en el año corrido y actual";
			resultado +=  "\n" + listarDarDinero(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
			}
			else{
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	/**
     * Genera una cadena de caracteres con la lista de los alojamientos mas populares recibida: una línea por cada alojamiento
     * @param lista - La lista con los alojamientos
     * @return La cadena con una línea para cada alojamiento recibido
     */
    private String listarDarDinero(List<Object[]> lista) 
    {
    	String resp = "El dinero de cada proveedor se muestra a continuación:\n";
    	int i = 1;
        for (Object [] aloj : lista)
        {
			long idAlojamiento=(long) aloj[0];
			long dinero=(long) aloj[1];
        	resp += i++ + ". "  + "[";
			resp += "ID del Proveedor: "+idAlojamiento;
			resp += ", Dinero: "+dinero+" COP]"+"\n";
        }
		return resp;
	} 
	// RFC2: MOSTRAR LAS 20 OFERTAS MÁS POPULARES
	/**
     * Consulta en la base de datos las 20 ofertas de alojamiento mas populares
     */
    public void darOfertasMasPopulares( )
    {
    	try 
    	{
			List <VOAlojamiento> lista = parranderos.darVOAlojamientos();

			if (sesionEnCurso){
			String resultado = "Listando alojamientos mas populares";
			resultado +=  "\n" + listarOfertasPopulares (lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
			}
			else{
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	/**
     * Genera una cadena de caracteres con la lista de los alojamientos mas populares recibida: una línea por cada alojamiento
     * @param lista - La lista con los alojamientos
     * @return La cadena con una línea para cada alojamiento recibido
     */
    private String listarOfertasPopulares(List<VOAlojamiento> lista) 
    {
    	String resp = "Los alojamientos mas populares son:\n";
    	int i = 1;
        for (VOAlojamiento aloj : lista)
        {
        	resp += i++ + ". " + aloj.toString() + "\n";
        }
		return resp;
	} 
	// RFC3: MOSTRAR EL ÍNDICE DE OCUPACIÓN DE CADA UNA DE LAS OFERTAS DE ALOJAMIENTO REGISTRADAS
	/**
     * Consulta en la base de datos las 20 ofertas de alojamiento mas populares
     */
    public void darIndiceDeOcupacion( )
    {
    	try 
    	{
			List <Object[]> lista = parranderos.darIndiceDeOcupacion();

			if (sesionEnCurso){
			String resultado = "Listando indices de ocupación de los alojamientos";
			resultado +=  "\n" + listarIndicesOcupacion(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
			}
			else{
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	/**
     * Genera una cadena de caracteres con la lista de los alojamientos mas populares recibida: una línea por cada alojamiento
     * @param lista - La lista con los alojamientos
     * @return La cadena con una línea para cada alojamiento recibido
     */
    private String listarIndicesOcupacion(List<Object[]> lista) 
    {
    	String resp = "Los indices de ocupación de los alojamientos son:\n";
    	int i = 1;
        for (Object [] aloj : lista)
        {
			
			long idAlojamiento=(long) aloj[0];
			String nombre=(String) aloj[1];
			long indiceOcupacion=(long) aloj[2];
        	resp += i++ + ". "  + "[";
			resp += "ID del Alojamiento: "+idAlojamiento;
			resp += ", Nombre del Alojamiento: "+nombre;
			resp += ", Indice de ocupación del Alojamiento: "+indiceOcupacion+"%]"+"\n";
        }
		return resp;
	} 
	// RFC4: MOSTRAR LOS ALOJAMIENTOS DISPONIBLES EN UN RANGO DE FECHAS, QUE CUMPLEN CON UN CONJUNTO DE SERVICIOS
	 /**
     * Adiciona un Alojamiento con la información dada por el usuario
     * Se crea una nueva tupla de Alojamiento en la base de datos, si un Alojamiento con ese nombre no existía
     */
	 public void darAlojamientosDisponibles( )
	 {
		try 
    	{
    		String fecha1 = JOptionPane.showInputDialog (this, "Fechas inicial ('aaaa-mm-dd')?", "Dar alojamientos disponibles", JOptionPane.QUESTION_MESSAGE);
			String fecha2 = JOptionPane.showInputDialog (this, "Fecha final ('aaaa-mm-dd')?", "Dar alojamientos disponibles", JOptionPane.QUESTION_MESSAGE);
			String nombreServicio = JOptionPane.showInputDialog (this, "Nombre del servicio?", "Dar alojamientos disponibles", JOptionPane.QUESTION_MESSAGE);

			List <VOAlojamiento> lista = parranderos.darVOAlojamientosDisponibles(Date.valueOf(fecha1), Date.valueOf(fecha2), nombreServicio);

    		if (fecha1 != null && fecha2 != null && nombreServicio  != null && sesionEnCurso)
    		{
				String resultado = "Listando alojamientos que cumplen las condiciones dadas";
        		resultado +=  "\n" + listarAlojamientosDisponibles (lista);;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }

	 /**
     * Genera una cadena de caracteres con la lista de los alojamientos disponibles recibida: una línea por cada alojamiento
     * @param lista - La lista con los alojamientos
     * @return La cadena con una línea para cada alojamiento recibido
     */
    private String listarAlojamientosDisponibles(List<VOAlojamiento> lista) 
    {
    	String resp = "Los alojamientos disponibles que cumplen las condiciones dadas son:\n";
    	int i = 1;
        for (VOAlojamiento aloj : lista)
        {
        	resp += i++ + ". " + aloj.toString() + "\n";
        }
		return resp;
	} 

	//  RFC9 - ENCONTRAR LAS OFERTAS DE ALOJAMIENTO QUE NO TIENEN MUCHA DEMANDA
	 /**
     * Retorna el listado de las alojamientos con poca demanda
     */
	public void encontrarOfertasConBajaDemanda( )
	{
	   try 
	   {
		   List <VOAlojamiento> lista = parranderos.encontrarOfertasConBajaDemanda();

		   if (sesionEnCurso)
		   {
			   String resultado = "Listando alojamientos que cumplen las condiciones dadas";
			   resultado +=  "\n" + listarAlojamientosConPocaDemanda (lista);;
			   resultado += "\n Operación terminada";
			   panelDatos.actualizarInterfaz(resultado);
		   }
		   else if (!sesionEnCurso){
			   String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
			   resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
			   panelDatos.actualizarInterfaz(resultado);
		   }
		   else
		   {
			   panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
		   }
	   } 
	   catch (Exception e) 
	   {
//			e.printStackTrace();
		   String resultado = generarMensajeError(e);
		   panelDatos.actualizarInterfaz(resultado);
	   }
	}

	/**
	* Genera una cadena de caracteres con la lista de los alojamientos con poca demanda recibida: una línea por cada alojamiento
	* @param lista - La lista con los alojamientos
	* @return La cadena con una línea para cada alojamiento recibido
	*/
   private String listarAlojamientosConPocaDemanda(List<VOAlojamiento> lista) 
   {
	   String resp = "Los alojamientos con poca demanda que no han sido reservados hace mas de un mes son:\n";
	   int i = 1;
	   for (VOAlojamiento aloj : lista)
	   {
		   resp += i++ + ". " + aloj.toString() + "\n";
	   }
	   return resp;
   } 


	 // RF6: RETIRAR UNA OFERTA DE ALOJAMIENTO
	 /**
     * Borra de la base de datos el alojamiento con el identificador dado por el usuario
     * Cuando dicho Alojamiento no existe, se indica que se borraron 0 registros de la base de datos
	 * Si quedan reservas vigentes pendientes, entonces no se podrá eliminar el alojamiento
     */
	 public void retirarOfertaAlojamiento( )
	 {
		try 
    	{
    		String idAlojamientoStr = JOptionPane.showInputDialog (this, "Id del alojamiento?", "Retirar alojamiento por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idAlojamientoStr != null && sesionEnCurso)
    		{
    			long idAlojamiento = Long.valueOf (idAlojamientoStr);

				long reservasEliminadas = parranderos.retirarOfertaAlojamiento (idAlojamiento); 
				System.out.println(reservasEliminadas);
				if (reservasEliminadas != 0){
    			long alojamientosEliminados = parranderos.eliminarAlojamientoPorId (idAlojamiento);
    			String resultado = "En eliminar Alojamiento\n\n";
    			resultado += "Se elimino el alojamiento con exito\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
				}
				else{
					String resultado = "En eliminar Alojamiento\n\n";
					resultado += "No se pudo eliminar el alojamiento con exito\n";
					resultado += "\n Verifique que el alojamiento exista y que no quede pendiente ninguna reserva vigente";
					panelDatos.actualizarInterfaz(resultado);
				}
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }


	 /* ****************************************************************
	 * 			CRUD de ViviendaUniversitaria
	 *****************************************************************/
    /**
     * Adiciona una ViviendaUniversitaria con la información dada por el usuario
     * Se crea una nueva tupla de ViviendaUniversitaria en la base de datos, si una ViviendaUniversitaria con ese nombre no existía
     */

	 public void adicionarViviendaUniversitaria( )
	 {
		try 
    	{
    		String idAlojamiento = JOptionPane.showInputDialog (this, "Id de la vivienda universitaria (debe ser de un alojamiento ya existente)?", "Adicionar nueva vivienda universitaria", JOptionPane.QUESTION_MESSAGE);
			String tipoHabitacion = JOptionPane.showInputDialog (this, "Tipo de habitacion ('Compartida' o 'Individual')?", "Adicionar nueva vivienda universitaria", JOptionPane.QUESTION_MESSAGE);

    		if (idAlojamiento != null && tipoHabitacion != null && sesionEnCurso)
    		{
        		VOViviendaUniversitaria tb = parranderos.adicionarViviendaUniversitaria (Long.parseLong(idAlojamiento), tipoHabitacion);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una vivienda universitarica con el id: " + idAlojamiento);
        		}
        		String resultado = "En adicionarViviendaUniversitaria\n\n";
        		resultado += "ViviendaUniversitaria adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }


	/* ****************************************************************
	 * 			CRUD de HabitacionVivienda
	 *****************************************************************/
    /**
     * Adiciona una HabitacionVivienda con la información dada por el usuario
     * Se crea una nueva tupla de HabitacionVivienda en la base de datos, si una HabitacionVivienda con ese nombre no existía
     */

	 public void adicionarHabitacionVivienda( )
	 {
		try 
    	{
    		String idAlojamiento = JOptionPane.showInputDialog (this, "Id de la habitacion de vivienda (debe ser de un alojamiento ya existente)?", "Adicionar nueva habitacion de vivienda", JOptionPane.QUESTION_MESSAGE);
			String tipoBano = JOptionPane.showInputDialog (this, "Tipo de baño ('Privado' o 'Publico')?", "Adicionar nueva habitacion de vivienda", JOptionPane.QUESTION_MESSAGE);
			String tipoHabitacion = JOptionPane.showInputDialog (this, "Tipo de habitacion (Compartida o 'Individual')?", "Adicionar nueva habitacion de vivienda", JOptionPane.QUESTION_MESSAGE);

    		if (idAlojamiento != null && tipoHabitacion != null && tipoBano != null && sesionEnCurso)
    		{
        		VOHabitacionVivienda tb = parranderos.adicionarHabitacionVivienda (Long.parseLong(idAlojamiento),tipoBano, tipoHabitacion);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una habitacion de vivienda con el id: " + idAlojamiento);
        		}
        		String resultado = "En adicionarHabitacionVivienda\n\n";
        		resultado += "HabitacionVivienda adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }


	/* ****************************************************************
	 * 			CRUD de Apartamento
	 *****************************************************************/
    /**
     * Adiciona una Apartamento con la información dada por el usuario
     * Se crea una nueva tupla de Apartamento en la base de datos, si una Apartamento con ese nombre no existía
     */

	 public void adicionarApartamento( )
	 {
		try 
    	{
    		String idAlojamiento = JOptionPane.showInputDialog (this, "Id del apartamento (debe ser de un alojamiento ya existente)?", "Adicionar nuevo apartamento", JOptionPane.QUESTION_MESSAGE);

    		if (idAlojamiento != null && sesionEnCurso)
    		{
        		VOApartamento tb = parranderos.adicionarApartamento (Long.parseLong(idAlojamiento));
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un apartameno con el id: " + idAlojamiento);
        		}
        		String resultado = "En adicionarApartamento\n\n";
        		resultado += "Apartamento adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }


	/* ****************************************************************
	 * 			CRUD de HabitacionHotel
	 *****************************************************************/
    /**
     * Adiciona una HabitacionHotel con la información dada por el usuario
     * Se crea una nueva tupla de HabitacionHotel en la base de datos, si una HabitacionHotel con ese nombre no existía
     */

	 public void adicionarHabitacionHotel( )
	 {
		try 
    	{
    		String idAlojamiento = JOptionPane.showInputDialog (this, "Id de la habitacion de hotel (debe ser de un alojamiento ya existente)?", "Adicionar nueva habitacion de hotel", JOptionPane.QUESTION_MESSAGE);
			String tipoHabitacion = JOptionPane.showInputDialog (this, "Tipo de habitacion ('Estandar', 'Semisuites' o 'Suites')?", "Adicionar nueva habitacion de hotel", JOptionPane.QUESTION_MESSAGE);

    		if (idAlojamiento != null && tipoHabitacion != null && sesionEnCurso)
    		{
        		VOHabitacionHotel tb = parranderos.adicionarHabitacionHotel (Long.parseLong(idAlojamiento), tipoHabitacion);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una habitacion de hotel con el id: " + idAlojamiento);
        		}
        		String resultado = "En adicionarHabitacionHotel\n\n";
        		resultado += "HabitacionHotel adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }


	/* ****************************************************************
	 * 			CRUD de Hostal
	 *****************************************************************/
    /**
     * Adiciona un Hostal con la información dada por el usuario
     * Se crea una nueva tupla de Hostal en la base de datos, si un Hostal con ese nombre no existía
     */

	 public void adicionarHostal( )
	 {
		try 
    	{
    		String idAlojamiento = JOptionPane.showInputDialog (this, "Id del hostal (debe ser de un alojamiento ya existente)?", "Adicionar nuevo hostal", JOptionPane.QUESTION_MESSAGE);
			String horarioApertura = JOptionPane.showInputDialog (this, "Hora de apertura en formato 24H (HH:MM)?", "Adicionar nuevo hostal", JOptionPane.QUESTION_MESSAGE);
			String horarioCierre = JOptionPane.showInputDialog (this, "Hora de cierre en formato 24H (HH:MM)?", "Adicionar nuevo hostal", JOptionPane.QUESTION_MESSAGE);

    		if (idAlojamiento != null && horarioApertura != null && horarioCierre != null && sesionEnCurso)
    		{
        		VOHostal tb = parranderos.adicionarHostal (Long.parseLong(idAlojamiento), horarioApertura, horarioCierre );
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un hostal con el id: " + idAlojamiento);
        		}
        		String resultado = "En adicionarHostal\n\n";
        		resultado += "Hostal adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }


	/* ****************************************************************
	 * 			CRUD de ViviendaTemporal
	 *****************************************************************/
    /**
     * Adiciona una ViviendaTemporal con la información dada por el usuario
     * Se crea una nueva tupla de ViviendaTemporal en la base de datos, si una ViviendaTemporal con ese nombre no existía
     */

	 public void adicionaViviendaTemporal( )
	 {
		try 
    	{
    		String idAlojamiento = JOptionPane.showInputDialog (this, "Id de la vivienda temporal (debe ser de un alojamiento ya existente)?", "Adicionar nueva vivienda temporal", JOptionPane.QUESTION_MESSAGE);
			String numHabitaciones = JOptionPane.showInputDialog (this, "Numero de habitaciones (Numero natural)?", "Adicionar nueva vivienda temporal", JOptionPane.QUESTION_MESSAGE);

    		if (idAlojamiento != null && numHabitaciones != null && sesionEnCurso)
    		{
        		VOViviendaTemporal tb = parranderos.adicionarViviendaTemporal (Long.parseLong(idAlojamiento), Integer.parseInt(numHabitaciones) );
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una vivienda temporal con el id: " + idAlojamiento);
        		}
        		String resultado = "En adicionarViviendaTemporal\n\n";
        		resultado += "ViviendaTemporal adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }


	 /* ****************************************************************
	 * 			CRUD de Cliente
	 *****************************************************************/
    /**
     * Adiciona una Cliente con la información dada por el usuario
     * Se crea una nueva tupla de Cliente en la base de datos, si una Cliente con ese nombre no existía
     */

	 public void adicionarCliente( )
	 {
		try 
    	{
			String idCliente = JOptionPane.showInputDialog (this, "ID del cliente?", "Adicionar nuevo cliente", JOptionPane.QUESTION_MESSAGE);
			String tipoIdentificacion = JOptionPane.showInputDialog (this, "Tipo de identificación?", "Adicionar nuevo cliente", JOptionPane.QUESTION_MESSAGE);
			String nombreCliente = JOptionPane.showInputDialog (this, "Nombre Cliente?", "Adicionar nuevo cliente", JOptionPane.QUESTION_MESSAGE);
			String fechaNacimiento = JOptionPane.showInputDialog (this, "Fecha de nacimiento del cliente ('aaaa-mm-dd')?", "Adicionar nuevo cliente", JOptionPane.QUESTION_MESSAGE);
			

    		if (idCliente != null && tipoIdentificacion != null && nombreCliente != null && fechaNacimiento != null && sesionEnCurso)
    		{
        		VOCliente tb = parranderos.adicionarCliente (Long.parseLong(idCliente),tipoIdentificacion,nombreCliente,java.sql.Date.valueOf(fechaNacimiento));
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear el cliente ");
        		}
        		String resultado = "En adicionarCliente\n\n";
        		resultado += "Cliente adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }


	 /* ****************************************************************
	 * 			CRUD de MiembroActivo
	 *****************************************************************/
    /**
     * Adiciona un MiembroActivo con la información dada por el usuario
     * Se crea una nueva tupla de MiembroActivo en la base de datos, si un MiembroActivo con ese nombre no existía
     */

	 public void adicionarMiembroActivo( )
	 {
		try 
    	{
			String idMiembroActivo = JOptionPane.showInputDialog (this, "Número de identificación del cliente (ya existente)?", "Adicionar nuevo MiembroActivo", JOptionPane.QUESTION_MESSAGE);
			String carnet = JOptionPane.showInputDialog (this, "Número de carnet?", "Adicionar nuevo MiembroActivo", JOptionPane.QUESTION_MESSAGE);
			String tipo = JOptionPane.showInputDialog (this, "Tipo de MiembroActivo?", "Adicionar nuevo MiembroActivo", JOptionPane.QUESTION_MESSAGE);
			

    		if (idMiembroActivo != null && carnet != null && tipo != null)
    		{
        		VOMiembroActivo tb = parranderos.adicionarMiembroActivo (Long.parseLong(idMiembroActivo),carnet,tipo);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear el MiembroActivo");
        		}
        		String resultado = "En adicionarMiembroActivo\n\n";
        		resultado += "MiembroActivo adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }


	/* ****************************************************************
	 * 			CRUD de MiembroSecundario
	 *****************************************************************/
    /**
     * Adiciona un MiembroSecundario con la información dada por el usuario
     * Se crea una nueva tupla de MiembroSecundario en la base de datos, si un MiembroSecundario con ese nombre no existía
     */

	 public void adicionarMiembroSecundario( )
	 {
		try 
    	{
			String idMiembroSecundario = JOptionPane.showInputDialog (this, "Número de identificación del cliente (ya existente)?", "Adicionar nuevo MiembroSecundario", JOptionPane.QUESTION_MESSAGE);
			String tipo = JOptionPane.showInputDialog (this, "Tipo de MiembroSecundario?", "Adicionar nuevo MiembroSecundario", JOptionPane.QUESTION_MESSAGE);
			

    		if (idMiembroSecundario != null && tipo!= null && sesionEnCurso)
			{
        		VOMiembroSecundario tb = parranderos.adicionarMiembroSecundario (Long.parseLong(idMiembroSecundario),tipo);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear el MiembroSecundario");
        		}
        		String resultado = "En adicionarMiembroSecundario\n\n";
        		resultado += "MiembroSecundario adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }


	/* ****************************************************************
	 * 			CRUD de Servicio
	 *****************************************************************/
    /**
     * Adiciona un Servicio con la información dada por el usuario
     * Se crea una nueva tupla de Servicio en la base de datos, si un Servicio con ese nombre no existía
     */

	 public void adicionarServicio( )
	 {
		try 
    	{
			String nombre = JOptionPane.showInputDialog (this, "Nombre del servicio?", "Adicionar nuevo servicio", JOptionPane.QUESTION_MESSAGE);
			String costo = JOptionPane.showInputDialog (this, "Costo (Numero natural)?", "Adicionar nuevo servicio", JOptionPane.QUESTION_MESSAGE);

    		if (nombre != null && costo != null && sesionEnCurso)
    		{
        		VOServicio tb = parranderos.adicionarServicio (nombre, Integer.parseInt(costo) );
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un servicio con el nombre: " + nombre);
        		}
        		String resultado = "En adicionarServicio\n\n";
        		resultado += "Servicio adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }


	/* ****************************************************************
	 * 			CRUD de Dispone
	 *****************************************************************/
    /**
     * Adiciona Dispone con la información dada por el usuario
     * Se crea una nueva tupla de Dispone en la base de datos, si Dispone con ese nombre no existía
     */

	 public void adicionaDispone( )
	 {
		try 
    	{
			String idServicio = JOptionPane.showInputDialog (this, "Id del servicio (ya existente)?", "Adicionar nueva relacion de dispone", JOptionPane.QUESTION_MESSAGE);
    		String idAlojamiento = JOptionPane.showInputDialog (this, "Id del alojamiento (ya existente)?", "Adicionar nueva nueva relacion de dispone", JOptionPane.QUESTION_MESSAGE);

    		if (idAlojamiento != null && idServicio != null && sesionEnCurso)
    		{
        		VODispone tb = parranderos.adicionarDispone (Long.parseLong(idServicio), Long.parseLong(idAlojamiento) );
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una nueva relacion Dispone con el id de servicio " + idServicio + " y el id de alojamiento " + idAlojamiento);
        		}
        		String resultado = "En Dispone\n\n";
        		resultado += "Relacion Dispone adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }


	/* ****************************************************************
	 * 			CRUD de Reserva
	 *****************************************************************/
    /**
     * Adiciona una Reserva con la información dada por el usuario
     * Se crea una nueva tupla de Reserva en la base de datos, si una Reserva con ese nombre no existía
     */

	 public void adicionarReserva( )
	 {
		try 
    	{
			String idAlojamiento = JOptionPane.showInputDialog (this, "ID del alojamiento (ya existente)?", "Adicionar nueva reserva", JOptionPane.QUESTION_MESSAGE);
			String idCliente = JOptionPane.showInputDialog (this, "ID del cliente (ya existente)?", "Adicionar nueva reserva", JOptionPane.QUESTION_MESSAGE);
			String duracion = JOptionPane.showInputDialog (this, "Duración de la reserva en días (número natural)?", "Adicionar nueva reserva", JOptionPane.QUESTION_MESSAGE);
			String fechaInicio = JOptionPane.showInputDialog (this, "Fecha de inicio de la reserva ('aaaa-mm-dd')?", "Adicionar nueva reserva", JOptionPane.QUESTION_MESSAGE);
			String fechaFinal = JOptionPane.showInputDialog (this, "Fecha final de la reserva ('aaaa-mm-dd')?", "Adicionar nueva reserva", JOptionPane.QUESTION_MESSAGE);
			String costoTotal = JOptionPane.showInputDialog (this, "Costo total de la reserva (número)?", "Adicionar nueva reserva", JOptionPane.QUESTION_MESSAGE);
			String estado = "Activa";
    		String numPersonas = JOptionPane.showInputDialog (this, "Número de personas en la reserva (número natural)?", "Adicionar nueva reserva", JOptionPane.QUESTION_MESSAGE);

			if (idAlojamiento != null && idCliente != null && duracion != null && fechaInicio != null && fechaFinal != null&& costoTotal != null&& estado != null&& numPersonas != null && sesionEnCurso)
    		{	long centinela=parranderos.revisarReserva(Long.parseLong(idCliente), java.sql.Date.valueOf(fechaInicio),java.sql.Date.valueOf(fechaFinal));
				if (centinela == 0){
        		VOReserva tb = parranderos.adicionarReserva (Long.parseLong(idAlojamiento),Long.parseLong(idCliente),Integer.parseInt(duracion),java.sql.Date.valueOf(fechaInicio),java.sql.Date.valueOf(fechaFinal),Long.parseLong(costoTotal), estado,Integer.parseInt(numPersonas));
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear el reserva ");
        		}
        		String resultado = "En adicionarReserva\n\n";
        		resultado += "Reserva adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    			}
				else{
				panelDatos.actualizarInterfaz("Operación No valida, el cliente ya tiene una reserva en estas fechas");
			    }
			}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		}
		 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		} 
	 }

	 public void cancelarReserva( )
	 {
		try 
    	{
			String idReserva = JOptionPane.showInputDialog (this, "ID de la Reserva (ya existente)?", "Cancelar reserva", JOptionPane.QUESTION_MESSAGE);

			if (idReserva != null && sesionEnCurso)
    		{
        		long tb = parranderos.cancelarReservaPorId(Long.parseLong(idReserva));
        		if (tb == -1)
        		{
        			throw new Exception ("No se pudo cancelar la reserva");
        		}
        		String resultado = "En cancelarReserva\n\n";
        		resultado += "Reserva cancelada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		} 
	 }

	 // RF8 - CANCELAR RESERVA COLECTIVA
	 public void cancelarReservaColectivaPorId( )
	 {
		try 
    	{
			String idReservaColectiva = JOptionPane.showInputDialog (this, "ID de la Reserva colectiva (ya existente)?", "Cancelar reserva colectiva", JOptionPane.QUESTION_MESSAGE);

			if (idReservaColectiva != null && sesionEnCurso)
    		{
        		long tb = parranderos.cancelarReservaColectivaPorId(Long.parseLong(idReservaColectiva));
        		if (tb == -1)
        		{
        			throw new Exception ("No se pudo cancelar la reserva colectiva");
        		}
        		String resultado = "En cancelarReservaColectiva\n\n";
        		resultado += "Reserva colectiva cancelada exitosamente. Numero de reservas individuales canceladas: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
			else if (!sesionEnCurso){
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		} 
	 }

	 // RFC6 - MOSTRAR EL USO DE ALOHANDES PARA UN USUARIO DADO
	 /**
     * Consulta en la base de datos el numero de reservas, el dinero pagado, y las noches reservas por un cliente
     */
    public void mostrarUsoPorUsuario()
    {
    	try 
    	{
			String idCliente = JOptionPane.showInputDialog (this, "ID del cliente (ya existente)?", "Mostrar uso de AlohaAndes a un cliente", JOptionPane.QUESTION_MESSAGE);

			List <Object[]> lista = parranderos.mostrarUsoPorUsuario(Long.parseLong(idCliente));

			if (idCliente != null && sesionEnCurso){
			String resultado = "Listando las principales caracteristicas de las reservas a nombre de ese cliente";
			resultado +=  "\n" + listarmostrarUsoPorUsuario(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
			}
			else{
				String resultado = "No cuenta con los permisos necesarios para ejecutar esta operacion\n\n";
        		resultado += "Es necesario que inicie sesion con un cuenta que si cuente con los permisos necesarios: " ;
    			panelDatos.actualizarInterfaz(resultado);
			}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	/**
     * Genera una cadena de caracteres con la lista de las principales caracteristicas de las resrvas asociadas a un cliente
     * @param lista - La lista con las carcteristicas de las resrvas de un cliente
     * @return La cadena con una línea para cada cliente recibido
     */
    private String listarmostrarUsoPorUsuario(List<Object[]> lista) 
    {
    	String resp = "La lista de las principales caracteristicas de las reservas asociadas al cliente son:\n";
    	int i = 1;
        for (Object [] aloj : lista)
        {
			long idClienteActual =(long) aloj[0];
			long numeroReservas =(long) aloj[1];
			long numeroNochesReservadas =(long) aloj[2];
			long dineroPagado =(long) aloj[3];
        	resp += i++ + ". "  + "[";
			resp += "ID del Cliente: "+idClienteActual;
			resp += ", Numero de reservas a su nombre: "+numeroReservas;
			resp += ", Numero de noches reservadas: "+numeroNochesReservadas;
			resp += ", Dinero pagado: "+dineroPagado+" COP]"+"\n";
        }
		return resp;
	} 

	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("parranderos.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogParranderos ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
    		// Ejecución de la demo y recolección de los resultados
			long eliminados [] = parranderos.limpiarParranderos();
			
			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Reservas eliminadas\n";
			resultado += eliminados [1] + " Tuplas de Dispone eliminadas\n";
			resultado += eliminados [2] + " Propietarios eliminados\n";
			resultado += eliminados [3] + " Empresas eliminadas\n";
			resultado += eliminados [4] + " ViviendasUniversitarias eliminadas\n";
			resultado += eliminados [5] + " HabitacionesViviendas eliminadas\n";
			resultado += eliminados [6] + " Apartamentos eliminados\n";
			resultado += eliminados [7] + " HabitacionesHoteles eliminadas\n";
			resultado += eliminados [8] + " Hostales eliminadas\n";
			resultado += eliminados [9] + " ViviendasTemporales eliminadas\n";
			resultado += eliminados [10] + " MiembrosActivos eliminados\n";
			resultado += eliminados [11] + " MiembroSecundarios eliminados\n";
			resultado += eliminados [12] + " Clientes eliminados\n";
			resultado += eliminados [13] + " Alojamientos eliminados\n";
			resultado += eliminados [14] + " Operadores eliminados\n";
			resultado += eliminados [15] + " Servicios eliminados\n";
			resultado += "\nLimpieza terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	/**
	 * Muestra el modelo conceptual de Alohaandes
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("Docs/Modelo conceptual.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de Alohaandes
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("Docs/Modelo relacional.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("Docs/EsquemaAlohaAndes.sql");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("Docs/index.html");  // Pendiente
	}
	
	/**
     * Muestra la información acerca del desarrollo de esta aplicación
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: AlohaAndes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Juan Sebastian Sanchez, Santiago Vargas\n";
		resultado += " * Marzo de 2023\n";
		resultado += " * \n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
    }
    

	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
    
    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}
 
	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazParranderosApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazParranderosApp interfaz = new InterfazParranderosApp( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
