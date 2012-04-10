/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.tools;

import com.cpuz.domain.User;
import com.cpuz.domain.UserType;
import com.cpuz.misc.ConfigurationBean;
import com.cpuz.misc.PageBean;
import com.cpuz.model.UserModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class CheckTool {

	public CheckTool() {
	}

	public static void checkSession(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException, NamingException {
// Comprueba session y usuario. Incrementa contador de visitas
//        (si es nueva session) y comprueba usuario.
		HttpSession session = request.getSession(false);
		if (request.getSession(false) == null) {
			session = request.getSession();
			//   session.getAttribute("closeSession");
			session.setAttribute("closeSession", false);
			// } else {
			//    session.getAttribute("closeSession");
		}
		if (session.isNew()) {
			// Se incrementa el contador de visitas
			ConfigurationBean configuration = new ConfigurationBean();
			// No se incrementa el contador de visitas si la página es novisit.jsp o cerrarsession.jsp
			if (request.getRequestURI().endsWith("novisit.jsp")
					|| request.getRequestURI().endsWith("logout.jsp")) {
				configuration.incrementAccesses(0);
			} else {
				configuration.incrementAccesses(1);
			}
			if (configuration.isRegistered()) {
				session.setAttribute("visitas", configuration.getAccesses());
			} else {
				session.setAttribute("visitas", 0);
			}
			// Se inicializan atributos de control de usuarios
			setUserSessionAttributes(session, "", "", 0, 0);
			// Se inicializan atributos de gestáón de páginas del grupo 'index'
			session.setAttribute("indexSolapa1", "noticias/novedades.jspf");
			session.setAttribute("indexSolapa2", "noticias/terecomendamos.jspf");
			session.setAttribute("indexSolapa3", "juntanoticiaslistar.jsp");
		}
	}
// fin de comprobación de session

	public static void checkUserCredentials(HttpServletRequest request) throws SQLException {

		HttpSession session = request.getSession(false);
		UserModel usersModel = new UserModel();
		if (request.getUserPrincipal() != null) {
			List<User> users = usersModel.getNewsRecords(UserType.ADMIN, "usu_user = '"
					+ request.getUserPrincipal().getName() + "'");
			if (users != null && users.size() == 1) {
				setUserSessionAttributes(session,
						users.get(0).getUser(),
						users.get(0).getName(),
						users.get(0).getCategory(),
						users.get(0).getStatus());
			} else {
				setUserSessionAttributes(session, "", "", 0, 0);
			}
		} else {
			setUserSessionAttributes(session, "", "", 0, 0);
		}
	}

// fin de comprobación de session y usuario
	static public String checkPageAndPermission(HttpServletRequest request,
			HttpServletResponse response, String id, String page)
			throws ServletException, IOException, SQLException {
		// Se comprueba la autorización para el uso de la página
		HttpSession session = request.getSession();
		PageBean pageBean = new PageBean();
		if (session.getAttribute("user") == null) {
			session.setAttribute("user", "");
		}
		pageBean.setUser(session.getAttribute("user").toString());
		pageBean.setPágina(id);
		try {
			if (!pageBean.autorizada()) {
// La página no está autorizada explícitamente o es de uso general.
				page = "/WEB-INF/pages/nonesuch.jsp";
				request.setAttribute("nonesuch", id);
			}
		} catch (NamingException ex) {
//            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
			page = "/WEB-INF/pages/nonesuch.jsp";
			request.setAttribute("nonesuch", id);
		}
		// Fin de la comprobación de autorización de uso de la página
		switch (id) {
			case "/pages/juntanoticiaslistar.jsp":
				session.setAttribute("indexSolapa3", "juntanoticiaslistar.jsp");
				page = "/WEB-INF/pages/index.jsp";
				break;
			case "/docs/juntanoticiasseleccionar.jsp":
				session.setAttribute("indexSolapa3", "juntanoticiasseleccionar.jsp");
				page = "/WEB-INF/pages/index.jsp";
				break;
			case "/docs/juntanoticiasseleccionados.jsp":
				session.setAttribute("indexSolapa3", "juntanoticiasseleccionados.jsp");
				page = "/WEB-INF/pages/index.jsp";
				break;
		}
		return page;
	}

	static public void setUserSessionAttributes(HttpSession session,
			String user, String userName, Integer userCategory, Integer userEstado) {
		session.setAttribute("user", user);
		session.setAttribute("userName", userName);
		session.setAttribute("userCategory", userCategory);
		session.setAttribute("userEstado", userEstado);
	}
}
