package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tm.BusinessLogicException;
import vos.Cliente;
import vos.Persona;
import vos.Propuesta;
import vos.Reserva;


/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicacion
 * 
 * Data Access Object (DAO)
 * Por medio de la conexioÃŒï¿½n que se crea en el Transaction Manager, este componente ejecuta las distintas 
 * sentencias SQL, recibe la informacioÃŒï¿½n correspondiente y se encarga de transformar tales resultados 
 * (ResultSets) en objetos que se manipulan posteriormente para atender las peticiones seguÃŒï¿½n sea el caso.
 * 
 * 
 */
public class DAOReserva {


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
	public DAOReserva() {
		recursos = new ArrayList<Object>();
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE COMUNICACION CON LA BASE DE DATOS
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * Retorna la reserva por id
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Reserva getReservaById(Long id) throws SQLException, Exception {
		Reserva reserva = null;

		String sql = String.format("SELECT * FROM %1$s.RESERVAS WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			reserva = convertResultSetTo_Reserva(rs);
		}

		return reserva;
	}



	/**
	 * RF-4
	 * 
	 * 
	 * 
	 * Metodo que agregar la informacion de una nueva reserva en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param reserva Reserva que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws BusinessLogicException Si se genera un error dentro del metodo.
	 */
	public void registrarReserva( Reserva reserva ) throws SQLException, BusinessLogicException, Exception {

		if ( reserva == null )
			throw new BusinessLogicException("La reserva que se piensa registrar es nula.");

		// REGLAS:
		// 1. una persona no puede reservar maÌ�s de un alojamiento en un mismo diÌ�a
		// 2. un alojamiento no acepta reservas que superen su capacidad <CAPACIDAD_MAXIMA>
		// 3.  el alojamiento en vivienda universitaria soÌ�lo estaÌ� habilitado a estudiantes, profesores, empleados y profesores visitantes.

		ArrayList<Reserva> reservasEnFecha = new ArrayList<>();

		//consigo las reservas que hay para ese dia
		String reservas = String.format("SELECT * FROM %1$s.RESERVAS WHERE ID = %2$d AND FECHA_INICIO_ESTADIA = %3$s",
				USUARIO, reserva.getId(), reserva.getFecha_inicio_estadia());
		PreparedStatement prepStmt1= conn.prepareStatement(reservas);
		recursos.add(prepStmt1);	
		ResultSet rs = prepStmt1.executeQuery(); 

		while(rs.next())
			reservasEnFecha.add(convertResultSetTo_Reserva(rs));

		// para usar un buscador de persona o propuesta por id
		DAOPersona dao = new DAOPersona();
		
		Persona solicitado = dao.get_Persona_ById(reserva.getId_cliente());

		//se valida que el cliente no haga mas reservas un mismo dia
		for(Reserva res: reservasEnFecha) {
			Persona cliente = dao.get_Persona_ById(res.getId_cliente());
			if( solicitado.getId() == cliente.getId() )
				throw new BusinessLogicException("No puede hacer mas reservas el mismo dia");
		}


		//valido que la propuesta sea vigente
		Propuesta propuesta = dao.getPropuestaById(reserva.getId_propuesta());
		if( propuesta.getSeVaRetirar() )
			throw new BusinessLogicException("No se puede realizar la reserva porque la propuesta no esta disponible para mas fechas");


		// valida que la cantidad de personas a usar lel inmueble no exceda la capacidad maxima de este
		if ( reserva.getCantidad_personas() > propuesta.getCapacidad_maxima() )
			throw new BusinessLogicException("La reserva exceda la capacidad maxima de personas permitido para el alohamiento");


		//sentencia para insertar la resrva en la base de datos
		String sql = 
				String.format("INSERT INTO $1%s.RESERVAS ( ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL, CANTIDAD_PERSONAS, HAY_MULTA, VALOR_MULTA ) "
						+ "VALUES ( $2%d, $3%d, $4%d, '$5%s', '$6%s', '$7%s', '$8%s', '$9%s', '$10%s', '$11%s', '$12%s')", 
						reserva.getId(),
						reserva.getId_cliente(),
						reserva.getId_propuesta(),
						reserva.getFecha_registro(),
						reserva.getFecha_cancelacion(),
						reserva.getFecha_inicio_estadia(),
						reserva.getDuracion(),
						reserva.getCosto_total(),
						reserva.getCantidad_personas(),
						reserva.getHayMulta(),
						reserva.getValorMulta());

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery(); //se inserta la reserva

	}

	/**
	 * RF-5
	 * 
	 * Metodo que actualiza la informacion de la reserva en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param resrva Reserva que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws BusinessLogicException Si se genera un error dentro del metodo.
	 */
	public void cancelarReserva( Reserva reserva ) throws SQLException, BusinessLogicException, Exception {

		// REGLAS:
		// Si cancela antes del tiempo minimo, multa de 10% del costo total reserva
		// Si se cancela entre el tiempo limite y el dia anterior a la fecha de llegada, multa de 30% del costo total de la  reserva
		// Se se cancela luego de la fecha inicio estadia, multa de 50% del costo total de la reserva
		// Tiempo limite en dias: 3 dias. en semanas o meses: 1 semana

		Date actual = new Date();
		Double multa = 0.0;

		String string = reserva.getFecha_inicio_estadia();
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		Date fecha_inicio_estadia = format.parse(string);

		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha_inicio_estadia);

		// fecha limite de cancelacion. Determina el porcentaje de multa
		Date fecha_limite;

		// Apartamento y Habitacion y Vivienda Universitaria es por meses :: Tiempo Limite 1 semana
		DAOPersona dao = new DAOPersona();
		Propuesta prop = dao.getPropuestaById(reserva.getId_propuesta());
		if ( prop != null ) {
			if ( prop.getTipo_inmueble().equalsIgnoreCase("Apartamento") || 
					prop.getTipo_inmueble().equalsIgnoreCase("Habiatcion") || 
					prop.getTipo_inmueble().equalsIgnoreCase("Vivienda Universitaria") ) {
				cal.add(Calendar.DAY_OF_YEAR, -7);
				fecha_limite = cal.getTime();
			} else {
				cal.add(Calendar.DAY_OF_YEAR, -3);
				fecha_limite = cal.getTime();
			}
		} else {
			cal.add(Calendar.DAY_OF_YEAR, -3);
			fecha_limite = cal.getTime();
		}

		// Determina los percentajes
		if ( actual.before(fecha_inicio_estadia) && actual.after(fecha_limite) ) {
			multa = reserva.getCosto_total() * 0.30;
			reserva.setHayMulta(true);
			reserva.setCosto_total(multa);
		} else if ( actual.before(fecha_limite) ) {
			multa = reserva.getCosto_total() * 0.10;
			reserva.setHayMulta(true);
			reserva.setCosto_total(multa);
		} else if ( actual.after(fecha_inicio_estadia) ) {
			multa = reserva.getCosto_total() * 0.50;
			reserva.setHayMulta(true);
			reserva.setCosto_total(multa);
		} else {
			multa = reserva.getCosto_total() * 0.50;
			reserva.setHayMulta(true);
			reserva.setCosto_total(multa);
		}

		// Se le asigna la multa a la persona correspondiente
		String sql = String.format("UPDATE %1$s.PERSONAS P SET COSTO_MULTA = %2$d WHERE P.ID = %3$d; ", USUARIO, reserva.getCosto_total() ,reserva.getId_cliente());
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		// Se elimina la reserva de la base de datos
		String delete = String.format("DELETE FROM %1$s.RESERVAS R WHERE R.ID = %2$d ", USUARIO, reserva.getId());
		PreparedStatement delete_sql = conn.prepareStatement(delete);
		recursos.add(delete_sql);
		delete_sql.executeQuery();

	}


