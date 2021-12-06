package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Atraccion;
import model.PromoAbsoluta;
import model.PromoAxB;
import model.PromoPorcentual;
import model.Promocion;
import persistence.PromocionaDAO;
import persistence.commons.Conexion;
import persistence.commons.MissingDataException;

public class PromocionDAOImpl implements PromocionaDAO {

	public List<Promocion> encontrarTodos() {

		try {

			String sql = "SELECT * from Promocion JOIN TipoAtraccion on Promocion.tipo_id = TipoAtraccion.id ";

			Connection conn = Conexion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Promocion> promociones = new ArrayList<Promocion>();
			while (resultados.next()) {
				promociones.add(aPromocion(resultados));
			}

			return promociones;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int contarTodos(){
		try {
			String sql = "SELECT COUNT(1) AS TOTAL FROM Promocion";
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

	public int insertar(Promocion promocion){
		try {
			String sql = "INSERT INTO Promocion (nombre,descripcion,descuento,tipo_id,atraccion1_id,atraccion2_id,atraccion3_id) VALUES (?,?,?, (SELECT id FROM TipoAtraccion WHERE descripcion = ?),(SELECT id FROM Atraccion WHERE nombre=? ),(SELECT id FROM Atraccion WHERE nombre=? ),(SELECT id FROM Atraccion WHERE nombre=? ))";
			Connection conn = Conexion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, promocion.getNombre());
			statement.setString(2, "");
			statement.setDouble(3, promocion.getDescuento());
			statement.setString(4, promocion.getTipo());
			statement.setString(5, promocion.getAtraccionesIncluidas().get(0));
			statement.setString(6, promocion.getAtraccionesIncluidas().get(1));
			statement.setString(7, promocion.getAtraccionesIncluidas().get(2));
			int rows = statement.executeUpdate();
			promocion.getAtraccionesIncluidas();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	
	
	
	public int actualizarAtraccionesDeLasPromociones(Promocion promocion) {
		
		try {
			String sql2 = "UPDATE Atraccion SET cupo = ? WHERE id = ?";
			Connection conn = Conexion.getConnection();
			PreparedStatement statement2 = conn.prepareStatement(sql2);
			

			for (int i = 0; i < promocion.getItinerario().length; i++) {
				statement2.setInt(1, promocion.getItinerario()[i].getCupo());
				statement2.setInt(2, promocion.getItinerario()[i].getId());
				statement2.executeUpdate();
			}
			return 0;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		
	}
	
	
	public int actualizarDatos(Promocion promocion){
		try {
			String sql = "UPDATE Promocion SET nombre = ?,descripcion = ?,descuento = ?,tipo_id = (SELECT id FROM TipoAtraccion WHERE descripcion = ?),atraccion1_id= (SELECT id FROM Atraccion WHERE nombre=?),atraccion2_id =(SELECT id FROM Atraccion WHERE nombre=?),atraccion3_id = (SELECT id FROM Atraccion WHERE nombre=?) WHERE id = ?";
			Connection conn = Conexion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, promocion.getNombre());
			statement.setString(2,"");
			statement.setDouble(3, promocion.getDescuento());
			statement.setString(4, promocion.getTipo());
			statement.setString(5, promocion.getAtraccionesIncluidas().get(0));
			statement.setString(6, promocion.getAtraccionesIncluidas().get(1));
			statement.setString(7, promocion.getAtraccionesIncluidas().get(2));
			statement.setInt(8, promocion.getId());
		
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int borrar(Promocion promocion) {
		try {
			String sql = "DELETE FROM  Promocion WHERE id = ?";
			Connection conn = Conexion.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, promocion.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public Promocion encontrarPorID(int ID) {
		try {
			String sql = "SELECT * from Promocion JOIN TipoAtraccion on Promocion.tipo_id = TipoAtraccion.id WHERE Promocion.id=?";
			Connection conn = Conexion.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, ID);
			ResultSet resultados = statement.executeQuery();

			Promocion promo = null;

			if (resultados.next()) {
				promo = aPromocion(resultados);
			}

			return promo;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Promocion aPromocion(ResultSet resultados) throws SQLException {

		if (resultados.getString(2).equals("absoluta")) {
			return new PromoAbsoluta(resultados.getString(3), crarPaquetes(resultados), resultados.getString(10),
					resultados.getInt(4),resultados.getInt(1));
		}

		if (resultados.getString(2).equals("axb")) {
			return new PromoAxB(resultados.getString(3), crarPaquetes(resultados), resultados.getString(10),resultados.getInt(1),resultados.getInt(1));
		}

		if (resultados.getString(2).equals("porcentual")) {
			return new PromoPorcentual(resultados.getString(3), crarPaquetes(resultados), resultados.getString(10),
					resultados.getInt(4),resultados.getInt(1));
		}
		return null;
	}

	private Atraccion[] crarPaquetes(ResultSet resultados) {
		AtraccionDAOImpl atrraccionDAO = new AtraccionDAOImpl();

		Atraccion a1 = null;
		Atraccion a2 = null;
		Atraccion a3 = null;

		try {
			if (resultados.getString(2).equals("absoluta")) {
				a1 = atrraccionDAO.encontrarPorID(resultados.getInt(6));
				a2 = atrraccionDAO.encontrarPorID(resultados.getInt(7));
				a3 = atrraccionDAO.encontrarPorID(resultados.getInt(8));
			}

			if (resultados.getString(2).equals("axb")) {
				a1 = atrraccionDAO.encontrarPorID(resultados.getInt(6));
				a2 = atrraccionDAO.encontrarPorID(resultados.getInt(7));
				a3 = atrraccionDAO.encontrarPorID(resultados.getInt(8));

			}

			if (resultados.getString(2).equals("porcentual")) {
				a1 = atrraccionDAO.encontrarPorID(resultados.getInt(6));
				a2 = atrraccionDAO.encontrarPorID(resultados.getInt(7));
				a3 = atrraccionDAO.encontrarPorID(resultados.getInt(8));
			}

		} catch (Exception e) {

			throw new MissingDataException(e);
		}

		Atraccion atraccioens[] = { a1, a2, a3 };
		return atraccioens;
	}
}
