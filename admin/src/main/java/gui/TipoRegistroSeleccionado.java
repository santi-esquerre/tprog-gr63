package gui;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.awt.event.ActionEvent;
import interfaces.Factory;
import interfaces.IEdicionController;
import datatypes.DTTipoRegistro;

public class TipoRegistroSeleccionado extends JDialog {
	Map<String, DTTipoRegistro> mapCombo = new HashMap<>();
	private DTTipoRegistro selectedValue = null; // Variable para almacenar el valor seleccionado
	public TipoRegistroSeleccionado(Window parent, String title, String nombreEdicion) {
		super(parent, title, Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{10, 0};
		gbl_panel.rowHeights = new int[]{75, 0, 71, -2, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.anchor = GridBagConstraints.WEST;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 350, 0};
		gbl_panel_1.rowHeights = new int[]{0, 35, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel = new JLabel("Tipo de registro:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("- Seleccionar tipo de registro -");
		
		JTextArea textArea = new JTextArea();
		
		//se rellena el combobox
		Factory factory = Factory.get();
		var edicion = factory.getIEdicionController();
		Set<DTTipoRegistro> tiposDeRegistro = edicion.mostrarTiposDeRegistro(nombreEdicion); // Se trae los tipos de registro asociados a la edicion
		for (DTTipoRegistro tipo : tiposDeRegistro) {
			String texto = tipo.nombre();
			int cupo = tipo.getCupo();
			if (cupo == 0) {
				texto += " (No hay cupo disponible)";
			} else {
				int costo = tipo.getCupo();
				texto += " $" + Float.toString(costo); 
			}
			comboBox.addItem(texto);
			mapCombo.put(texto, tipo);
		}
	
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = comboBox.getSelectedIndex();
				if (comboBox.getSelectedIndex() > 0) {
					String opcionSeleccionada = (String) comboBox.getSelectedItem();
					textArea.setText("Descripción: " + mapCombo.get(opcionSeleccionada).getDescripcion());
				}
					
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		panel_1.add(comboBox, gbc_comboBox);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JSeparator separator = new JSeparator();
		panel_2.add(separator);
		
		JPanel panelDescripcion = new JPanel();
		GridBagConstraints gbc_panelDescripcion = new GridBagConstraints();
		gbc_panelDescripcion.insets = new Insets(0, 0, 5, 0);
		gbc_panelDescripcion.fill = GridBagConstraints.BOTH;
		gbc_panelDescripcion.gridx = 0;
		gbc_panelDescripcion.gridy = 2;
		panel.add(panelDescripcion, gbc_panelDescripcion);
		panelDescripcion.setLayout(new BoxLayout(panelDescripcion, BoxLayout.X_AXIS));
		
		panelDescripcion.add(textArea);
		textArea.setText("Descripción: ");
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 3;
		panel.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		JSeparator separator_1 = new JSeparator();
		panel_3.add(separator_1);
		
		JPanel btnPanel = new JPanel();
		GridBagConstraints gbc_btnPanel = new GridBagConstraints();
		gbc_btnPanel.fill = GridBagConstraints.VERTICAL;
		gbc_btnPanel.gridx = 0;
		gbc_btnPanel.gridy = 4;
		panel.add(btnPanel, gbc_btnPanel);
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() > 0) {
					
					String opcionSeleccionada = (String) comboBox.getSelectedItem();
					if (mapCombo.get(opcionSeleccionada).cupo() == 0) {
						
						JOptionPane.showMessageDialog(btnAceptar, "Debe seleccionar un tipo de registro con cupo", "Tipo de Registro",
			                    JOptionPane.ERROR_MESSAGE);
					}
					else {
						selectedValue = mapCombo.get(opcionSeleccionada);
						dispose(); // Cierra la ventana 
					}
					
				}
				else 
					JOptionPane.showMessageDialog(btnAceptar, "Debe seleccionar un tipo de registro", "Tipo de Registro",
		                    JOptionPane.ERROR_MESSAGE);
				}
			});
		
		
		btnAceptar.setPreferredSize(new Dimension(90, 30));
		btnAceptar.setMaximumSize(new Dimension(90, 30));
		btnAceptar.setMinimumSize(new Dimension(90, 30));
		btnPanel.add(btnAceptar);
		btnPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedValue = null; // Si se cancela, se guarda null
				dispose(); // Cierra la ventana
			}
		});
		btnCancelar.setMaximumSize(new Dimension(90, 30));
		btnCancelar.setMinimumSize(new Dimension(90, 30));
		btnCancelar.setPreferredSize(new Dimension(90, 30));
		btnPanel.add(btnCancelar);

	}
	
	// Metodo para regresar el valor seleccionado
	public DTTipoRegistro getSelectedValue() {
		return selectedValue;
	}
}
