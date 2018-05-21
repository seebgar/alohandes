/**-------------------------------------------------------------------
 * ISIS2304 - Sistemas Transaccionales
 * Departamento de Ingenieria de Sistemas
 * Universidad de los Andes
 * Bogota, Colombia
 * 
 * Iteracion 1
 * -------------------------------------------------------------------
 */
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import dao.DAOPersona;
import dao.DAOReserva;
import vos.AnalisisPropuesta;
import vos.BuenosClientes;
import vos.ClienteFrecuente;
import vos.Colectivo;
import vos.DineroOperador;
import vos.Filtro;
import vos.Funcionamiento;
import vos.Indice;
import vos.Persona;
import vos.Populares;
import vos.Propuesta;
import vos.Reserva;

/**
 * 
 * Clase que representa al Manejador de Transacciones de la Aplicacion (Fachada en patron singleton de la aplicacion)
 * Responsabilidades de la clase: 
 * 		Intermediario entre los servicios REST de la aplicacion y la comunicacion con la Base de Datos
 * 		Modelar y manejar autonomamente las transacciones y las reglas de negocio.

 * En este componente se validan las reglas de negocio, se implementa la 
 * loÌ�gica de la aplicacioÌ�n, se administran los recursos y se crea la conexioÌ�n con
 *  la base de datos para acceder a la informacioÌ�n requerida. Por lo anterior, es 
 *  posible evidenciar que este componente es esencial debido a que se encarga 
 *  de procesar todas las peticiones que llegan.
 * 
 *  (*) TeÌ�ngase en cuenta que, de acuerdo a la vista presentada, solamente debe existir 
 *  un Manejador de Transacciones dentro de la estructura del proyecto.
 *  
 */
