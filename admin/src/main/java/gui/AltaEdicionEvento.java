package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

@SuppressWarnings("serial")
public class AltaEdicionEvento extends JInternalFrame {

    // Campos de formulario
    private JComboBox<String> cmbEvento;
    private JComboBox<String> cmbOrganizador;
    private JTextField txtNombre;
    private JTextField txtSigla;
    private JTextField txtCiudad;
    private JTextField txtPais;
    private DatePicker dpFechaInicio;
    private DatePicker dpFechaFin;
    private DatePicker dpFechaAlta;

    // Botones
    private JButton btnAceptar;
    private JButton btnCancelar;

    public AltaEdicionEvento() {
        super("Alta de Edición de Evento", true, true, true, true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        buildContent();
        buildActions();
        pack();
        setSize(640, 440);
    }

    private void buildContent() {
        JPanel form = new JPanel(new GridBagLayout());

        // Settings DatePicker (es-UY) y valores por defecto
        DatePickerSettings settingsInicio = new DatePickerSettings(Locale.forLanguageTag("es-UY"));
        settingsInicio.setAllowEmptyDates(false);
        DatePickerSettings settingsFin = new DatePickerSettings(Locale.forLanguageTag("es-UY"));
        settingsFin.setAllowEmptyDates(false);
        DatePickerSettings settingsAlta = new DatePickerSettings(Locale.forLanguageTag("es-UY"));
        settingsAlta.setAllowEmptyDates(false);

        dpFechaInicio = new DatePicker(settingsInicio);
        dpFechaFin = new DatePicker(settingsFin);
        dpFechaAlta = new DatePicker(settingsAlta);
        dpFechaAlta.setDate(LocalDate.now());

        int y = 0;

        addRow(form, y++, new JLabel("Evento:"), cmbEvento = new JComboBox<>());
        addRow(form, y++, new JLabel("Organizador:"), cmbOrganizador = new JComboBox<>());
        addRow(form, y++, new JLabel("Nombre (único):"), txtNombre = new JTextField(24));
        addRow(form, y++, new JLabel("Sigla:"), txtSigla = new JTextField(12));
        addRow(form, y++, new JLabel("Ciudad:"), txtCiudad = new JTextField(16));
        addRow(form, y++, new JLabel("País:"), txtPais = new JTextField(16));
        addRow(form, y++, new JLabel("Fecha inicio:"), dpFechaInicio);
        addRow(form, y++, new JLabel("Fecha fin:"), dpFechaFin);
        addRow(form, y++, new JLabel("Fecha alta:"), dpFechaAlta);

        // Botonera
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        buttons.add(btnCancelar);
        buttons.add(btnAceptar);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        // Accesibilidad básica
        getRootPane().setDefaultButton(btnAceptar);
    }

    private void buildActions() {
        btnCancelar.addActionListener(e -> dispose());

        btnAceptar.addActionListener(e -> {
            if (!validar()) return;
            // Placeholder sin lógica conectada
            JOptionPane.showMessageDialog(
                this,
                "Formulario válido. Conectar con la lógica en el siguiente paso.",
                "Alta de Edición",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    private boolean validar() {
        if (cmbEvento.getSelectedItem() == null) {
            warn("Seleccioná un evento.");
            cmbEvento.requestFocusInWindow();
            return false;
        }
        if (cmbOrganizador.getSelectedItem() == null) {
            warn("Seleccioná un organizador.");
            cmbOrganizador.requestFocusInWindow();
            return false;
        }
        if (isEmpty(txtNombre)) {
            warn("Ingresá el nombre de la edición.");
            txtNombre.requestFocusInWindow();
            return false;
        }
        if (dpFechaInicio.getDate() == null) {
            warn("Ingresá la fecha de inicio.");
            dpFechaInicio.requestFocusInWindow();
            return false;
        }
        if (dpFechaFin.getDate() == null) {
            warn("Ingresá la fecha de fin.");
            dpFechaFin.requestFocusInWindow();
            return false;
        }
        // Regla básica: inicio ≤ fin
        LocalDate ini = dpFechaInicio.getDate();
        LocalDate fin = dpFechaFin.getDate();
        if (ini.isAfter(fin)) {
            warn("La fecha de inicio no puede ser posterior a la fecha de fin.");
            dpFechaInicio.requestFocusInWindow();
            return false;
        }
        if (dpFechaAlta.getDate() == null) {
            warn("Ingresá la fecha de alta.");
            dpFechaAlta.requestFocusInWindow();
            return false;
        }
        return true;
    }

    // ---- Helpers UI ---------------------------------------------------------

    private void addRow(JPanel panel, int y, JComponent label, JComponent field) {
        panel.add(label, gbc(0, y, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(6, 10, 6, 8)));
        panel.add(field, gbc(1, y, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(6, 0, 6, 10)));
    }

    private static GridBagConstraints gbc(int x, int y, int w, int h, double wx, double wy, int anchor, int fill, Insets insets) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = w;
        c.gridheight = h;
        c.weightx = wx;
        c.weighty = wy;
        c.anchor = anchor;
        c.fill = fill;
        c.insets = insets != null ? insets : new Insets(0, 0, 0, 0);
        return c;
    }

    private static boolean isEmpty(JTextField tf) {
        return tf.getText() == null || tf.getText().trim().isEmpty();
    }

    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validación", JOptionPane.WARNING_MESSAGE);
    }

    // ---- Getters básicos (útiles para el wiring posterior) ------------------

    public JComboBox<String> getCmbEvento() { return cmbEvento; }
    public JComboBox<String> getCmbOrganizador() { return cmbOrganizador; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtSigla() { return txtSigla; }
    public JTextField getTxtCiudad() { return txtCiudad; }
    public JTextField getTxtPais() { return txtPais; }
    public DatePicker getDpFechaInicio() { return dpFechaInicio; }
    public DatePicker getDpFechaFin() { return dpFechaFin; }
    public DatePicker getDpFechaAlta() { return dpFechaAlta; }
    public JButton getBtnAceptar() { return btnAceptar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}
