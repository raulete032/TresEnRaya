package vista;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class JDialogHistoricoJugador extends JDialog {
	
	private static final long serialVersionUID = -530510994610387312L;

	
	
	/**
	 * Constructor
	 */
	public JDialogHistoricoJugador(String [][] datos, String nick) {
		
		this.setModal(true);
		this.setTitle("Histórico de partidas de " + nick);
		this.setLayout(new FlowLayout());
		
		String [] cabecera = {"Jugador", "Victorias", "Empates", "Derrotas"};
		
		JTable tablaRanking = new JTable(datos, cabecera);
		JScrollPane sp = new JScrollPane(tablaRanking);	
		
		this.add(sp);
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(500, 200));
		this.pack();		
		this.setVisible(true);
	//	this.setResizable(false);
		
		
	}
}
