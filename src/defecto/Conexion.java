package defecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * En esta clase se crea la conexi�n con la base de datos
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
	 * M�todo que crea la conexi�n
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public void creaConexion() throws ClassNotFoundException, SQLException {		
			//Registrar la conexi�n / Levantar el JDBC
			String driver = "com.mysql.cj.jdbc.Driver";
			Class.forName(driver);
			System.out.println("Conexi�n regristrada");
			
			String url = "jdbc:mysql://localhost:3306/tresenraya";
			String usuario = "admin";
			String pass = "1234";
			
			this.conect = DriverManager.getConnection(url, usuario, pass);
			
			System.out.println("Conexi�n establecida");		
	}
	
	
	/**
	 * M�todo que cierra la conexi�n
	 * @throws SQLException 
	 */
	public void cerrarConexion() throws SQLException {				
			this.conect.close();
			System.out.println("Conexi�n cerrada");				
	}
	
	
	
	/**
	 * M�todo que crea el Statement
	 * @return
	 * @throws SQLException
	 */
	public Statement creaStatement() throws SQLException {		
		return this.conect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
	}
	
	
	
	
	
	
	
}
