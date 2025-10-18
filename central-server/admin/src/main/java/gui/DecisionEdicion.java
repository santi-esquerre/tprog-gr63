package gui;

import javax.swing.JInternalFrame;

import interfaces.Factory;
import interfaces.IReloadable;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import datatypes.DTAsistente;
import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.EstadoEdicion;
import exceptions.ValidationInputException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;

public class DecisionEdicion extends JInternalFrame implements IReloadable {
	private static final long serialVersionUID = 1L;
	private JTable table;
	Factory factory = Factory.get();
	JComboBox comboBoxEvento;
	private ButtonGroup seleccionRol;
	private JRadioButton rbtnAceptar;
	private JRadioButton rbtnRechazar;
	
	public DecisionEdicion() {
		setResizable(true);
		setMaximizable(true);
		setClosable(true);
		setTitle("Decisión de Edición");
		setBounds(100, 100, 500, 455);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{77, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panelComboBox = new JPanel();
		GridBagConstraints gbc_panelComboBox = new GridBagConstraints();
		gbc_panelComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_panelComboBox.fill = GridBagConstraints.BOTH;
		gbc_panelComboBox.gridx = 0;
		gbc_panelComboBox.gridy = 0;
		panel.add(panelComboBox, gbc_panelComboBox);
		GridBagLayout gbl_panelComboBox = new GridBagLayout();
		gbl_panelComboBox.columnWidths = new int[]{69, 239, 0, 0, 0, 0, 0};
		gbl_panelComboBox.rowHeights = new int[]{0, 0, 0};
		gbl_panelComboBox.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelComboBox.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelComboBox.setLayout(gbl_panelComboBox);
		
		JLabel lblNewLabel = new JLabel("Eventos:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panelComboBox.add(lblNewLabel, gbc_lblNewLabel);
		
		comboBoxEvento = new JComboBox();
		comboBoxEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBoxEvento.getSelectedIndex() > 0) {

					String eventoSeleccionado = (String) comboBoxEvento.getSelectedItem();
					var evento = factory.getIEventoController();
					try {
						Set<DTEdicion> ediciones = evento.mostrarEdiciones(eventoSeleccionado, EstadoEdicion.INGRESADA);
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.setRowCount(0);
						for (var ed : ediciones) {
							model.addRow(new Object[] { ed.nombre(), ed.sigla(), ed.fechaInicio(), ed.fechaFin(),
									ed.fechaAlta(), ed.pais(), ed.ciudad() });
						}

					} catch (ValidationInputException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		GridBagConstraints gbc_comboBoxEvento = new GridBagConstraints();
		gbc_comboBoxEvento.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEvento.gridwidth = 2;
		gbc_comboBoxEvento.insets = new Insets(0, 0, 0, 5);
		gbc_comboBoxEvento.gridx = 0;
		gbc_comboBoxEvento.gridy = 1;
		panelComboBox.add(comboBoxEvento, gbc_comboBoxEvento);
		
		JPanel panelTabla = new JPanel();
		GridBagConstraints gbc_panelTabla = new GridBagConstraints();
		gbc_panelTabla.insets = new Insets(0, 0, 5, 0);
		gbc_panelTabla.fill = GridBagConstraints.BOTH;
		gbc_panelTabla.gridx = 0;
		gbc_panelTabla.gridy = 1;
		panel.add(panelTabla, gbc_panelTabla);
		GridBagLayout gbl_panelTabla = new GridBagLayout();
		gbl_panelTabla.columnWidths = new int[]{184, 241, 0};
		gbl_panelTabla.rowHeights = new int[]{-10, 220, 0};
		gbl_panelTabla.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelTabla.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panelTabla.setLayout(gbl_panelTabla);
		
		JLabel lblNewLabel_2 = new JLabel("Ediciones:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_2.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		panelTabla.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMinimumSize(new Dimension(500, 23));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.weighty = 1.0;
		panelTabla.add(scrollPane, gbc_scrollPane);

		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] { "Nombre", "Sigla", "Fecha Inicio", "Fecha Fin", "Fecha Alta", "Pais", "Ciudad" }));
		scrollPane.setViewportView(table);
		scrollPane.setPreferredSize(new Dimension(450, 200));
		table.setPreferredScrollableViewportSize(new Dimension(560, 430));
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_3 = new JLabel("Decision:");
		panel_1.add(lblNewLabel_3);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		rbtnAceptar = new JRadioButton("Aceptar");
		panel_2.add(rbtnAceptar);
		
		rbtnRechazar = new JRadioButton("Rechazar");
		panel_2.add(rbtnRechazar);
		
		seleccionRol = new ButtonGroup();
		seleccionRol.add(rbtnAceptar);
		seleccionRol.add(rbtnRechazar);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 3;
		panel.add(panel_3, gbc_panel_3);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					decisionEdicionesIngresadas(e);
				} catch (ValidationInputException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_3.add(btnAceptar);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 10));
		panel_3.add(rigidArea);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				setVisible(false);
			}
		});
		panel_3.add(btnCancelar);
		table.setDefaultEditor(Object.class, null);
	}

	protected void cargarDatos() {
		System.out.println("Cargando datos...");
		comboBoxEvento.addItem("");
		var evento = factory.getIEventoController();
		Set<DTEvento> listaE = evento.listarEventos();
		for (var e : listaE) {
			comboBoxEvento.addItem(e.nombre());
		}
		comboBoxEvento.setSelectedIndex(0);
		
	}
	
	protected void decisionEdicionesIngresadas(ActionEvent e) throws ValidationInputException {
		if (checkCampos()) {
			String edicionSeleccionada = (String) table.getValueAt(table.getSelectedRow(), 0);
			EstadoEdicion decision;
			if (rbtnAceptar.isSelected()) {
				decision = EstadoEdicion.ACEPTADA;
			} else {
				decision = EstadoEdicion.RECHAZADA;
			}
			var edicion = factory.getIEdicionController();
			try {
				edicion.decisionSeleccion(edicionSeleccionada, decision);
				JOptionPane.showMessageDialog(this, "Se ha cambiado el estado de la edición con exito", "Aceptar/Rechazar Edicion de Evento",
						JOptionPane.INFORMATION_MESSAGE);
				limpiarCampos();
				setVisible(false);
			} catch (ValidationInputException en) {
				util.ExceptionHandler.manageException(en);
			}
		}
	}
	
	
	private void limpiarCampos() {
		comboBoxEvento.setSelectedIndex(-1);
		comboBoxEvento.removeAllItems();
		table.clearSelection();
		table.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] { "Nombre", "Sigla", "Fecha Inicio", "Fecha Fin", "Fecha Alta", "Pais", "Ciudad" }));
		seleccionRol.clearSelection();
	}
	
	private boolean checkCampos() {
		if (comboBoxEvento.getSelectedIndex() <= 0 || table.getSelectedRow() == -1 || seleccionRol.getSelection() == null) {
			JOptionPane.showMessageDialog(this, "Quedan campos obligatorios por rellenar",
					"Registro a Edicion de Evento", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	
	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}

}
