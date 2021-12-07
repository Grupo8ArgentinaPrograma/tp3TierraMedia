package testsTierraMedia;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Atraccion;
import model.PromoAxB;
import model.Promocion;




public class TestPromociones {

	Promocion promo1;

	@Before
	public void iniciar() {
		Atraccion a1 = new Atraccion("Mordor uno", 25, 10, 5, "Aventura");
		Atraccion a2 = new Atraccion("Mordor dos", 25, 10, 5, "Aventura");
		Atraccion a3 = new Atraccion("Mordor tres", 25, 10, 5, "Aventura");
		Atraccion a4 = new Atraccion("Mordor cuatro", 2500, 10, 5, "Aventura");
		Atraccion atracciones[] = { a1, a2, a3, a4 };
		promo1 = new PromoAxB("nombre",atracciones,"aventra");
	}

	@Test
	public void queSeCreaPromocion() {
		assertNotNull(promo1);
	}

	@Test
	public void queCalculaElcostoTotal() {

		double costo = promo1.getCosto();
		assertEquals(75, costo, 0.0);
	}

}
