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
			@JsonProperty(value="costo_admin") Double costo_admin ) {
		this.id = id;
		this.amoblado = amoblado;
		this.costo_admin = costo_admin;
		// TODO propuesta y servicos basicos
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

//	public static void main(String[] args) {
//		Date d = new Date("12/26/1997 12:02:33");
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(d);
//		cal.add(Calendar.DAY_OF_YEAR, -46);
//		System.out.println(cal.getTime());
//		
//	}


}
