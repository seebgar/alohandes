package vos;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa un Objeto retornado en el Requerimiento de Consulta 13
 * para que en JSON aparecezca como Objeto
 * @author sebastian
 *
 */
public class BuenosClientes 

{
	
	/**
	 * Clientes concurrentes
	 */
	@JsonProperty(value="concurrentes")
	private List<Cliente> concurrentes;
	/**
	 * Clientes que solo alquilan propuestas caras
	 */
	@JsonProperty(value="costosos")
	private List<Cliente> costosos;
	/**
	 * Clientes que solo alquilan suites
	 */
	@JsonProperty(value="suites")
	private List<Cliente> suites;
	
	
	public BuenosClientes() 
	{
	
		this.concurrentes = new ArrayList<>();
		this.costosos = new ArrayList<>();
		this.suites = new ArrayList<>();
	}


	public List<Cliente> getConcurrentes() {
		return concurrentes;
	}


	public void setConcurrentes(List<Cliente> concurrentes) {
		this.concurrentes = concurrentes;
	}


	public List<Cliente> getCostosos() {
		return costosos;
	}


	public void setCostosos(List<Cliente> costosos) {
		this.costosos = costosos;
	}


	public List<Cliente> getSuites() {
		return suites;
	}


	public void setSuites(List<Cliente> suites) {
		this.suites = suites;
	}



	







}
