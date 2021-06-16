package vista;

import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class JDialogHanJugado extends JDialog {

	private static final long serialVersionUID = -1239480887834827658L;
	
	/**
	 * Constructor
	 */
	public JDialogHanJugado (String [][] datos) {
				
		this.setModal(true);
		this.setTitle("Jugadores que han jugado al menos una partida");
		this.setLayout(new FlowLayout());
		
		String [] columnas = {"Nick"};
		
		JTable tabla = new JTable(datos, columnas);
		
		JScrollPane sp = new JScrollPane(tabla);
		
		this.add(sp);
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
		
		
		
	}
	

}
