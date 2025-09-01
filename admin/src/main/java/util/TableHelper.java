package util;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public final class TableHelper {
    private TableHelper() {}

    public static JTable nonEditableTable(String... columnNames) {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        return table;
    }

    public static JScrollPane scroll(JTable table) {
        return new JScrollPane(table);
    }
}
