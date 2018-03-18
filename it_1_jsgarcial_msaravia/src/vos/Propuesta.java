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
	 * Id de la PROPUESTA
	 */
	@JsonProperty(value="id")
	private Long id; 
	
	/**
	 * Representa el tipo de inmuble apartamento de la propuesta
	 */
	@JsonProperty(value="apartamento")
	private Apartamento apartamento = null;
	
	/**
	 * Representa el tipo de inmuble habitacion de la propuesta
	 */
	@JsonProperty(value="habitacion")
	private Habitacion habitacion = null;
	
	/**
	 * Representa el tipo de inmuble hostel de la propuesta
	 */
	@JsonProperty(value="hostel")
	private Hostel hostel = null;
	
	/**
	 * Representa el tipo de inmuble hotel de la propuesta
	 */
	@JsonProperty(value="hotel")
	private Hotel hotel = null;
	
	/**
	 * Representa el tipo de inmuble vivienda express de la propuesta
	 */
	@JsonProperty(value="vivienda_express")
	private ViviendaExpress vivienda_express = null;
	
	/**
	 * Representa el tipo de inmuble vivienda universitaria de la propuesta
	 */
	@JsonProperty(value="vivienda_universitaria")
	private ViviendaUniversitaria vivienda_universitarias = null;

	

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	public Propuesta ( String tipo_inmueble,
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="amoblado") Integer amoblado,
			@JsonProperty(value="costo_admin") Double costo_admin,
			@JsonProperty(value="tipo_habitacion") String tipo_habitacion,
			@JsonProperty(value="ubicacion") String ubicacion,
			@JsonProperty(value="horario_admin_inicial") Integer horario_admin_inicial,
			@JsonProperty(value="horario_admin_final") Integer horario_admin_final,
			@JsonProperty(value="horario_admin_24h") Integer hora_admin_24h,
			@JsonProperty(value="menaje") String menaje,
			@JsonProperty(value="capacidad") String capacidad,
			@JsonProperty(value="precio_especial") Integer precio_especial,
			@JsonProperty(value="registro_camara_comercio") String registro_camara_comercio,
			@JsonProperty(value="registro_superintendencia") String registro_superintendencia,
			@JsonProperty(value="descripcion") String descripcion, 
			@JsonProperty(value="mensual") Integer mensual ) {
		
		if ( TIPO_INMUEBLE.APARTAMENTO.toString().equalsIgnoreCase(tipo_inmueble) ) {
			
			boolean rep_amoblado = amoblado == 0 ? false : true;
			this.apartamento = new Apartamento(id, rep_amoblado, costo_admin);
			
		} else if ( TIPO_INMUEBLE.HABITACION.toString().equalsIgnoreCase(tipo_inmueble) ) {
			
			boolean rep_precios_esprecial = precio_especial == 0 ? false : true;
			this.habitacion = new Habitacion(id, rep_precios_esprecial, tipo_habitacion);
			
		} else if ( TIPO_INMUEBLE.HOSTEL.toString().equalsIgnoreCase(tipo_inmueble) ) {
			
			this.hostel = new Hostel(id, registro_camara_comercio, registro_superintendencia, tipo_habitacion, ubicacion, horario_admin_inicial, horario_admin_final);       
			
		} else if ( TIPO_INMUEBLE.HOTEL.toString().equalsIgnoreCase(tipo_inmueble) ) {
			
			boolean rep_hora_24h = hora_admin_24h == 0 ? false : true;
			this.hotel = new Hotel(id, registro_camara_comercio, registro_superintendencia, tipo_habitacion, ubicacion, rep_hora_24h);
			
		} else if ( TIPO_INMUEBLE.VIVIENDA_EXPRESS.toString().equalsIgnoreCase(tipo_inmueble) ) {
			
			this.vivienda_express = new ViviendaExpress(id, menaje, ubicacion);
			
		} else if ( TIPO_INMUEBLE.VIVIENDA_UNIVERSITARIA.toString().equalsIgnoreCase(tipo_inmueble) ) {
			
			boolean rep_mensual = mensual == 0 ? false : true;
			this.vivienda_universitarias = new ViviendaUniversitaria(id, ubicacion, capacidad, menaje, descripcion, tipo_habitacion, rep_mensual);
			
		} else {
			System.out.println("Tipo de inmubele invalido = " + tipo_inmueble);
		}
		
		
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

	
}
