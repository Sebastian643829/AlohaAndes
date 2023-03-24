
package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de Operador.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Juan Sebastian Sanchez
 */

public interface VOOperador {
  /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id del operador
      */
      public long getIdOperador();
     
      /**
       * @return el telefono de operador
       */
      public String getTelefono();
      
      /**
       * @return el tipo de vinculacion de operador
       */
      public String getTipoVinculacion();
  
      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos del operador
       */
      public String toString();

} 