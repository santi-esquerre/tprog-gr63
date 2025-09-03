package gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import datatypes.DTAsistente;
import datatypes.DTEdicion;
import datatypes.DTOrganizador;
import datatypes.DTRegistro;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import interfaces.IEdicionController;
import interfaces.IEventoController;
import interfaces.IUsuarioController;
import util.ExceptionHandler;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Panel;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.awt.CardLayout;
import java.awt.Component;


public class ConsultaUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombreOrganizador;
	private JTextField txtCorreoOrganizador;
	private JTextField txtNicknameOrganizador;
	private JTextField txtLinkOrganizador;
	private JTextArea txtDescripcionOrganizador;
	private JTable tableEdiciones;
	private JTextField txtNicknameAsistente;
	private JTextField txtNombreAsistente;
	private JTextField txtApellidoAsistente;
	private JTextField txtCorreoAsistente;
	private JTable tblRegistros;
	private JTextField txtDia;
	private JTextField txtMes;
	private JTextField txtAnio;
	private JTable tableUsuarios;
	private DefaultTableModel modelUsuarios;
	private DefaultTableModel modelEdiciones;
	private DefaultTableModel modelRegistros;
	private IEdicionController edicionController;
	private IUsuarioController usuarioController;
	private List<datatypes.DTUsuarioItemListado> usuariosData;
	private JPanel pnlOrganizadores;
	private JPanel pnlAsistentes;
	private JPanel pnlDetalle;
	private JLabel lblNomEd;
	private JLabel lblSiglaEd;
	private JLabel lblFIni;
	private JLabel lblFFin;
	private JLabel lblCiudad;
	private JLabel lblPais;
	private JLabel lblOrgNick;
	private JLabel lblOrgNombre;
	private JLabel lblOrgCorreo;
	private DefaultTableModel tiposModel;
	private JTable tblTipos;
	private Button btnVerTipo;
	private DefaultTableModel registrosModel;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public ConsultaUsuario(IUsuarioController usuarioController, IEdicionController edicionController) {
		setMaximizable(true);
		this.usuarioController = usuarioController;
		this.edicionController = edicionController;
		setResizable(true);
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
		
		tableUsuarios = new JTable();
		tableUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = tableUsuarios.getSelectedRow();
				if (selectedRow >= 0) {
					String nickname = (String) modelUsuarios.getValueAt(selectedRow, 0);
					
					try {
						DTUsuarioItemListado usuarioItemListado = null;
						for (var u : usuariosData) {
							if(nickname == u.nickname()) {
								usuarioItemListado = u;
								break;
							}
						}
						
						if (usuarioItemListado.tipoUsuario() == TipoUsuario.ORGANIZADOR) {
							
							pnlOrganizadores.setVisible(true);
							pnlAsistentes.setVisible(false);

							DTOrganizador usuario = usuarioController.seleccionarOrganizador(nickname);
							txtNicknameOrganizador.setText(usuario.nickname());
							txtNombreOrganizador.setText(usuario.nombre());
							txtCorreoOrganizador.setText(usuario.correo());
							
							txtLinkOrganizador.setText(usuario.linkSitioWeb());
							txtDescripcionOrganizador.setText(usuario.descripcion());
							
							Set<DTEdicion> ediciones = edicionController.obtenerEdicionesPorOrganizador(nickname);
							modelEdiciones.setRowCount(0);
							for (var ed : ediciones) {
								modelEdiciones.addRow(new Object[] {
									ed.nombre(),
									ed.fechaAlta(),
									ed.fechaInicio(),
									ed.fechaFin(),
									ed.pais(),
									ed.ciudad()
								});
							}
							
						} else if (usuarioItemListado.tipoUsuario() == TipoUsuario.ASISTENTE) {
							
							pnlOrganizadores.setVisible(false);
							pnlAsistentes.setVisible(true);
							
							DTAsistente usuario = usuarioController.seleccionarAsistente(nickname);
							txtNicknameAsistente.setText(usuario.nickname());
							txtNombreAsistente.setText(usuario.nombre());
							txtCorreoAsistente.setText(usuario.correo());
							txtApellidoAsistente.setText(usuario.apellido());
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							String[] fechaNac = sdf.format(usuario.fechaNacimiento()).split("/");
							txtDia.setText(fechaNac[0]);
							txtMes.setText(fechaNac[1]);
							txtAnio.setText(fechaNac[2]);
							List<DTRegistro> registros = usuarioController.obtenerRegistrosUsuario(nickname);
							modelRegistros.setRowCount(0);
							for (var r : registros) {
								modelRegistros.addRow(new Object[] {
									r.nombreEvento(),
									r.nombreEdicion(),
									r.fecha(),
									r.costo(),
									r.tipoRegistro().nombre()
								});
							}
							
						}
					} catch (Exception ex) {
						ExceptionHandler.manageException(ex);
					}
					
					
				}}});
		tableUsuarios.setFillsViewportHeight(true);
		tableUsuarios.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(tableUsuarios);

		// Table setup
		String[] columnNames = {"Nickname", "Correo", "Tipo de usuario"};
		modelUsuarios = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tableUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tableUsuarios.setModel(modelUsuarios);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		
		pnlOrganizadores = new JPanel();
		
		GridBagLayout gbl_pnlOrganizadores = new GridBagLayout();
		gbl_pnlOrganizadores.columnWidths = new int[]{0, 0, 0};
		gbl_pnlOrganizadores.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pnlOrganizadores.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlOrganizadores.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		pnlOrganizadores.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlOrganizadores.setLayout(gbl_pnlOrganizadores);
		
		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNickname = new GridBagConstraints();
		gbc_lblNickname.insets = new Insets(0, 0, 5, 5);
		gbc_lblNickname.anchor = GridBagConstraints.WEST;
		gbc_lblNickname.gridx = 0;
		gbc_lblNickname.gridy = 0;
		pnlOrganizadores.add(lblNickname, gbc_lblNickname);
		
		txtNicknameOrganizador = new JTextField();
		txtNicknameOrganizador.setEditable(false);
		txtNicknameOrganizador.setColumns(10);
		GridBagConstraints gbc_txtNicknameOrganizador = new GridBagConstraints();
		gbc_txtNicknameOrganizador.insets = new Insets(0, 0, 5, 0);
		gbc_txtNicknameOrganizador.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNicknameOrganizador.gridx = 1;
		gbc_txtNicknameOrganizador.gridy = 0;
		pnlOrganizadores.add(txtNicknameOrganizador, gbc_txtNicknameOrganizador);
		
		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		pnlOrganizadores.add(lblNewLabel, gbc_lblNewLabel);
		
		txtNombreOrganizador = new JTextField();
		txtNombreOrganizador.setEditable(false);
		GridBagConstraints gbc_txtNombreOrganizador = new GridBagConstraints();
		gbc_txtNombreOrganizador.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombreOrganizador.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombreOrganizador.gridx = 1;
		gbc_txtNombreOrganizador.gridy = 1;
		pnlOrganizadores.add(txtNombreOrganizador, gbc_txtNombreOrganizador);
		txtNombreOrganizador.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Correo electrónico:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		pnlOrganizadores.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtCorreoOrganizador = new JTextField();
		txtCorreoOrganizador.setEditable(false);
		txtCorreoOrganizador.setColumns(10);
		GridBagConstraints gbc_txtCorreoOrganizador = new GridBagConstraints();
		gbc_txtCorreoOrganizador.insets = new Insets(0, 0, 5, 0);
		gbc_txtCorreoOrganizador.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCorreoOrganizador.gridx = 1;
		gbc_txtCorreoOrganizador.gridy = 2;
		pnlOrganizadores.add(txtCorreoOrganizador, gbc_txtCorreoOrganizador);
		
		JLabel lblNewLabel_1_1 = new JLabel("Link del sitio web:");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1.gridx = 0;
		gbc_lblNewLabel_1_1.gridy = 3;
		pnlOrganizadores.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);
		
		txtLinkOrganizador = new JTextField();
		txtLinkOrganizador.setEditable(false);
		txtLinkOrganizador.setColumns(10);
		GridBagConstraints gbc_txtLinkOrganizador = new GridBagConstraints();
		gbc_txtLinkOrganizador.insets = new Insets(0, 0, 5, 0);
		gbc_txtLinkOrganizador.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLinkOrganizador.gridx = 1;
		gbc_txtLinkOrganizador.gridy = 3;
		pnlOrganizadores.add(txtLinkOrganizador, gbc_txtLinkOrganizador);
		
		JLabel lblNewLabel_2 = new JLabel("Descripción:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.gridwidth = 2;
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 4;
		pnlOrganizadores.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		txtDescripcionOrganizador = new JTextArea();
		txtDescripcionOrganizador.setEditable(false);
		GridBagConstraints gbc_txtDescripcionOrganizador = new GridBagConstraints();
		gbc_txtDescripcionOrganizador.insets = new Insets(0, 0, 5, 0);
		gbc_txtDescripcionOrganizador.gridwidth = 2;
		gbc_txtDescripcionOrganizador.fill = GridBagConstraints.BOTH;
		gbc_txtDescripcionOrganizador.gridx = 0;
		gbc_txtDescripcionOrganizador.gridy = 5;
		pnlOrganizadores.add(txtDescripcionOrganizador, gbc_txtDescripcionOrganizador);
		
		JLabel lblNewLabel_3 = new JLabel("Ediciones que organiza:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.gridwidth = 2;
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 6;
		pnlOrganizadores.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 7;
		pnlOrganizadores.add(scrollPane_1, gbc_scrollPane_1);
		
		modelEdiciones = new DefaultTableModel(new String[] {
			"Nombre", "Alta", "Inicio", "Fin", "País", "Ciudad"
		}, 0) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		
		tableEdiciones = new JTable();
		tableEdiciones.setModel(modelEdiciones);
		tableEdiciones.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane_1.setViewportView(tableEdiciones);
		tableEdiciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SubConsultaEdicionConsultaUsuario subEdicion = new SubConsultaEdicionConsultaUsuario(edicionController, modelEdiciones.getValueAt(tableEdiciones.getSelectedRow(), 0).toString());
				subEdicion.setVisible(true);
			}
		});
		
		pnlAsistentes = new JPanel();
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
		tblRegistros.setFillsViewportHeight(true);
		
		modelRegistros = new DefaultTableModel(new Object[][] {},new String[] {"Evento", "Edición", "Fecha", "Costo", "Tipo"}) {
		boolean[] columnEditables = new boolean[] {
			false, false, false, false, false
		};
		public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
		}};
		
		
		tblRegistros.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tblRegistros.setModel(modelRegistros);
		scrollPane_1_1.setViewportView(tblRegistros);
		tableUsuarios.getColumnModel().getColumn(1).setPreferredWidth(98);
		panel_1.setLayout(new CardLayout(0, 0));
		
		panel_1.add(pnlOrganizadores, "organizadores");
		panel_1.add(pnlAsistentes, "asistentes");

		panel_1.add(new Panel());
		
		
	    // Carga inicial
	    loadData();

	}
	
	public void loadData() {
		modelUsuarios.setRowCount(0);
		modelEdiciones.setRowCount(0);
		modelRegistros.setRowCount(0);
		usuariosData = usuarioController.obtenerUsuarios();
		for (var u : usuariosData) {
			if (u == null) continue;
			modelUsuarios.addRow(new Object[] {
				u.nickname(),
				u.correo(),
				u.tipoUsuario() == TipoUsuario.ORGANIZADOR ? "Organizador" : (u.tipoUsuario() == TipoUsuario.ASISTENTE ? "Asistente" : "Otro")
			});
		}
		
		if (modelUsuarios.getRowCount() > 0) {
			tableUsuarios.getSelectionModel().clearSelection();
		}
		
		pnlOrganizadores.setVisible(false);
		pnlAsistentes.setVisible(false);
	}
	
}