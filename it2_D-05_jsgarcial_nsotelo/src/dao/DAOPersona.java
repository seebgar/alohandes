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

import dao.DAOReserva;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sun.javafx.beans.IDProperty;
import com.sun.media.sound.PortMixerProvider;

//import jdk.jshell.spi.ExecutionControl.ExecutionControlException;
import tm.BusinessLogicException;
import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicacion
 * 
 * Data Access Object (DAO)
 * Por medio de la conexioÌ�n que se crea en el Transaction Manager, este componente ejecuta las distintas 
 * sentencias SQL, recibe la informacioÌ�n correspondiente y se encarga de transformar tales resultados 
 * (ResultSets) en objetos que se manipulan posteriormente para atender las peticiones seguÌ�n sea el caso.
 * 
 * 
 */
public class DAOPersona {


	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante para indicar el usuario Oracle del estudiante
	 */
	public final static String USUARIO = "ISIS2304A331810";


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
	 * Metodo constructor de la clase DAOPersona <br/>
	 */
	public DAOPersona() {
		recursos = new ArrayList<Object>();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo que obtiene la informacion de todos los operadores en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los operadores que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Persona> getOperadores() throws SQLException, Exception {

		ArrayList<Persona> operadores = new ArrayList<Persona>();

		String sql = String.format("SELECT * FROM %1$s.PERSONAS WHERE ROL = 'operador'", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			operadores.add(convertResultSetTo_Persona(rs));
		}
		return operadores;
	}

	/**
	 * Metodo que obtiene la informacion de todos los clientes en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los clientes que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Persona> getClientes() throws SQLException, Exception {

		ArrayList<Persona> clientes = new ArrayList<Persona>();

		String sql = String.format("SELECT * FROM %1$s.PERSONAS WHERE ROL = 'cliente'", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			clientes.add(convertResultSetTo_Persona(rs));
		}
		return clientes;
	}


	/**
	 * Metodo que obtiene la informacion de todos las propuestas en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los clientes que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Propuesta> getPropuestas() throws SQLException, Exception {

		ArrayList<Propuesta> props = new ArrayList<Propuesta>();

		String sql = String.format("SELECT * FROM %1$s.PROPUESTAS", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			props.add(convertResultSetTo_Propuesta(rs));
		}
		return props;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Propuesta getPropuestaById(Long id) throws SQLException, Exception {

		Propuesta propuesta = null;

		System.out.println( id + " >>>>>>>>>>>>>>>>>> ES EL ID DE LA PROPUESTA SE SE PIENSA BUSCAR >>>>>>>>>>>>>>>>>>>>>>>>");
		String sql = "SELECT * FROM PROPUESTAS p WHERE p.ID = " + id; 
		System.out.println(sql + "\n <<<<<<<<<<<<<<<<<<< sql busqueda de propuesta >>>>>>>>>>>>>>>>>>>>>");

		PreparedStatement mt = conn.prepareStatement(sql);
		recursos.add(mt);
		ResultSet rs = mt.executeQuery();

		System.out.println(" <<<<<<<<<<<<<<<<<<< ahora se va a hacer la covercion de json a objeto propuesta >>>>>>>>>>>>>>>>>>>>>");
		if(rs.next()) {
			propuesta = convertResultSetTo_Propuesta(rs);
		}
		System.out.println(propuesta + " <<<<<<<<<<<<<<<<<<< ES LA PROPUESTA >>>>>>>>>>>>>>>>>>>>>");
		return propuesta;
	}


	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Persona get_Persona_ById(Long id) throws SQLException, Exception {

		Persona per = null;

		String sql = String.format("SELECT * FROM %1$s.PERSONAS WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			per = convertResultSetTo_Persona(rs);
		}

		return per;
	}



	/**
	 * Metodo que obtiene la informacion de todos LAS PERSONAS en la Base de Datos que son de 
	 * TIPO = {estudiante, registrado, empleado, profesor, padre, invitado, empresa}
	 * dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @param tipo de la persona que se quiere buscar = {estudiante, registrado, empleado, profesor, padre, invitado, empresa}
	 * @return lista con la informacion de todos las personas que se encuentran en la Base de Datos que cumplen con los criterios de la sentencia SQL
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */

	public ArrayList<Persona> getPersonas_Por_Tipo ( String tipo ) throws SQLException, Exception{

		ArrayList<Persona> personas = new ArrayList<Persona>();

		String sql = String.format("SELECT * FROM %1$s.PERSONAS WHERE TIPO = '%2$s'", USUARIO, tipo);
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			personas.add(convertResultSetTo_Persona(rs));
		}
		return personas;
	}

