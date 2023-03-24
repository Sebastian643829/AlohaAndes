package uniandes.isis2304.parranderos.negocio;

public interface VOMiembroSecundario {
     /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id del miembro secundario
      */
      public long getIdMiembroSecundario();
     
      /**
       * @return El tipo del miembro secundario
       */
      public String getTipo();

      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos del miembro secundario
       */
      public String toString();
}
