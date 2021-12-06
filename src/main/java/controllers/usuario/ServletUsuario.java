package controllers.usuario;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;
import services.ServicioUsuario;

@WebServlet("/usuarios/index.do")
public class ServletUsuario extends HttpServlet implements Servlet {

	private static final long serialVersionUID = -3441280170145178666L;
	private ServicioUsuario servicioUsuario;

	@Override
	public void init() throws ServletException {
		super.init();
		this.servicioUsuario = new ServicioUsuario();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Usuario> usuarios = servicioUsuario.listar();
		req.setAttribute("usuarios", usuarios);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/usuarios/index.jsp");
		dispatcher.forward(req, resp);

	}
}
