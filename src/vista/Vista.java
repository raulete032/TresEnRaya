package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.*;

public class Vista extends JPanel {
	
	private static final long serialVersionUID = -5066497429931480003L;
	
	//Variables de instancia
	private JButton [][] tablero;
	private JButton comenzar, ranking, historico;
	private JTextField nick1, nick2;
	private JLabel turno;
	private JLabel subTit;
	private JMenuBar barraMenu;
	private JMenuItem historicoJugador, victorias, derrotas, empates, hanJugado;
	private JMenuItem registrarse, modificar, eliminar;
	private JMenuItem top, top3;
	private JMenuItem eliminarTodo, cargar;
	
	/**
	 * Constructor
	 */
	public Vista() {
		
		JPanel pPrincipal = new JPanel(new BorderLayout());
		
		barraMenu(); //aquí preparo la barra del Menú
		
		
		//**************
		//TITULO
		//**************
		JPanel pTitulo = preparaTitulo();
		
		
		
		//*************
		//TABLERO
		//*************
		JPanel pTablero = preparaTablero();
		
		
		//****************
		//SUR
		//****************
		JPanel pSur = preparaPanelSur();
		
		
		//*****************
		//OESTE (nick1)
		//*****************
		this.nick1 = new JTextField(10);
		ImageIcon imagen1 = new ImageIcon("src/imagenesYarchivos/circuloNegro.png");
		JPanel pOeste = preparaPanelesLaterales(nick1, imagen1);
		
		
		//*****************
		//ESTE (nick2)
		//*****************
		this.nick2 = new JTextField(10);
		ImageIcon imagen2 = new ImageIcon("src/imagenesYarchivos/cruzRoja.png");
		JPanel pEste = preparaPanelesLaterales(nick2, imagen2);
		
		
		pPrincipal.add(pTitulo, BorderLayout.NORTH);
		pPrincipal.add(pTablero, BorderLayout.CENTER);
		pPrincipal.add(pSur, BorderLayout.SOUTH);
		pPrincipal.add(pOeste, BorderLayout.WEST);
		pPrincipal.add(pEste, BorderLayout.EAST);
		
		
		añadeToolTip();
		asignaAlias();
		this.add(pPrincipal);
		
		
	}
	
	
	
	/**
	 * Método que prepara el panel del título
	 * @return
	 */
	private JPanel preparaTitulo() {

		JPanel p = new JPanel(new GridLayout(2, 1));
		JPanel p2 = new JPanel();

		JLabel eti = new JLabel("MI TRES EN RAYA", JLabel.CENTER);
		this.subTit = new JLabel("Turno de ");
		this.subTit.setVisible(false);
		this.turno = new JLabel("", JLabel.CENTER);
		this.turno.setVisible(false);

		Font fuente = new Font ("Comic Sans MS", Font.BOLD|Font.ITALIC, 25);
		
		eti.setFont(fuente);
		this.subTit.setFont(fuente);
		this.turno.setFont(fuente);
		this.turno.setForeground(Color.RED);
		
		p2.add(subTit);
		p2.add(turno);
		
		p.add(eti);
		p.add(p2);

		p.setBorder(new EmptyBorder(5, 0, 10, 0));

		return p;
	}
	
	
	
	
	/**
	 * Método que prepara el tablero de juego
	 * @return
	 */
	private JPanel preparaTablero() {

		JPanel p = new JPanel(new GridLayout(3,3));

		this.tablero = new JButton[3][3]; //le doy tamaño a la matriz


		for(int f= 0; f<tablero.length;f++) {

			for(int c=0; c<tablero[f].length; c++) {		

				this.tablero[f][c]= new JButton(); //le asigno a cada hueco un botón				
			}			
		}


		//**********
		//PONGO CADA BOTÓN EN EL PANEL GRIDLAYOUT
		//**********

		for(int f=0; f<tablero.length; f++) {

			for(int c=0; c<tablero[f].length; c++) {

				JButton boton = tablero[f][c];
				boton.setPreferredSize(new Dimension(50, 50));
				boton.setEnabled(false);
				p.add(boton);			
			}
		}		
		return p;
	}
	
	
	
	/**
	 * Método que prepara el panel del Sur
	 */
	public JPanel preparaPanelSur() {
		
		JPanel p = new JPanel();
		
		this.comenzar = new JButton("Comenzar");
		this.ranking = new JButton("Ranking");
		this.historico = new JButton("Histórico");
		
		p.add(comenzar);
		p.add(ranking);
		p.add(historico);
		
		p.setBorder(new EmptyBorder(20, 0, 0, 0));
		
		return p;
	}
	
	
	
	/**
	 * Método que prepara el panel del Oeste (NICK 1)
	 * @return
	 */
	public JPanel preparaPanelesLaterales(JTextField nick, ImageIcon imag) {
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		JPanel paux = new JPanel();
		JPanel panelImagen = new JPanel();
		
		JLabel labelImagen = new JLabel(imag);
		
		panelImagen.add(labelImagen);
		
					
		paux.add(nick);
		
		JLabel eti = new JLabel("Nick: ");		
		
		p.add(eti);
		p.add(paux);
		p.add(panelImagen);
		
		
		return p;		
	}
	
	
	
