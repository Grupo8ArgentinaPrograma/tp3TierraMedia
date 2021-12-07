package testsTierraMedia;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import persistence.AtraccionDAO;
import persistence.commons.DAOFactory;
import persistence.UsuarioDAO;
import model.Ofertable;
import model.Usuario;

public class TestUsuarios {

	UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();

	@Test
	public void queSeCreeUsuarioYPoderRecuperarlo() throws SQLException {
		Usuario galadriel = new Usuario("Galadriel", 100, 33, "Aventura");
		usuarioDAO.insertar(galadriel);
		assertNotNull(galadriel);
		// Restaurar
		Usuario galadrielRecuperado = usuarioDAO.encontrarPorNombre("Galadriel");
		assertNotNull(galadrielRecuperado);
		usuarioDAO.borrar(galadrielRecuperado);
	}

	@Test
	public void queElNombreDelUsuarioSeaIgualAlIngresado() throws SQLException {
		Usuario galadriel = new Usuario("Galadriel", 100, 33, "Aventura");
		usuarioDAO.insertar(galadriel);
		assertEquals("Galadriel", galadriel.getNombre());
		// Restaurar
		Usuario galadrielRecuperado = usuarioDAO.encontrarPorNombre("Galadriel");
		assertNotNull(galadrielRecuperado);
		usuarioDAO.borrar(galadrielRecuperado);
	}

	@Test
	public void queLosCamposRegistradosSeanIgualesALosIngresados() throws SQLException {
		Usuario galadriel = new Usuario("Galadriel", 100, 33, "Aventura");
		usuarioDAO.insertar(galadriel);
		assertEquals("Galadriel", galadriel.getNombre());
		assertEquals(100, galadriel.getDineroDisponible(), 0);
		assertEquals(33, galadriel.getTiempoDisponible(), 0);
		assertEquals("Aventura", galadriel.getTipoPreferido());
		// Restaurar
		Usuario galadrielRecuperado = usuarioDAO.encontrarPorNombre("Galadriel");
		assertNotNull(galadrielRecuperado);
		usuarioDAO.borrar(galadrielRecuperado);
	}

	@Test(expected = Error.class)
	public void queNoPermitaCrearUsuarioConTiempoDisponibleNegativo() throws SQLException {
		Usuario galadriel = new Usuario("Galadriel", -100, 33, "Aventura");
		usuarioDAO.insertar(galadriel);
	}

	@Test(expected = Error.class)
	public void queNoSeCreanUsuariosConConTiempoNegativo() throws SQLException {
		Usuario galadriel = new Usuario("Galadriel", 100, -33, "Aventura");
		usuarioDAO.insertar(galadriel);
	}

	@Test
	public void queAlCrearDosUsuariosSeCuentenCantidadOriginalMasDosUsuarios() throws SQLException {
		// Cantidad original de usuarios
		int cantUsuarios = usuarioDAO.contarTodos();
		Usuario galadriel = new Usuario("Galadriel", 100, 33, "Aventura");
		usuarioDAO.insertar(galadriel);
		Usuario eowyn = new Usuario("Eowyn", 200, 66, "Paisaje");
		usuarioDAO.insertar(eowyn);
		// Cantidad Esperada
		int cantUsuariosEsperada = cantUsuarios + 2;
		int cantUsuariosObtenida = usuarioDAO.contarTodos();
		assertEquals(cantUsuariosEsperada, cantUsuariosObtenida);
		// Restaurar
		Usuario galadrielRecuperado = usuarioDAO.encontrarPorNombre("Galadriel");
		assertNotNull(galadrielRecuperado);
		usuarioDAO.borrar(galadrielRecuperado);
		Usuario eowynRecuperado = usuarioDAO.encontrarPorNombre("Eowyn");
		assertNotNull(eowynRecuperado);
		usuarioDAO.borrar(eowynRecuperado);
	}

	@Test
	public void queAlCrearUnUsuarioSePuedaRecuperarPorNombre() throws SQLException {
		Usuario galadriel = new Usuario("Galadriel", 100, 33, "Aventura");
		usuarioDAO.insertar(galadriel);

		// Recuperarlo por nombre
		Usuario galadrielRecuperado = usuarioDAO.encontrarPorNombre("Galadriel");
		assertEquals(galadriel.getNombre(), galadrielRecuperado.getNombre());
		assertEquals(galadriel.getDineroDisponible(), galadrielRecuperado.getDineroDisponible(), 0.01);
		assertEquals(galadriel.getTiempoDisponible(), galadrielRecuperado.getTiempoDisponible(), 0.01);
		assertEquals(galadriel.getTipoPreferido(), galadrielRecuperado.getTipoPreferido());
		// Restaurar
		usuarioDAO.borrar(galadrielRecuperado);
	}

