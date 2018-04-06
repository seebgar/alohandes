package vos;

import org.codehaus.jackson.annotate.JsonProperty;


/**
 * Representa la propuesta de un operador de alohandes
 * @author sebastian
 *
 */
public class Propuesta {


	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Representa el tipo de inmueble de la propuesta
	 * @author sebastian
	 *
	 */
	public enum TIPO_INMUEBLE {
		APARTAMENTO,
		HABITACION,
		HOSTEL,
		HOTEL,
		VIVIENDA_EXPRESS,
		VIVIENDA_UNIVERSITARIA;
	}
	
	/**
	 * Representa el tipo de inmueble de la propuesta
	 */
	@JsonProperty(value="tipo_inmueble")
	private String tipo_inmueble;
	
	/**
	 * Id de la PROPUESTA
	 */
	@JsonProperty(value="id")
	private Long id; 
	
	/**
	 * Representa el tipo de inmuble apartamento de la propuesta
	 */
	@JsonProperty(value="apartamento")
	private Apartamento apartamento;
	
	/**
	 * Representa el tipo de inmuble habitacion de la propuesta
	 */
	@JsonProperty(value="habitacion")
	private Habitacion habitacion;
	
	/**
	 * Representa el tipo de inmuble hostel de la propuesta
	 */
	@JsonProperty(value="hostel")
	private Hostel hostel;
	
	/**
	 * Representa el tipo de inmuble hotel de la propuesta
	 */
	@JsonProperty(value="hotel")
	private Hotel hotel;
	
	/**
	 * Representa el tipo de inmuble vivienda express de la propuesta
	 */
	@JsonProperty(value="vivienda_express")
	private ViviendaExpress vivienda_express;
	
	/**
	 * Representa el tipo de inmuble vivienda universitaria de la propuesta
	 */
	@JsonProperty(value="vivienda_universitaria")
	private ViviendaUniversitaria vivienda_universitarias;
	
	/**
	 * atributo que define si la propuesta se debe retirar
	 */
	@JsonProperty(value="se_va_retirar")
	private Boolean seVaRetirar;
	
	/**
	 * Numero maximo de personas que pueder habitar este inmueble
	 */
	@JsonProperty(value="capacidad_maxima")
	private Integer capacidad_maxima;

	/**
	 * Id del operador responsable de esta propuesta
	 */
	@JsonProperty(value="persona")
	private Integer id_persona;
	/**
	 * Boolean que representa si una propuesta esta disponble o no
	 */
	@JsonProperty(value="disponible")
	private Boolean disonible;
	/**
	 * Fecha inicial desde la cual la propuesta esta disponible
	 */
	@JsonProperty(value="fecha_inicio_disponibilidad")
	private String fecha_inicio_disponibilidad;
	/**
	 * Fecha final desde la cual la propuesta esta no disponible
	 */
	@JsonProperty(value="fecha_final_disponibilidad")
	private String fecha_final_disponibilidad;
	/**
	 * cantidad de dias disponibles que lleva libre una propuesta
	 */
	@JsonProperty(value="cantidad_dias_disponibles")
	private Integer cantidad_dias_disponibles;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	public Boolean getDisonible() {
		return disonible;
	}


	public void setDisonible(Boolean disonible) {
		this.disonible = disonible;
	}


	public String getFecha_inicio_disponibilidad() {
		return fecha_inicio_disponibilidad;
	}


	public void setFecha_inicio_disponibilidad(String fecha_inicio_disponibilidad) {
		this.fecha_inicio_disponibilidad = fecha_inicio_disponibilidad;
	}


	public String getFecha_final_disponibilidad() {
		return fecha_final_disponibilidad;
	}


	public void setFecha_final_disponibilidad(String fecha_final_disponibilidad) {
		this.fecha_final_disponibilidad = fecha_final_disponibilidad;
	}


	public Integer getCantidad_dias_disponibles() {
		return cantidad_dias_disponibles;
	}


	public void setCantidad_dias_disponibles(Integer cantidad_dias_disponibles) {
		this.cantidad_dias_disponibles = cantidad_dias_disponibles;
	}


	public Propuesta ( @JsonProperty(value="id") Long id,
			@JsonProperty(value="tipo_inmueble") String tipo_inmueble,
			@JsonProperty(value="capacidad_maxima") Integer cap,
			@JsonProperty(value="persona") Integer id_persona,
			@JsonProperty(value="cantidad_dias_disponibles") Integer dias_dispo,
	@JsonProperty(value="fecha_final_disponibilidad")String fecha_inicio_dispo,
	@JsonProperty(value="fecha_inicio_disponibilidad")String fecha_final_dispo,
	@JsonProperty(value="disponible") Boolean disponible) {
		this.id = id;
		this.tipo_inmueble = tipo_inmueble;
		this.apartamento = null;
		this.habitacion = null;
		this.hostel = null;
		this.hotel = null;
		this.vivienda_express = null;
		this.vivienda_universitarias = null;
		this.seVaRetirar= false;
		this.capacidad_maxima = cap;
		this.seVaRetirar = false;
		this.id_persona = id_persona;
		this.disonible=disponible;
		this.cantidad_dias_disponibles= dias_dispo;
		this.fecha_final_disponibilidad=fecha_final_dispo;
		this.fecha_inicio_disponibilidad=fecha_inicio_dispo;
		
	}
	

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------

	
	public Long getId() {
		return id;
	}

	public Integer getId_persona() {
		return id_persona;
	}


	public void setId_persona(Integer id_persona) {
		this.id_persona = id_persona;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public Hostel getHostel() {
		return hostel;
	}

	public void setHostel(Hostel hostel) {
		this.hostel = hostel;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public ViviendaExpress getVivienda_express() {
		return vivienda_express;
	}

	public void setVivienda_express(ViviendaExpress vivienda_express) {
		this.vivienda_express = vivienda_express;
	}

	public ViviendaUniversitaria getVivienda_universitarias() {
		return vivienda_universitarias;
	}

	public void setVivienda_universitarias(ViviendaUniversitaria vivienda_universitarias) {
		this.vivienda_universitarias = vivienda_universitarias;
	}

	public String getTipo_inmueble() {
		return tipo_inmueble;
	}

	public void setTipo_inmueble(String tipo_inmueble) {
		this.tipo_inmueble = tipo_inmueble;
	}


	/**
	 * @return the seVaRetirar
	 */
	public Boolean getSeVaRetirar() {
		return seVaRetirar;
	}


	/**
	 * @param seVaRetirar the seVaRetirar to set
	 */
	public void setSeVaRetirar(Boolean seVaRetirar) {
		this.seVaRetirar = seVaRetirar;
	}


	public Integer getCapacidad_maxima() {
		return capacidad_maxima;
	}


	public void setCapacidad_maxima(Integer capacidad_maxima) {
		this.capacidad_maxima = capacidad_maxima;
	}
	
	

	
}
