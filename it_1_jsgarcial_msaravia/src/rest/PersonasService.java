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
import vos.Persona;

/**
 * Clase que expone servicios REST con ruta base: 
 * http://localhost:8080/TutorialParranderos/rest/personas/...
 * 
 * @author sebastian
 *
 */
@Path("personas")
public class PersonasService {

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
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOperadores() {

		try {
			AlohandesTransactionManager tm = new AlohandesTransactionManager(getPath());
			List<Persona> operadores;
			operadores = tm.getAllOperadores();
			return Response.status(200).entity(operadores).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	/**
	 * Metodo GET que trae a la persona en la Base de Datos con el ID dado por parametro <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/personaes/{id} <br/>
	 * @return	<b>Response Status 200</b> - JSON  que contiene a la persona cuyo ID corresponda al parametro <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getPersonaById( @PathParam( "id" ) Long id )
	{
		try{
			AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );

			Persona persona = tm.getPersonaById(id);
			return Response.status( 200 ).entity( persona ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que trae a las personas de la Base de Datos que son del tipo por parametro <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/personas/filterBy?tipo=---&apellido=--- <br/>
	 * @param ciudad - <em>[QueryParam]</em> parametro que indica la ciudad de los personaes
	 * @param presupuesto - <em>[QueryParam]</em> parametro que indica el presupuesto de los personaes
	 * @return	<b>Response Status 200</b> - JSONs que contienen a los personaes que tengan el nombre o el apellido correspondiente<br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@GET
	@Path( "/query" )
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes( { MediaType.APPLICATION_JSON } )
	public Response getPersonasByTipo(@QueryParam("tipo")String tipo){

		try{
			AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );

			List<Persona> personas;
			personas = tm.getPersonasByTipo(tipo);

			return Response.status( 200 ).entity( personas ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que recibe una persona en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente a LA PERSONA. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/personas <br/>
	 * @param persona JSON con la informacion del persona que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al persona que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })

	public Response addPersona(Persona persona) {

		try{
			AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );
			tm.addPersona(persona);
			return Response.status( 200 ).entity( persona ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}



	/**
	 * Metodo que recibe un persona en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se actualiza la Base de datos con la informacion correspondiente al persona.<br/>
	 * @param persona JSON con la informacion del persona que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al persona que se desea modificar <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePersona( Persona persona) {
		try {
			AlohandesTransactionManager tm = new AlohandesTransactionManager(getPath());
			tm.updatePersona(persona);
			return Response.status( 200 ).entity(persona).build();
		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}


	}

	/**
	 * Metodo que recibe un persona en formato JSON y lo elimina de la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se elimina de la Base de datos al persona con la informacion correspondiente.<br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/personaes <br/>
	 * @param persona JSON con la informacion del persona que se desea eliminar
	 * @return	<b>Response Status 200</b> - JSON que contiene al persona que se desea eliminar <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePersona( Persona persona) {
		try {
			AlohandesTransactionManager tm = new AlohandesTransactionManager(getPath());
			tm.deletePersona(persona);
			return Response.status( 200 ).entity(persona).build();
		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}
	}

}


