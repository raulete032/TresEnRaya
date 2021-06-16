package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import defecto.Conexion;


/**
 * Desde esta clase opero la Tabla Jugadores
 * @author raul
 *
 */


public class ModeloJugador {
	
	
	//Variables de instancia
	private Conexion conect;
	private Statement stmt;
	private ResultSet rs;	
	
	
	/**
	 * Constructor
	 * @throws SQLException 
	 */
	public ModeloJugador(Conexion conect) throws SQLException {
		
		this.conect = conect;
		
		creaStatement(); //creo el Statement
		creaConsulta(); //creo la consulta (ResultSet)	
	}
	
	
	/**
	 * Método que crea el Statement
	 * @throws SQLException
	 */
	public void creaStatement() throws SQLException {
		
		this.stmt  = this.conect.creaStatement();
	}
	
	
	/**
	 * Método que cierra el Statement
	 * @throws SQLException
	 */
	public void cierraStatement() throws SQLException {
		this.stmt.close();
	}
	
	
	
	/**
	 * Método que crea la consulta que muestra TODOS los jugadores
	 * @throws SQLException
	 */
	public void creaConsulta() throws SQLException {
		
		String sqlSelect = "select * from tresenraya.jugadores";
		
		this.rs = this.stmt.executeQuery(sqlSelect);
	}
		
	
	/**
	 * Muestra el ranking de los jugadores. Ordenados de descendentemente por puntos
	 * @throws SQLException 
	 */
	public String [][] ranking() throws SQLException {
		
		String sqlSelect = "select * from tresenraya.jugadores " +
							"\norder by puntos desc";
		
		ResultSet rsConsulta = null;
		rsConsulta = this.stmt.executeQuery(sqlSelect);
		
		//Averiguar el número de filas devueltas
		rsConsulta.last();
		int numFilas = rsConsulta.getRow();
		rsConsulta.first();
		
		String [][] datos = new String[numFilas][3];
		
		
		for(int f = 0; f<numFilas; f++) {
			datos[f][0] = String.valueOf(f+1);
			datos[f][1] = rsConsulta.getString("nick");
			datos[f][2] = Integer.toString(rsConsulta.getInt("puntos"));			
			rsConsulta.next();			
		}
		
		this.creaConsulta();
		
		return datos;		
	}
	
	
	/**
	 * Método que devuelve el jugador con más puntos
	 * @return
	 * @throws SQLException
	 */
	public Jugador getPrimero() throws SQLException {
		String sqlSelect = "select * from tresenraya.jugadores order by puntos desc";
		this.rs = this.stmt.executeQuery(sqlSelect);
		rs.first();
		return this.creaJugador();		
	}
	
	
	
	/**
	 * Método que recoge una fila de la tabla de la consulta y la devuelve como objeto Jugador
	 * @return
	 * @throws SQLException
	 */
	private Jugador creaJugador() throws SQLException {
		
		return new Jugador (rs.getString("nick"),
					rs.getInt("puntos"));		
	}
		
	
	/**
	 * Método que inserta un jugador en la base de datos
	 * @param player
	 * @throws SQLException
	 */
	public void insertaJugador(Jugador player) throws SQLException {
		
		
		if(!buscaJugador(player)) { //NO existe. Lo crea
			String sqlInsert = "insert into tresenraya.jugadores (nick, puntos) " + 
					"\nvalues('" + player.getNick() + "', " + player.getPuntos() +")";

			System.out.println("se va a ejecutar: " + sqlInsert);

			this.stmt.executeUpdate(sqlInsert);

			this.creaConsulta();
			
			return;			
		}
		else { //existe, entonces lo actualiza	
			updateJugador(player);			
		}	
	}
	
	
	
	/**
	 * Método que actualiza los puntos del jugador ya existente
	 * @param player
	 * @throws SQLException 
	 */
	private void updateJugador(Jugador player) throws SQLException {
		
		String sqlUpdate = "update tresenraya.jugadores " +
							"\nset puntos = puntos + " + player.getPuntos() +
							"\nwhere nick= '" + player.getNick() + "'";
		
		System.out.println("Se va a ejecutar: " + sqlUpdate);
		
		this.stmt.executeUpdate(sqlUpdate);
		
		this.creaConsulta();		
	}
	
		
	
	
	/**
	 * Método que nos dice si un jugador ya existe en la base de datos
	 * @param Objeto Jugador
	 * @return
	 * @throws SQLException
	 */
	private boolean buscaJugador(Jugador player) throws SQLException {
		
		String sqlSelect = "select * from tresenraya.jugadores where nick= '" + player.getNick() + "'";
		
		System.out.println("Se va a ejecutar: " + sqlSelect);
		
		ResultSet rs= this.stmt.executeQuery(sqlSelect);
		rs.first();
		int existe = rs.getRow(); //esto devuelve 0 o 1, ya que estoy filtrando por el Nick (PK)
		
		if(existe==1)
			return true;
		return false;
		
	}
	
	
	/**
	 * Método que nos dice si un jugador existe en la base de datos.
	 * @param nick del jugador
	 * @return
	 * @throws SQLException
	 */
	public boolean buscaJugador(String nick) throws SQLException {
		
		
		String sqlSelect = "select * from tresenraya.jugadores where nick = '" + nick +"'";
		
		System.out.println("Se va a ejecutar: " + sqlSelect);
		
		ResultSet rs = this.stmt.executeQuery(sqlSelect);
		
		rs.first(); //me pongo en el primero
		int existe = rs.getRow(); //esto devuelve 0 o 1. Así sé si existe o no
		
		if(existe==1)
			return true;
		return false;
		
	}
	
	
	
