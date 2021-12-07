package testsTierraMedia;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Atraccion;
import model.PromoPorcentual;
import model.Promocion;
import model.Sistema;
import model.Usuario;

public class TestSistema {

	PromoPorcentual pp;
	Atraccion a1;
	Atraccion a2;
	Atraccion a3;
	Atraccion a4;
	Atraccion atracciones[] = new Atraccion[4];

	Usuario u1;
	Sistema s1;

	@Before
	public void test() {
		s1 = new Sistema();
	}

	@Test
	public void queSeCreaSistema() {
		Sistema s1 = new Sistema();
		assertNotNull(s1);
	}

	@Test
	public void queSistemaAgregaUsuariosDesdeArchivo() {
		s1.cargarUsuarios();
		assertNotNull(s1.getVisitantes());
	}

	@Test
	public void queSistemaAgregaAtraccionesDesdeArchivo() {
		s1.cargarAtracciones();
		assertNotNull(s1.getAtracciones());
	}

	@Test
	public void queSistemaAgregapromocionesDesdeArchivo() {
		s1.cargarPromociones();
		assertNotNull(s1.getPromociones());
	}

	@Test
	public void queCargueCorrectamenteLaTablas() {
		s1.cargarUsuarios();
		s1.cargarAtracciones();
		s1.cargarPromociones();

		assertNotNull(s1.getPromociones());
	}

	@Test
	public void queSistemaRegistraAtraccionComprada() {
		s1.cargarAtracciones();
		s1.cargarPromociones();
		u1 = new Usuario("nombre", 2000, 20000, "Aventura");

		s1.setOfertas(u1);
		int cantComprada = u1.getProductosComprados().size();
		double cantDinero = u1.getDineroDisponible();
		double cantTiempo = u1.getTiempoDisponible();

		Atraccion atrac1 = s1.getAtracciones().get(0);
		double costoAtraccionComprada = atrac1.getCosto();
		double tiempoAtraccionComprada = atrac1.getTiempo();
		u1.comprarProducto(atrac1);

		int cantCompradaAtra1 = u1.getProductosComprados().size();
		double cantDineroActual = u1.getDineroDisponible();
		double cantTiempoActual = u1.getTiempoDisponible();

		s1.setOfertas(u1);
		int cantCompradaEsperable = cantComprada + 1;
		double cantDineroEsperada = cantDinero - costoAtraccionComprada;
		double cantTiempoEsperada = cantTiempo - tiempoAtraccionComprada;

		assertEquals(cantCompradaEsperable, cantCompradaAtra1);
		assertEquals(cantDineroEsperada, cantDineroActual, 0.0);
		assertEquals(cantTiempoEsperada, cantTiempoActual, 0.0);
	}

	@Test
	public void queSistemaOrdenaDeMayorAmenor() {
		u1 = new Usuario("nombre", 2000, 20000, "Aventura");
		s1.cargarAtracciones();
		s1.cargarPromociones();
		s1.setOfertas(u1);

		double primerCosto = s1.getOfertasDiaria().get(0).getCosto();
		double segundoCosto = s1.getOfertasDiaria().get(1).getCosto();
		assertTrue(primerCosto >= segundoCosto);
	}

	@Test
	public void queSistemaOrdenaPrimeroPromocionesLuegoAtracciones() {
		u1 = new Usuario("nombre", 2000, 20000, "Aventura");
		s1.cargarAtracciones();
		s1.cargarPromociones();
		s1.setOfertas(u1);

		assertTrue(s1.getOfertasDiaria().get(0) instanceof Promocion);
		assertTrue(s1.getOfertasDiaria().get(1) instanceof Promocion);
		assertTrue(s1.getOfertasDiaria().get(2) instanceof Promocion);
		assertTrue(s1.getOfertasDiaria().get(3) instanceof Atraccion);
	}

