package uniandes.isis2304.parranderos.negocio;

public interface VOServicio {
    /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id del servicio
      */
      public long getIdServicio();
     
      /**
       * @return El nombre del servicio
       */
      public String getNombre();

      /**
       * @return El costo del servicio
       */
      public int getCosto();

      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos del servicio
       */
      public String toString();
}
