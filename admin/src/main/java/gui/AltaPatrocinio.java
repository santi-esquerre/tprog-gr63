package gui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import factory.Factory;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Set;
import java.awt.event.ActionEvent;

import datatypes.DTEdicion;
import datatypes.DTEvento;
import datatypes.DTInstitucion;
import datatypes.NivelPatrocinio;
import exceptions.CostoRegistrosGratuitosException;
import exceptions.ExistePatrocinioException;
import exceptions.ValidationInputException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.Year;
import java.awt.Dimension;

public class AltaPatrocinio extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textAporte;
	private JTextField textCantGratuitos;
	private JTextField textCodigo;
	private JComboBox comboBoxEvento;
	private JComboBox comboBoxInstitucion;
	private JComboBox comboBoxTipoRegistro;
	private JComboBox<Integer> comboBoxDia;
	private JComboBox<String> comboBoxMes;
	private JComboBox<Integer> comboBoxAnio;
	private JRadioButton rdbtnPlatino;
	private JRadioButton rdbtnOro;
	private JRadioButton rdbtnPlata;
	private JRadioButton rdbtnBronce;
	private Factory factory = Factory.get();
	private Integer[] dias;
	private String[] meses;
	private Integer[] anio;
	
	public AltaPatrocinio() {
		setBounds(100, 100, 687, 414);
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setTitle("Alta Patrocinio");
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		var evento = factory.getIEventoController();
		var institucion = factory.getIInstitucionController();
		var edicion = factory.getIEdicionController();
		
        dias = new Integer[31];
        for (int i = 0; i < 31; i++) dias[i] = i + 1;
		
		meses = new String[] {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre"
				, "Noviembre", "Diciembre"};
		
		int anioActual = Year.now().getValue();
		int anioAIngresar = anioActual; 
		anio = new Integer[121];
		for (int i = 0; i <= 120; i++) {
			anio[i] = anioAIngresar;
			anioAIngresar--;
		}
		
		JSplitPane splitPane = new JSplitPane();
		getContentPane().add(splitPane, BorderLayout.NORTH);
		
		
		JPanel panelIzq = new JPanel();
		panelIzq.setBorder(new EmptyBorder(10, 10, 10, 10));
		splitPane.setLeftComponent(panelIzq);
		GridBagLayout gbl_panelIzq = new GridBagLayout();
		gbl_panelIzq.columnWidths = new int[]{259, 58, 0};
		gbl_panelIzq.rowHeights = new int[]{0, 0, 0, 0, 0, 197, 0};
		gbl_panelIzq.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panelIzq.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelIzq.setLayout(gbl_panelIzq);
		
		JLabel lblNewLabel = new JLabel("Eventos:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panelIzq.add(lblNewLabel, gbc_lblNewLabel);
		
		comboBoxEvento = new JComboBox();
		GridBagConstraints gbc_comboBoxEvento = new GridBagConstraints();
		gbc_comboBoxEvento.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEvento.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEvento.gridx = 0;
		gbc_comboBoxEvento.gridy = 1;
		panelIzq.add(comboBoxEvento, gbc_comboBoxEvento);
		
		JLabel lblNewLabel_1 = new JLabel("Instituciones:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panelIzq.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		comboBoxInstitucion = new JComboBox();
		GridBagConstraints gbc_comboBoxInstitucion = new GridBagConstraints();
		gbc_comboBoxInstitucion.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxInstitucion.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxInstitucion.gridx = 0;
		gbc_comboBoxInstitucion.gridy = 3;
		panelIzq.add(comboBoxInstitucion, gbc_comboBoxInstitucion);
		
		JLabel lblNewLabel_2 = new JLabel("Ediciones:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 4;
		panelIzq.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 5;
		panelIzq.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					if (row != -1) {
						String edicionSeleccionada = (String) table.getValueAt(row, 0);
						var tiposRegistro = edicion.mostrarTiposDeRegistro(edicionSeleccionada);
						comboBoxTipoRegistro.setSelectedIndex(-1);
						comboBoxTipoRegistro.removeAllItems();
						for (var tr : tiposRegistro) 
							comboBoxTipoRegistro.addItem(tr.nombre());
					}
				}
			}
		});
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
		                new Object[][]{},
		                new String[]{"Nombre", "Sigla", "Fecha Inicio", "Fecha Fin"}
		        )  );
		
		scrollPane.setViewportView(table);
		table.setPreferredScrollableViewportSize(new Dimension(350, 192));
		table.setDefaultEditor(Object.class, null);
		JPanel panelDer = new JPanel();
		panelDer.setBorder(new EmptyBorder(30, 15, 10, 10));
		splitPane.setRightComponent(panelDer);
		GridBagLayout gbl_panelDer = new GridBagLayout();
		gbl_panelDer.columnWidths = new int[]{58, 56, 66, 73};
		gbl_panelDer.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelDer.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
		gbl_panelDer.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panelDer.setLayout(gbl_panelDer);
		
		JLabel lblNewLabel_3 = new JLabel("Aporte:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 0;
		panelDer.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		textAporte = new JTextField();
		GridBagConstraints gbc_textAporte = new GridBagConstraints();
		gbc_textAporte.gridwidth = 3;
		gbc_textAporte.insets = new Insets(0, 0, 5, 0);
		gbc_textAporte.fill = GridBagConstraints.HORIZONTAL;
		gbc_textAporte.gridx = 1;
		gbc_textAporte.gridy = 0;
		panelDer.add(textAporte, gbc_textAporte);
		textAporte.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Tipo de Registro:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 1;
		panelDer.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		comboBoxTipoRegistro = new JComboBox();
		GridBagConstraints gbc_comboBoxTipoRegistro = new GridBagConstraints();
		gbc_comboBoxTipoRegistro.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxTipoRegistro.gridwidth = 4;
		gbc_comboBoxTipoRegistro.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxTipoRegistro.gridx = 0;
		gbc_comboBoxTipoRegistro.gridy = 2;
		panelDer.add(comboBoxTipoRegistro, gbc_comboBoxTipoRegistro);
		
		rdbtnPlatino = new JRadioButton("Platino");
		GridBagConstraints gbc_rdbtnPlatino = new GridBagConstraints();
		gbc_rdbtnPlatino.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnPlatino.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnPlatino.gridx = 0;
		gbc_rdbtnPlatino.gridy = 3;
		panelDer.add(rdbtnPlatino, gbc_rdbtnPlatino);
		
		rdbtnOro = new JRadioButton("Oro");
		GridBagConstraints gbc_rdbtnOro = new GridBagConstraints();
		gbc_rdbtnOro.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnOro.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnOro.gridx = 1;
		gbc_rdbtnOro.gridy = 3;
		panelDer.add(rdbtnOro, gbc_rdbtnOro);
		
		rdbtnPlata = new JRadioButton("Plata");
		GridBagConstraints gbc_rdbtnPlata = new GridBagConstraints();
		gbc_rdbtnPlata.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnPlata.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnPlata.gridx = 2;
		gbc_rdbtnPlata.gridy = 3;
		panelDer.add(rdbtnPlata, gbc_rdbtnPlata);
		
		rdbtnBronce = new JRadioButton("Bronce");
		GridBagConstraints gbc_rdbtnBronce = new GridBagConstraints();
		gbc_rdbtnBronce.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnBronce.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnBronce.gridx = 3;
		gbc_rdbtnBronce.gridy = 3;
		panelDer.add(rdbtnBronce, gbc_rdbtnBronce);
		
		ButtonGroup seleccionNivelPatrocinio = new ButtonGroup();
		seleccionNivelPatrocinio.add(rdbtnPlatino);
		seleccionNivelPatrocinio.add(rdbtnOro);
		seleccionNivelPatrocinio.add(rdbtnPlata);
		seleccionNivelPatrocinio.add(rdbtnBronce);
		
		JLabel lblNewLabel_5 = new JLabel("Cant. de registros gratuitos:");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_5.gridwidth = 2;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 4;
		panelDer.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		textCantGratuitos = new JTextField();
		GridBagConstraints gbc_textCantGratuitos = new GridBagConstraints();
		gbc_textCantGratuitos.insets = new Insets(0, 0, 5, 0);
		gbc_textCantGratuitos.gridwidth = 2;
		gbc_textCantGratuitos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCantGratuitos.gridx = 2;
		gbc_textCantGratuitos.gridy = 4;
		panelDer.add(textCantGratuitos, gbc_textCantGratuitos);
		textCantGratuitos.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Código de Patrocinio:");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_6.gridwidth = 2;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 5;
		panelDer.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		textCodigo = new JTextField();
		GridBagConstraints gbc_textCodigo = new GridBagConstraints();
		gbc_textCodigo.insets = new Insets(0, 0, 5, 0);
		gbc_textCodigo.gridwidth = 2;
		gbc_textCodigo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCodigo.gridx = 2;
		gbc_textCodigo.gridy = 5;
		panelDer.add(textCodigo, gbc_textCodigo);
		textCodigo.setColumns(10);
		
		comboBoxDia = new JComboBox<>(dias);
		GridBagConstraints gbc_comboBoxDia = new GridBagConstraints();
		gbc_comboBoxDia.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxDia.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxDia.gridx = 0;
		gbc_comboBoxDia.gridy = 6;
		panelDer.add(comboBoxDia, gbc_comboBoxDia);
		
		comboBoxMes = new JComboBox<>(meses);
		GridBagConstraints gbc_comboBoxMes = new GridBagConstraints();
		gbc_comboBoxMes.gridwidth = 2;
		gbc_comboBoxMes.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxMes.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxMes.gridx = 1;
		gbc_comboBoxMes.gridy = 6;
		panelDer.add(comboBoxMes, gbc_comboBoxMes);
		
		comboBoxAnio = new JComboBox<>(anio);
		GridBagConstraints gbc_comboBoxAnio = new GridBagConstraints();
		gbc_comboBoxAnio.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxAnio.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxAnio.gridx = 3;
		gbc_comboBoxAnio.gridy = 6;
		panelDer.add(comboBoxAnio, gbc_comboBoxAnio);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 4;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 7;
		panelDer.add(panel, gbc_panel);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaPatrocinioActionPerformed(e);
			}
		});
		panel.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				setVisible(false);
			}
		});
		panel.add(btnCancelar);
		
		comboBoxEvento.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				table.clearSelection();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				String eventoSeleccionado = (String) comboBoxEvento.getSelectedItem();
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
		
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				limpiarCampos();
			}
		});
	}
	
	private boolean checkCampos() {
		if (textAporte.getText().isEmpty() || textCantGratuitos.getText().isEmpty() || textCodigo.getText().isEmpty() || comboBoxDia.getSelectedIndex() == -1 | comboBoxMes.getSelectedIndex() == -1 || comboBoxAnio.getSelectedIndex() == -1 || comboBoxEvento.getSelectedIndex() == -1 || comboBoxInstitucion.getSelectedIndex() == -1 || comboBoxTipoRegistro.getSelectedIndex() == -1 || (!rdbtnPlatino.isSelected() && !rdbtnOro.isSelected() && !rdbtnPlata.isSelected() && !rdbtnBronce.isSelected()) || table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "No se puede tener campos vacios", "Alta de Patrocinio", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			Float.parseFloat(textAporte.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El campo Aporte debe ser un número", "Alta de Patrocinio", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			Integer.parseInt(textCantGratuitos.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El campo Cantidad de registros gratuitos debe ser un número entero", "Alta de Patrocinio", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	protected void altaPatrocinioActionPerformed(ActionEvent e) {
		if (checkCampos()) {
			try {
				String institucionSeleccionada = (String) comboBoxInstitucion.getSelectedItem();
				String edicionSeleccionada = (String) table.getValueAt(table.getSelectedRow(), 0);
				String aporte = textAporte.getText();
				String tipoRegistro = (String) comboBoxTipoRegistro.getSelectedItem();
				String cantGratuitos = textCantGratuitos.getText();
				String codigo = textCodigo.getText();
				NivelPatrocinio nivelPatrocinio;
				if (rdbtnPlatino.isSelected()) {
					nivelPatrocinio = NivelPatrocinio.PLATINO;
				} else if (rdbtnOro.isSelected()) {
					nivelPatrocinio = NivelPatrocinio.ORO;
				} else if (rdbtnPlata.isSelected()) {
					nivelPatrocinio = NivelPatrocinio.PLATA;
				} else {
					nivelPatrocinio = NivelPatrocinio.BRONCE;
				}
				LocalDate fecha = LocalDate.of((Integer) comboBoxAnio.getSelectedItem(), comboBoxMes.getSelectedIndex() + 1, (Integer) comboBoxDia.getSelectedItem());
				var edicion = factory.getIEdicionController();
				edicion.altaPatrocinio(fecha, edicionSeleccionada, institucionSeleccionada, Float.parseFloat(aporte), tipoRegistro, Integer.parseInt(cantGratuitos), codigo, nivelPatrocinio);
				JOptionPane.showMessageDialog(this, "Patrocinio registrado correctamente", "Alta de Patrocinio", JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (ExistePatrocinioException ep) {
				util.ExceptionHandler.manageException(this, ep);
			}
			catch (CostoRegistrosGratuitosException crg) {
				util.ExceptionHandler.manageException(this, crg);
			}
			limpiarCampos();
			setVisible(false);
		}
		
	}
	
	protected void cargarDatos() {
		var evento = factory.getIEventoController();
		var institucion = factory.getIInstitucionController();
		var edicion = factory.getIEdicionController();
		Set<DTEvento> listaEventos = evento.listarEventos();
		for (var event : listaEventos) {
			comboBoxEvento.addItem(event.nombre());
		}
		
		Set<DTInstitucion> listaInstituciones = institucion.listarInstituciones();
		for (var inst : listaInstituciones) {
			comboBoxInstitucion.addItem(inst.nombre());
		}
	}
	
	private void limpiarCampos() {
		textAporte.setText("");
		textCantGratuitos.setText("");
		textCodigo.setText("");
		comboBoxEvento.setSelectedIndex(-1);
		comboBoxInstitucion.setSelectedIndex(-1);
		comboBoxTipoRegistro.setSelectedIndex(-1);
		comboBoxEvento.removeAllItems();
		comboBoxInstitucion.removeAllItems();
		comboBoxTipoRegistro.removeAllItems();
		comboBoxDia.setSelectedIndex(-1);
		comboBoxMes.setSelectedIndex(-1);
		comboBoxAnio.setSelectedIndex(-1);
		rdbtnPlatino.setSelected(false);
		rdbtnOro.setSelected(false);
		rdbtnPlata.setSelected(false);
		rdbtnBronce.setSelected(false);
		table.clearSelection();
		table.setModel(new DefaultTableModel(
			    new Object[][] {}, 
			    new String[] { "Nombre", "Sigla", "Fecha Inicio", "Fecha Fin" }
			));
	}
}