	@Test
	public void queSistemaOrdenaPrimeroPromocionesyAtraccionesConElTipoPreferidoDelUsuario() {
		u1 = new Usuario("nombre", 5, 20, "Aventura");
		s1.cargarAtracciones();
		s1.cargarPromociones();
		s1.setOfertas(u1);

		for (int i = 0; i < 34; i++) {
			if (i < 11) {
				assertTrue(s1.getOfertasDiaria().get(i).getTipo().equals("Aventura"));
			} else {
				assertFalse(s1.getOfertasDiaria().get(i).getTipo().equals("Aventura"));
			}
		}
	}

	@Test(expected = Error.class)
	public void queSistemaUnaAtraccionSinCupoNoLaOfrece() {
		s1.cargarAtracciones();
		s1.cargarPromociones();
		// atraccion en posicion 14 Moria con cupo = 2... haremos tres compras
		u1 = new Usuario("nombre", 2000, 20000, "Aventura");
		s1.setOfertas(u1);
		Atraccion atrac1 = s1.getAtracciones().get(14);
		u1.comprarProducto(atrac1);

		Usuario u2 = new Usuario("nombre2", 2000, 20000, "Aventura");
		s1.setOfertas(u2);
		u2.comprarProducto(atrac1);

		Usuario u3 = new Usuario("nombre3", 2000, 20000, "Aventura");
		s1.setOfertas(u3);
		u3.comprarProducto(atrac1);

		System.out.println(s1.getAtracciones().get(14).getCupo());
	}

	@Test(expected = Error.class)
	public void queSistemaSoloOfrezcaLasAtraccioensQueTienenCupo() {
		s1 = new Sistema();
		a1 = new Atraccion("Mordor uno", 25, 10, 2, "Aventura");
		a2 = new Atraccion("Mordor dos", 25, 10, 5, "Aventura");
		a3 = new Atraccion("Mordor tres", 25, 10, 5, "Aventura");
		a4 = new Atraccion("Mordor cuatro", 25, 10, 5, "Aventura");
		u1 = new Usuario("nombre", 20, 20, "Aventura");

		atracciones[0] = a1;
		atracciones[1] = a2;
		atracciones[2] = a3;
		atracciones[3] = a4;

		s1.cargarPromociones();

		u1 = new Usuario("nombre", 200, 20, "Aventura");
		u1.comprarProducto(a1);

		Usuario u2 = new Usuario("nombre2", 2000, 20000, "Aventura");
		u2.comprarProducto(a1);

		// No hay cupo para comprar esta atracci�n
		Usuario u3 = new Usuario("nombre3", 2000, 20000, "Aventura");
		u3.comprarProducto(a1);

	}

	@Test
	public void queSistemaNoOfrezcaAtraccionIncluidaEnPromocionYaComprada() {
		s1.cargarAtracciones();
		s1.cargarPromociones();
		// Promocion posicion 1 : Moria - Mordor - Bosque Negro
		// Atraccion en posicion 14 Moria
		u1 = new Usuario("nombre", 2000, 200, "Aventura");
		s1.setOfertas(u1);
		u1.comprarProducto(s1.getOfertasDiaria().get(1));

		Atraccion atrac1 = s1.getAtracciones().get(14);
		assertTrue(u1.atraccionIncluidaEnPromocionComprada(atrac1));
	}

	@Test
	public void queSistemaSoloOfrezcaLasPromocionesyAtraccionesQueElUsuarioPuedePagar() {
		u1 = new Usuario("nombre", 4, 20000, "Aventura");
		s1.cargarPromociones();
		s1.cargarAtracciones();
		s1.setOfertas(u1);

		int cantEsperable = 5;
		int oferta = s1.getOfertasDiaria().size();

		int cantidadOfertas = 0;
		for (int i = 0; i < oferta; i++) {
			if (s1.getOfertasDiaria().get(i).getCosto() <= u1.getDineroDisponible()) {
				cantidadOfertas++;
			}
		}
		assertEquals(cantidadOfertas, cantEsperable);
	}
}
