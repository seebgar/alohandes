package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Representa la persona Cliente cuyo tipo pertenece a 
 * estudiante, registrado, empleado, profesor, padres, invitado, empresa
 * @author sebastian
 *
 */
public class Cliente extends Persona{
	
	
	//atributos
	
	/**
	 * reserva que el cliente desea tener
	 */
	private Reserva reserva;
	
	//constructor
	public Cliente(  @JsonProperty(value="id")Long id, @JsonProperty(value="nombre")String nombre , @JsonProperty(  value="apellido")String apellido  ,   @JsonProperty(value="tipo")String tipo ) {                                
		super(id, nombre, apellido, tipo);
	}
	
	//metodos

	/**
	 * @return the reserva
	 */
	public Reserva getReserva() {
		return reserva;
	}

	/**
	 * @param reserva the reserva to set
	 */
	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	

}
