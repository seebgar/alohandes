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
package dao;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.*;

/**
 * @author Santiago Cortes Fernandez 	- 	s.cortes@uniandes.edu.co
 * @author Juan David Vega Guzman		-	jd.vega11@uniandes.edu.co
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicacion
 * 
 * 
 * 
 * 
 * 
 * Data Access Object (DAO)
 * Por medio de la conexión que se crea en el Transaction Manager, este componente ejecuta las distintas 
 * sentencias SQL, recibe la información correspondiente y se encarga de transformar tales resultados 
 * (ResultSets) en objetos que se manipulan posteriormente para atender las peticiones según sea el caso.
 * 
 * 
 * 
 * 
 * 
 */
public class DAOBebedor {

	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Constante para indicar el usuario Oracle del estudiante
	 */
	//TODO Requerimiento 1H: Modifique la constante, reemplazando al ususario PARRANDEROS por su ususario de Oracle
	public final static String USUARIO = "ISIS2304A491810";
	
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase DAOBebedor <br/>
	*/
	public DAOBebedor() {
		recursos = new ArrayList<Object>();
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que obtiene la informacion de todos los bebedores en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los bebedores que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Bebedor> getBebedores() throws SQLException, Exception {
		ArrayList<Bebedor> bebedores = new ArrayList<Bebedor>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format("SELECT * FROM %1$s.BEBEDORES WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			bebedores.add(convertResultSetToBebedor(rs));
		}
		return bebedores;
	}
	
	/**
	 * Metodo que obtiene la informacion de todos los bebedores en la Base de Datos que se encuentran en la ciudad y con el presupuesto dados por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @param ciudad ciudad en la que se encuentran los bebedores
	 * @param presupuesto presupuesto de los bebedores
	 * @return lista con la informacion de todos los bebedores que se encuentran en la Base de Datos que cumplen con los criterios de la sentencia SQL
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */

	public ArrayList<Bebedor> getBebedoresByCiudadAndPresupuesto(String ciudad, String presupuesto) throws SQLException, Exception{
		ArrayList<Bebedor> bebedores = new ArrayList<Bebedor>();
		
		String sql = String.format("SELECT * FROM %1$s.BEBEDORES WHERE CIUDAD = '%2$s' AND PRESUPUESTO = '%3$s'", USUARIO, ciudad, presupuesto);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			bebedores.add(convertResultSetToBebedor(rs));
		}
		return bebedores;
	}

	/**
	 * Metodo que obtiene la informacion del bebedor en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del bebedor
	 * @return la informacion del bebedor que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el bebedor conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Bebedor findBebedorById(Long id) throws SQLException, Exception 
	{
		Bebedor bebedor = null;

		String sql = String.format("SELECT * FROM %1$s.BEBEDORES WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			bebedor = convertResultSetToBebedor(rs);
		}

		return bebedor;
	}
	
	/**
	 * Metodo que agregar la informacion de un nuevo bebedor en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addBebedor(Bebedor bebedor) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.BEBEDORES (ID, NOMBRE, PRESUPUESTO, CIUDAD) VALUES (%2$s, '%3$s', '%4$s', '%5$s')", 
									USUARIO, 
									bebedor.getId(), 
									bebedor.getNombre(),
									bebedor.getPresupuesto(), 
									bebedor.getCiudad());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza la informacion del bebedor en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateBebedor(Bebedor bebedor) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.BEBEDORES SET ", USUARIO));
		sql.append(String.format("NOMBRE = '%1$s' AND CIUDAD = '%2$s' AND PRESUPUESTO = '%3$s' ", bebedor.getNombre(), bebedor.getCiudad(), bebedor.getPresupuesto()));
		
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que actualiza la informacion del bebedor en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteBebedor(Bebedor bebedor) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.BEBEDORES WHERE ID = %2$d", USUARIO, bebedor.getId());

		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Metodo que obtiene la cantidad de bebedores de una ciudad especifica, dada por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param ciudad ciudad que se desea saber la cantidad de bebedores
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public double getCountBebedoresByCiudad(String ciudad) throws SQLException, Exception
	{		
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("SELECT COUNT(*) AS CANTIDAD_CIUDAD ", USUARIO));
		sql.append(String.format("FROM %s.BEBEDORES ", USUARIO));
		sql.append(String.format("WHERE CIUDAD = '%s' ", ciudad));
		sql.append("GROUP BY CIUDAD");
		
		System.out.println(sql.toString());
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);	
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			return rs.getInt("CANTIDAD_CIUDAD");	
		}
		return 0;
		
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS AUXILIARES
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metodo encargado de inicializar la conexion del DAO a la Base de Datos a partir del parametro <br/>
	 * <b>Postcondicion: </b> el atributo conn es inicializado <br/>
	 * @param connection la conexion generada en el TransactionManager para la comunicacion con la Base de Datos
	 */
	public void setConn(Connection connection){
		this.conn = connection;
	}
	
	/**
	 * Metodo que cierra todos los recursos que se encuentran en el arreglo de recursos<br/>
	 * <b>Postcondicion: </b> Todos los recurso del arreglo de recursos han sido cerrados.
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}
	
	/**
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla BEBEDORES) en una instancia de la clase Bebedor.
	 * @param resultSet ResultSet con la informacion de un bebedor que se obtuvo de la base de datos.
	 * @return Bebedor cuyos atributos corresponden a los valores asociados a un registro particular de la tabla BEBEDORES.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Bebedor convertResultSetToBebedor(ResultSet resultSet) throws SQLException {
		//TODO Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Bebedor. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)

		long id = resultSet.getLong("ID");
		String nombre = resultSet.getString("NOMBRE");
		String presupuesto = resultSet.getString("PRESUPUESTO");
		String ciudad = resultSet.getString("CIUDAD");
		
		Bebedor beb = new Bebedor(id, nombre, presupuesto, ciudad);

		return beb;
	}

}
