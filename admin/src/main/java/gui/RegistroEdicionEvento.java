package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import interfaces.IEdicionController;
import interfaces.IUsuarioController;

public class RegistroEdicionEvento extends JInternalFrame {
	private JTable table;
	String seleccionado = null; // Variable que se usará para tomar el valor del tipo de registro seleccionado
	public RegistroEdicionEvento(IEdicionController edicionController, IUsuarioController usuarioController) {
	
		setBounds(100, 100, 455, 461);
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{104, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 77, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
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
		gbl_panelComboBox.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panelComboBox.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelComboBox.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelComboBox.setLayout(gbl_panelComboBox);
		
		JLabel lblNewLabel = new JLabel("Eventos:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panelComboBox.add(lblNewLabel, gbc_lblNewLabel);
		
		JComboBox comboBoxEvento = new JComboBox();
		GridBagConstraints gbc_comboBoxEvento = new GridBagConstraints();
		gbc_comboBoxEvento.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEvento.gridwidth = 2;
		gbc_comboBoxEvento.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEvento.gridx = 0;
		gbc_comboBoxEvento.gridy = 1;
		panelComboBox.add(comboBoxEvento, gbc_comboBoxEvento);
		
		JLabel lblNewLabel_1 = new JLabel("Asistentes:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panelComboBox.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JComboBox comboBoxAsistente = new JComboBox();
		GridBagConstraints gbc_comboBoxAsistente = new GridBagConstraints();
		gbc_comboBoxAsistente.insets = new Insets(0, 0, 0, 5);
		gbc_comboBoxAsistente.gridwidth = 2;
		gbc_comboBoxAsistente.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxAsistente.gridx = 0;
		gbc_comboBoxAsistente.gridy = 3;
		panelComboBox.add(comboBoxAsistente, gbc_comboBoxAsistente);
		
		JPanel panelTabla = new JPanel();
		GridBagConstraints gbc_panelTabla = new GridBagConstraints();
		gbc_panelTabla.insets = new Insets(0, 0, 5, 0);
		gbc_panelTabla.fill = GridBagConstraints.BOTH;
		gbc_panelTabla.gridx = 0;
		gbc_panelTabla.gridy = 1;
		panel.add(panelTabla, gbc_panelTabla);
		GridBagLayout gbl_panelTabla = new GridBagLayout();
		gbl_panelTabla.columnWidths = new int[]{184, 241, 0};
		gbl_panelTabla.rowHeights = new int[]{0, 219, 0};
		gbl_panelTabla.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelTabla.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelTabla.setLayout(gbl_panelTabla);
		
		JLabel lblNewLabel_2 = new JLabel("Ediciones:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		panelTabla.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panelTabla.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int fila = table.getSelectedRow();
					if (fila != -1) {
						Object valor = table.getValueAt(fila, 0);
						Window parent = SwingUtilities.getWindowAncestor(table);
						TipoRegistroSeleccionado dialog = new TipoRegistroSeleccionado(parent, "Tipo de Registro", valor.toString(), edicionController);
						dialog.setVisible(true);
						seleccionado = dialog.getSelectedValue();
					}
				}
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"Nombre", "Sigla", "Fecha Inicio", "Fecha Fin", "Pais", "Ciudad"
			}
		));
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setDefaultEditor(Object.class, null);
		
		JPanel panelBtn = new JPanel();
		GridBagConstraints gbc_panelBtn = new GridBagConstraints();
		gbc_panelBtn.fill = GridBagConstraints.VERTICAL;
		gbc_panelBtn.gridx = 0;
		gbc_panelBtn.gridy = 2;
		panel.add(panelBtn, gbc_panelBtn);
		panelBtn.setLayout(new BoxLayout(panelBtn, BoxLayout.X_AXIS));
		
		JButton btnAceptar = new JButton("Aceptar");
		panelBtn.add(btnAceptar);
		panelBtn.add(Box.createRigidArea(new Dimension(20, 10)));
		JButton btnCancelar = new JButton("Cancelar");
		panelBtn.add(btnCancelar);
	}

}
