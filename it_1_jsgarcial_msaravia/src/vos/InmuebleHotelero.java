package vos;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa un inmueble hotelero: hoteles y hosteles
 * @author sebastian
 *
 */
public class InmuebleHotelero {


	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id del inmueble
	 */
	@JsonProperty(value="id")
	private Long id;

	/**
	 * Registro ante la camara de comercio. No puede ser NULL
	 */
	@JsonProperty(value="registro_camara_comercio")
	private String registro_camara_comercio;

	/**
	 * Registro ante la superintendencia de turismo. No puede ser NULL
	 */
	@JsonProperty(value="registro_superintendencia")
	private String registro_superintendencia;

	/**
	 * Tipo de la habitacion
	 * Pertenece a estandar, semisuite, suite, sencilla, compartida
	 */
	@JsonProperty(value="tipo_habitacion")
	private String tipo_habitacion;

	/**
	 * Ubicacion del inmueble. Es una direccion.
	 */
	@JsonProperty(value="ubicacion")
	private String ubicacion;

	/**
	 * propuesta del immueble
	 */
	@JsonProperty(value="propuesta")
	private Propuesta propuesta;
	
	/**
	 * Servicios hoteleros ofrecido por el inmuble de tipo hotelero
	 */
	@JsonProperty(value="servicios_hoteleros")
	private List<ServicioHotelero> servicios;
	




	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @param id
	 * @param registro_camara_comercio
	 * @param registro_superintendencia
	 * @param tipo_habitacion
	 * @param ubicacion
	 */
	public InmuebleHotelero(
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="registro_camara_comercio") String registro_camara_comercio,
			@JsonProperty(value="registro_superintendencia") String registro_superintendencia,
			@JsonProperty(value="tipo_habitacion") String tipo_habitacion,
			@JsonProperty(value="ubicacion") String ubicacion ) {
		this.id = id;
		this.registro_camara_comercio = registro_camara_comercio;
		this.registro_superintendencia = registro_superintendencia;
		this.tipo_habitacion = tipo_habitacion;
		this.ubicacion = ubicacion;
		// TODO inicializar propuesta
		this.servicios = new ArrayList<>();
	}



	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegistro_camara_comercio() {
		return registro_camara_comercio;
	}

	public void setRegistro_camara_comercio(String registro_camara_comercio) {
		this.registro_camara_comercio = registro_camara_comercio;
	}

	public String getRegistro_superintendencia() {
		return registro_superintendencia;
	}

	public void setRegistro_superintendencia(String registro_superintendencia) {
		this.registro_superintendencia = registro_superintendencia;
	}

	/**
	 * Pertenece a estandar, semisuite, suite, sencilla, compartida
	 * @return
	 */
	public String getTipo_habitacion() {
		return tipo_habitacion;
	}

	/**
	 * Pertenece a estandar, semisuite, suite, sencilla, compartida
	 * @param tipo_habitacion
	 */
	public void setTipo_habitacion(String tipo_habitacion) {
		this.tipo_habitacion = tipo_habitacion;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * @return the propuesta
	 */
	public Propuesta getPropuesta() {
		return propuesta;
	}

	/**
	 * @param propuesta the propuesta to set
	 */
	public void setPropuesta(Propuesta propuesta) {
		this.propuesta = propuesta;
	}

	public List<ServicioHotelero> getServicios() {
		return servicios;
	}

	public void setServicios(List<ServicioHotelero> servicios) {
		this.servicios = servicios;
	}
	
	public void add_Servicio_Hotelero ( ServicioHotelero serv) {
		this.servicios.add(serv);
	}
	


}
