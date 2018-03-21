package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohandesTransactionManager;
import vos.Reserva;

@Path("reservas")
public class ReservaService {

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
	 * Retorna una reserva por id
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id: \\d+}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getReservaById(@PathParam("id") Long id) {

		try {
			AlohandesTransactionManager tm= new AlohandesTransactionManager(getPath());

			Reserva reserva = tm.getReservaById(id);
			return Response.status(200).entity(reserva).build();
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}

	}

	/**
	 * RF 4
	 * TODO
	 * Agrega una reserva a la base de datos
	 * 
	 * 
	 * @param reserva
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response registrarReserva(Reserva reserva) {

		try {
			AlohandesTransactionManager tm= new AlohandesTransactionManager(getPath());
			tm.registrarReserva(reserva);
			return Response.status(200).entity(reserva).build();
		}catch( Exception e ){
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * RF 5
	 * TODO
	 * 
	 * Elimina una reserva de la base de datos
	 * 
	 * @param reserva
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelarReserva(Reserva reserva) {

		try {
			AlohandesTransactionManager tm= new AlohandesTransactionManager(getPath());
			tm.cancelarReserva(reserva);
			return Response.status( 200 ).entity(reserva).build();
		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}
	}

	/**
	 * Lo mismo pero por id
	 * @param reserva
	 * @return
	 */
	@PUT
	@Path( "{id: \\d+}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelarReserva_porId( @PathParam( "id" ) Long id ) {

		try {
			AlohandesTransactionManager tm= new AlohandesTransactionManager(getPath());
			tm.cancelarReserva_porid(id);
			return Response.status( 200 ).entity(id).build();
		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}
	}

}
