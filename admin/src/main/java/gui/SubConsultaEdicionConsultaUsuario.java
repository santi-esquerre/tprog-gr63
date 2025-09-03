package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Comparator;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import datatypes.DTEdicionDetallada;
import datatypes.DTEvento;
import datatypes.DTPatrocinio;
import datatypes.DTRegistro;
import datatypes.DTTipoRegistro;
import exceptions.ValidationInputException;
import interfaces.IEdicionController;

public class SubConsultaEdicionConsultaUsuario extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
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
	private JButton btnVerTipo;
	private DefaultTableModel registrosModel;
	private JTable tblRegistros;
	private DefaultTableModel patrociniosModel;
	private JTable tblPatrocinios;
	private JButton btnVerPatro;
	private JTextField txtBuscarEvento;
	private JButton btnBuscar;
	private DefaultTableModel eventosModel;
	private DTEdicionDetallada edicionDetActual;
	private DTEvento[] allEventos;
	private JTable tblEdiciones;
	private String nombreEventoSeleccionado;
	private JTable edicionesModel;
	private IEdicionController edicionController;


	/**
	 * Create the dialog.
	 */
	public SubConsultaEdicionConsultaUsuario(IEdicionController edicionController, String nombreEdicion) {
		this.setModal(true);
		
		this.setBounds(100, 100, 800, 600);
		this.setTitle("Consulta de edición de usuario");
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		lblNomEd = new JLabel("-");
	    lblSiglaEd = new JLabel("-");
	    lblFIni = new JLabel("-");
	    lblFFin = new JLabel("-");
	    lblCiudad = new JLabel("-");
	    lblPais = new JLabel("-");
	    lblOrgNick = new JLabel("-");
	    lblOrgNombre = new JLabel("-");
	    lblOrgCorreo = new JLabel("-");
	    tblTipos = new JTable();
	    btnVerTipo = new JButton("Ver...");
	    tblRegistros = new JTable();
	    tblPatrocinios = new JTable();
	    btnVerPatro = new JButton("Ver...");
	    txtBuscarEvento = new JTextField(20);
	    btnBuscar = new JButton("Filtrar");
	    eventosModel = new DefaultTableModel(new Object[]{"Nombre", "Sigla", "Descripción", "Fecha alta"}, 0) {
	      @Override public boolean isCellEditable(int r, int c) { return false; }
	    };
	    tblEdiciones = new JTable();
	    edicionesModel = new JTable();
		this.edicionController = edicionController;
		// ===== Panel DERECHO: detalle de edición =====
	    panel = new JPanel(new BorderLayout(8, 8));
	    panel.setBorder(new EmptyBorder(8, 4, 8, 8));
	    this.add(panel);

	    JPanel hdr = new JPanel(new GridBagLayout());
	    hdr.setBorder(new TitledBorder("Datos de la edición"));
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(2, 6, 2, 6);
	    gbc.anchor = GridBagConstraints.WEST;

	    int y = 0;
	    addLbl(hdr, gbc, 0, y, "Nombre:");    hdrAdd(hdr, gbc, lblNomEd, 1, y++);
	    addLbl(hdr, gbc, 0, y, "Sigla:");     hdrAdd(hdr, gbc, lblSiglaEd, 1, y++);
	    addLbl(hdr, gbc, 0, y, "Inicio:");    hdrAdd(hdr, gbc, lblFIni, 1, y++);
	    addLbl(hdr, gbc, 0, y, "Fin:");       hdrAdd(hdr, gbc, lblFFin, 1, y++);
	    addLbl(hdr, gbc, 0, y, "Ciudad:");    hdrAdd(hdr, gbc, lblCiudad, 1, y++);
	    addLbl(hdr, gbc, 0, y, "País:");      hdrAdd(hdr, gbc, lblPais, 1, y++);

	    addLbl(hdr, gbc, 2, 0, "Organizador (nick):"); hdrAdd(hdr, gbc, lblOrgNick, 3, 0);
	    addLbl(hdr, gbc, 2, 1, "Nombre:");             hdrAdd(hdr, gbc, lblOrgNombre, 3, 1);
	    addLbl(hdr, gbc, 2, 2, "Correo:");             hdrAdd(hdr, gbc, lblOrgCorreo, 3, 2);

	    panel.add(hdr, BorderLayout.NORTH);

	    // Tabs
	    JTabbedPane tabs = new JTabbedPane();

	    // TAB TIPOS
	    JPanel tabTipos = new JPanel(new BorderLayout(4, 4));
	    tiposModel = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Costo", "Cupo"}, 0) {
	      @Override public boolean isCellEditable(int r, int c) { return false; }
	    };
	    tblTipos.setModel(tiposModel);
	    tblTipos.setAutoCreateRowSorter(true);
	    tabTipos.add(new JScrollPane(tblTipos), BorderLayout.CENTER);
	    JPanel pnlTiposBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    btnVerTipo.setEnabled(false);
	    pnlTiposBtns.add(btnVerTipo);
	    tabTipos.add(pnlTiposBtns, BorderLayout.SOUTH);
	    tabs.addTab("Tipos de registro", tabTipos);

	    // TAB REGISTROS
	    JPanel tabReg = new JPanel(new BorderLayout(4, 4));
	    registrosModel = new DefaultTableModel(new Object[]{"Fecha", "Costo", "Asistente", "Tipo registro"}, 0) {
	      @Override public boolean isCellEditable(int r, int c) { return false; }
	    };
	    tblRegistros.setModel(registrosModel);
	    tblRegistros.setAutoCreateRowSorter(true);
	    tabReg.add(new JScrollPane(tblRegistros), BorderLayout.CENTER);
	    tabs.addTab("Registros", tabReg);

	    // TAB PATROCINIOS
	    JPanel tabPatro = new JPanel(new BorderLayout(4, 4));
	    patrociniosModel = new DefaultTableModel(new Object[]{"Patrocinador", "Nivel", "Monto"}, 0) {
	      @Override public boolean isCellEditable(int r, int c) { return false; }
	    };
	    tblPatrocinios.setModel(patrociniosModel);
	    tblPatrocinios.setAutoCreateRowSorter(true);
	    tabPatro.add(new JScrollPane(tblPatrocinios), BorderLayout.CENTER);
	    JPanel pnlPatroBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    btnVerPatro.setEnabled(false);
	    pnlPatroBtns.add(btnVerPatro);
	    tabPatro.add(pnlPatroBtns, BorderLayout.SOUTH);
	    tabs.addTab("Patrocinios", tabPatro);

	    panel.add(tabs, BorderLayout.CENTER);

	    // ==== Handlers ====
	    // Habilitar botones de “ver…” cuando haya selección
	    tblTipos.getSelectionModel().addListSelectionListener(e -> {
	      if (!e.getValueIsAdjusting()) btnVerTipo.setEnabled(tblTipos.getSelectedRow() >= 0 && edicionDetActual != null);
	    });
	    tblPatrocinios.getSelectionModel().addListSelectionListener(e -> {
	      if (!e.getValueIsAdjusting()) btnVerPatro.setEnabled(tblPatrocinios.getSelectedRow() >= 0 && edicionDetActual != null);
	    });

	    // Filtro incremental + botón "Filtrar"
	    DocumentListener doc = new DocumentListener() {
	      @Override public void insertUpdate(DocumentEvent e) { filterEventos(); }
	      @Override public void removeUpdate(DocumentEvent e) { filterEventos(); }
	      @Override public void changedUpdate(DocumentEvent e) { filterEventos(); }
	    };
	    txtBuscarEvento.getDocument().addDocumentListener(doc);
	    btnBuscar.addActionListener(e -> filterEventos());

	    // Botón ver tipo
	    btnVerTipo.addActionListener(e -> {
	      if (edicionDetActual == null) return;
	      int sel = tblTipos.getSelectedRow();
	      if (sel < 0) return;
	      int mi = tblTipos.convertRowIndexToModel(sel);
	      String nombreTipo = (String) tiposModel.getValueAt(mi, 0);
	      edicionDetActual.tiposRegistro().stream()
	          .filter(t -> t.nombre().equals(nombreTipo))
	          .findFirst()
	          .ifPresent(this::showTipoDialog);
	    });

	    // Botón ver patrocinio
	    btnVerPatro.addActionListener(e -> {
	      if (edicionDetActual == null) return;
	      int sel = tblPatrocinios.getSelectedRow();
	      if (sel < 0) return;
	      int mi = tblPatrocinios.convertRowIndexToModel(sel);
	      String nombreInst = (String) patrociniosModel.getValueAt(mi, 0);
	      edicionDetActual.patrocinios().stream()
	          .filter(p -> p.institucion() != null && p.institucion().nombre().equals(nombreInst))
	          .findFirst()
	          .ifPresent(this::showPatroDialog);
	    });
	    
	    cargarDetalle(nombreEdicion);


	}
	
	private void filterEventos() {
	    String q = txtBuscarEvento.getText().trim().toLowerCase();
	    eventosModel.setRowCount(0);
	    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    for (DTEvento ev : allEventos) {
	      if (q.isEmpty() || ev.nombre().toLowerCase().contains(q)) {
	        eventosModel.addRow(new Object[]{ev.nombre(), ev.sigla(), ev.descripcion(), df.format(ev.fechaAlta())});
	      }
	    }
	    // Al cambiar el listado, limpiar ediciones/detalle
	    //clearEdicionesPanel();
	    clearDetallePanel();
	  }
	
	  // ==== Selección de edición -> cargar detalle ====
	  private void cargarDetalle(String nombreEdicion) {
	    clearDetallePanel();
	    try {
	      edicionDetActual = edicionController.obtenerDatosDetalladosEdicion(nombreEdicion);
	     
	      if (edicionDetActual == null) {
	        clearDetallePanel();
	        return;
	      }
	      populateDetalle(edicionDetActual);
	      setDetalleEnabled(true);
	    } catch (ValidationInputException ex) {
	      clearDetallePanel();
	      System.err.println("Error de validación al obtener detalles de la edición: " + ex.getMessage());
	    }
	  }

	  private void populateDetalle(DTEdicionDetallada det) {
	    SimpleDateFormat dfd = new SimpleDateFormat("dd/MM/yyyy");

	    // Labels
	    lblNomEd.setText(det.edicion().nombre());
	    lblSiglaEd.setText(det.edicion().sigla());
	    lblFIni.setText(dfd.format(det.edicion().fechaInicio()));
	    lblFFin.setText(dfd.format(det.edicion().fechaFin()));
	    lblCiudad.setText(det.edicion().ciudad());
	    lblPais.setText(det.edicion().pais());
	    lblOrgNick.setText(det.organizador().nickname());
	    lblOrgNombre.setText(det.organizador().nombre());
	    lblOrgCorreo.setText(det.organizador().correo());

	    // Tipos
	    tiposModel.setRowCount(0);
	    det.tiposRegistro().stream()
	        .sorted(Comparator.comparing(DTTipoRegistro::nombre))
	        .forEach(tr -> tiposModel.addRow(new Object[]{tr.nombre(), tr.descripcion(), tr.costo(), tr.cupo()}));
	    btnVerTipo.setEnabled(false);

	    // Registros
	    registrosModel.setRowCount(0);
	    SimpleDateFormat dft = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    det.registros().stream()
	        .sorted(Comparator.<DTRegistro, Long>comparing(r -> r.fecha().getTime()).reversed())
	        .forEach(r -> registrosModel.addRow(new Object[]{
	            dft.format(r.fecha()), String.format("%.2f", r.costo()),
	            r.nicknameAsistente(), r.tipoRegistro().nombre()
	        }));

	    // Patrocinios
	    patrociniosModel.setRowCount(0);
	    det.patrocinios().stream()
	        .sorted(Comparator.comparing((DTPatrocinio p) -> p.institucion() != null ? p.institucion().nombre() : ""))
	        .forEach(p -> patrociniosModel.addRow(new Object[]{
	            p.institucion() != null ? p.institucion().nombre() : "(s/i)",
	            p.nivel(), String.format("%.2f", p.monto())
	        }));
	    btnVerPatro.setEnabled(false);
	  }

	  private void clearDetallePanel() {
	    lblNomEd.setText("-");
	    lblSiglaEd.setText("-");
	    lblFIni.setText("-");
	    lblFFin.setText("-");
	    lblCiudad.setText("-");
	    lblPais.setText("-");
	    lblOrgNick.setText("-");
	    lblOrgNombre.setText("-");
	    lblOrgCorreo.setText("-");
	    tiposModel.setRowCount(0);
	    registrosModel.setRowCount(0);
	    patrociniosModel.setRowCount(0);
	    edicionDetActual = null;
	    setDetalleEnabled(false);
	    btnVerTipo.setEnabled(false);
	    btnVerPatro.setEnabled(false);
	  }

	  private void setDetalleEnabled(boolean enabled) {
	    panel.setEnabled(enabled);
	    lblNomEd.setEnabled(enabled);
	    lblSiglaEd.setEnabled(enabled);
	    lblFIni.setEnabled(enabled);
	    lblFFin.setEnabled(enabled);
	    lblCiudad.setEnabled(enabled);
	    lblPais.setEnabled(enabled);
	    lblOrgNick.setEnabled(enabled);
	    lblOrgNombre.setEnabled(enabled);
	    lblOrgCorreo.setEnabled(enabled);
	    tblTipos.setEnabled(enabled);
	    tblRegistros.setEnabled(enabled);
	    tblPatrocinios.setEnabled(enabled);
	  }
	
	 private static void addLbl(JPanel p, GridBagConstraints gbc, int x, int y, String text) {
		 gbc.gridx = x; gbc.gridy = y;
		    p.add(new JLabel(text), gbc);
		  }
	 private static void hdrAdd(JPanel p, GridBagConstraints gbccnst, JLabel comp, int x, int y) {
		 gbccnst.gridx = x; gbccnst.gridy = y;
		p.add(comp, gbccnst);
		}
		  
		  private void showTipoDialog(DTTipoRegistro dt) {
			    JDialog d = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), "Tipo de Registro", true);
			    d.getContentPane().setLayout(new GridBagLayout());
			    GridBagConstraints g = new GridBagConstraints();
			    g.insets = new Insets(6, 8, 6, 8);
			    g.anchor = GridBagConstraints.WEST;

			    int y = 0;
			    addDlgRow(d, g, 0, y++, "Nombre:", dt.nombre());
			    addDlgRow(d, g, 0, y++, "Descripción:", dt.descripcion());
			    addDlgRow(d, g, 0, y++, "Costo:", String.format("%.2f", dt.costo()));
			    addDlgRow(d, g, 0, y++, "Cupo:", Integer.toString(dt.cupo()));

			    d.pack();
			    d.setLocationRelativeTo(this);
			    d.setVisible(true);
			  }

			  private void showPatroDialog(DTPatrocinio p) {
			    JDialog d = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), "Patrocinio", true);
			    d.getContentPane().setLayout(new GridBagLayout());
			    GridBagConstraints g = new GridBagConstraints();
			    g.insets = new Insets(6, 8, 6, 8);
			    g.anchor = GridBagConstraints.WEST;

			    int y = 0;
			    addDlgRow(d, g, 0, y++, "Institución:", p.institucion() != null ? p.institucion().nombre() : "(s/i)");
			    addDlgRow(d, g, 0, y++, "Nivel:", String.valueOf(p.nivel()));
			    addDlgRow(d, g, 0, y++, "Monto:", String.format("%.2f", p.monto()));
			    addDlgRow(d, g, 0, y++, "Código:", Integer.toString(p.codigo()));
			    d.pack();
			    d.setLocationRelativeTo(this);
			    d.setVisible(true);
			  }
			  
			  private static void addDlgRow(JDialog d, GridBagConstraints g, int x, int y, String k, String v) {
				    g.gridx = x; g.gridy = y; d.getContentPane().add(new JLabel(k), g);
				    g.gridx = x+1; d.getContentPane().add(new JLabel(v), g);
				  }

}
