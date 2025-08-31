package gui;

import components.TagLabel;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

public class ConsultaRegistro extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JComboBox cmbUsuarios;
	private JTable tableListadoRegistrosUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaRegistro frame = new ConsultaRegistro();
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
	public ConsultaRegistro() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel searchPanel = new JPanel();
		getContentPane().add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblUsuario = new JLabel("Usuario:");
		searchPanel.add(lblUsuario);
		
		cmbUsuarios = new JComboBox();
		cmbUsuarios.setToolTipText("Usuarios");
		searchPanel.add(cmbUsuarios);
//		cmbUsuarios.setColumns(10);
		
		JPanel dataPanel = new JPanel();
		getContentPane().add(dataPanel, BorderLayout.CENTER);
		GridBagLayout gbl_dataPanel = new GridBagLayout();
		gbl_dataPanel.columnWidths = new int[]{0, 0};
		gbl_dataPanel.rowHeights = new int[]{0, 0, 0};
		gbl_dataPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_dataPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		dataPanel.setLayout(gbl_dataPanel);
		
		tableListadoRegistrosUsuario = new JTable();
		
		GridBagConstraints gbc_tableListadoRegistrosUsuario = new GridBagConstraints();
		gbc_tableListadoRegistrosUsuario.gridheight = 2;
		gbc_tableListadoRegistrosUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_tableListadoRegistrosUsuario.fill = GridBagConstraints.BOTH;
		gbc_tableListadoRegistrosUsuario.gridx = 0;
		gbc_tableListadoRegistrosUsuario.gridy = 0;
		dataPanel.add(tableListadoRegistrosUsuario, gbc_tableListadoRegistrosUsuario);
		
		JPanel detailsPanel = new JPanel();
		detailsPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Detalle", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_detailsPanel = new GridBagConstraints();
		gbc_detailsPanel.gridheight = 2;
		gbc_detailsPanel.fill = GridBagConstraints.BOTH;
		gbc_detailsPanel.gridx = 1;
		gbc_detailsPanel.gridy = 0;
		dataPanel.add(detailsPanel, gbc_detailsPanel);
		GridBagLayout gbl_detailsPanel = new GridBagLayout();
		gbl_detailsPanel.columnWidths = new int[]{0, 0, 0};
		gbl_detailsPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_detailsPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_detailsPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		detailsPanel.setLayout(gbl_detailsPanel);
		
		JLabel labelFecha = new JLabel("Fecha:");
		labelFecha.setVerticalAlignment(SwingConstants.TOP);
		labelFecha.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_labelFecha = new GridBagConstraints();
		gbc_labelFecha.insets = new Insets(0, 0, 5, 5);
		gbc_labelFecha.gridx = 0;
		gbc_labelFecha.gridy = 0;
		detailsPanel.add(labelFecha, gbc_labelFecha);
		
		JLabel lblFechaRegistroPlaceholder = new JLabel("FechaReg");
		GridBagConstraints gbc_lblFechaRegistroPlaceholder = new GridBagConstraints();
		gbc_lblFechaRegistroPlaceholder.insets = new Insets(0, 0, 5, 0);
		gbc_lblFechaRegistroPlaceholder.gridx = 1;
		gbc_lblFechaRegistroPlaceholder.gridy = 0;
		detailsPanel.add(lblFechaRegistroPlaceholder, gbc_lblFechaRegistroPlaceholder);
		
		JLabel lblCostoRegistro = new JLabel("Costo:");
		lblCostoRegistro.setVerticalAlignment(SwingConstants.TOP);
		lblCostoRegistro.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblCostoRegistro = new GridBagConstraints();
		gbc_lblCostoRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_lblCostoRegistro.gridx = 0;
		gbc_lblCostoRegistro.gridy = 1;
		detailsPanel.add(lblCostoRegistro, gbc_lblCostoRegistro);
		
		JLabel lblSiglaPlaceholder = new JLabel("100");
		GridBagConstraints gbc_lblSiglaPlaceholder = new GridBagConstraints();
		gbc_lblSiglaPlaceholder.insets = new Insets(0, 0, 5, 0);
		gbc_lblSiglaPlaceholder.gridx = 1;
		gbc_lblSiglaPlaceholder.gridy = 1;
		detailsPanel.add(lblSiglaPlaceholder, gbc_lblSiglaPlaceholder);
		
		JLabel lblEvento = new JLabel("Evento:");
		lblEvento.setVerticalAlignment(SwingConstants.TOP);
		lblEvento.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblEvento = new GridBagConstraints();
		gbc_lblEvento.insets = new Insets(0, 0, 5, 5);
		gbc_lblEvento.gridx = 0;
		gbc_lblEvento.gridy = 2;
		detailsPanel.add(lblEvento, gbc_lblEvento);
		
		JLabel lblEventoPlaceholder = new JLabel("Evento_1");
		lblEventoPlaceholder.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblEventoPlaceholder = new GridBagConstraints();
		gbc_lblEventoPlaceholder.insets = new Insets(0, 0, 5, 0);
		gbc_lblEventoPlaceholder.gridx = 1;
		gbc_lblEventoPlaceholder.gridy = 2;
		detailsPanel.add(lblEventoPlaceholder, gbc_lblEventoPlaceholder);
		
		JLabel lblEdicion = new JLabel("Edición:");
		lblEdicion.setVerticalAlignment(SwingConstants.TOP);
		lblEdicion.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblEdicion = new GridBagConstraints();
		gbc_lblEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdicion.gridx = 0;
		gbc_lblEdicion.gridy = 3;
		detailsPanel.add(lblEdicion, gbc_lblEdicion);
		
		JLabel lblEdiciónPlaceholder = new JLabel("Edición_1");
		lblEdiciónPlaceholder.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblEdiciónPlaceholder = new GridBagConstraints();
		gbc_lblEdiciónPlaceholder.insets = new Insets(0, 0, 5, 0);
		gbc_lblEdiciónPlaceholder.gridx = 1;
		gbc_lblEdiciónPlaceholder.gridy = 3;
		detailsPanel.add(lblEdiciónPlaceholder, gbc_lblEdiciónPlaceholder);
		
		JLabel lblAsistente = new JLabel("Asistente:");
		lblAsistente.setVerticalAlignment(SwingConstants.TOP);
		lblAsistente.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblAsistente = new GridBagConstraints();
		gbc_lblAsistente.insets = new Insets(0, 0, 5, 5);
		gbc_lblAsistente.gridx = 0;
		gbc_lblAsistente.gridy = 4;
		detailsPanel.add(lblAsistente, gbc_lblAsistente);
		
		JLabel lblAsistentePlaceholder = new JLabel("Asistente_1");
		lblAsistentePlaceholder.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblAsistentePlaceholder = new GridBagConstraints();
		gbc_lblAsistentePlaceholder.insets = new Insets(0, 0, 5, 0);
		gbc_lblAsistentePlaceholder.gridx = 1;
		gbc_lblAsistentePlaceholder.gridy = 4;
		detailsPanel.add(lblAsistentePlaceholder, gbc_lblAsistentePlaceholder);
		
		JLabel lblTipoDeRegistro = new JLabel("Tipo de Registro:");
		lblTipoDeRegistro.setVerticalAlignment(SwingConstants.TOP);
		lblTipoDeRegistro.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblTipoDeRegistro = new GridBagConstraints();
		gbc_lblTipoDeRegistro.insets = new Insets(0, 0, 0, 5);
		gbc_lblTipoDeRegistro.gridx = 0;
		gbc_lblTipoDeRegistro.gridy = 5;
		detailsPanel.add(lblTipoDeRegistro, gbc_lblTipoDeRegistro);
		
		JLabel lblTipoderegistro = new JLabel("TipoDeRegistro_1");
		lblTipoderegistro.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblTipoderegistro = new GridBagConstraints();
		gbc_lblTipoderegistro.gridx = 1;
		gbc_lblTipoderegistro.gridy = 5;
		detailsPanel.add(lblTipoderegistro, gbc_lblTipoderegistro);

	}

}
