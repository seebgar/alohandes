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
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import dao.DAOBebedor;
import vos.Bebedor;

/**
 * @author Santiago Cortes Fernandez 	- 	s.cortes@uniandes.edu.co
 * @author Juan David Vega Guzman		-	jd.vega11@uniandes.edu.co
 * 
 * Clase que representa al Manejador de Transacciones de la Aplicacion (Fachada en patron singleton de la aplicacion)
 * Responsabilidades de la clase: 
 * 		Intermediario entre los servicios REST de la aplicacion y la comunicacion con la Base de Datos
 * 		Modelar y manejar autonomamente las transacciones y las reglas de negocio.
 * 
 * 
 * 
 * 
 * 
 * 
 * En este componente se validan las reglas de negocio, se implementa la 
 * lógica de la aplicación, se administran los recursos y se crea la conexión con
 *  la base de datos para acceder a la información requerida. Por lo anterior, es 
 *  posible evidenciar que este componente es esencial debido a que se encarga 
 *  de procesar todas las peticiones que llegan.
 *  
 *  
 *  
 *  
 *  (*) Téngase en cuenta que, de acuerdo a la vista presentada, solamente debe existir 
 *  un Manejador de Transacciones dentro de la estructura del proyecto.
 *  
 *  
 *  
 */
public class ParranderosTransactionManager {

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
	
