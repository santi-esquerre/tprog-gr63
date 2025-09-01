package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

import interfaces.IEdicionController;
import interfaces.IEventoController;
import interfaces.IReceiver;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import util.Dialog;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import exceptions.ValidationInputException;

import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;

/*Dependencias:
 * 1)IEventoController listarEventos();
 * 2)IEventoController mostrarEdiciones(String nombreEvento);
 * 3)IEdicionController mostrarTiposDeRegistro(String nombreEdicion);
 * 4.a)IEdicionController altaTipoRegistro(String nombreEdicion, DTTipoRegistro datosTipoRegistro);
 * 4.b)IEdicionController consultaTipoRegistro(String nombreEdicion, String nombreTipoRegistro);
 * */

public class ConsultaTipoRegistro extends JInternalFrame implements IReceiver {
	
	private static final int TAMANIO_ICONO = 10;
	private static final long serialVersionUID = 1L;
	private JTextField txtEvento;
	private ListarEventos listarEventosDialog;
	private JComboBox<String> cbxEdicion;
	private JComboBox<String> cbxTipoRegistro;
	private IEventoController eventoController;
	private IEdicionController edicionController;
	private JTextField txtNombre;
	private JTextField txtCosto;
	private JTextField txtCupo;
	private JTextArea txtDescripcion;
	
	
	

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ConsultaTipoRegistro(IEventoController eventoController, IEdicionController edicionController) {
		this.listarEventosDialog = new ListarEventos(this, eventoController);
		this.eventoController = eventoController;
		this.edicionController = edicionController;
		listarEventosDialog.setVisible(false);
		setTitle("Consulta de tipo de registro");
		// Íconos
		final Icon iconSearch = IconFontSwing.buildIcon(FontAwesome.SEARCH_PLUS, TAMANIO_ICONO);
		setBounds(100, 100, 503, 277);
        setResizable(true);
        setMaximizable(true);
        setClosable(true);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new MigLayout("", "[][grow][][grow]", "[][][][][][][][grow]"));

