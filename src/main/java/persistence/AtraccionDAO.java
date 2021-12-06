package persistence;

import model.Atraccion;
import persistence.commons.GenericDAO;


public interface AtraccionDAO extends GenericDAO<Atraccion> {
	
	public abstract Atraccion encontrarPorNombre(String nombre);
	public abstract Atraccion encontrarPorID(int ID);
}
