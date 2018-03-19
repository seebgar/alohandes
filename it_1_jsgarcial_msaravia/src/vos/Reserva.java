package vos;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.management.Query;

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
	
	/**
	 * Determina si existe una multa hacia el cliente
	 */
	@JsonProperty(value= "hay_multa")
	private Boolean hayMulta;
	
	/**
	 * el costo se hay multa
	 */
	@JsonProperty(value="valor_multa")
	private Double valorMulta;
	
	/**
	 * propuesta de la reserva
	 */
	@JsonProperty(value="propuesta")
	private Propuesta propuesta;
	
	/**
	 * cliente que le corresponde a cada reserva
	 */
	@JsonProperty(value= "cliente")
	private Cliente cliente;


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
	 * @param duracion
	 * @param costo_total
	 * @param cantidad_personas
	 * @param hayMulta
	 * @param valorMulta
	 */
	public Reserva(
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="fecha_registro") String fecha_registro,
			@JsonProperty(value="fecha_cancelacion") String fecha_cancelacion,
			@JsonProperty(value="fecha_inicio_estadia") String fecha_inicio_estadia,
			@JsonProperty(value="duracion") Integer duracion,
			@JsonProperty(value="costo_total") Double costo_total,
			@JsonProperty(value="cantidad_personas") Integer cantidad_personas,
			@JsonProperty(value= "hay_multa") Boolean hayMulta,
			@JsonProperty(value="vlor_multa") Double valorMulta,
			@JsonProperty(value= "propuesta") Propuesta propuesta,
			@JsonProperty(value= "cliente") Cliente cliente) {
		this.id = id;
		this.fecha_registro = fecha_registro;
		this.fecha_cancelacion = fecha_cancelacion;
		this.fecha_inicio_estadia = fecha_inicio_estadia;
		this.duracion = duracion;
		this.costo_total = costo_total;
		this.cantidad_personas = cantidad_personas;
		this.hayMulta= hayMulta;
		this.valorMulta= valorMulta;
		//TODO inizialicar propuesta y cliente
		
		this.propuesta= propuesta;
		this.cliente=cliente;
		
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

	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
	/**
	 * @return the hayMulta
	 */
	public Boolean getHayMulta() {
		return hayMulta;
	}




	/**
	 * @param hayMulta the hayMulta to set
	 */
	public void setHayMulta(Boolean hayMulta) {
		this.hayMulta = hayMulta;
	}




	/**
	 * @return the valorMulta
	 */
	public Double getValorMulta() {
		return valorMulta;
	}




	/**
	 * @param valorMulta the valorMulta to set
	 */
	public void setValorMulta(Double valorMulta) {
		this.valorMulta = valorMulta;
	}




	public Date getFechaFinal() throws Exception{
		
        DateFormat formato= new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
		Date fechaInicio;
		
		fechaInicio = formato.parse(fecha_inicio_estadia);
        
		Calendar cal= Calendar.getInstance();
		
		cal.setTime(fechaInicio);
		cal.add(Calendar.DAY_OF_YEAR, duracion);
		Date fechaFin= cal.getTime();
		
		return fechaFin;
	}








}
