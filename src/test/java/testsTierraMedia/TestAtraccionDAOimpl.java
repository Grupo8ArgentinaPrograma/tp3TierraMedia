package testsTierraMedia;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import persistence.impl.AtraccionDAOImpl;
import persistence.commons.DAOFactory;
import model.Atraccion;

public class TestAtraccionDAOimpl {
	
	AtraccionDAOImpl atra;
	
	
	@Before
	public void iniciar(){
		atra = (AtraccionDAOImpl) DAOFactory.getAtraccionDAO();
	}
	
	@Test
	public void queBorraAtraccion() {
		Atraccion a4 = new Atraccion("AtraccionABorrar",400,400,400,"Paisaje");
		atra.insertar(a4);
		
		Atraccion atraccionInsertada = atra.encontrarPorNombre("AtraccionABorrar");
		assertNotNull(atraccionInsertada);
		atra.borrar(atraccionInsertada);
		assertNull(atraccionInsertada);
		
	}
	
	@Test
	public void queSeInsertaAtraccionYEncuentraPorNombre() {
		
		Atraccion a1 = new Atraccion("TestInsertar1", 15, 2, 10, "Paisaje");
		atra.insertar(a1);
		
		Atraccion insertada = atra.encontrarPorNombre("TestInsertar1");
		assertNotNull(insertada);
		assertEquals(insertada,a1);
		
		atra.borrar(insertada);

	}
	
	@Test
	public void queSeActualizaAtraccion(){
		Atraccion a2 = new Atraccion("AtraccionTEST", 23, 1, 56, "Aventura");
		atra.insertar(a2);
		assertEquals(atra.encontrarPorNombre("AtraccionTEST").getCupo(),56);
		
		Atraccion a3 = new Atraccion("AtraccionTEST",23,35,800,"Aventura");
		atra.actualizarDatos(a3);
		
		Atraccion actualizada = atra.encontrarPorNombre("AtraccionTEST");
		assertEquals(actualizada.getCupo(),800);
		
		atra.borrar(actualizada);
	}
	@Test
	public void queCuentaTodasLasAtracciones() {
		int total = atra.contarTodos();
		//System.out.println(total);
		assertEquals(total,25);//Comprobar el n�mero de elementos dentro de la tabla Atracciones en la db
	}
	
	@Test
	public void queEncuentraPorID() {
		Atraccion a4 = atra.encontrarPorNombre("Moria");
		int ID = a4.getId();
		
		Atraccion atraccionPorID = atra.encontrarPorID(ID);
		assertNotNull(atraccionPorID);
		
		assertEquals(a4,atraccionPorID);
	}
	
	@Test
	public void queEncuentraATodasLasAtracciones() {
		Atraccion a5 = atra.encontrarPorNombre("Moria");//Primera atracci�n
		Atraccion a6 = atra.encontrarPorNombre("Rhun");//Ultima atracci�n
		Atraccion a7 = atra.encontrarPorNombre("Bree");//Atracci�n del medio
		List<Atraccion> todas = atra.encontrarTodos();
		
		assertTrue(todas.contains(a5));
		assertTrue(todas.contains(a6));
		assertTrue(todas.contains(a7));
		
		int largo = todas.size();
		assertEquals(largo, atra.contarTodos());
	}
	
}