	/**
	 * Metodo que obtiene la informacion del bebedor en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador de la persona
	 * @return la informacion de la persona que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe la persona con los criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Persona findPersonaById ( Long id ) throws SQLException, Exception 
	{
		Persona pep = null;

		String sql = String.format("SELECT * FROM %1$s.PERSONAS WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			pep = convertResultSetTo_Persona(rs);
		}

		return pep;
	}

	/**
	 * Null si la propuesta no existe
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Propuesta find_Propuestas_ById ( Long id ) throws SQLException, Exception 
	{
		Propuesta pep = null;

		String sql = String.format("SELECT * FROM %1$s.PROPUESTAS WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			pep = convertResultSetTo_Propuesta(rs);
		}

		return pep;
	}

	/**
	 * RF-1
	 * RF-3
	 * 
	 * 
	 * Metodo que agregar la informacion de un nuevo persona en la Base de Datos a partir del parametro ingresado<br/>
	 * Se define el rol de la persona {cliente, operador}
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addPersona (Persona persona) throws SQLException, Exception {

		if ( persona == null )
			throw new BusinessLogicException("La persona que se intenta persistir es null");

		String sql = 
				String.format(
						"INSERT INTO %1$s.PERSONAS (ID, NOMBRE, APELLIDO, CEDULA, TIPO, NIT, ROL) "
								+ "VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s', '%7$s', '%8$s' )", 
								USUARIO, 
								persona.getId(), 
								persona.getNombre(),
								persona.getApellido(), 
								persona.getCedula(),
								persona.getTipo(),
								persona.getNit(),
								persona.getRol()
						);
		System.out.println( " >>>>>>>>>>>>>>> ADD PERSONA :: \t" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		if ( persona.getRol().equalsIgnoreCase("cliente") ) {
			System.out.println("Se ha registrado correctamente un Cliente a la base de datos.");
		} else if ( persona.getRol().equalsIgnoreCase("operador") )
			System.out.println("Se ha registrado correctamente un Operador a la base de datos.");
		else
			System.out.println("Se registro pero no se que es");

	}


	/**
	 * RF-2
	 * 
	 * 
	 * Agrega la informacion de una propuestas a la base de datos
	 *  <b>Precondicion: </b> la conexion ha sido inicializada y la propuesta no puede existir sin una persona operador que la maneje <br/>  
	 * @param persona
	 * @param propuesta
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addPropuesta ( Propuesta propuesta ) throws SQLException, Exception, BusinessLogicException {

		if ( propuesta == null )
			throw new BusinessLogicException("La propuesta que se intentan persistir son nulls");

		Persona operador = this.findPersonaById((long)propuesta.getId_persona());
		if ( operador == null ) {
			System.out.println(String.format("Actualmente la persona con id = {%1$s} no esta registrada. Debe estar registrado como operador y estar relacionado con la universidad para poder registrar una propuesta.", 
					propuesta.getId_persona()));
			return;
		}

		// ESTA PARTE ASEGURA QUE LA PERSONA QUE ESTA HCIENDO LA PROPUESTA, SEA UN OPERADOR RELACIONADO A LA UNIVERSIDAD
		try {
			operador.addPropuesta(propuesta);
		} catch ( BusinessLogicException e ) {
			throw new BusinessLogicException(e.getMessage());
		} 

		Object ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION;

		//Verificacion de IDS para conectar el inmueble
		if ( propuesta.getHostel() == null ) ID_HOSTEL = "null";
		else ID_HOSTEL = propuesta.getHostel();

		if ( propuesta.getHotel() == null ) ID_HOTEL = "null";
		else ID_HOTEL = propuesta.getHotel();

		if ( propuesta.getVivienda_express() == null ) ID_VIVIENDA_EXPRESS = "null";
		else ID_VIVIENDA_EXPRESS = propuesta.getVivienda_express();

		if ( propuesta.getApartamento() == null ) ID_APARTAMENTO = "null";
		else ID_APARTAMENTO = propuesta.getApartamento();

		if ( propuesta.getVivienda_universitarias() == null ) ID_VIVIENDA_UNIVERSITARIA = "null";
		else ID_VIVIENDA_UNIVERSITARIA = propuesta.getVivienda_universitarias();

		if ( propuesta.getVivienda_universitarias() == null ) ID_VIVIENDA_UNIVERSITARIA = "null";
		else ID_VIVIENDA_UNIVERSITARIA = propuesta.getVivienda_universitarias();

		if ( propuesta.getHabitacion() == null ) ID_HABITACION = "null";
		else ID_HABITACION = propuesta.getHabitacion();

		Integer DISPONIBLE = propuesta.getDisonible() == true ? 1 : 0;

		Integer CANTIDAD_DIAS_DISPONIBLES = propuesta.getCantidad_dias_disponibles() == null ? 0 : propuesta.getCantidad_dias_disponibles();

		String capacidad_maxima = propuesta.getCapacidad_maxima() == null ? "0" :  propuesta.getCapacidad_maxima().toString();

		String fecha_f_d = propuesta.getFecha_final_disponibilidad() == null ? "null" : propuesta.getFecha_final_disponibilidad();

		// SE VA RETIRAR se inicializa con false en el constructor de propuesta
		// FECHA_INICIO_DISPONIBILIDAD se entiende como la fecha en la que se registra la propuesta. Luego se cambia cuando se acaba una reserva.

		String sql =
				"INSERT INTO PROPUESTAS " + 
						"(ID, TIPO_INMUEBLE, ID_PERSONA, " + 
						"ID_HOTEL, ID_HOSTEL, " + 
						"ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION, " + 
						"SE_VA_RETIRAR,CAPACIDAD_MAXIMA, DISPONIBLE, " + 
						"FECHA_INICIO_DISPONIBILIDAD, " + 
						"FECHA_FINAL_DISPONIBILIDAD, " + 
						"CANTIDAD_DIAS_DISPONIBLE, " + 
						"SUB_TOTAL " + 
						") " + 
						"VALUES ( " + 
						+ propuesta.getId() + ", " +  
						" '" + propuesta.getTipo_inmueble() + "', " + 
						+ propuesta.getId_persona() + ", " + 
						ID_HOTEL + ", " + 
						ID_HOSTEL + ", " + 
						ID_VIVIENDA_EXPRESS + ", " + 
						ID_APARTAMENTO + ", " + 
						ID_VIVIENDA_UNIVERSITARIA + ", " + 
						ID_HABITACION + ", " + 
						"0 , " + 
						capacidad_maxima + ", " + 
						"1, " + 
						"'" + propuesta.getFecha_inicio_disponibilidad() + "', " + 
						"'" + fecha_f_d + "', " + 
						"0, " + 
						+ propuesta.getSub_total() + "  " + 
						")";

		System.out.println(sql);

		PreparedStatement prepStmt_add_propuesta = conn.prepareStatement(sql);
		recursos.add(prepStmt_add_propuesta);
		prepStmt_add_propuesta.executeQuery();

	}


	/**
	 * RF-6
	 * Elimina una Propuestas si es posible
	 * Si tiene reservas solo se cambia su estado de se_va_retirar a true
	 * para que no acepte más reservas
	 * 
	 * @param propuesta
	 * @throws SQLException
	 * @throws Exception
	 * @throws BusinessLogicException
	 */
	public void retirarPropuesta(Propuesta propuesta)throws SQLException, Exception, BusinessLogicException {

		if ( propuesta == null )
			throw new BusinessLogicException("La propuesta que se piensa retirar es nula.");

		String sql = String.format("SELECT * FROM %1$s.RESERVAS R WHERE R.ID_PROPUESTA = %2$d AND ROWNUM = 1 ORDER BY R.FECHA_INICIO_ESTADIA DESC", USUARIO, propuesta.getId());
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		Reserva res = null;

		if( rs.next() ) {
			res = convertResultSetTo_Reserva(rs);
		}

		if ( res == null ) {
			System.out.println("La propuesta no tiene reservas vigentes. Se puede eliminar de forma inmediata.");
			String delete = String.format("DELETE FROM %1$s.PROPUESTAS P WHERE P.ID = %2$d", USUARIO, propuesta.getId());
			System.out.println(delete);
			PreparedStatement prepStmt_del = conn.prepareStatement(delete);
			recursos.add(prepStmt_del);
			prepStmt_del.executeQuery();
		} else {
			propuesta.setSeVaRetirar(true);
			String update = String.format("UPDATE %1$s.PROPUESTAS P SET P.SE_VA_RETIRAR = 1 WHERE P.ID = %2$d", USUARIO, propuesta.getId());
			System.out.println(update);
			PreparedStatement prepStmt_up = conn.prepareStatement(update);
			recursos.add(prepStmt_up);
			prepStmt_up.executeQuery();
			System.out.println("La propuesta cuenta con reservas vigentes. Por lo que tiene que esperar a que se acabe la ultima reserva el { que inicia el " + res.getFecha_inicio_estadia() + " y tiene una duracion de " + res.getDuracion() + " dias } para poder eliminar esta propuesta");
		}

	}