	/**
	 * Constatne que representa el numero maximo de Bebedores que pueden haber en una ciudad
	 */
	private final static Integer CANTIDAD_MAXIMA = 345;

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
	public ParranderosTransactionManager(String contextPathP) {
		
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

		FileInputStream fileInputStream = new FileInputStream(new File(ParranderosTransactionManager.CONNECTION_DATA_PATH));
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
		System.out.println("[PARRANDEROS APP] Attempting Connection to: " + url + " - By User: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS TRANSACCIONALES
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metodo que modela la transaccion que retorna todos los bebedores de la base de datos. <br/>
	 * @return List<Bebedor> - Lista de bebedores que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Bebedor> getAllBebedores() throws Exception {
		DAOBebedor daoBebedor = new DAOBebedor();
		List<Bebedor> bebedores;
		try 
		{
			this.conn = darConexion();
			daoBebedor.setConn(conn);
			
			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			bebedores = daoBebedor.getBebedores();
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
				daoBebedor.cerrarRecursos();
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
		return bebedores;
	}
	
	/**
	 * Metodo que modela la transaccion que busca el bebedor en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del bebedor a buscar. id != null
	 * @return Bebedor - Bebedor que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Bebedor getBebedorById(Long id) throws Exception {
		DAOBebedor daoBebedor = new DAOBebedor();
		Bebedor bebedor = null;
		try 
		{
			this.conn = darConexion();
			daoBebedor.setConn(conn);
			bebedor = daoBebedor.findBebedorById(id);
			if(bebedor == null)
			{
				throw new Exception("El bebedor con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
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
				daoBebedor.cerrarRecursos();
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
		return bebedor;
	}
	
	/**
	 * Metodo que modela la transaccion que busca en la base de datos el/los bebedores que son de la ciudad y tienen el presupuesto dados por parametro. <br/>
	 * @param ciudad - Ciudad de los bebedores a buscar. ciudad != null
	 * @param presupuesto - Presupuesto de los bebedores a buscar. presupuesto != null
	 * @return List<Bebedor> - Lista de bebedores que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Bebedor> getBebedoresByCiudadAndPresupuesto(String ciudad, String presupuesto) throws Exception {		
		DAOBebedor daoBebedor = new DAOBebedor();
		List<Bebedor> bebedores;
		try 
		{
			this.conn = darConexion();
			daoBebedor.setConn(conn);
			bebedores = daoBebedor.getBebedoresByCiudadAndPresupuesto(ciudad, presupuesto);
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
				daoBebedor.cerrarRecursos();
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
		return bebedores;
	}
	

	/**
	 * Metodo que modela la transaccion que agrega un bebedor a la base de datos. <br/>
	 * <b> post: </b> se ha agregado el bebedor que entra como parametro <br/>
	 * @param bebedor - el bebedor a agregar. bebedor != null
	 * @throws Exception - Cualquier error que se genere agregando el bebedor
	 */
	public void addBebedor(Bebedor bebedor) throws Exception 
	{
		
		DAOBebedor daoBebedor = new DAOBebedor( );
		try
		{
			//TODO Requerimiento 3D: Obtenga la conexion a la Base de Datos (revise los metodos de la clase)

			//TODO Requerimiento 3E: Establezca la conexion en el objeto DAOBebedor (revise los metodos de la clase DAOBebedor)

			this.conn = darConexion();
			daoBebedor.setConn(conn);
			
			daoBebedor.addBebedor(bebedor);

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
				daoBebedor.cerrarRecursos();
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
	 * Metodo que modela la transaccion que agrega un bebedor a la base de datos  <br/>
	 * unicamente si el n�mero de bebedores que existen en su ciudad es menor la constante CANTIDAD_MAXIMA <br/>
	 * <b> post: </b> Si se cumple la condicion, se ha agregado el bebedor que entra como parametro  <br/>
	 * @param bebedor - el bebedor a agregar. bebedor != null
	 * @param cantidadMaxima -representa la cantidad maxima de bebedores que pueden haber en la misma ciudad
	 * @throws Exception - Cualquier error que se genere agregando el bebedor
	 */
	public void addBebedorWithLimitations(Bebedor bebedor) throws Exception 
	{
		DAOBebedor daoBebedor = new DAOBebedor( );
		try
		{
			//TODO Requerimiento 4B: Obtenga la conexion a la Base de Datos (revise los metodos de la clase)
			this.conn = darConexion();

			//TODO Requerimiento 4C: Establezca la conexion del DaoBebedor a la Base de datos (revise los metodos de DAOBebedor)
			daoBebedor.setConn(conn);

			
			//TODO Requerimiento 4C: Verifique la regla de negocio descrita en la documentacion. En caso que no se cumpla, lance una excepcion explicando lo sucedido
			//						 (Solo se agrega el bebedor si la cantidad de bebedores, en la Base de Datos, de su misma ciudad es inferior al valor de la constante CANTIDAD_MAXIMA.
			
			if ( daoBebedor.getCountBebedoresByCiudad(bebedor.getCiudad()) >= CANTIDAD_MAXIMA ) 
				throw new Exception("El bebedor con el id = " + bebedor.getId() + " no puede ser agregado pues la ciudad " + bebedor.getCiudad() + " supera la capacidad macima de " + CANTIDAD_MAXIMA);
			else
				daoBebedor.addBebedor(bebedor);

			
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
				daoBebedor.cerrarRecursos();
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
	 * Metodo que modela la transaccion que actualiza en la base de datos al bebedor que entra por parametro.<br/>
	 * Solamente se actualiza si existe el bebedor en la Base de Datos <br/>
	 * <b> post: </b> se ha actualizado el bebedor que entra como parametro <br/>
	 * @param bebedor - Bebedor a actualizar. bebedor != null
	 * @throws Exception - Cualquier error que se genere actualizando al bebedor.
	 */
	public void updateBebedor(Bebedor bebedor) throws Exception 
	{
		DAOBebedor daoBebedor = new DAOBebedor( );
		try
		{
			this.conn = darConexion();
			daoBebedor.setConn( conn );
			//TODO Requerimiento 5C: Utilizando los Metodos de DaoBebedor, verifique que exista el bebedor con el ID dado en el parametro. 
			//						 Si no existe un bebedor con el ID ingresado, lance una excepcion en donde se explique lo sucedido
			//						 De lo contrario, se actualiza la informacion del bebedor de la Base de Datos
			if ( this.getBebedorById(bebedor.getId()) == null )
				throw new Exception("El bebedor con el id = " + bebedor.getId() + " no se encuentra persistido en la base de datos.");
			else
				daoBebedor.updateBebedor(bebedor);


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
				daoBebedor.cerrarRecursos();
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
	 * Metodo que modela la transaccion que elimina de la base de datos al bebedor que entra por parametro. <br/>
	 * Solamente se actualiza si existe el bebedor en la Base de Datos <br/>
	 * <b> post: </b> se ha eliminado el bebedor que entra por parametro <br/>
	 * @param Bebedor - bebedor a eliminar. bebedor != null
	 * @throws Exception - Cualquier error que se genere eliminando al bebedor.
	 */
	public void deleteBebedor(Bebedor bebedor) throws Exception 
	{
		DAOBebedor daoBebedor = new DAOBebedor( );
		try
		{
			this.conn = darConexion();
			daoBebedor.setConn( conn );
			//TODO Requerimiento 6D: Utilizando los Metodos de DaoBebedor, verifique que exista el bebedor con el ID dado en el parametro. 
			//						 Si no existe un bebedor con el ID ingresado, lance una excepcion en donde se explique lo sucedido
			//						 De lo contrario, se elimina la informacion del bebedor de la Base de Datos
			if ( this.getBebedorById(bebedor.getId()) == null )
				throw new Exception("El bebedor con el id = " + bebedor.getId() + " no se encuentra persistido en la base de datos.");
			else 
				daoBebedor.deleteBebedor(bebedor);
			


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
				daoBebedor.cerrarRecursos();
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

}
