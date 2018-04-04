package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa los servicios basicos que un
 * inmueble debe o puede ofrecer al cliente
 * @author sebastian
 *
 */
public class ServicioBasico {

	
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	
	/**
	 * Nombre del servicio basico = 
	 * {Luz, Telefono, agua, tv, desayuno, almuerzo, comida, cocina, bano, gym, apoyo social, apoyo acadmico}
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Representa el costo de un servicio
	 */
	@JsonProperty(value="costo")
	private Double costo = 0.0;
	
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// Metodo Constructor
	//----------------------------------------------------------------------------------------------------------------------------------

	
	/**
	 * Constructor de un Servicio Basico
	 * @param nombre
	 * @param costo
	 */
	public ServicioBasico( @JsonProperty(value="nombre") String nombre,  @JsonProperty(value="costo") Double costo ) {
		this.nombre = nombre;
		this.costo = costo;
	}


	
	//----------------------------------------------------------------------------------------------------------------------------------
	// Metodos de la clase
	//----------------------------------------------------------------------------------------------------------------------------------

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}
	

		

}