	/**
	 * Método que devuelve una matriz con los tres mejores jugadores
	 * @return
	 * @throws SQLException
	 */
	public String [][] tresMejores() throws SQLException {
		
		String sqlSelect = "select * from tresenraya.jugadores order by puntos desc limit 3";
				
		ResultSet rsConsulta = null;
		rsConsulta = this.stmt.executeQuery(sqlSelect);
		
		//Averiguar el número de filas devueltas
		rsConsulta.last();
		int numFilas = rsConsulta.getRow();
		rsConsulta.first();
		
		String [][] datos = new String[numFilas][3];
		
		
		for(int f = 0; f<numFilas; f++) {
			datos[f][0] = String.valueOf(f+1);
			datos[f][1] = rsConsulta.getString("nick");
			datos[f][2] = Integer.toString(rsConsulta.getInt("puntos"));			
			rsConsulta.next();			
		}
		
		this.creaConsulta();
		
		return datos;		
	}
	
	
	
	
	
	
	/**
	 * Muestra los jugadores que han jugado, al menos, una partida.
	 * @throws SQLException 
	 */
	public String [][] hanJugado() throws SQLException {
		
		String sqlSelect = "select distinct nick "+
							"\nfrom jugadores " +
							"\njoin partidas on (nick = nick1 or nick = nick2)";
		
		
		ResultSet rsConsulta = null;
		rsConsulta = this.stmt.executeQuery(sqlSelect);
		
		//Averiguar el número de filas devueltas
		rsConsulta.last();
		int numFilas = rsConsulta.getRow();
		rsConsulta.first();
		
		String [][] datos = new String[numFilas][1];
		
		
		for(int f = 0; f<numFilas; f++) {
			datos[f][0] = rsConsulta.getString("nick");						
			rsConsulta.next();			
		}
		
		this.creaConsulta();
		
		return datos;		
	}
	
	
	
	/**
	 * Método que modifica el nick de un jugador
	 * @param nick
	 * @throws SQLException 
	 */
	public void modificaJugador(String nick, String nickNuevo) throws SQLException {
		
		//Si estoy aquí es porque ambos parámetros son correctos
		
		String sqlUpdate = "update tresenraya.jugadores " +	
							"\nset nick = '" + nickNuevo + "' "+
							"\nwhere nick = '" + nick + "' ";
		
		System.out.println("Se va a ejecutar: " + sqlUpdate);		
		
		this.stmt.executeUpdate(sqlUpdate);
				
		this.creaConsulta(); //guardo los cambios en al ResultSet		
	}
	
	
	
	/**
	 * Método que elimina un jugador de la tabla Jugadores.
	 * @param nick
	 * @throws SQLException 
	 */
	public void eliminaJugador(String nick) throws SQLException {
		
		String sqlDelete = "delete from tresenraya.jugadores " +
							"\nwhere nick = '" + nick + "' ";
		
		System.out.println("Se va a ejecutar: " + sqlDelete);
		
		this.stmt.executeUpdate(sqlDelete);
		
		this.creaConsulta();		
	}
	
	
	
	/**
	 * Método que borra TODOS los jugadores
	 * @throws SQLException 
	 */
	public void borraTodo() throws SQLException {		
		
		this.creaConsulta(); //ahora en this.rs hay TODA la tabla
		
		while(this.rs.next()) { //mientras haya un siguiente
			
			String sqlDelete = "delete from tresenraya.jugadores " +
								"\nwhere nick = '" + this.rs.getString("nick") + "' ";
			
			System.out.println("Se va a ejecutar: " + sqlDelete);
			
			this.stmt.executeUpdate(sqlDelete);
			
			this.creaConsulta(); //actualizo la consulta ya que sino da error
			
		}		
		this.creaConsulta(); //actualizo la consulta. Ahora estará vacía		
	}
	
	
	
	
	/**
	 * Método que vuelve a cargar los datos 
	 * @param conect
	 * @throws SQLException
	 */
	public void carga() throws SQLException, FileNotFoundException, NumberFormatException {				
		
		try(Scanner sc = new Scanner (new File("src/imagenesYarchivos/Jugadores.txt")).useDelimiter(",|\\r\\n")){
			
			String nick;
			int puntos;
			
			while(sc.hasNext()) {
				
				nick = sc.next();
				puntos = Integer.parseInt(sc.next());
				
				String sqlInsert = "insert into tresenraya.jugadores (Nick, Puntos) " + 
									"\nvalues('" + nick + "', " + puntos + ")";
				
				System.out.println("Se va a ajecutar " + sqlInsert);
				
				stmt.executeUpdate(sqlInsert);			
			}			
		}
			
	}
	
	
	

}
