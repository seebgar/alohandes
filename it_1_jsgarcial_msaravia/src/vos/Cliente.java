package vos;



/**
 * Representa la persona Cliente cuyo tipo pertenece a 
 * estudiante, registrado, empleado, profesor, padres, invitado, empresa
 * @author sebastian
 *
 */
public class Cliente extends Persona{
	
	public Cliente(Long id, String nombre, String apellido, String tipo) {
		super(id, nombre, apellido, tipo);
	}
	

}
