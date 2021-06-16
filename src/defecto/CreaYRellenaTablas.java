package defecto;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CreaYRellenaTablas {

	public static void main(String[] args) {

		
		Connection conexion = null;		
		
		//Llamo a la puerta
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			Class.forName(driver);
			System.out.println("Conexión registrada");
			
			String url = "jdbc:mysql://localhost:3306/tresenraya";
			String user = "admin";
			String pass = "1234";
			conexion = DriverManager.getConnection(url, user, pass);
			
			System.out.println("Conexión establecida");
			
			creaTablas(conexion);
		
			rellenaTablaJugadores(conexion);
			rellenaTablaPartidas(conexion);
			
		}
		catch(SQLException e) {		
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
			try {
				conexion.close();
				System.out.println("Conexión cerrada");
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		
	}
	
	
	/**
	 * Método que crea las tablas
	 * @param con
	 * @throws SQLException 
	 */
	public static void creaTablas(Connection con) throws SQLException {
		
		Statement stmt = null;
		//**************
		//CREO TABLA JUGADORES
		//**************
		String createJugadores = "create table tresenraya.jugadores " +
								"\n(Nick VARCHAR(20) not null primary key, " +
								"\nPuntos INT default 0)";
		
		System.out.println("Se va a ejecutar: " + createJugadores);
		
		
		
		stmt = con.createStatement();
		stmt.executeUpdate(createJugadores);
		System.out.println("Tabla jugadores creada");
		stmt.close();
		
		
		//****************
		//CREO TABLA PARTIDAS
		//****************		
		String createPartidas = "create table tresenraya.partidas " + 
								"\n(CodPartida int not null AUTO_INCREMENT primary key, "+
								"\nFecha date default current_date, " +
								"\nNick1 VARCHAR(20), " + 
								"\nNick2 VARCHAR(20), " +
								"\nGanador VARCHAR(20))";
		
		System.out.println("Se va a ejecutar: " + createPartidas);
		
		
		
		stmt = con.createStatement();
		
		stmt.executeUpdate(createPartidas);
		System.out.println("Tabla partidas creada");
		stmt.close();
		
		
		
		//****************
		//MODIFICO TABLA PARTIDAS PARA AÑADIR FK
		//****************		
		String alterPartidas1 = "alter table tresenraya.partidas " +
								"\nadd constraint FK_partidas_jugdores_01 foreign key (Nick1) references tresenraya.jugadores(Nick) " + 
								"\non delete set null " +
								"\non update cascade";
		
		String alterPartidas2 = "alter table tresenraya.partidas " +
								"\nadd constraint FK_partidas_jugdores_02 foreign key (Nick2) references tresenraya.jugadores(Nick) " + 
								"\non delete set null " +
								"\non update cascade";
		
		System.out.println("Se van a ejecutar : " + "\n" + alterPartidas1 + "\n\n" + alterPartidas2);
		
		
		stmt = con.createStatement();
		stmt.executeUpdate(alterPartidas1);
		System.out.println("Primera FK");
		stmt.close();
		
		
		stmt = con.createStatement();
		stmt.executeUpdate(alterPartidas2);
		System.out.println("Segunda FK");
		stmt.close();		
	}
	
	
	
	
	/**
	 * Método que rellena las tablas con datos previos
	 * @throws SQLException 
	 */
	public static void rellenaTablaJugadores(Connection con) throws SQLException {
		
		Statement stmt = con.createStatement();
		
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
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
		finally {
			stmt.close();
		}
		
	}
	
	
	
	/**
	 * Método que rellena la tabla de las Partidas
	 * @param con
	 * @throws SQLException 
	 */
	public static void rellenaTablaPartidas(Connection con) throws SQLException {
		
		Statement stmt = con.createStatement();
		
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
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
		finally {
			stmt.close();
		}
		
		
	}
	
	
	
	
	
	
	

}
