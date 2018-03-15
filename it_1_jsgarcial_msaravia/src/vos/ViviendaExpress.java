package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa una vivienda express
 * @author sebastian
 *
 */
public class ViviendaExpress {

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id del inmueble
	 */
	@JsonProperty(value="id")
	private Long id; 

	/**
	 * Descripcion de los accesorios o muebles incluidos en este inmueble
	 */
	@JsonProperty(value="menaje")
	private String menaje;

	/**
	 * Direccion de la vivienda express
	 */
	@JsonProperty(value="ubicacion")
	private String ubicacion;


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constructor de la clase vivienda express
	 * @param id
	 * @param menaje
	 * @param ubicacion
	 */
	public ViviendaExpress(
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="menaje") String menaje,
			@JsonProperty(value="ubicacion") String ubicacion) {
		this.id = id;
		this.menaje = menaje;
		this.ubicacion = ubicacion;
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
	
	public String getMenaje() {
		return menaje;
	}

	public void setMenaje(String menaje) {
		this.menaje = menaje;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	

}
