package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;
import persistence.UsuarioDAO;
import persistence.commons.Conexion;
import persistence.commons.DAOFactory;
import persistence.commons.MissingDataException;

public class UsuarioDAOImpl implements UsuarioDAO {

	public List<Usuario> encontrarTodos() {
		try {
			String sql = "SELECT Visitantes.id,visitantes.nombre,Visitantes.dineroDisponible,Visitantes.tiempoDisponible,TipoAtraccion.descripcion FROM Visitantes JOIN TipoAtraccion on Visitantes.tipo_id = TipoAtraccion.id";
			Connection conn = Conexion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Usuario> usuarios = new ArrayList<Usuario>();
			while (resultados.next()) {
				usuarios.add(aUsuario(resultados));
			}

			return usuarios;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int contarTodos() {
		try {
			String sql = "SELECT COUNT(1) AS TOTAL FROM Visitantes";
			Connection conn = Conexion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			resultados.next();
			int total = resultados.getInt("TOTAL");

			return total;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int insertar(Usuario usuario) {
		try {
			String sql = "INSERT INTO Visitantes (nombre,dineroDisponible,tiempoDisponible,tipo_id) VALUES (?,?, ?, (SELECT id FROM TipoAtraccion WHERE descripcion = ?))";
			Connection conn = Conexion.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, usuario.getNombre());
			statement.setDouble(2, usuario.getDineroDisponible());
			statement.setDouble(3, usuario.getTiempoDisponible());
			statement.setString(4, usuario.getTipoPreferido());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int actualizarDatos(Usuario usuario) {
		try {
			String sql = "UPDATE Visitantes SET nombre = ?,dineroDisponible = ?, tiempoDisponible = ?,tipo_id = (SELECT id FROM TipoAtraccion WHERE descripcion = ?) WHERE id = ?";
			Connection conn = Conexion.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, usuario.getNombre());
			statement.setDouble(2, usuario.getDineroDisponible());
			statement.setDouble(3, usuario.getTiempoDisponible());
			statement.setString(4, usuario.getTipoPreferido());
			statement.setInt(5, usuario.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int borrar(Usuario usuario) {
		try {
			String sql = "DELETE FROM Visitantes WHERE id = ?";
			Connection conn = Conexion.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, usuario.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public Usuario encontrarPorNombre(String nombre) {
		try {
			String sql = "SELECT Visitantes.id,visitantes.nombre,Visitantes.dineroDisponible,Visitantes.tiempoDisponible,TipoAtraccion.descripcion FROM Visitantes JOIN TipoAtraccion on Visitantes.tipo_id = TipoAtraccion.id WHERE nombre = ?";
			Connection conn = Conexion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombre);
			ResultSet resultados = statement.executeQuery();

			Usuario user = null;

			if (resultados.next()) {
				user = aUsuario(resultados);
			}

			return user;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Usuario aUsuario(ResultSet resultados) throws SQLException {
		ItineraioDAOImpl itiDAO = DAOFactory.getItinerarioDaoImpl();
		return new Usuario(resultados.getString(2), resultados.getInt(3), resultados.getDouble(4),
				resultados.getString(5), resultados.getInt(1), itiDAO.cargarComprasUsuario(resultados.getInt(1)));
	}
}
