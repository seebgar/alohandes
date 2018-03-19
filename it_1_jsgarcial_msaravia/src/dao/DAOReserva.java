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
	 * Metodo que agregar la informacion de una nueva reserva en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param reserva Reserva que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws BusinessLogicException Si se genera un error dentro del metodo.
	 */
	public void registrarReserva(Reserva reserva) throws SQLException, BusinessLogicException, Exception {
		
		ArrayList<Reserva> reservasEnFecha = new ArrayList<>();
		
		String reservas = String.format("SELECT * FROM RESERVAS WHERE ID = %2$d AND fecha_inicio_estadia= %3$d", USUARIO, reserva.getId(), reserva.getFecha_inicio_estadia());
		PreparedStatement prepStmt1= conn.prepareStatement(reservas);
		recursos.add(prepStmt1);	
		ResultSet rs = prepStmt1.executeQuery(); //consigo las reservas que hay para ese día
		
		while(rs.next())
			reservasEnFecha.add(convertResultSetToReserva(rs));
		
		Cliente solicitado= reserva.getCliente();
		
		for(Reserva res: reservasEnFecha) {
			
			Cliente cliente= res.getCliente();
			if(solicitado == cliente)
				throw new BusinessLogicException("No puede hacer más reservas el mismo día");
			//se valida que el cliente no haga más reservas un mismo dia
		}
		
		Propuesta propuesta= reserva.getPropuesta();
		if(propuesta.getSeVaRetirar())
			throw new BusinessLogicException("No se puede realizar la reserva porque la propuesta no esta disponible para más fechas");
		//valido que la propuesta sea vigente
		
		
		//sentencia para insertar la resrva en la base de datos
		String sql = String.format("INSERT INTO %1$s.RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s', '%7$s', '%8$s', '%9$s')", 
				USUARIO, 
				reserva.getId(), 
				solicitado.getId(),
				reserva.getPropuesta().getId(),
				reserva.getFecha_registro(),
				reserva.getFecha_cancelacion(),
				reserva.getFecha_inicio_estadia(),
				reserva.getDuracion(),
				reserva.getCosto_total());
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery(); //se inserta la reserva

	}
	
	/**
	 * Metodo que actualiza la informacion de la reserva en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param resrva Reserva que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws BusinessLogicException Si se genera un error dentro del metodo.
	 */
	public void cancelarReserva(Reserva reserva) throws SQLException, BusinessLogicException, Exception {
        
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
		
		//necesito la fecha de estadia
		String darFechaEstadia = String.format("SELECT * FROM RESERVAS WHERE ID = %2$d", USUARIO, reserva.getId());
		PreparedStatement prepStmt = conn.prepareStatement(darFechaEstadia);
		recursos.add(prepStmt);
		
		ResultSet rs = prepStmt.executeQuery();
		Reserva reserva1= convertResultSetToReserva(rs);
				
		Date fechaDeEstadia= formatoConHora.parse(reserva1.getFecha_inicio_estadia()); // obtengo la fecha e estadia

		//necesito la fecha de regisro
//		String fechaDeRegistro= String.format("SELECT * FROM RESERVAS WHERE ID = %2$d", USUARIO, reserva.getId());
//		
//		PreparedStatement prepStmt2 = conn.prepareStatement(fechaDeRegistro);
//		ResultSet rs2= prepStmt2.executeQuery();
//		Reserva reserva2= convertResultSetToReserva(rs2);
//		
//		Date fechaDeRegistroAQuedarse= formatoConHora.parse(reserva2.getFecha_registro());
		 // me da la fecha que se hizo la reserva
		
		Calendar cal= Calendar.getInstance();
		
		cal.setTime(fechaDeEstadia);
		cal.add(Calendar.DAY_OF_YEAR, -8);
		Date fechaMaxima= cal.getTime();
		
		if(fechaActual.before(fechaMaxima)){
			// primera regla de negocio, si esta antes de la fecha máxima de cancelacion se cobra el 10%
			double valorMulta= reserva.getCosto_total();
			reserva.setValorMulta(valorMulta*0.1);
			reserva.setCosto_total(valorMulta);
			StringBuilder sql = new StringBuilder();
			sql.append(String.format("UPDATE RESERVAS SET ", USUARIO));
			sql.append(String.format("FECHA_CANCELACION = '%1$s' AND HAY_MULTA = '%2$s' AND VALOR_MULTA = '%3$s' ", fechaActual.toString(), 1, reserva.getValorMulta()));
		}else if(fechaActual.after(fechaMaxima) && fechaActual.before(fechaDeEstadia)){
			//segunda regla de negocio, si se reserva despues de la fecha maxima y antes de la fecha de inicio de la estadia se cobra el 30%
			double valorMulta= reserva.getCosto_total();
			reserva.setValorMulta(valorMulta*0.3);
			reserva.setCosto_total(valorMulta);
			StringBuilder sql = new StringBuilder();
			sql.append(String.format("UPDATE RESERVAS SET ", USUARIO));
			sql.append(String.format("FECHA_CANCELACION = '%1$s' AND HAY_MULTA = '%2$s' AND VALOR_MULTA = '%3$s' ", fechaActual.toString(), 1, reserva.getValorMulta()));
		}else{
			//tercera regla de negocio (una parte), si se reserva despues de la fecha de inicio de estadia, se cobra el 50%
			double valorMulta= reserva.getCosto_total();
			reserva.setValorMulta(valorMulta*0.5);
			reserva.setCosto_total(valorMulta);
			StringBuilder sql = new StringBuilder();
			sql.append(String.format("UPDATE RESERVAS SET ", USUARIO));
			sql.append(String.format("FECHA_CANCELACION = '%1$s' AND HAY_MULTA = '%2$s' AND VALOR_MULTA = '%3$s' ", fechaActual.toString(), 1, reserva.getValorMulta()));
		}
		
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
	
	
	public Reserva convertResultSetToReserva(ResultSet resultSet) throws SQLException {
		

		Long id = resultSet.getLong("ID");
		String fecha_registro = resultSet.getString("FECHA_REGISTRO");
		String fecha_cancelacion = resultSet.getString("FECHA_CANCELACION");
		String fecha_inicio_estadia = resultSet.getString("FECHA_INICIO_ESTADIA");
		Integer duracion= resultSet.getInt("DURACION");
		Double costo_total= resultSet.getDouble("COSTO_TOTAL");
		Integer cantidad_personas= resultSet.getInt("CANTIDAD_PERSONAS");
		int multa= resultSet.getInt("HAY_MULTA");
		
		Boolean hayMulta= (multa ==1)? true :false;
		
		Double valorMulta= resultSet.getDouble("VALOR_MULTA");
		Long idCliente= resultSet.getLong("ID_PERSONA");
		Long idPropuesta= resultSet.getLong("ID_PROPUESTA");
		
		String personita = String.format("SELECT * FROM PERSONAS WHERE ID = %2$d", USUARIO, idCliente);
		PreparedStatement prepStmt = conn.prepareStatement(personita);
		ResultSet rs2= prepStmt.executeQuery();

		Persona personaRequerida= persona.convertResultSetTo_Persona(rs2);
		Cliente cliente= new Cliente(personaRequerida.getId(), personaRequerida.getNombre()
				, personaRequerida.getApellido(), personaRequerida.getTipo()
				, personaRequerida.getRol(), personaRequerida.getNit(), personaRequerida.getCedula(), personaRequerida.getEmail());
		
		String propuestica = String.format("SELECT * FROM PROPUESTAS WHERE ID = %2$d", USUARIO, idPropuesta);
		PreparedStatement prepStmt2 = conn.prepareStatement(propuestica);
		ResultSet rs3= prepStmt2.executeQuery();
		Propuesta propuesta= persona.convertResultSetTo_Propuesta(rs3);
		
		Reserva reserva= new Reserva(id, fecha_registro, fecha_cancelacion, fecha_inicio_estadia, duracion, costo_total, cantidad_personas, hayMulta, valorMulta, propuesta, cliente);

		return reserva;
	}
	
	
	public static void main(String[] args){
		
		Date fechaActual = new Date();
        System.out.println(fechaActual);
        System.out.println("---------------------------------------------");
        
        //Formateando la fecha:
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Son las: "+formatoHora.format(fechaActual)+" de fecha: "+formatoFecha.format(fechaActual));
        
        //Fecha actual desglosada:
        Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        
     

        System.out.println("Fecha Actual: "+ dia + "/" + (mes) + "/" + año);
        System.out.printf("Hora Actual: %02d:%02d:%02d %n", hora, minuto, segundo);
        System.out.println("-------------Fecha desglosada----------------");
        System.out.println("El año es: "+ año);
        System.out.println("El mes es: "+ mes);
        System.out.println("El día es: "+ dia);
        System.out.printf("La hora es: %02d %n", hora);
        System.out.printf("El minuto es: %02d %n", minuto);
        System.out.printf("El segundo es: %02d %n", segundo);
        
		Calendar cal = Calendar.getInstance();
		String date1= "17/03/2018";
		Date date;
		try {
			date = formatoFecha.parse(date1);
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_YEAR, -8);
			System.out.println("la resta es -----> "+cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
}