	/**
	 * Mismo metodo pero con propuesta por id
	 * @param propuesta
	 * @throws SQLException
	 * @throws Exception
	 * @throws BusinessLogicException
	 */
	public void retirarPropuesta_porId(Long id)throws SQLException, Exception, BusinessLogicException {

		if ( this.find_Propuestas_ById(id) == null )
			throw new BusinessLogicException("La propuesta que se piensa retirar es nula.");

		String sql = String.format("SELECT * FROM %1$s.RESERVAS R WHERE R.ID_PROPUESTA = %2$d AND ROWNUM = 1 ORDER BY R.FECHA_INICIO_ESTADIA DESC", USUARIO, id);
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		Reserva res = null;

		if( rs.next() ) {
			res = convertResultSetTo_Reserva(rs);
		}

		if ( res == null ) {
			System.out.println("La propuesta no tiene reservas vigentes. Se puede eliminar de forma inmediata.");
			String delete = String.format("DELETE FROM %1$s.PROPUESTAS P WHERE P.ID = %2$d", USUARIO, id);
			System.out.println(delete);
			PreparedStatement prepStmt_del = conn.prepareStatement(delete);
			recursos.add(prepStmt_del);
			prepStmt_del.executeQuery();
		} else {
			this.find_Propuestas_ById(id).setSeVaRetirar(true);
			String update = String.format("UPDATE %1$s.PROPUESTAS P SET P.SE_VA_RETIRAR = 1 WHERE P.ID = %2$d", USUARIO, id);
			System.out.println(update);
			PreparedStatement prepStmt_up = conn.prepareStatement(update);
			recursos.add(prepStmt_up);
			prepStmt_up.executeQuery();
			System.out.println("La propuesta cuenta con reservas vigentes. Por lo que tiene que esperar a que se acabe la ultima reserva el { que inicia el " + res.getFecha_inicio_estadia() + " y tiene una duracion de " + res.getDuracion() + " dias } para poder eliminar esta propuesta");
		}

	}

