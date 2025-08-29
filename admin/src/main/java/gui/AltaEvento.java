package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AltaEvento extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextField txtSigla;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;

    /**
     * Launch the application (para probar de forma independiente).
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AltaEvento frame = new AltaEvento();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public AltaEvento() {
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setTitle("Alta de Evento");
        setBounds(100, 100, 500, 320);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 0, 0, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        panel.setLayout(gbl_panel);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNombre = new GridBagConstraints();
        gbc_lblNombre.anchor = GridBagConstraints.WEST;
        gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
        gbc_lblNombre.gridx = 0;
        gbc_lblNombre.gridy = 0;
        panel.add(lblNombre, gbc_lblNombre);

        txtNombre = new JTextField();
        GridBagConstraints gbc_txtNombre = new GridBagConstraints();
        gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
        gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNombre.gridx = 1;
        gbc_txtNombre.gridy = 0;
        panel.add(txtNombre, gbc_txtNombre);
        txtNombre.setColumns(20);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
        gbc_lblDescripcion.anchor = GridBagConstraints.WEST;
        gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescripcion.gridx = 0;
        gbc_lblDescripcion.gridy = 1;
        panel.add(lblDescripcion, gbc_lblDescripcion);

        JPanel panelBotones = new JPanel();
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        JButton btnAceptar = new JButton("Aceptar");
        panelBotones.add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnCancelar);
        
        String[] categorias = {"Conferencia", "Taller", "Seminario", "Curso", "Feria"};
                                
                                        txtDescripcion = new JTextArea(4, 20);
                                        txtDescripcion.setLineWrap(true);
                                        txtDescripcion.setWrapStyleWord(true);
                                        GridBagConstraints gbc_txtDescripcion = new GridBagConstraints();
                                        gbc_txtDescripcion.gridwidth = 2;
                                        gbc_txtDescripcion.insets = new Insets(0, 0, 5, 0);
                                        gbc_txtDescripcion.fill = GridBagConstraints.BOTH;
                                        gbc_txtDescripcion.gridx = 0;
                                        gbc_txtDescripcion.gridy = 2;
                                        panel.add(txtDescripcion, gbc_txtDescripcion);
                                        String descripcion = txtDescripcion.getText().trim();
                        
                                JLabel lblFechaAlta = new JLabel("Fecha de alta:");
                                GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
                                gbc_lblFechaAlta.anchor = GridBagConstraints.WEST;
                                gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
                                gbc_lblFechaAlta.gridx = 0;
                                gbc_lblFechaAlta.gridy = 3;
                                panel.add(lblFechaAlta, gbc_lblFechaAlta);
                        
                        JPanel panel_3 = new JPanel();
                        GridBagConstraints gbc_panel_3 = new GridBagConstraints();
                        gbc_panel_3.insets = new Insets(0, 0, 5, 0);
                        gbc_panel_3.fill = GridBagConstraints.BOTH;
                        gbc_panel_3.gridx = 1;
                        gbc_panel_3.gridy = 3;
                        panel.add(panel_3, gbc_panel_3);
                        GridBagLayout gbl_panel_3 = new GridBagLayout();
                        gbl_panel_3.columnWidths = new int[]{0, 0, 0, 0};
                        gbl_panel_3.rowHeights = new int[]{0, 0};
                        gbl_panel_3.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
                        gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
                        panel_3.setLayout(gbl_panel_3);
                        
                        textField = new JTextField();
                        textField.setText("21");
                        textField.setEditable(false);
                        textField.setColumns(10);
                        GridBagConstraints gbc_textField = new GridBagConstraints();
                        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
                        gbc_textField.insets = new Insets(0, 0, 0, 5);
                        gbc_textField.gridx = 0;
                        gbc_textField.gridy = 0;
                        panel_3.add(textField, gbc_textField);
                        
                        textField_1 = new JTextField();
                        textField_1.setText("12");
                        textField_1.setEditable(false);
                        textField_1.setColumns(10);
                        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
                        gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
                        gbc_textField_1.insets = new Insets(0, 0, 0, 5);
                        gbc_textField_1.gridx = 1;
                        gbc_textField_1.gridy = 0;
                        panel_3.add(textField_1, gbc_textField_1);
                        
                        textField_2 = new JTextField();
                        textField_2.setText("2004");
                        textField_2.setEditable(false);
                        textField_2.setColumns(10);
                        GridBagConstraints gbc_textField_2 = new GridBagConstraints();
                        gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
                        gbc_textField_2.gridx = 2;
                        gbc_textField_2.gridy = 0;
                        panel_3.add(textField_2, gbc_textField_2);
                
                        JLabel lblSigla = new JLabel("Sigla:");
                        GridBagConstraints gbc_lblSigla = new GridBagConstraints();
                        gbc_lblSigla.anchor = GridBagConstraints.WEST;
                        gbc_lblSigla.insets = new Insets(0, 0, 5, 5);
                        gbc_lblSigla.gridx = 0;
                        gbc_lblSigla.gridy = 4;
                        panel.add(lblSigla, gbc_lblSigla);
                
                        txtSigla = new JTextField();
                        GridBagConstraints gbc_txtSigla = new GridBagConstraints();
                        gbc_txtSigla.insets = new Insets(0, 0, 5, 0);
                        gbc_txtSigla.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtSigla.gridx = 1;
                        gbc_txtSigla.gridy = 4;
                        panel.add(txtSigla, gbc_txtSigla);
                        txtSigla.setColumns(10);
                        String sigla = txtSigla.getText().trim();
        
                JLabel lblCategorias = new JLabel("Categorías:");
                GridBagConstraints gbc_lblCategorias = new GridBagConstraints();
                gbc_lblCategorias.anchor = GridBagConstraints.WEST;
                gbc_lblCategorias.insets = new Insets(0, 0, 0, 5);
                gbc_lblCategorias.gridx = 0;
                gbc_lblCategorias.gridy = 5;
                panel.add(lblCategorias, gbc_lblCategorias);
                JList<String> listaCategorias = new JList<>(categorias);
                GridBagConstraints gbc_listaCategorias = new GridBagConstraints();
                gbc_listaCategorias.fill = GridBagConstraints.HORIZONTAL;
                gbc_listaCategorias.gridx = 1;
                gbc_listaCategorias.gridy = 5;
                panel.add(listaCategorias, gbc_listaCategorias);
                listaCategorias.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // Listeners
        btnAceptar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();

            if (nombre.isEmpty() || descripcion.isEmpty() || sigla.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                        "Debe completar todos los campos obligatorios.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Acá iría la validación real contra BD o controlador:
            if ("EventoDuplicado".equalsIgnoreCase(nombre)) {
                JOptionPane.showMessageDialog(this, 
                        "Ya existe un evento con ese nombre. Corrija los datos.", 
                        "Evento duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, 
                    "Evento \"" + nombre + "\" dado de alta correctamente.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}