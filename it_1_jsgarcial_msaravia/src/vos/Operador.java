package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Representa una persona tipo Operador 
 * su tipo pertenece a estudiante, registrado, empleado, profesor, padres, invitado, empresa
 * @author sebastian
 *
 */
public class Operador extends Persona {

	public Operador(  @JsonProperty(value="id")Long id, @JsonProperty(value="nombre")String nombre , @JsonProperty(  value="apellido")String apellido  ,   @JsonProperty(value="tipo")String tipo ) {                                
		super(id, nombre, apellido, tipo);
	}
	
}
