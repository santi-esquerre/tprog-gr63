package models;

import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.table.AbstractTableModel;

public class GenericRecordTableModel<T> extends AbstractTableModel {
  private final Class<T> type;
  private final RecordComponent[] components;
  private final String[] headers;
  private final List<T> data = new ArrayList<>();

  public GenericRecordTableModel(Class<T> type) {
    this(type, null, null);
  }

  public GenericRecordTableModel(Class<T> type, List<T> initialData, String[] customHeaders) {
    this.type = Objects.requireNonNull(type, "type");
    if (!type.isRecord())
      throw new IllegalArgumentException(type.getName() + " no es un record");
    this.components = type.getRecordComponents();
    if (customHeaders != null) {
      if (customHeaders.length != components.length)
        throw new IllegalArgumentException("headers debe tener " + components.length + " columnas");
      this.headers = customHeaders;
    } else {
      this.headers = new String[components.length];
      for (int i = 0; i < components.length; i++) {
        this.headers[i] = capitalize(components[i].getName());
      }
    }
    if (initialData != null) {
      this.data.addAll(initialData);
    }
  }

  public void setData(List<T> rows) {
    data.clear();
    if (rows != null) data.addAll(rows);
    fireTableDataChanged();
  }

  public T get(int row) { return data.get(row); }

  @Override public int getRowCount() { return data.size(); }
  @Override public int getColumnCount() { return components.length; }
  @Override public String getColumnName(int col) { return headers[col]; }
  @Override public Class<?> getColumnClass(int col) {
    // tipo declarado del componente del record
    return components[col].getType();
  }
  @Override public boolean isCellEditable(int r, int c) { return false; }

  @Override
  public Object getValueAt(int row, int col) {
    try {
      Object rowObj = data.get(row);
      // accessors de record son métodos “componentN()”
      return components[col].getAccessor().invoke(rowObj);
    } catch (Exception e) {
      return null;
    }
  }

  private static String capitalize(String s) {
    if (s == null || s.isEmpty()) return s;
    return Character.toUpperCase(s.charAt(0)) + s.substring(1);
  }
}
