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
 * Clase que representa una Persona
 * @author sebastian
 *
 */
public class Persona {

	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id de la persona
	 */
	@JsonProperty(value="id")
	private Long id; 

	/**
	 * Nombre de la persona
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * Apellido de la persona
	 */
	@JsonProperty(value="apellido")
	private String apellido;
	
	/**
	 * Tipo de persona. Puede ser : estudiante, registrado, empleado, profesor, padre, invitado, empresa
	 */
	@JsonProperty(value="tipo")
	private String tipo;
	
	/**
	 * Determina el rol de la persona {cliente, operador}
	 */
	@JsonProperty(value="rol")
	private String rol;
	
	/**
	 * Representa el NIT de una persona, normalmento perosna tipo empresa
	 */
	@JsonProperty(value="nit")
	private String nit;
	
	/**
	 * Cedula de la persona
	 */
	@JsonProperty(value="cedula")
	private String cedula;
	
	
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase Persona
	 * <b>post: </b> Crea la persona con los valores que entran por parametro
	 * @param id - Id de la persona.
	 * @param nombre - Nombre de la persona.
	 * @param apellido - Apellido de la persona.
	 * @param tipo - tipo de la persona.
	 */
	public Persona(  
			@JsonProperty(value="id")Long id, 
			@JsonProperty(value="nombre")String nombre , 
			@JsonProperty(  value="apellido")String apellido  ,   
			@JsonProperty(value="tipo")String tipo,
			@JsonProperty(value="rol") String rol,
			@JsonProperty(value="nit") String nit,
			@JsonProperty(value="cedula") String cedula ) {                                
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.tipo = tipo;
		this.rol = rol;
		this.nit = nit;
		this.cedula = cedula;
	}

	
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------
		
		
	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setPresupuesto(String apellido) {
		this.apellido = apellido;
	}

	public String getTipo() {
		return tipo;
	}

	public void setCiudad(String tipo) {
		this.tipo = tipo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
