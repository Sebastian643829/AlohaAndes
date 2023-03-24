package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de Propietario.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Juan Sebastian Sanchez
 */

public interface VOPropietario {
  /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id del propietario
      */
      public long getIdOperador(); 
     
      /**
       * @return la identificacion del propietario
       */
      public String getIdentificacion();
      
      /**
       * @return el tipo de identificacion del propietario
       */
      public String getTipoIdentificacion();

       /**
       * @return el nombre del propietario
       */
      public String getNombrePropietario();
  
      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos del propietario
       */
      public String toString(); 

} 