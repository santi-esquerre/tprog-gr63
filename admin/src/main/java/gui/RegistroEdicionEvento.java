package gui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.awt.Window;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import datatypes.DTAsistente;
import datatypes.DTEvento;
import datatypes.DTInstitucion;
import datatypes.DTTipoRegistro;
import exceptions.ValidationInputException;
import datatypes.DTEdicion;
import interfaces.Factory;

import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistroEdicionEvento extends JInternalFrame {
	private JTable table;
	DTTipoRegistro seleccionado = null; // Variable que se usará para tomar el valor del tipo de registro seleccionado
	JComboBox comboBoxEvento;
	JComboBox comboBoxAsistente;
	JLabel lblSeleccion;
	private JComboBox<Integer> comboBoxDia;
	private JComboBox<String> comboBoxMes;
	private JComboBox<Integer> comboBoxAnio;
	private Integer[] dias;
	private String[] meses;
	private Integer[] anio;
	Factory factory = Factory.get();

	public RegistroEdicionEvento() {
		setSize(new Dimension(493, 513));

		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Registro a Edivion de Evento");
		setBounds(100, 100, 500, 513);

		dias = new Integer[31];
		for (int i = 0; i < 31; i++)
			dias[i] = i + 1;

		meses = new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
				"Octubre", "Noviembre", "Diciembre" };

		int anioActual = Year.now().getValue();
		int anioAIngresar = anioActual;
		anio = new Integer[121];
		for (int i = 0; i <= 120; i++) {
			anio[i] = anioAIngresar;
			anioAIngresar--;
		}

		setBounds(100, 100, 455, 513);
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 480, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 31, 0, 0, 77, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JPanel panelComboBox = new JPanel();
		GridBagConstraints gbc_panelComboBox = new GridBagConstraints();
		gbc_panelComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_panelComboBox.fill = GridBagConstraints.BOTH;
		gbc_panelComboBox.gridx = 0;
		gbc_panelComboBox.gridy = 0;
		panel.add(panelComboBox, gbc_panelComboBox);
		GridBagLayout gbl_panelComboBox = new GridBagLayout();
		gbl_panelComboBox.columnWidths = new int[] { 69, 239, 0, 0, 0, 0, 0 };
		gbl_panelComboBox.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelComboBox.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelComboBox.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelComboBox.setLayout(gbl_panelComboBox);

		JLabel lblNewLabel = new JLabel("Eventos:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panelComboBox.add(lblNewLabel, gbc_lblNewLabel);

		comboBoxEvento = new JComboBox();
		comboBoxEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBoxEvento.getSelectedIndex() > 0) {
					borrarSeleccion();
					String eventoSeleccionado = (String) comboBoxEvento.getSelectedItem();
					var evento = factory.getIEventoController();
					try {
						Set<DTEdicion> ediciones = evento.mostrarEdiciones(eventoSeleccionado);
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.setRowCount(0);
						for (var ed : ediciones) {
							model.addRow(new Object[] { ed.nombre(), ed.sigla(), ed.fechaInicio(), ed.fechaFin(),
									ed.fechaAlta(), ed.pais(), ed.ciudad() });
						}

					} catch (ValidationInputException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		GridBagConstraints gbc_comboBoxEvento = new GridBagConstraints();
		gbc_comboBoxEvento.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEvento.gridwidth = 2;
		gbc_comboBoxEvento.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEvento.gridx = 0;
		gbc_comboBoxEvento.gridy = 1;
		panelComboBox.add(comboBoxEvento, gbc_comboBoxEvento);

		JLabel lblNewLabel_1 = new JLabel("Asistentes:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panelComboBox.add(lblNewLabel_1, gbc_lblNewLabel_1);

		comboBoxAsistente = new JComboBox();
		GridBagConstraints gbc_comboBoxAsistente = new GridBagConstraints();
		gbc_comboBoxAsistente.insets = new Insets(0, 0, 0, 5);
		gbc_comboBoxAsistente.gridwidth = 2;
		gbc_comboBoxAsistente.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxAsistente.gridx = 0;
		gbc_comboBoxAsistente.gridy = 3;
		panelComboBox.add(comboBoxAsistente, gbc_comboBoxAsistente);

		JLabel lblNewLabel_3 = new JLabel("Fecha de Realizacion:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 1;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		comboBoxDia = new JComboBox<>(dias);
		;
		panel_1.add(comboBoxDia);

		panel_1.add(Box.createRigidArea(new Dimension(20, 10)));

		comboBoxMes = new JComboBox<>(meses);
		panel_1.add(comboBoxMes);

		panel_1.add(Box.createRigidArea(new Dimension(20, 10)));

		comboBoxAnio = new JComboBox<>(anio);
		panel_1.add(comboBoxAnio);

		JPanel panelTabla = new JPanel();
		GridBagConstraints gbc_panelTabla = new GridBagConstraints();
		gbc_panelTabla.insets = new Insets(0, 0, 5, 0);
		gbc_panelTabla.fill = GridBagConstraints.BOTH;
		gbc_panelTabla.gridx = 0;
		gbc_panelTabla.gridy = 3;
		panel.add(panelTabla, gbc_panelTabla);
		GridBagLayout gbl_panelTabla = new GridBagLayout();
		gbl_panelTabla.columnWidths = new int[] { 184, 241, 0 };
		gbl_panelTabla.rowHeights = new int[] { 0, 219, 0 };
		gbl_panelTabla.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelTabla.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panelTabla.setLayout(gbl_panelTabla);

		JLabel lblNewLabel_2 = new JLabel("Ediciones:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		panelTabla.add(lblNewLabel_2, gbc_lblNewLabel_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMinimumSize(new Dimension(500, 23));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.weighty = 1.0;
		panelTabla.add(scrollPane, gbc_scrollPane);

		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] { "Nombre", "Sigla", "Fecha Inicio", "Fecha Fin", "Fecha Alta", "Pais", "Ciudad" }));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int fila = table.getSelectedRow();
					if (fila != -1 && table.getValueAt(fila, 0) != null) {
						Object valor = table.getValueAt(fila, 0);
						Window parent = SwingUtilities.getWindowAncestor(table);
						TipoRegistroSeleccionado dialog = new TipoRegistroSeleccionado(parent, "Tipo de Registro",
								valor.toString());
						dialog.pack(); // ajusta el tamaño al contenido
						dialog.setLocationRelativeTo(parent);
						dialog.setVisible(true);
						seleccionado = dialog.getSelectedValue();
						if (seleccionado != null)
							mostrarSeleccion();
						else
							borrarSeleccion();
					}
				}
			}
		});
		scrollPane.setViewportView(table);
		scrollPane.setPreferredSize(new Dimension(450, 200));
		table.setPreferredScrollableViewportSize(new Dimension(560, 430));
		table.setDefaultEditor(Object.class, null);

		lblSeleccion = new JLabel("New label");
		lblSeleccion.setVisible(false);
		GridBagConstraints gbc_lblSeleccion = new GridBagConstraints();
		gbc_lblSeleccion.anchor = GridBagConstraints.WEST;
		gbc_lblSeleccion.insets = new Insets(0, 0, 5, 0);
		gbc_lblSeleccion.gridx = 0;
		gbc_lblSeleccion.gridy = 4;
		panel.add(lblSeleccion, gbc_lblSeleccion);

		JPanel panelBtn = new JPanel();
		GridBagConstraints gbc_panelBtn = new GridBagConstraints();
		gbc_panelBtn.fill = GridBagConstraints.VERTICAL;
		gbc_panelBtn.gridx = 0;
		gbc_panelBtn.gridy = 5;
		panel.add(panelBtn, gbc_panelBtn);
		panelBtn.setLayout(new BoxLayout(panelBtn, BoxLayout.X_AXIS));

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaRegistroEdicionEventoActionPerformed(e);
			}
		});
		panelBtn.add(btnAceptar);
		panelBtn.add(Box.createRigidArea(new Dimension(20, 10)));
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				setVisible(false);
			}
		});
		panelBtn.add(btnCancelar);

		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				limpiarCampos();

			}
		});
	}

	protected void cargarDatos() {
		System.out.println("Cargando datos...");
		comboBoxEvento.addItem("");
		comboBoxAsistente.addItem("");
		var evento = factory.getIEventoController();
		Set<DTEvento> listaE = evento.listarEventos();
		for (var e : listaE) {
			comboBoxEvento.addItem(e.nombre());
		}
		var usuario = factory.getIUsuarioController();
		Set<DTAsistente> listaA = usuario.mostrarAsistentes();
		for (var a : listaA) {
			comboBoxAsistente.addItem(a.nickname());
		}
		comboBoxEvento.setSelectedIndex(0);
		comboBoxAsistente.setSelectedIndex(0);

	}

	protected void altaRegistroEdicionEventoActionPerformed(ActionEvent e) {
		if (checkCampos()) {
			String nicknameAsistente = (String) comboBoxAsistente.getSelectedItem();
			int dia = (Integer) comboBoxDia.getSelectedItem();
			String mesStr = (String) comboBoxMes.getSelectedItem();
			int anio = (Integer) comboBoxAnio.getSelectedItem();
			Map<String, Integer> meses = Map.ofEntries(
					Map.entry("Enero", 0),
					Map.entry("Febrero", 1),
					Map.entry("Marzo", 2),
					Map.entry("Abril", 3),
					Map.entry("Mayo", 4),
					Map.entry("Junio", 5),
					Map.entry("Julio", 6),
					Map.entry("Agosto", 7),
					Map.entry("Septiembre", 8),
					Map.entry("Octubre", 9),
					Map.entry("Noviembre", 10),
					Map.entry("Diciembre", 11));
			int mes = meses.get(mesStr);
			Calendar cal = Calendar.getInstance();
			cal.set(anio, mes, dia, 0, 0, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date fecha = cal.getTime();
			String nombreTipoRegistro = seleccionado.nombre();
			var edicion = factory.getIEdicionController();
			try {
				edicion.altaRegistroEdicionEvento(nombreTipoRegistro, nicknameAsistente,
						new java.sql.Date(fecha.getTime()));
				JOptionPane.showMessageDialog(this, "Se ha registrado con exito", "Registro a Edicion de Evento",
						JOptionPane.INFORMATION_MESSAGE);
				limpiarCampos();
				setVisible(false);
			} catch (IllegalStateException en) {
				util.ExceptionHandler.manageException(en);
			}
		}
	}

	private void mostrarSeleccion() {
		lblSeleccion.setText("Tipo de Registro seleccionado: " + seleccionado.nombre());
		lblSeleccion.setVisible(true);
	}

	private void borrarSeleccion() {
		lblSeleccion.setText("");
		lblSeleccion.setVisible(false);
		seleccionado = null;
	}

	private boolean checkCampos() {
		if (comboBoxAsistente.getSelectedIndex() == -1 || comboBoxDia.getSelectedIndex() == -1
				|| comboBoxMes.getSelectedIndex() == -1 || comboBoxAnio.getSelectedIndex() == -1
				|| seleccionado == null) {
			JOptionPane.showMessageDialog(this, "Quedan campos obligatorios por rellenar",
					"Registro a Edicion de Evento", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void limpiarCampos() {
		comboBoxEvento.setSelectedIndex(-1);
		comboBoxAsistente.setSelectedIndex(-1);
		comboBoxEvento.removeAllItems();
		comboBoxAsistente.removeAllItems();
		comboBoxDia.setSelectedIndex(0);
		comboBoxMes.setSelectedIndex(0);
		comboBoxAnio.setSelectedIndex(0);
		table.clearSelection();
		table.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] { "Nombre", "Sigla", "Fecha Inicio", "Fecha Fin", "Fecha Alta", "Pais", "Ciudad" }));
		lblSeleccion.setText("");
		lblSeleccion.setVisible(false);
		seleccionado = null;
		var e = factory.getIEdicionController();
		e.cancelarRegistroEdicionEvento();
	}

}
