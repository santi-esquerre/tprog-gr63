package gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Component;
import java.awt.ComponentOrientation;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.time.LocalDate;
import java.time.Year;
import java.util.Set;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import datatypes.DTInstitucion;
import exceptions.ExistePatrocinioException;
import exceptions.UsuarioCorreoRepetidoException;
import exceptions.UsuarioNicknameRepetidoException;
import interfaces.Factory;

public class AltaUsuario extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtCorreo;
	private JTextField txtNombre;
	private JTextField txtNickname;
	private JTextField txtApellido;
	private JTextField txtLink;
	private JTextField txtDescripcion;
	private JComboBox<String> comboBoxInstitucion;
	private JComboBox<Integer> comboBoxDia;
	private JComboBox<String> comboBoxMes;
	private JComboBox<Integer> comboBoxAnio;
	private ButtonGroup seleccionRol;
	private JRadioButton rbtnAsistente;
	private JRadioButton rbtnOrganizador;
	private CardLayout cl;
	private JPanel cardPanel;
	private String[] institucion;
	private Integer[] dias;
	private String[] meses;
	private Integer[] anio;
	private Factory factory = Factory.get();

	public AltaUsuario() {
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Alta de Usuario");
		setBounds(100, 100, 440, 358);

		getContentPane().setLayout(new BorderLayout(0, 0));

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

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 500, 0 };
		gbl_panel_1.rowHeights = new int[] { 147, 119, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		panel_1.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 190, 434, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel_2 = new JLabel("Nickname:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);

		txtNickname = new JTextField();
		GridBagConstraints gbc_txtNickname = new GridBagConstraints();
		gbc_txtNickname.insets = new Insets(0, 0, 5, 0);
		gbc_txtNickname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNickname.gridx = 1;
		gbc_txtNickname.gridy = 0;
		panel.add(txtNickname, gbc_txtNickname);
		txtNickname.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		txtNombre = new JTextField();
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 1;
		panel.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);

		JLabel lblNewLabel = new JLabel("Correo electrónico:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		txtCorreo = new JTextField();
		GridBagConstraints gbc_txtCorreo = new GridBagConstraints();
		gbc_txtCorreo.insets = new Insets(0, 0, 5, 0);
		gbc_txtCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCorreo.gridx = 1;
		gbc_txtCorreo.gridy = 2;
		panel.add(txtCorreo, gbc_txtCorreo);
		txtCorreo.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Rol:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 0, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 4;
		panel.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		cardPanel = new JPanel();
		GridBagConstraints gbc_cardPanel = new GridBagConstraints();
		gbc_cardPanel.fill = GridBagConstraints.BOTH;
		gbc_cardPanel.insets = new Insets(0, 0, 5, 0);
		gbc_cardPanel.gridx = 0;
		gbc_cardPanel.gridy = 1;
		panel_1.add(cardPanel, gbc_cardPanel);
		cardPanel.setLayout(new CardLayout(0, 0));
		cl = (CardLayout) cardPanel.getLayout();

		JPanel emptyPanel = new JPanel();
		cardPanel.add(emptyPanel, "Vacio");

		rbtnAsistente = new JRadioButton("Asistente");
		rbtnAsistente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(cardPanel, "Asistente");
			}
		});
		panel_2.add(rbtnAsistente);

		rbtnOrganizador = new JRadioButton("Organizador");
		rbtnOrganizador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(cardPanel, "Organizador");
			}
		});
		panel_2.add(rbtnOrganizador);

		seleccionRol = new ButtonGroup();
		seleccionRol.add(rbtnOrganizador);
		seleccionRol.add(rbtnAsistente);

		JPanel asistentePanel = new JPanel();
		cardPanel.add(asistentePanel, "Asistente");
		asistentePanel.setBorder(null);
		GridBagLayout gbl_asistentePanel = new GridBagLayout();
		gbl_asistentePanel.columnWidths = new int[] { 190, 434, 0 };
		gbl_asistentePanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_asistentePanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_asistentePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		asistentePanel.setLayout(gbl_asistentePanel);

		JLabel lblNewLabel_4 = new JLabel("Apellido:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 0;
		asistentePanel.add(lblNewLabel_4, gbc_lblNewLabel_4);

		txtApellido = new JTextField();
		GridBagConstraints gbc_txtApellido = new GridBagConstraints();
		gbc_txtApellido.insets = new Insets(0, 0, 5, 0);
		gbc_txtApellido.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellido.gridx = 1;
		gbc_txtApellido.gridy = 0;
		asistentePanel.add(txtApellido, gbc_txtApellido);
		txtApellido.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("Institución:");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 1;
		asistentePanel.add(lblNewLabel_6, gbc_lblNewLabel_6);
		institucion = new String[] { "No corresponde" };
		comboBoxInstitucion = new JComboBox<>(institucion);
		GridBagConstraints gbc_comboBoxInstitucion = new GridBagConstraints();
		gbc_comboBoxInstitucion.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxInstitucion.fill = GridBagConstraints.BOTH;
		gbc_comboBoxInstitucion.gridx = 1;
		gbc_comboBoxInstitucion.gridy = 1;
		asistentePanel.add(comboBoxInstitucion, gbc_comboBoxInstitucion);

		JLabel lblNewLabel_5 = new JLabel("Fecha de nacimiento:");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 2;
		asistentePanel.add(lblNewLabel_5, gbc_lblNewLabel_5);

		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.gridwidth = 2;
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 3;
		asistentePanel.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		comboBoxDia = new JComboBox<>(dias);
		comboBoxDia.setBorder(new EmptyBorder(1, 1, 1, 1));
		panel_4.add(comboBoxDia);

		panel_4.add(Box.createRigidArea(new Dimension(20, 10)));
		comboBoxMes = new JComboBox<>(meses);
		panel_4.add(comboBoxMes);

		panel_4.add(Box.createRigidArea(new Dimension(20, 10)));
		comboBoxAnio = new JComboBox<>(anio);
		panel_4.add(comboBoxAnio);

		JPanel organizadorPanel = new JPanel();
		cardPanel.add(organizadorPanel, "Organizador");
		GridBagLayout gbl_organizadorPanel = new GridBagLayout();
		gbl_organizadorPanel.columnWidths = new int[] { 190, 434, 0 };
		gbl_organizadorPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_organizadorPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_organizadorPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		organizadorPanel.setLayout(gbl_organizadorPanel);

		JLabel lblNewLabel_8 = new JLabel("Descripción:");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 0;
		gbc_lblNewLabel_8.gridy = 0;
		organizadorPanel.add(lblNewLabel_8, gbc_lblNewLabel_8);

		txtDescripcion = new JTextField();
		GridBagConstraints gbc_txtDescripcion = new GridBagConstraints();
		gbc_txtDescripcion.insets = new Insets(0, 0, 5, 0);
		gbc_txtDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescripcion.gridx = 1;
		gbc_txtDescripcion.gridy = 0;
		organizadorPanel.add(txtDescripcion, gbc_txtDescripcion);
		txtDescripcion.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("Link del sitio web (opcional):");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 1;
		organizadorPanel.add(lblNewLabel_7, gbc_lblNewLabel_7);

		txtLink = new JTextField();
		GridBagConstraints gbc_txtLink = new GridBagConstraints();
		gbc_txtLink.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLink.gridx = 1;
		gbc_txtLink.gridy = 1;
		organizadorPanel.add(txtLink, gbc_txtLink);
		txtLink.setColumns(10);

		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.fill = GridBagConstraints.VERTICAL;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 2;
		panel_1.add(panel_6, gbc_panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaUsuarioActionPerformed(e);
			}
		});
		panel_6.add(btnAceptar);

		panel_6.add(Box.createRigidArea(new Dimension(20, 10)));

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				setVisible(false);
			}
		});
		panel_6.add(btnCancelar);

		GridBagConstraints gbc_textField1 = new GridBagConstraints();
		gbc_textField1.insets = new Insets(0, 0, 5, 0);
		gbc_textField1.fill = GridBagConstraints.BOTH;
		gbc_textField1.gridx = 1;
		gbc_textField1.gridy = 1;

		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				txtNickname.setText("");
				limpiarCampos();

			}
		});

	}

	protected void cargarDatos() {
		System.out.println("Cargando datos...");
		var institucion = factory.getIInstitucionController();
		Set<DTInstitucion> listaI = institucion.listarInstituciones();
		for (var i : listaI) {
			comboBoxInstitucion.addItem(i.nombre());
		}
	}

	protected void altaUsuarioActionPerformed(ActionEvent e) {
		if (checkCampos()) {
			try {
				var usuario = factory.getIUsuarioController();
				String nickname = txtNickname.getText();
				String nombre = txtNombre.getText();
				String correo = txtCorreo.getText();
				if (rbtnAsistente.isSelected()) {
					String apellido = txtApellido.getText();
					LocalDate fecha = LocalDate.of((Integer) comboBoxAnio.getSelectedItem(),
							comboBoxMes.getSelectedIndex() + 1, (Integer) comboBoxDia.getSelectedItem());
					if (comboBoxInstitucion.getSelectedIndex() == 0) {
						usuario.crearAsistente(nickname, nombre, apellido, correo, fecha);
					} else {
						String institucion = (String) comboBoxInstitucion.getSelectedItem();
						usuario.crearAsistente(nickname, nombre, apellido, correo, fecha, institucion);
					}
				} else {
					String descripcion = txtDescripcion.getText();
					if (txtLink.getText().isEmpty()) {
						usuario.crearOrganizador(nickname, nombre, correo, descripcion);
					} else {
						String linkSitioWeb = txtLink.getText();
						usuario.crearOrganizador(nickname, nombre, correo, descripcion, linkSitioWeb);
					}

				}
			} catch (UsuarioNicknameRepetidoException en) {
				util.ExceptionHandler.manageException(en);
			} catch (UsuarioCorreoRepetidoException ec) {
				util.ExceptionHandler.manageException(ec);
			} catch (exceptions.InstitucionNoExistenteException ei) {
				util.ExceptionHandler.manageException(ei);
			} catch (Exception ex) {
				util.ExceptionHandler.manageException(ex);
			}
			JOptionPane.showMessageDialog(this, "Usuario registrado correctamente", "Registro de Usuario",
					JOptionPane.INFORMATION_MESSAGE);
			limpiarCampos();
			setVisible(false);
		}

	}

	private boolean checkCampos() {
		if (!rbtnAsistente.isSelected() && !rbtnOrganizador.isSelected()) {
			JOptionPane.showMessageDialog(this, "Quedan campos obligatorios por rellenar", "Alta de Usuario",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (rbtnAsistente.isSelected() && (txtNickname.getText().isEmpty() || txtNombre.getText().isEmpty()
				|| txtCorreo.getText().isEmpty() || txtApellido.getText().isEmpty()
				|| comboBoxDia.getSelectedIndex() == -1 | comboBoxMes.getSelectedIndex() == -1
				|| comboBoxAnio.getSelectedIndex() == -1)) {
			JOptionPane.showMessageDialog(this, "Quedan campos obligatorios por rellenar", "Alta de Usuario",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (rbtnOrganizador.isSelected() && (txtNickname.getText().isEmpty() || txtNombre.getText().isEmpty()
				|| txtCorreo.getText().isEmpty() || txtDescripcion.getText().isEmpty())) {
			JOptionPane.showMessageDialog(this, "Quedan campos obligatorios por rellenar", "Alta de Usuario",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	public void limpiarCampos() {
		txtNickname.setText("");
		txtNombre.setText("");
		txtCorreo.setText("");
		txtApellido.setText("");
		txtDescripcion.setText("");
		txtLink.setText("");
		comboBoxInstitucion.setSelectedIndex(0);
		comboBoxInstitucion.removeAllItems();
		comboBoxInstitucion.addItem("No corresponde");
		comboBoxDia.setSelectedIndex(0);
		comboBoxMes.setSelectedIndex(0);
		comboBoxAnio.setSelectedIndex(0);
		seleccionRol.clearSelection();
		cl.show(cardPanel, "Vacio");
	}

}
