package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DatePicker;

import datatypes.DTEventoAlta;
import exceptions.ValidationInputException;
import interfaces.IEventoController;
import util.ExceptionHandler;

public class AltaEvento extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextField txtSigla;
    private DatePicker dateFechaAlta;
    IEventoController controller;
    public AltaEvento(IEventoController controller) {
    	this.controller = controller;
    	
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

        JButton btnSave = new JButton("Aceptar");
        panelBotones.add(btnSave);

        JButton btnCancel = new JButton("Cancelar");
        panelBotones.add(btnCancel);
        
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
                        
                        dateFechaAlta = new DatePicker();
                        //dateFechaAlta.setDateToToday();
                        GridBagConstraints gbc_txtFechaAltaDia = new GridBagConstraints(
                        		);
                        gbc_txtFechaAltaDia.fill = GridBagConstraints.HORIZONTAL;
                        gbc_txtFechaAltaDia.insets = new Insets(0, 0, 0, 5);
                        gbc_txtFechaAltaDia.gridx = 0;
                        gbc_txtFechaAltaDia.gridy = 0;
                        panel_3.add(dateFechaAlta, gbc_txtFechaAltaDia);
                
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
        btnSave.addActionListener(e -> {
            saveAction(e);
        });

        btnCancel.addActionListener(e -> dispose());
    }
    
    private void clearForm() {
		txtNombre.setText("");
		txtDescripcion.setText("");
		txtSigla.setText("");
		//categoriaList.clearSelection();
	}
    
    //Actions
    private void saveAction(ActionEvent e) {
    	String nombre = txtNombre.getText().trim();
    	String descripcion = txtDescripcion.getText().trim();
    	String sigla = txtSigla.getText().trim();
    	Date fechaAlta = Date.from(dateFechaAlta.getDate().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
    	
    	DTEventoAlta eventoData = new DTEventoAlta(nombre, descripcion, fechaAlta, sigla, null);
    	
    	try {
        	controller.altaEvento(eventoData);
            clearForm();
            dispose();
		} catch (Exception ex) {
			ExceptionHandler.manageException(this, ex);
		}finally {
			
		}
    	
    	/*

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
        */
    }
}