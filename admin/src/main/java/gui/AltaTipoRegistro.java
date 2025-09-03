package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import exceptions.ValidationInputException;
import interfaces.IEdicionController;
import interfaces.IEventoController;
import interfaces.IReceiver;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.miginfocom.swing.MigLayout;
import util.Dialog;

/*Dependencias:
 * 1)IEventoController listarEventos();
 * 2)IEventoController mostrarEdiciones(String nombreEvento);
 * 3)IEdicionController mostrarTiposDeRegistro(String nombreEdicion);
 * 4.a)IEdicionController altaTipoRegistro(String nombreEdicion, DTTipoRegistro datosTipoRegistro);
 * 4.b)IEdicionController consultaTipoRegistro(String nombreEdicion, String nombreTipoRegistro);
 * */

public class AltaTipoRegistro extends JInternalFrame implements IReceiver {

	private static final int TAMANIO_ICONO = 10;
	private static final long serialVersionUID = 1L;
	private JTextField txtEvento;
	private JTextField txtNombre;
	private ListarEventos listarEventosDialog;
	private JComboBox<String> cbxEdicion;
	private IEventoController eventoController;
	private IEdicionController edicionController;
	private JSpinner spnCosto;
	private JSpinner spnCupo;
	private JTextArea txtDescripcion;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public AltaTipoRegistro(IEventoController eventoController, IEdicionController edicionController) {
		IconFontSwing.register(FontAwesome.getIconFont());

		this.listarEventosDialog = new ListarEventos(this, eventoController);
		this.eventoController = eventoController;
		this.edicionController = edicionController;
		listarEventosDialog.setVisible(false);
		setTitle("Alta de tipo de registro");
		// Íconos
		final Icon iconSearch = IconFontSwing.buildIcon(FontAwesome.SEARCH_PLUS, TAMANIO_ICONO);
		setBounds(100, 100, 460, 283);
		setResizable(true);
		setMaximizable(true);
		setClosable(true);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnSave = new JButton("Guardar");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAction(e);
			}
		});
		panel.add(btnSave);

		JButton btnCancel = new JButton("Descartar");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnCancel);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new MigLayout("", "[grow][grow][][grow][]", "[][][][][][grow]"));

		JLabel lblNewLabel = new JLabel("Evento:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(lblNewLabel, "cell 0 0,alignx left");

		txtEvento = new JTextField();
		txtEvento.setEditable(false);
		panel_1.add(txtEvento, "cell 1 0 3 1,growx");
		txtEvento.setColumns(10);

		JButton btnSeleccionarEvento = new JButton("");
		btnSeleccionarEvento.addActionListener((ActionEvent e) -> selectEventoAction(e));
		btnSeleccionarEvento.setIcon(iconSearch);
		panel_1.add(btnSeleccionarEvento, "cell 4 0");

		JLabel lblEdicinDeEvento = new JLabel("Edición de evento:");
		panel_1.add(lblEdicinDeEvento, "cell 0 1,alignx left");

		cbxEdicion = new JComboBox();
		cbxEdicion.setEnabled(false);
		cbxEdicion.setEditable(true);
		panel_1.add(cbxEdicion, "cell 1 1 4 1,growx");

		JLabel lblNombre = new JLabel("Nombre");
		panel_1.add(lblNombre, "cell 0 2,alignx left");

		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setColumns(10);
		panel_1.add(txtNombre, "cell 1 2 4 1,growx");

		JLabel lblCupo = new JLabel("Costo:");
		panel_1.add(lblCupo, "flowx,cell 0 3,alignx left,aligny top");

		spnCosto = new JSpinner();
		spnCosto.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		spnCosto.setEnabled(false);
		panel_1.add(spnCosto, "cell 1 3,growx");

		JLabel lblCupo_2 = new JLabel("Cupo:");
		panel_1.add(lblCupo_2, "cell 2 3,alignx trailing");

		spnCupo = new JSpinner();
		spnCupo.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		spnCupo.setEnabled(false);
		panel_1.add(spnCupo, "cell 3 3,growx");

		JLabel lblDescripcin = new JLabel("Descripción:");
		panel_1.add(lblDescripcin, "cell 0 4");

		txtDescripcion = new JTextArea();
		panel_1.add(txtDescripcion, "cell 0 5 5 1,grow");

	}

	public void receive(Object... params) {
		try {
			cbxEdicion.removeAllItems();
			eventoController.mostrarEdiciones((String) params[0]).forEach(ed -> cbxEdicion.addItem(ed.nombre()));
			if (cbxEdicion.getItemCount() == 0) {
				util.Dialog.showWarning(
						"El evento seleccionado no tiene ediciones disponibles para agregar tipos de registro");
				txtEvento.setText("");
				return;
			} else {
				txtEvento.setText((String) params[0]);
				cbxEdicion.setEnabled(true);
				txtNombre.setEditable(true);
				spnCosto.setEnabled(true);
				spnCupo.setEnabled(true);
				txtDescripcion.setEditable(true);
			}
		} catch (Exception e) {
			JFrame parent = (JFrame) this.getTopLevelAncestor();
			util.ExceptionHandler.manageException(e);
		}

	}

	private void selectEventoAction(ActionEvent e) {
		listarEventosDialog.pack();
		listarEventosDialog.setLocationRelativeTo(this);
		listarEventosDialog.setVisible(true);
	}

	private void saveAction(ActionEvent e) {
		try {
			if (cbxEdicion.getSelectedItem() == null)
				throw new ValidationInputException("Debe seleccionar una edición");
			String nombreEdicion = cbxEdicion.getSelectedItem().toString();
			String nombreTipoRegistro = txtNombre.getText();
			if (nombreTipoRegistro.isBlank())
				throw new ValidationInputException("Debe ingresar un nombre para el tipo de registro");
			float costo = Float.parseFloat(spnCosto.getValue().toString());
			int cupo = Integer.parseInt(spnCupo.getValue().toString());
			String descripcion = txtDescripcion.getText();
			if (descripcion.isBlank())
				throw new ValidationInputException("Debe ingresar una descripción para el tipo de registro");

			var datosTipoRegistro = new datatypes.DTTipoRegistro(nombreTipoRegistro, descripcion, costo, cupo);

			edicionController.altaTipoRegistro(nombreEdicion, datosTipoRegistro);

			Dialog.showInfo("Tipo de registro creado exitosamente");
			this.dispose();
		} catch (Exception ex) {
			util.ExceptionHandler.manageException(ex);
		}
	}

}
