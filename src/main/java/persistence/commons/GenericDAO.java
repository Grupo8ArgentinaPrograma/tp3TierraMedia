package persistence.commons;

import java.util.List;

public interface GenericDAO<T> {

	public List<T> encontrarTodos();
	public  int    contarTodos();
	public  int    insertar(T t);
	public  int    actualizarDatos(T t) ;
	public  int    borrar(T t) ;
	
}
