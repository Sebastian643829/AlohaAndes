package uniandes.isis2304.parranderos.negocio;

public interface VOMiembroActivo {
    /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id del miembro activo
      */
      public long getIdMiembroActivo();
     
      /**
       * @return El carnet del miembro activo
       */
      public String getCarnet();

      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos del miembro activo
       */
      public String toString();
}
