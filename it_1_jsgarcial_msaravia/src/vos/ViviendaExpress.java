package vos;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa una vivienda express
 * @author sebastian
 *
 */
public class ViviendaExpress {

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id del inmueble
	 */
	@JsonProperty(value="id")
	private Long id; 

	/**
	 * Descripcion de los accesorios o muebles incluidos en este inmueble
	 */
	@JsonProperty(value="menaje")
	private String menaje;

	/**
	 * Direccion de la vivienda express
	 */
	@JsonProperty(value="ubicacion")
	private String ubicacion;
	
	/**
	 * servicios que ofrece la vivienda
	 */
	@JsonProperty(value="servicios_basicos")
	private List<ServicioBasico> serviciosBasicos;
	
	/**
	 * propuesta que ofrece la vivienda
	 */
	@JsonProperty(value="propuesta")
	private Propuesta propuesta;


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Constructor de la clase vivienda express
	 * @param id
	 * @param menaje
	 * @param ubicacion
	 */
	public ViviendaExpress(
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="menaje") String menaje,
			@JsonProperty(value="ubicacion") String ubicacion) {
		this.id = id;
		this.menaje = menaje;
		this.ubicacion = ubicacion;
		// TODO inicializar propuesta
		this.serviciosBasicos = new ArrayList<>();
	}


	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMenaje() {
		return menaje;
	}

	public void setMenaje(String menaje) {
		this.menaje = menaje;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * @return the serviciosBasicos
	 */
	public List<ServicioBasico> getServiciosBasicos() {
		return serviciosBasicos;
	}

	/**
	 * @param serviciosBasicos the serviciosBasicos to set
	 */
	public void setServiciosBasicos(List<ServicioBasico> serviciosBasicos) {
		this.serviciosBasicos = serviciosBasicos;
	}
	
	public void add_Servicio_Basico ( ServicioBasico serv, Double costo ) {
		serv.setCosto(costo);
		this.serviciosBasicos.add(serv);
	}

	/**
	 * @return the propuesta
	 */
	public Propuesta getPropuesta() {
		return propuesta;
	}

	/**
	 * @param propuesta the propuesta to set
	 */
	public void setPropuesta(Propuesta propuesta) {
		this.propuesta = propuesta;
	}
	

}
