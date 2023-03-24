package uniandes.isis2304.parranderos.negocio;

public interface VODispone {
    /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id del servicio
      */
      public long getIdServicio();
     
      /**
       * @return El id del alojamiento
       */
      public long getIdAlojamiento();

      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos de la tabla de dispone
       */
      public String toString();
}
