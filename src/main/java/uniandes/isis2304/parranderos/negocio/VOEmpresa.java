package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de Operador.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Juan Sebastian Sanchez
 */

public interface VOEmpresa {
    /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id de la empresa
      */
      public long getIdOperador();
     
      /**
       * @return el NIT de la empresa
       */
      public String getNit();
      
      /**
       * @return el nombre de la empresa
       */
      public String getNombreEmpresa();
  
      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos de la empresa
       */
      public String toString();
}
