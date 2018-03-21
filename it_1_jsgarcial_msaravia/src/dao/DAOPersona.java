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

		String sql = String.format("SELECT * FROM %1$s.PROPUESTAS WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			propuesta = convertResultSetTo_Propuesta(rs);
		}

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
	 * TODO
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
	 * TODO
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

		String sql =
				String.format("INSERT INTO %1$s.PROPUESTAS(ID, ID_PERSONA, ID_HOSTEL, ID_HOTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO"
						+ ", ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION, SE_VA_RETIRAR)"
						+ " VALUES ( %2$d, %3$d, %4$d, %5$d, %6$d, %7$d, %8$d, %9$d, %10$d  )",
						USUARIO,
						propuesta.getId(),
						operador.getId(),
						propuesta.getHostel() == null ? null : propuesta.getHostel().getId(),
						propuesta.getHotel() == null ? null : propuesta.getHotel().getId(),
						propuesta.getVivienda_express() == null ? null : propuesta.getVivienda_express().getId(),
						propuesta.getApartamento() == null ? null : propuesta.getApartamento().getId(),
						propuesta.getVivienda_universitarias() == null ? null : propuesta.getVivienda_universitarias().getId(),
						propuesta.getHabitacion() == null ? null : propuesta.getHabitacion().getId(),
						(propuesta.getSeVaRetirar() == false) ? null : 1);

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	/**
	 * RF-6
	 * TODO
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
		//String email = resultSet.getString("EMAIL");

		Persona pep = new Operador(id, nombre, apellido, tipo, rol, nit, cedula, null);

		return pep;
	}

	public Propuesta convertResultSetTo_Propuesta(ResultSet resultSet) throws SQLException {

		long id = resultSet.getLong("ID");
		String tipo_inmueble = resultSet.getString("TIPO_INMUEBLE");
		Integer capacidad = resultSet.getInt("CAPACIDAD_MAXIMA");
		Integer id_persona = resultSet.getInt("ID_PERSONA");

		Propuesta prop = new Propuesta(id, tipo_inmueble, capacidad, id_persona);

		if ( Propuesta.TIPO_INMUEBLE.APARTAMENTO.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.APARTAMENTOS WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_APARTAMENTO"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setApartamento(new Apartamento(rs.getLong("ID"), rs.getInt("AMOBLADO") == 0 ? false : true , rs.getDouble("COSTO_ADMIN"), capacidad));
			}
		} 

		if ( Propuesta.TIPO_INMUEBLE.HABITACION.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.HABITACIONES WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_HABITACION"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setHabitacion( new Habitacion(rs.getLong("ID"), rs.getInt("PRECIO_ESPECIAL") == 0 ? false : true, rs.getString("TIPO_HABITACION"), capacidad) );
			}
		}

		if ( Propuesta.TIPO_INMUEBLE.HOSTEL.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.HOSTELES WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_HOSTEL"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setHostel( new Hostel(rs.getLong("ID"), rs.getString("REGISTRO_CAMARA_COMERCIO"), rs.getString("REGISTRO_SUPERINTENDENCIA"), rs.getString("TIPO_HABITACION"), rs.getString("UBICACION"), rs.getInt("HORARIO_ADMIN_INICIAL"), rs.getInt("HORARIO_ADMIN_FINAL"), capacidad) );
			}
		}

		if ( Propuesta.TIPO_INMUEBLE.HOTEL.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.HOTELES WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_HOTEL"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setHotel( new Hotel(rs.getLong("ID"), rs.getString("REGISTRO_CAMARA_COMERCIO"), rs.getString("REGISTRO_SUPERINTENDENCIA"), rs.getString("TIPO_HABITACION"), rs.getString("UBICACION"), rs.getInt("HORARIO_ADMIN_24H") == 0 ? false : true, capacidad) );
			}
		}

		if ( Propuesta.TIPO_INMUEBLE.VIVIENDA_EXPRESS.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.VIVIENDAS_EXPRESS WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_VIVIENDA_EXPRESS"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setVivienda_express( new ViviendaExpress(rs.getLong("ID"), rs.getString("MENAJE"), rs.getString("UBICACION"), capacidad) );
			}
		}

		if ( Propuesta.TIPO_INMUEBLE.VIVIENDA_UNIVERSITARIA.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.VIVIENDAS_UNIVERSITARIAS WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_VIVIENDA_UNIVERSITARIA"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setVivienda_universitarias( new ViviendaUniversitaria(rs.getLong("ID"), rs.getString("UBICACION"), rs.getString("CAPACIDAD"), rs.getString("MENAJE"), rs.getString("DESCRIPCION"), rs.getString("TIPO"), rs.getInt("MENSUAL") == 0 ? false : true, capacidad) );
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
	 * TODO
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
	 * TODO
	 * Retorna las 20 ofertas mas populares
	 * 
	 * 
	 * Retorna las 20 orfertas mÃ¡s populares 
	 * @return
	 */
	public List<Populares> _20_ofertas_mas_populares ()  throws SQLException, Exception {

		String sql =String.format( "SELECT  ID_PROPUESTA, COUNT(ID_PROPUESTA) AS \"Cantidad Reservas\" \n" + 
				"		FROM %1$s.RESERVAS \n" + 
				"		GROUP BY ID_PROPUESTA\n" + 
				"		ORDER BY \"Cantidad Reservas\" DESC", USUARIO);

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




	/**
	 * Convierte un SQL a VO Reserva
	 * @param resultSet
	 * @return
	 * @throws Exception 
	 */
	public Reserva convertResultSetTo_Reserva ( ResultSet resultSet ) throws Exception {

		Integer id, duracion_contrato, cantidad_personas, hay_multa;
		Long id_persona, id_propuesta;
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


		Reserva res = new Reserva((long)id, fecha_registro, fecha_cancelacion, fecha_inicio_estadia, duracion_contrato, (double)costo_total, 
				cantidad_personas, hay_multa == 0 ? false : true, (double)valor_multa,
						id_propuesta, id_persona);


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















}
