package services;

import java.util.List;

import model.Usuario;
import persistence.commons.DAOFactory;

public class ServicioUsuario {
	
	public List<Usuario> listar() {
		return DAOFactory.getUsuarioDAO().encontrarTodos();
	}
	
}
