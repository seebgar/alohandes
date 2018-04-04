package vos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa un inmueble de tipo apartamento
 * @author sebastian
 *
 */
public class Apartamento {


	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id del inmueble
	 */
	@JsonProperty(value="id")
	private Long id;

	/**
	 * Determina si el apartamento esta amoblado
	 */
	@JsonProperty(value="amoblado")
	private Boolean amoblado;

	/**
	 * Costo adicional de la administracion
	 */
	@JsonProperty(value="costo_admin")
	private Double costo_admin;

	/**
	 * servicios que ofrece el apartamento
	 */
	@JsonProperty(value="servicios_basicos")
	private List<ServicioBasico> serviciosBasicos;

	/**
	 * propuesta que oferece el apartamento
	 */	
	@JsonProperty(value="propuesta")
	private Propuesta propuesta;
	
	/**
	 * Numero maximo de personas que pueder habitar este inmueble
	 */
	@JsonProperty(value="capacidad_maxima")
	private Integer capacidad_maxima;

	
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * Constructor de la clase Apartamento
	 * @param id
	 * @param amoblado
	 * @param costo_admin
	 */
	public Apartamento(
			@JsonProperty(value="id") Long id,
			@JsonProperty(value="amoblado") Boolean amoblado,
			@JsonProperty(value="costo_admin") Double costo_admin,
			@JsonProperty(value="capacidad_maxima") Integer cap_max) {
		this.id = id;
		this.amoblado = amoblado;
		this.costo_admin = costo_admin;
		// TODO propuesta y servicos basicos
		this.serviciosBasicos = new ArrayList<>();
		this.capacidad_maxima = cap_max;
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

	public Boolean getAmoblado() {
		return amoblado;
	}

	public void setAmoblado(Boolean amoblado) {
		this.amoblado = amoblado;
	}

	public Double getCosto_admin() {
		return costo_admin;
	}

	public void setCosto_admin(Double costo_admin) {
		this.costo_admin = costo_admin;
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

	public Integer getCapacidad_maxima() {
		return capacidad_maxima;
	}
	
	public void setCapacidad_maxima( Integer cap ) {
		this.capacidad_maxima = cap;
	}

}
