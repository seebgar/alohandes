package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import tm.BusinessLogicException;
import vos.Cliente;
import vos.Persona;
import vos.Propuesta;
import vos.Reserva;

public class DAOReserva {

	//constantes

	/**
	 * constante que contiene el usuario de oracle
	 */
	public final static String USUARIO = "ISIS2304A491810";

	//atributos

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	/**
	 * 
	 */
	private DAOPersona persona;

	//constructor

	/**
	 * 
	 */
	public DAOReserva(){

		recursos= new ArrayList<Object>();
		persona= new DAOPersona();
	}




	//metodos

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Reserva getReservaById(Long id) throws SQLException, Exception {
		Reserva reserva = null;

		String sql = String.format("SELECT * FROM %1$s.RSERVAS WHERE ID = %2$d", USUARIO, id); 

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
		// 1. una persona no puede reservar más de un alojamiento en un mismo día
		// 2. un alojamiento no acepta reservas que superen su capacidad <CAPACIDAD_MAXIMA>
		// 3.  el alojamiento en vivienda universitaria sólo está habilitado a estudiantes, profesores, empleados y profesores visitantes.

		ArrayList<Reserva> reservasEnFecha = new ArrayList<>();

		//consigo las reservas que hay para ese dia
		String reservas = String.format("SELECT * FROM %1$s.RESERVAS WHERE ID = %2$d AND FECHA_INICIO_ESTADIA = %3$d",
				USUARIO, reserva.getId(), reserva.getFecha_inicio_estadia());
		PreparedStatement prepStmt1= conn.prepareStatement(reservas);
		recursos.add(prepStmt1);	
		ResultSet rs = prepStmt1.executeQuery(); 

		while(rs.next())
			reservasEnFecha.add(convertResultSetTo_Reserva(rs));

		Cliente solicitado= reserva.getCliente();

		//se valida que el cliente no haga mas reservas un mismo dia
		for(Reserva res: reservasEnFecha) {
			Cliente cliente = res.getCliente();
			if( solicitado.getId() == cliente.getId() )
				throw new BusinessLogicException("No puede hacer mas reservas el mismo dia");
		}


		//valido que la propuesta sea vigente
		Propuesta propuesta = reserva.getPropuesta();
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
						solicitado.getId(),
						reserva.getPropuesta().getId(),
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
	 * 
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
		if ( reserva.getPropuesta().getTipo_inmueble().equalsIgnoreCase("Apartamento") || 
				reserva.getPropuesta().getTipo_inmueble().equalsIgnoreCase("Habiatcion") || 
				reserva.getPropuesta().getTipo_inmueble().equalsIgnoreCase("Vivienda Universitaria") ) {
			cal.add(Calendar.DAY_OF_YEAR, -7);
			fecha_limite = cal.getTime();
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
		String sql = String.format("UPDATE %1$s.PERSONAS P SET COSTO_MULTA = %2$d WHERE P.ID = %3$d; ", USUARIO, reserva.getCosto_total() ,reserva.getCliente().getId());
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		// Se elimina la reserva de la base de datos
		String delete = String.format("DELETE FROM %1$s.RESERVAS R WHERE R.ID = %2$d ", USUARIO, reserva.getId());
		PreparedStatement delete_sql = conn.prepareStatement(delete);
		recursos.add(delete_sql);
		delete_sql.executeQuery();






		//        //Formateando la fecha:
		//        DateFormat formatoConHora = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
		//        
		//        //Fecha actual desglosada:
		//        Calendar fecha = Calendar.getInstance();
		//        int anio = fecha.get(Calendar.YEAR);
		//        int mes = fecha.get(Calendar.MONTH) + 1;
		//        int dia = fecha.get(Calendar.DAY_OF_MONTH);
		//        int hora = fecha.get(Calendar.HOUR_OF_DAY);
		//        int minuto = fecha.get(Calendar.MINUTE);
		//        int segundo = fecha.get(Calendar.SECOND);
		//        String actualDate = ""+anio+"-"+mes+"-"+dia+" "+hora+":"+minuto+":"+segundo;
		//        
		//		Date fechaActual= formatoConHora.parse(actualDate);
		//		
		//		//necesito la fecha de estadia
		//		String darFechaEstadia = String.format("SELECT * FROM RESERVAS WHERE ID = %2$d", USUARIO, reserva.getId());
		//		PreparedStatement prepStmt = conn.prepareStatement(darFechaEstadia);
		//		recursos.add(prepStmt);
		//		
		//		ResultSet rs = prepStmt.executeQuery();
		//		Reserva reserva1= convertResultSetToReserva(rs);
		//				
		//		Date fechaDeEstadia= formatoConHora.parse(reserva1.getFecha_inicio_estadia()); // obtengo la fecha e estadia
		//
		//		//necesito la fecha de regisro
		////		String fechaDeRegistro= String.format("SELECT * FROM RESERVAS WHERE ID = %2$d", USUARIO, reserva.getId());
		////		
		////		PreparedStatement prepStmt2 = conn.prepareStatement(fechaDeRegistro);
		////		ResultSet rs2= prepStmt2.executeQuery();
		////		Reserva reserva2= convertResultSetToReserva(rs2);
		////		
		////		Date fechaDeRegistroAQuedarse= formatoConHora.parse(reserva2.getFecha_registro());
		//		 // me da la fecha que se hizo la reserva
		//		
		//		Calendar cal= Calendar.getInstance();
		//		
		//		cal.setTime(fechaDeEstadia);
		//		cal.add(Calendar.DAY_OF_YEAR, -8);
		//		Date fechaMaxima= cal.getTime();
		//		
		//		if(fechaActual.before(fechaMaxima)){
		//			// primera regla de negocio, si esta antes de la fecha m�xima de cancelacion se cobra el 10%
		//			double valorMulta= reserva.getCosto_total();
		//			reserva.setValorMulta(valorMulta*0.1);
		//			reserva.setCosto_total(valorMulta);
		//			StringBuilder sql = new StringBuilder();
		//			sql.append(String.format("UPDATE RESERVAS SET ", USUARIO));
		//			sql.append(String.format("FECHA_CANCELACION = '%1$s' AND HAY_MULTA = '%2$s' AND VALOR_MULTA = '%3$s' ", fechaActual.toString(), 1, reserva.getValorMulta()));
		//		}else if(fechaActual.after(fechaMaxima) && fechaActual.before(fechaDeEstadia)){
		//			//segunda regla de negocio, si se reserva despues de la fecha maxima y antes de la fecha de inicio de la estadia se cobra el 30%
		//			double valorMulta= reserva.getCosto_total();
		//			reserva.setValorMulta(valorMulta*0.3);
		//			reserva.setCosto_total(valorMulta);
		//			StringBuilder sql = new StringBuilder();
		//			sql.append(String.format("UPDATE RESERVAS SET ", USUARIO));
		//			sql.append(String.format("FECHA_CANCELACION = '%1$s' AND HAY_MULTA = '%2$s' AND VALOR_MULTA = '%3$s' ", fechaActual.toString(), 1, reserva.getValorMulta()));
		//		}else{
		//			//tercera regla de negocio (una parte), si se reserva despues de la fecha de inicio de estadia, se cobra el 50%
		//			double valorMulta= reserva.getCosto_total();
		//			reserva.setValorMulta(valorMulta*0.5);
		//			reserva.setCosto_total(valorMulta);
		//			StringBuilder sql = new StringBuilder();
		//			sql.append(String.format("UPDATE RESERVAS SET ", USUARIO));
		//			sql.append(String.format("FECHA_CANCELACION = '%1$s' AND HAY_MULTA = '%2$s' AND VALOR_MULTA = '%3$s' ", fechaActual.toString(), 1, reserva.getValorMulta()));
		//		}

		//		String[] particion= fechaDeRegistroAQuedarse.toString().split("-");
		//		int dia1= Integer.parseInt(particion[2]);
		//		
		//		
		//		Calendar calendario= Calendar.getInstance();
		//		calendario.se
		//		int diaMax;
		//		int mesMax;
		//		int anioMax;
		//		
		//		if()
		//		String sql = String.format("DELETE FROM RECURSOS WHERE ID = %2$d", USUARIO, reserva.getId());
		//		
		//		PreparedStatement prepStmt = conn.prepareStatement(sql);
		//		recursos.add(prepStmt);
		//		prepStmt.executeQuery();
	}


	/**
	 * Transforma setencia SQL  a VO Reserva
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public Reserva convertResultSetTo_Reserva ( ResultSet resultSet ) throws Exception {

		Integer id, id_persona, id_propuesta, duracion_contrato, cantidad_personas, hay_multa;
		String fecha_registro, fecha_cancelacion, fecha_inicio_estadia;
		float costo_total, valor_multa; 

		id = resultSet.getInt("ID");
		id_persona = resultSet.getInt("ID_PERSONA");
		fecha_registro = resultSet.getString("FECHA_REGISTRO");
		fecha_cancelacion = resultSet.getString("FECHA_CANCELACION");
		fecha_inicio_estadia = resultSet.getString("FECHA_INICIO_ESTADIA");
		duracion_contrato = resultSet.getInt("DURACION_CONTRATO");
		costo_total = resultSet.getFloat("COSTO_TOTAL");
		cantidad_personas = resultSet.getInt("CANTIDAD_PERSONAS");
		hay_multa = resultSet.getInt("HAY_MULTA");
		valor_multa = resultSet.getFloat("VALOR_MULTA");

		id_propuesta = resultSet.getInt("ID_PROPUESTA");
		DAOPersona dao = new DAOPersona();
		Propuesta propuesta = dao.getPropuestaById((long)id_propuesta);

		id_persona = resultSet.getInt("ID_PERSONA");
		Persona persona = dao.get_Persona_ById((long)id_persona);


		Reserva res = new Reserva((long)id, fecha_registro, fecha_cancelacion, fecha_inicio_estadia, duracion_contrato, (double)costo_total, 
				cantidad_personas, hay_multa == 0 ? false : true, (double)valor_multa,
						propuesta, (Cliente) persona);

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


}
