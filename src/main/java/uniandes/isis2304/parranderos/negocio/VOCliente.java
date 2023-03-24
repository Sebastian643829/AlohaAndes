package uniandes.isis2304.parranderos.negocio;
import java.sql.Timestamp;

public interface VOCliente {
    /* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
      * @return El id del cliente
      */
      public long getIdCliente();
     
      /**
       * @return El tipo de identificacion del cliente
       */
      public String getTipoIdentificacion();
      
      /**
       * @return El nombre del cliente
       */
      public String getNombreCliente();
  
        /**
       * @return La fecha de nacimiento del cliente
       */
      public Timestamp getFechaNacimiento();

      @Override
      /**
       * @return Una cadena de caracteres con todos los atributos del cliente
       */
      public String toString();
}
