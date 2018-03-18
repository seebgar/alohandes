/**-------------------------------------------------------------------
 * ISIS2304 - Sistemas Transaccionales
 * Departamento de Ingenieria de Sistemas
 * Universidad de los Andes
 * Bogota, Colombia
 * 
 * Actividad: Tutorial Parranderos: Arquitectura
 * Autores:
 * 			Santiago Cortes Fernandez	-	s.cortes@uniandes.edu.co
 * 			Juan David Vega Guzman		-	jd.vega11@uniandes.edu.co
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

/**
 * @author Santiago Cortes Fernandez 	- 	s.cortes@uniandes.edu.co
 * @author Juan David Vega Guzman		-	jd.vega11@uniandes.edu.co
 * 
 * Clase que expone servicios REST con ruta base: 
 * http://localhost:8080/TutorialParranderos/rest/bebedores/...
 */
@Path("bebedores")
public class BebedoresService {

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
	 * Metodo GET que trae a todos los bebedores en la Base de datos. <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/bebedores <br/>
	 * @return	<b>Response Status 200</b> - JSON que contiene a todos los bebedores que estan en la Base de Datos <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */			
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getBebedores() {

		try {
			AlohandesTransactionManager tm = new AlohandesTransactionManager(getPath());

			List<Bebedor> bebedores;
			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			bebedores = tm.getAllBebedores();
			return Response.status(200).entity(bebedores).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	/**
	 * Metodo GET que trae al bebedor en la Base de Datos con el ID dado por parametro <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/bebedores/{id} <br/>
	 * @return	<b>Response Status 200</b> - JSON Bebedor que contiene al bebedor cuyo ID corresponda al parametro <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getBebedorById( @PathParam( "id" ) Long id )
	{
		try{
			AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );

			Bebedor bebedor = tm.getBebedorById( id );
			return Response.status( 200 ).entity( bebedor ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que trae a los bebedores de la Base de Datos que viven en la ciudad y tienen el presupuesto dados por par�metro <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/bebedores/filterBy?nombre=---&apellido=--- <br/>
	 * @param ciudad - <em>[QueryParam]</em> parametro que indica la ciudad de los bebedores
	 * @param presupuesto - <em>[QueryParam]</em> parametro que indica el presupuesto de los bebedores
	 * @return	<b>Response Status 200</b> - JSONs que contienen a los bebedores que tengan el nombre o el apellido correspondiente<br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	//TODO Requerimiento 2A: Identifique e implemente la anotacion correcta para la realizacion del metodo
	@GET
	@Path( "/query" )
	//TODO Requerimiento 2B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce y/o consume el metodo 
	@Produces( { MediaType.APPLICATION_JSON } )
	@Consumes( { MediaType.APPLICATION_JSON } )
	//TODO Requerimiento 2C: Complete la signatura del metodo (parametros) a partir de la documentacion dada.
	public Response getBebedoresByCiudadAndPresupuesto(@QueryParam("ciudad")String ciudad, @QueryParam("presupuesto")String presupuesto){

		try{
			AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );
			List<Bebedor>bebedores;

			//TODO Requerimiento 2D: Llame al metodo del ParranderosTransactionManager que retorne el resultado esperado a partir de los criterios establecidos     
			bebedores = tm.getBebedoresByCiudadAndPresupuesto(ciudad, presupuesto);

			return Response.status( 200 ).entity( bebedores ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que recibe un bebedor en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al bebedor. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/bebedores <br/>
	 * @param bebedor JSON con la informacion del bebedor que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al bebedor que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	//TODO Requerimiento 3A: Identifique e implemente la anotacion correcta para la realizacion del metodo

	@POST
	//TODO Requerimiento 3B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })

	public Response addBebedor(Bebedor bebedor) {

		//TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
		try{
			AlohandesTransactionManager tm = new AlohandesTransactionManager( getPath( ) );
			tm.addBebedor(bebedor);
			return Response.status( 200 ).entity( bebedor ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo POST que recibe un bebedor en formato JSON y lo agrega a la Base de Datos unicamente 
	 * si el n�mero de bebedores que existen en su ciudad es menor la constante CANTIDAD_MAXIMA <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al bebedor. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/bebedores <br/>
	 * @param cantidadMaxima representa la cantidad maxima de bebedores que pueden haber en la misma ciudad
	 * @return	<b>Response Status 200</b> - JSON que contiene al bebedor que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@POST
	@Path( "restriccionCantidad" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBebedorWithLimitations(Bebedor bebedor) {

		//TODO Requerimiento 4A: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 

		try {
			AlohandesTransactionManager tm = new AlohandesTransactionManager(getPath());
			tm.addBebedorWithLimitations(bebedor);
			return Response.status( 200 ).entity(bebedor).build();
		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}

	}



	/**
	 * Metodo que recibe un bebedor en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se actualiza la Base de datos con la informacion correspondiente al bebedor.<br/>
	 * @param bebedor JSON con la informacion del bebedor que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al bebedor que se desea modificar <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	//TODO Requerimiento 5A: Identifique e implemente la anotacion correcta para la realizacion del metodo
	@PUT
	//TODO Requerimiento 5B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response updateBebedor(Bebedor bebedor) {
		//TODO Requerimiento 5B: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 

		try {
			AlohandesTransactionManager tm = new AlohandesTransactionManager(getPath());
			tm.updateBebedor(bebedor);
			return Response.status( 200 ).entity(bebedor).build();
		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}


	}

	/**
	 * Metodo que recibe un bebedor en formato JSON y lo elimina de la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se elimina de la Base de datos al bebedor con la informacion correspondiente.<br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/bebedores <br/>
	 * @param bebedor JSON con la informacion del bebedor que se desea eliminar
	 * @return	<b>Response Status 200</b> - JSON que contiene al bebedor que se desea eliminar <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	//TODO Requerimiento 6A: Identifique e implemente la anotacion correcta para la realizacion del metodo

	//TODO Requerimiento 6B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response deleteBebedor(Bebedor bebedor) {
		//TODO Requerimiento 6C: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
		try {
			AlohandesTransactionManager tm = new AlohandesTransactionManager(getPath());
			tm.deleteBebedor(bebedor);
			return Response.status( 200 ).entity(bebedor).build();
		} catch (Exception e) {
			return Response.status( 500 ).entity(doErrorMessage(e)).build();
		}
	}

}