	@Test
	public void queActualizaElDineroDisponible() throws SQLException {
		// Generar usuario
		Usuario galadriel = new Usuario("Galadriel", 100, 33, "Aventura");
		usuarioDAO.insertar(galadriel);
		// Recuperar desde la base de datos
		Usuario galadrielRecuperado = usuarioDAO.encontrarPorNombre("Galadriel");
		double dinero = galadrielRecuperado.getDineroDisponible();
		assertEquals(100, dinero, 0.0);
		galadrielRecuperado.gastarDinero(90);
		assertEquals(10, galadrielRecuperado.getDineroDisponible(), 0.0);
		// Actualizar usuario
		usuarioDAO.actualizarDatos(galadrielRecuperado);
		// Restaurar
		usuarioDAO.borrar(galadrielRecuperado);
	}

	@Test
	public void queActualizaElTiempoDisponible() throws SQLException {
		// Generar usuario
		Usuario galadriel = new Usuario("TGaladriel", 100, 10, "Aventura");
		usuarioDAO.insertar(galadriel);
		// Recuperar desde la base de datos
		Usuario galadrielRecuperado = usuarioDAO.encontrarPorNombre("TGaladriel");
		double tiempo = galadrielRecuperado.getTiempoDisponible();
		assertEquals(10, tiempo, 0.0);
		galadrielRecuperado.restarTiempo(3);
		assertEquals(7, galadrielRecuperado.getTiempoDisponible(), 0.0);
		// Actualizar usuario
		usuarioDAO.actualizarDatos(galadrielRecuperado);
		// Restaurar
		usuarioDAO.borrar(galadrielRecuperado);
	}

	@Test(expected = Error.class)
	public void queNoSePuedeGastarMasDineroDelQueSeTiene() throws SQLException {
		// Generar usuario
		Usuario galadriel = new Usuario("!Borrar", 100, 33, "Aventura");
		usuarioDAO.insertar(galadriel);
		// Recuperar desde la base de datos
		Usuario galadrielRecuperado = usuarioDAO.encontrarPorNombre("!Borrar");
		// Gastar de m�s
		galadrielRecuperado.gastarDinero(101);
	}

	@Test(expected = Error.class)
	public void queUsuarioNoPuedeComprarAtraccionSiNoLeAlcanzaElDinero() throws SQLException {
		// Generar usuario
		Usuario galadriel = new Usuario("!Borrar", 10, 33, "Aventura");
		usuarioDAO.insertar(galadriel);
		// Recuperar desde la base de datos
		Usuario galadrielRecuperado = usuarioDAO.encontrarPorNombre("!Borrar");
		// Gastar de m�s
		galadrielRecuperado.gastarDinero(40);
	}

	@Test
	public void queSeActualiceElTiempoYCostoDisponibleSegunLoQueCompra() throws SQLException {
		// Generar usuario
		Usuario galadriel = new Usuario("Comprador", 100, 10, "Aventura");
		usuarioDAO.insertar(galadriel);
		// Recuperar desde la base de datos
		Usuario galadrielRecuperado = usuarioDAO.encontrarPorNombre("Comprador");

		// Obtener atraccion desde la base de datos
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		Ofertable producto = atraccionDAO.encontrarPorID(1);
		// atraccionDAO.encontrarPorID(1); // Moria 10 2

		// Comprar atracci�n
		galadrielRecuperado.comprarProducto(producto);
		usuarioDAO.actualizarDatos(galadrielRecuperado);

		// Verificar si actualiza tiempo y dinero disponible
		Usuario galadrielModificado = usuarioDAO.encontrarPorNombre("Comprador");
		double dineroActualizado = galadrielModificado.getDineroDisponible();
		assertEquals(90, dineroActualizado, 0.0);
		double tiempoActualizado = galadrielModificado.getTiempoDisponible();
		assertEquals(8, tiempoActualizado, 0.0);

		// Restaurar
		usuarioDAO.borrar(galadrielModificado);
	}

