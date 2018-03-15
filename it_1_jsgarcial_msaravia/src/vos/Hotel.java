package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa un Hotel, de tipo inmueble hotelero
 * @author sebastian
 *
 */
public class Hotel extends InmuebleHotelero {

	/**
	 * Dtermina si el hotel tiene un horario de administracion las 24 horas
	 */
	@JsonProperty(value="horario_admin_24h")
	private Boolean horario_admin_24h;
	
	public Hotel(
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="registro_camara_comercio") String registro_camara_comercio,
			@JsonProperty(value="registro_superintendencia") String registro_superintendencia,
			@JsonProperty(value="tipo_habitacion") String tipo_habitacion,
			@JsonProperty(value="ubicacion") String ubicacion,
			@JsonProperty(value="horario_admin_24h") Boolean horario_admin_24h ) {
		super(id, registro_camara_comercio, registro_superintendencia, tipo_habitacion, ubicacion);
		this.horario_admin_24h = horario_admin_24h;
	}

	public Boolean getHorario_admin_24h() {
		return horario_admin_24h;
	}

	public void setHorario_admin_24h(Boolean horario_admin_24h) {
		this.horario_admin_24h = horario_admin_24h;
	}
	
	
}