	/**
	 * Metodo que actualiza la informacion de LA PERSONA en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param persona Persona que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updatePersona ( Persona persona ) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append(String.format("UPDATE %s.PERSONAS SET ", USUARIO));
		sql.append(String.format("NOMBRE = '%1$s' AND APELLIDO = '%2$s' AND CEDULA = '%3$s' "
				+ "AND TIPO = '%4$s' AND NIT = '%5$s' AND ROL = '%6$s' AND EMAIL = '%7$s'",
				persona.getNombre(), 
				persona.getApellido(), 
				persona.getCedula(),
				persona.getTipo(),
				persona.getNit(),
				persona.getRol(),
				persona.getEmail()
				));

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina una Persona de la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param persona Persona que desea eliminar de la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deletePersona ( Persona persona ) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.PERSONAS P WHERE P.ID = %2$d", USUARIO, persona.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deletePersona_byId ( Long id ) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.PERSONAS P WHERE P.ID = %2$d", USUARIO, id);

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla PERSONAS) en una instancia de la clase PERSONA.
	 * @param resultSet ResultSet con la informacion de un bebedor que se obtuvo de la base de datos.
	 * @return Persona cuyos atributos corresponden a los valores asociados a un registro particular de la tabla PERSONAS.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Persona convertResultSetTo_Persona(ResultSet resultSet) throws SQLException {
		//Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos 
		//(ID, NOMBRE, APELLIDO, TIPO, CEDULA, ROL, NIT, EMAIL)

		long id = resultSet.getLong("ID");
		String nombre = resultSet.getString("NOMBRE");
		String apellido = resultSet.getString("APELLIDO");
		String tipo = resultSet.getString("TIPO");
		String rol = resultSet.getString("ROL");
		String cedula = resultSet.getString("CEDULA");
		String nit = resultSet.getString("NIT");
		String email = resultSet.getString("EMAIL");

		Persona pep = new Persona(id, nombre, apellido, tipo, rol, nit, cedula, email);

		return pep;
	}

	public Propuesta convertResultSetTo_Propuesta(ResultSet resultSet) throws SQLException {

		long id = resultSet.getLong("ID");
		String tipo_inmueble = resultSet.getString("TIPO_INMUEBLE");
		Integer capacidad = resultSet.getInt("CAPACIDAD_MAXIMA");
		Integer id_persona = resultSet.getInt("ID_PERSONA");
		//TODOO
		Boolean disponible =resultSet.getInt("DISPONIBLE")==1?true : false ;
		String fecha_inicio_disponibilidad=resultSet.getString("FECHA_INICIO_DISPONIBILIDAD");
		String fecha_fin_disponibilidad=resultSet.getString("FECHA_FINAL_DISPONIBILIDAD");
		Integer dias_disponibilidad=resultSet.getInt("CANTIDAD_DIAS_DISPONIBLE");
		Double sub_total = resultSet.getDouble("SUB_TOTAL");


		Propuesta prop = new Propuesta(id, tipo_inmueble, capacidad, id_persona,dias_disponibilidad,fecha_inicio_disponibilidad,fecha_fin_disponibilidad,disponible, sub_total);

		if ( Propuesta.TIPO_INMUEBLE.APARTAMENTO.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.APARTAMENTOS WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_APARTAMENTO"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setApartamento(rs.getLong("ID"));
			}
		} 

		if ( Propuesta.TIPO_INMUEBLE.HABITACION.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.HABITACIONES WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_HABITACION"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setHabitacion( rs.getLong("ID") );
			}
		}

		if ( Propuesta.TIPO_INMUEBLE.HOSTEL.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.HOSTELES WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_HOSTEL"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setHostel( rs.getLong("ID") );
			}
		}

		if ( Propuesta.TIPO_INMUEBLE.HOTEL.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.HOTELES WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_HOTEL"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setHotel(rs.getLong("ID") );
			}
		}

		if ( Propuesta.TIPO_INMUEBLE.VIVIENDA_EXPRESS.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.VIVIENDAS_EXPRESS WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_VIVIENDA_EXPRESS"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setVivienda_express( rs.getLong("ID") );
			}
		}

		if ( Propuesta.TIPO_INMUEBLE.VIVIENDA_UNIVERSITARIA.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.VIVIENDAS_UNIVERSITARIAS WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_VIVIENDA_UNIVERSITARIA"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setVivienda_universitarias( rs.getLong("ID"));
			}
		}

		int retiro= resultSet.getInt("SE_VA_RETIRAR");
		Boolean seVaRetirar= (retiro == 1)? true: false;
		prop.setSeVaRetirar(seVaRetirar);


		return prop;
	}





















	//----------------------------------------------------------------------------------------------------------------------------------
	// REQUERIMIENTOS FUNCIONALES DE CONSULTA
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * RFC 1
	 * Retorna el dinero recibido por cada operador
	 * 
	 * 
	 * EL DINERO RECIBIDO POR CADA PROVEEDOR DE ALOJAMIENTO DURANTE EL AÑO ACTUAL Y EL AÑO CORRIDO
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<DineroOperador> _dinero_recibido () throws SQLException, Exception {

		String sql = "SELECT PP.*, ASW.ID_PROPUESTA, asw.\"TOTAL GANANCIAS\" FROM (\n" + 
				"\n" + 
				"SELECT RE.ID_PROPUESTA AS \"ID_PROPUESTA\", SUM(RE.COSTO_TOTAL) AS \"TOTAL GANANCIAS\"\n" + 
				"FROM RESERVAS RE\n" + 
				"WHERE RE.ID_PROPUESTA IN (\n" + 
				"    SELECT PT.ID\n" + 
				"    FROM PROPUESTAS PT \n" + 
				"    WHERE PT.ID_PERSONA IN (\n" + 
				"        SELECT PEP.ID  \n" + 
				"        FROM PERSONAS PEP \n" + 
				"        WHERE ROL = 'operador'\n" + 
				"    )\n" + 
				")\n" + 
				"GROUP BY RE.ID_PROPUESTA\n" + 
				") ASW\n" + 
				"INNER JOIN PROPUESTAS PU\n" + 
				"ON PU.ID = ASW.ID_PROPUESTA\n" + 
				"\n" + 
				"INNER JOIN PERSONAS PP\n" + 
				"ON PP.ID = PU.ID_PERSONA\n" + 
				"\n" + 
				"ORDER BY asw.\"TOTAL GANANCIAS\" DESC\n";


		ArrayList<DineroOperador> pagos = new ArrayList<>();
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			DineroOperador dd = new DineroOperador(rs.getLong("ID"),rs.getString("NOMBRE") , rs.getString("APELLIDO"), rs.getLong("ID_PROPUESTA"), rs.getFloat("TOTAL GANANCIAS"));
			pagos.add(dd);
		}
		return pagos;
	}

	/**
	 * RFC2
	 * Retorna las 20 ofertas mas populares
	 * 
	 * 
	 * Retorna las 20 orfertas mÃ¡s populares 
	 * @return
	 */
	public List<Populares> _20_ofertas_mas_populares ()  throws SQLException, Exception {

		String sql =String.format( "SELECT  *\r\n" + 
				"FROM \r\n" + 
				"( SELECT ID_PROPUESTA, COUNT(ID_PROPUESTA) AS \"Cantidad Reservas\"  \r\n" + 
				"FROM RESERVAS \r\n" + 
				"GROUP BY ID_PROPUESTA\r\n" + 
				"ORDER BY \"Cantidad Reservas\" DESC)\r\n" + 
				"WHERE ROWNUM <= 20");

		//String sql = "SELECT * FROM " + USUARIO + ".RESERVAS  ";


		ArrayList<Populares> populares = new ArrayList<>();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();


		while (rs.next()) {
			Populares pp = new Populares(rs.getLong("ID_PROPUESTA"), rs.getInt("Cantidad Reservas"));

			populares.add(pp);
		}
		return populares;

	}




