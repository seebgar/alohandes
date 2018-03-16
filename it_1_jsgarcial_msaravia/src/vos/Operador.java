package vos;

<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> df0512da31cd84585ed0406c530c93edc782615a
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Representa una persona tipo Operador 
 * su tipo pertenece a estudiante, registrado, empleado, profesor, padres, invitado, empresa
 * @author sebastian
 *
 */
public class Operador extends Persona {
	
	
<<<<<<< HEAD
	//private List<Propuesta> propuestas;
	

	public Operador(  
			@JsonProperty(value="id")Long id, 
			@JsonProperty(value="nombre")String nombre , 
			@JsonProperty(  value="apellido")String apellido  ,   
			@JsonProperty(value="tipo")String tipo ) {                                
=======
	//atributos
	
	/**
	 * propuestas que trabaja cada operador
	 */
	private List<Propuesta> propuestas;

	//constructor
	
	public Operador(  @JsonProperty(value="id")Long id, @JsonProperty(value="nombre")String nombre , @JsonProperty(  value="apellido")String apellido  ,   @JsonProperty(value="tipo")String tipo ) {                                
>>>>>>> df0512da31cd84585ed0406c530c93edc782615a
		super(id, nombre, apellido, tipo);
		//this.propuestas = new ArrayList<>();
	}

	//metodos
	/**
	 * @return the propuesta
	 */
	public List<Propuesta> getPropuestas() {
		return propuestas;
	}

	/**
	 * @param propuesta the propuesta to set
	 */
	public void setPropuestas(List<Propuesta> propuestas) {
		this.propuestas = propuestas;
	}
	
}
