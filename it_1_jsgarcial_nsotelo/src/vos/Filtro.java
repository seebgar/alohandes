package vos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa l dinero recibido por cada operador
 * @author sebastian
 *
 */
public class Filtro {


	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	
	@JsonProperty(value="id_propuesta")
	private Long id_propuesta;

	@JsonProperty(value="fecha_inicio_estadia")
	private String fecha_inicio_estadia;
	
	@JsonProperty(value="duracion_contrato")
	private Integer duracion_contrato;
	
	@JsonProperty(value="id_apartamento")
	private Long id_apartamento;
	
	@JsonProperty(value="id_vivienda_express")
	private Long id_vivienda_express;
	
	@JsonProperty(value="id_vivienda_universitaria")
	private Long id_vivienda_universitaria;
	
	
	@JsonProperty(value="id_habitacion")
	private Long id_habitacion;
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * Constructor de la clase Apartamento
	 * @param id
	 * @param amoblado
	 * @param costo_admin
	 */
	public Filtro(
			@JsonProperty(value="id_propuesta") Long id_propuesta,
			@JsonProperty(value="fecha_inicio_estadia") String fecha_inicio_estadia,
			@JsonProperty(value="duracion_contrato") Integer duracion_contrato,
			@JsonProperty(value="id_apartamento") Long id_apartamento,
			@JsonProperty(value="id_vivienda_express") Long id_vivienda_express,
			@JsonProperty(value="id_vivienda_universitaria") Long id_vivienda_universitaria ,
			@JsonProperty(value="id_habitacion") Long id_habitacion ) {
		
		this.id_propuesta = id_propuesta;
		this.fecha_inicio_estadia = fecha_inicio_estadia;
		this.duracion_contrato = duracion_contrato;
		this.id_apartamento = id_apartamento;
		this.id_vivienda_express = id_vivienda_express;
		this.id_vivienda_universitaria = id_vivienda_universitaria;
		this.id_habitacion = id_habitacion;
		
	}





	public Long getId_propuesta() {
		return id_propuesta;
	}





	public void setId_propuesta(Long id_propuesta) {
		this.id_propuesta = id_propuesta;
	}





	public String getFecha_inicio_estadia() {
		return fecha_inicio_estadia;
	}





	public void setFecha_inicio_estadia(String fecha_inicio_estadia) {
		this.fecha_inicio_estadia = fecha_inicio_estadia;
	}





	public Integer getDuracion_contrato() {
		return duracion_contrato;
	}





	public void setDuracion_contrato(Integer duracion_contrato) {
		this.duracion_contrato = duracion_contrato;
	}





	public Long getId_apartamento() {
		return id_apartamento;
	}





	public void setId_apartamento(Long id_apartamento) {
		this.id_apartamento = id_apartamento;
	}





	public Long getId_vivienda_express() {
		return id_vivienda_express;
	}





	public void setId_vivienda_express(Long id_vivienda_express) {
		this.id_vivienda_express = id_vivienda_express;
	}





	public Long getId_vivienda_universitaria() {
		return id_vivienda_universitaria;
	}





	public void setId_vivienda_universitaria(Long id_vivienda_universitaria) {
		this.id_vivienda_universitaria = id_vivienda_universitaria;
	}





	public Long getId_habitacion() {
		return id_habitacion;
	}





	public void setId_habitacion(Long id_habitacion) {
		this.id_habitacion = id_habitacion;
	}

	
}
