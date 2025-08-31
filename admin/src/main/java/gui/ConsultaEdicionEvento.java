package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Consulta de Edición de Evento (solo UI, sin wiring).
 * Flujo:
 *  1) Tabla Eventos (izq)
 *  2) Tabla Ediciones del evento (centro)
 *  3) Detalle de la edición (der): organizador + tablas de tipos, registros, patrocinios
 *  4) Botones "Ver Tipo de Registro..." y "Ver Patrocinio..." (deshabilitados hasta seleccionar)
 */
public class ConsultaEdicionEvento extends JInternalFrame {

  // --- IZQUIERDA: EVENTOS ---
  private final JTextField txtBuscarEvento = new JTextField(16);
  private final JTable tblEventos = new JTable();

  // --- CENTRO: EDICIONES ---
  private final JTable tblEdiciones = new JTable();

  // --- DERECHA: DETALLE EDICIÓN ---
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

  private final JButton btnVerTipo = new JButton("Ver Tipo de Registro...");
  private final JButton btnVerPatro = new JButton("Ver Patrocinio...");

  public ConsultaEdicionEvento() {
    super("Consulta de Edición de Evento", true, true, true, true);
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

    tblEventos.setModel(new DefaultTableModel(
        new Object[][]{}, new String[]{"Nombre", "Sigla", "Descripción", "Fecha alta"}) {
      @Override public boolean isCellEditable(int r, int c) { return false; }
    });
    tblEventos.setAutoCreateRowSorter(true);
    pnlEventos.add(new JScrollPane(tblEventos), BorderLayout.CENTER);

    // engancho centro-derecha
    spEventosEdiciones.setLeftComponent(pnlEventos);
    spEventosEdiciones.setRightComponent(spCentroDerecha);

    // ===== panel CENTRO: ediciones =====
    JPanel pnlEdiciones = new JPanel(new BorderLayout(8, 8));
    pnlEdiciones.setBorder(new EmptyBorder(8, 4, 8, 4));
    pnlEdiciones.add(new JLabel("Ediciones del evento seleccionado"), BorderLayout.NORTH);

    tblEdiciones.setModel(new DefaultTableModel(
        new Object[][]{}, new String[]{"Nombre", "Sigla", "Inicio", "Fin", "Ciudad", "País"}) {
      @Override public boolean isCellEditable(int r, int c) { return false; }
    });
    tblEdiciones.setAutoCreateRowSorter(true);
    pnlEdiciones.add(new JScrollPane(tblEdiciones), BorderLayout.CENTER);

    spCentroDerecha.setLeftComponent(pnlEdiciones);

    // ===== panel DERECHA: detalle edición =====
    JPanel pnlDetalle = new JPanel(new BorderLayout(8, 8));
    pnlDetalle.setBorder(new EmptyBorder(8, 4, 8, 8));

    // cabecera detalle
    JPanel hdr = new JPanel(new GridBagLayout());
    hdr.setBorder(new TitledBorder("Datos de la edición"));
    GridBagConstraints base = new GridBagConstraints();
    base.insets = new Insets(4, 6, 4, 6);
    base.anchor = GridBagConstraints.WEST;

    int row = 0;
    GridBagConstraints g1 = (GridBagConstraints) base.clone(); g1.gridx = 0; g1.gridy = row;
    hdr.add(new JLabel("Nombre:"), g1);
    GridBagConstraints g2 = (GridBagConstraints) base.clone(); g2.gridx = 1; g2.gridy = row;
    hdr.add(lblNomEd, g2);
    GridBagConstraints g3 = (GridBagConstraints) base.clone(); g3.gridx = 2; g3.gridy = row;
    hdr.add(new JLabel("Sigla:"), g3);
    GridBagConstraints g4 = (GridBagConstraints) base.clone(); g4.gridx = 3; g4.gridy = row;
    hdr.add(lblSiglaEd, g4);

    row++;
    GridBagConstraints g5 = (GridBagConstraints) base.clone(); g5.gridx = 0; g5.gridy = row;
    hdr.add(new JLabel("Fecha inicio:"), g5);
    GridBagConstraints g6 = (GridBagConstraints) base.clone(); g6.gridx = 1; g6.gridy = row;
    hdr.add(lblFIni, g6);
    GridBagConstraints g7 = (GridBagConstraints) base.clone(); g7.gridx = 2; g7.gridy = row;
    hdr.add(new JLabel("Fecha fin:"), g7);
    GridBagConstraints g8 = (GridBagConstraints) base.clone(); g8.gridx = 3; g8.gridy = row;
    hdr.add(lblFFin, g8);

    row++;
    GridBagConstraints g9 = (GridBagConstraints) base.clone(); g9.gridx = 0; g9.gridy = row;
    hdr.add(new JLabel("Ciudad:"), g9);
    GridBagConstraints g10 = (GridBagConstraints) base.clone(); g10.gridx = 1; g10.gridy = row;
    hdr.add(lblCiudad, g10);
    GridBagConstraints g11 = (GridBagConstraints) base.clone(); g11.gridx = 2; g11.gridy = row;
    hdr.add(new JLabel("País:"), g11);
    GridBagConstraints g12 = (GridBagConstraints) base.clone(); g12.gridx = 3; g12.gridy = row;
    hdr.add(lblPais, g12);

    // organizador
    row++;
    GridBagConstraints g13 = (GridBagConstraints) base.clone(); 
    g13.gridx = 0; g13.gridy = row; g13.gridwidth = 4;
    JPanel orgPanel = new JPanel(new GridBagLayout());
    orgPanel.setBorder(new TitledBorder("Organizador"));
    GridBagConstraints baseOrg = new GridBagConstraints();
    baseOrg.insets = new Insets(3, 6, 3, 6); 
    baseOrg.anchor = GridBagConstraints.WEST;

    int ro = 0;
    GridBagConstraints go1 = (GridBagConstraints) baseOrg.clone(); go1.gridx = 0; go1.gridy = ro;
    orgPanel.add(new JLabel("Nickname:"), go1);
    GridBagConstraints go2 = (GridBagConstraints) baseOrg.clone(); go2.gridx = 1; go2.gridy = ro;
    orgPanel.add(lblOrgNick, go2);
    ro++;
    GridBagConstraints go3 = (GridBagConstraints) baseOrg.clone(); go3.gridx = 0; go3.gridy = ro;
    orgPanel.add(new JLabel("Nombre:"), go3);
    GridBagConstraints go4 = (GridBagConstraints) baseOrg.clone(); go4.gridx = 1; go4.gridy = ro;
    orgPanel.add(lblOrgNombre, go4);
    ro++;
    GridBagConstraints go5 = (GridBagConstraints) baseOrg.clone(); go5.gridx = 0; go5.gridy = ro;
    orgPanel.add(new JLabel("Correo:"), go5);
    GridBagConstraints go6 = (GridBagConstraints) baseOrg.clone(); go6.gridx = 1; go6.gridy = ro;
    orgPanel.add(lblOrgCorreo, go6);

    hdr.add(orgPanel, g13);
    pnlDetalle.add(hdr, BorderLayout.NORTH);

    // pestañas: tipos / registros / patrocinios
    JTabbedPane tabs = new JTabbedPane();

    // TAB TIPOS
    JPanel tabTipos = new JPanel(new BorderLayout(4, 4));
    tblTipos.setModel(new DefaultTableModel(
        new Object[][]{}, new String[]{"Nombre", "Descripción", "Costo", "Cupo"}) {
      @Override public boolean isCellEditable(int r, int c) { return false; }
    });
    tblTipos.setAutoCreateRowSorter(true);
    tabTipos.add(new JScrollPane(tblTipos), BorderLayout.CENTER);
    JPanel pnlTiposBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnVerTipo.setEnabled(false);
    pnlTiposBtns.add(btnVerTipo);
    tabTipos.add(pnlTiposBtns, BorderLayout.SOUTH);
    tabs.addTab("Tipos de registro", tabTipos);

    // TAB REGISTROS
    JPanel tabReg = new JPanel(new BorderLayout(4, 4));
    tblRegistros.setModel(new DefaultTableModel(
        new Object[][]{}, new String[]{"Fecha", "Costo", "Asistente", "Tipo registro"}) {
      @Override public boolean isCellEditable(int r, int c) { return false; }
    });
    tblRegistros.setAutoCreateRowSorter(true);
    tabReg.add(new JScrollPane(tblRegistros), BorderLayout.CENTER);
    tabs.addTab("Registros", tabReg);

    // TAB PATROCINIOS
    JPanel tabPatro = new JPanel(new BorderLayout(4, 4));
    tblPatrocinios.setModel(new DefaultTableModel(
        new Object[][]{}, new String[]{"Patrocinador", "Nivel", "Monto"}) {
      @Override public boolean isCellEditable(int r, int c) { return false; }
    });
    tblPatrocinios.setAutoCreateRowSorter(true);
    tabPatro.add(new JScrollPane(tblPatrocinios), BorderLayout.CENTER);
    JPanel pnlPatroBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnVerPatro.setEnabled(false);
    pnlPatroBtns.add(btnVerPatro);
    tabPatro.add(pnlPatroBtns, BorderLayout.SOUTH);
    tabs.addTab("Patrocinios", tabPatro);

    pnlDetalle.add(tabs, BorderLayout.CENTER);
    spCentroDerecha.setRightComponent(pnlDetalle);

    // ===== placeholders/wiring mínimo (sin lógica) =====
    // Habilitar botones de “ver…” cuando haya selección
    tblTipos.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) btnVerTipo.setEnabled(tblTipos.getSelectedRow() >= 0);
    });
    tblPatrocinios.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) btnVerPatro.setEnabled(tblPatrocinios.getSelectedRow() >= 0);
    });
  }

  // --- Getters útiles para el futuro wiring (opcional) ---
  public JTable getTblEventos() { return tblEventos; }
  public JTable getTblEdiciones() { return tblEdiciones; }
  public JTable getTblTipos() { return tblTipos; }
  public JTable getTblRegistros() { return tblRegistros; }
  public JTable getTblPatrocinios() { return tblPatrocinios; }
  public JTextField getTxtBuscarEvento() { return txtBuscarEvento; }

  public JLabel getLblNomEd() { return lblNomEd; }
  public JLabel getLblSiglaEd() { return lblSiglaEd; }
  public JLabel getLblFIni() { return lblFIni; }
  public JLabel getLblFFin() { return lblFFin; }
  public JLabel getLblCiudad() { return lblCiudad; }
  public JLabel getLblPais() { return lblPais; }
  public JLabel getLblOrgNick() { return lblOrgNick; }
  public JLabel getLblOrgNombre() { return lblOrgNombre; }
  public JLabel getLblOrgCorreo() { return lblOrgCorreo; }

  public JButton getBtnVerTipo() { return btnVerTipo; }
  public JButton getBtnVerPatro() { return btnVerPatro; }
}
