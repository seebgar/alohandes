/**-------------------------------------------------------------------
 * ISIS2304 - Sistemas Transaccionales
 * Departamento de Ingenieria de Sistemas
 * Universidad de los Andes
 * Bogota, Colombia
 * 
 * Actividad: Tutorial Parranderos: Arquitectura
 * Autores:
 * 			Santiago Cortes Fernandez	-	s.cortes@uniandes.edu.co
 * 			Juan David Vega Guzman		-	jd.vega11@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import org.codehaus.jackson.annotate.*;

/**
 * @author Santiago Cortes Fernandez 	- 	s.cortes@uniandes.edu.co
 * @author Juan David Vega Guzman		-	jd.vega11@uniandes.edu.co
 * Clase que representa a los Bebebores del modelo Parranderos
 */
public class Bebedor {

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id del bebedor
	 */
	@JsonProperty(value="id")
	//TODO Requerimiento 1A: Cree un atributo tipo Long para representar el id. Este debe tener el mismo nombre que la etiqueta
	private long id; 

	/**
	 * Nombre del bebedor
	 */
	@JsonProperty(value="nombre")
	//TODO Requerimiento 1B: Cree un atributo tipo String para representar el nombre. Este debe tener el mismo nombre que la etiqueta
	private String nombre;

	/**
	 * Presupuesto del bebedor. Puede ser Alto, Medio o Bajo.
	 */
	//TODO Requerimiento 1C: Agregue la anotacion @JsonProperty para el atributo que representa el presupuesto. Esta debe tener el mismo nombre que el atributo
	@JsonProperty(value="presupuesto")
	private String presupuesto;
	
	/**
	 * Ciudad del bebedor
	 */
	//TODO Requerimiento 1D: Agregue la anotacion @JsonProperty para el atributo que representa la ciudad. Esta debe tener el mismo nombre que el atributo
	@JsonProperty(value="ciudad")
	private String ciudad;
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase Bebedor
	 * <b>post: </b> Crea el bebedor con los valores que entran por parametro
	 * @param id - Id del bebedor.
	 * @param nombre - Nombre del bebedor.
	 * @param presupuesto - Presupuesto del bebedor.
	 * @param ciudad - Ciudad del bebedor.
	 */
	//TODO Requerimiento 1E: Complete el metodo constructor (parametros y contenido) con los atributos agregados anteriormente
	public Bebedor(  @JsonProperty(value="id")long id, @JsonProperty(value="nombre")String nombre , @JsonProperty(  value="presupuesto")String presupuesto  ,   @JsonProperty(value="ciudad")String ciudad ) {
		this.id = id;
		this.nombre = nombre;
		this.presupuesto = presupuesto;
		this.ciudad = ciudad;
	}

	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------
		
		
	//TODO Requerimiento 1F: Genere los Getters y Setter de los atributos utilizando la opcion en Source-> Generate Getters And Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(String presupuesto) {
		this.presupuesto = presupuesto;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	
}
