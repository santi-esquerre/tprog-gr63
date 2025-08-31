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

public class ConsultaEvento extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTable tableListadoEventos;
	private JTable tableListadoEdiciones;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaEvento frame = new ConsultaEvento();
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
	public ConsultaEvento() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel searchPanel = new JPanel();
		getContentPane().add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNombre = new JLabel("Nombre:");
		searchPanel.add(lblNombre);
		
		txtNombre = new JTextField();
		searchPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblCategoria = new JLabel("Categoría:");
		searchPanel.add(lblCategoria);
		
		JComboBox cmbCategorias = new JComboBox();
		searchPanel.add(cmbCategorias);
		
		JPanel dataPanel = new JPanel();
		getContentPane().add(dataPanel, BorderLayout.CENTER);
		GridBagLayout gbl_dataPanel = new GridBagLayout();
		gbl_dataPanel.columnWidths = new int[]{0, 0};
		gbl_dataPanel.rowHeights = new int[]{0, 0, 0};
		gbl_dataPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_dataPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		dataPanel.setLayout(gbl_dataPanel);
		
		tableListadoEventos = new JTable();
		tableListadoEventos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Sigla", "Descripci\u00F3n", "Fecha de Alta"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		GridBagConstraints gbc_tableListadoEventos = new GridBagConstraints();
		gbc_tableListadoEventos.gridheight = 2;
		gbc_tableListadoEventos.insets = new Insets(0, 0, 5, 0);
		gbc_tableListadoEventos.fill = GridBagConstraints.BOTH;
		gbc_tableListadoEventos.gridx = 0;
		gbc_tableListadoEventos.gridy = 0;
		dataPanel.add(tableListadoEventos, gbc_tableListadoEventos);
		
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
		gbl_detailsPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_detailsPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_detailsPanel.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		detailsPanel.setLayout(gbl_detailsPanel);
		
		JLabel labelNombreEvento = new JLabel("Nombre:");
		GridBagConstraints gbc_labelNombreEvento = new GridBagConstraints();
		gbc_labelNombreEvento.insets = new Insets(0, 0, 5, 5);
		gbc_labelNombreEvento.gridx = 0;
		gbc_labelNombreEvento.gridy = 0;
		detailsPanel.add(labelNombreEvento, gbc_labelNombreEvento);
		
		JLabel lblNombreEventoPlaceholder = new JLabel("NombreEvento");
		GridBagConstraints gbc_lblNombreEventoPlaceholder = new GridBagConstraints();
		gbc_lblNombreEventoPlaceholder.insets = new Insets(0, 0, 5, 0);
		gbc_lblNombreEventoPlaceholder.gridx = 1;
		gbc_lblNombreEventoPlaceholder.gridy = 0;
		detailsPanel.add(lblNombreEventoPlaceholder, gbc_lblNombreEventoPlaceholder);
		
		JLabel lblSigla = new JLabel("Sigla:");
		lblSigla.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblSigla = new GridBagConstraints();
		gbc_lblSigla.insets = new Insets(0, 0, 5, 5);
		gbc_lblSigla.anchor = GridBagConstraints.WEST;
		gbc_lblSigla.gridx = 0;
		gbc_lblSigla.gridy = 1;
		detailsPanel.add(lblSigla, gbc_lblSigla);
		
		JLabel lblSiglaPlaceholder = new JLabel("CAP");
		GridBagConstraints gbc_lblSiglaPlaceholder = new GridBagConstraints();
		gbc_lblSiglaPlaceholder.insets = new Insets(0, 0, 5, 0);
		gbc_lblSiglaPlaceholder.gridx = 1;
		gbc_lblSiglaPlaceholder.gridy = 1;
		detailsPanel.add(lblSiglaPlaceholder, gbc_lblSiglaPlaceholder);
		
		JLabel lblCategorías = new JLabel("Categorías:");
		lblCategorías.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblCategorías = new GridBagConstraints();
		gbc_lblCategorías.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategorías.anchor = GridBagConstraints.NORTH;
		gbc_lblCategorías.gridx = 0;
		gbc_lblCategorías.gridy = 2;
		detailsPanel.add(lblCategorías, gbc_lblCategorías);
		
		JPanel panelCategorías = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelCategorías.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelCategorías = new GridBagConstraints();
		gbc_panelCategorías.insets = new Insets(0, 0, 5, 0);
		gbc_panelCategorías.fill = GridBagConstraints.BOTH;
		gbc_panelCategorías.gridx = 1;
		gbc_panelCategorías.gridy = 2;
		detailsPanel.add(panelCategorías, gbc_panelCategorías);
		
		panelCategorías.add(new TagLabel("Congreso"));
		panelCategorías.add(new TagLabel("Taller"));
		
		JPanel panelDetalleEdiciones = new JPanel();
		GridBagConstraints gbc_panelDetalleEdiciones = new GridBagConstraints();
		gbc_panelDetalleEdiciones.gridheight = 3;
		gbc_panelDetalleEdiciones.gridwidth = 2;
		gbc_panelDetalleEdiciones.insets = new Insets(0, 0, 0, 5);
		gbc_panelDetalleEdiciones.fill = GridBagConstraints.BOTH;
		gbc_panelDetalleEdiciones.gridx = 0;
		gbc_panelDetalleEdiciones.gridy = 3;
		detailsPanel.add(panelDetalleEdiciones, gbc_panelDetalleEdiciones);
		GridBagLayout gbl_panelDetalleEdiciones = new GridBagLayout();
		gbl_panelDetalleEdiciones.columnWidths = new int[]{0, 0};
		gbl_panelDetalleEdiciones.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panelDetalleEdiciones.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelDetalleEdiciones.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panelDetalleEdiciones.setLayout(gbl_panelDetalleEdiciones);
		
		tableListadoEdiciones = new JTable();
		tableListadoEdiciones.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Sigla", "Fecha de Inicio", "Fecha de Fin", "Ciudad", "Pa\u00EDs", "Fecha de Alta"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, Object.class, Object.class, String.class, String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		GridBagConstraints gbc_tableListadoEdiciones = new GridBagConstraints();
		gbc_tableListadoEdiciones.gridheight = 4;
		gbc_tableListadoEdiciones.insets = new Insets(0, 0, 5, 0);
		gbc_tableListadoEdiciones.fill = GridBagConstraints.BOTH;
		gbc_tableListadoEdiciones.gridx = 0;
		gbc_tableListadoEdiciones.gridy = 0;
		panelDetalleEdiciones.add(tableListadoEdiciones, gbc_tableListadoEdiciones);
		
		JPanel panelActions = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelActions.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panelActions = new GridBagConstraints();
		gbc_panelActions.anchor = GridBagConstraints.SOUTH;
		gbc_panelActions.insets = new Insets(0, 0, 5, 0);
		gbc_panelActions.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelActions.gridx = 0;
		gbc_panelActions.gridy = 4;
		panelDetalleEdiciones.add(panelActions, gbc_panelActions);
		
		JButton btnVerEdicion = new JButton("Ver edición...");
		btnVerEdicion.setEnabled(false);
		panelActions.add(btnVerEdicion);

	}

}
