package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import tm.AlohandesTransactionManager;
import tm.BusinessLogicException;
import vos.Cliente;
import vos.Colectivo;
import vos.Persona;
import vos.Propuesta;
import vos.Reserva;
import vos.UsuarioEnColectivo;


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
		DAOPersona dao = new DAOPersona();
		dao.setConn(conn);
		if ( reserva == null )
			throw new BusinessLogicException("La reserva que se piensa registrar es nula.");

		// REGLAS:
		// 1. una persona no puede reservar maÌ�s de un alojamiento en un mismo diÌ�a
		// 2. un alojamiento no acepta reservas que superen su capacidad <CAPACIDAD_MAXIMA>
		// 3.  el alojamiento en vivienda universitaria soÌ�lo estaÌ� habilitado a estudiantes, profesores, empleados y profesores visitantes.

		ArrayList<Reserva> reservasEnFecha = new ArrayList<>();

		//consigo las reservas que hay para ese dia
		String reservas = String.format("SELECT * FROM RESERVAS WHERE ID ="+ reserva.getId()+" AND FECHA_INICIO_ESTADIA ='"+reserva.getFecha_inicio_estadia()+"'");
		System.out.println(reservas);
		PreparedStatement prepStmt1= conn.prepareStatement(reservas);
		recursos.add(prepStmt1); 
		ResultSet rs = prepStmt1.executeQuery(); 

		while(rs.next())
			reservasEnFecha.add(convertResultSetTo_Reserva(rs));

		// para usar un buscador de persona o propuesta por id
		Persona solicitado = dao.get_Persona_ById(reserva.getId_cliente());

		//se valida que el cliente no haga mas reservas un mismo dia
		for(Reserva res: reservasEnFecha) {
			Persona cliente = dao.get_Persona_ById(res.getId_cliente());
			if( solicitado.getId() == cliente.getId() )
				throw new BusinessLogicException("No puede hacer mas reservas el mismo dia :: ID = " + solicitado.getId() );
		}


		//valido que la propuesta sea vigente
		Propuesta propuesta = dao.getPropuestaById(reserva.getId_propuesta());
		if( propuesta.getSeVaRetirar() )
			throw new BusinessLogicException("No se puede realizar la reserva porque la propuesta no esta disponible para mas fechas");


		// valida que la cantidad de personas a usar lel inmueble no exceda la capacidad maxima de este
		if ( reserva.getCantidad_personas() > propuesta.getCapacidad_maxima() )
			throw new BusinessLogicException("La reserva exceda la capacidad maxima de personas permitido para el alohamiento");


		//sentencia para insertar la resrva en la base de datos
		int hay_m = reserva.getHayMulta().toString().equals("true") ? 1 : 0;
		String xx = reserva.getFecha_cancelacion() == null ? "null" : reserva.getFecha_cancelacion(); 
		String sql = "INSERT INTO RESERVAS  " + 
				"( ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL, CANTIDAD_PERSONAS, HAY_MULTA, VALOR_MULTA, ID_COLECTIVO ) " + 
				"VALUES " + 
				"(  " + reserva.getId() + " , " + 
				"  " + reserva.getId_cliente() + " , " + 
				"  " + reserva.getId_propuesta() + " , " + 
				"'" + reserva.getFecha_registro() + "'," + 
				"  " + xx + "   , " + 
				"'"+ reserva.getFecha_inicio_estadia() +"'," + 
				"   " + reserva.getDuracion() + "  , " + 
				"   " + reserva.getCosto_total() + "   , " + 
				"   " + reserva.getCantidad_personas() + "   , " + 
				"   " + hay_m + " , " + 
				"  " + reserva.getValorMulta() + " ,  " + 
				"   null  )";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		System.out.println(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery(); //se inserta la reserva

		//  CAMBIAR EL ATRIBUTO DE DISPONIBILIDAD DE UNA PROPUESTA

		String update = "UPDATE PROPUESTAS P SET P.DISPONIBLE = 0 WHERE P.ID = " + reserva.getId_propuesta();
		PreparedStatement up = conn.prepareStatement(update);
		System.out.println(update);
		recursos.add(up);
		up.executeQuery();

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

		Long id_prop = reserva.getId_propuesta();
		System.out.println(id_prop + " << ID PROPUESTA DE LA RESERVA >> " + reserva.getId());

		if ( id_prop == null )
			throw new BusinessLogicException("La reserva con id = " + reserva.getId() + " tiene una propuesta id = " + id_prop + " invalido");

		try {

			Propuesta prop = dao.getPropuestaById( id_prop );

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

			this.cancelarReserva_Auxiliar(cal, actual, fecha_limite, fecha_inicio_estadia, reserva, multa);

		} catch (Exception e) {

			cal.add(Calendar.DAY_OF_YEAR, -3);
			fecha_limite = cal.getTime();

			this.cancelarReserva_Auxiliar(cal, actual, fecha_limite, fecha_inicio_estadia, reserva, multa);
		}



	}


	/**
	 * Metodo Auxiliar a cancelar reserva, la funcion de este metodo es factorizar para que no queda tan largo el metodo cancelar reserva
	 * Asimismo, aqui es donde se hacen las sentencias SQL para eliminar una reserva y asignar la multa al cliente correspondiente si 
	 * es necesario
	 * @param cal
	 * @param prop
	 * @param actual
	 * @param fecha_limite
	 * @param fecha_inicio_estadia
	 * @param reserva
	 * @param multa
	 * @throws SQLException
	 */
	public void cancelarReserva_Auxiliar( Calendar cal, Date actual, Date fecha_limite, Date  fecha_inicio_estadia, Reserva reserva, Double multa  ) throws SQLException {

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
		String sql = String.format(
				"UPDATE PERSONAS P "
						+ "SET COSTO_MULTA = " + reserva.getCosto_total()
						+ " WHERE P.ID = " + reserva.getId_cliente()
				);
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		// Se elimina la reserva de la base de datos
		String delete = String.format(
				"DELETE FROM RESERVAS R "
						+ "WHERE R.ID = " + reserva.getId());
		System.out.println(delete);
		PreparedStatement delete_sql = conn.prepareStatement(delete);
		recursos.add(delete_sql);
		delete_sql.executeQuery();

		// TODO CAMBIAR EL ATRIBUTO DE DISPONIBILIDAD DE UNA PROPUESTA
		PreparedStatement estado = conn.prepareStatement("UPDATE PROPUESTAS SET DISPONIBLE = 1 WHERE PROPUESTA.ID=? ");            
		estado.setLong(1, reserva.getId_propuesta());
		recursos.add(estado);
		estado.executeQuery();


	}


	/**
	 * Transforma setencia SQL  a VO Reserva
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public Reserva convertResultSetTo_Reserva ( ResultSet resultSet ) throws Exception {

		Integer id,  duracion_contrato, cantidad_personas, hay_multa;
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
		id_colectivo=resultSet.getLong("ID_COLECTIVO");

		Reserva res = new Reserva((long)id, fecha_registro, fecha_cancelacion, fecha_inicio_estadia, duracion_contrato, (double)costo_total, 
				cantidad_personas, hay_multa == 0 ? false : true, (double)valor_multa,
						id_propuesta,  id_persona,id_colectivo);

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












	//----------------------------------------------------------------------------------------------------------------------------------
	// ITERACION 2
	// SISTEMAS TRANSACCIONALES
	//----------------------------------------------------------------------------------------------------------------------------------


	/**
	 * RF7
	 * 
	 * Esta operacion es comun en los casos de eventos masivos (conferencias, foros, festivales,…). El usuario indica
	 * el tipo de alojamiento deseado y la cantidad deseada, por ejemplo, 100 habitaciones sencillas con ciertos
	 * servicios deseados. ALOHANDES debe revisar si esta en capacidad de satisfacer esa solicitud, eventualmente
	 * con varios proveedores, y en caso afirmativo realizar las reservas individuales correspondientes. La reserva
	 * colectiva es identificable de manera individual. Tanto en caso afirmativo como negativo debe informar de
	 * manera completa y coherente las operaciones realizadas
	 * 
	 * @param id_colectivo_reserva ID que representa una reserva colectiva
	 * @param dias Cantidad de dias que durara la reserva
	 * @param tipo_inmueble < HOTEL | HOSTEL | VIVIENDA EXPRESS | APARTAMENTO | VIVIENDA UNIVERSITARIA | HABITACION >
	 * @param privacidad < COMPARTIDA | SENCILLA >  ; PERO si es HOTEL = < SEMISUITE | SUITE | ESTANDAR >
	 * @param cantidad_inmuebles Intege que representa la cantidad de Inmuebles en total que se pretenden reservar
	 * @param servicios_deseados Lista de String que representan los servicios que se desean ::
	 * { para Hotel/Hostel = < SALA_ESTUDIO | GYM | RESTAURANTE | JACUZZI > } 
	 * { para inmuebles = < LUZ | TV | AGUA | INTERNET | COMIDA | BAÑO | APOYOSOCIAL | APOYOACADEMICO > } 
	 * 
	 * @return LISTA DE RESERVAS REALIZADAS
	 * @throws Exception 
	 * 
	 */
	public List<Reserva> RF7_registrar_reserva_colectiva ( Colectivo reserva_colectiva ) throws Exception {

		// ejemplo de la siguiente cadena  = "( 'baño', 'tv')"
		String cadena_servicios = "( ";

		for( String serv : reserva_colectiva.getServicios_deseados() ) {
			cadena_servicios += "'" + serv.toLowerCase() + "', "; // tiene comillas simples
		}
		cadena_servicios = cadena_servicios.substring( 0, cadena_servicios.length() - 2 ); 
		cadena_servicios += ")";

		String tipo_inmueble = reserva_colectiva.getTipo_inmueble();

		// retorna los ID de propuestas que cumplen con las restricciones de servicios deseados y el tipo de inmueble
		String propuestas = 
				"SELECT P.ID " + 
						"FROM PROPUESTAS P " + 
						"WHERE UPPER(TIPO_INMUEBLE) = UPPER('" + tipo_inmueble + "') " +  
						"AND P.ID_" + tipo_inmueble + " " + 
						"IN ( " + 
						"SELECT S.ID_" + tipo_inmueble + " " +
						"FROM SERVICIOS_BASICOS S INNER JOIN TIPOS T ON T.ID = S.ID_TIPO " + 
						"WHERE T.NOMBRE IN " + cadena_servicios + " "  +
						")" + 
						"";

		System.out.println(propuestas);
		PreparedStatement prepStmt = conn.prepareStatement(propuestas);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		List<Integer> propuestas_id = new ArrayList<>();
		while( rs.next() ) 
			propuestas_id.add(rs.getInt("ID"));

		// <Syso> para mostrar un mensaje en la Interfaz. No se por ahora como mostrarlo
		// TODO Que se muestre este mensaje en la interfaz el mensaje 
		if ( propuestas_id.size() < reserva_colectiva.getCantidad_inmuebles() )
			System.out.println("El sistema no cuenta con los suficientes inmuebles que se requieren. # " + tipo_inmueble + "s = " + propuestas_id.size());

		if ( propuestas_id.size() == 0 )
			throw new BusinessLogicException("El sistema no cuenta con inmuebles que cumplan con las restricciones requeridas: " + cadena_servicios + " para el tipo de inmueble " + tipo_inmueble);

		//reservas a realizar
		List<Reserva> reservas = new ArrayList<>();

		DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); // formato fecha SQL
		Date today = Calendar.getInstance().getTime(); // Fecha en la que se registro la reserva, supone que es el mismo dia de la transaccion        
		String fecha_registro = df.format(today);

		int suma = reserva_colectiva.getCantidad_inmuebles();
		DAOPersona dao = new DAOPersona();
		dao.setConn(this.conn);

		PROPUESTAS : for ( Integer prop : propuestas_id ) {
			if ( suma > 0 ) {
				UsuarioEnColectivo usuario = reserva_colectiva.getUusuarios().get(suma);

				Propuesta pp = dao.getPropuestaById(prop.longValue());
				Double xx = pp == null ? Math.random() * 1000000 :  pp.getSub_total();

				// id reserva / fecha registro / fecha cancelacion / fecha inicio estadia / duracion / costo total / cantidad personas / hay multa / valor multa / propuesta / cliente / id colectivo

				Reserva res = new Reserva(usuario.getId_reserva(), fecha_registro, null, reserva_colectiva.getFecha_inicio_estadia(), 
						reserva_colectiva.getDuracion(), xx , 
						usuario.getCantidad_personas(), false, 0.0, Long.parseLong(""+prop), usuario.getId(), 
						reserva_colectiva.getId());
				reservas.add(res);

			} else 
				break PROPUESTAS;
			suma--;
		}

		//REALIZA EFECTIVAMENTE LA RESERVA EN EL SISTEMA
		reservas.forEach( reserva -> {
			try {
				this.registrarReserva(reserva);
			} catch (Exception e) {
				System.out.println("FAIL CREANDO RESERVA COLECTIVA RF7");
				e.printStackTrace();
			}
		});


		return reservas;
	}


	/**
	 * RF8
	 * 
	 * 
	 * Operación inversa al registro colectivo de reserva . 
	 * El usuario indica la reserva colectiva que quiere cancelar y ALOHANDES 
	 * cancela las reservas individuales correspondientes y
	 * calcula también las penalizaciones correspondientes.
	 * @param id_colectivo_reserva id de la reserva colectiva que se quiere cancelar
	 * @throws Exception 
	 */

	public void cancelarReservaColectiva( Long id_colectivo_reserva) throws Exception {
		List<Reserva> reservas_a_cancelar= new ArrayList<>();

		String sentecnia = "SELECT * FROM RESERVAS R WHERE R.ID_COLECTIVO = " + id_colectivo_reserva;
		System.out.println(sentecnia);

		PreparedStatement prepStmt = conn.prepareStatement(sentecnia);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
			reservas_a_cancelar.add(convertResultSetTo_Reserva(rs));

		for ( Reserva reserva : reservas_a_cancelar) {
			PreparedStatement sentenciaParaCancelar;
			try {

				sentenciaParaCancelar = conn.prepareStatement("UPDATE RESERVAS SET ID_COLECTIVO = NULL WHERE ID = ?");
				sentenciaParaCancelar.setLong(1, reserva.getId());
				System.out.println(sentenciaParaCancelar);
				sentenciaParaCancelar.executeUpdate();
				try {
					cancelarReserva(reserva);
				} catch (Exception e) {
					System.out.println("FAIL ELIMIANDO DESDE RF8");
					e.printStackTrace();
				}

			} catch (SQLException e) {
				System.out.println("FAIL SQL< ELIMIANDO DESDE RF8");
				e.printStackTrace();
			}

		}
	}


	/**
	 * Retorna todas las reservas que se hicieron de manera colectiva ::
	 * por id colevtivo
	 * @param id_colectiva
	 * @return
	 * @throws Exception
	 */
	public List<Reserva> getReservasColectivas() throws Exception {
		List<Reserva> rta= new ArrayList<>();
		PreparedStatement sentenciaParaBuscar;
		sentenciaParaBuscar = conn.prepareStatement("Select * from reservas r where r.id_colectivo is not null");

		ResultSet rs = sentenciaParaBuscar.executeQuery();
		while (rs.next())
			rta.add(convertResultSetTo_Reserva(rs));

		return rta;
	}








	/**
	 * RF9
	 * 
	 * Esta operación se hace necesaria cuando por motivos externos (obras internas de mantenimiento, orden
	 * público, etc.) una oferta de alojamiento debe deshabilitarse de manera temporal. Las reservas vigentes sobre
	 * esa oferta de alojamiento deben entonces relocalizarse en las otras ofertas de alojamiento disponibles en
	 * ALOHANDES, dando prioridad a las vigentes en el momento que se realiza la operación y luego en el orden en
	 * que fueron realizadas las reservas. Siendo un caso excepcional, las reservas colectivas involucradas deben
	 * desagregarse a las reservas individuales correspondientes y puede haber reservas que no pueden ser
	 * satisfechas con la oferta disponible en el momento que se realiza la operación. ALOHANDES debe informar de
	 * manera completa y clara las operaciones realizadas, tanto el traslado exitoso de reservas como las reservas
	 * que no pudieron ser trasladadas. La oferta de alojamiento en cuestión no debe ser tenida en cuenta para
	 * reservas mientras no se haya rehabilitado (ver RF10)
	 * @param propuesta
	 * @return
	 */
	public List<Reserva> RF9_deshabilitar_propuesta ( Propuesta propuesta ) {

		Date hoy = new Date();

		//		String fecha = reserva.getFecha_inicio_estadia();
		//		DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		//		Date fecha_inicio_estadia = format.parse(fecha);

		Calendar cal = Calendar.getInstance();
		cal.setTime(hoy);


		return null;

	}











}
