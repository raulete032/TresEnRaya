package modelo;



/**
 * De una partida conocemos los dos jugadores y el ganador o empate
 * @author raul
 *
 */
public class Partida {

	
	//Variables de instancia
	private String nick1;
	private String nick2;
	private String ganador;
	
	
	/**
	 * Constructor
	 * @param nick1
	 * @param nick2
	 * @param ganador
	 */
	public Partida(String nick1, String nick2, String ganador) {
		
		this.nick1 = nick1;
		this.nick2 = nick2;
		this.ganador=ganador;		
	}


	//GETTERS y SETTERS
	
	public String getNick1() {return nick1;}
	public String getNick2() {return nick2;}
	public String getGanador() {return ganador;}

	public void setNick1(String nick1) {this.nick1 = nick1;}
	public void setNick2(String nick2) {this.nick2 = nick2;}
	public void setGanador(String ganador) {this.ganador = ganador;}
	


	/**
	 * Redefinición método toString
	 */
	@Override
	public String toString() {
		return "Nick1: " + this.nick1 + 
				"\nNick2: " + this.nick2 +
				"\nGanador: " + this.ganador;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
