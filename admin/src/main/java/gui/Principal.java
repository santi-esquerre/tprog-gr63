package gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.io.Console;
import java.security.PublicKey;

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
import javax.swing.SwingConstants;

import interfaces.*;
import jiconfont.swing.IconFontSwing;
import util.ExceptionHandler;
import jiconfont.icons.font_awesome.FontAwesome;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JDesktopPane;
import java.awt.Color;



public class Principal {
	
	final int TAMANIO_ICONO = 20;
	final String CODIGO_VERSION = "0.1.0";

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
		
		// Ventana
		frame = new JFrame("Eventos UY");
		frame.setIconImage(IconFontSwing.buildImage(FontAwesome.CALENDAR, 50));
		// Center and double size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 900;
		int height = 600;
		int x = (screenSize.width - width) / 2;
		int y = (screenSize.height - height) / 2;
		frame.setBounds(x, y, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.setBackground(new Color(240, 240, 240));
		frame.getContentPane().add(panel, BorderLayout.WEST);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
		menuBar.setMargin(new Insets(0, 0, 0, 0));
		menuBar.setBorder(BorderFactory.createEmptyBorder());
		panel.add(menuBar);
		
		// Opciones del menú
		
		// Sistema
		JMenu menuSistema = new HorizontalMenu("Sistema");
		menuSistema.setIcon(iconSistema);
		menuSistema.getPopupMenu().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(menuSistema);
		
		// Opciones de Usuarios
		JMenu menuUsuarios = new HorizontalMenu("Usuarios");
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
					internalFramealtaUsuario.setVisible(true);
				}
 			});
		menuUsuarios.add(menuItemUsuariosAlta);

		ConsultaUsuario internalFrameconsultaUsuario = new ConsultaUsuario();
		internalFrameconsultaUsuario.setVisible(false);
		frame.getContentPane().add(internalFrameconsultaUsuario);
		
		JMenuItem menuItemUsuariosConsulta = new JMenuItem("Consulta de usuario");
		menuItemUsuariosConsulta.setIcon(iconConsulta);
		menuItemUsuariosConsulta.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Abriendo consulta de usuario...");
					internalFrameconsultaUsuario.setVisible(true);
				}
			}
		);
		menuUsuarios.add(menuItemUsuariosConsulta);
		

		JMenuItem menuItemUsuariosEditar = new JMenuItem("Editar usuario");
		menuItemUsuariosEditar.setIcon(iconEditar);
		menuUsuarios.add(menuItemUsuariosEditar);
		
		// Opciones de Eventos
		JMenu menuEventos = new HorizontalMenu("Eventos");
		menuEventos.setIcon(iconEventos);
		menuEventos.getPopupMenu().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(menuEventos);
		
		AltaEvento internalFramealtAltaEvento = new AltaEvento(factory.getIEventoController());
		internalFramealtAltaEvento.setVisible(false);
		frame.getContentPane().add(internalFramealtAltaEvento);
		JMenuItem menuItemEventosAlta = new JMenuItem("Alta de evento");
		menuItemEventosAlta.setIcon(iconAlta);
		menuEventos.add(menuItemEventosAlta);

		ConsultaEvento internalFrameconsultaEvento = new ConsultaEvento();
		internalFrameconsultaEvento.setVisible(false);
		frame.getContentPane().add(internalFrameconsultaEvento);

		
		menuItemEventosAlta.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						internalFramealtAltaEvento.loadForm();
						internalFramealtAltaEvento.setVisible(true);
					}
				}
			);
		
		JMenuItem menuItemEventosConsulta = new JMenuItem("Consulta de evento");
		menuItemEventosConsulta.setIcon(iconConsulta);
		menuEventos.add(menuItemEventosConsulta);

		menuItemEventosConsulta.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						internalFrameconsultaEvento.setVisible(true);
					}
				}
			);

		JMenuItem menuItemEventosEditar = new JMenuItem("Editar evento");
		menuItemEventosEditar.setIcon(iconEditar);
		menuEventos.add(menuItemEventosEditar);
		
		// Subopciones de Ediciones
		JMenu menuEdicionesBar = new HorizontalMenu("Ediciones");
		menuEdicionesBar.setIcon(iconEdiciones);
		menuEventos.add(menuEdicionesBar);
		
		JMenuItem menuItemEdicionesAlta = new JMenuItem("Alta de edición");
		menuItemEdicionesAlta.setIcon(iconAlta);
		menuEdicionesBar.add(menuItemEdicionesAlta);
		
		JMenuItem menuItemEdicionesConsulta = new JMenuItem("Consulta de edición");
		menuItemEdicionesConsulta.setIcon(iconConsulta);
		menuEdicionesBar.add(menuItemEdicionesConsulta);
		
		RegistroEdicionEvento internalFrameRegistroEdicion = new RegistroEdicionEvento();
		internalFrameRegistroEdicion.setVisible(false);
		frame.getContentPane().add(internalFrameRegistroEdicion);
		
		JMenuItem menuItemEdicionesRegistro = new JMenuItem("Registro a edición");
		menuItemEdicionesRegistro.setIcon(iconRegistros);
		menuItemEdicionesRegistro.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("Abriendo registro a edición...");
						internalFrameRegistroEdicion.setVisible(true);
					}
				}
			);
		menuEdicionesBar.add(menuItemEdicionesRegistro);
		
		JMenuItem menuItemEdicionesEditar = new JMenuItem("Consulta de registro");
		menuItemEdicionesEditar.setIcon(iconConsulta);
		menuEdicionesBar.add(menuItemEdicionesEditar);
		
		// Subopciones de Patrocinios
		JMenu menuPatrociniosBar = new HorizontalMenu("Patrocinios");
		menuPatrociniosBar.setIcon(iconPatrocinios);
		menuEventos.add(menuPatrociniosBar);
		
		JMenuItem menuItemPatrociniosAlta = new JMenuItem("Alta de patrocinio");
		menuItemPatrociniosAlta.setIcon(iconAlta);
		menuPatrociniosBar.add(menuItemPatrociniosAlta);
		
		JMenuItem menuItemPatrociniosConsulta = new JMenuItem("Consulta de patrocinio");
		menuItemPatrociniosConsulta.setIcon(iconConsulta);
		menuPatrociniosBar.add(menuItemPatrociniosConsulta);
		
		// Subopciones de Tipos de registros
		JMenu menuRegistrosBar = new HorizontalMenu("Tipos de registros");
		menuRegistrosBar.setIcon(iconRegistros);
		menuEventos.add(menuRegistrosBar);
		
		JMenuItem menuItemRegistrosAlta = new JMenuItem("Alta de tipo de registro");
		menuItemRegistrosAlta.setIcon(iconAlta);
		menuRegistrosBar.add(menuItemRegistrosAlta);
		
		JMenuItem menuItemRegistrosConsulta = new JMenuItem("Consulta de tipo de registro");
		menuItemRegistrosConsulta.setIcon(iconConsulta);
		menuRegistrosBar.add(menuItemRegistrosConsulta);
		
		// Opciones de Instituciones
		
		JMenu menuInstituciones = new HorizontalMenu("Instituciones");
		menuInstituciones.setIcon(iconInstituciones);
		menuBar.add(menuInstituciones);
		
		JMenuItem menuItemInstitucionesAlta = new JMenuItem("Alta de institución");
		menuItemInstitucionesAlta.setIcon(iconAlta);
		menuInstituciones.add(menuItemInstitucionesAlta);
		
		AltaInstitucion internalFramealtaInstitucion = new AltaInstitucion();
		internalFramealtaInstitucion.setVisible(false);
		frame.getContentPane().add(internalFramealtaInstitucion);
		
		menuItemInstitucionesAlta.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("Abriendo alta de institución...");
						internalFramealtaInstitucion.setVisible(true);
					}
				}
			);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setForeground(new Color(0, 0, 0));
		desktopPane.setBackground(new Color(238, 238, 238));
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		
		
