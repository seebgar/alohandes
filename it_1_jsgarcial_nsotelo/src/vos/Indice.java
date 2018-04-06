package vos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa el dinero recibido por cada operador
 * @author sebastian
 *
 */
public class Indice {


	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	
	@JsonProperty(value="id_propuesta")
	private Long id_propuesta;

	
	@JsonProperty(value="total_personas")
	private Integer total_personas ;
	
	@JsonProperty(value="total_capacidad")
	private Integer total_capacidad ;
	

	@JsonProperty(value="indice")
	private Float indice ;
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * Constructor de la clase Apartamento
	 * @param id
	 * @param amoblado
	 * @param costo_admin
	 */
	public Indice(
			@JsonProperty(value="id_propuesta") Long id,
			@JsonProperty(value="total_personas") Integer pers,
			@JsonProperty(value="total_capacidad") Integer caps,
			@JsonProperty(value="indice") Float indice ) {
		this.id_propuesta = id;
		this.total_personas = pers;
		this.total_capacidad = caps;
		this.indice = indice;
	}



	public Long getId_propuesta() {
		return id_propuesta;
	}



	public void setId_propuesta(Long id_propuesta) {
		this.id_propuesta = id_propuesta;
	}



	public Integer getTotal_personas() {
		return total_personas;
	}



	public void setTotal_personas(Integer total_personas) {
		this.total_personas = total_personas;
	}



	public Integer getTotal_capacidad() {
		return total_capacidad;
	}



	public void setTotal_capacidad(Integer total_capacidad) {
		this.total_capacidad = total_capacidad;
	}



	public Float getIndice() {
		return indice;
	}



	public void setIndice(Float indice) {
		this.indice = indice;
	}
	
}