		getContentPane().add(panel_1, BorderLayout.CENTER);
		JLabel lblNewLabel = new JLabel("Evento:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(lblNewLabel, "cell 0 0,alignx left");
		
		JButton btnSeleccionarEvento = new JButton("");
		btnSeleccionarEvento.addActionListener((ActionEvent e) -> selectEventoAction(e));
		
		txtEvento = new JTextField();
		txtEvento.setEditable(false);
		panel_1.add(txtEvento, "cell 1 0 2 1,growx");
		txtEvento.setColumns(10);
		btnSeleccionarEvento.setIcon(iconSearch);
		panel_1.add(btnSeleccionarEvento, "cell 3 0");
		
		JLabel lblEdicinDeEvento = new JLabel("Edición de evento:");
		panel_1.add(lblEdicinDeEvento, "cell 0 1,alignx left");
		
		cbxEdicion = new JComboBox();
		cbxEdicion.setEnabled(false);
		cbxEdicion.setEditable(true);
		cbxEdicion.addActionListener((ActionEvent e) -> selectEdicionAction(e));
		panel_1.add(cbxEdicion, "cell 1 1 3 1,growx");
		
		JLabel lblTipoDeRegistro = new JLabel("Tipo de registro de la edición:");
		panel_1.add(lblTipoDeRegistro, "cell 0 2");
		
		cbxTipoRegistro = new JComboBox<String>();
		cbxTipoRegistro.setEnabled(false);
		cbxTipoRegistro.setEditable(true);
		cbxTipoRegistro.addActionListener((ActionEvent e) -> selectTipoRegistroAction(e));
		panel_1.add(cbxTipoRegistro, "cell 1 2 3 1,growx");
		
		JLabel lblDatosDelTipo = new JLabel("Datos del tipo de registro:");
		panel_1.add(lblDatosDelTipo, "cell 0 3");
		
		JLabel lblNombre = new JLabel("Nombre");
		panel_1.add(lblNombre, "cell 0 4");
		
		txtNombre = new JTextField();
		panel_1.add(txtNombre, "cell 1 4 3 1,growx");
		txtNombre.setEditable(false);
		txtNombre.setColumns(10);
		
		JLabel lblCupo_1 = new JLabel("Costo:");
		panel_1.add(lblCupo_1, "cell 0 5,alignx right");
		
		txtCosto = new JTextField();
		panel_1.add(txtCosto, "cell 1 5,growx");
		txtCosto.setEditable(false);
		txtCosto.setColumns(10);
		
		JLabel lblCupo_2_1 = new JLabel("Cupo:");
		panel_1.add(lblCupo_2_1, "cell 2 5");
		
		txtCupo = new JTextField();
		panel_1.add(txtCupo, "cell 3 5,growx");
		txtCupo.setEditable(false);
		txtCupo.setColumns(10);
		
		JLabel lblDescripcin = new JLabel("Descripción:");
		panel_1.add(lblDescripcin, "cell 0 6");
		
		txtDescripcion = new JTextArea();
		panel_1.add(txtDescripcion, "cell 0 7 4 1,grow");
		txtDescripcion.setEditable(false);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new MigLayout("", "[grow][grow][][][grow]", "[][][][grow]"));

	}
	
	public void receive(Object... params) {
		try {
			cbxEdicion.removeAllItems();
			eventoController.mostrarEdiciones((String)params[0]).forEach(ed -> cbxEdicion.addItem(ed.nombre()));
			if(cbxEdicion.getItemCount() == 0) {
				util.Dialog.showWarning("El evento seleccionado no tiene ediciones disponibles para agregar tipos de registro");
				txtEvento.setText("");
				return;
			}else {
				txtEvento.setText((String)params[0]);
				cbxEdicion.setEnabled(true);
				
			}
		} catch (Exception e) {
			JFrame parent = (JFrame)this.getTopLevelAncestor();
			util.ExceptionHandler.manageException(e);
		}
		
	}
	
	private void selectEdicionAction(ActionEvent e) {
		try {
			cbxTipoRegistro.removeAllItems();
			//System.out.println("Seleccionando edicion: " + cbxEdicion.getSelectedItem().toString());
			edicionController.mostrarTiposDeRegistro(cbxEdicion.getSelectedItem().toString()).forEach(tr -> cbxTipoRegistro.addItem(tr.nombre()));
			if(cbxEdicion.getItemCount() == 0) {
				Dialog.showWarning("La edición seleccionada no tiene tipos de registro disponibles");
				cbxTipoRegistro.setEnabled(false);
				return;
			}else {
				cbxTipoRegistro.setEnabled(true);
			}
		} catch (Exception ex) {
			JFrame parent = (JFrame)this.getTopLevelAncestor();
			util.ExceptionHandler.manageException(ex);
		}
	}
	
	private void selectTipoRegistroAction(ActionEvent e) {
		if(cbxTipoRegistro.getSelectedItem() == null) return;
		try {
			var tr = edicionController.consultaTipoRegistro(cbxEdicion.getSelectedItem().toString(), cbxTipoRegistro.getSelectedItem().toString());
			if(tr == null) {
				throw new ValidationInputException("El tipo de registro seleccionado no existe");
			}
			txtNombre.setText(tr.nombre());
			txtCosto.setText(String.valueOf(tr.costo()));
			txtCupo.setText(String.valueOf(tr.cupo()));
			txtDescripcion.setText(tr.descripcion());
		} catch (Exception ex) {
			JFrame parent = (JFrame)this.getTopLevelAncestor();
			util.ExceptionHandler.manageException(ex);
		}
	}
	private void selectEventoAction(ActionEvent e) {
		listarEventosDialog.pack();
		listarEventosDialog.setLocationRelativeTo(this);
		listarEventosDialog.setVisible(true);
	}
	
}
