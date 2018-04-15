package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Representa un Objeto para el requerimiento de consulta 8
 * para que en JSON se despliegue como Objeto
 * @author js.garcial1
 *
 */
public class ClienteFrecuente {
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Id del cliente frecuente
	 */
	@JsonProperty(value="id")
	private Long id;
	
	/**
	 * Cantidad de reservas realizadas por el cliente
	 */
	@JsonProperty(value="cantidad_reservas")
	private Integer cantidad_reservas;
	
	/**
	 * Nombre del cliente
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Tipo del cliente o relacion con la universidad
	 */
	@JsonProperty(value="tipo")
	private String tipo;
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constructor de la clase
	 * @param id
	 * @param cantidad_reservas
	 * @param nombre
	 * @param tipo o relacion con la universidad
	 */
	public ClienteFrecuente (
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="cantidad_reservas") Integer cantidad_reservas,
			@JsonProperty(value="nombre") String nombre,
			@JsonProperty(value="tipo") String tipo ) {
		this.id = id;
		this.cantidad_reservas = cantidad_reservas;
		this.nombre = nombre;
		this.tipo = tipo;
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

	public Integer getCantidad_reservas() {
		return cantidad_reservas;
	}

	public void setCantidad_reservas(Integer cantidad_reservas) {
		this.cantidad_reservas = cantidad_reservas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	

}
