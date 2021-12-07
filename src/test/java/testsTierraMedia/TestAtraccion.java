
package testsTierraMedia;



import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import model.Atraccion;



public class TestAtraccion {

	Atraccion a1;

	@Before
	public void iniciar() {
		a1 = new Atraccion("Mordor", 25, 3, 4, "Aventura");
	}

	@Test
	public void queSeCreaAtracciontest() {
		assertNotNull(a1);
	}

	
	@Test (expected = Error.class)
	public void queNoSeCreanAtraccionesConCostoNegativos() {
		a1 = new Atraccion("Mordor", -25, 3, 4, "Aventura");
	}
	
	@Test (expected = Error.class)
	public void queNoSeCreanAtraccionesConTiempoNegativos() {
		a1 = new Atraccion("Mordor", 25, -3, 4, "Aventura");
	}
	
	@Test (expected = Error.class)
	public void queNoSeCreanAtraccionesConCupoNegativos() {
		a1 = new Atraccion("Mordor", 25, 3, -4, "Aventura");
	}
	
	
	@Test
	public void queAtraccionTodaviaTieneCupo() {
		boolean cupo = a1.tieneCupo();
		assertTrue(cupo);

	}

	@Test
	public void queSeOcupaUnCupo() {
		int cupo = a1.getCupo();
		assertEquals(4, cupo);
		a1.ocuparLugar();
		assertEquals(3, a1.getCupo());
	}

	@Test(expected = Error.class)
	public void queLosCuposSeTerminan() {
		a1.ocuparLugar();
		a1.ocuparLugar();
		a1.ocuparLugar();
		a1.ocuparLugar();
		a1.ocuparLugar();	
		
	}

}