	//----------------------------------------------------------------------------------------------------------------------------------
	// BONO
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * RFC 3
	 * indice de ocupacion
	 */
	public List<Indice> get_indice_ocupacion() throws SQLException {
		String sql = "SELECT R.ID_PROPUESTA, SUM( R.CANTIDAD_PERSONAS), SUM(P.CAPACIDAD_MAXIMA), ( SUM( R.CANTIDAD_PERSONAS) / SUM(P.CAPACIDAD_MAXIMA) ) AS \"INDICE\"\r\n" + 
				"\r\n" + 
				"	FROM RESERVAS R\r\n" + 
				"	INNER JOIN PROPUESTAS P \r\n" + 
				"	ON R.ID_PROPUESTA = P.ID\r\n" + 
				"\r\n" + 
				"	GROUP BY R.ID_PROPUESTA\r\n" + 
				"";

		ArrayList<Indice> ins = new ArrayList<>();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();


		while (rs.next()) {

			Indice i = new Indice(rs.getLong("ID_PROPUESTA"), rs.getInt("SUM(R.CANTIDAD_PERSONAS)"), rs.getInt("SUM(P.CAPACIDAD_MAXIMA)"), rs.getFloat("INDICE"));

			ins.add(i);
		}
		return ins;


	}


	/**
	 * rfc 4
	 * alohamientos con un filtro
	 * @return
	 * @throws SQLException
	 */
	public List<Filtro> get_filtros_bono() throws SQLException {

		String sql = "SELECT R.ID_PROPUESTA, R.FECHA_INICIO_ESTADIA, R.DURACION_CONTRATO,\r\n" + 
				"	P.ID_APARTAMENTO, P.ID_VIVIENDA_EXPRESS, P.ID_VIVIENDA_UNIVERSITARIA, P.ID_HABITACION\r\n" + 
				"\r\n" + 
				"	FROM RESERVAS R\r\n" + 
				"	INNER JOIN PROPUESTAS P\r\n" + 
				"	ON R.ID_PROPUESTA = P.ID\r\n" + 
				"\r\n" + 
				"	WHERE \r\n" + 
				"	R.FECHA_INICIO_ESTADIA BETWEEN '2018-01-19 21:48:01' AND '2018-01-25 21:48:01'\r\n" + 
				"	    AND ( P.ID_HABITACION IN (\r\n" + 
				"	        SELECT S.ID_HABITACION FROM SERVICIOS_BASICOS S INNER JOIN TIPOS T ON T.ID = S.ID_TIPO\r\n" + 
				"	        WHERE T.NOMBRE = 'baño' OR T.NOMBRE = 'cocina'\r\n" + 
				"	    )\r\n" + 
				"	    OR P.ID_APARTAMENTO IN (\r\n" + 
				"	         SELECT S.ID_APARTAMENTO FROM SERVICIOS_BASICOS S INNER JOIN TIPOS T ON T.ID = S.ID_TIPO\r\n" + 
				"	        WHERE T.NOMBRE = 'baño' OR T.NOMBRE = 'cocina'\r\n" + 
				"	    )\r\n" + 
				"	    OR P.ID_VIVIENDA_EXPRESS IN (\r\n" + 
				"	         SELECT S.ID_VIVIENDA_EXPRESS FROM SERVICIOS_BASICOS S INNER JOIN TIPOS T ON T.ID = S.ID_TIPO\r\n" + 
				"	        WHERE T.NOMBRE = 'baño' OR T.NOMBRE = 'cocina'\r\n" + 
				"	    )\r\n" + 
				"	    OR P.ID_VIVIENDA_UNIVERSITARIA IN (\r\n" + 
				"	         SELECT S.ID_VIVIENDA_UNIVERSITARIA FROM SERVICIOS_BASICOS S INNER JOIN TIPOS T ON T.ID = S.ID_TIPO\r\n" + 
				"	        WHERE T.NOMBRE = 'baño' OR T.NOMBRE = 'cocina'\r\n" + 
				"	    )\r\n" + 
				"	   \r\n" + 
				"	)";


		ArrayList<Filtro> fs = new ArrayList<>();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();


		while (rs.next()) {

			Filtro f = new Filtro(rs.getLong("ID_PROPUESTA"), rs.getString("FECHA_INICIO_ESTADIA"),
					rs.getInt("DURACION_CONTRATO"), rs.getLong("ID_APARTAMENTO"), rs.getLong("ID_VIVIENDA_EXPRESS"), rs.getLong("ID_VIVIENDA_UNIVERSITARIA"),
					rs.getLong("ID_HABITACION"));

			fs.add(f);
		}
		return fs;

	}








	/**
	 * Convierte un SQL a VO Reserva
	 * @param resultSet
	 * @return
	 * @throws Exception 
	 */
	public Reserva convertResultSetTo_Reserva ( ResultSet resultSet ) throws Exception {

		Integer id, duracion_contrato, cantidad_personas, hay_multa;
		Long id_persona, id_propuesta,id_colectivo;
		String fecha_registro, fecha_cancelacion, fecha_inicio_estadia;
		float costo_total, valor_multa; 

		id = resultSet.getInt("ID");
		fecha_registro = resultSet.getString("FECHA_REGISTRO");
		fecha_cancelacion = resultSet.getString("FECHA_CANCELACION");
		fecha_inicio_estadia = resultSet.getString("FECHA_INICIO_ESTADIA");
		duracion_contrato = resultSet.getInt("DURACION_CONTRATO");
		costo_total = resultSet.getFloat("COSTO_TOTAL");
		cantidad_personas = resultSet.getInt("CANTIDAD_PERSONAS");
		hay_multa = resultSet.getInt("HAY_MULTA");
		valor_multa = resultSet.getFloat("VALOR_MULTA");

		id_propuesta = resultSet.getLong("ID_PROPUESTA");
		id_persona = resultSet.getLong("ID_PERSONA");
		id_colectivo = resultSet.getLong("ID_COLECTIVO");


		Reserva res = new Reserva((long)id, fecha_registro, fecha_cancelacion, fecha_inicio_estadia, duracion_contrato, (double)costo_total, 
				cantidad_personas, hay_multa == 0 ? false : true, (double)valor_multa,
						id_propuesta, id_persona,id_colectivo);


		return res;

	}






