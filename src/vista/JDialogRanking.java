package vista;

import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class JDialogRanking extends JDialog {

	private static final long serialVersionUID = 2984909728191506148L;
	
	
	
	/**
	 * Constructor
	 */
	public JDialogRanking(String [][] datos) {
		
		this.setModal(true);
		this.setTitle("Ranking de los jugadores");
		this.setLayout(new FlowLayout());
		
		String [] columnas = {"Puesto", "Nick", "Puntos"};
		
		JTable tablaRanking = new JTable(datos, columnas);
		JScrollPane sp = new JScrollPane(tablaRanking);
		
		this.add(sp);
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
		
		
	}
	

}
