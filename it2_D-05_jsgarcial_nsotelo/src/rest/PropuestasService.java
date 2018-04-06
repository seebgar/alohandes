/**-------------------------------------------------------------------
 * ISIS2304 - Sistemas Transaccionales
 * Departamento de Ingenieria de Sistemas
 * Universidad de los Andes
 * Bogota, Colombia
 * 
 * Iteracion 1
 * -------------------------------------------------------------------
 */
package rest;


import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohandesTransactionManager;
import vos.Bebedor;
import vos.Persona;
import vos.Populares;
import vos.Propuesta;

/**
 * Clase que expone servicios REST con ruta base: 
 * http://localhost:8080/TutorialParranderos/rest/personas/...
 * 
 * @author sebastian
 *
 */
//@Path("propuestas")
@Path("personas/operadores/{idPropuesta: \\d+}/propuestas")
public class PropuestasService {

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
	 * Metodo GET que trae a todos las personas de la Base de datos. <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/personaes <br/>
	 * @return	<b>Response Status 200</b> - JSON que contiene a todos las personas que estan en la Base de Datos <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */			
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPropuestas_por_Persona (@PathParam("idPropuesta") Long idPersona) {

		try {
			AlohandesTransactionManager tm = new AlohandesTransactionManager(getPath());
			List<Propuesta> res = tm.get_Propuestas_Operador_PorID(idPersona);	
			if (res == null) {
				throw new Exception("El recurso /personas/" + idPersona + "/reviews no existe." + 404);
			}

			return Response.status( 200 ).entity(res).build();

		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}
	}


	/**
	 * RF 2
	 * TODO
	 * Agrega una propuesta a la base de datos
	 * 
	 * @param propuesta
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarPropuesta(Propuesta propuesta) {

		try {
			AlohandesTransactionManager tm= new AlohandesTransactionManager(getPath());
			tm.addPropuesta(propuesta);
			return Response.status( 200 ).entity(propuesta).build();
		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}
	}


	/**
	 * RF 6
	 * TODO
	 * Elimina una propuesta de la base de datos
	 * 
	 * @param propuesta
	 * @return
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retirarPropuesta(Propuesta propuesta) {

		try {
			AlohandesTransactionManager tm= new AlohandesTransactionManager(getPath());
			tm.retirarPropuesta(propuesta);
			return Response.status( 200 ).entity(propuesta).build();
		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}
	}


	/**
	 * Delte por id
	 * @param propuesta
	 * @return
	 */
	@DELETE
	@Path( "{id: \\d+}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retirarPropuesta_porId ( @PathParam( "id" ) Long id ) {

		try {
			AlohandesTransactionManager tm= new AlohandesTransactionManager(getPath());
			tm.retirarPropuesta_byid(id);
			return Response.status( 200 ).entity(id).build();
		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}
	}


	
	
	




	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE CONSULTA
	//----------------------------------------------------------------------------------------------------------------------------------




	/**
	 * RFC 2
	 * TODO
	 * 
	 * Metodo que trae a todas las  20 propuestas más populares de la Base de Datos  <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * 
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/bebedores/filterBy?nombre=---&apellido=--- <br/>
	 * 
	 * @param ciudad - <em>[QueryParam]</em> parametro que indica la ciudad de los bebedores
	 * @param presupuesto - <em>[QueryParam]</em> parametro que indica el presupuesto de los bebedores
	 * @return	<b>Response Status 200</b> - JSONs que contienen a los bebedores que tengan el nombre o el apellido correspondiente<br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@GET
	@Path( "/populares" )
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes( { MediaType.APPLICATION_JSON } )
	public Response get_20_propuestas_mas_populares( ){

		try{
			AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );
			List<Populares> pops;
			pops = tm.Propuestas_Populares();

			return Response.status( 200 ).entity( pops ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}










}


