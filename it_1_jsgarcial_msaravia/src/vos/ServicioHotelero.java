package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Representa un servicio Hotelero
 * @author sebastian
 *
 */
public class ServicioHotelero {
	
	//----------------------------------------------------------------------------------------------------------------------------------
		// ATRIBUTOS
		//----------------------------------------------------------------------------------------------------------------------------------

		
		/**
		 * Nombre del servicio basico = 
		 * {jacuzzi, spa, gym , restaurante, sala}
		 */
		@JsonProperty(value="nombre")
		private String nombre;
		
		
		//----------------------------------------------------------------------------------------------------------------------------------
		// Metodo Constructor
		//----------------------------------------------------------------------------------------------------------------------------------

		
		/**
		 * Constructor de un Servicio Hotelero
		 * @param nombre
		 * @param costo
		 */
		public ServicioHotelero( @JsonProperty(value="nombre") String nombre ) {
			this.nombre = nombre;
		}


		
		//----------------------------------------------------------------------------------------------------------------------------------
		// Metodos de la clase
		//----------------------------------------------------------------------------------------------------------------------------------

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		
	

}
