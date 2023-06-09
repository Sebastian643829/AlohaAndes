package uniandes.isis2304.parranderos.negocio;

public interface VOAlojamiento {
    /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id del alojamiento
      */
      public long getIdAlojamiento();
     
      /**
       * @return el nombre del alojamiento
       */
      public String getNombre();
      
      /**
       * @return la capacidad del alojamiento
       */
      public int getCapacidad();

      /**
       * @return la ubicacion del alojamiento
       */
      public String getUbicacion();

      /**
       * @return el tamaño del alojamiento
       */
      public int getTamano();

      /**
       * @return el precio de la noche del alojamiento
       */
      public int getPrecioNoche();

      /**
       * @return la ocupacion total actual del alojamiento
       */
      public int getOcupacionActual();

      /**
       * @return el numero de reservas del alojamiento
       */
      public int getNumReservas();

      /**
      * @return El id del operador
      */
      public long getIdOperador();

      /**
      * @return El estado del alojamiento
      */
      public String getEstado();

      /**
      * @return El estado del tipo de alojamiento
      */
      public String getTipo();
  
      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos del operador
       */
      public String toString();
}
