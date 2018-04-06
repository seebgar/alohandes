package vos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa l dinero recibido por cada operador
 * @author sebastian
 *
 */
public class DineroOperador {


	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	
	@JsonProperty(value="id")
	private Long id;

	@JsonProperty(value="nombre")
	private String nombre;

	@JsonProperty(value="apellido")
	private String apellido;

	
	@JsonProperty(value="id_propuesta")
	private Long id_propuesta;

	
	@JsonProperty(value="ganancias")
	private Float ganancias;
	
	
	
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * Constructor de la clase Apartamento
	 * @param id
	 * @param amoblado
	 * @param costo_admin
	 */
	public DineroOperador(
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="nombre") String nombre,
			@JsonProperty(value="apellido") String apellido,
			@JsonProperty(value="id_propuesta") Long id_prop,
			@JsonProperty(value="ganancias") Float ganancias ) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.id_propuesta = id_prop;
		this.ganancias = ganancias;
	}






	public Long getId() {
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






	public void setApellido(String apellido) {
		this.apellido = apellido;
	}






	public Long getId_propuesta() {
		return id_propuesta;
	}






	public void setId_propuesta(Long id_propuesta) {
		this.id_propuesta = id_propuesta;
	}






	public Float getGanancias() {
		return ganancias;
	}






	public void setGanancias(Float ganancias) {
		this.ganancias = ganancias;
	}






	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------




}
