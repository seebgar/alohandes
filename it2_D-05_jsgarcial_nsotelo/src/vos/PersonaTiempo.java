package vos;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import tm.BusinessLogicException;

public class PersonaTiempo {
	
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
	 * Tipo de persona. Puede 
	 * ser : estudiante, registrado, empleado, profesor, padre, invitado, empresa, egresado
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
	
	/**
	 * Correo electronico de la persona
	 */
	@JsonProperty(value="email")
	private String email;
	
	/**
	 * Lista de propuestad de un operador
	 */
	@JsonProperty(value="propuestas")
	private List<Propuesta> propuestas;
	
	@JsonProperty(value="costo_multa")
	private Double costo_multa;
	
	@JsonProperty(value="tiempo")
	private String tiempo;
	
	
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
	public PersonaTiempo(  
			@JsonProperty(value="id")Long id, 
			@JsonProperty(value="nombre")String nombre , 
			@JsonProperty(  value="apellido")String apellido  ,   
			@JsonProperty(value="tipo")String tipo,
			@JsonProperty(value="rol") String rol,
			@JsonProperty(value="nit") String nit,
			@JsonProperty(value="cedula") String cedula,
			@JsonProperty(value="email") String email,	@JsonProperty(value="tiempo") String tiempo ) { 
		
		if ( !tipo.equalsIgnoreCase("empresa") )
			this.nit = null;
		else
			this.nit = nit;
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.tipo = tipo;
		this.rol = rol;
		this.cedula = cedula;
		this.email = email;
		this.costo_multa = 0.0;
		
		this.propuestas = new ArrayList<>();
		this.tiempo=tiempo;
		
	}

	
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------
		
		
	public String getTiempo() {
		return tiempo;
	}





	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}





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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail( String email ) {
		this.email = email;
	}

	public List<Propuesta> getPropuestas() {
		return propuestas;
	}

	public void setPropuestas(List<Propuesta> propuestas) {
		this.propuestas = propuestas;
	}

	/**
	 * Para realizar una propuesta, se debe ser {operador} y estar asociado con
	 * la universidad {estudiante, empleado, profesor, padre, empresa}
	 * @param propuesta
	 */
	public void addPropuesta ( Propuesta propuesta ) throws BusinessLogicException {
		
		if (this.rol.equalsIgnoreCase("CLIENTE")) 
			throw new BusinessLogicException("Un cliente no puede realizar propuestas de alohamiento. Debe estar registrado como operador y estar asociado con la universidad.");
		if (this.tipo.equalsIgnoreCase("INVITADO") || this.tipo.equalsIgnoreCase("INVITADO")) 
			throw new BusinessLogicException("El usuario no cuenta con los requisitos para relaizar una propuesta. Debe estar registrado como operador y estar asociado con la universidad.");
			
		this.propuestas.add(propuesta);
		
	}

	public Double getCosto_multa() {
		return costo_multa;
	}

	public void setCosto_multa(Double costo_multa) {
		this.costo_multa = costo_multa;
	}
	
	
	
	
	
	/*
	 * ITERACION 3
	 */
	@JsonProperty(value="inmueble")
	private String inmueble = "NA";

	public String getInmueble() {
		return inmueble;
	}

	public void setInmueble(String inmueble) {
		this.inmueble = inmueble;
	}
	
	

}
