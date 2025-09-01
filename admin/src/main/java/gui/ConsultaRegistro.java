package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import datatypes.DTRegistro;
import datatypes.DTRegistroDetallado;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import interfaces.IUsuarioController;

public class ConsultaRegistro extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private final JComboBox<DTUsuarioItemListado> cmbUsuarios;
	private final JTable tableListadoRegistrosUsuario;
	private final IUsuarioController usuarioController;
	
	// Detail panel labels
	private JLabel lblFechaRegistroValue;
	private JLabel lblCostoValue;
	private JLabel lblEventoValue;
	private JLabel lblEdicionValue;
	private JLabel lblAsistenteValue;
	private JLabel lblTipoRegistroValue;
	private JLabel lblPatrocinioValue;
	private final JPanel detailsPanel;
	
	private final DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				// For testing purposes only - in real app, controller should be injected
				ConsultaRegistro frame = new ConsultaRegistro(null);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConsultaRegistro(IUsuarioController usuarioController) {
		this.usuarioController = usuarioController;
		
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel searchPanel = new JPanel();
		getContentPane().add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblUsuario = new JLabel("Usuario:");
		searchPanel.add(lblUsuario);
		
		cmbUsuarios = new JComboBox<>();
		cmbUsuarios.setToolTipText("Usuarios");
		cmbUsuarios.setRenderer(new javax.swing.DefaultListCellRenderer() {
			@Override
			public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, 
					int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value == null) {
					setText("Seleccionar asistente...");
				}
				return this;
			}
		});
		searchPanel.add(cmbUsuarios);
		
		JPanel dataPanel = new JPanel();
		getContentPane().add(dataPanel, BorderLayout.CENTER);
		dataPanel.setLayout(new BorderLayout(5, 5));
		
		// Table setup
		String[] columnNames = {"Fecha", "Costo", "Evento", "Edición", "Tipo Registro"};
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableListadoRegistrosUsuario = new JTable(tableModel);
		tableListadoRegistrosUsuario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(tableListadoRegistrosUsuario);
		dataPanel.add(scrollPane, BorderLayout.CENTER);
		
		// Details panel
		detailsPanel = new JPanel();
		detailsPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Detalle", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		detailsPanel.setPreferredSize(new java.awt.Dimension(300, 0));
		dataPanel.add(detailsPanel, BorderLayout.EAST);
		
		initializeDetailsPanel();
		setupEventHandlers();
		loadAssistants();
	}
	
	private void initializeDetailsPanel() {
		GridBagLayout gbl_detailsPanel = new GridBagLayout();
		gbl_detailsPanel.columnWidths = new int[]{0, 0, 0};
		gbl_detailsPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_detailsPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_detailsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		detailsPanel.setLayout(gbl_detailsPanel);
		
		// Fecha
		JLabel labelFecha = new JLabel("Fecha:");
		GridBagConstraints gbc_labelFecha = new GridBagConstraints();
		gbc_labelFecha.insets = new Insets(5, 5, 5, 5);
		gbc_labelFecha.anchor = GridBagConstraints.WEST;
		gbc_labelFecha.gridx = 0;
		gbc_labelFecha.gridy = 0;
		detailsPanel.add(labelFecha, gbc_labelFecha);
		
		lblFechaRegistroValue = new JLabel("-");
		GridBagConstraints gbc_lblFechaRegistroValue = new GridBagConstraints();
		gbc_lblFechaRegistroValue.insets = new Insets(5, 0, 5, 5);
		gbc_lblFechaRegistroValue.anchor = GridBagConstraints.WEST;
		gbc_lblFechaRegistroValue.gridx = 1;
		gbc_lblFechaRegistroValue.gridy = 0;
		detailsPanel.add(lblFechaRegistroValue, gbc_lblFechaRegistroValue);
		
		// Costo
		JLabel lblCostoRegistro = new JLabel("Costo:");
		GridBagConstraints gbc_lblCostoRegistro = new GridBagConstraints();
		gbc_lblCostoRegistro.insets = new Insets(0, 5, 5, 5);
		gbc_lblCostoRegistro.anchor = GridBagConstraints.WEST;
		gbc_lblCostoRegistro.gridx = 0;
		gbc_lblCostoRegistro.gridy = 1;
		detailsPanel.add(lblCostoRegistro, gbc_lblCostoRegistro);
		
		lblCostoValue = new JLabel("-");
		GridBagConstraints gbc_lblCostoValue = new GridBagConstraints();
		gbc_lblCostoValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblCostoValue.anchor = GridBagConstraints.WEST;
		gbc_lblCostoValue.gridx = 1;
		gbc_lblCostoValue.gridy = 1;
		detailsPanel.add(lblCostoValue, gbc_lblCostoValue);
		
		// Evento
		JLabel lblEvento = new JLabel("Evento:");
		GridBagConstraints gbc_lblEvento = new GridBagConstraints();
		gbc_lblEvento.insets = new Insets(0, 5, 5, 5);
		gbc_lblEvento.anchor = GridBagConstraints.WEST;
		gbc_lblEvento.gridx = 0;
		gbc_lblEvento.gridy = 2;
		detailsPanel.add(lblEvento, gbc_lblEvento);
		
		lblEventoValue = new JLabel("-");
		GridBagConstraints gbc_lblEventoValue = new GridBagConstraints();
		gbc_lblEventoValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblEventoValue.anchor = GridBagConstraints.WEST;
		gbc_lblEventoValue.gridx = 1;
		gbc_lblEventoValue.gridy = 2;
		detailsPanel.add(lblEventoValue, gbc_lblEventoValue);
		
		// Edición
		JLabel lblEdicion = new JLabel("Edición:");
		GridBagConstraints gbc_lblEdicion = new GridBagConstraints();
		gbc_lblEdicion.insets = new Insets(0, 5, 5, 5);
		gbc_lblEdicion.anchor = GridBagConstraints.WEST;
		gbc_lblEdicion.gridx = 0;
		gbc_lblEdicion.gridy = 3;
		detailsPanel.add(lblEdicion, gbc_lblEdicion);
		
		lblEdicionValue = new JLabel("-");
		GridBagConstraints gbc_lblEdicionValue = new GridBagConstraints();
		gbc_lblEdicionValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdicionValue.anchor = GridBagConstraints.WEST;
		gbc_lblEdicionValue.gridx = 1;
		gbc_lblEdicionValue.gridy = 3;
		detailsPanel.add(lblEdicionValue, gbc_lblEdicionValue);
		
		// Asistente
		JLabel lblAsistente = new JLabel("Asistente:");
		GridBagConstraints gbc_lblAsistente = new GridBagConstraints();
		gbc_lblAsistente.insets = new Insets(0, 5, 5, 5);
		gbc_lblAsistente.anchor = GridBagConstraints.WEST;
		gbc_lblAsistente.gridx = 0;
		gbc_lblAsistente.gridy = 4;
		detailsPanel.add(lblAsistente, gbc_lblAsistente);
		
		lblAsistenteValue = new JLabel("-");
		GridBagConstraints gbc_lblAsistenteValue = new GridBagConstraints();
		gbc_lblAsistenteValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblAsistenteValue.anchor = GridBagConstraints.WEST;
		gbc_lblAsistenteValue.gridx = 1;
		gbc_lblAsistenteValue.gridy = 4;
		detailsPanel.add(lblAsistenteValue, gbc_lblAsistenteValue);
		
		// Tipo de Registro
		JLabel lblTipoDeRegistro = new JLabel("Tipo de Registro:");
		GridBagConstraints gbc_lblTipoDeRegistro = new GridBagConstraints();
		gbc_lblTipoDeRegistro.insets = new Insets(0, 5, 5, 5);
		gbc_lblTipoDeRegistro.anchor = GridBagConstraints.WEST;
		gbc_lblTipoDeRegistro.gridx = 0;
		gbc_lblTipoDeRegistro.gridy = 5;
		detailsPanel.add(lblTipoDeRegistro, gbc_lblTipoDeRegistro);
		
		lblTipoRegistroValue = new JLabel("-");
		GridBagConstraints gbc_lblTipoRegistroValue = new GridBagConstraints();
		gbc_lblTipoRegistroValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoRegistroValue.anchor = GridBagConstraints.WEST;
		gbc_lblTipoRegistroValue.gridx = 1;
		gbc_lblTipoRegistroValue.gridy = 5;
		detailsPanel.add(lblTipoRegistroValue, gbc_lblTipoRegistroValue);
		
		// Patrocinio
		JLabel lblPatrocinio = new JLabel("Patrocinio:");
		GridBagConstraints gbc_lblPatrocinio = new GridBagConstraints();
		gbc_lblPatrocinio.insets = new Insets(0, 5, 0, 5);
		gbc_lblPatrocinio.anchor = GridBagConstraints.WEST;
		gbc_lblPatrocinio.gridx = 0;
		gbc_lblPatrocinio.gridy = 6;
		detailsPanel.add(lblPatrocinio, gbc_lblPatrocinio);
		
		lblPatrocinioValue = new JLabel("-");
		GridBagConstraints gbc_lblPatrocinioValue = new GridBagConstraints();
		gbc_lblPatrocinioValue.insets = new Insets(0, 0, 0, 5);
		gbc_lblPatrocinioValue.anchor = GridBagConstraints.WEST;
		gbc_lblPatrocinioValue.gridx = 1;
		gbc_lblPatrocinioValue.gridy = 6;
		detailsPanel.add(lblPatrocinioValue, gbc_lblPatrocinioValue);
		
		// Initially disable the details panel
		enableDetailsPanel(false);
	}
	
	private void setupEventHandlers() {
		// ComboBox selection handler
		cmbUsuarios.addActionListener(e -> {
			DTUsuarioItemListado selected = (DTUsuarioItemListado) cmbUsuarios.getSelectedItem();
			if (selected != null) {
				loadUserRegistrations(selected.nickname());
			} else {
				clearTable();
			}
		});
		
		// Table selection handler
		tableListadoRegistrosUsuario.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = tableListadoRegistrosUsuario.getSelectedRow();
				if (selectedRow >= 0) {
					showRegistrationDetails(selectedRow);
				} else {
					clearDetailsPanel();
				}
			}
		});
	}
	
	private void loadAssistants() {
		if (usuarioController == null) {
			return; // For testing purposes
		}
		
		try {
			List<DTUsuarioItemListado> assistants = usuarioController.obtenerUsuarios(TipoUsuario.ASISTENTE);
			cmbUsuarios.removeAllItems();
			cmbUsuarios.addItem(null); // Add empty option
			
			for (DTUsuarioItemListado assistant : assistants) {
				cmbUsuarios.addItem(assistant);
			}
		} catch (Exception e) {
			// Handle error - could show a message dialog
			e.printStackTrace();
		}
	}
	
	private void loadUserRegistrations(String nickname) {
		if (usuarioController == null) {
			return;
		}
		
		try {
			List<DTRegistro> registrations = usuarioController.obtenerRegistrosUsuario(nickname);
			updateTable(registrations);
			clearDetailsPanel();
		} catch (Exception e) {
			// Handle error
			e.printStackTrace();
		}
	}
	
	private void updateTable(List<DTRegistro> registrations) {
		tableModel.setRowCount(0);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		for (DTRegistro registro : registrations) {
			Object[] rowData = {
				dateFormat.format(registro.fecha()),
				String.format("%.2f", registro.costo()),
				registro.nombreEvento(),
				registro.nombreEdicion(),
				registro.tipoRegistro().nombre()
			};
			tableModel.addRow(rowData);
		}
	}
	
	private void clearTable() {
		tableModel.setRowCount(0);
		clearDetailsPanel();
	}
	
	private void showRegistrationDetails(int rowIndex) {
		if (usuarioController == null) {
			return;
		}
		
		// Get the selected assistant and edition from the table
		DTUsuarioItemListado selectedAssistant = (DTUsuarioItemListado) cmbUsuarios.getSelectedItem();
		if (selectedAssistant == null) {
			return;
		}
		
		String editionName = (String) tableModel.getValueAt(rowIndex, 3); // Edition column
		
		try {
			DTRegistroDetallado detalle = usuarioController.obtenerRegistroDetallado(
				selectedAssistant.nickname(), 
				editionName
			);
			
			if (detalle != null) {
				updateDetailsPanel(detalle);
				enableDetailsPanel(true);
			}
		} catch (Exception e) {
			// Handle error
			e.printStackTrace();
		}
	}
	
	private void updateDetailsPanel(DTRegistroDetallado detalle) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		lblFechaRegistroValue.setText(dateFormat.format(detalle.fecha()));
		lblCostoValue.setText(String.format("%.2f", detalle.costo()));
		lblEventoValue.setText(detalle.evento().nombre());
		lblEdicionValue.setText(detalle.edicion().nombre());
		lblAsistenteValue.setText(detalle.asistente().nickname());
		lblTipoRegistroValue.setText(detalle.tipoRegistro().nombre());
		
		// Handle patrocinio (optional)
		if (detalle.patrocinio().isPresent()) {
			var patrocinio = detalle.patrocinio().get();
			String patrocinioText = String.format("%s - %s (Nivel: %s)", 
				patrocinio.institucion().nombre(),
				patrocinio.codigo(),
				patrocinio.nivel().toString());
			lblPatrocinioValue.setText(patrocinioText);
		} else {
			lblPatrocinioValue.setText("Sin patrocinio");
		}
	}
	
	private void clearDetailsPanel() {
		lblFechaRegistroValue.setText("-");
		lblCostoValue.setText("-");
		lblEventoValue.setText("-");
		lblEdicionValue.setText("-");
		lblAsistenteValue.setText("-");
		lblTipoRegistroValue.setText("-");
		lblPatrocinioValue.setText("-");
		enableDetailsPanel(false);
	}
	
	private void enableDetailsPanel(boolean enabled) {
		detailsPanel.setEnabled(enabled);
		// You could also change the appearance to show it's disabled
		java.awt.Color color = enabled ? java.awt.Color.BLACK : java.awt.Color.GRAY;
		lblFechaRegistroValue.setForeground(color);
		lblCostoValue.setForeground(color);
		lblEventoValue.setForeground(color);
		lblEdicionValue.setForeground(color);
		lblAsistenteValue.setForeground(color);
		lblTipoRegistroValue.setForeground(color);
		lblPatrocinioValue.setForeground(color);
	}

}