	//----------------------------------------------------------------------------------------------------------------------------------
	// PROVACIDAD
	//----------------------------------------------------------------------------------------------------------------------------------




	/**
	 * Retorna las propuestas de un operador por id 
	 * @param id_cliente
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Propuesta> get_Porpuestas_Operador_PorID ( Long id_operador) throws SQLException, Exception {

		ArrayList<Propuesta> props = new ArrayList<>();

		String sql = " SELECT * FROM " + USUARIO + ".PROPUESTAS P WHERE P.ID_PERSONA = " + id_operador;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet set = prepStmt.executeQuery();

		while (set.next()) {
			props.add(this.convertResultSetTo_Propuesta(set));
		}

		return props;

	}
























	//----------------------------------------------------------------------------------------------------------------------------------
	// ITERACION 2
	// SISTEMAS TRANSACCIONALES
	//----------------------------------------------------------------------------------------------------------------------------------













	//----------------------------------------------------------------------------------------------------------------------------------
	// REQUERIMIENTOS DE CONSULTA
	//----------------------------------------------------------------------------------------------------------------------------------




	/**
	 * RFC7 - ANALIZAR LA OPERACIÓN DE ALOHANDES 
	 * 
	 * Para una unidad de tiempo definido (por ejemplo, semana o mes) y un tipo de alojamiento, considerando todo
	 * el tiempo de operación de AloHandes, indicar cuáles fueron las fechas de mayor demanda (mayor cantidad de
	 * alojamientos ocupados), las de mayores ingresos (mayor cantidad de dinero recibido) y las de menor ocupación.
	 * @param filtro { mayor | ingresos | menor } = Mayor ocupacion, mayores ingresos o menor ocupacion.
	 * @param tiempo { semana | mes } = String que especifica si se trata de las semanaas o de los meses
	 * @param tipo_alojamiento { Apartamento | Hotel | Hostel | Vivienda Universitaria | Vivienda Express | Habitacion }
	 * @return Lista de las 
	 * @throws BusinessLogicException, SQLException, Exception 
	 */
	public List<AnalisisPropuesta> RC7_analisis_propuestas ( String filtro, String tiempo, String tipo_alojamiento ) throws BusinessLogicException, SQLException, Exception {

		if ( filtro.isEmpty() || tiempo.isEmpty() || tipo_alojamiento.isEmpty() )
			throw new BusinessLogicException("Parametros invalidos : " + filtro + " " + tiempo + " " +tipo_alojamiento);

		if ( filtro.equalsIgnoreCase("mayor") || filtro.equalsIgnoreCase("menor") ) {

			String desc = filtro.equalsIgnoreCase("mayor") ? "DESC" : " ";
			String mayor_ocupacional = "";

			if ( tiempo.equalsIgnoreCase("semana") ) {
				mayor_ocupacional = 
						"SELECT to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW')) AS \"TIEMPO\",  COUNT (to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW'))) AS \"CANTIDAD_RESERVAS\" " + 
								"FROM RESERVAS R " + 
								"INNER JOIN PROPUESTAS P ON " + 
								"R.ID_PROPUESTA = P.ID " + 
								"WHERE UPPER(P.TIPO_INMUEBLE) = UPPER('"+ tipo_alojamiento +"') " + 
								"AND ( R.HAY_MULTA IS NULL " + 
								"OR R.HAY_MULTA = 0 ) " + 
								"GROUP BY to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW')) " + 
								"ORDER BY \"CANTIDAD_RESERVAS\" " + desc + " ";
			} else if ( tiempo.equalsIgnoreCase("mes") ) {
				mayor_ocupacional = 
						"SELECT TO_CHAR(TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'), 'Month') AS \"TIEMPO\", COUNT (TO_CHAR(TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'), 'Month')) AS \"CANTIDAD_RESERVAS\" " + 
								"FROM RESERVAS R " + 
								"INNER JOIN PROPUESTAS P ON " + 
								"R.ID_PROPUESTA = P.ID " + 
								"WHERE UPPER(P.TIPO_INMUEBLE) = UPPER('" + tipo_alojamiento + "') " + 
								"AND ( R.HAY_MULTA IS NULL " + 
								"OR R.HAY_MULTA = 0 ) " + 
								"GROUP BY TO_CHAR(TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'), 'Month') " + 
								"ORDER BY \"CANTIDAD_RESERVAS\" " + desc + " ";
			}


			List<AnalisisPropuesta> apps = new ArrayList<>();

			PreparedStatement prepStmt = conn.prepareStatement(mayor_ocupacional);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			while ( rs.next() ) {
				AnalisisPropuesta ap = new AnalisisPropuesta(rs.getString("TIEMPO"), rs.getDouble("CANTIDAD_RESERVAS"));
				apps.add(ap);
			}

			return apps;
		}


		else if ( filtro.equalsIgnoreCase("ingresos") ) {

			String ingresos = "";

			if ( tiempo.equalsIgnoreCase("semana") ) {

				ingresos = 
						"SELECT to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW')) AS \"TIEMPO\", SUM(R.COSTO_TOTAL) AS \"INGRESOS\" " + 
								"FROM RESERVAS R " + 
								"INNER JOIN PROPUESTAS P ON " + 
								"R.ID_PROPUESTA = P.ID " + 
								"WHERE UPPER(P.TIPO_INMUEBLE) = UPPER('" + tipo_alojamiento + "') " + 
								"AND ( R.HAY_MULTA IS NULL " + 
								"OR R.HAY_MULTA = 0 ) " + 
								"GROUP BY to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW')) " + 
								"ORDER BY \"INGRESOS\" DESC";

			} else if ( tiempo.equalsIgnoreCase("mes") ) {

				ingresos = 
						"SELECT TO_CHAR(TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'), 'Month') AS \"TIEMPO\", SUM(R.COSTO_TOTAL) AS \"INGRESOS\" " + 
								"FROM RESERVAS R " + 
								"INNER JOIN PROPUESTAS P ON " + 
								"R.ID_PROPUESTA = P.ID " + 
								"WHERE UPPER(P.TIPO_INMUEBLE) = UPPER('" + tipo_alojamiento + "') " +
								"AND ( R.HAY_MULTA IS NULL  " + 
								"OR R.HAY_MULTA = 0 ) " + 
								"GROUP BY TO_CHAR(TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'), 'Month') " + 
								"ORDER BY \"INGRESOS\" DESC";
			}

			List<AnalisisPropuesta> apps = new ArrayList<>();

			PreparedStatement prepStmt = conn.prepareStatement(ingresos);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			while ( rs.next() ) {
				AnalisisPropuesta ap = new AnalisisPropuesta(rs.getString("TIEMPO"), rs.getDouble("INGRESOS"));
				apps.add(ap);
			}

			return apps;
		}


		return new ArrayList<>();
	}













