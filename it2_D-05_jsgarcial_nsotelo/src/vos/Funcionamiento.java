package vos;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Funcionamiento 
{
	/**
	 * Alojamientos de menor ocupacion
	 */
	@JsonProperty(value="AlojamientosmenorOcupacion")
	private List<PropuestaTiempo> AlojamientosmenorOcupacion;
	/**
	 * Alojamientos de mayor ocupacion
	 */
	@JsonProperty(value="AlojamientosmayorOcupacion")
	private List<PropuestaTiempo> AlojamientosmayorOcupacion;
	
	/**
	 * Alojamientos de menor ocupacion
	 */
	@JsonProperty(value="OperadoresMenosSolicitados")
	private List<PersonaTiempo> OperadoresMenosSolicitados;
	/**
	 * Alojamientos de mayor ocupacion
	 */
	@JsonProperty(value="OperadoresMasSolicitados")
	private List<PersonaTiempo> OperadoresMasSolicitados;
	
	public Funcionamiento() 
	{
		
		AlojamientosmenorOcupacion=new ArrayList<>();
		AlojamientosmayorOcupacion=new ArrayList<>();
		OperadoresMenosSolicitados= new ArrayList<>();
		OperadoresMasSolicitados=new ArrayList<>();
	}

	public List<PropuestaTiempo> getAlojamientosmenorOcupacion() {
		return AlojamientosmenorOcupacion;
	}

	public void setAlojamientosmenorOcupacion(List<PropuestaTiempo> alojamientosmenorOcupacion) {
		AlojamientosmenorOcupacion = alojamientosmenorOcupacion;
	}

	public List<PropuestaTiempo> getAlojamientosmayorOcupacion() {
		return AlojamientosmayorOcupacion;
	}

	public void setAlojamientosmayorOcupacion(List<PropuestaTiempo> alojamientosmayorOcupacion) {
		AlojamientosmayorOcupacion = alojamientosmayorOcupacion;
	}

	public List<PersonaTiempo> getOperadoresMenosSolicitados() {
		return OperadoresMenosSolicitados;
	}

	public void setOperadoresMenosSolicitados(List<PersonaTiempo> operadoresMenosSolicitados) {
		OperadoresMenosSolicitados = operadoresMenosSolicitados;
	}

	public List<PersonaTiempo> getOperadoresMasSolicitados() {
		return OperadoresMasSolicitados;
	}

	public void setOperadoresMasSolicitados(List<PersonaTiempo> operadoresMasSolicitados) {
		OperadoresMasSolicitados = operadoresMasSolicitados;
	}

}
