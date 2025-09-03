package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import components.TagLabel;
import datatypes.DTEdicion;
import datatypes.DTEdicionDetallada;
import datatypes.DTEvento;
import datatypes.DTEventoDetallado;
import interfaces.IEdicionController;
import interfaces.IEventoController;

public class ConsultaEvento extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTable tableListadoEventos;
	private JTable tableListadoEdiciones;
	private final IEventoController eventoController;
	private final IEdicionController edicionController;
	private JButton btnVerEdicion;

	// Detail panel labels
	private JLabel lblNombreEventoPlaceholder;
	private JLabel lblSiglaPlaceholder;
	private JLabel lblDescripcionPlaceholder;
	private JPanel panelCategorias;
	private JPanel detailsPanel;

	private DefaultTableModel eventosTableModel;
	private DefaultTableModel edicionesTableModel;
	private List<DTEvento> allEventos; // For filtering
	private Set<DTEdicion> currentEdiciones; // Current event's editions

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				// For testing purposes only - in real app, controllers should be injected
				ConsultaEvento frame = new ConsultaEvento(null, null);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConsultaEvento(IEventoController eventoController, IEdicionController edicionController) {
		this.eventoController = eventoController;
		this.edicionController = edicionController;
		this.setMaximizable(true);
		this.setClosable(true);
		this.setTitle("Consulta de Evento");

		setBounds(100, 100, 1000, 700);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel searchPanel = new JPanel();
		getContentPane().add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblNombre = new JLabel("Nombre:");
		searchPanel.add(lblNombre);

		txtNombre = new JTextField();
		searchPanel.add(txtNombre);
		txtNombre.setColumns(15);

		JPanel dataPanel = new JPanel();
		getContentPane().add(dataPanel, BorderLayout.CENTER);
		dataPanel.setLayout(new BorderLayout(5, 5));

		// Left panel with events table
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.setBorder(new TitledBorder("Eventos"));

		// Events table setup
		String[] eventosColumns = { "Nombre", "Sigla", "Descripción", "Fecha Alta" };
		eventosTableModel = new DefaultTableModel(eventosColumns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableListadoEventos = new JTable(eventosTableModel);
		tableListadoEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane eventosScrollPane = new JScrollPane(tableListadoEventos);
		leftPanel.add(eventosScrollPane, BorderLayout.CENTER);

		dataPanel.add(leftPanel, BorderLayout.WEST);
		leftPanel.setPreferredSize(new java.awt.Dimension(400, 0));

		// Right panel with details
		detailsPanel = new JPanel();
		detailsPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Detalle",
				TitledBorder.LEFT, TitledBorder.TOP, null, null));
		detailsPanel.setPreferredSize(new java.awt.Dimension(580, 0));
		dataPanel.add(detailsPanel, BorderLayout.CENTER);

		initializeDetailsPanel();
		setupEventHandlers();
		loadEventos();
	}

	public void loadForm() {
		System.out.println("Cargando datos...");
		loadEventos();
	}

	private void initializeDetailsPanel() {
		GridBagLayout gbl_detailsPanel = new GridBagLayout();
		gbl_detailsPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_detailsPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_detailsPanel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_detailsPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		detailsPanel.setLayout(gbl_detailsPanel);

		JLabel labelNombreEvento = new JLabel("Nombre:");
		labelNombreEvento.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_labelNombreEvento = new GridBagConstraints();
		gbc_labelNombreEvento.insets = new Insets(5, 5, 5, 5);
		gbc_labelNombreEvento.anchor = GridBagConstraints.WEST;
		gbc_labelNombreEvento.gridx = 0;
		gbc_labelNombreEvento.gridy = 0;
		detailsPanel.add(labelNombreEvento, gbc_labelNombreEvento);

		lblNombreEventoPlaceholder = new JLabel("-");
		GridBagConstraints gbc_lblNombreEventoPlaceholder = new GridBagConstraints();
		gbc_lblNombreEventoPlaceholder.insets = new Insets(5, 0, 5, 5);
		gbc_lblNombreEventoPlaceholder.anchor = GridBagConstraints.WEST;
		gbc_lblNombreEventoPlaceholder.gridx = 1;
		gbc_lblNombreEventoPlaceholder.gridy = 0;
		detailsPanel.add(lblNombreEventoPlaceholder, gbc_lblNombreEventoPlaceholder);

		JLabel lblSigla = new JLabel("Sigla:");
		lblSigla.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblSigla = new GridBagConstraints();
		gbc_lblSigla.insets = new Insets(0, 5, 5, 5);
		gbc_lblSigla.anchor = GridBagConstraints.WEST;
		gbc_lblSigla.gridx = 0;
		gbc_lblSigla.gridy = 1;
		detailsPanel.add(lblSigla, gbc_lblSigla);

		lblSiglaPlaceholder = new JLabel("-");
		GridBagConstraints gbc_lblSiglaPlaceholder = new GridBagConstraints();
		gbc_lblSiglaPlaceholder.insets = new Insets(0, 0, 5, 5);
		gbc_lblSiglaPlaceholder.anchor = GridBagConstraints.WEST;
		gbc_lblSiglaPlaceholder.gridx = 1;
		gbc_lblSiglaPlaceholder.gridy = 1;
		detailsPanel.add(lblSiglaPlaceholder, gbc_lblSiglaPlaceholder);

		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.insets = new Insets(0, 5, 5, 5);
		gbc_lblDescripcion.anchor = GridBagConstraints.WEST;
		gbc_lblDescripcion.gridx = 0;
		gbc_lblDescripcion.gridy = 2;
		detailsPanel.add(lblDescripcion, gbc_lblDescripcion);

		lblDescripcionPlaceholder = new JLabel("-");
		GridBagConstraints gbc_lblDescripcionPlaceholder = new GridBagConstraints();
		gbc_lblDescripcionPlaceholder.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcionPlaceholder.anchor = GridBagConstraints.WEST;
		gbc_lblDescripcionPlaceholder.gridx = 1;
		gbc_lblDescripcionPlaceholder.gridy = 2;
		detailsPanel.add(lblDescripcionPlaceholder, gbc_lblDescripcionPlaceholder);

		JLabel lblCategorías = new JLabel("Categorías:");
		lblCategorías.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblCategorías = new GridBagConstraints();
		gbc_lblCategorías.insets = new Insets(0, 5, 5, 5);
		gbc_lblCategorías.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblCategorías.gridx = 0;
		gbc_lblCategorías.gridy = 3;
		detailsPanel.add(lblCategorías, gbc_lblCategorías);

		panelCategorias = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelCategorias.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelCategorías = new GridBagConstraints();
		gbc_panelCategorías.insets = new Insets(0, 0, 5, 5);
		gbc_panelCategorías.fill = GridBagConstraints.BOTH;
		gbc_panelCategorías.gridx = 1;
		gbc_panelCategorías.gridy = 3;
		detailsPanel.add(panelCategorias, gbc_panelCategorías);

		JPanel panelDetalleEdiciones = new JPanel();
		panelDetalleEdiciones.setBorder(new TitledBorder("Ediciones"));
		GridBagConstraints gbc_panelDetalleEdiciones = new GridBagConstraints();
		gbc_panelDetalleEdiciones.gridwidth = 2;
		gbc_panelDetalleEdiciones.insets = new Insets(0, 5, 5, 5);
		gbc_panelDetalleEdiciones.fill = GridBagConstraints.BOTH;
		gbc_panelDetalleEdiciones.gridx = 0;
		gbc_panelDetalleEdiciones.gridy = 4;
		detailsPanel.add(panelDetalleEdiciones, gbc_panelDetalleEdiciones);
		GridBagLayout gbl_panelDetalleEdiciones = new GridBagLayout();
		gbl_panelDetalleEdiciones.columnWidths = new int[] { 0, 0 };
		gbl_panelDetalleEdiciones.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelDetalleEdiciones.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelDetalleEdiciones.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		panelDetalleEdiciones.setLayout(gbl_panelDetalleEdiciones);

		// Editions table setup
		String[] edicionesColumns = { "Nombre", "Sigla", "Ciudad", "País", "Fecha Inicio", "Fecha Fin" };
		edicionesTableModel = new DefaultTableModel(edicionesColumns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableListadoEdiciones = new JTable(edicionesTableModel);
		tableListadoEdiciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane edicionesScrollPane = new JScrollPane(tableListadoEdiciones);
		GridBagConstraints gbc_tableListadoEdiciones = new GridBagConstraints();
		gbc_tableListadoEdiciones.insets = new Insets(0, 0, 5, 0);
		gbc_tableListadoEdiciones.fill = GridBagConstraints.BOTH;
		gbc_tableListadoEdiciones.gridx = 0;
		gbc_tableListadoEdiciones.gridy = 0;
		panelDetalleEdiciones.add(edicionesScrollPane, gbc_tableListadoEdiciones);

		JPanel panelActions = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelActions.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panelActions = new GridBagConstraints();
		gbc_panelActions.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelActions.gridx = 0;
		gbc_panelActions.gridy = 1;
		panelDetalleEdiciones.add(panelActions, gbc_panelActions);

		btnVerEdicion = new JButton("Ver edición...");
		btnVerEdicion.setEnabled(false);
		panelActions.add(btnVerEdicion);

		// Initially disable the details panel
		enableDetailsPanel(false);
	}

	private void setupEventHandlers() {
		// Text field filter handler
		txtNombre.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filterEventos();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filterEventos();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filterEventos();
			}
		});

		// Events table selection handler
		tableListadoEventos.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = tableListadoEventos.getSelectedRow();
				if (selectedRow >= 0) {
					showEventDetails(selectedRow);
				} else {
					clearDetailsPanel();
				}
			}
		});

		// Editions table selection handler
		tableListadoEdiciones.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = tableListadoEdiciones.getSelectedRow();
				btnVerEdicion.setEnabled(selectedRow >= 0);
			}
		});

		// Ver edición button handler
		btnVerEdicion.addActionListener(e -> showEdicionDetails());
	}

	private void loadEventos() {
		if (eventoController == null) {
			return; // For testing purposes
		}

		try {
			Set<DTEvento> eventos = eventoController.listarEventos();
			allEventos = new java.util.ArrayList<>(eventos);
			updateEventosTable(allEventos);
		} catch (Exception e) {
			// Handle error - could show a message dialog
			e.printStackTrace();
		}
	}

	private void filterEventos() {
		if (allEventos == null) {
			return;
		}

		String filterText = txtNombre.getText().trim().toLowerCase();

		if (filterText.isEmpty()) {
			updateEventosTable(allEventos);
		} else {
			List<DTEvento> filteredEventos = allEventos.stream()
					.filter(evento -> evento.nombre().toLowerCase().contains(filterText))
					.collect(java.util.stream.Collectors.toList());
			updateEventosTable(filteredEventos);
		}

		clearDetailsPanel(); // Clear details when filtering
	}

	private void updateEventosTable(List<DTEvento> eventos) {
		eventosTableModel.setRowCount(0);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		for (DTEvento evento : eventos) {
			Object[] rowData = {
					evento.nombre(),
					evento.sigla(),
					evento.descripcion(),
					dateFormat.format(evento.fechaAlta())
			};
			eventosTableModel.addRow(rowData);
		}
	}

	private void showEventDetails(int rowIndex) {
		if (eventoController == null) {
			return;
		}

		// Get the selected event name from the table
		String nombreEvento = (String) eventosTableModel.getValueAt(rowIndex, 0);

		try {
			DTEventoDetallado eventoDetallado = eventoController.obtenerDatosDetalladosEvento(nombreEvento);

			if (eventoDetallado != null) {
				updateDetailsPanel(eventoDetallado);
				enableDetailsPanel(true);
			}
		} catch (Exception e) {
			// Handle error
			e.printStackTrace();
		}
	}

	private void updateDetailsPanel(DTEventoDetallado eventoDetallado) {
		// Update basic info
		lblNombreEventoPlaceholder.setText(eventoDetallado.evento().nombre());
		lblSiglaPlaceholder.setText(eventoDetallado.evento().sigla());
		lblDescripcionPlaceholder.setText(eventoDetallado.evento().descripcion());

		// Update categories
		panelCategorias.removeAll();
		for (String categoria : eventoDetallado.categorias()) {
			panelCategorias.add(new TagLabel(categoria));
		}
		panelCategorias.revalidate();
		panelCategorias.repaint();

		// Update editions table
		updateEdicionesTable(eventoDetallado.ediciones());
		currentEdiciones = eventoDetallado.ediciones();
	}

	private void updateEdicionesTable(Set<DTEdicion> ediciones) {
		edicionesTableModel.setRowCount(0);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		for (DTEdicion edicion : ediciones) {
			Object[] rowData = {
					edicion.nombre(),
					edicion.sigla(),
					edicion.ciudad(),
					edicion.pais(),
					dateFormat.format(edicion.fechaInicio()),
					dateFormat.format(edicion.fechaFin())
			};
			edicionesTableModel.addRow(rowData);
		}

		btnVerEdicion.setEnabled(false); // Reset button state
	}

	private void clearDetailsPanel() {
		lblNombreEventoPlaceholder.setText("-");
		lblSiglaPlaceholder.setText("-");
		lblDescripcionPlaceholder.setText("-");
		panelCategorias.removeAll();
		panelCategorias.revalidate();
		panelCategorias.repaint();

		edicionesTableModel.setRowCount(0);
		btnVerEdicion.setEnabled(false);
		enableDetailsPanel(false);
		currentEdiciones = null;
	}

	private void enableDetailsPanel(boolean enabled) {
		detailsPanel.setEnabled(enabled);
		// Change appearance to show it's disabled
		java.awt.Color color = enabled ? java.awt.Color.BLACK : java.awt.Color.GRAY;
		lblNombreEventoPlaceholder.setForeground(color);
		lblSiglaPlaceholder.setForeground(color);
		lblDescripcionPlaceholder.setForeground(color);
	}

	private void showEdicionDetails() {
		if (edicionController == null || currentEdiciones == null) {
			return;
		}

		int selectedRow = tableListadoEdiciones.getSelectedRow();
		if (selectedRow < 0) {
			return;
		}

		// Get the selected edition info
		String nombreEvento = lblNombreEventoPlaceholder.getText();
		String nombreEdicion = (String) edicionesTableModel.getValueAt(selectedRow, 0);

		try {
			DTEdicionDetallada edicionDetallada = edicionController.obtenerDatosDetalladosEdicion(nombreEvento,
					nombreEdicion);

			if (edicionDetallada != null) {
				// Show the edition details in a dialog
				showEdicionDialog(edicionDetallada);
			}
		} catch (Exception e) {
			// Handle error
			e.printStackTrace();
		}
	}

	private void showEdicionDialog(DTEdicionDetallada edicionDetallada) {
		JDialog dialog = new JDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this),
				"Detalle de Edición", true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLayout(new BorderLayout());

		// Create main panel
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

		// Header info
		JPanel headerPanel = new JPanel(new GridBagLayout());
		headerPanel.setBorder(new TitledBorder("Información General"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;

		// Add basic edition info
		addLabelValue(headerPanel, gbc, 0, 0, "Nombre:", edicionDetallada.edicion().nombre());
		addLabelValue(headerPanel, gbc, 0, 1, "Sigla:", edicionDetallada.edicion().sigla());
		addLabelValue(headerPanel, gbc, 0, 2, "Ciudad:", edicionDetallada.edicion().ciudad());
		addLabelValue(headerPanel, gbc, 0, 3, "País:", edicionDetallada.edicion().pais());

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		addLabelValue(headerPanel, gbc, 2, 0, "Fecha Inicio:",
				dateFormat.format(edicionDetallada.edicion().fechaInicio()));
		addLabelValue(headerPanel, gbc, 2, 1, "Fecha Fin:", dateFormat.format(edicionDetallada.edicion().fechaFin()));
		addLabelValue(headerPanel, gbc, 2, 2, "Fecha Alta:", dateFormat.format(edicionDetallada.edicion().fechaAlta()));

		// Organizador info
		addLabelValue(headerPanel, gbc, 2, 3, "Organizador:", edicionDetallada.organizador().nombre());

		mainPanel.add(headerPanel, BorderLayout.NORTH);

		// Tabbed pane for details
		javax.swing.JTabbedPane tabbedPane = new javax.swing.JTabbedPane();

		// Tipos de registro tab
		if (!edicionDetallada.tiposRegistro().isEmpty()) {
			tabbedPane.addTab("Tipos de Registro", createTiposRegistroPanel(edicionDetallada.tiposRegistro()));
		}

		// Patrocinios tab
		if (!edicionDetallada.patrocinios().isEmpty()) {
			tabbedPane.addTab("Patrocinios", createPatrociniosPanel(edicionDetallada.patrocinios()));
		}

		// Registros tab
		if (!edicionDetallada.registros().isEmpty()) {
			tabbedPane.addTab("Registros", createRegistrosPanel(edicionDetallada.registros()));
		}

		mainPanel.add(tabbedPane, BorderLayout.CENTER);

		// Close button
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton closeButton = new JButton("Cerrar");
		closeButton.addActionListener(e -> dialog.dispose());
		buttonPanel.add(closeButton);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		dialog.add(mainPanel);
		dialog.setSize(800, 600);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	private void addLabelValue(JPanel panel, GridBagConstraints gbc, int x, int y, String label, String value) {
		gbc.gridx = x;
		gbc.gridy = y;
		panel.add(new JLabel(label), gbc);

		gbc.gridx = x + 1;
		JLabel valueLabel = new JLabel(value != null ? value : "-");
		valueLabel.setFont(valueLabel.getFont().deriveFont(java.awt.Font.BOLD));
		panel.add(valueLabel, gbc);
	}

	private JPanel createTiposRegistroPanel(Set<datatypes.DTTipoRegistro> tiposRegistro) {
		JPanel panel = new JPanel(new BorderLayout());

		String[] columns = { "Nombre", "Descripción", "Costo", "Cupo" };
		DefaultTableModel model = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable table = new JTable(model);

		for (datatypes.DTTipoRegistro tipo : tiposRegistro) {
			model.addRow(new Object[] {
					tipo.nombre(),
					tipo.descripcion(),
					String.format("%.2f", tipo.costo()),
					tipo.cupo()
			});
		}

		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		return panel;
	}

	private JPanel createPatrociniosPanel(Set<datatypes.DTPatrocinio> patrocinios) {
		JPanel panel = new JPanel(new BorderLayout());

		String[] columns = { "Institución", "Código", "Nivel", "Monto", "Fecha" };
		DefaultTableModel model = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable table = new JTable(model);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		for (datatypes.DTPatrocinio patrocinio : patrocinios) {
			model.addRow(new Object[] {
					patrocinio.institucion().nombre(),
					patrocinio.codigo(),
					patrocinio.nivel().toString(),
					String.format("%.2f", patrocinio.monto()),
					dateFormat.format(patrocinio.fechaRealizacion())
			});
		}

		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		return panel;
	}

	private JPanel createRegistrosPanel(Set<datatypes.DTRegistro> registros) {
		JPanel panel = new JPanel(new BorderLayout());

		String[] columns = { "Asistente", "Tipo Registro", "Costo", "Fecha" };
		DefaultTableModel model = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable table = new JTable(model);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		for (datatypes.DTRegistro registro : registros) {
			model.addRow(new Object[] {
					registro.nicknameAsistente(),
					registro.tipoRegistro().nombre(),
					String.format("%.2f", registro.costo()),
					dateFormat.format(registro.fecha())
			});
		}

		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		return panel;
	}

}