	/**
	 * RFC8 - ENCONTRAR LOS CLIENTES FRECUENTES
	 * 
	 * Para un alojamiento dado, encontrar la información de sus clientes frecuentes. se considera frecuente a un
	 * cliente si ha utilizado (o tiene reservado) ese alojamiento por lo menos en tres ocasiones o por lo menos 15
	 * noches, durante todo el periodo de operación de AlohAndes
	 * @param tipo_alojamiento { Apartamento | Hotel | Hostel | Vivienda Universitaria | Vivienda Express | Habitacion }
	 * @return
	 */
	public List<ClienteFrecuente> RC8_clientes_frecuentes( String tipo_alojamiento ) throws BusinessLogicException, Exception, SQLException {

		if ( tipo_alojamiento.isEmpty() )
			throw new BusinessLogicException("Tipo de alojamiento invalido");

		String sql_frecuentes = 
				"SELECT Q.ID_PERSONA, q.\"CANTIDAD_RESERVAS\", P.NOMBRE, P.APELLIDO, P.TIPO FROM " + 
						"( " + 
						"SELECT R.ID_PERSONA, COUNT(R.ID_PERSONA) AS \"CANTIDAD_RESERVAS\" " + 
						"FROM RESERVAS R " + 
						"WHERE R.ID_PROPUESTA IN ( " + 
						"    SELECT P.ID FROM PROPUESTAS P WHERE UPPER(P.TIPO_INMUEBLE) = UPPER('" + tipo_alojamiento + "') " + 
						") " + 
						"AND R.DURACION_CONTRATO >= 15 " + 
						"GROUP BY R.ID_PERSONA " + 
						"HAVING COUNT(R.ID_PERSONA) >= 3 " + 
						"ORDER BY \"CANTIDAD_RESERVAS\" DESC " + 
						") Q " + 
						"JOIN PERSONAS P " + 
						"ON Q.ID_PERSONA = P.ID ";


		List<ClienteFrecuente> cfs = new ArrayList<>();

		PreparedStatement prepStmt = conn.prepareStatement(sql_frecuentes);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while ( rs.next() ) {
			ClienteFrecuente cf = new ClienteFrecuente(rs.getLong("ID_PERSONA"), rs.getInt("CANTIDAD_RESERVAS"), rs.getString("NOMBRE") + " " + rs.getString("APELLIDO"), rs.getString("TIPO"));
			cfs.add(cf);
		}

		return cfs;
	}















