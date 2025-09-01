package util;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Dialog {
	public static void showSuccess(JFrame parent, String message) {
		JOptionPane.showMessageDialog(parent, message, "", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showSuccess(JFrame parent) {
		showSuccess(parent, "Operación realizada con éxito.");
	}
	public static void showInfo(JFrame parent, String message) {
		JOptionPane.showMessageDialog(parent, message, "", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showWarning(JFrame parent, String message) {
		JOptionPane.showMessageDialog(null, message, "", JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showError(JFrame parent, String message) {
		JOptionPane.showMessageDialog(parent, message, "", JOptionPane.ERROR_MESSAGE);
	}
	
	public static int showConfirm(JFrame parent, String message) {
		return JOptionPane.showConfirmDialog(parent, message, "Confirmar", JOptionPane.YES_NO_OPTION);
	}
	
	public static String showInput(JFrame parent, String message) {
		return JOptionPane.showInputDialog(parent, message);
	}

	public static void showWarning(String string) {
		showWarning(null, string);
	}
	
	public static void showError(String string) {
		showError(null, string);
	}
	
	public static int showConfirm(String string) {
		return showConfirm(null, string);
	}
	
	public static String showInput(String string) {
		return showInput(null, string);
	}
	
	public static void showInfo(String string) {
		showInfo(null, string);
	}
	
	public static void showSuccess(String string) {
		showSuccess(null, string);
	}
}
