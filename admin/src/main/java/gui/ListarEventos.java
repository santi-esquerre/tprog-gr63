package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import interfaces.IEventoController;
import interfaces.IReceiver;
import util.Dialog;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Dialog.ModalityType;

public class ListarEventos extends JDialog {

	private static final long serialVersionUID = 1L;
	private IReceiver parentReceiver;
	private JTable table;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public ListarEventos(IReceiver source, IEventoController eventoController) {
		super(null, "Listado de Eventos", ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 466, 396);
		
		getContentPane().setLayout(new BorderLayout());
		{
			JScrollPane scrollPane = new JScrollPane();
			
			
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				table.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Nombre", "Sigla", "Descripci\u00F3n", "Fecha de Alta"
					}
				) {
					Class[] columnTypes = new Class[] {
						String.class, String.class, String.class, String.class
					};
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
					boolean[] columnEditables = new boolean[] {
						false, false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
				DefaultTableModel model = (DefaultTableModel) table.getModel();

				scrollPane.setViewportView(table);
				
				model.setDataVector(eventoController.listarEventos().stream().map(e -> new Object[] {
						e.nombre(), e.sigla(), e.descripcion(), e.fechaAlta()
				}).toArray(Object[][]::new), new String[] {
						"Nombre", "Sigla", "Descripci\u00F3n", "Fecha de Alta"
					});
				
				if(model.getRowCount() == 0) {
					Dialog.showWarning("No hay eventos para mostrar");
					dispose();
				}
			}
		}
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		            source.receive(table.getValueAt(row, 0).toString());
		            dispose();
		        }
		    }
		});
	}

}
