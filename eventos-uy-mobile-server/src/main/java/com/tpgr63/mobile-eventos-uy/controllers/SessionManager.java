package controllers;

import jakarta.servlet.http.HttpServletRequest;
import model.EstadoSesion;

public class SessionManager {
	public static void initSesion(HttpServletRequest request) {
		if(request.getSession().getAttribute("estado_sesion") == null) {
			request.getSession().setAttribute("estado_sesion", EstadoSesion.NO_LOGIN);
		}
}
}
