package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Representa la persona Cliente cuyo tipo pertenece a 
 * estudiante, registrado, empleado, profesor, padres, invitado, empresa
 * @author sebastian
 *
 */
public class Cliente extends Persona{


	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * reserva que el cliente desea tener
	 */
	@JsonProperty(value="reserva")
	private Reserva reserva;



	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 
	 * @param id
	 * @param nombre
	 * @param apellido
	 * @param tipo
	 */
	public Cliente(  
			@JsonProperty(value="id")Long id, 
			@JsonProperty(value="nombre")String nombre , 
			@JsonProperty(  value="apellido")String apellido  ,   
			@JsonProperty(value="tipo")String tipo,
			@JsonProperty(value="rol") String rol,
			@JsonProperty(value="nit") String nit,
			@JsonProperty(value="cedula") String cedula,
			@JsonProperty(value="email") String email) {                                
		super(id, nombre, apellido, tipo, rol, nit, cedula, email);
		// TODO inicializar reserva
	}



	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------


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
