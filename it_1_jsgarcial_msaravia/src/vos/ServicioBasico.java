package vos;


/**
 * Clase que representa los servicios basicos que un
 * inmueble debe o puede ofrecer al cliente
 * @author sebastian
 *
 */
public class ServicioBasico {

	/**
	 * Constantes de Servicio con un Costo
	 * @author sebastian
	 *
	 */
//	@JsonProperty(value="tipo")
	// TODO
	public enum TIPO {	
		Luz,
		Telefono, 
		Agua,
		TV, 
		Internet,
		Comida,
		Cocina,
		Bano,
		GYM,
		ApoyoSocial,
		ApoyoAcademico;
		
		/**
		 * Costo Adicional de cada Servicio
		 */
		private Double costo;
		public Double getCosto() {
			return this.costo;
		}
		public void setCosto(Double costo) {
			this.costo = costo;
		}
	}

		

}
