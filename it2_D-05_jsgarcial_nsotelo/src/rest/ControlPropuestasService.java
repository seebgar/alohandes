package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohandesTransactionManager;
import vos.Propuesta;
import vos.Reserva;
@Path("propuestas")
public class ControlPropuestasService 
{	//----------------------------------------------------------------------------------------------------------------------------------
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

	@PUT
	@Path("/deshabilitar/{id:\\d+}")
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes( { MediaType.APPLICATION_JSON } )
	public Response deshabilitarPropuesta( @PathParam( "id" ) Long id)
	{
		try 
		{
			AlohandesTransactionManager tm= new AlohandesTransactionManager(getPath());
			List<Reserva>lista= tm.deshabilitarOfertaDeAlojamiento(id); 
			return Response.status( 200 ).entity(lista).build();
		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}
	}

	@PUT
	@Path("/habilitar/{id:\\d+}")
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes( { MediaType.APPLICATION_JSON } )
	public Response habilitarPropuesta( @PathParam( "id" ) Long id)
	{
		try 
		{
			AlohandesTransactionManager tm= new AlohandesTransactionManager(getPath());
			tm.rehabilitarOfertaDeAlojamiento(id); 
			return Response.status( 200 ).entity(id).build();
		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}
	}

}
