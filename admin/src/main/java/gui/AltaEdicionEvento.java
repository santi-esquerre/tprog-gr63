package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTUsuario;
import datatypes.DTUsuarioItemListado;
import datatypes.TipoUsuario;
import interfaces.IEventoController;
import interfaces.IUsuarioController;

public class AltaEdicionEvento extends JInternalFrame {

    // Controllers
    private final IEventoController eventoController;
    private final IUsuarioController usuarioController;

    // UI
    private final JComboBox<DTEvento> cmbEvento = new JComboBox<>();
    private final JComboBox<DTUsuario> cmbOrganizador = new JComboBox<>();

    private final JTextField txtNombre = new JTextField(24);
    private final JTextField txtSigla = new JTextField(12);
    private final JTextField txtCiudad = new JTextField(18);
    private final JTextField txtPais = new JTextField(18);

    private final JSpinner spInicio = new JSpinner(
            new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
    private final JSpinner spFin = new JSpinner(
            new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
    private final JSpinner spAlta = new JSpinner(
            new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));

    private final JButton btnAceptar = new JButton("Aceptar");
    private final JButton btnCancelar = new JButton("Cancelar");

    public AltaEdicionEvento(IEventoController eventoController, IUsuarioController usuarioController) {
        super("Alta de Edición de Evento", true, true, true, true);
        this.eventoController = Objects.requireNonNull(eventoController);
        this.usuarioController = Objects.requireNonNull(usuarioController);

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(720, 520);
        setLayout(new BorderLayout());

        // ======= NORTH: título =======
        JLabel titleLabel = new JLabel("Completa los datos y confirma el alta", SwingConstants.LEFT);
        titleLabel.setBorder(new EmptyBorder(8, 12, 0, 12));
        add(titleLabel, BorderLayout.NORTH);

        // ======= CENTER: formulario =======
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(8, 12, 8, 12));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 6, 0, 6);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0;
        g.gridy = 0;

        // Combos
        addRow(form, g, "Evento:", cmbEvento);
        addRow(form, g, "Organizador:", cmbOrganizador);

        // Campos texto
        addRow(form, g, "Nombre (único en plataforma):", txtNombre);
        addRow(form, g, "Sigla:", txtSigla);
        addRow(form, g, "Ciudad:", txtCiudad);
        addRow(form, g, "País:", txtPais);

        // Spinners de fecha
        setDateEditor(spInicio, "dd/MM/yyyy");
        setDateEditor(spFin, "dd/MM/yyyy");
        setDateEditor(spAlta, "dd/MM/yyyy");
        addRow(form, g, "Fecha de inicio:", spInicio);
        addRow(form, g, "Fecha de fin:", spFin);
        addRow(form, g, "Fecha de alta:", spAlta);

        add(form, BorderLayout.CENTER);

        // ======= SOUTH: acciones =======
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        actions.add(btnAceptar);
        actions.add(btnCancelar);
        add(actions, BorderLayout.SOUTH);

        // Listeners
        btnCancelar.addActionListener(e -> dispose());
        btnAceptar.addActionListener(e -> onAceptar());

