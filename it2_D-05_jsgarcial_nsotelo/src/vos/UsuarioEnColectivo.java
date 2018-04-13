package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Representa a un usuario involucrado en una reserva colectiva
 * @author sebastian
 *
 */
public class UsuarioEnColectivo {
	

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id 
	 */
	@JsonProperty(value="id")
	private Long id; 

	/**
	 * Representa el ID de la reserva
	 */
	@JsonProperty(value="id_reserva")
	private Long id_reserva;
	
	/**
	 * Representa la cantidad de personas que haran uso del inmueble
	 */
	@JsonProperty(value="cantidad_personas")
	private Integer cantidad_personas;
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase Colectivo
	 * <b>pre: </b> Existen los usuarios cuyos IDS estan involucrados en la reserva colevtiva
	 * <b>post: </b> Crea reservas para cada usuario con un id de reserva colevtiva en comun
	 * @param id - Id de la reserva colectiva
	 * @param ids_usuarios - IDs de los usuarios de la reserva colectiva
	 */
	public UsuarioEnColectivo (  
			@JsonProperty(value="id")Long id,
			@JsonProperty(value="id_reserva")Long id_reserva,
			@JsonProperty(value="cantidad_personas") Integer cantidad_personas
			) { 
		this.id = id;
		this.id_reserva = id_reserva;
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

	public Long getId_reserva() {
		return id_reserva;
	}

	public void setId_reserva(Long id_reserva) {
		this.id_reserva = id_reserva;
	}

	public Integer getCantidad_personas() {
		return cantidad_personas;
	}

	public void setCantidad_personas(Integer cantidad_personas) {
		this.cantidad_personas = cantidad_personas;
	}

	

}
