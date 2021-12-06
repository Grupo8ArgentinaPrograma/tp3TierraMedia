package persistence.commons;

import persistence.AtraccionDAO;
import persistence.PromocionaDAO;
import persistence.UsuarioDAO;
import persistence.impl.AtraccionDAOImpl;
import persistence.impl.ItineraioDAOImpl;
import persistence.impl.PromocionDAOImpl;
import persistence.impl.UsuarioDAOImpl;

public class DAOFactory {

	public static UsuarioDAO getUsuarioDAO() {
		return new UsuarioDAOImpl();
	}

	public static AtraccionDAO getAtraccionDAO() {
		return new AtraccionDAOImpl();
	}

	
	public static PromocionaDAO getPromocionDAO() {
		return new PromocionDAOImpl();
	}

	
	public static ItineraioDAOImpl getItinerarioDaoImpl() {
		return new ItineraioDAOImpl();
	}
}
