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
import vos.Persona;
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
	 * RFC7 - ANALIZAR LA OPERACI�N DE ALOHANDES 
	 * 
	 * Para una unidad de tiempo definido (por ejemplo, semana o mes) y un tipo de alojamiento, considerando todo
	 * el tiempo de operaci�n de AloHandes, indicar cu�les fueron las fechas de mayor demanda (mayor cantidad de
	 * alojamientos ocupados), las de mayores ingresos (mayor cantidad de dinero recibido) y las de menor ocupaci�n.
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
	 * Para un alojamiento dado, encontrar la informaci�n de sus clientes frecuentes. se considera frecuente a un
	 * cliente si ha utilizado (o tiene reservado) ese alojamiento por lo menos en tres ocasiones o por lo menos 15
	 * noches, durante todo el periodo de operaci�n de AlohAndes
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
	 * el periodo de operaci�n de AlohAndes
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











	//----------------------------------------------------------------------------------------------------------------------------------
	// ITERACION 3
	//----------------------------------------------------------------------------------------------------------------------------------










	/**
	 * RFC10 - CONSULTAR CONSUMO EN ALOHANDES
	 * RFC 11 - INVERSO
	 * 
	 * Se quiere conocer la información de los usuarios que realizaron al menos una reserva de una determinada
	 * oferta de alojamiento en un rango de fechas. Los resultados deben ser clasificados según un criterio deseado
	 * por quien realiza la consulta. En la clasificación debe ofrecerse la posibilidad de agrupamiento y ordenamiento
	 * de las respuestas según los intereses del usuario que consulta como, por ejemplo, por los datos del cliente, por
	 * oferta de alojamiento y por tipo de alojamiento.

	 * @param admin { 1 | 0 } 1 si es administrador el que hace la consulta
	 * @param id_propuesta
	 * @param fecha_inicial String fecha inicial formato YYYY-MM-DD
	 * @param fecha_final  String fecha final formato YYYY-MM-DD
	 * @param tipo_ordenamiento String pertenece a { inmueble | id_persona  }
	 * @param id_usuario se es usuario el que hace la consulta, se especifica su identificador
	 * @param inverso String pertenece a { inverso | normal } para especificar si se quiere hacer la consullat RFC10 o la verison inversa RFC11

	 * @return Response
	 */
	@GET
	
	@Path("/{admin}/{id_propuesta}/{fecha_inicial}/{fecha_final}/{tipo_ordenamiento}/{id_usuario}/{inverso}")
	
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response RFC10_11_consumo_admina( @PathParam("admin") Long admin, @PathParam("id_propuesta") Long id_propuesta, @PathParam("fecha_inicial") String fecha_inicial, 
			@PathParam("fecha_final") String fecha_final, @PathParam("tipo_ordenamiento") String tipo_ordenamiento, @PathParam("id_usuario") Long id_usuario,
			@PathParam("inverso") String inverso ) {

		/* ADMINISTRADOR */
		if ( admin == 1 ) {

			if ( inverso.equalsIgnoreCase("inverso") ) {
				try {

					AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );
					/* INVERSO RFC 11*/
					// TIEMPO
					long startTime = System.nanoTime();
					
					List<Persona> ans = tm.RFC11_inverso_consumo_admin(id_propuesta, fecha_inicial, fecha_final, tipo_ordenamiento);

					long endTime = System.nanoTime();
					long duration = endTime - startTime;
					double seconds = (double)duration / 1000000000.00;
					
					System.out.println("TIEMPO RFC 11 >> "+ seconds + " seg");
					
					return Response.status( 200 ).entity( ans ).build( );	

				} catch( Exception e ) {
					return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
				}
			} else {
				try {

					AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );
					/* NORMAL RFC 11*/
					// TIEMPO
					long startTime = System.nanoTime();
					
					List<Persona> ans = tm.RFC10_consumo_admina(id_propuesta, fecha_inicial, fecha_final, tipo_ordenamiento);
					
					long endTime = System.nanoTime();
					long duration = endTime - startTime;
					double seconds = (double)duration / 1000000000.00;
					
					System.out.println("TIEMPO RFC 10 >> "+ seconds + " seg");

					return Response.status( 200 ).entity( ans ).build( );	

				} catch( Exception e ) {
					return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
				}
			}
			

			/* USUARIO */
		} else {

			if ( inverso.equalsIgnoreCase("inverso") ) {
				try {

					AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );
					/* INVERSO RFC 11*/
					// TIEMPO
					long startTime = System.nanoTime();
					
					List<Persona> ans = tm.RFC11_inverso_consumo_user(id_usuario, id_propuesta, fecha_inicial, fecha_final, tipo_ordenamiento);
					
					long endTime = System.nanoTime();
					long duration = endTime - startTime;
					double seconds = (double)duration / 1000000000.00;
					
					System.out.println("TIEMPO RFC 11 >> "+ seconds + " seg");

					return Response.status( 200 ).entity( ans ).build( );	

				} catch( Exception e ) {
					return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
				}
			} else {
				try {

					AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );
					/* NORMAL RFC 11*/
					// TIEMPO
					long startTime = System.nanoTime();
					
					List<Persona> ans = tm.RFC10_consumo_user(id_usuario, id_propuesta, fecha_inicial, fecha_final, tipo_ordenamiento);
					
					long endTime = System.nanoTime();
					long duration = endTime - startTime;
					double seconds = (double)duration / 1000000000.00;
					
					System.out.println("TIEMPO RFC 10 >> "+ seconds + " seg");

					return Response.status( 200 ).entity( ans ).build( );	

				} catch( Exception e ) {
					return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
				}
			}

		}

	}


























}
