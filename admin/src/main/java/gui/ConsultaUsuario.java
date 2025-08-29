package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.GridLayout;


public class ConsultaUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table_1;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTable table_2;
	private JTextField txtSadsad;
	private JTextField textField_9;
	private JTextField textField_10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaUsuario frame = new ConsultaUsuario();
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
	public ConsultaUsuario() {
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Consulta de usuario");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		getContentPane().add(splitPane);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"jdoe", "jdoe@email.com", "Organizador"},
				{"asmith", "asmith@email.com", "Asistente"},
				{"mjane", "mjane@email.com", "Organizador"},
				{"lgarcia", "lgarcia@email.com", "Organizador"},
				{"pfernandez", "pfernandez@email.com", "Asistente"},
				{"rrodriguez", "rrodriguez@email.com", "Organizador"},
				{"mlopez", "mlopez@email.com", "Organizador"},
				{"cjimenez", "cjimenez@email.com", "Asistente"},
				{"vcastro", "vcastro@email.com", "Organizador"},
				{"dtorres", "dtorres@email.com", "Asistente"},
				{"sramirez", "sramirez@email.com", "Organizador"},
				{"jmartinez", "jmartinez@email.com", "Organizador"},
				{"fhernandez", "fhernandez@email.com", "Organizador"},
			},
			new String[] {
				"Nickname", "Correo electr\u00F3nico", "Tipo"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(gbl_panel);
		
		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNickname = new GridBagConstraints();
		gbc_lblNickname.insets = new Insets(0, 0, 5, 5);
		gbc_lblNickname.anchor = GridBagConstraints.WEST;
		gbc_lblNickname.gridx = 0;
		gbc_lblNickname.gridy = 0;
		panel.add(lblNickname, gbc_lblNickname);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 0;
		panel.add(textField_2, gbc_textField_2);
		
		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		textField = new JTextField();
		textField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Correo electrónico:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 2;
		panel.add(textField_1, gbc_textField_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Link del sitio web:");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1.gridx = 0;
		gbc_lblNewLabel_1_1.gridy = 3;
		panel.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 3;
		panel.add(textField_3, gbc_textField_3);
		
		JLabel lblNewLabel_2 = new JLabel("Descripción:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.gridwidth = 2;
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 4;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.gridwidth = 2;
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 5;
		panel.add(textArea, gbc_textArea);
		
		JLabel lblNewLabel_3 = new JLabel("Ediciones que organiza:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.gridwidth = 2;
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 6;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 7;
		panel.add(scrollPane_1, gbc_scrollPane_1);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"Expo Innovaci\u00F3n 2023", "01/12/2023", "10/12/2023", "Uruguay", "Montevideo"},
				{"Congreso TIC 2024", "10/11/2024", "20/11/2024", "Uruguay", "Salto"},
				{"Feria Emprendedores 2025", "05/10/2025", "15/10/2025", "Uruguay", "Rivera"},
				{"Jornadas Cient\u00EDficas 2026", "10/09/2026", "20/09/2026", "Uruguay", "Paysand\u00FA"},
				{"Semana de la Tecnolog\u00EDa 2027", "08/08/2027", "18/08/2027", "Uruguay", "Colonia"},
				{"Hackathon UY 2028", "05/07/2028", "15/07/2028", "Uruguay", "Maldonado"},
				{"Encuentro de Rob\u00F3tica 2029", "02/06/2029", "12/06/2029", "Uruguay", "Canelones"},
				{"Simposio IA 2030", "01/05/2030", "10/05/2030", "Uruguay", "Florida"},
				{"Foro Educaci\u00F3n Digital 2031", "01/04/2031", "08/04/2031", "Uruguay", "Durazno"},
				{"Festival de Apps 2032", "01/03/2032", "05/03/2032", "Uruguay", "Artigas"},
				{"Expo Videojuegos 2033", "01/02/2033", "02/02/2033", "Uruguay", "Tacuaremb\u00F3"},
				{"Cumbre Startups 2034", "01/01/2034", "01/01/2034", "Uruguay", "Treinta y Tres"},
			},
			new String[] {
				"Nombre", "Inicio", "Fin", "Pa\u00EDs", "Ciudad"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, true, true, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane_1.setViewportView(table_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0,0,0,0,0,1,0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblNickname_1 = new JLabel("Nickname:");
		lblNickname_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNickname_1 = new GridBagConstraints();
		gbc_lblNickname_1.anchor = GridBagConstraints.WEST;
		gbc_lblNickname_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNickname_1.gridx = 0;
		gbc_lblNickname_1.gridy = 0;
		panel_2.add(lblNickname_1, gbc_lblNickname_1);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 0;
		panel_2.add(textField_4, gbc_textField_4);
		
		JLabel lblNewLabel_4 = new JLabel("Nombre:");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 1;
		panel_2.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		textField_5.setColumns(10);
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.insets = new Insets(0, 0, 5, 0);
		gbc_textField_5.gridx = 1;
		gbc_textField_5.gridy = 1;
		panel_2.add(textField_5, gbc_textField_5);
		
		JLabel lblNewLabel_1_2 = new JLabel("Apellido:");
		GridBagConstraints gbc_lblNewLabel_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_1_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_2.gridx = 0;
		gbc_lblNewLabel_1_2.gridy = 2;
		panel_2.add(lblNewLabel_1_2, gbc_lblNewLabel_1_2);
		
		textField_6 = new JTextField();
		textField_6.setEditable(false);
		textField_6.setColumns(10);
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.insets = new Insets(0, 0, 5, 0);
		gbc_textField_6.gridx = 1;
		gbc_textField_6.gridy = 2;
		panel_2.add(textField_6, gbc_textField_6);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Correo electrónico:");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_1.gridx = 0;
		gbc_lblNewLabel_1_1_1.gridy = 3;
		panel_2.add(lblNewLabel_1_1_1, gbc_lblNewLabel_1_1_1);
		
		textField_7 = new JTextField();
		textField_7.setEditable(false);
		textField_7.setColumns(10);
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.insets = new Insets(0, 0, 5, 0);
		gbc_textField_7.gridx = 1;
		gbc_textField_7.gridy = 3;
		panel_2.add(textField_7, gbc_textField_7);
		
		JLabel lblNewLabel_2_1 = new JLabel("Fecha de nacimiento:");
		GridBagConstraints gbc_lblNewLabel_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_1.gridx = 0;
		gbc_lblNewLabel_2_1.gridy = 4;
		panel_2.add(lblNewLabel_2_1, gbc_lblNewLabel_2_1);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 4;
		panel_2.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		txtSadsad = new JTextField();
		txtSadsad.setEditable(false);
		txtSadsad.setText("21");
		GridBagConstraints gbc_txtSadsad = new GridBagConstraints();
		gbc_txtSadsad.insets = new Insets(0, 0, 0, 5);
		gbc_txtSadsad.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSadsad.gridx = 0;
		gbc_txtSadsad.gridy = 0;
		panel_3.add(txtSadsad, gbc_txtSadsad);
		txtSadsad.setColumns(10);
		
		textField_9 = new JTextField();
		textField_9.setEditable(false);
		textField_9.setText("12");
		GridBagConstraints gbc_textField_9 = new GridBagConstraints();
		gbc_textField_9.insets = new Insets(0, 0, 0, 5);
		gbc_textField_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_9.gridx = 1;
		gbc_textField_9.gridy = 0;
		panel_3.add(textField_9, gbc_textField_9);
		textField_9.setColumns(10);
		
		textField_10 = new JTextField();
		textField_10.setEditable(false);
		textField_10.setText("2004");
		GridBagConstraints gbc_textField_10 = new GridBagConstraints();
		gbc_textField_10.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_10.gridx = 2;
		gbc_textField_10.gridy = 0;
		panel_3.add(textField_10, gbc_textField_10);
		textField_10.setColumns(10);
		
		JLabel lblNewLabel_3_1 = new JLabel("Registros:");
		GridBagConstraints gbc_lblNewLabel_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1.gridwidth = 2;
		gbc_lblNewLabel_3_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3_1.gridx = 0;
		gbc_lblNewLabel_3_1.gridy = 5;
		panel_2.add(lblNewLabel_3_1, gbc_lblNewLabel_3_1);
		
		JScrollPane scrollPane_1_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1_1 = new GridBagConstraints();
		gbc_scrollPane_1_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1_1.gridwidth = 2;
		gbc_scrollPane_1_1.gridx = 0;
		gbc_scrollPane_1_1.gridy = 6;
		panel_2.add(scrollPane_1_1, gbc_scrollPane_1_1);
		scrollPane_1_1.setViewportView(table_2);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Evento", "Edici\u00F3n", "Fecha", "Costo", "Tipo"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane_1_1.setViewportView(table_2);
		table.getColumnModel().getColumn(1).setPreferredWidth(98);
		
		//panel_1.add(panel);
		panel_1.add(panel_2);

	}

}