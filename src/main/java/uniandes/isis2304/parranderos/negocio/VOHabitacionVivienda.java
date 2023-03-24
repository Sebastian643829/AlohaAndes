package uniandes.isis2304.parranderos.negocio;

public interface VOHabitacionVivienda {
    /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id de la habitacion de vivienda
      */
      public long getIdAlojamiento();
     
      /**
       * @return El tipo de baño de la habitacion de vivienda
       */
      public String getTipoBano();
      
      /**
       * @return El tipo de habitacion de la habitacion de vivienda
       */
      public String getTipoHabitacion();
  
      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos de la habitacion vivienda
       */
      public String toString();
}
