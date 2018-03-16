package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Representa una persona tipo Operador 
 * su tipo pertenece a estudiante, registrado, empleado, profesor, padres, invitado, empresa
 * @author sebastian
 *
 */
public class Operador extends Persona {
	
	
	//atributos
	
	/**
	 * propuestas que trabaja cada operador
	 */
	private List<Propuesta> propuestas;

	//constructor
	
	public Operador(  @JsonProperty(value="id")Long id, @JsonProperty(value="nombre")String nombre , @JsonProperty(  value="apellido")String apellido  ,   @JsonProperty(value="tipo")String tipo ) {                                
		super(id, nombre, apellido, tipo);
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