	/**
	 * RC 9 - ENCONTRAR LAS OFERTAS DE ALOJAMIENTO QUE NO TIENEN MUCHA DEMANDA
	 * 
	 * Encontrar las ofertas de alojamiento que no han recibido clientes en periodos superiores a 1 mes, durante todo
	 * el periodo de operación de AlohAndes
	 * @return
	 * @throws SQLException 
	 */
	public List<Propuesta> RC9_poca_demanda () throws SQLException {

		String sql = 
				"SELECT * " + 
						"FROM PROPUESTAS P " + 
						"WHERE P.CANTIDAD_DIAS_DISPONIBLE >= 30";

		List<Propuesta> propuestas = new ArrayList<>();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while ( rs.next() ) {
			propuestas.add( convertResultSetTo_Propuesta(rs) );
		}

		return propuestas;
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
	public List<Persona> RFC10_consumo_admin ( Long id_propuesta, String fecha_inicial, String fecha_final, String tipo_ordenamiento ) throws BusinessLogicException, Exception, SQLException {

		if ( id_propuesta == 0 || id_propuesta == null | fecha_final.isEmpty() | fecha_inicial.isEmpty() | tipo_ordenamiento == null )
			throw new BusinessLogicException("Digitar datos correctos :: ID_PROPUESTA = " + id_propuesta + "  Rango = " + fecha_inicial + " -- " + fecha_final );

		String odernamiento = tipo_ordenamiento.equalsIgnoreCase("inmueble") ? "PP.TIPO" : "PER.ID";

		String sql = 
				"SELECT PER.*, PP.TIPO as inmueble " + 
						"FROM PERSONAS PER " + 
						"INNER JOIN ( " + 
						" " + 
						"    SELECT R.ID_PERSONA AS PERSO, P.TIPO_INMUEBLE AS TIPO " + 
						"    FROM RESERVAS R  " + 
						"    INNER JOIN PROPUESTAS P " + 
						"    ON P.ID = R.ID_PROPUESTA " + 
						" " + 
						"    WHERE P.ID = " + id_propuesta + " " + 
						"    AND R.FECHA_INICIO_ESTADIA BETWEEN '" + fecha_inicial + "' AND '"  + fecha_final +  "' " + 
						" " + 
						") " + 
						"PP ON PP.PERSO = PER.ID " + 
						" " + 
						"ORDER BY " + odernamiento;


		List<Persona> personas = new ArrayList<>();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while ( rs.next() ) {
			Persona p = this.convertResultSetTo_Persona(rs);
			p.setInmueble(rs.getString("inmueble"));
			personas.add(p);
		}

		return personas;
	}

	/**
	 * 
	 * @param id_usuario se define el cliente actual que está utilizando la aplicacion
	 * @param id_propuesta
	 * @param fecha_inicial
	 * @param fecha_final
	 * @param tipo_ordenamiento
	 * @return
	 * @throws BusinessLogicException
	 * @throws Exception
	 * @throws SQLException
	 */
	public List<Persona> RFC10_consumo_user ( Long id_usuario, Long id_propuesta, String fecha_inicial, String fecha_final, String tipo_ordenamiento ) throws BusinessLogicException, Exception, SQLException {

		if ( id_propuesta == 0 || id_propuesta == null | fecha_final.isEmpty() | fecha_inicial.isEmpty() | tipo_ordenamiento == null )
			throw new BusinessLogicException("Digitar datos correctos :: ID_PROPUESTA = " + id_propuesta + "  Rango = " + fecha_inicial + " -- " + fecha_final );

		String odernamiento = tipo_ordenamiento.equalsIgnoreCase("inmueble") ? "PP.TIPO" : "PER.ID";

		String sql = 
				"SELECT PER.*, PP.TIPO as inmueble " + 
						"FROM PERSONAS PER " + 
						"INNER JOIN ( " + 
						" " + 
						"    SELECT R.ID_PERSONA AS PERSO, P.TIPO_INMUEBLE AS TIPO " + 
						"    FROM RESERVAS R  " + 
						"    INNER JOIN PROPUESTAS P " + 
						"    ON P.ID = R.ID_PROPUESTA " + 
						" " + 
						"    WHERE P.ID = " + id_propuesta + " " + 
						"    AND R.FECHA_INICIO_ESTADIA BETWEEN '" + fecha_inicial + "' AND '"  + fecha_final +  "' " + 
						" " + 
						") " + 
						"PP ON PP.PERSO = PER.ID " + 
						"where per.id = " + id_usuario + 
						"ORDER BY " + odernamiento;


		List<Persona> personas = new ArrayList<>();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while ( rs.next() ) {
			Persona p = this.convertResultSetTo_Persona(rs);
			p.setInmueble(rs.getString("inmueble"));
			personas.add(p);
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
	public List<Persona> RFC11_inverso_consumo_admin ( Long id_propuesta, String fecha_inicial, String fecha_final, String tipo_ordenamiento ) throws BusinessLogicException, Exception, SQLException {

		if ( id_propuesta == 0 || id_propuesta == null | fecha_final.isEmpty() | fecha_inicial.isEmpty() | tipo_ordenamiento == null )
			throw new BusinessLogicException("Digitar datos correctos :: ID_PROPUESTA = " + id_propuesta + "  Rango = " + fecha_inicial + " -- " + fecha_final );

		String odernamiento = tipo_ordenamiento.equalsIgnoreCase("inmueble") ? "PP.TIPO" : "PER.ID";

		String sql = 
				"SELECT PER.*, PP.TIPO as inmueble " + 
						"FROM PERSONAS PER " + 
						"INNER JOIN ( " + 
						" " + 
						"    SELECT R.ID_PERSONA AS PERSO, P.TIPO_INMUEBLE AS TIPO " + 
						"    FROM RESERVAS R  " + 
						"    INNER JOIN PROPUESTAS P " + 
						"    ON P.ID = R.ID_PROPUESTA " + 
						" " + 
						"    WHERE P.ID = " + id_propuesta + " " + 
						"    AND R.FECHA_INICIO_ESTADIA NOT BETWEEN '" + fecha_inicial + "' AND '"  + fecha_final +  "' " + 
						" " + 
						") " + 
						"PP ON PP.PERSO = PER.ID " + 
						" " + 
						"ORDER BY " + odernamiento;


		List<Persona> personas = new ArrayList<>();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while ( rs.next() ) {
			Persona p = this.convertResultSetTo_Persona(rs);
			p.setInmueble(rs.getString("inmueble"));
			personas.add(p);
		}

		return personas;
	}

	/**
	 * 
	 * @param id_usuario se define el cliente actual que está utilizando la aplicacion
	 * @param id_propuesta
	 * @param fecha_inicial
	 * @param fecha_final
	 * @param tipo_ordenamiento
	 * @return
	 * @throws BusinessLogicException
	 * @throws Exception
	 * @throws SQLException
	 */
	public List<Persona> RFC11_inverso_consumo_user ( Long id_usuario, Long id_propuesta, String fecha_inicial, String fecha_final, String tipo_ordenamiento ) throws BusinessLogicException, Exception, SQLException {

		if ( id_propuesta == 0 || id_propuesta == null | fecha_final.isEmpty() | fecha_inicial.isEmpty() | tipo_ordenamiento == null )
			throw new BusinessLogicException("Digitar datos correctos :: ID_PROPUESTA = " + id_propuesta + "  Rango = " + fecha_inicial + " -- " + fecha_final );

		String odernamiento = tipo_ordenamiento.equalsIgnoreCase("inmueble") ? "PP.TIPO" : "PER.ID";

		String sql = 
				"SELECT PER.*, PP.TIPO as inmueble " + 
						"FROM PERSONAS PER " + 
						"INNER JOIN ( " + 
						" " + 
						"    SELECT R.ID_PERSONA AS PERSO, P.TIPO_INMUEBLE AS TIPO " + 
						"    FROM RESERVAS R  " + 
						"    INNER JOIN PROPUESTAS P " + 
						"    ON P.ID = R.ID_PROPUESTA " + 
						" " + 
						"    WHERE P.ID = " + id_propuesta + " " + 
						"    AND R.FECHA_INICIO_ESTADIA NOT BETWEEN '" + fecha_inicial + "' AND '"  + fecha_final +  "' " + 
						" " + 
						") " + 
						"PP ON PP.PERSO = PER.ID " + 
						"where per.id = " + id_usuario + 
						"ORDER BY " + odernamiento;


		List<Persona> personas = new ArrayList<>();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while ( rs.next() ) {
			Persona p = this.convertResultSetTo_Persona(rs);
			p.setInmueble(rs.getString("inmueble"));
			personas.add(p);
		}

		return personas;
	}






















}
