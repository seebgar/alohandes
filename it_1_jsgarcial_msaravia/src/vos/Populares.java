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
public class Populares {


	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	
	@JsonProperty(value="id")
	private Long id;

	
	@JsonProperty(value="reservas")
	private Integer reservas;
	
	
	
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * Constructor de la clase Apartamento
	 * @param id
	 * @param amoblado
	 * @param costo_admin
	 */
	public Populares(
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="reservas") Integer cant ) {
		this.id = id;
		this.reservas = cant;
	}






	public Long getId() {
		return id;
	}






	public void setId(Long id) {
		this.id = id;
	}






	public Integer getCant_reservas() {
		return reservas;
	}






	public void setCant_reservas(Integer cant_reservas) {
		this.reservas = cant_reservas;
	}

	




}
