package gui;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;

import exceptions.InstitucionRepetidaException;
import interfaces.IInstitucionController;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AltaInstitucion extends JInternalFrame{
	private static final long serialVersionUID = 1L;
	private JTextField textNombre;
	private JTextField textWeb;
	private JTextArea textDesc;
	private IInstitucionController institucionController;
	public AltaInstitucion(IInstitucionController institucionController) {
		this.institucionController = institucionController;
		setBounds(100, 100, 400, 283);
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setTitle("Alta Institucion");
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{81, 0, 23, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 66, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		textNombre = new JTextField();
		GridBagConstraints gbc_textNombre = new GridBagConstraints();
		gbc_textNombre.gridwidth = 4;
		gbc_textNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNombre.gridx = 1;
		gbc_textNombre.gridy = 0;
		panel.add(textNombre, gbc_textNombre);
		textNombre.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Sitio Web:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textWeb = new JTextField();
		GridBagConstraints gbc_textWeb = new GridBagConstraints();
		gbc_textWeb.gridwidth = 4;
		gbc_textWeb.insets = new Insets(0, 0, 5, 5);
		gbc_textWeb.fill = GridBagConstraints.HORIZONTAL;
		gbc_textWeb.gridx = 1;
		gbc_textWeb.gridy = 1;
		panel.add(textWeb, gbc_textWeb);
		textWeb.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Descripción:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textDesc = new JTextArea();
		GridBagConstraints gbc_textDesc = new GridBagConstraints();
		gbc_textDesc.gridwidth = 6;
		gbc_textDesc.fill = GridBagConstraints.BOTH;
		gbc_textDesc.gridx = 0;
		gbc_textDesc.gridy = 3;
		gbc_textDesc.weightx = 1.0;
		gbc_textDesc.weighty = 1.0;
		panel.add(textDesc, gbc_textDesc);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 10, 20, 10));
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnAceptar_1 = new JButton("Aceptar");
		btnAceptar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaInstitucionActionPerformed(e);
			}
		});
		panel_1.add(btnAceptar_1);
		
		JButton btnCancelar_1 = new JButton("Cancelar");
		btnCancelar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				setVisible(false);
			}
		});
		panel_1.add(btnCancelar_1);
		
		
	}
	
	protected void altaInstitucionActionPerformed(ActionEvent e) {
		String nombre = textNombre.getText();
		String desc = textDesc.getText();
		String web = textWeb.getText();
		
		if (checkCampos()) {
			try {
				institucionController.crearInstitucion(nombre, desc, web);
				JOptionPane.showMessageDialog(this, "Institución registrada correctamente", "Registro de Institucion", JOptionPane.INFORMATION_MESSAGE);
			} catch (InstitucionRepetidaException i) {
				JOptionPane.showMessageDialog(this, i.getMessage(), "Registro de Institucion", JOptionPane.ERROR_MESSAGE);
			}
			limpiarCampos();
			setVisible(false);
		}
	}
	
	private boolean checkCampos() {
		String nombre = textNombre.getText();
		String desc = textDesc.getText();
		String web = textWeb.getText();
		if (nombre.isEmpty() || desc.isEmpty() || web.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No se puede tener campos vacios", "Registro de Institucion", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	private void limpiarCampos() {
		textNombre.setText("");
		textWeb.setText("");
		textDesc.setText("");
	}
}