	@Test
	public void queElUsuarioSabeCuandoUnaAtraccionLeGusta() throws SQLException {
		// Obtener atraccion desde la base de datos
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		Ofertable atraccionTipo1 = atraccionDAO.encontrarPorID(1);
		Ofertable atraccionTipo2 = atraccionDAO.encontrarPorID(9);

		// Generar usuario contipo de preferencia = 1
		Usuario galadriel = new Usuario("LeGusta1", 100, 10, "Aventura");
		usuarioDAO.insertar(galadriel);
		// Recuperar desde la base de datos
		Usuario galadrielRecuperado = usuarioDAO.encontrarPorNombre("LeGusta1");

		assertTrue(galadrielRecuperado.estaAtraccionMegusta(atraccionTipo1));
		assertFalse(galadrielRecuperado.estaAtraccionMegusta(atraccionTipo2));

		// Restaurar
		usuarioDAO.borrar(galadrielRecuperado);
	}


	/*
	 * @Test public void queCompraPromociones() {
	 * 
	 * Atraccion atra = new Atraccion("Mordor uno", 25 , 10, 5, "Aventura");
	 * Atraccion atra2 = new Atraccion("Mordor dos", 25, 10, 5, "Aventura");
	 * Atraccion atra3 = new Atraccion("Mordor tres", 25, 10, 5, "Aventura");
	 * Atraccion itinerario[] = { atra, atra2, atra3 }; Promocion oferta= new
	 * PromoAbsoluta("nombre",itinerario, "aventura", 20);
	 * 
	 * Promocion []esperada = {oferta}; u1.comprarProducto(oferta);
	 * 
	 * assertEquals(980, u1.getDineroDisponible(),0); assertEquals(70,
	 * u1.getTiempoDisponible(),0);
	 * assertArrayEquals(esperada,u1.getproductosComprados().toArray()); }
	 * 
	 * @Test public void quePagaPromocionesQueCOmpra() {
	 * 
	 * Atraccion atra = new Atraccion("Mordor uno", 25 , 10, 5, "Aventura");
	 * Atraccion atra2 = new Atraccion("Mordor dos", 25, 10, 5, "Aventura");
	 * Atraccion atra3 = new Atraccion("Mordor tres", 25, 10, 5, "Aventura");
	 * Atraccion itinerario[] = { atra, atra2, atra3 }; Promocion oferta= new
	 * PromoAbsoluta("nombre",itinerario, "aventura", 20);
	 * 
	 * u1.comprarProducto(oferta); assertEquals(980, u1.getDineroDisponible(),0); }
	 * 
	 * @Test public void queRestaTiempoDePromoComprada() {
	 * 
	 * Atraccion atra = new Atraccion("Mordor uno", 25 , 10, 5, "Aventura");
	 * Atraccion atra2 = new Atraccion("Mordor dos", 25, 10, 5, "Aventura");
	 * Atraccion atra3 = new Atraccion("Mordor tres", 25, 10, 5, "Aventura");
	 * Atraccion itinerario[] = { atra, atra2, atra3 }; Promocion oferta= new
	 * PromoAbsoluta("nombre",itinerario, "aventura", 20);
	 * 
	 * u1.comprarProducto(oferta);
	 * 
	 * assertEquals(70, u1.getTiempoDisponible(),0); }
	 * 
	 * 
	 * @Test public void queCalculaElTiempo() {
	 * 
	 * Atraccion atra = new Atraccion("Mordor uno", 25, 10, 5, "Aventura");
	 * u1.comprarProducto(atra); assertEquals(90, u1.getTiempoDisponible(), 0); }
	 * 
	 * @Test public void
	 * queSabeCuandoUnaAtraccionYaEstaIncluidaEnUnaPromocionQueCompro() { Atraccion
	 * atra = new Atraccion("Mordor uno", 25 , 10, 5, "Aventura"); Atraccion atra2 =
	 * new Atraccion("Mordor dos", 25, 10, 5, "Aventura"); Atraccion atra3 = new
	 * Atraccion("Mordor tres", 25, 10, 5, "Aventura"); Atraccion itinerario[] = {
	 * atra, atra2 }; Promocion oferta= new PromoAbsoluta("nombre",itinerario,
	 * "aventura", 20);
	 * 
	 * u1.comprarProducto(oferta);
	 * 
	 * assertTrue(u1.atraccionIncluidaEnPromocionComprada(atra));
	 * assertFalse(u1.atraccionIncluidaEnPromocionComprada(atra3)); }
	 */

}
