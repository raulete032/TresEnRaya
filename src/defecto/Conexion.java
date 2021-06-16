package defecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * En esta clase se crea la conexión con la base de datos
 * @author raul
 *
 */


public class Conexion {
	
	//Variables de instancia
	private Connection conect;
	
	
	/**
	 * Constructor
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Conexion() throws ClassNotFoundException, SQLException {
		creaConexion();		
	}
	

	/**
	 * Método que crea la conexión
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public void creaConexion() throws ClassNotFoundException, SQLException {		
			//Registrar la conexión / Levantar el JDBC
			String driver = "com.mysql.cj.jdbc.Driver";
			Class.forName(driver);
			System.out.println("Conexión regristrada");
			
			String url = "jdbc:mysql://localhost:3306/tresenraya";
			String usuario = "admin";
			String pass = "1234";
			
			this.conect = DriverManager.getConnection(url, usuario, pass);
			
			System.out.println("Conexión establecida");		
	}
	
	
	/**
	 * Método que cierra la conexión
	 * @throws SQLException 
	 */
	public void cerrarConexion() throws SQLException {				
			this.conect.close();
			System.out.println("Conexión cerrada");				
	}
	
	
	
	/**
	 * Método que crea el Statement
	 * @return
	 * @throws SQLException
	 */
	public Statement creaStatement() throws SQLException {		
		return this.conect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
	}
	
	
	
	
	
	
	
}
