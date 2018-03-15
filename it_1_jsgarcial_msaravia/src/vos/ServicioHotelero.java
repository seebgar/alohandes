package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Representa un servicio Hotelero
 * @author sebastian
 *
 */
public class ServicioHotelero {
	
	/**
	 * Constantes de Servicio
	 * @author sebastian
	 *
	 */
//	@JsonProperty(value="tipo")
	public enum TIPO {	
		Jacuzzi,
		SalaIncluida, 
		Spa,
		GYM, 
		Restaurante,
		SalaEstudia,
		Aseo,
		WIFI,
		TV		
	}
		
	

}
