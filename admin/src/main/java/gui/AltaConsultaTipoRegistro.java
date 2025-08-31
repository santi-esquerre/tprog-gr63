package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

/*Dependencias:
 * 1)IEventoController listarEventos();
 * 2)IEventoController mostrarEdiciones(String nombreEvento);
 * 3)IEdicionController mostrarTiposDeRegistro(String nombreEdicion);
 * 4.a)IEdicionController altaTipoRegistro(String nombreEdicion, DTTipoRegistro datosTipoRegistro);
 * 4.b)IEdicionController consultaTipoRegistro(String nombreEdicion, String nombreTipoRegistro);
 * */

public class AltaConsultaTipoRegistro extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaConsultaTipoRegistro frame = new AltaConsultaTipoRegistro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AltaConsultaTipoRegistro() {
		setBounds(100, 100, 450, 300);

	}

}
