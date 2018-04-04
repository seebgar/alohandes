package vos;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Representa una persona tipo Operador 
 * su tipo pertenece a estudiante, registrado, empleado, profesor, padres, invitado, empresa
 * @author sebastian
 *
 */
public class Operador extends Persona {


	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------



	/**
	 * propuestas que trabaja cada operador
	 */
	@JsonProperty(value="propuestas")
	private List<Propuesta> propuestas;


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 
	 * @param id
	 * @param nombre
	 * @param apellido
	 * @param tipo
	 */
	public Operador(  
			@JsonProperty(value="id")Long id, 
			@JsonProperty(value="nombre")String nombre , 
			@JsonProperty(  value="apellido")String apellido  ,  
			@JsonProperty(value="tipo")String tipo,
			@JsonProperty(value="rol") String rol,
			@JsonProperty(value="nit") String nit,
			@JsonProperty(value="cedula") String cedula,
			@JsonProperty(value="email") String email) {                                
		super(id, nombre, apellido, tipo, rol, nit, cedula, email);
		// TODO this.propuestas = new ArrayList<>();
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------


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