//		JLabel lblNewLabel = new JLabel("Versión " + CODIGO_VERSION + " ");
//		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//		lblNewLabel.setBounds(271, 250, 64, 13);
//		desktopPane.add(lblNewLabel);
//		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);

	}
	
	class HorizontalMenu extends JMenu {
        HorizontalMenu(String label) {
            super(label);
            JPopupMenu pm = getPopupMenu();
            pm.setLayout(new BoxLayout(pm, BoxLayout.Y_AXIS));
        }
 
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }
 
        public Dimension getMaximumSize() {
            return getPreferredSize();
        }
 
        public void setPopupMenuVisible(boolean b) {
            boolean isVisible = isPopupMenuVisible();
            if (b != isVisible) {
                if ((b==true) && isShowing()) {
                    //Set location of popupMenu (pulldown or pullright).
                    //Perhaps this should be dictated by L&F.
                    int x = 0;
                    int y = 0;
                    Container parent = getParent();
                    if (parent instanceof JPopupMenu) {
                        x = getWidth() + 1;
                        y = getHeight() - getSize().height;
                    } else {
                        x = getWidth();
                        y = 0;
                    }
                    getPopupMenu().show(this, x, y);
                } else {
                    getPopupMenu().setVisible(false);
                }
            }
        }
    }

}