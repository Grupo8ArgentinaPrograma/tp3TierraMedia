package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Atraccion;
import model.Ofertable;
import model.Usuario;
import persistence.commons.Conexion;
import persistence.commons.MissingDataException;

public class ItineraioDAOImpl {

	public int insertarEnItinerario(Ofertable producto, Usuario visitante) {
		try {
			String sql = "INSERT INTO Itinerario (visitante_id, tipo, promocion_id, atraccion_id) VALUES (?,?, ?, ?)";
			Connection conn = Conexion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			if (producto instanceof Atraccion) {
				statement.setInt(1, visitante.getId());
				statement.setString(2, "Atraccion");
				statement.setInt(4, producto.getId());
				int rows = statement.executeUpdate();
				return rows;
			} else {

				statement.setInt(1, visitante.getId());
				statement.setString(2, "Promocion");
				statement.setInt(3, producto.getId());
				int rows = statement.executeUpdate();
				return rows;
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}

	}
	
	public ArrayList<Ofertable> cargarComprasUsuario(int ID) {

		AtraccionDAOImpl atrraccionDAO = new AtraccionDAOImpl();
		PromocionDAOImpl promocionDAO = new PromocionDAOImpl();
		ArrayList<Ofertable> itinerario = new ArrayList<Ofertable>();
		try {
			String sql = "SELECT * FROM Itinerario where visitante_id = ?";
			Connection conn = Conexion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, ID);
			ResultSet resultados = statement.executeQuery();

			while (resultados.next()) {
				if (resultados.getString(3).equals("Atraccion")) {
					itinerario.add(atrraccionDAO.encontrarPorID(resultados.getInt(5)));
				} else {
					itinerario.add(promocionDAO.encontrarPorID(resultados.getInt(4)));
				}
			}
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return itinerario;
	}
	
	
}
