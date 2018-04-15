package vos;

import org.codehaus.jackson.annotate.JsonProperty;


/**
 * Clase que representa un Objeto retornado en el Requerimiento de Consulta 7
 * para que en JSON aparecezca como Objeto
 * @author sebastian
 *
 */
public class AnalisisPropuesta {


	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Semana o Mes
	 */
	@JsonProperty(value="tiempo")
	private String tiempo;
	
	/**
	 * Resultado dendiendo de la consulta
	 */
	@JsonProperty(value="resultado")
	private Double resultado;
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODO CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * Contructor de la clase
	 * @param tiempo
	 * @param cantidad_reservas
	 */
	public AnalisisPropuesta(
			@JsonProperty(value="tiempo") String tiempo,
			@JsonProperty(value="resultado") Double resultado ) {
		this.tiempo = tiempo;
		this.resultado = resultado;
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE LA CLASE
	//----------------------------------------------------------------------------------------------------------------------------------

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public Double get_resultado() {
		return resultado;
	}

	public void set_resultado(Double resultado) {
		this.resultado = resultado;
	}

}
