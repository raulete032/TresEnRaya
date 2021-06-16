package vista;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.TableColumnModel;

public class JDialogHistorico extends JDialog {

	private static final long serialVersionUID = -6431989510643125461L;

	
	/**
	 * Constructor
	 */
	public JDialogHistorico(String [][] datos) {
		
		this.setModal(true);
		this.setTitle("Histórico de partidas");
		this.setLayout(new FlowLayout());
		
		String [] columnas = {"Código partida", "Fecha partida", "Nick1", "Nick2", "Ganador"};
		
		JTable tablaRanking = new JTable(datos, columnas);
		JScrollPane sp = new JScrollPane(tablaRanking);
		
		sp.setPreferredSize(new Dimension(700, 200));
		
		TableColumnModel tabla = tablaRanking.getColumnModel();
		
		tabla.getColumn(0).setPreferredWidth(300);
		tabla.getColumn(1).setPreferredWidth(300);
		tabla.getColumn(2).setPreferredWidth(500);
		tabla.getColumn(3).setPreferredWidth(500);
		tabla.getColumn(4).setPreferredWidth(500);
		
		this.add(sp);
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
		
		
	}
	
	
	
	
}
