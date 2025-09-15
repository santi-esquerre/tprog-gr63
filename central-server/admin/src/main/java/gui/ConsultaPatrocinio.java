/*package gui;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import exceptions.ValidationInputException;
import factory.Factory;

import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;

import datatypes.DTPatrocinio;

public class ConsultaPatrocinio extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textFecha;
	private JTextField textAporte;
	private JTextField textTipoRegistro;
	private JTextField textNivel;
	private JTextField textCupos;
	private JTextField textCodigo;
	private JComboBox comboBoxEventos;
	private JComboBox comboBoxPatrocinios;
	private Factory factory = Factory.get();

	public ConsultaPatrocinio() {
		setBounds(100, 100, 622, 386);
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setTitle("Consulta de Patrocinio");
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		var evento = factory.getIEventoController();
		var edicion = factory.getIEdicionController();
		
		JSplitPane splitPane = new JSplitPane();
		getContentPane().add(splitPane, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		splitPane.setLeftComponent(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{187, 152, 0};
		gbl_panel.rowHeights = new int[]{14, 0, 22, 227, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("Eventos:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		comboBoxEventos = new JComboBox();
		comboBoxEventos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.clearSelection();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				String eventoSeleccionado = (String) comboBoxEventos.getSelectedItem();
				Set<DTEdicion> listaEdiciones = null;
				if (eventoSeleccionado != null) {
					try {
						listaEdiciones = evento.mostrarEdiciones(eventoSeleccionado);
					} catch (ValidationInputException e1) {
						e1.printStackTrace();
					}
					for(var ed : listaEdiciones) {
						model.addRow(new Object[] {ed.nombre(), ed.sigla(), ed.fechaInicio(), ed.fechaFin()});
					}
				}
			}
		});
		GridBagConstraints gbc_comboBoxEventos = new GridBagConstraints();
		gbc_comboBoxEventos.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEventos.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEventos.gridx = 0;
		gbc_comboBoxEventos.gridy = 1;
		panel.add(comboBoxEventos, gbc_comboBoxEventos);
		
		JLabel lblNewLabel_1 = new JLabel("Ediciones:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		panel.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
				
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					if (row != -1) {
						String edicionSeleccionada = (String) table.getValueAt(row, 0);
						var patrocinios = edicion.mostrarPatrocinios(edicionSeleccionada);
						comboBoxPatrocinios.setSelectedIndex(-1);
						comboBoxPatrocinios.removeAllItems();
						for (var p : patrocinios) 
							comboBoxPatrocinios.addItem(p.institucion());
					}
				}
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Nombre", "Sigla", "Fecha Inicio", "Fecha Fin"
			}
		));
		
		table.setPreferredScrollableViewportSize(new Dimension(350, 192));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(10, 10, 10, 10));
		splitPane.setRightComponent(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Patrocinios:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		comboBoxPatrocinios = new JComboBox();
		GridBagConstraints gbc_comboBoxPatrocinios = new GridBagConstraints();
		gbc_comboBoxPatrocinios.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxPatrocinios.gridwidth = 2;
		gbc_comboBoxPatrocinios.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxPatrocinios.gridx = 0;
		gbc_comboBoxPatrocinios.gridy = 1;
		panel_1.add(comboBoxPatrocinios, gbc_comboBoxPatrocinios);
		
		JLabel lblNewLabel_4 = new JLabel("Fecha Alta:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 3;
		panel_1.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		textFecha = new JTextField();
		textFecha.setEditable(false);
		GridBagConstraints gbc_textFecha = new GridBagConstraints();
		gbc_textFecha.gridwidth = 2;
		gbc_textFecha.insets = new Insets(0, 0, 5, 0);
		gbc_textFecha.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFecha.gridx = 1;
		gbc_textFecha.gridy = 3;
		panel_1.add(textFecha, gbc_textFecha);
		textFecha.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Monto Aporte:");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 4;
		panel_1.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		textAporte = new JTextField();
		textAporte.setEditable(false);
		GridBagConstraints gbc_textAporte = new GridBagConstraints();
		gbc_textAporte.gridwidth = 2;
		gbc_textAporte.insets = new Insets(0, 0, 5, 0);
		gbc_textAporte.fill = GridBagConstraints.HORIZONTAL;
		gbc_textAporte.gridx = 1;
		gbc_textAporte.gridy = 4;
		panel_1.add(textAporte, gbc_textAporte);
		textAporte.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Tipo de Registro:");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridx = 0;
		gbc_lblNewLabel_9.gridy = 5;
		panel_1.add(lblNewLabel_9, gbc_lblNewLabel_9);
		
		textTipoRegistro = new JTextField();
		textTipoRegistro.setEditable(false);
		GridBagConstraints gbc_textTipoRegistro = new GridBagConstraints();
		gbc_textTipoRegistro.gridwidth = 2;
		gbc_textTipoRegistro.insets = new Insets(0, 0, 5, 0);
		gbc_textTipoRegistro.fill = GridBagConstraints.HORIZONTAL;
		gbc_textTipoRegistro.gridx = 1;
		gbc_textTipoRegistro.gridy = 5;
		panel_1.add(textTipoRegistro, gbc_textTipoRegistro);
		textTipoRegistro.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Nivel:");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 6;
		panel_1.add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		textNivel = new JTextField();
		textNivel.setEditable(false);
		GridBagConstraints gbc_textNivel = new GridBagConstraints();
		gbc_textNivel.gridwidth = 2;
		gbc_textNivel.insets = new Insets(0, 0, 5, 0);
		gbc_textNivel.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNivel.gridx = 1;
		gbc_textNivel.gridy = 6;
		panel_1.add(textNivel, gbc_textNivel);
		textNivel.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Cant. Cupos Gratis:");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 0;
		gbc_lblNewLabel_8.gridy = 7;
		panel_1.add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		textCupos = new JTextField();
		textCupos.setEditable(false);
		GridBagConstraints gbc_textCupos = new GridBagConstraints();
		gbc_textCupos.insets = new Insets(0, 0, 5, 0);
		gbc_textCupos.gridwidth = 2;
		gbc_textCupos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCupos.gridx = 1;
		gbc_textCupos.gridy = 7;
		panel_1.add(textCupos, gbc_textCupos);
		textCupos.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Código:");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 8;
		panel_1.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		textCodigo = new JTextField();
		textCodigo.setEditable(false);
		GridBagConstraints gbc_textCodigo = new GridBagConstraints();
		gbc_textCodigo.insets = new Insets(0, 0, 5, 0);
		gbc_textCodigo.gridwidth = 2;
		gbc_textCodigo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCodigo.gridx = 1;
		gbc_textCodigo.gridy = 8;
		panel_1.add(textCodigo, gbc_textCodigo);
		textCodigo.setColumns(10);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setPreferredSize(new Dimension(90, 30));
		btnCerrar.setMaximumSize(new Dimension(90, 30));
		btnCerrar.setMinimumSize(new Dimension(90, 30));
		GridBagConstraints gbc_btnCerrar = new GridBagConstraints();
		gbc_btnCerrar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCerrar.gridx = 0;
		gbc_btnCerrar.gridy = 11;
		panel_1.add(btnCerrar, gbc_btnCerrar);
	}
	
	protected void cargarDatos() {
		var evento = factory.getIEventoController();
		Set<DTEvento> listaEventos = evento.listarEventos();
		for (var event : listaEventos) {
			comboBoxEventos.addItem(event.nombre());
		}
	}
	
	private void limpiarCampos() {
		textFecha.setText("");
		textAporte.setText("");
		textTipoRegistro.setText("");
		textNivel.setText("");
		textCupos.setText("");
		textCodigo.setText("");
		table.clearSelection();
		table.setModel(new DefaultTableModel());
	}

}*/
