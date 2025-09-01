package util;

import javax.swing.JComponent;

import exceptions.ValidationInputException;
public class ExceptionHandler {
	public static final boolean DEBUG = true;
	public static void log(Exception e) {
		e.printStackTrace();
	}
	
	public static void manageException(JComponent parent, Exception e) {
		if (e instanceof ValidationInputException) {
            Dialog.showWarning(parent, e.getMessage());
        }else {
        	if (DEBUG) {
        		Dialog.showError(parent, "Ocurrió un error inesperado: " + e.getMessage());
        	}
        }
		e.printStackTrace();
	}
}
