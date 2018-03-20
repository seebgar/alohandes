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

	
	

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	public Propuesta ( @JsonProperty(value="id") Long id,
			@JsonProperty(value="id") String tipo_inmueble ) {
		this.id = id;
		this.tipo_inmueble = tipo_inmueble;
		this.apartamento = null;
		this.habitacion = null;
		this.hostel = null;
		this.hotel = null;
		this.vivienda_express = null;
		this.vivienda_universitarias = null;
		this.seVaRetirar= false;
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
	
	

	
}