	/**
	 * Método que añade control a los componentes
	 */
	public void añadeControl(Controlador ctr) {
		
		this.comenzar.addActionListener(ctr);
		this.ranking.addActionListener(ctr);
		this.historico.addActionListener(ctr);
		
		//añado control a los botones del tablero
		for(int f=0; f<this.tablero.length; f++) {
			
			for(int c=0; c<this.tablero[f].length; c++) {
				
				this.tablero[f][c].addActionListener(ctr);
			}
		}
		
		
		this.historicoJugador.addActionListener(ctr);
		this.victorias.addActionListener(ctr);
		this.empates.addActionListener(ctr);
		this.derrotas.addActionListener(ctr);	
		this.registrarse.addActionListener(ctr);
		this.modificar.addActionListener(ctr);
		this.eliminar.addActionListener(ctr);
		this.top.addActionListener(ctr);
		this.top3.addActionListener(ctr);
		this.hanJugado.addActionListener(ctr);
		this.eliminarTodo.addActionListener(ctr);
		this.cargar.addActionListener(ctr);
		
	}



	
	//GETTERS y SETTERS
	public JButton[][] getTablero() {return tablero;}
	public JButton getComenzar() {return comenzar;}
	public JButton getRanking() {return ranking;}
	public JButton getHistorico() {return historico;}
	public JTextField getNick1() {return nick1;}
	public JTextField getNick2() {return nick2;}
	public JLabel getTurno() {return turno;}	
	public JMenuBar getBarraMenu() {return barraMenu;}
	public JMenuItem getHistoricoJugador() {return historicoJugador;}
	public JMenuItem getVictorias() {return victorias;}
	public JMenuItem getDerrotas() {return derrotas;}
	public JMenuItem getEmpates() {return empates;}
	public JMenuItem getHanJugado() {return hanJugado;}
	public JLabel getSubTit() {return subTit;}
	public JMenuItem getRegistrarse() {return registrarse;}
	public JMenuItem getModificar() {return modificar;}
	public JMenuItem getEliminar() {return eliminar;}
	public JMenuItem getTop() {return top;}
	public JMenuItem getTop3() {return top3;}
	public JMenuItem getEliminarTodo() {return eliminarTodo;}
	public JMenuItem getCargar() {return cargar;}



	/**
	 * Método que perpara la barra del Menú
	 */
	public void barraMenu() {
		
		this.barraMenu = new JMenuBar();
		
		JMenu jugadores = new JMenu("Jugadores");
		
		this.historicoJugador = new JMenuItem("Histórico del jugador...");
		this.victorias = new JMenuItem("Victorias del jugador...");
		this.empates = new JMenuItem("Empates del jugador...");
		this.derrotas = new JMenuItem("Derrotas del jugador...");
		this.hanJugado = new JMenuItem("Han jugado");
		
		jugadores.add(this.historicoJugador);
		jugadores.add(this.victorias);
		jugadores.add(this.empates);
		jugadores.add(this.derrotas);
		jugadores.add(this.hanJugado);
		
		JMenu registro = new JMenu("Mantenimiento");		
		this.registrarse = new JMenuItem("Registrarse SIN jugar");	
		this.modificar = new JMenuItem("Modificar nick");
		this.eliminar = new JMenuItem("Banear jugador");
		
		registro.add(this.registrarse);
		registro.add(this.modificar);
		registro.add(this.eliminar);
		
		JMenu mejor = new JMenu("Ranking cabeza");		
		this.top = new JMenuItem("El mejor");
		this.top3 = new JMenuItem("Top 3");		
		
		mejor.add(this.top);
		mejor.add(this.top3);		
		
		
		JMenu borrar = new JMenu("Borrar");
		this.eliminarTodo = new JMenuItem("Borrar todo");
		this.cargar = new JMenuItem("Cargar datos");
		
		borrar.add(eliminarTodo);
		borrar.add(cargar);
		
		this.barraMenu.add(jugadores);
		this.barraMenu.add(registro);		
		this.barraMenu.add(mejor);
		this.barraMenu.add(borrar);
	}
	
	
	/**
	 * Método que añade los ToolTip a los componentes
	 */
	private void añadeToolTip() {
		
		this.comenzar.setToolTipText("Comienza la partida");
		this.ranking.setToolTipText("Muestra el ranking de todos los jugadores");
		this.historico.setToolTipText("Muestra todas las partidas que se han jugado");
		
		this.historicoJugador.setToolTipText("Muestra las Victorias, Empates y Derrotas de un jugador en concerto");
		this.victorias.setToolTipText("Mestra el número de Victorias de un jugador en concreto");
		this.empates.setToolTipText("Muestra el número de Empates de un jugador en concreto");
		this.derrotas.setToolTipText("Muestra el número de Derrotas de un jugador en concreto");
		this.hanJugado.setToolTipText("Muestra los jugadores que han jugado al menos una partida");
		
		this.registrarse.setToolTipText("Permite registrarse en la base de datos sin jugar una partida");
		this.modificar.setToolTipText("Permite modificar el nick de un usuario ya registrado");
		this.eliminar.setToolTipText("Permite borrar un usuario existente");
		
		this.top.setToolTipText("Muestra el jugador con más puntos");
		this.top3.setToolTipText("Muestra una tabla con los 3 jugadores con más puntos");
		
		this.eliminarTodo.setToolTipText("Borra TODOS los registros de la base de datos");
		this.cargar.setToolTipText("Permite cargar nuevamente registros a la base de datos");
		
	}
	
	
	
	/**
	 * Método que pone alias a cada uno de los botones del tablero
	 */
	private void asignaAlias() {
		
		this.tablero[0][0].setActionCommand("00");
		this.tablero[0][1].setActionCommand("01");
		this.tablero[0][2].setActionCommand("02");
		this.tablero[1][0].setActionCommand("10");
		this.tablero[1][1].setActionCommand("11");
		this.tablero[1][2].setActionCommand("12");
		this.tablero[2][0].setActionCommand("20");
		this.tablero[2][1].setActionCommand("21");
		this.tablero[2][2].setActionCommand("22");		
	}
	
	
	
	
	
	
	
	

}
