package controllers;

import jakarta.servlet.http.HttpServletRequest;

public class SessionManager {
	public static void initSesion(HttpServletRequest request) {
			if(request.getSession().getAttribute("estado_sesion") == null) {
				request.getSession().setAttribute("estado_sesion", model.EstadoSesion.NO_LOGIN);
			}
	}
}
