package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Atraccion;
import persistence.AtraccionDAO;
import persistence.commons.Conexion;
import persistence.commons.MissingDataException;

public class AtraccionDAOImpl implements AtraccionDAO {

	public List<Atraccion> encontrarTodos() {
		try {
			String sql = "SELECT Atraccion.id,Atraccion.nombre,Atraccion.costo,Atraccion.tiempo,Atraccion.cupo, TipoAtraccion.descripcion FROM Atraccion JOIN TipoAtraccion on Atraccion.tipo_id = TipoAtraccion.id";
			Connection conn = Conexion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Atraccion> atraccions = new ArrayList<Atraccion>();
			while (resultados.next()) {
				atraccions.add(aAtraccion(resultados));
			}

			return atraccions;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int contarTodos() {
		try {
			String sql = "SELECT COUNT(1) AS TOTAL FROM Atraccion";
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

	public int insertar(Atraccion Atraccion) {
		try {
			String sql = "INSERT INTO Atraccion (nombre,costo,tiempo,cupo,tipo_id) VALUES (?,?, ?, ?,(SELECT id FROM TipoAtraccion WHERE descripcion = ?))";
			Connection conn = Conexion.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, Atraccion.getNombre());
			statement.setDouble(2, Atraccion.getCosto());
			statement.setDouble(3, Atraccion.getTiempo());
			statement.setInt(4, Atraccion.getCupo());
			statement.setString(5, Atraccion.getTipo());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int actualizarDatos(Atraccion producto) {
		try {
			String sql = "UPDATE Atraccion SET nombre = ?,costo = ?, tiempo = ?, cupo = ?, tipo_id = (SELECT id FROM TipoAtraccion WHERE descripcion = ?) WHERE nombre = ?";
			Connection conn = Conexion.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, producto.getNombre());
			statement.setDouble(2, producto.getCosto());
			statement.setDouble(3, producto.getTiempo());
			statement.setInt(4, producto.getCupo());
			statement.setString(5, producto.getTipo());
			statement.setString(6, producto.getNombre());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int borrar(Atraccion Atraccion) {
		try {
			String sql = "DELETE FROM Atraccion WHERE id = ?";
			Connection conn = Conexion.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, Atraccion.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	public Atraccion encontrarPorNombre(String nombre) {
		try {
			String sql = "SELECT Atraccion.id,Atraccion.nombre,Atraccion.costo,Atraccion.tiempo,Atraccion.cupo, TipoAtraccion.descripcion FROM Atraccion JOIN TipoAtraccion on Atraccion.tipo_id = TipoAtraccion.id WHERE nombre = ?";
			Connection conn = Conexion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombre);
			ResultSet resultados = statement.executeQuery();

			Atraccion atrac = null;

			if (resultados.next()) {
				atrac = aAtraccion(resultados);
			}

			return atrac;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	public Atraccion encontrarPorID(int ID) {
		try {
			String sql = "SELECT Atraccion.id,Atraccion.nombre,Atraccion.costo,Atraccion.tiempo,Atraccion.cupo, TipoAtraccion.descripcion FROM Atraccion JOIN TipoAtraccion on Atraccion.tipo_id = TipoAtraccion.id WHERE Atraccion.id = ?";
			Connection conn = Conexion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, ID);
			ResultSet resultados = statement.executeQuery();

			Atraccion atrac = null;

			if (resultados.next()) {
				atrac = aAtraccion(resultados);
			}

			return atrac;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	private Atraccion aAtraccion(ResultSet resultados) throws SQLException {
		return new Atraccion(resultados.getInt(1),resultados.getString(2), resultados.getInt(3), resultados.getDouble(4),
				resultados.getInt(5),resultados.getString(6));
	}
}
