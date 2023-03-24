package uniandes.isis2304.parranderos.negocio;

public interface VOHostal {
    /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id de la habitacion del hostal
      */
      public long getIdAlojamiento();
     
      /**
       * @return El horario de apertura del hostal
       */
      public String getHorarioApertura();
      
      /**
       * @return El horario del cierre del hostal
       */
      public String getHorarioCierre();
  
      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos del hostal
       */
      public String toString();
}
