package modelo;
/**
 * De una ficha conocemos sus coordenadas en la matriz de botones (fila, columna) y su color
 * @author raul
 *
 */
public class Ficha {

	
	//Variable de instancia
	private int fila, columna;
	private String color;
		
		
		/**
		 * Constructor
		 */
		public Ficha(int fila, int columna, String color) {
			
			this.fila= fila;
			this.columna= columna;
			this.color = color;
		}


		//GETTERS y SETTERS
		public int getFila() {return fila;}
		public int getColumna() {return columna;}
		public String getColor() {return color;}
		
		public void setFila(int fila) {this.fila = fila;}
		public void setColumna(int columna) {this.columna = columna;}
		public void setColor(String color) {this.color = color;}
	
	
	
	
	
	
	
	
	
	
	
}
