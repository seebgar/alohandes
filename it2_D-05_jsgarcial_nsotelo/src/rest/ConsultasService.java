package rest;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohandesTransactionManager;
import tm.BusinessLogicException;
import vos.AnalisisPropuesta;
import vos.ClienteFrecuente;
import vos.Filtro;
import vos.Propuesta;

/**
 * Clase que representa el despliegue de los requerimientos de consulta
 * Iteracion 2
 * @author js.garcial1
 *
 */
@Path("consultas")
public class ConsultasService {

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS REST
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * RFC7 - ANALIZAR LA OPERACIÓN DE ALOHANDES 
	 * 
	 * Para una unidad de tiempo definido (por ejemplo, semana o mes) y un tipo de alojamiento, considerando todo
	 * el tiempo de operación de AloHandes, indicar cuáles fueron las fechas de mayor demanda (mayor cantidad de
	 * alojamientos ocupados), las de mayores ingresos (mayor cantidad de dinero recibido) y las de menor ocupación.
	 * @param filtro { mayor | ingresos | menor } = Mayor ocupacion, mayores ingresos o menor ocupacion.
	 * @param tiempo { semana | mes } = String que especifica si se trata de las semanaas o de los meses
	 * @param tipo_alojamiento { Apartamento | Hotel | Hostel | Vivienda Universitaria | Vivienda Express | Habitacion }
	 * @return Lista de las 
	 * @throws BusinessLogicException, SQLException, Exception 
	 */
	@GET
	@Path("/{filtro}/{tiempo}/{tipo_alojamiento}")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response RC7_analisis_propuestas ( @PathParam("filtro") String filtro, @PathParam("tiempo") String tiempo, @PathParam("tipo_alojamiento") String tipo_alojamiento ) {

		try {

			AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );
			List<AnalisisPropuesta> ans = tm.RC7_analisis_propuestas(filtro, tiempo, tipo_alojamiento);

			return Response.status( 200 ).entity( ans ).build( );	

		} catch( Exception e ) {
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}

	}

	/**
	 * RFC8 - ENCONTRAR LOS CLIENTES FRECUENTES
	 * 
	 * Para un alojamiento dado, encontrar la información de sus clientes frecuentes. se considera frecuente a un
	 * cliente si ha utilizado (o tiene reservado) ese alojamiento por lo menos en tres ocasiones o por lo menos 15
	 * noches, durante todo el periodo de operación de AlohAndes
	 * @param tipo_alojamiento { Apartamento | Hotel | Hostel | Vivienda Universitaria | Vivienda Express | Habitacion }
	 * @return
	 */
	@GET
	@Path("/frecuentes/{tipo_alojamiento}")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response RC8_clientes_frecuentes ( @PathParam("tipo_alojamiento") String tipo_alojamiento ) {

		try {

			AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );
			List<ClienteFrecuente> ans = tm.RC8_clientes_frecuentes(tipo_alojamiento);

			return Response.status( 200 ).entity( ans ).build( );	

		} catch( Exception e ) {
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}

	}

	/**
	 * RC 9 - ENCONTRAR LAS OFERTAS DE ALOJAMIENTO QUE NO TIENEN MUCHA DEMANDA
	 * 
	 * Encontrar las ofertas de alojamiento que no han recibido clientes en periodos superiores a 1 mes, durante todo
	 * el periodo de operación de AlohAndes
	 * @return
	 * @throws SQLException 
	 */
	@GET
	@Path( "/poca_demanda" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response RC9_poca_demanda (  ) {

		try {

			AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );
			List<Propuesta> ans = tm.RC9_poca_demanda();

			return Response.status( 200 ).entity( ans ).build( );	

		} catch( Exception e ) {
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}

	}


}
