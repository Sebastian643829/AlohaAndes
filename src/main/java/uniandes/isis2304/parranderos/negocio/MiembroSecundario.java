package uniandes.isis2304.parranderos.negocio;

public class MiembroSecundario implements VOMiembroSecundario {
    /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO del miembro secundario
	 */
	private long idMiembroSecundario; 

	/**
	 * El tipo del miembro secundario
	 */
	private String tipo;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public MiembroSecundario() 
    {
    	this.idMiembroSecundario = 0;
		this.tipo = "";
	}

	/**
	 * Constructor con valores
	 * @param idMiembroSecundario - El id del miembro secundario
	 * @param tipo - El tipo del miembro secundario
	 */
    public MiembroSecundario(long idMiembroSecundario, String tipo){
    	this.idMiembroSecundario = idMiembroSecundario;
		this.tipo = tipo;
	}

    /**
	 * @return El id del miembro secundario
	 */
	public long getIdMiembroSecundario() 
	{
		return idMiembroSecundario;
	}
	
	/**
	 * @param idMiembroSecundario - El nuevo id del miembro secundario
	 */
	public void setIdMiembroSecundario(long idMiembroSecundario) 
	{
		this.idMiembroSecundario = idMiembroSecundario;
	}
	
	/**
	 * @return El tipo del miembro secundario
	 */
	public String getTipo() 
	{
		return tipo;
	}
	
	/**
	 * @param tipo - El tipo del miembro secundario
	 */
	public void setTipo(String tipo) 
	{
		this.tipo = tipo;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del miembro activo
	 */
	public String toString() 
	{
		return "MiembroSecundario  [idMiembroSecundario=" + idMiembroSecundario + ", tipo=" + tipo + "]";
	}
}
