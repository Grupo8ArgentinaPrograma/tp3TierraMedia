package testsTierraMedia;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Atraccion;
import model.PromoAbsoluta;
import model.Usuario;


public class TestPromoAbsoluta {

	PromoAbsoluta pp;
	Atraccion a1;
	Atraccion a2;
	Atraccion a3;
	Atraccion a4;
	Atraccion atracciones[] = new Atraccion[4];
	
	@Before
	public void test() {
		a1 = new Atraccion("Mordor uno", 25, 10, 5, "Aventura");
		a2 = new Atraccion("Mordor dos", 25, 10, 5, "Aventura");
		a3 = new Atraccion("Mordor tres", 25, 10, 5, "Aventura");
		a4 = new Atraccion("Mordor cuatro", 25, 10, 5, "Aventura");
		atracciones[0] = a1;
		atracciones[1]=a2; 
		atracciones[2] =a3;
		atracciones[3]= a4 ;
		pp = new PromoAbsoluta("nombre",atracciones, "Paisaje", 25);
	}
	
	@Test
	public void queSeCreaPromoAbsoluta() {
		assertNotNull(a1);
	}

	@Test (expected = Error.class)
	public void queNoSeCreanAtraccionesConCostoNegativos() {
		pp = new PromoAbsoluta("Mordor",atracciones, "Aventura",-20);
	}
	
	@Test
	public void queHaceDescuentoAbsoluto() {
		double costo = pp.getCosto();
		assertEquals(25, costo, 0.0);

	}
	
	@Test
	public void queRestaCupoATodasLasAtraccionesDelItinerarioCuandoSeCompra() {
		Usuario u1 = new Usuario("nombre",100,100,"gusto");
		u1.comprarProducto(pp);	
		assertEquals(4, a1.getCupo());
		assertEquals(4, a2.getCupo());
		assertEquals(4, a3.getCupo());
		assertEquals(4, a4.getCupo());
	}
	
	@Test(expected = Error.class)
	public void queLosCuposSeTerminan() {
		Usuario u1 = new Usuario("nombre",10000,10000,"gusto");
		u1.comprarProducto(pp);	
		u1.comprarProducto(pp);	
		u1.comprarProducto(pp);	
		u1.comprarProducto(pp);	
		u1.comprarProducto(pp);
		u1.comprarProducto(pp);	
	}
}
