package defecto;

import java.awt.Dimension;

import javax.swing.JFrame;

import controlador.*;
import vista.*;

public class Main {

	public static void main(String[] args) {

		
		
		Vista miVista = new Vista();
		Controlador ctr = new Controlador(miVista);
		miVista.añadeControl(ctr);
		
		JFrame ventana = new JFrame("3 EN RAYA");	
		ventana.addWindowListener(ctr);		
		ventana.setJMenuBar(miVista.getBarraMenu());

		ventana.setContentPane(miVista);
		ventana.setVisible(true);	
		ventana.setPreferredSize(new Dimension(450, 400));
		ventana.pack();
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		
		
	}

}
