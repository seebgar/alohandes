package vos;


/**
 * Representa una persona tipo Operador 
 * su tipo pertenece a estudiante, registrado, empleado, profesor, padres, invitado, empresa
 * @author sebastian
 *
 */
public class Operador extends Persona {

	public Operador( Long id, String nombre, String apellido, String tipo ) {
		super(id, nombre, apellido, tipo);
	}
	
}
