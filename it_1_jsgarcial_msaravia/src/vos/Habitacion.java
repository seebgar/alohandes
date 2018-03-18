package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el inmueble de habitacion
 * @author sebastian
 *
 */
public class Habitacion {



	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id del inmueble
	 */
	@JsonProperty(value="id")
	private Long id;

	/**
	 * True si este inmueble deberia tener un precio especial
	 */
	@JsonProperty(value="precio_especial")
	private Boolean precio_especial;

	/**
	 * Tipo de habitacion pertenece a individual o compartida
	 */
	@JsonProperty(value="tipo_habitacion")
	private String tipo_habitacion;

	/**
	 * Servicios que dispone la habitacion
	 */
	@JsonProperty(value="servicios_basicos")
	private List<ServicioBasico> serviciosBasicos;
	
	/**
	 * propuesta de la habitacion
	 */
	@JsonProperty(value="propuesta")
	private Propuesta propuesta;

	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constructor de la clase Habitacion
	 * @param id
	 * @param precio_especial
	 * @param tipo_habitacion
	 */
	public Habitacion (
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="precio_especial") Boolean precio_especial,
			@JsonProperty(value="tipo_habitacion") String tipo_habitacion) {
		this.id = id;
		this.precio_especial = precio_especial;
		this.tipo_habitacion = tipo_habitacion;
		//TODO inicializar propesta y servicios
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

	public Boolean getPrecio_especial() {
		return precio_especial;
	}

	public void setPrecio_especial(Boolean precio_especial) {
		this.precio_especial = precio_especial;
	}

	public String getTipo_habitacion() {
		return tipo_habitacion;
	}

	public void setTipo_habitacion(String tipo_habitacion) {
		this.tipo_habitacion = tipo_habitacion;
	}

	/**
	 * @return the serviciosBasicos
	 */
	public List<ServicioBasico> getServiciosBasicos() {
		return serviciosBasicos;
	}

	/**
	 * @param serviciosBasicos the serviciosBasicos to set
	 */
	public void setServiciosBasicos(List<ServicioBasico> serviciosBasicos) {
		this.serviciosBasicos = serviciosBasicos;
	}

	public void add_Servicio_Basico ( ServicioBasico serv, Double costo ) {
		serv.setCosto(costo);
		this.serviciosBasicos.add(serv);
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



}
