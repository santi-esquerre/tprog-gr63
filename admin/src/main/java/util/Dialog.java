package util;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class Dialog {
	public static void showSuccess(JComponent parent, String message) {
		JOptionPane.showMessageDialog(parent, message, "", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showSuccess(JComponent parent) {
		showSuccess(parent, "Operación realizada con éxito.");
	}
	public static void showInfo(JComponent parent, String message) {
		JOptionPane.showMessageDialog(parent, message, "", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showWarning(JComponent parent, String message) {
		JOptionPane.showMessageDialog(parent, message, "", JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showError(JComponent parent, String message) {
		JOptionPane.showMessageDialog(parent, message, "", JOptionPane.ERROR_MESSAGE);
	}
	
	public static int showConfirm(JComponent parent, String message) {
		return JOptionPane.showConfirmDialog(parent, message, "Confirmar", JOptionPane.YES_NO_OPTION);
	}
	
	public static String showInput(JComponent parent, String message) {
		return JOptionPane.showInputDialog(parent, message);
	}
}
