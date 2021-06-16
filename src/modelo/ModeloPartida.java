package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import defecto.Conexion;
import defecto.LibreriaFechas;


/**
 * Desde esta clase opero la Tabla Partidas
 * @author raul
 *
 */
public class ModeloPartida {
	
	
	//Variables de instancia
	private Conexion conect;
	private Statement stmt;
	private ResultSet rs;
	
	
	
	/**
	 * Constructor
	 * @throws SQLException 
	 */
	public ModeloPartida(Conexion conect) throws SQLException {
		
		this.conect = conect;
		
		creaStatement();
		creaConsulta();		
	}
	
	/**
	 * Método que crea el Statement
	 * @throws SQLException
	 */
	public void creaStatement() throws SQLException {
		
		this.stmt = this.conect.creaStatement();
	}
	
	
	
	/**
	 * Método que cierra el Statement
	 * @throws SQLException
	 */
	public void cierraStatement() throws SQLException {
		this.stmt.close();
	}
	
	
	
	/**
	 * Método que crea la consulta que muestra TODAS las partidas
	 * @throws SQLException
	 */
	public void creaConsulta() throws SQLException {
		
		String sqlSelect = "select * from tresenraya.partidas";
		
		this.rs = this.stmt.executeQuery(sqlSelect);
		
	}
	
	
	/**
	 * Método que muestra todas las partidas jugadas hasta ese momento.
	 * @return
	 * @throws SQLException
	 */
	public String [][] historico() throws SQLException{
		
		String sqlSelect = "select * from tresenraya.partidas";
		
		ResultSet rsConsulta = null;
		rsConsulta = this.stmt.executeQuery(sqlSelect);
		
		//Averiguar el número de filas devueltas
		rsConsulta.last();
		int numFilas = rsConsulta.getRow();
		rsConsulta.first();
		
		String [][] datos = new String[numFilas][5];
		
		for(int f=0; f<numFilas; f++) {
			
			datos[f][0] = Integer.toString(rsConsulta.getInt("codPartida"));
			datos[f][1] = LibreriaFechas.getFechaPersonalizada(LibreriaFechas.convierteDateACalendar(new Date (rsConsulta.getTimestamp("Fecha").getTime())));
			datos[f][2] = rsConsulta.getString("nick1");
			datos[f][3] = rsConsulta.getString("nick2");
			datos[f][4] = rsConsulta.getString("ganador");
			rsConsulta.next();
		}
		
		this.creaConsulta();
		
		return datos;		
	}
	
	
	
	/**
	 * Método que inserta una partida en el histórico de partidas
	 * @param juego
	 * @throws SQLException 
	 */
	public void insertaPartida(Partida juego) throws SQLException {		
		
		String sqlInsert = "insert into tresenraya.partidas (nick1, nick2, ganador) " +
							"\nvalues('" + juego.getNick1() + "', '" + juego.getNick2() + "', '" + juego.getGanador() + "')";
		
		System.out.println("Se va a ejecutar: " + sqlInsert);
		
		this.stmt.executeUpdate(sqlInsert);
		
		this.creaConsulta();
	}
	
	
	
	/**
	 * Método que devuelve el número de victorias de un determinado nick
	 * @param nick
	 * @return
	 * @throws SQLException
	 */
	public int victoriaJugadorX(String nick) throws SQLException {
		
		String sqlSelect = "select count(*) " +
							"\nfrom tresenraya.partidas " +
							"\nwhere ganador = '" + nick + "'";
		
		System.out.println("Se va a ejecutar: " + sqlSelect);
		
		ResultSet rs = this.stmt.executeQuery(sqlSelect);
		
		rs.first();
		
		int victorias = rs.getInt(1);
		
		return victorias;		
	}
	
	
	/**
	 * Método que devuelve el número de victorias de un determinado nick
	 * @param nick
	 * @return
	 * @throws SQLException
	 */
	public int empateJugadorX(String nick) throws SQLException {
		
		String sqlSelect = "select count(*) " +
							"\nfrom tresenraya.partidas " +
							"\nwhere ganador = 'empate' and (nick1 = '" + nick + "' or nick2 = '" + nick +"')";
		
		System.out.println("Se va a ejecutar: " + sqlSelect);
		
		ResultSet rs = this.stmt.executeQuery(sqlSelect);
		
		rs.first();
		
		int empates = rs.getInt(1);
		
		return empates;		
	}
	
	
	
	/**
	 * Método que devuelve el número de victorias de un determinado nick
	 * @param nick
	 * @return
	 * @throws SQLException
	 */
	public int derrotaJugadorX(String nick) throws SQLException {
		
		String sqlSelect = "select count(*) " +
							"\nfrom tresenraya.partidas " +
							"\nwhere (nick1 = '" + nick + "' or nick2 = '" + nick + "' ) and (ganador <> 'empate' and ganador <> '" + nick + "')";
		
		System.out.println("Se va a ejecutar: " + sqlSelect);
		
		ResultSet rs = this.stmt.executeQuery(sqlSelect);
		
		rs.first();
		
		int derrotas = rs.getInt(1);
		
		return derrotas;		
	}
	
	
	/**
	 * Método que modifica el nick del usuario del campo Ganador
	 * @param nick
	 * @throws SQLException 
	 */
	public void modificaGanador(String nick, String nickNuevo) throws SQLException {
		
		String sqlUpdate = "update tresenraya.partidas " +	
							"\nset ganador = '" + nickNuevo + "' "+
							"\nwhere ganador = '" + nick + "' ";

		System.out.println("Se va a ejecutar: " + sqlUpdate);


		this.stmt.executeUpdate(sqlUpdate);
		
		this.creaConsulta();		
	}
	
	
	
	/**
	 * Método que borra TODOS los registros de la tabla Partidas
	 * @throws SQLException 
	 */
	public void borraTodo() throws SQLException {		
		
		this.creaConsulta(); //ahora tengo en this.rs toda la tabla
					
		while(this.rs.next()) { //mientras haya un siguiente
			
			String sqlDelete = "delete from tresenraya.partidas " +
								"\nwhere codPartida = " + this.rs.getInt("codPartida");
			
			System.out.println("Se va a ejecutar: " + sqlDelete);
			
			this.stmt.executeUpdate(sqlDelete); //elimino ese registro
			
			this.creaConsulta(); //actualizo la consulta ya que sino da error
		}
		
		this.creaConsulta(); //vuelvo a crear una consulta (esta vez estará vacía)
		
	}
	
	
	/**
	 * Método que vuelve a cargar los datos
	 * @throws SQLException
	 */
	public void carga() throws SQLException, FileNotFoundException, NumberFormatException {

		try(Scanner sc = new Scanner (new File("src/imagenesYarchivos/Partidas.txt")).useDelimiter(",|\\r\\n")){

			String nick1;
			String nick2;
			String ganador;

			while(sc.hasNext()) {

				nick1= sc.next();
				nick2= sc.next();
				ganador= sc.next();

				String sqlInsert = "insert into tresenraya.partidas (Nick1, Nick2, Ganador) " + 
						"\nvalues('" + nick1 + "', '" + nick2 + "', '" + ganador + "' )"; 

				System.out.println("Se va a ajecutar " + sqlInsert);
				
				stmt.executeUpdate(sqlInsert);
			}
		}		

	}
	
	
	
	
	
	
	

}
