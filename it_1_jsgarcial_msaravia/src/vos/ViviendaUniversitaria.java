package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Representa una vivienda univeristaria
 * @author sebastian
 *
 */
public class ViviendaUniversitaria {

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id del inmueble
	 */
	@JsonProperty(value="id")
	private Long id; 

	/**
	 * Direccion en donde se ubica el inmueble
	 */
	@JsonProperty(value="ubicacion")
	private String ubicacion;

	/**
	 * Capacidad del inmueble
	 */
	@JsonProperty(value="capacidad")
	private String capacidad;

	/**
	 * Menaje del inmueble
	 */
	@JsonProperty(value="menaje")
	private String menaje;

	/**
	 * Descripcion del inmueble deberia contener informacion sobre la disponibilidad de porteria
	 */
	@JsonProperty(value="descripcion")
	private String descripcion;

	/**
	 * Tipo del inmueble pertenece a individual, compartida
	 */
	@JsonProperty(value="tipo")
	private String tipo;

	/**
	 * Determina si el inmueble es oara uso MENSUAL o SEMESTRAL
	 */
	@JsonProperty(value="mensual")
	private Boolean mensual;

	/**
	 * Lista de servicios disponibles con el inmueble
	 */
	@JsonProperty(value="servicios")
	private List<ServicioBasico> servicios;


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	public ViviendaUniversitaria(
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="ubicacion") String ubicacion, 
			@JsonProperty(value="capacidad") String capacidad,
			@JsonProperty(value="menaje") String menaje,
			@JsonProperty(value="descripcion") String descripcion,
			@JsonProperty(value="tipo") String tipo,
			@JsonProperty(value="mensual") Boolean mensual ) {
		this.id = id;
		this.ubicacion = ubicacion;
		this.capacidad = capacidad;
		this.menaje = menaje;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.mensual = mensual;
		// TODO inicializar servicios
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

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getMenaje() {
		return menaje;
	}

	public void setMenaje(String menaje) {
		this.menaje = menaje;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getMensual() {
		return mensual;
	}

	public void setMensual(Boolean mensual) {
		this.mensual = mensual;
	}

	public List<ServicioBasico> getServicios() {
		return servicios;
	}

	public void setServicios(List<ServicioBasico> servicios) {
		this.servicios = servicios;
	}







}
