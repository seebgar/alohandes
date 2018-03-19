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

import tm.BusinessLogicException;
import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicacion
 * 
 * 
 * 
 * 
 * 
 * Data Access Object (DAO)
 * Por medio de la conexioÌ�n que se crea en el Transaction Manager, este componente ejecuta las distintas 
 * sentencias SQL, recibe la informacioÌ�n correspondiente y se encarga de transformar tales resultados 
 * (ResultSets) en objetos que se manipulan posteriormente para atender las peticiones seguÌ�n sea el caso.
 * 
 * 
 * 
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
	 * Metodo que obtiene la informacion de todos LAS PERSONAS en la Base de Datos que son de TIPO = {estudiante, registrado, empleado, profesor, padre, invitado, empresa}
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
	 * REQUERIMIENTO 1
	 * 
	 * Metodo que agregar la informacion de un nuevo persona en la Base de Datos a partir del parametro ingresado<br/>
	 * Se define el rol de la persona {cliente, operador}
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param bebedor Bebedor que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addPersona (Persona persona) throws SQLException, Exception {

		String sql = 
				String.format(
						"INSERT INTO %1$s.PERSONAS (ID, NOMBRE, APELLIDO, CEDULA, TIPO, NIT, ROL EMAIL) "
								+ "VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s', '%7$s', '%8$s', '%9$s' )", 
								USUARIO, 
								persona.getId(), 
								persona.getNombre(),
								persona.getApellido(), 
								persona.getCedula(),
								persona.getTipo(),
								persona.getNit(),
								persona.getRol(),
								persona.getEmail()
						);
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	/**
	 * Agrega la informacion de una propuestas a la base de datos
	 *  <b>Precondicion: </b> la conexion ha sido inicializada y la propuesta no puede existir sin una persona operador que la maneje <br/>  
	 * @param persona
	 * @param propuesta
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addPropuesta ( Persona persona, Propuesta propuesta ) throws SQLException, Exception, BusinessLogicException {

		if ( this.findPersonaById(persona.getId()) == null ) {
			System.out.println(String.format("Actualmente la persona con id = {%1$s} {%2$s %3$s} no esta registrada. Debe estar registrado como operador y estar relacionado con la universidad para poder registrar una propuesta.", 
					persona.getId(), persona.getNombre(), persona.getApellido()));
			return;
		}

		// ESTA PARTE ASEGURA QUE LA PERSONA QUE ESTA HCIENDO LA PROPUESTA, SEA UN OPERADOR RELACIONADO A LA UNIVERSIDAD
		try {
			persona.addPropuesta(propuesta);
		} catch ( BusinessLogicException e ) {
			throw new BusinessLogicException(e.getMessage());
		} 

		String sql =
				String.format("INSERT INTO %1$s.PROPUESTAS(ID, ID_PERSONA, ID_HOSTEL, ID_HOTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO"
						+ ", ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION, SE_VA_RETIRAR)"
						+ " VALUES ( %2$d, %3$d, %4$d, %5$d, %6$d, %7$d, %8$d, %9$d, %10$d  )",
						propuesta.getId(),
						persona.getId(),
						propuesta.getHostel().getId(),
						propuesta.getHotel().getId(),
						propuesta.getVivienda_express().getId(),
						propuesta.getApartamento().getId(),
						propuesta.getVivienda_universitarias().getId(),
						propuesta.getHabitacion().getId(),
						(propuesta.getSeVaRetirar()==false)? 0 : 1);

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	
	/**
	 * 
	 * @param propuesta
	 * @throws SQLException
	 * @throws Exception
	 * @throws BusinessLogicException
	 */
	public void retirarPropuesta(Propuesta propuesta)throws SQLException, Exception, BusinessLogicException{
		
		//Formateando la fecha:
        DateFormat formatoConHora= new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
        
        //Fecha actual desglosada:
        Calendar fecha = Calendar.getInstance();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        String actualDate= ""+anio+"-"+mes+"-"+dia+" "+hora+":"+minuto+":"+segundo;
        
		Date fechaActual= formatoConHora.parse(actualDate);
		
		
		//necesitio las reservas que tengan esa propuesta
		ArrayList<Reserva> reservasConPropuesta = new ArrayList<>();
		
		String reservitas= String.format("SELECT * FROM RESERVAS WHERE ID_PROPUESTA = %2$d", USUARIO, propuesta.getId());
		PreparedStatement prepStmt1= conn.prepareStatement(reservitas);
		recursos.add(prepStmt1);	
		ResultSet rs = prepStmt1.executeQuery();
		DAOReserva dao= new DAOReserva();
		
		while(rs.next())
			reservasConPropuesta.add(dao.convertResultSetToReserva(rs));
		//obtengo las reservas con la propuesta dada
		
		Date lastDate= new Date("1500-01-01 00:00:00");
		for(Reserva res: reservasConPropuesta) {
			Date temp= res.getFechaFinal();
			if(temp.after(lastDate)) {
				lastDate= temp;
			}
		} //obtengo la fecha de la ultima reserva que se acaba
		
		if(fechaActual.before(lastDate)) {
			propuesta.setSeVaRetirar(true);
			StringBuilder sql = new StringBuilder();
			sql.append(String.format("UPDATE PROPUESTAS SET ", USUARIO));
			sql.append(String.format("SE_VA_RETIRAR = '%1$s' ", propuesta.getSeVaRetirar()));
			
		}
		else if(fechaActual.after(lastDate)) {
			String sql = String.format("DELETE FROM %1$s.PROPUESTAS WHERE ID = %2$d", USUARIO, propuesta.getId());
			
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
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

		String sql = String.format("DELETE FROM %1$s.PERSONAS WHERE ID = %2$d", USUARIO, persona.getId());

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

		Persona pep = new Operador(id, nombre, apellido, tipo, rol, nit, cedula, email);

		return pep;
	}

	public Propuesta convertResultSetTo_Propuesta(ResultSet resultSet) throws SQLException {

		long id = resultSet.getLong("ID");
		String tipo_inmueble = resultSet.getString("TIPO_INMUEBLE");

		Propuesta prop = new Propuesta(id, tipo_inmueble);

		if ( Propuesta.TIPO_INMUEBLE.APARTAMENTO.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.APARTAMENTOS WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_APARTAMENTO"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setApartamento(new Apartamento(rs.getLong("ID"), rs.getInt("AMOBLADO") == 0 ? false : true , rs.getDouble("COSTO_ADMIN")));
			}
		} 

		if ( Propuesta.TIPO_INMUEBLE.HABITACION.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.HABITACIONES WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_HABITACION"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setHabitacion( new Habitacion(rs.getLong("ID"), rs.getInt("PRECIO_ESPECIAL") == 0 ? false : true, rs.getString("TIPO_HABITACION")) );
			}
		}

		if ( Propuesta.TIPO_INMUEBLE.HOSTEL.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.HOSTELES WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_HOSTEL"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setHostel( new Hostel(rs.getLong("ID"), rs.getString("REGISTRO_CAMARA_COMERCIO"), rs.getString("REGISTRO_SUPERINTENDENCIA"), rs.getString("TIPO_HABITACION"), rs.getString("UBICACION"), rs.getInt("HORARIO_ADMIN_INICIAL"), rs.getInt("HORARIO_ADMIN_FINAL")) );
			}
		}

		if ( Propuesta.TIPO_INMUEBLE.HOTEL.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.HOTELES WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_HOTEL"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setHotel( new Hotel(rs.getLong("ID"), rs.getString("REGISTRO_CAMARA_COMERCIO"), rs.getString("REGISTRO_SUPERINTENDENCIA"), rs.getString("TIPO_HABITACION"), rs.getString("UBICACION"), rs.getInt("HORARIO_ADMIN_24H") == 0 ? false : true) );
			}
		}

		if ( Propuesta.TIPO_INMUEBLE.VIVIENDA_EXPRESS.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.VIVIENDAS_EXPRESS WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_VIVIENDA_EXPRESS"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setVivienda_express( new ViviendaExpress(rs.getLong("ID"), rs.getString("MENAJE"), rs.getString("UBICACION")) );
			}
		}

		if ( Propuesta.TIPO_INMUEBLE.VIVIENDA_UNIVERSITARIA.toString().equalsIgnoreCase(tipo_inmueble) ) {
			String sql = String.format("SELECT * FROM %1$s.VIVIENDAS_UNIVERSITARIAS WHERE ID = %2$d", USUARIO, resultSet.getLong("ID_VIVIENDA_UNIVERSITARIA"));
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				prop.setVivienda_universitarias( new ViviendaUniversitaria(rs.getLong("ID"), rs.getString("UBICACION"), rs.getString("CAPACIDAD"), rs.getString("MENAJE"), rs.getString("DESCRIPCION"), rs.getString("TIPO"), rs.getInt("MENSUAL") == 0 ? false : true) );
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
	 * RFC2
	 * 
	 * Retorna las 20 orfertas mÃ¡s populares 
	 * @return
	 */
	public ArrayList<String> _20_ofertas_mas_populares ()  throws SQLException, Exception {
		
		System.out.println("entra");
		
		String sql =String.format( "SELECT  ID_PROPUESTA, COUNT(ID_PROPUESTA) AS \"Cantidad Reservas\" \n" + 
				"		FROM %1$s.RESERVAS \n" + 
				"		GROUP BY ID_PROPUESTA\n" + 
				"		ORDER BY \"Cantidad Reservas\" DESC", USUARIO);
		
		//String sql = "SELECT * FROM " + USUARIO + ".RESERVAS  ";
		
		System.out.println("sale");
		
		ArrayList<String> populares = new ArrayList<String>();

		System.out.println("-1");
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		System.out.println("0");
		recursos.add(prepStmt);
		System.out.println("1");
		ResultSet rs = prepStmt.executeQuery();
		System.out.println("2");
		
		System.out.println("query");

		while (rs.next()) {
			populares.add(rs.getLong("ID_PROPUESTA") + "");
		}
		return populares;
		
	}

	
	

	





















}
