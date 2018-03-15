package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa una Rerserva 
 * que es la representacion de un Contrato en el caso de estudio
 * @author sebastian
 *
 */
public class Reserva {



	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id de la reserva
	 */
	@JsonProperty(value="id")
	private Long id; 

	/**
	 * Fecha en la que se registró la reserva en el sistema
	 */
	@JsonProperty(value="fecha_registro")
	private String fecha_registro;

	/**
	 * Fecha en la que se canceló la reserva 
	 */
	@JsonProperty(value="fecha_cancelacion")
	private String fecha_cancelacion;

	/**
	 * Fecha en la que la persona empieza a hacer uso del inmueble
	 */
	@JsonProperty(value="fecha_inicio_estadia")
	private String fecha_inicio_estadia;

	/**
	 * Duracion de la estadia en DIAS
	 */
	@JsonProperty(value="duracion")
	private Integer duracion; 

	/**
	 * Costo total de la reserva
	 */
	@JsonProperty(value="costo_total")
	private Double costo_total;
	
	/**
	 * Determina la cantidad de personas que ocuparan un inmueble por medio de esta reserva
	 */
	@JsonProperty(value="cantidad_personas")
	private Integer cantidad_personas;


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase Reserva
	 * <b>post: </b> Crea la reserva con los valores que entran por parametro
	 * @param id
	 * @param fecha_registro
	 * @param fecha_cancelacion
	 * @param fecha_inicio_estadia
	 * @param duracion en DIAS
	 * @param costo_total
	 */
	public Reserva(
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="fecha_registro") String fecha_registro,
			@JsonProperty(value="fecha_cancelacion") String fecha_cancelacion,
			@JsonProperty(value="fecha_inicio_estadia") String fecha_inicio_estadia,
			@JsonProperty(value="duracion") Integer duracion,
			@JsonProperty(value="costo_total") Double costo_total,
			@JsonProperty(value="cantidad_personas") Integer cantidad_personas ) {
		this.id = id;
		this.fecha_registro = fecha_registro;
		this.fecha_cancelacion = fecha_cancelacion;
		this.fecha_inicio_estadia = fecha_inicio_estadia;
		this.duracion = duracion;
		this.costo_total = costo_total;
		this.cantidad_personas = cantidad_personas;
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

	public String getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(String fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public String getFecha_cancelacion() {
		return fecha_cancelacion;
	}

	public void setFecha_cancelacion(String fecha_cancelacion) {
		this.fecha_cancelacion = fecha_cancelacion;
	}

	public String getFecha_inicio_estadia() {
		return fecha_inicio_estadia;
	}

	public void setFecha_inicio_estadia(String fecha_inicio_estadia) {
		this.fecha_inicio_estadia = fecha_inicio_estadia;
	}

	/**
	 * EN DIAS
	 * @return
	 */
	public Integer getDuracion() {
		return duracion;
	}

	/**
	 * EN DIAS
	 * @param duracion
	 */
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public Double getCosto_total() {
		return costo_total;
	}

	public void setCosto_total(Double costo_total) {
		this.costo_total = costo_total;
	}

	public Integer getCantidad_personas() {
		return cantidad_personas;
	}

	public void setCantidad_personas(Integer cantidad_personas) {
		this.cantidad_personas = cantidad_personas;
	}




























}
