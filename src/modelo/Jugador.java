package modelo;

/**
 * De un jugador conocemos su Nick (será único) y los puntos que lleva
 * @author raul
 * 
 */

public class Jugador {
	
	
	//Variables de instancia
	private String nick;
	private int puntos;
	
	
	
	/**
	 * Constructor
	 * @param nick
	 */
	public Jugador(String nick) {		
		this.nick = nick;
		this.puntos = 0;
	}
	
	
	/**
	 * Constructor
	 * @param nick
	 * @param puntos
	 */
	public Jugador(String nick, int puntos) {
		this.nick = nick;
		this.puntos = puntos;
	}

	

	//GETTERS y SETTERS
	public String getNick() {return nick;}
	public int getPuntos() {return puntos;}

	public void setNick(String nick) {this.nick = nick;}
	public void setPuntos(int puntos) {this.puntos = puntos;}

	
	
	/**
	 * Redefinición método toString
	 */
	@Override
	public String toString() {
		
		return "Nick: " + this.nick + ". Puntos: " + this.puntos;
	}
	
	
	
	
	
	
	
	
	

}
