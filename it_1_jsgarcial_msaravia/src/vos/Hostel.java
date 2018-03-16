package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa un Hostal pero en SQL lo escribi mal entonces quedo mo Hostel
 * @author sebastian
 *
 */
public class Hostel extends InmuebleHotelero {

	
	/**
	 * Hora (0 - 24) de apertura de la administracion del hostal
	 */
	@JsonProperty(value="horario_admin_inicial")
	private Integer horario_admin_inicial;
	
	/**
	 * Hora (0 -24) de cierre de la administracion del hostal
	 */
	@JsonProperty(value="horario_admin_final")
	private Integer horario_admin_final;
	
	/**
	 * servicios de hotel que el hostel ofrece
	 */
	@JsonProperty(value="servicios_hoteleros")
	private List<ServicioHotelero> serviciosHoteleros;
	
	//constructor
	public Hostel(
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="registro_camara_comercio") String registro_camara_comercio,
			@JsonProperty(value="registro_superintendencia") String registro_superintendencia,
			@JsonProperty(value="tipo_habitacion") String tipo_habitacion,
			@JsonProperty(value="ubicacion") String ubicacion,
			@JsonProperty(value="horario_admin_inicial")Integer horario_admin_inicial,
			@JsonProperty(value="horario_admin_final")Integer horario_admin_final ) {
		super(id, registro_camara_comercio, registro_superintendencia, tipo_habitacion, ubicacion);
		this.horario_admin_inicial = horario_admin_inicial;
		this.horario_admin_final = horario_admin_final;
	}

	/**
	 * Hora (0 - 24)
	 * @return
	 */
	public Integer getHorario_admin_inicial() {
		return horario_admin_inicial;
	}

	/**
	 * Hora (0 - 24)
	 * @param horario_admin_inicial
	 */
	public void setHorario_admin_inicial(Integer horario_admin_inicial) {
		this.horario_admin_inicial = horario_admin_inicial;
	}

	/**
	 * Hora (0 - 24)
	 * @return
	 */
	public Integer getHorario_admin_final() {
		return horario_admin_final;
	}

	/**
	 * Hora (0 - 24)
	 * @param horario_admin_final
	 */
	public void setHorario_admin_final(Integer horario_admin_final) {
		this.horario_admin_final = horario_admin_final;
	}

	/**
	 * @return the serviciosHoteleros
	 */
	public List<ServicioHotelero> getServiciosHoteleros() {
		return serviciosHoteleros;
	}

	/**
	 * @param serviciosHoteleros the serviciosHoteleros to set
	 */
	public void setServiciosHoteleros(List<ServicioHotelero> serviciosHoteleros) {
		this.serviciosHoteleros = serviciosHoteleros;
	}
	
	
	
	
	
}