        // Renderers de combos
        cmbEvento.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof DTEvento ev)
                    setText(ev.nombre() + " (" + ev.sigla() + ")");
                return this;
            }
        });
        cmbOrganizador.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof DTUsuario u)
                    setText(u.nickname() + " — " + u.nombre());
                return this;
            }
        });

        // Cargar datos
        loadEventos();
        loadOrganizadores();
    }

    public void loadForm() {
        loadEventos();
        loadOrganizadores();
        // clearForm();
    }

    private static void setDateEditor(JSpinner sp, String pattern) {
        sp.setEditor(new JSpinner.DateEditor(sp, pattern));
        ((JSpinner.DefaultEditor) sp.getEditor()).getTextField().setColumns(10);
    }

    private static void addRow(JPanel panel, GridBagConstraints g, String label, JComponent field) {
        g.gridx = 0;
        panel.add(new JLabel(label), g);
        g.gridx = 1;
        panel.add(field, g);
        g.gridy++;
    }

    private void loadEventos() {
        cmbEvento.removeAllItems();
        // listarEventos() → Set<DTEvento>
        Set<DTEvento> eventos = eventoController.listarEventos();
        List<DTEvento> orden = eventos.stream()
                .sorted(Comparator.comparing(DTEvento::nombre, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        orden.forEach(cmbEvento::addItem);
        if (cmbEvento.getItemCount() > 0)
            cmbEvento.setSelectedIndex(0);
    }

    private void loadOrganizadores() {
        cmbOrganizador.removeAllItems();
        // Obtener organizadores usando el método correcto de la interfaz
        List<DTUsuarioItemListado> orgs = usuarioController.obtenerUsuarios(TipoUsuario.ORGANIZADOR);
        List<DTUsuario> organizadores = orgs.stream()
                .map(item -> new DTUsuario(item.nickname(), "", item.correo()))
                .sorted(Comparator.comparing(DTUsuario::nickname, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        organizadores.forEach(cmbOrganizador::addItem);
        if (cmbOrganizador.getItemCount() > 0)
            cmbOrganizador.setSelectedIndex(0);
    }

    private void onAceptar() {
        DTEvento ev = (DTEvento) cmbEvento.getSelectedItem();
        DTUsuario org = (DTUsuario) cmbOrganizador.getSelectedItem();

        String nombre = txtNombre.getText().trim();
        String sigla = txtSigla.getText().trim();
        String ciudad = txtCiudad.getText().trim();
        String pais = txtPais.getText().trim();
        Date fIni = (Date) spInicio.getValue();
        Date fFin = (Date) spFin.getValue();
        Date fAlta = (Date) spAlta.getValue();

        // Validaciones mínimas de UI (la lógica valida de nuevo)
        if (ev == null) {
            msg("Debe seleccionar un evento.");
            return;
        }
        if (org == null) {
            msg("Debe seleccionar un organizador.");
            return;
        }
        if (nombre.isEmpty()) {
            msg("El nombre es obligatorio.");
            txtNombre.requestFocus();
            return;
        }
        if (sigla.isEmpty()) {
            msg("La sigla es obligatoria.");
            txtSigla.requestFocus();
            return;
        }
        if (ciudad.isEmpty()) {
            msg("La ciudad es obligatoria.");
            txtCiudad.requestFocus();
            return;
        }
        if (pais.isEmpty()) {
            msg("El país es obligatorio.");
            txtPais.requestFocus();
            return;
        }
        if (fIni.after(fFin)) {
            msg("La fecha de inicio no puede ser posterior a la fecha de fin.");
            return;
        }

        try {
            // DTEdicion constructor: nombre, sigla, fechaInicio, fechaFin, fechaAlta,
            // ciudad, pais
            DTEdicion dt = new DTEdicion(nombre, sigla, fIni, fFin, fAlta, ciudad, pais);
            boolean ok = eventoController.agregarEdicionAEvento(ev.nombre(), org.nickname(), dt);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Edición creada correctamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                // Por contrato, la lógica lanza excepción si hay duplicado; este else es
                // defensivo.
                JOptionPane.showMessageDialog(this, "No se pudo crear la edición.", "Atención",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (exceptions.ValidationInputException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtNombre.setText("");
        txtSigla.setText("");
        txtCiudad.setText("");
        txtPais.setText("");
        spInicio.setValue(new Date());
        spFin.setValue(new Date());
        spAlta.setValue(new Date());
        if (cmbEvento.getItemCount() > 0)
            cmbEvento.setSelectedIndex(0);
        if (cmbOrganizador.getItemCount() > 0)
            cmbOrganizador.setSelectedIndex(0);
        txtNombre.requestFocus();
    }

    private void msg(String m) {
        JOptionPane.showMessageDialog(this, m, "Validación", JOptionPane.WARNING_MESSAGE);
    }
}
