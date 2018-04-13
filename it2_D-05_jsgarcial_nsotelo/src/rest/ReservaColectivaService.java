package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohandesTransactionManager;
import vos.Colectivo;
import vos.Reserva;




@Path("reservasColectivas")
public class ReservaColectivaService {
	
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
	 * RF7
	 * 
	 * @param reserva
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response RF7_Registrar_Reserva_Colectiva( Colectivo reserva ) {

		try {
			
			AlohandesTransactionManager tm = new AlohandesTransactionManager(getPath());
			tm.RF7_registrar_reserva_colectiva(reserva);
			return Response.status(200).entity(reserva).build();
			
		}catch( Exception e ){
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	
	
	
	
	
	
	/**
	 * RF8
	 * 
	 * Cancelar una Reserva colectiva
	 */
	@DELETE 
	@Path("{id:\\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response RF8_Cancelar_Reserva_Colectiva ( @PathParam( "id" ) Long id )
	{
		try 
		{
			AlohandesTransactionManager tm= new AlohandesTransactionManager(getPath());
			tm.RF8_cancelarReservaColectiva(id);
			return Response.status( 200 ).entity(id).build();

		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}
	}
	
	
	
	
	

}