public class AlohandesTransactionManager {

	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private static String CONNECTION_DATA_PATH;


	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * Atributo que representa la conexion a la base de datos
	 */
	private Connection conn;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE CONEXION E INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * <b>Metodo Contructor de la Clase ParranderosTransactionManager</b> <br/>
	 * <b>Postcondicion: </b>	Se crea un objeto  ParranderosTransactionManager,
	 * 						 	Se inicializa el path absoluto del archivo de conexion,
	 * 							Se inicializna los atributos para la conexion con la Base de Datos
	 * @param contextPathP Path absoluto que se encuentra en el servidor del contexto del deploy actual
	 * @throws IOException Se genera una excepcion al tener dificultades con la inicializacion de la conexion<br/>
	 * @throws ClassNotFoundException 
	 */
	public AlohandesTransactionManager(String contextPathP) {

		try {
			CONNECTION_DATA_PATH = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
			initializeConnectionData();
		} 
		catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo encargado de inicializar los atributos utilizados para la conexion con la Base de Datos.<br/>
	 * <b>post: </b> Se inicializan los atributos para la conexion<br/>
	 * @throws IOException Se genera una excepcion al no encontrar el archivo o al tener dificultades durante su lectura<br/>
	 * @throws ClassNotFoundException 
	 */
	private void initializeConnectionData() throws IOException, ClassNotFoundException {

		FileInputStream fileInputStream = new FileInputStream(new File(AlohandesTransactionManager.CONNECTION_DATA_PATH));
		Properties properties = new Properties();

		properties.load(fileInputStream);
		fileInputStream.close();

		this.url = properties.getProperty("url");
		this.user = properties.getProperty("usuario");
		this.password = properties.getProperty("clave");
		this.driver = properties.getProperty("driver");

		//Class.forName(driver);
	}

	/**
	 * Metodo encargado de generar una conexion con la Base de Datos.<br/>
	 * <b>Precondicion: </b>Los atributos para la conexion con la Base de Datos han sido inicializados<br/>
	 * @return Objeto Connection, el cual hace referencia a la conexion a la base de datos
	 * @throws SQLException Cualquier error que se pueda llegar a generar durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("[ALOHANDES APP] Attempting Connection to: " + url + " - By User: " + user);
		return DriverManager.getConnection(url, user, password);
	}



	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS TRANSACCIONALES
	//----------------------------------------------------------------------------------------------------------------------------------





	/**
	 * Metodo que modela la transaccion que retorna todos LOS OPERADORES de la base de datos. <br/>
	 * @return List<Bebedor> - Lista de operadores que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Persona> getAllOperadores() throws Exception {
		DAOPersona dao = new DAOPersona();
		List<Persona> operadores;
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);

			operadores = dao.getOperadores();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return operadores;
	}



	/**
	 * Metodo que modela la transaccion que retorna todos LOS CLIENTES de la base de datos. <br/>
	 * @return List<Bebedor> - Lista de operadores que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Persona> getAllClientes() throws Exception {
		DAOPersona dao = new DAOPersona();
		List<Persona> clientes;
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);

			clientes = dao.getClientes();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes;
	}


	/**
	 * Metodo que modela la transaccion que retorna todas las propuestas de la base de datos. <br/>
	 * @return List<Bebedor> - Lista de propuestas que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Propuesta> getAllPropuestas() throws Exception {
		DAOPersona dao = new DAOPersona();
		List<Propuesta> props;
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);

			props = dao.getPropuestas();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return props;
	}



	/**
	 * Metodo que modela la transaccion que busca la persona en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id de la persona a buscar. id != null
	 * @return Persona - Persona que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Persona getPersonaById(Long id) throws Exception {
		DAOPersona dao = new DAOPersona();
		Persona persona = null;
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			persona = dao.findPersonaById(id);
			if(persona == null)
				throw new Exception("La persona con el id = " + id + " no se encuentra persistido en la base de datos.");				
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return persona;
	}




	/**
	 * Metodo que modela la transaccion que busca en la base de datos la/las personas que
	 * son de TIPO = {estudiante, registrado, empleado, profesor, padre, invitado, empresa o egresado} <br/>
	 * @param tipo Tipo de persona {estudiante, registrado, empleado, profesor, padre, invitado, empresa o egresado}
	 * @return List<Bebedor> - Lista de personas que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Persona> getPersonasByTipo(String tipo) throws Exception {		
		DAOPersona dao = new DAOPersona();
		List<Persona> personas;
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			personas = dao.getPersonas_Por_Tipo(tipo);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return personas;
	}





	/**
	 * REQUERIMIENTO 1 - 3
	 * TODO
	 * 
	 * 
	 * 
	 * Metodo que modela la transaccion que agrega una persona a la base de datos. <br/>
	 * <b> post: </b> se ha agregado la persona que entra como parametro <br/>
	 * @param persona - la persona a agregar. persona != null
	 * @throws Exception - Cualquier error que se genere agregando el bebedor
	 */
	public void addPersona( Persona persona ) throws Exception 
	{

		DAOPersona dao = new DAOPersona();
		try
		{
			this.conn = darConexion();
			dao.setConn(conn);

			dao.addPersona(persona);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}





	/**
	 * REQUERIMIENTO 2
	 * TODO
	 * 
	 * 
	 * 
	 * 
	 * Metodo que modela la transaccion que agrega una propuesta a la base de datos. <br/>
	 * <b> pre: </b> se ha agregado la persona que entra como parametro <br/>
	 * <b> pos: </b> se agrega la propuesta que entra como parametro <br/>
	 * @param persona - la persona a agregar la propuesta. persona != null
	 * @param propuesta - Propuesta ha ser agregada.
	 * @throws Exception - Cualquier error que se genere agregando el bebedor
	 */
	public void addPropuesta( Propuesta propuesta ) throws Exception 
	{

		DAOPersona dao = new DAOPersona();
		try
		{
			this.conn = darConexion();
			dao.setConn(conn);

			dao.addPropuesta( propuesta);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}






	/**
	 * Metodo que modela la transaccion que actualiza en la base de datos a la persona que entra por parametro.<br/>
	 * Solamente se actualiza si existe la persona en la Base de Datos <br/>
	 * <b> post: </b> se ha actualizado la persona que entra como parametro <br/>
	 * @param persona - Persona a actualizar. persona != null
	 * @throws Exception - Cualquier error que se genere actualizando a la persona.
	 */
	public void updatePersona( Persona persona ) throws Exception 
	{
		DAOPersona dao = new DAOPersona();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			if ( this.getPersonaById(persona.getId()) == null )
				throw new Exception("El bebedor con el id = " + persona.getId() + " no se encuentra persistido en la base de datos.");
			else
				dao.updatePersona(persona);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}




	/**
	 * Metodo que modela la transaccion que elimina de la base de datos a la persona que entra por parametro. <br/>
	 * <b> post: </b> se ha eliminado a la persona que entra por parametro <br/>
	 * @param Persona - persona a eliminar. persona != null
	 * @throws Exception - Cualquier error que se genere eliminando al bebedor.
	 */
	public void deletePersona ( Persona persona ) throws Exception 
	{
		DAOPersona dao = new DAOPersona( );
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			if ( this.getPersonaById(persona.getId()) == null )
				throw new Exception("El bebedor con el id = " + persona.getId() + " no se encuentra persistido en la base de datos.");
			else 
				dao.deletePersona(persona);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}


	/**
	 * 
	 * @param persona
	 * @throws Exception
	 */
	public void deletePersona_byId (Long id ) throws Exception 
	{
		DAOPersona dao = new DAOPersona( );
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			if ( this.getPersonaById(id) == null )
				throw new Exception("El bebedor con el id = " + id + " no se encuentra persistido en la base de datos.");
			else 
				dao.deletePersona_byId(id);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}


	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Reserva getReservaById(Long id) throws Exception{

		DAOReserva dao= new DAOReserva();
		Reserva reserva= null;
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			reserva = dao.getReservaById(id);
			if(reserva == null)
				throw new Exception("La reserva con el id = " + id + " no se encuentra persistido en la base de datos.");				
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return reserva;
	}

	/**
	 * RF 4
	 * TODO
	 * 
	 * metodo que registra la reserva en la base de datos
	 * @param reserva
	 * @throws Exception
	 */
	public void registrarReserva(Reserva reserva) throws Exception {

		DAOReserva dao= new DAOReserva();

		try {
			this.conn= darConexion();
			dao.setConn(conn);
			dao.registrarReserva(reserva);
		}catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		}catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * RF 5
	 * TODO
	 * 
	 * 
	 * @param reserva
	 * @throws Exception
	 */
	public void cancelarReserva(Reserva reserva) throws Exception{

		DAOReserva dao= new DAOReserva();

		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			if ( this.getReservaById(reserva.getId())== null )
				throw new Exception("La reserva con el id = " + reserva.getId() + " no se encuentra persistida en la base de datos.");
			else
				dao.cancelarReserva(reserva);
		}catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * lO MISMO PERO POR ID
	 * @param reserva
	 * @throws Exception
	 */
	public void cancelarReserva_porid ( Long id ) throws Exception{

		DAOReserva dao= new DAOReserva();

		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			if ( this.getReservaById(id)== null )
				throw new Exception("La reserva con el id = " + id + " no se encuentra persistida en la base de datos.");
			else
				dao.cancelarReserva(this.getReservaById(id));
		}catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public Propuesta getPropuestaById(Long id) throws Exception{

		DAOPersona dao= new DAOPersona();
		Propuesta propuesta= null;
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			propuesta = dao.getPropuestaById(id);
			if(propuesta == null)
				throw new Exception("La propuesta con el id = " + id + " no se encuentra persistido en la base de datos.");				
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return propuesta;
	}


	/**
	 * RF 6
	 * TODO
	 * 
	 * 
	 * 
	 * @param propuesta
	 * @throws Exception
	 */
	public void retirarPropuesta(Propuesta propuesta) throws Exception {

		DAOPersona dao = new DAOPersona( );
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			if(this.getPropuestaById(propuesta.getId()) == null)
				throw new Exception("La propuesta con el id = " + propuesta.getId() + " no se encuentra persistido en la base de datos.");
			else
				dao.retirarPropuesta(propuesta);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Mismo pero por id
	 * @param propuesta
	 * @throws Exception
	 */
	public void retirarPropuesta_byid(Long id) throws Exception {

		DAOPersona dao = new DAOPersona( );
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			if(this.getPropuestaById(id) == null)
				throw new Exception("La propuesta con el id = " + id + " no se encuentra persistido en la base de datos.");
			else
				dao.retirarPropuesta_porId(id);
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// REQUERIMIENTOS FUNCIONALES DE CONSULTA
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * RFC 1
	 * TODO
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DineroOperador> dinero_por_operador() throws Exception 
	{
		DAOPersona dao = new DAOPersona( );
		List<DineroOperador> ss = new ArrayList<>();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			ss = dao._dinero_recibido();

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	

		return ss;
	}


	/**
	 * RFC 2
	 * TODO
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Populares> Propuestas_Populares() throws Exception 
	{
		DAOPersona dao = new DAOPersona( );
		List<Populares> ss = new ArrayList<>();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			ss = dao._20_ofertas_mas_populares();

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	

		return ss;
	}




	/**
	 * RFC 3
	 * TODO
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Indice> indices_ocupacion() throws Exception 
	{
		DAOPersona dao = new DAOPersona( );
		List<Indice> ss = new ArrayList<>();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			ss = dao.get_indice_ocupacion();

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	

		return ss;
	}


	/**
	 * RFC 4
	 * TODO
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Filtro> filtros() throws Exception 
	{
		DAOPersona dao = new DAOPersona( );
		List<Filtro> ss = new ArrayList<>();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			ss = dao.get_filtros_bono();

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	

		return ss;
	}









	//----------------------------------------------------------------------------------------------------------------------------------
	// PRIVACIDAD
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * Rettorna las reservas de una perosna por id
	 * @return
	 * @throws Exception
	 */
	public List<Reserva> get_Reservas_Cliente_PorID( Long id_persona ) throws Exception 
	{
		DAOReserva dao = new DAOReserva( );
		List<Reserva> ss = new ArrayList<>();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			ss = dao.get_Reservas_Cliente_PorID(id_persona);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	

		return ss;
	}



	/**
	 * Retorna las propuestas de un operador por id
	 * @param id_persona
	 * @return
	 * @throws Exception
	 */
	public List<Propuesta> get_Propuestas_Operador_PorID( Long id_persona ) throws Exception 
	{
		DAOPersona dao = new DAOPersona( );
		List<Propuesta> ss = new ArrayList<>();
		try
		{
			this.conn = darConexion();
			dao.setConn( conn );
			ss = dao.get_Porpuestas_Operador_PorID(id_persona);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	

		return ss;
	}









	//----------------------------------------------------------------------------------------------------------------------------------
	// ITERACION 2
	//----------------------------------------------------------------------------------------------------------------------------------




	/**
	 * RF 7
	 * 
	 * Registra una reserva de forma colectiva
	 * @param reserva_colectiva
	 * @return
	 * @throws Exception
	 */
	public List<Reserva> RF7_registrar_reserva_colectiva( Colectivo reserva_colectiva ) throws Exception{

		DAOReserva dao= new DAOReserva();
		List<Reserva> reservas = new ArrayList<>();
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			reservas = dao.RF7_registrar_reserva_colectiva(reserva_colectiva);	
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION RF7] General Exception: \n" + exception.getMessage() + " \n " + exception.getCause());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return reservas;
	}







	/**
	 * Metodo que permite cancelar una reserva colectiva (RF8)
	 * @param  id_colectivo_reserva id de la reserva colectiva que se quiere cancelar.
	 * 
	 */
	public void RF8_cancelarReservaColectiva(Long  id_colectivo_reserva )throws Exception 
	{
		DAOReserva dao= new DAOReserva();
		try
		{
			System.out.println("Ejecutando operacion");
			this.conn = darConexion();
			dao.setConn( conn );
			dao.cancelarReservaColectiva(id_colectivo_reserva);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}		





	}



	/**
	 * Metodo que permite deshabilitar una oferta de alojamiento (RF9)
	 * @param id
	 * @return
	 * @throws Exception
	 */


	public List<Reserva> deshabilitarOfertaDeAlojamiento(	Long id) throws Exception  {
		DAOReserva dao= new DAOReserva();
		try
		{
			System.out.println("Ejecutando operacion");
			this.conn = darConexion();
			dao.setConn( conn );
			return dao.RF9_deshabilitar_propuesta(id);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close(); 
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}

		}

	}

	/** Metodo que permite habilitar una propuesta (RF10)
	 * @throws SQLException,Exception
	 */
	public void rehabilitarOfertaDeAlojamiento(Long id) throws SQLException, Exception 
	{
		DAOReserva dao= new DAOReserva();
		try
		{
			System.out.println("Ejecutando operacion");
			this.conn = darConexion();
			dao.setConn( conn );
			dao.rehabilitarOfertaDeAlojamineto(id);

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close(); 
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}

		}

	}

	public List<Reserva> obtenerTodasLasReservasColectivas() throws Exception  {
		DAOReserva dao= new DAOReserva();
		try
		{
			System.out.println("Ejecutando operacion");
			this.conn = darConexion();
			dao.setConn( conn );
			return dao.getReservasColectivas();

		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close(); 
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}

		}

	}














	//----------------------------------------------------------------------------------------------------------------------------------
	// REQUERIMIENTOS DE CONSULTA ITERACION 2
	//----------------------------------------------------------------------------------------------------------------------------------




	/**
	 * RC7
	 * 
	 * @param filtro
	 * @param tiempo
	 * @param tipo_alojamiento
	 * @return
	 * @throws Exception
	 */
	public List<AnalisisPropuesta> RC7_analisis_propuestas( String filtro, String tiempo, String tipo_alojamiento) throws Exception {
		DAOPersona dao= new DAOPersona();
		List<AnalisisPropuesta> ps = new ArrayList<>();
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			ps = dao.RC7_analisis_propuestas(filtro, tiempo, tipo_alojamiento)	;
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION RF7] General Exception: \n" + exception.getMessage() + " \n " + exception.getCause());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ps;
	}

	/**
	 * RC8
	 * 
	 * @param filtro
	 * @param tiempo
	 * @param tipo_alojamiento
	 * @return
	 * @throws Exception
	 */
	public List<ClienteFrecuente> RC8_clientes_frecuentes ( String tipo_alojamiento ) throws Exception {
		DAOPersona dao= new DAOPersona();
		List<ClienteFrecuente> ps = new ArrayList<>();
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			ps = dao.RC8_clientes_frecuentes(tipo_alojamiento)	;
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION RF7] General Exception: \n" + exception.getMessage() + " \n " + exception.getCause());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ps;
	}


