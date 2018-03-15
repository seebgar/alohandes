package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Representa la persona Cliente cuyo tipo pertenece a 
 * estudiante, registrado, empleado, profesor, padres, invitado, empresa
 * @author sebastian
 *
 */
public class Cliente extends Persona{
	
	public Cliente(  @JsonProperty(value="id")Long id, @JsonProperty(value="nombre")String nombre , @JsonProperty(  value="apellido")String apellido  ,   @JsonProperty(value="tipo")String tipo ) {                                
		super(id, nombre, apellido, tipo);
	}
	

}
