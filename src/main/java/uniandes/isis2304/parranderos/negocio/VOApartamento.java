package uniandes.isis2304.parranderos.negocio;

public interface VOApartamento {
     /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id del apartamento
      */
      public long getIdAlojamiento();

      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos del apartamento
       */
      public String toString();
}