	/**
	 * RC9
	 * 
	 * @param filtro
	 * @param tiempo
	 * @param tipo_alojamiento
	 * @return
	 * @throws Exception
	 */
	public List<Propuesta> RC9_poca_demanda ( ) throws Exception {
		DAOPersona dao= new DAOPersona();
		List<Propuesta> ps = new ArrayList<>();
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			ps = dao.RC9_poca_demanda()	;
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION RF7] General Exception: \n" + exception.getMessage() + " \n " + exception.getCause());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ps;
	}



	public List<Reserva> getReservasColectivaPorId(Long idPersona) throws Exception
	{
		DAOReserva dao = new DAOReserva();
		List<Reserva> ps = new ArrayList<>();
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			ps = dao.getReservaColectivaPorPersona(idPersona);
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION RF7] General Exception: \n" + exception.getMessage() + " \n " + exception.getCause());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ps;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// ITERACION 3
	//----------------------------------------------------------------------------------------------------------------------------------

	
	/**
	 * RFC10 - CONSULTAR CONSUMO EN ALOHANDES
	 * 
	 * Se quiere conocer la información de los usuarios que realizaron al menos una reserva de una determinada
	 * oferta de alojamiento en un rango de fechas. Los resultados deben ser clasificados según un criterio deseado
	 * por quien realiza la consulta. En la clasificación debe ofrecerse la posibilidad de agrupamiento y ordenamiento
	 * de las respuestas según los intereses del usuario que consulta como, por ejemplo, por los datos del cliente, por
	 * oferta de alojamiento y por tipo de alojamiento.

	 * @param id_propuesta Long identificador de la propuesta que se piensa analizar
	 * @param fecha_inicial String fecha inicial formato YYYY-MM-DD
	 * @param fecha_final String fecha final formato YYY-MM-DD
	 * @param tipo_ordenamiento String pertenece a { inmueble | id_persona  }
	 * @return
	 */
	public List<Persona> RFC10_consumo_admina( Long id_propuesta, String fecha_inicial, String fecha_final, String tipo_ordenamiento ) throws Exception {

		DAOPersona dao= new DAOPersona();
		List<Persona> personas = new ArrayList<>();
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			personas = dao.RFC10_consumo_admin(id_propuesta, fecha_inicial, fecha_final, tipo_ordenamiento);
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION RF7] General Exception: \n" + exception.getMessage() + " \n " + exception.getCause());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return personas;
	}
	
	public List<Persona> RFC10_consumo_user ( Long id_usuario, Long id_propuesta, String fecha_inicial, String fecha_final, String tipo_ordenamiento ) throws Exception {

		DAOPersona dao= new DAOPersona();
		List<Persona> personas = new ArrayList<>();
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			personas = dao.RFC10_consumo_user(id_usuario, id_propuesta, fecha_inicial, fecha_final, tipo_ordenamiento);
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION RF7] General Exception: \n" + exception.getMessage() + " \n " + exception.getCause());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return personas;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * *RFC11 - CONSULTAR CONSUMO EN ALOHANDES – RFC10-V2
	 *
	 *Se quiere conocer la información de los usuarios QUE NO realizaron al menos una reserva de una determinada
	 *oferta de alojamiento en un rango de fechas. En la clasificación debe ofrecerse la posibilidad de agrupamiento
	 *y ordenamiento de las respuestas según los intereses del usuario q
	 *
	 * @param id_propuesta
	 * @param fecha_inicial
	 * @param fecha_final
	 * @param tipo_ordenamiento
	 * @return
	 * @throws BusinessLogicException
	 * @throws Exception
	 * @throws SQLException
	 */
	public List<Persona> RFC11_inverso_consumo_admin ( Long id_propuesta, String fecha_inicial, String fecha_final, String tipo_ordenamiento )  throws Exception {

		DAOPersona dao= new DAOPersona();
		List<Persona> personas = new ArrayList<>();
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			personas = dao.RFC11_inverso_consumo_admin(id_propuesta, fecha_inicial, fecha_final, tipo_ordenamiento);
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION RF7] General Exception: \n" + exception.getMessage() + " \n " + exception.getCause());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return personas;
	}
	
	public List<Persona> RFC11_inverso_consumo_user ( Long id_usuario, Long id_propuesta, String fecha_inicial, String fecha_final, String tipo_ordenamiento ) throws Exception {
		DAOPersona dao= new DAOPersona();
		List<Persona> personas = new ArrayList<>();
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			personas = dao.RFC11_inverso_consumo_user(id_usuario, id_propuesta, fecha_inicial, fecha_final, tipo_ordenamiento);
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION RF7] General Exception: \n" + exception.getMessage() + " \n " + exception.getCause());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return personas;
	}
	

	public Funcionamiento RFC12funcionamiento(String tipo,String orden) throws SQLException
	{
		DAOPersona dao= new DAOPersona();
		Funcionamiento rta = new Funcionamiento();
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			System.out.println(tipo +""+ orden);
			rta = dao.RFC12_funcionamiento(tipo, orden);
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION R13] General Exception: \n" + exception.getMessage() + " \n " + exception.getCause());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rta;
		
	}
	
	
	public BuenosClientes RFC13darBuenosClientes() throws SQLException
	{
		DAOPersona dao= new DAOPersona();
		BuenosClientes rta = new BuenosClientes();
		try 
		{
			this.conn = darConexion();
			dao.setConn(conn);
			rta = dao.RFC13_buenosClientes();
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION R13] General Exception: \n" + exception.getMessage() + " \n " + exception.getCause());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rta;
		
	}
	
	
	






}
