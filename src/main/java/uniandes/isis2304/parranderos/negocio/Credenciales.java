package uniandes.isis2304.parranderos.negocio;

import java.util.HashMap;
import java.util.Map;

public class Credenciales {
    
    // Atributos
    private Map<String, String> credenciales = new HashMap<String, String>();

    //
    public Credenciales ()
	{
		credenciales.put("js.sanchezd1","12345");
        credenciales.put("ds.vargasp1","12345");
	}
    
    // Metodo GET
	public Map<String, String> getCredenciales() 
	{
		return credenciales;
	}
}
