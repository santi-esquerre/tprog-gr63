package gui;

//import gui.*;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import interfaces.*;
import jiconfont.swing.IconFontSwing;
import util.ExceptionHandler;
import util.TestLoader;
import jiconfont.icons.font_awesome.FontAwesome;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;

import java.awt.Color;
import javax.swing.JButton;

public class Principal {

	final int TAMANIO_ICONO = 20;
	final String CODIGO_VERSION = "0.1.0";
	Factory factory = Factory.get();
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Principal window = new Principal();
				window.frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				ExceptionHandler.manageException(null, new Exception(e));
				System.exit(1);
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final Factory factory = Factory.get();
		System.out.println(factory);
		IconFontSwing.register(FontAwesome.getIconFont());

		// Íconos
		final Icon iconSistema = IconFontSwing.buildIcon(FontAwesome.COGS, TAMANIO_ICONO);
		final Icon iconUsuarios = IconFontSwing.buildIcon(FontAwesome.USERS, TAMANIO_ICONO);
		final Icon iconEventos = IconFontSwing.buildIcon(FontAwesome.CALENDAR, TAMANIO_ICONO);
		final Icon iconAlta = IconFontSwing.buildIcon(FontAwesome.FILE, TAMANIO_ICONO - 1);
		final Icon iconConsulta = IconFontSwing.buildIcon(FontAwesome.SEARCH, TAMANIO_ICONO - 1);
		final Icon iconEditar = IconFontSwing.buildIcon(FontAwesome.PENCIL, TAMANIO_ICONO - 1);
		final Icon iconEdiciones = IconFontSwing.buildIcon(FontAwesome.HASHTAG, TAMANIO_ICONO);
		final Icon iconRegistros = IconFontSwing.buildIcon(FontAwesome.TICKET, TAMANIO_ICONO);
		final Icon iconPatrocinios = IconFontSwing.buildIcon(FontAwesome.MONEY, TAMANIO_ICONO);
		final Icon iconInstituciones = IconFontSwing.buildIcon(FontAwesome.UNIVERSITY, TAMANIO_ICONO);
		final Icon iconSalir = IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, TAMANIO_ICONO);
		final Icon iconTestIcon = IconFontSwing.buildIcon(FontAwesome.FILE_TEXT_O, TAMANIO_ICONO);
		
		// Ventana
		frame = new JFrame("Eventos UY");
		frame.setIconImage(IconFontSwing.buildImage(FontAwesome.CALENDAR, 50));
		frame.setUndecorated(false);
		// Center and double size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 900;
		int height = 600;
		int x = (screenSize.width - width) / 2;
		int y = (screenSize.height - height) / 2;
		frame.setBounds(x, y, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		

		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.setBackground(new Color(240, 240, 240));
		frame.getContentPane().add(panel, BorderLayout.WEST);
		
//		JMenuBar menuBar1 = new JMenuBar();
//		menuBar.setBorderPainted(false);
//		menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
//		menuBar.setMargin(new Insets(0, 0, 0, 0));
//		menuBar.setBorder(BorderFactory.createEmptyBorder());
		//panel.add(menuBar);
		
		// Opciones del menú

		// Sistema
		JMenu menuSistema = new JMenu("Sistema");
		menuSistema.setIcon(iconSistema);
		menuSistema.getPopupMenu().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(menuSistema);
		
		JMenuItem menuItemCargarPruebas = new JMenuItem("Cargar datos de prueba");
		menuItemCargarPruebas.setIcon(iconTestIcon);
		menuItemCargarPruebas.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Cargando datos de prueba...");
					TestLoader testLoader = new TestLoader();
					try {
						testLoader.loadAll("assets/tests");
					} catch (Exception ex) {
						ExceptionHandler.manageException(frame, ex);
					}
				}
			}
		);
		menuSistema.add(menuItemCargarPruebas);
		
		JMenuItem menuItemSalir = new JMenuItem("Salir");
		menuItemSalir.setIcon(iconSalir);
		menuItemSalir.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Saliendo...");
					System.exit(0);
				}
			}
		);
		menuSistema.add(menuItemSalir);
		
		// Opciones de Usuarios
		JMenu menuUsuarios = new JMenu("Usuarios");
		menuUsuarios.setIcon(iconUsuarios);
		menuUsuarios.getPopupMenu().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(menuUsuarios);

		AltaUsuario internalFramealtaUsuario = new AltaUsuario();
		internalFramealtaUsuario.setVisible(false);
		frame.getContentPane().add(internalFramealtaUsuario);

		JMenuItem menuItemUsuariosAlta = new JMenuItem("Alta de usuario");
		menuItemUsuariosAlta.setIcon(iconAlta);
		menuItemUsuariosAlta.addActionListener( 
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Abriendo alta de usuario...");
					internalFramealtaUsuario.cargarDatos();
					internalFramealtaUsuario.setVisible(true);
					internalFramealtaUsuario.moveToFront();
					try {
						internalFramealtaUsuario.setMaximum(false);
						internalFramealtaUsuario.setSelected(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
					}
				}
 			});
		menuUsuarios.add(menuItemUsuariosAlta);

		ConsultaUsuario internalFrameconsultaUsuario = new ConsultaUsuario(factory.getIUsuarioController(), factory.getIEdicionController());
		internalFrameconsultaUsuario.setVisible(false);
		frame.getContentPane().add(internalFrameconsultaUsuario);

		JMenuItem menuItemUsuariosConsulta = new JMenuItem("Consulta de usuario");
		menuItemUsuariosConsulta.setIcon(iconConsulta);
		menuItemUsuariosConsulta.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("Abriendo consulta de usuario...");
						internalFrameconsultaUsuario.setVisible(true);
						internalFrameconsultaUsuario.moveToFront();
						try {
							internalFrameconsultaUsuario.setMaximum(false);
							internalFrameconsultaUsuario.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
						}
					}
				});
		menuUsuarios.add(menuItemUsuariosConsulta);

		JMenuItem menuItemUsuariosEditar = new JMenuItem("Editar usuario");
		menuItemUsuariosEditar.setIcon(iconEditar);
		menuUsuarios.add(menuItemUsuariosEditar);

		// Opciones de Eventos
		JMenu menuEventos = new JMenu("Eventos");
		menuEventos.setIcon(iconEventos);
		menuEventos.getPopupMenu().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(menuEventos);
		
		AltaEvento internalFramealtAltaEvento = new AltaEvento(null, factory.getIEventoController());
		internalFramealtAltaEvento.setVisible(false);
		frame.getContentPane().add(internalFramealtAltaEvento);
		JMenuItem menuItemEventosAlta = new JMenuItem("Alta de evento");
		menuItemEventosAlta.setIcon(iconAlta);
		menuEventos.add(menuItemEventosAlta);

		ConsultaEvento internalFrameconsultaEvento = new ConsultaEvento(factory.getIEventoController(),
				factory.getIEdicionController());
		internalFrameconsultaEvento.setVisible(false);
		frame.getContentPane().add(internalFrameconsultaEvento);

		menuItemEventosAlta.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						internalFramealtAltaEvento.loadForm();
						internalFramealtAltaEvento.setVisible(true);
						internalFramealtAltaEvento.moveToFront();
						try {
							internalFramealtAltaEvento.setMaximum(false);
							internalFramealtAltaEvento.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
						}
					}
				});

		JMenuItem menuItemEventosConsulta = new JMenuItem("Consulta de evento");
		menuItemEventosConsulta.setIcon(iconConsulta);
		menuEventos.add(menuItemEventosConsulta);

		menuItemEventosConsulta.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						internalFrameconsultaEvento.setVisible(true);
						internalFrameconsultaEvento.moveToFront();
						try {
							internalFrameconsultaEvento.setMaximum(false);
							internalFrameconsultaEvento.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
						}
					}
				});

		JMenuItem menuItemEventosEditar = new JMenuItem("Editar evento");
		menuItemEventosEditar.setIcon(iconEditar);
		menuEventos.add(menuItemEventosEditar);

		// Subopciones de Ediciones
		JMenu menuEdicionesBar = new JMenu("Ediciones");
		menuEdicionesBar.setIcon(iconEdiciones);
		menuEventos.add(menuEdicionesBar);

		JMenuItem menuItemEdicionesAlta = new JMenuItem("Alta de edición");
		menuItemEdicionesAlta.setIcon(iconAlta);
		menuEdicionesBar.add(menuItemEdicionesAlta);

		AltaEdicionEvento internalFrameAltaEdicion = new AltaEdicionEvento(factory.getIEventoController(),
				factory.getIUsuarioController());
		internalFrameAltaEdicion.setVisible(false);
		frame.getContentPane().add(internalFrameAltaEdicion);

		menuItemEdicionesAlta.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("Abriendo alta de edición...");
						// internalFrameAltaEdicion.loadForm();
						internalFrameAltaEdicion.setVisible(true);
						internalFrameAltaEdicion.moveToFront();
						try {
							internalFrameAltaEdicion.setMaximum(false);
							internalFrameAltaEdicion.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
						}
					}
				});

		JMenuItem menuItemEdicionesConsulta = new JMenuItem("Consulta de edición");
		menuItemEdicionesConsulta.setIcon(iconConsulta);
		menuEdicionesBar.add(menuItemEdicionesConsulta);

		// justo después de crear internalFrameconsultaEvento
		ConsultaEdicionEvento internalFrameConsultaEdicion = new ConsultaEdicionEvento(factory.getIEventoController(),
				factory.getIEdicionController());
		internalFrameConsultaEdicion.setVisible(false);
		frame.getContentPane().add(internalFrameConsultaEdicion);

		menuItemEdicionesConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				internalFrameConsultaEdicion.setVisible(true);
				try {
					internalFrameConsultaEdicion.setMaximum(false);
					internalFrameConsultaEdicion.moveToFront();
					internalFrameConsultaEdicion.setSelected(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
				}
			}
		});

		IEdicionController edicionController = factory.getIEdicionController();
		IUsuarioController usuarioController = factory.getIUsuarioController();

		RegistroEdicionEvento internalFrameRegistroEdicion = new RegistroEdicionEvento();
		internalFrameRegistroEdicion.setVisible(false);
		frame.getContentPane().add(internalFrameRegistroEdicion);

		JMenuItem menuItemEdicionesRegistro = new JMenuItem("Registro a edición");
		menuItemEdicionesRegistro.setIcon(iconRegistros);
		menuItemEdicionesRegistro.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						internalFrameRegistroEdicion.setSize(550, 513);
						System.out.println("Abriendo registro a edición...");
						internalFrameRegistroEdicion.cargarDatos();
						internalFrameRegistroEdicion.setVisible(true);
						internalFrameRegistroEdicion.moveToFront();
						try {
							internalFrameRegistroEdicion.setMaximum(false);
							internalFrameRegistroEdicion.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
						}
					}
				});
		menuEdicionesBar.add(menuItemEdicionesRegistro);

		JMenuItem menuItemEdicionesEditar = new JMenuItem("Consulta de registro");
		menuItemEdicionesEditar.setIcon(iconConsulta);
		menuEdicionesBar.add(menuItemEdicionesEditar);

		ConsultaRegistro internalFrameConsultaRegistro = new ConsultaRegistro(usuarioController);
		internalFrameConsultaRegistro.setVisible(false);
		frame.getContentPane().add(internalFrameConsultaRegistro);
		menuItemEdicionesEditar.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("Abriendo consulta de registro...");
						internalFrameConsultaRegistro.setVisible(true);
						internalFrameConsultaRegistro.moveToFront();
						try {
							internalFrameConsultaRegistro.setMaximum(false);
							internalFrameConsultaRegistro.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
						}
					}
				});

		// Subopciones de Patrocinios
		JMenu menuPatrociniosBar = new JMenu("Patrocinios");
		menuPatrociniosBar.setIcon(iconPatrocinios);
		menuEventos.add(menuPatrociniosBar);
		
		AltaPatrocinio internalFrameAltaPatrocinio = new AltaPatrocinio();
		internalFrameAltaPatrocinio.setVisible(false);
		frame.getContentPane().add(internalFrameAltaPatrocinio);
		
		JMenuItem menuItemPatrociniosAlta = new JMenuItem("Alta de patrocinio");
		menuItemPatrociniosAlta.setIcon(iconAlta);
		menuPatrociniosBar.add(menuItemPatrociniosAlta);
		menuItemPatrociniosAlta.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("Abriendo alta de patrocinio...");
						internalFrameAltaPatrocinio.cargarDatos();
						internalFrameAltaPatrocinio.setVisible(true);
						internalFrameAltaPatrocinio.moveToFront();
						try {
							internalFrameAltaPatrocinio.setMaximum(false);
							internalFrameAltaPatrocinio.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
						}
						
						
					}
				}
			);
		
		
		JMenuItem menuItemPatrociniosConsulta = new JMenuItem("Consulta de patrocinio");
		menuItemPatrociniosConsulta.setIcon(iconConsulta);
		menuPatrociniosBar.add(menuItemPatrociniosConsulta);

		// Subopciones de Tipos de registros
		JMenu menuRegistrosBar = new JMenu("Tipos de registros");
		menuRegistrosBar.setIcon(iconRegistros);
		menuEventos.add(menuRegistrosBar);

		JMenuItem menuItemRegistrosAlta = new JMenuItem("Alta de tipo de registro");
		menuItemRegistrosAlta.setIcon(iconAlta);
		menuRegistrosBar.add(menuItemRegistrosAlta);
		AltaTipoRegistro internalFramealtaConsultaTipoRegistro = new AltaTipoRegistro(factory.getIEventoController(), edicionController);
		frame.getContentPane().add(internalFramealtaConsultaTipoRegistro);
		menuItemRegistrosAlta.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						internalFramealtaConsultaTipoRegistro.setVisible(true);
						internalFramealtaConsultaTipoRegistro.moveToFront();
						try {
							internalFramealtaConsultaTipoRegistro.setMaximum(false);
							internalFramealtaConsultaTipoRegistro.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
						}
					}
				}
			);
		
		ConsultaTipoRegistro internalFrameconsultaTipoRegistro = new ConsultaTipoRegistro(factory.getIEventoController(), edicionController);
		frame.getContentPane().add(internalFrameconsultaTipoRegistro);
		JMenuItem menuItemRegistrosConsulta = new JMenuItem("Consulta de tipo de registro");
		menuItemRegistrosConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				internalFrameconsultaTipoRegistro.setVisible(true);
				internalFrameconsultaTipoRegistro.moveToFront();
				try {
					internalFrameconsultaTipoRegistro.setMaximum(false);
					internalFrameconsultaTipoRegistro.setSelected(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
				}
			}
		});
		menuItemRegistrosConsulta.setIcon(iconConsulta);
		menuRegistrosBar.add(menuItemRegistrosConsulta);

		// Opciones de Instituciones
		
		JMenu menuInstituciones = new JMenu("Instituciones");
		menuInstituciones.setIcon(iconInstituciones);
		menuBar.add(menuInstituciones);

		JMenuItem menuItemInstitucionesAlta = new JMenuItem("Alta de institución");
		menuItemInstitucionesAlta.setIcon(iconAlta);
		menuInstituciones.add(menuItemInstitucionesAlta);

		IInstitucionController institucionController = factory.getIInstitucionController();
		AltaInstitucion internalFramealtaInstitucion = new AltaInstitucion(institucionController);
		internalFramealtaInstitucion.setVisible(false);
		frame.getContentPane().add(internalFramealtaInstitucion);

		menuItemInstitucionesAlta.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("Abriendo alta de institución...");
						internalFramealtaInstitucion.setVisible(true);
						internalFramealtaInstitucion.moveToFront();
						try {
							internalFramealtaInstitucion.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
						}
					}
				}
			);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setForeground(new Color(0, 0, 0));
		desktopPane.setBackground(new Color(238, 238, 238));
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		
		

		// JLabel lblNewLabel = new JLabel("Versión " + CODIGO_VERSION + " ");
		// lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		// lblNewLabel.setBounds(271, 250, 64, 13);
		// desktopPane.add(lblNewLabel);
		// lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);

	}
	
}