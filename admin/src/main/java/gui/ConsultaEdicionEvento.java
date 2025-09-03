// admin/src/main/java/gui/ConsultaEdicionEvento.java
package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
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

import datatypes.DTEdicion;
import datatypes.DTEdicionDetallada;
import datatypes.DTEvento;
import datatypes.DTEventoDetallado;
import datatypes.DTPatrocinio;
import datatypes.DTRegistro;
import datatypes.DTTipoRegistro;
import exceptions.ValidationInputException;
import interfaces.IEdicionController;
import interfaces.IEventoController;

/**
 * Consulta de Edición de Evento
 * - Carga inicial: listarEventos() -> tblEventos
 * - Filtro incremental por nombre (txtBuscarEvento)
 * - Selección de evento -> obtenerDatosDetalladosEvento() -> tblEdiciones
 * - Selección de edición -> obtenerDatosDetalladosEdicion() -> pnlDetalle +
 * tabs
 * - Botones Ver... abren JDialog con el DT correspondiente
 */
public class ConsultaEdicionEvento extends JInternalFrame {

  // ==== Controllers ====
  private final IEventoController eventoController;
  private final IEdicionController edicionController;

  // ==== IZQUIERDA: EVENTOS ====
  private final JTextField txtBuscarEvento = new JTextField(16);
  private final JTable tblEventos = new JTable();
  private DefaultTableModel eventosModel;

  // ==== CENTRO: EDICIONES ====
  private final JTable tblEdiciones = new JTable();
  private DefaultTableModel edicionesModel;
  private JPanel pnlEdiciones;

  // ==== DERECHA: DETALLE EDICIÓN ====
  private final JLabel lblNomEd = new JLabel("-");
  private final JLabel lblSiglaEd = new JLabel("-");
  private final JLabel lblFIni = new JLabel("-");
  private final JLabel lblFFin = new JLabel("-");
  private final JLabel lblCiudad = new JLabel("-");
  private final JLabel lblPais = new JLabel("-");

  private final JLabel lblOrgNick = new JLabel("-");
  private final JLabel lblOrgNombre = new JLabel("-");
  private final JLabel lblOrgCorreo = new JLabel("-");

  private final JTable tblTipos = new JTable();
  private final JTable tblRegistros = new JTable();
  private final JTable tblPatrocinios = new JTable();

  private DefaultTableModel tiposModel;
  private DefaultTableModel registrosModel;
  private DefaultTableModel patrociniosModel;

  private final JButton btnVerTipo = new JButton("Ver Tipo de Registro");
  private final JButton btnVerPatro = new JButton("Ver Patrocinio");
  private JPanel pnlDetalle;

  // ==== Cache / estado ====
  private List<DTEvento> allEventos = new ArrayList<>();
  private DTEdicionDetallada edicionDetActual = null;
  private String nombreEventoSeleccionado = null;

