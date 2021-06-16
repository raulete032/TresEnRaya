package modelo;

public class ModeloFuncionamiento {
	
	
	
	//Variable de instancia
	private Ficha[] fichas;
	private byte contador;
	
	
	/**
	 * Constructor
	 */
	public ModeloFuncionamiento() {
		
		this.fichas = new Ficha[9]; //hasta completar las 9
		this.contador=1;
	}
	
	
	
	/**
	 * M�todo que crea las fichas y les asigna las coordenadas
	 */
	public void creaFicha(int fila, int colum, String color) {
		
		Ficha ficha = new Ficha(fila, colum, color);
		this.fichas[contador-1]= ficha; //se lo pongo as�, para luego poder saber a quien le toca
		contador++;		
	}
	
	
	/**
	 * M�todo que nos dice si existe una determinada ficha
	 */
	public boolean buscaFicha(int fila, int colum) {
		
		boolean sw = false;
		Ficha ficha = null;
		
		for(int i=0; i<fichas.length && !sw; i++) {
			
			ficha = this.fichas[i];
			
			if(ficha==null)
				sw= true;
			
			else if(ficha.getFila()==fila && ficha.getColumna()==colum)
				sw=true;
		}		
		if(ficha!=null)
			return false; //existe, luego NO puede colocarla
		
		return true; //NO existe, luego S� puede colocarla
	}


	/**
	 * M�todo que devuelve la ficha de esa coordenada
	 * @param fila
	 * @param colum
	 * @return
	 */
	public Ficha devuelveFicha(int fila, int colum) {
		
		boolean sw = false;
		Ficha ficha = null;
		
		for(int i=0; i<fichas.length && !sw; i++) {
			
			ficha=this.fichas[i]; //obtengo la ficha de esa posici�n en el array
			
			if(ficha==null) //no hay ficha, luego se puede salir del bucle, pues no hay m�s fichas en adelante
				sw=true;
			
			else if(ficha.getFila()==fila && ficha.getColumna() == colum) //si es esa ficha se sale
				sw=true;		
		}
		
		if(!sw) //sw a�n es nulo, es que NO encontr� esa ficha
			return null;
		
		return ficha;	
	}
	
	
	//GETTERS
	public Ficha[] getFichas() {return fichas;}
	public byte getContador() {return contador;}
	
	public void setFichas(Ficha[] fichas) {this.fichas = fichas;}
	public void setContador(byte contador) {this.contador = contador;}
	
	
	
	
	
	
	

}