	/**
	 * Transforma setencia SQL  a VO Reserva
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public Reserva convertResultSetTo_Reserva ( ResultSet resultSet ) throws Exception {

		Integer id,  duracion_contrato, cantidad_personas, hay_multa;
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

		
//		DAOPersona dao = new DAOPersona();
//		Propuesta propuesta = null;
//		try {
//			propuesta = dao.getPropuestaById((long)id_propuesta);
//		} catch (Exception e) {	}
//		Persona persona = null;
//		try {
//			persona =  dao.get_Persona_ById((long)id_persona);
//		} catch (Exception e) {}


		Reserva res = new Reserva((long)id, fecha_registro, fecha_cancelacion, fecha_inicio_estadia, duracion_contrato, (double)costo_total, 
				cantidad_personas, hay_multa == 0 ? false : true, (double)valor_multa,
						id_propuesta,  id_propuesta);

		return res;

	}


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



	//----------------------------------------------------------------------------------------------------------------------------------
	// PROVACIDAD
	//----------------------------------------------------------------------------------------------------------------------------------



	/**
	 * Retorna las reservas de un cliente por id 
	 * @param id_cliente
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Reserva> get_Reservas_Cliente_PorID ( Long id_cliente) throws SQLException, Exception {

		ArrayList<Reserva> res = new ArrayList<>();

		String sql = " SELECT * FROM " + USUARIO + ".RESERVAS R WHERE R.ID_PERSONA = " + id_cliente;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet set = prepStmt.executeQuery();

		while (set.next()) {
			res.add(this.convertResultSetTo_Reserva(set));
		}

		return res;

	}














}
