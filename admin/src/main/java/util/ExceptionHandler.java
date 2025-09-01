package util;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import exceptions.ValidationInputException;
public class ExceptionHandler {
	public static final boolean DEBUG = true;
	public static void log(Exception e) {
		e.printStackTrace();
	}
	
	public static void manageException(JFrame parent, Exception e) {
		if (e instanceof ValidationInputException) {
            Dialog.showWarning(parent, e.getMessage());
        }else {
        	if (DEBUG) {
        		Dialog.showError(parent, "Ocurrió un error inesperado: " + e.getMessage());
        	}
        }
		e.printStackTrace();
	}
	
	public static void manageException(Exception e) {
		manageException(null, e);
	}
}
