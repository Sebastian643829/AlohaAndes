package uniandes.isis2304.parranderos.negocio;

public interface VOViviendaTemporal {
    /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id de la vivienda temporal
      */
      public long getIdAlojamiento();
     
      /**
       * @return El numero de habitaciones de la vivienda temporal
       */
      public int getNumHabitaciones();
  
      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos de la vivienda temporal
       */
      public String toString();
}
