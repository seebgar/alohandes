package vos;

import java.util.List;
import org.codehaus.jackson.annotate.*;


/**
 * Clase que representa una clase Colectiva
 * @author sebastian
 *
 */
public class Colectivo {

	
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id 
	 */
	@JsonProperty(value="id")
	private Long id; 

	/**
	 * Representa una lista de los ids de los usuarios involucrados en una
	 * Reserva Colectiva
	 */
	@JsonProperty(value="usuarios")
	private List<UsuarioEnColectivo> usuarios;
	
	/**
	 * Representa la duracion de cada reserva que se realizara de forma colectiva
	 */
	@JsonProperty(value="duracion")
	private Integer duracion;
	
	/**
	 * Representa la fecha de inicio de estadia / 
	 * fecha de inicio de utilizacion del inmueble
	 */
	@JsonProperty(value="fecha_inicio_estadia")
	private String fecha_inicio_estadia;
	
	/**
	 * Representa el tipo de inmueble del cual se quiere hacer una reserva 
	 * < HOTEL | HOSTEL | VIVIENDA EXPRESS | APARTAMENTO | VIVIENDA UNIVERSITARIA | HABITACION >
	 */
	@JsonProperty(value="tipo_inmueble")
	private String tipo_inmueble;
	
	/**
	 * Representa la privacidad del inmueble deseado
	 * < COMPARTIDA | SENCILLA >  ; PERO si es HOTEL = < SEMISUITE | SUITE | ESTANDAR >
	 */
	@JsonProperty(value="privacidad")
	private String privacidad;
	
	/**
	 * Cantidad de inmuebles que se dean reservar
	 */
	@JsonProperty(value="cantidad_inmuebles")
	private Integer cantidad_inmuebles;
	
	/**
	 *  Lista de String que representan los servicios que se desean ::
	 * { para Hotel/Hostel = < SALA_ESTUDIO | GYM | RESTAURANTE | JACUZZI > } 
	 * { para inmuebles = < LUZ | TV | AGUA | INTERNET | COMIDA | BAÑO | APOYOSOCIAL | APOYOACADEMICO > } 
	 */
	@JsonProperty(value="servicios_deseados")
	private List<String> servicios_deseados;
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase Colectivo
	 * <b>pre: </b> Existen los usuarios cuyos IDS estan involucrados en la reserva colevtiva
	 * <b>post: </b> Crea reservas para cada usuario con un id de reserva colevtiva en comun
	 * @param id - Id de la reserva colectiva
	 * @param ids_usuarios - IDs de los usuarios de la reserva colectiva
	 */
	public Colectivo (  
			@JsonProperty(value="id")Long id, 
			@JsonProperty(value="usuarios")List<UsuarioEnColectivo> usuarios,
			@JsonProperty(value="duracion")Integer duracion,
			@JsonProperty(value="fecha_inicio_estadia")String fecha_inicio_estadia,
			@JsonProperty(value="tipo_inmueble")String tipo_inmueble,
			@JsonProperty(value="privacidad")String privacidad,
			@JsonProperty(value="cantidad_inmuebles")Integer cantidad_inmuebles,
			@JsonProperty(value="servicios_deseados")List<String> servicios_deseados
			) { 
		this.id = id;
		this.usuarios = usuarios;
		this.duracion = duracion;
		this.fecha_inicio_estadia = fecha_inicio_estadia;
		this.tipo_inmueble = tipo_inmueble;
		this.privacidad = privacidad;
		this.cantidad_inmuebles = cantidad_inmuebles;
		this.servicios_deseados = servicios_deseados;
	}

	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------
		
		
	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<UsuarioEnColectivo> getUusuarios() {
		return usuarios;
	}

	public void setIds_usuarios(List<UsuarioEnColectivo> usuarios) {
		this.usuarios = usuarios;
	}


	public Integer getDuracion() {
		return duracion;
	}


	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}


	public String getFecha_inicio_estadia() {
		return fecha_inicio_estadia;
	}


	public void setFecha_inicio_estadia(String fecha_inicio_estadia) {
		this.fecha_inicio_estadia = fecha_inicio_estadia;
	}


	public String getTipo_inmueble() {
		return tipo_inmueble;
	}


	public void setTipo_inmueble(String tipo_inmueble) {
		this.tipo_inmueble = tipo_inmueble;
	}


	public String getPrivacidad() {
		return privacidad;
	}


	public void setPrivacidad(String privacidad) {
		this.privacidad = privacidad;
	}


	public Integer getCantidad_inmuebles() {
		return cantidad_inmuebles;
	}


	public void setCantidad_inmuebles(Integer cantidad_inmuebles) {
		this.cantidad_inmuebles = cantidad_inmuebles;
	}


	public List<String> getServicios_deseados() {
		return servicios_deseados;
	}


	public void setServicios_deseados(List<String> servicios_deseados) {
		this.servicios_deseados = servicios_deseados;
	}

	
	
}