  public ConsultaEdicionEvento(IEventoController eventoController, IEdicionController edicionController) {
    super("Consulta de Edición de Evento", true, true, true, true);
    this.eventoController = eventoController;
    this.edicionController = edicionController;

    setDefaultCloseOperation(HIDE_ON_CLOSE);
    setSize(1100, 640);
    getContentPane().setLayout(new BorderLayout());

    // ===== layout principal =====
    JSplitPane spEventosEdiciones = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    spEventosEdiciones.setResizeWeight(0.3);
    JSplitPane spCentroDerecha = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    spCentroDerecha.setResizeWeight(0.45);
    getContentPane().add(spEventosEdiciones, BorderLayout.CENTER);

    // ===== panel IZQ: eventos =====
    JPanel pnlEventos = new JPanel(new BorderLayout(8, 8));
    pnlEventos.setBorder(new EmptyBorder(8, 8, 8, 4));
    JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
    pnlSearch.add(new JLabel("Buscar:"));
    pnlSearch.add(txtBuscarEvento);
    JButton btnBuscar = new JButton("Filtrar");
    pnlSearch.add(btnBuscar);
    pnlEventos.add(pnlSearch, BorderLayout.NORTH);

    eventosModel = new DefaultTableModel(new Object[] { "Nombre", "Sigla", "Descripción", "Fecha alta" }, 0) {
      @Override
      public boolean isCellEditable(int r, int c) {
        return false;
      }
    };
    tblEventos.setModel(eventosModel);
    tblEventos.setAutoCreateRowSorter(true);
    pnlEventos.add(new JScrollPane(tblEventos), BorderLayout.CENTER);

    spEventosEdiciones.setLeftComponent(pnlEventos);
    spEventosEdiciones.setRightComponent(spCentroDerecha);

    // ===== panel CENTRO: ediciones =====
    pnlEdiciones = new JPanel(new BorderLayout(8, 8));
    pnlEdiciones.setBorder(new EmptyBorder(8, 4, 8, 4));
    pnlEdiciones.add(new JLabel("Ediciones del evento seleccionado"), BorderLayout.NORTH);

    edicionesModel = new DefaultTableModel(new Object[] { "Nombre", "Sigla", "Ciudad", "País", "Inicio", "Fin" }, 0) {
      @Override
      public boolean isCellEditable(int r, int c) {
        return false;
      }
    };
    tblEdiciones.setModel(edicionesModel);
    tblEdiciones.setAutoCreateRowSorter(true);
    pnlEdiciones.add(new JScrollPane(tblEdiciones), BorderLayout.CENTER);

    spCentroDerecha.setLeftComponent(pnlEdiciones);

    // ===== panel DERECHA: detalle edición =====
    pnlDetalle = new JPanel(new BorderLayout(8, 8));
    pnlDetalle.setBorder(new EmptyBorder(8, 4, 8, 8));

    JPanel hdr = new JPanel(new GridBagLayout());
    hdr.setBorder(new TitledBorder("Datos de la edición"));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(2, 6, 2, 6);
    gbc.anchor = GridBagConstraints.WEST;

    int y = 0;
    addLbl(hdr, gbc, 0, y, "Nombre:");
    hdrAdd(hdr, gbc, lblNomEd, 1, y++);
    addLbl(hdr, gbc, 0, y, "Sigla:");
    hdrAdd(hdr, gbc, lblSiglaEd, 1, y++);
    addLbl(hdr, gbc, 0, y, "Inicio:");
    hdrAdd(hdr, gbc, lblFIni, 1, y++);
    addLbl(hdr, gbc, 0, y, "Fin:");
    hdrAdd(hdr, gbc, lblFFin, 1, y++);
    addLbl(hdr, gbc, 0, y, "Ciudad:");
    hdrAdd(hdr, gbc, lblCiudad, 1, y++);
    addLbl(hdr, gbc, 0, y, "País:");
    hdrAdd(hdr, gbc, lblPais, 1, y++);

    addLbl(hdr, gbc, 2, 0, "Organizador (nick):");
    hdrAdd(hdr, gbc, lblOrgNick, 3, 0);
    addLbl(hdr, gbc, 2, 1, "Nombre:");
    hdrAdd(hdr, gbc, lblOrgNombre, 3, 1);
    addLbl(hdr, gbc, 2, 2, "Correo:");
    hdrAdd(hdr, gbc, lblOrgCorreo, 3, 2);

    pnlDetalle.add(hdr, BorderLayout.NORTH);

    // Tabs
    JTabbedPane tabs = new JTabbedPane();

    // TAB TIPOS
    JPanel tabTipos = new JPanel(new BorderLayout(4, 4));
    tiposModel = new DefaultTableModel(new Object[] { "Nombre", "Descripción", "Costo", "Cupo" }, 0) {
      @Override
      public boolean isCellEditable(int r, int c) {
        return false;
      }
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
    registrosModel = new DefaultTableModel(new Object[] { "Fecha", "Costo", "Asistente", "Tipo registro" }, 0) {
      @Override
      public boolean isCellEditable(int r, int c) {
        return false;
      }
    };
    tblRegistros.setModel(registrosModel);
    tblRegistros.setAutoCreateRowSorter(true);
    tabReg.add(new JScrollPane(tblRegistros), BorderLayout.CENTER);
    tabs.addTab("Registros", tabReg);

    // TAB PATROCINIOS
    JPanel tabPatro = new JPanel(new BorderLayout(4, 4));
    patrociniosModel = new DefaultTableModel(new Object[] { "Patrocinador", "Nivel", "Monto" }, 0) {
      @Override
      public boolean isCellEditable(int r, int c) {
        return false;
      }
    };
    tblPatrocinios.setModel(patrociniosModel);
    tblPatrocinios.setAutoCreateRowSorter(true);
    tabPatro.add(new JScrollPane(tblPatrocinios), BorderLayout.CENTER);
    JPanel pnlPatroBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnVerPatro.setEnabled(false);
    pnlPatroBtns.add(btnVerPatro);
    tabPatro.add(pnlPatroBtns, BorderLayout.SOUTH);
    tabs.addTab("Patrocinios", tabPatro);

    pnlDetalle.add(tabs, BorderLayout.CENTER);
    spCentroDerecha.setRightComponent(pnlDetalle);

    // ==== Handlers ====
    // Habilitar botones de “ver…” cuando haya selección
    tblTipos.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting())
        btnVerTipo.setEnabled(tblTipos.getSelectedRow() >= 0 && edicionDetActual != null);
    });
    tblPatrocinios.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting())
        btnVerPatro.setEnabled(tblPatrocinios.getSelectedRow() >= 0 && edicionDetActual != null);
    });

    // Filtro incremental + botón "Filtrar"
    DocumentListener doc = new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        filterEventos();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        filterEventos();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        filterEventos();
      }
    };
    txtBuscarEvento.getDocument().addDocumentListener(doc);
    btnBuscar.addActionListener(e -> filterEventos());

    // Selección de evento
    tblEventos.getSelectionModel().addListSelectionListener(e -> {
      if (e.getValueIsAdjusting())
        return;
      onEventoSelectionChanged();
    });

    // Selección de edición
    tblEdiciones.getSelectionModel().addListSelectionListener(e -> {
      if (e.getValueIsAdjusting())
        return;
      onEdicionSelectionChanged();
    });

    // Botón ver tipo
    btnVerTipo.addActionListener(e -> {
      if (edicionDetActual == null)
        return;
      int sel = tblTipos.getSelectedRow();
      if (sel < 0)
        return;
      int mi = tblTipos.convertRowIndexToModel(sel);
      String nombreTipo = (String) tiposModel.getValueAt(mi, 0);
      edicionDetActual.tiposRegistro().stream()
          .filter(t -> t.nombre().equals(nombreTipo))
          .findFirst()
          .ifPresent(this::showTipoDialog);
    });

    // Botón ver patrocinio
    btnVerPatro.addActionListener(e -> {
      if (edicionDetActual == null)
        return;
      int sel = tblPatrocinios.getSelectedRow();
      if (sel < 0)
        return;
      int mi = tblPatrocinios.convertRowIndexToModel(sel);
      String nombreInst = (String) patrociniosModel.getValueAt(mi, 0);
      edicionDetActual.patrocinios().stream()
          .filter(p -> p.institucion() != null && p.institucion().nombre().equals(nombreInst))
          .findFirst()
          .ifPresent(this::showPatroDialog);
    });

    // Estados iniciales
    setEdicionesEnabled(false);
    setDetalleEnabled(false);

    // Carga inicial
    loadEventos();
  }

  public void loadForm() {
    System.out.println("Cargando datos...");
    // reset
    // txtBuscarEvento.setText("");
    // tblEventos.clearSelection();
    // clearEdicionesPanel();
    // clearDetallePanel();
    // recargar
    loadEventos();
  }

  // ==== Helpers de layout del header ====
  private static void addLbl(JPanel p, GridBagConstraints gbc, int x, int y, String text) {
    gbc.gridx = x;
    gbc.gridy = y;
    p.add(new JLabel(text), gbc);
  }

  private static void hdrAdd(JPanel p, GridBagConstraints gbc, JLabel comp, int x, int y) {
    gbc.gridx = x;
    gbc.gridy = y;
    p.add(comp, gbc);
  }

  // ==== Carga y filtro de eventos ====
  private void loadEventos() {
    eventosModel.setRowCount(0);
    allEventos = new ArrayList<>(eventoController.listarEventos()); // set -> list
    // Orden por nombre (estable)
    allEventos.sort(Comparator.comparing(DTEvento::nombre, String.CASE_INSENSITIVE_ORDER));
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    for (DTEvento ev : allEventos) {
      eventosModel.addRow(new Object[] { ev.nombre(), ev.sigla(), ev.descripcion(), df.format(ev.fechaAlta()) });
    }
  }

  private void filterEventos() {
    String q = txtBuscarEvento.getText().trim().toLowerCase();
    eventosModel.setRowCount(0);
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    for (DTEvento ev : allEventos) {
      if (q.isEmpty() || ev.nombre().toLowerCase().contains(q)) {
        eventosModel.addRow(new Object[] { ev.nombre(), ev.sigla(), ev.descripcion(), df.format(ev.fechaAlta()) });
      }
    }
    // Al cambiar el listado, limpiar ediciones/detalle
    clearEdicionesPanel();
    clearDetallePanel();
  }

  // ==== Selección de evento -> cargar ediciones ====
  private void onEventoSelectionChanged() {
    int sel = tblEventos.getSelectedRow();
    if (sel < 0) {
      clearEdicionesPanel();
      clearDetallePanel();
      return;
    }
    int mi = tblEventos.convertRowIndexToModel(sel);
    String nombreEvento = (String) eventosModel.getValueAt(mi, 0);
    try {
      DTEventoDetallado det = eventoController.obtenerDatosDetalladosEvento(nombreEvento);
      nombreEventoSeleccionado = (det != null && det.evento() != null) ? det.evento().nombre() : nombreEvento;
      Set<DTEdicion> eds = (det != null) ? det.ediciones() : Set.of();
      updateEdicionesTable(eds);
      setEdicionesEnabled(true);
      clearDetallePanel(); // se vacía hasta que elijan una edición
    } catch (ValidationInputException ex) {
      // comportamiento simple de error: limpiar y desactivar
      clearEdicionesPanel();
      clearDetallePanel();
      // Log error instead of printing stack trace
      System.err.println("Error de validación al obtener detalles del evento: " + ex.getMessage());
    }
  }

  private void updateEdicionesTable(Set<DTEdicion> ediciones) {
    edicionesModel.setRowCount(0);
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    // ordenar por fecha inicio asc, luego nombre
    List<DTEdicion> orden = ediciones.stream()
        .sorted(Comparator.<DTEdicion, Long>comparing(e -> e.fechaInicio().getTime()).thenComparing(DTEdicion::nombre))
        .collect(Collectors.toList());
    for (DTEdicion ed : orden) {
      edicionesModel.addRow(new Object[] {
          ed.nombre(), ed.sigla(), ed.ciudad(), ed.pais(),
          df.format(ed.fechaInicio()), df.format(ed.fechaFin())
      });
    }
  }

  private void clearEdicionesPanel() {
    edicionesModel.setRowCount(0);
    setEdicionesEnabled(false);
  }

  private void setEdicionesEnabled(boolean enabled) {
    pnlEdiciones.setEnabled(enabled);
    tblEdiciones.setEnabled(enabled);
  }

  // ==== Selección de edición -> cargar detalle ====
  private void onEdicionSelectionChanged() {
    int sel = tblEdiciones.getSelectedRow();
    if (sel < 0 || nombreEventoSeleccionado == null) {
      clearDetallePanel();
      return;
    }
    int mi = tblEdiciones.convertRowIndexToModel(sel);
    String nombreEdicion = (String) edicionesModel.getValueAt(mi, 0);
    try {
      edicionDetActual = edicionController.obtenerDatosDetalladosEdicion(nombreEventoSeleccionado, nombreEdicion);
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
        .forEach(tr -> tiposModel.addRow(new Object[] { tr.nombre(), tr.descripcion(), tr.costo(), tr.cupo() }));
    btnVerTipo.setEnabled(false);

    // Registros
    registrosModel.setRowCount(0);
    SimpleDateFormat dft = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    det.registros().stream()
        .sorted(Comparator.<DTRegistro, Long>comparing(r -> r.fecha().getTime()).reversed())
        .forEach(r -> registrosModel.addRow(new Object[] {
            dft.format(r.fecha()), String.format("%.2f", r.costo()),
            r.nicknameAsistente(), r.tipoRegistro().nombre()
        }));

    // Patrocinios
    patrociniosModel.setRowCount(0);
    det.patrocinios().stream()
        .sorted(Comparator.comparing((DTPatrocinio p) -> p.institucion() != null ? p.institucion().nombre() : ""))
        .forEach(p -> patrociniosModel.addRow(new Object[] {
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
    pnlDetalle.setEnabled(enabled);
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

  // ==== Diálogos ====
  private void showTipoDialog(DTTipoRegistro dt) {
    JDialog d = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), "Tipo de Registro", true);
    d.setLayout(new GridBagLayout());
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
    d.setLayout(new GridBagLayout());
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
    g.gridx = x;
    g.gridy = y;
    d.add(new JLabel(k), g);
    g.gridx = x + 1;
    d.add(new JLabel(v), g);
  }
}
