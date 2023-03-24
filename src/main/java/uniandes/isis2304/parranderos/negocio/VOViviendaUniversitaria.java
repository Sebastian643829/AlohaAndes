package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de Operador.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Juan Sebastian Sanchez
 */

public interface VOViviendaUniversitaria {
    /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id de la vivienda universitaria
      */
      public long getIdAlojamiento();
     
      /**
       * @return el tipo de habitacion de la vivienda universitaria
       */
      public String getTipoHabitacion();
  
      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos de la vivienda universitaria
       */
      public String toString();
}
