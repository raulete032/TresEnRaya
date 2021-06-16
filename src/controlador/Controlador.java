package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import defecto.Conexion;
import defecto.MiExcepcion;
import modelo.*;
import vista.*;


public class Controlador extends WindowAdapter implements ActionListener{

	
	//Variables de instancia
	private Vista miVista;
	private ModeloJugador mJugador;
	private ModeloPartida mPartida;
	private ModeloFuncionamiento mFunciona;
	private Conexion conect;
	
	/**
	 * Constructor
	 */
	public Controlador(Vista v) {
		
		try {
			this.conect = new Conexion(); //establezco la conexión

			this.miVista = v;
			
			this.mJugador = new ModeloJugador(conect); //creo el Statement y la consulta
			this.mPartida = new ModeloPartida(conect);
		} 
		catch (SQLException e) {
			mensaje("Error al conectar", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		catch(ClassNotFoundException e) {
			mensaje("Error al conectar", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		this.mFunciona = new ModeloFuncionamiento(); //este es el objeto que hace que funcione la app
		
		
	}
	
	
	
	
	//*************************
	//ACTION LISTENER
	//*************************	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
						
		if(arg0.getSource() == this.miVista.getComenzar()) //se pulsa Comenzar
			comenzar();
		
		else if(arg0.getSource() == this.miVista.getRanking()) //se pulsa Ranking
			ranking();
		
		else if(arg0.getSource() == this.miVista.getHistorico()) //se pulsa histórico
			historico();
		
		else if(arg0.getSource() == this.miVista.getVictorias())
			victoriasJugadorX();		
				
		else if(arg0.getSource() == this.miVista.getEmpates())
			empatesJugadorX();
		
		else if(arg0.getSource() == this.miVista.getDerrotas())
			derrotasJugadorX();
				
		else if(arg0.getSource() == this.miVista.getHistoricoJugador()) 
			historicoJugadorX();	
	
		else if(arg0.getSource() == this.miVista.getRegistrarse())
			registrar();
		
		else if(arg0.getSource() == this.miVista.getTop())
			mejor();		
		
		else if(arg0.getSource() == this.miVista.getTop3())
			tresMejores();
		
		else if(arg0.getSource() == this.miVista.getHanJugado())
			hanJugado();
		
		else if(arg0.getSource() == this.miVista.getModificar())
			modificaNick();
		
		else if(arg0.getSource() == this.miVista.getEliminar())
			banearNick();			
		
		else if(arg0.getSource() == this.miVista.getEliminarTodo())
			borraTodo();
		
		else if(arg0.getSource() == this.miVista.getCargar())
			cargarDatos();
		
		else { //la última opción son los botones del tablero
			
			int fila = (int)arg0.getActionCommand().charAt(0); //esto me devuelve el valor ASCII del primer caracter
			int col = (int)arg0.getActionCommand().charAt(1);
			
			metodoPrincipal(fila-48, col-48); //a le resto 48, ya que el 0 es el número 48. Luego si el primer carácter es un 0 me devolvería 48, de modo que le resto 48
			
		}
		
	}
	
	
	//********************
	//ITEM LISTENER
	//********************
	
	
	
	
	
	
	
	
	
	//******************
	//WINDOW ADAPTER
	//*****************
	@Override
	public void windowClosing(WindowEvent arg0) {
		
		try {
			this.mJugador.cierraStatement();
			this.mPartida.cierraStatement();
			this.conect.cerrarConexion();			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		System.exit(0);
		
	}
	
	
	
	
	
	
	
	//*******************************
	//MÉTODOS PARA EL FUNCIONAMIENTO DE LA APP
	//*******************************
	
	
	
	/**
	 * Método que hace empezar la partida
	 */
	private void comenzar() {
		
		//Compruebo que se han introducido los nicks		
		String nick1 = this.miVista.getNick1().getText();
		String nick2 = this.miVista.getNick2().getText();
		
		if(nick1.equals("") || nick2.equals("")) {
			this.mensaje("Debes introducir los nicks", "Error", JOptionPane.ERROR_MESSAGE);
			return; //se sale del método
		}
		
		else if(nick1.equals(nick2)) {
			this.mensaje("No puedes jugar contigo mismo", "Error", JOptionPane.ERROR_MESSAGE);
			return;//se sale del método
		}
		//En este punto se han introducido los dos nicks
		//Oculto los botones, pongo setEditable false a los JTextField y muestro el primer turno
		deshabilita();
		habilita(); //habilita tablero
	}
	
	
	/**
	 * Método que oculta, deshabilita componentes y muestra el turno del primer jugador
	 */
	private void deshabilita() {
		
		this.miVista.getComenzar().setVisible(false);
		this.miVista.getRanking().setVisible(false);
		this.miVista.getHistorico().setVisible(false);
		
		this.miVista.getNick1().setEditable(false);
		this.miVista.getNick2().setEditable(false);
		
		this.miVista.getSubTit().setVisible(true);
		this.miVista.getTurno().setText(this.miVista.getNick1().getText());
		this.miVista.getTurno().setVisible(true);
	}
	
	/**
	 * Método que habilita el tablero para poder jugar
	 */
	private void habilita() {
		
		for(int f=0; f<this.miVista.getTablero().length; f++) {
			
			for(int c=0; c<this.miVista.getTablero()[f].length; c++) {
				
				this.miVista.getTablero()[f][c].setEnabled(true);
			}			
		}		
	}
	
	
	
	
	
	/**
	 * Método principal. Desde este método se llama al resto de métodos
	 * @param fila --> 
	 * @param colum -->
	 * 					--> el botón (fila, colum) que se ha pulsado
	 */
	private void metodoPrincipal(int fila, int colum) {
		
		boolean gana;		
		
		//Compruebo si existe ya una ficha con esas coordenadas
		
		if(!this.mFunciona.buscaFicha(fila, colum)) {//si ya existe, muestra mensaje y se sale
			mensaje("Movimiento no permitido", "Info", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//En este punto NO existe en esa posición, por tanto la puedo crear y colocar
		colocaFicha(fila, colum);
		
		gana = comprueba3Raya(); //compruebo si ha hecho 3 en raya
		
		if(gana || this.mFunciona.getContador()==10) { //si alguien gana o se ha empatado
			
			int ganador = insertaJugador();
			
			if(ganador==1)
				mensaje("Ha ganado " + this.miVista.getNick1().getText(), "Info", JOptionPane.INFORMATION_MESSAGE);
			
			else if(ganador==2)
				mensaje("Ha ganado " + this.miVista.getNick2().getText(), "Info", JOptionPane.INFORMATION_MESSAGE);
			
			else{
				mensaje("Empate", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			
			insertaPartida(ganador);
			porDefecto();
			
			return;
		}
			
		
		
		if((this.mFunciona.getContador())%2==0) 
			this.miVista.getTurno().setText(this.miVista.getNick2().getText());		
		else
			this.miVista.getTurno().setText(this.miVista.getNick1().getText());		
	}
	
	
	/**
	 * Método que coloca la ficha en el tablero
	 * @param fila
	 * @param colum
	 */
	private void colocaFicha(int fila, int colum) {
		
		String color;
		
		if(this.mFunciona.getContador()%2==0) //es par le toca a las rojas
			color="Roja";
		
		else //es impar le toca a las blancas
			color="Negro";
		
		this.mFunciona.creaFicha(fila, colum, color); //creo la ficha (se añade al array)
		
		if(color.equals("Roja")) //si es roja, a esa coordenada le pongo su ficha
			this.miVista.getTablero()[fila][colum].setIcon(new ImageIcon("src/imagenesYarchivos/cruzRoja.png"));
		
		else //si es blanca, le pongo la blanca
			this.miVista.getTablero()[fila][colum].setIcon(new ImageIcon("src/imagenesYarchivos/circuloNegro.png"));	
	}
	
	
	/**
	 * Método que comprueba si se ha hecho 3 en raya
	 * @return
	 */
	private boolean comprueba3Raya() {
		
		int turno = this.mFunciona.getContador()-1; //le resto uno, ya que el método creaFicha() al crear la ficha le suma ++ al contador (le toca al siguiente)
													//pero antes de que le toque, hay que comprobar si el que acaba de jugar ha hecho 3 en raya
		
		String color;
		
		if(turno%2==0) //compruebo a que color le ha tocado antes ya que solo compruebo las de ese color
			color="Roja";
		else
			color="Negro";
		
		Ficha ficha = null;
		boolean sw=false;
		boolean sw2=false;
		int aciertos=0;
		int contador=0;
		
		//**********************
		//FILAS 
		//**********************
		//Se saldrá de los bucles cuando llegue al final o sw sea true
		for(int f=0; f<3 && !sw2; f++) { 
			
			for(int c=0; c<3 && !sw; c++) { 
				
				ficha= this.mFunciona.devuelveFicha(f, c); //me muevo en la primera fila
				
				if(ficha==null)
					sw=true; //se sale de los bucles, ya que no puede formar 3 en raya
				
				else if(ficha.getColor().equals(color))
					contador++;
			}
			aciertos=contador;
			if(contador==3)
				sw2=true;
			contador=0; //reinicio el contador
			sw=false; //sw vuelve a ser false para comprobar la siguiente línea
		}
						
		if(aciertos==3) //compruebo si en las filas se ha hecho línea
			return true;
		
		
		//***********************
		//COLUMNAS
		//***********************
		//Reinicio todas las variables
		ficha = null;
		sw=false;
		sw2=false;
		aciertos=0;
		contador=0;
		
		for(int c=0; c<3 && !sw2; c++) {
			
			for(int f=0; f<3 && !sw; f++) {
				
				ficha = this.mFunciona.devuelveFicha(f, c);
				
				if(ficha==null)
					sw=true; //se sale de los bucles, ya que no puede formar 3 en raya
				
				else if(ficha.getColor().equals(color))
					contador++;				
			}
			aciertos=contador;
			if(contador==3)
				sw2=true;
			contador=0; //reinicio el contador
			sw=false; //sw vuelve a ser false para comprobar la siguiente columna
		}
		
		if(aciertos==3)
			return true;
		
		
		//********************
		//DIAGONAL PRINCIPAL
		//********************
		//Reinicio todas las variables
		ficha = null;
		sw=false;
		aciertos=0;
		contador=0;
		
		for(int i=0; i<3 && !sw; i++) {
			
			ficha=this.mFunciona.devuelveFicha(i, i);
			
			if(ficha==null)
				sw=true; //se sale del bucle, ya que no puede formar 3 en raya
			
			else if(ficha.getColor().equals(color))
				contador++;
		}
		
		if(contador==3)
			return true;
		
		
		//*******************
		//DIAGONAL SECUNDARIA
		//*******************
		//Reinicio todas las variables
		ficha = null;
		sw=false;
		aciertos=0;
		contador=0;
		
		int colum=2;
		for(int f=0; f<3 && !sw; f++) {
			
			ficha= this.mFunciona.devuelveFicha(f, colum);
			colum--;
			if(ficha==null)
				sw=true;
			
			else if(ficha.getColor().equals(color))
				contador++;
		}
		
		
		if(contador==3)
			return true;		
		
		return false;
		
	}//end comprueba3Raya
	
	
	/**
	 * Método que inserta un jugador o lo actualiza
	 */
	private int insertaJugador() {
		
		String nick1 = this.miVista.getNick1().getText();
		String nick2 = this.miVista.getNick2().getText();
		
		Jugador player1;
		Jugador player2;
		int ganador;
		
		if(this.comprueba3Raya()) {//ha ganado alguien?
			
			if((this.mFunciona.getContador()-1)%2==0) { //se ganó con el turno de las rojas (nick2)
				player1 = new Jugador(nick1, 0);
				player2 = new Jugador(nick2, 10);
				ganador=2;
				
			}			
			else {
				player1 = new Jugador(nick1, 10);
				player2 = new Jugador(nick2, 0);
				ganador=1;
			}			
		}
		else { //si no ha ganado nadie es que se ha empatado. Luego 5 puntos para cada uno
			player1 = new Jugador(nick1, 5);
			player2 = new Jugador(nick2, 5);
			ganador=3;
		}
		
		
		try {
			this.mJugador.insertaJugador(player1);
			this.mJugador.insertaJugador(player2);
		} 
		catch (SQLException e) {			
			e.printStackTrace();
		}		
			
		return ganador;
		
	}//end insertaJugador
	
	
	
	
	private void insertaPartida(int ganador) {
		
		Partida juego;
		String nick1, nick2;
		
		nick1= this.miVista.getNick1().getText();
		nick2= this.miVista.getNick2().getText();
		
		try {		
		
		if(ganador==1) {//ha ganado el nick1
			juego = new Partida(nick1, nick2, nick1);
			this.mPartida.insertaPartida(juego);
		}
		else if(ganador==2) {//ha ganado el nick2
			juego = new Partida(nick1, nick2, nick2);
			this.mPartida.insertaPartida(juego);			
		}
		else {
			juego = new Partida(nick1, nick2, "empate");
			this.mPartida.insertaPartida(juego);
		}
		
		}
		catch(SQLException e) {
			mensaje("Error al insertar la partida", "Info", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
		
	/**
	 * Método que muestra un mensaje
	 * @param msn
	 */
	private void mensaje(String msn, String tit, int clave) {
		JOptionPane.showMessageDialog(this.miVista, msn, tit, clave);
	}
		
	
	
	
	/**
	 * Método que muestra el ranking de los jugadores
	 */
	private void ranking() {
		
		try {
			new JDialogRanking(this.mJugador.ranking());
			
		} catch (SQLException e) {
			mensaje("Error al crear la consulta", "ERROR", JOptionPane.ERROR_MESSAGE);			
		}		
	}
	
	
	
	/**
	 * Método que muestra el histórico de un jugador en concreto
	 */
	private void historico() {
		
		try {
			new JDialogHistorico(this.mPartida.historico());
			
		} 
		catch (SQLException e) {
			mensaje("Error al crear la consulta", "ERROR", JOptionPane.ERROR_MESSAGE);	
		}
	}
	
	
	
	/**
	 * Método que deja la ventana como está al abrirse por primera vez
	 */
	private void porDefecto() {
		
		this.miVista.getSubTit().setVisible(false);
		this.miVista.getTurno().setText("");
		this.miVista.getTurno().setVisible(false);		
		
		this.miVista.getNick1().setText("");
		this.miVista.getNick2().setText("");
		
		this.miVista.getNick1().setEditable(true);
		this.miVista.getNick2().setEditable(true);
		
		this.miVista.getComenzar().setVisible(true);
		this.miVista.getRanking().setVisible(true);
		this.miVista.getHistorico().setVisible(true);
		
		limpiaTablero();
		
		limpiaArray();
		
		this.mFunciona.setContador((byte)1);	
	}
	
	
	/**
	 * Método que quita las fichas del tablero
	 */
	private void limpiaTablero() {		
		
		for(int f=0; f<3; f++) {
			
			for(int c=0; c<3; c++) {
				
				this.miVista.getTablero()[f][c].setIcon(null);
				this.miVista.getTablero()[f][c].setEnabled(false);
				
			}
		}		
	}
	
	
	/**
	 * Método que elimina las fichas del array
	 */
	private void limpiaArray() {	
		
		for(int i=0; i<9; i++) {
			
			this.mFunciona.getFichas()[i]=null;			
		}		
	}



	/**
	 * Método que muestra las victorias de un determinado jugador
	 */
	private void victoriasJugadorX() {
		
		String nick= JOptionPane.showInputDialog("Introduce el nombre del jugador");
			
		try {
			
			if(nick==null || nick.equals("")) //si se pulsa Aceptar, Cancelar o se cierra sin poner nombre, se sale
				return;
			
			if(!this.mJugador.buscaJugador(nick)) //compruebo si ese nick existe
				throw new MiExcepcion();
			
			int victorias= this.mPartida.victoriaJugadorX(nick);
			
			mensaje(nick + " ha ganado " + victorias + " partidas", "Info", JOptionPane.INFORMATION_MESSAGE);	
			
			this.mJugador.creaConsulta();
		} 
		catch (SQLException e) {
			mensaje("Error en la consulta", "Error", JOptionPane.ERROR_MESSAGE);		
		}
		catch(MiExcepcion e) {
			mensaje("El jugador no existe", "Error", JOptionPane.ERROR_MESSAGE);	
		}
		
	}



	/**
	 * Método que muestra los empates de un determinado jugador
	 */
	private void empatesJugadorX() {
		
		String nick= JOptionPane.showInputDialog("Introduce el nombre del jugador");
		
		try {
			
			if(nick==null || nick.equals("")) //si se pulsa Aceptar, Cancelar o se cierra sin poner nombre, se sale
				return;
			
			if(!this.mJugador.buscaJugador(nick)) //compruebo si ese nick existe
				throw new MiExcepcion();
			
			int empates= this.mPartida.empateJugadorX(nick);
			
			mensaje(nick + " ha empatado " + empates + " partidas", "Info", JOptionPane.INFORMATION_MESSAGE);	
			
			this.mJugador.creaConsulta();
		} 
		catch (SQLException e) {
			mensaje("Error en la consulta", "Error", JOptionPane.ERROR_MESSAGE);		
		}
		catch(MiExcepcion e) {
			mensaje("El jugador no existe", "Error", JOptionPane.ERROR_MESSAGE);	
		}		
	}
	
	
	
	/**
	 * Metodo que muestra las derrotas de un determinado jugador
	 */
	private void derrotasJugadorX() {
		
		String nick= JOptionPane.showInputDialog("Introduce el nombre del jugador");
		
		try {
			
			if(nick==null || nick.equals("")) //si se pulsa Aceptar, Cancelar o se cierra sin poner nombre, se sale
				return;
			
			if(!this.mJugador.buscaJugador(nick)) //compruebo si ese nick existe
				throw new MiExcepcion();
			
			int derrotas= this.mPartida.derrotaJugadorX(nick);
			
			mensaje(nick + " ha perdido " + derrotas + " partidas", "Info", JOptionPane.INFORMATION_MESSAGE);
			
			this.mJugador.creaConsulta();
			
		} 
		catch (SQLException e) {
			mensaje("Error en la consulta", "Error", JOptionPane.ERROR_MESSAGE);		
		}
		catch(MiExcepcion e) {
			mensaje("El jugador no existe", "Error", JOptionPane.ERROR_MESSAGE);	
		}			
	}
	
		
	
	
	/**
	 * Método que muestra las victorias, empates y derrotas de un determinado jugador
	 */
	private void historicoJugadorX() {
		
		String nick= JOptionPane.showInputDialog("Introduce el nombre del jugador");
		
		try {
			
			if(nick==null || nick.equals("")) //si se pulsa Aceptar, Cancelar o se cierra sin poner nombre, se sale
				return;
			
			if(!this.mJugador.buscaJugador(nick)) //compruebo si ese nick existe
				throw new MiExcepcion();
			
			int victorias = this.mPartida.victoriaJugadorX(nick);
			int empates = this.mPartida.empateJugadorX(nick);
			int derrotas = this.mPartida.derrotaJugadorX(nick);			
			
			String [][]	matrizDatos = new String[1][4];
			
			matrizDatos[0][0] = nick;
			matrizDatos[0][1] = String.valueOf(victorias);
			matrizDatos[0][2] = String.valueOf(empates);
			matrizDatos[0][3] = String.valueOf(derrotas);			
			
			new JDialogHistoricoJugador(matrizDatos, nick);
		
			this.mJugador.creaConsulta();
		} 
		catch (SQLException e) {
			mensaje("Error en la consulta", "Error", JOptionPane.ERROR_MESSAGE);		
		}
		catch(MiExcepcion e) {
			mensaje("El jugador no existe", "Error", JOptionPane.ERROR_MESSAGE);	
		}		
	}
	
	
	
	
	/**
	 * Método que inserta un jugador en la tabla jugadores
	 */
	private void registrar() {
		
		String nick= JOptionPane.showInputDialog("Introduce el nombre del jugador");
		
		try {
			
			if(nick==null || nick.equals("")) //si se pulsa Aceptar, Cancelar o se cierra sin poner nombre, se sale
				return;
			
			if(this.mJugador.buscaJugador(nick)) //compruebo si ese nick existe
				throw new MiExcepcion();
			
			Jugador player = new Jugador(nick);
			
			this.mJugador.insertaJugador(player);
			
			mensaje("Jugador insertado", "Info", JOptionPane.INFORMATION_MESSAGE);
		
				
		} 
		catch (SQLException e) {
			mensaje("Error en la consulta", "Error", JOptionPane.ERROR_MESSAGE);		
		}
		catch(MiExcepcion e) {
			mensaje("Nick ya usado. Introduce otro.", "Error", JOptionPane.ERROR_MESSAGE);	
		}	
		
	}
	
	
	
	/**
	 * Método que modifica el nick de un usuario (se modifica en TODAS las tablas)
	 */
	private void modificaNick() {
		
		String nick= JOptionPane.showInputDialog("Introduce el nombre del jugador a modificar");
		String nickNuevo= JOptionPane.showInputDialog("Introduce el nuevo nick");
		
		try {
			
			if(nick==null || nick.equals("")) //si se pulsa Aceptar, Cancelar o se cierra sin poner nombre en el primer JOptionPane, se sale
				return;
			
			else if((nickNuevo==null || nickNuevo.equals(""))) //si se pulsa Aceptar, Cancelar o se cierra sin poner nombre en el segundo JOptionPane, se sale
				return;
			
			if(!this.mJugador.buscaJugador(nick)) //compruebo si ese nick existe. Si NO existe, lanza MiExcepcion
				throw new MiExcepcion();
			
			this.mJugador.modificaJugador(nick, nickNuevo);
			
			this.mPartida.modificaGanador(nick, nickNuevo); //tengo que modificar el ganador, ya que no es FK
			
			mensaje("Jugador modificado", "Info", JOptionPane.INFORMATION_MESSAGE);
		
				
		} 
		catch (SQLException e) {
			mensaje("Nick existente. NO puedes cambiarlo.", "Error", JOptionPane.ERROR_MESSAGE);		
		}
		catch(MiExcepcion e) {
			mensaje("El jugador que quieres modificar no existe", "Error", JOptionPane.ERROR_MESSAGE);	
		}			
	}
	
	
	
	/**
	 * Método que elimina un usuario de la tabla Jugadores. En la table Partidas, aparecerá null, pero luego la modificaré para que aparezca su nick con un *
	 */
	private void banearNick() {
		
		String nick= JOptionPane.showInputDialog("Introduce el nombre del jugador a eliminar");
		
		try {
		if(nick==null || nick.equals("")) //si se pulsa Aceptar, Cancelar o se cierra sin poner nombre en el JOptionPane, se sale
			return;
		
		if(!this.mJugador.buscaJugador(nick)) //compruebo si ese nick existe. Si NO existe, lanza MiExcepcion
			throw new MiExcepcion();
		
		this.mJugador.eliminaJugador(nick);
		
		//en este punto se ha eliminado. Como está en on delete set null... los nick1 y nick2 de la tabla partidas estarán en blanco, pero el Ganador NO.
		//Por tanto vamos a eliminar su nombre de ahí.
		this.mPartida.modificaGanador(nick, "");
		
		mensaje("Jugador eliminado", "Info", JOptionPane.INFORMATION_MESSAGE);		
		
		}
		catch (SQLException e) {
			mensaje("Error en la consulta", "Error", JOptionPane.ERROR_MESSAGE);		
		}
		catch(MiExcepcion e) {
			mensaje("El jugador que quieres eliminar no existe", "Error", JOptionPane.ERROR_MESSAGE);	
		}
		
	}
	
	
	/**
	 * Método que muestra el jugador con más puntos
	 */
	private void mejor() {
		
		try {
			Jugador player = this.mJugador.getPrimero();
			
			mensaje(player.toString(), "JUGADOR CON MÁS PUNTOS", JOptionPane.INFORMATION_MESSAGE);
			
			this.mJugador.creaConsulta();
			
		} 
		catch (SQLException e) {			
			mensaje("Error SQL", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
			
	}

	

	/**
	 * Método que muestra los tres mejores jugadores
	 */
	private void tresMejores() {
		
		try {
			
			new JDialogRanking(this.mJugador.tresMejores());
			this.mJugador.creaConsulta();
			
		} 
		catch (SQLException e) {
			mensaje("Error SQL", "ERROR", JOptionPane.ERROR_MESSAGE);
		}		
	}
		
	
	
	/**
	 * Método que muestra el histórico de un jugador en concreto
	 */
	private void hanJugado() {
		
		try {
			new JDialogHanJugado(this.mJugador.hanJugado());
			this.mJugador.creaConsulta();
		} 
		catch (SQLException e) {
			mensaje("Error al crear la consulta", "ERROR", JOptionPane.ERROR_MESSAGE);	
		}
	}
	
	
	/**
	 * Método que borra TODOS los registros de la base de datos
	 */
	private void borraTodo() {
		
		try {
			this.mPartida.borraTodo();
			this.mJugador.borraTodo();
		} 
		catch (SQLException e) {
			mensaje("Error SQL", "ERROR", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	
	/**
	 * Método que carga registros en la base de datos
	 */
	private void cargarDatos() {
	
		try {
			this.mJugador.carga();
			this.mPartida.carga();
		} 
		catch (SQLException e) {
			mensaje("Error SQL", "ERROR", JOptionPane.ERROR_MESSAGE);
		} 
		catch (NumberFormatException e) {
			mensaje("Error en los datos del .txt", "ERROR", JOptionPane.ERROR_MESSAGE);
		} 
		catch (FileNotFoundException e) {
			mensaje("Archivo .txt no encontrado", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	
	
	
	

}
