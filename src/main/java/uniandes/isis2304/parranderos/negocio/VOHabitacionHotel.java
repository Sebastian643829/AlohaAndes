package uniandes.isis2304.parranderos.negocio;

public interface VOHabitacionHotel {
    /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id del hotel
      */
      public long getIdAlojamiento();
      
      /**
       * @return El tipo de habitacion del hotel
       */
      public String getTipoHabitacion();
  
      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos del hotel
       */
      public String toString();
}
