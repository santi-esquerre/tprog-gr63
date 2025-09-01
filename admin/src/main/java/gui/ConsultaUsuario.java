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
	private JTable tblUsuarios;
	private JTextField txtNombreOrganizador;
	private JTextField txtCorreoOrganizador;
	private JTextField txtNicknameOrganizador;
	private JTextField txtLinkOrganizador;
	private JTable table_1;
	private JTextField txtNicknameAsistente;
	private JTextField txtNombreAsistente;
	private JTextField txtApellidoAsistente;
	private JTextField txtCorreoAsistente;
	private JTable tblRegistros;
	private JTextField txtDia;
	private JTextField txtMes;
	private JTextField txtAnio;

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
		
		tblUsuarios = new JTable();
		tblUsuarios.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(tblUsuarios);
		tblUsuarios.setModel(new DefaultTableModel(
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
		
		txtNicknameOrganizador = new JTextField();
		txtNicknameOrganizador.setEditable(false);
		txtNicknameOrganizador.setColumns(10);
		GridBagConstraints gbc_txtNicknameOrganizador = new GridBagConstraints();
		gbc_txtNicknameOrganizador.insets = new Insets(0, 0, 5, 0);
		gbc_txtNicknameOrganizador.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNicknameOrganizador.gridx = 1;
		gbc_txtNicknameOrganizador.gridy = 0;
		panel.add(txtNicknameOrganizador, gbc_txtNicknameOrganizador);
		
		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		txtNombreOrganizador = new JTextField();
		txtNombreOrganizador.setEditable(false);
		GridBagConstraints gbc_txtNombreOrganizador = new GridBagConstraints();
		gbc_txtNombreOrganizador.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombreOrganizador.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombreOrganizador.gridx = 1;
		gbc_txtNombreOrganizador.gridy = 1;
		panel.add(txtNombreOrganizador, gbc_txtNombreOrganizador);
		txtNombreOrganizador.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Correo electrónico:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtCorreoOrganizador = new JTextField();
		txtCorreoOrganizador.setEditable(false);
		txtCorreoOrganizador.setColumns(10);
		GridBagConstraints gbc_txtCorreoOrganizador = new GridBagConstraints();
		gbc_txtCorreoOrganizador.insets = new Insets(0, 0, 5, 0);
		gbc_txtCorreoOrganizador.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCorreoOrganizador.gridx = 1;
		gbc_txtCorreoOrganizador.gridy = 2;
		panel.add(txtCorreoOrganizador, gbc_txtCorreoOrganizador);
		
		JLabel lblNewLabel_1_1 = new JLabel("Link del sitio web:");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1.gridx = 0;
		gbc_lblNewLabel_1_1.gridy = 3;
		panel.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);
		
		txtLinkOrganizador = new JTextField();
		txtLinkOrganizador.setEditable(false);
		txtLinkOrganizador.setColumns(10);
		GridBagConstraints gbc_txtLinkOrganizador = new GridBagConstraints();
		gbc_txtLinkOrganizador.insets = new Insets(0, 0, 5, 0);
		gbc_txtLinkOrganizador.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLinkOrganizador.gridx = 1;
		gbc_txtLinkOrganizador.gridy = 3;
		panel.add(txtLinkOrganizador, gbc_txtLinkOrganizador);
		
		JLabel lblNewLabel_2 = new JLabel("Descripción:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.gridwidth = 2;
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 4;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JTextArea txtDescripcionOrganizador = new JTextArea();
		txtDescripcionOrganizador.setEditable(false);
		GridBagConstraints gbc_txtDescripcionOrganizador = new GridBagConstraints();
		gbc_txtDescripcionOrganizador.insets = new Insets(0, 0, 5, 0);
		gbc_txtDescripcionOrganizador.gridwidth = 2;
		gbc_txtDescripcionOrganizador.fill = GridBagConstraints.BOTH;
		gbc_txtDescripcionOrganizador.gridx = 0;
		gbc_txtDescripcionOrganizador.gridy = 5;
		panel.add(txtDescripcionOrganizador, gbc_txtDescripcionOrganizador);
		
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
		
		JPanel pnlAsistentes = new JPanel();
		pnlAsistentes.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		GridBagLayout gbl_pnlAsistentes = new GridBagLayout();
		gbl_pnlAsistentes.columnWidths = new int[]{0, 0, 0};
		gbl_pnlAsistentes.rowHeights = new int[]{0,0,0,0,0,1,0};
		gbl_pnlAsistentes.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlAsistentes.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		pnlAsistentes.setLayout(gbl_pnlAsistentes);
		
		JLabel lblNickname_1 = new JLabel("Nickname:");
		lblNickname_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNickname_1 = new GridBagConstraints();
		gbc_lblNickname_1.anchor = GridBagConstraints.WEST;
		gbc_lblNickname_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNickname_1.gridx = 0;
		gbc_lblNickname_1.gridy = 0;
		pnlAsistentes.add(lblNickname_1, gbc_lblNickname_1);
		
		txtNicknameAsistente = new JTextField();
		txtNicknameAsistente.setEditable(false);
		txtNicknameAsistente.setColumns(10);
		GridBagConstraints gbc_txtNicknameAsistente = new GridBagConstraints();
		gbc_txtNicknameAsistente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNicknameAsistente.insets = new Insets(0, 0, 5, 0);
		gbc_txtNicknameAsistente.gridx = 1;
		gbc_txtNicknameAsistente.gridy = 0;
		pnlAsistentes.add(txtNicknameAsistente, gbc_txtNicknameAsistente);
		
		JLabel lblNewLabel_4 = new JLabel("Nombre:");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 1;
		pnlAsistentes.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		txtNombreAsistente = new JTextField();
		txtNombreAsistente.setEditable(false);
		txtNombreAsistente.setColumns(10);
		GridBagConstraints gbc_txtNombreAsistente = new GridBagConstraints();
		gbc_txtNombreAsistente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombreAsistente.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombreAsistente.gridx = 1;
		gbc_txtNombreAsistente.gridy = 1;
		pnlAsistentes.add(txtNombreAsistente, gbc_txtNombreAsistente);
		
		JLabel lblNewLabel_1_2 = new JLabel("Apellido:");
		GridBagConstraints gbc_lblNewLabel_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_1_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_2.gridx = 0;
		gbc_lblNewLabel_1_2.gridy = 2;
		pnlAsistentes.add(lblNewLabel_1_2, gbc_lblNewLabel_1_2);
		
		txtApellidoAsistente = new JTextField();
		txtApellidoAsistente.setEditable(false);
		txtApellidoAsistente.setColumns(10);
		GridBagConstraints gbc_txtApellidoAsistente = new GridBagConstraints();
		gbc_txtApellidoAsistente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoAsistente.insets = new Insets(0, 0, 5, 0);
		gbc_txtApellidoAsistente.gridx = 1;
		gbc_txtApellidoAsistente.gridy = 2;
		pnlAsistentes.add(txtApellidoAsistente, gbc_txtApellidoAsistente);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Correo electrónico:");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_1.gridx = 0;
		gbc_lblNewLabel_1_1_1.gridy = 3;
		pnlAsistentes.add(lblNewLabel_1_1_1, gbc_lblNewLabel_1_1_1);
		
		txtCorreoAsistente = new JTextField();
		txtCorreoAsistente.setEditable(false);
		txtCorreoAsistente.setColumns(10);
		GridBagConstraints gbc_txtCorreoAsistente = new GridBagConstraints();
		gbc_txtCorreoAsistente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCorreoAsistente.insets = new Insets(0, 0, 5, 0);
		gbc_txtCorreoAsistente.gridx = 1;
		gbc_txtCorreoAsistente.gridy = 3;
		pnlAsistentes.add(txtCorreoAsistente, gbc_txtCorreoAsistente);
		
		JLabel lblNewLabel_2_1 = new JLabel("Fecha de nacimiento:");
		GridBagConstraints gbc_lblNewLabel_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_1.gridx = 0;
		gbc_lblNewLabel_2_1.gridy = 4;
		pnlAsistentes.add(lblNewLabel_2_1, gbc_lblNewLabel_2_1);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 4;
		pnlAsistentes.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		txtDia = new JTextField();
		txtDia.setEditable(false);
		txtDia.setText("21");
		GridBagConstraints gbc_txtDia = new GridBagConstraints();
		gbc_txtDia.insets = new Insets(0, 0, 0, 5);
		gbc_txtDia.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDia.gridx = 0;
		gbc_txtDia.gridy = 0;
		panel_3.add(txtDia, gbc_txtDia);
		txtDia.setColumns(10);
		
		txtMes = new JTextField();
		txtMes.setEditable(false);
		txtMes.setText("12");
		GridBagConstraints gbc_txtMes = new GridBagConstraints();
		gbc_txtMes.insets = new Insets(0, 0, 0, 5);
		gbc_txtMes.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMes.gridx = 1;
		gbc_txtMes.gridy = 0;
		panel_3.add(txtMes, gbc_txtMes);
		txtMes.setColumns(10);
		
		txtAnio = new JTextField();
		txtAnio.setEditable(false);
		txtAnio.setText("2004");
		GridBagConstraints gbc_txtAnio = new GridBagConstraints();
		gbc_txtAnio.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAnio.gridx = 2;
		gbc_txtAnio.gridy = 0;
		panel_3.add(txtAnio, gbc_txtAnio);
		txtAnio.setColumns(10);
		
		JLabel lblNewLabel_3_1 = new JLabel("Registros:");
		GridBagConstraints gbc_lblNewLabel_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1.gridwidth = 2;
		gbc_lblNewLabel_3_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3_1.gridx = 0;
		gbc_lblNewLabel_3_1.gridy = 5;
		pnlAsistentes.add(lblNewLabel_3_1, gbc_lblNewLabel_3_1);
		
		JScrollPane scrollPane_1_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1_1 = new GridBagConstraints();
		gbc_scrollPane_1_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1_1.gridwidth = 2;
		gbc_scrollPane_1_1.gridx = 0;
		gbc_scrollPane_1_1.gridy = 6;
		pnlAsistentes.add(scrollPane_1_1, gbc_scrollPane_1_1);
		scrollPane_1_1.setViewportView(tblRegistros);
		
		tblRegistros = new JTable();
		tblRegistros.setModel(new DefaultTableModel(
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
		tblRegistros.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane_1_1.setViewportView(tblRegistros);
		tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(98);
		
		panel_1.add(panel);
		//panel_1.add(pnlAsistentes);

	}

}