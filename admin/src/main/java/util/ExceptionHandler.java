package util;
import java.awt.Component;

import javax.swing.JOptionPane;

import exceptions.ValidationInputException;
public class ExceptionHandler {
	public static final boolean DEBUG = true;
	public static void log(Exception e) {
		e.printStackTrace();
	}
	
	public static void manageException(Component parent, Exception e) {
		if (e instanceof ValidationInputException) {
            JOptionPane.showMessageDialog(parent, e.getMessage(), "", JOptionPane.WARNING_MESSAGE);
        } else {
        	if (DEBUG) {
                JOptionPane.showMessageDialog(parent, "Ocurrió un error inesperado: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
        	}
        }
		e.printStackTrace();
	}
}
