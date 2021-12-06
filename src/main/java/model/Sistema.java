package model;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import persistence.commons.DAOFactory;
import persistence.impl.AtraccionDAOImpl;
import persistence.impl.ItineraioDAOImpl;
import persistence.impl.PromocionDAOImpl;
import persistence.impl.UsuarioDAOImpl;

public class Sistema {

	private ArrayList<Usuario> visitantes;
	private ArrayList<Promocion> promociones;
	private ArrayList<Atraccion> atracciones;
	private ArrayList<Ofertable> ofertasPrioritarias;
	private ArrayList<Ofertable> ofertasSecundarias;
	private ArrayList<Ofertable> ofertasDiaria;
	
	public Sistema() {
		this.visitantes = new ArrayList<Usuario>();
		this.promociones = new ArrayList<Promocion>();
		this.atracciones = new ArrayList<Atraccion>();
		this.ofertasPrioritarias = new ArrayList<Ofertable>();
		this.ofertasSecundarias = new ArrayList<Ofertable>();
		this.ofertasDiaria = new ArrayList<Ofertable>();
	}
	
	public  ArrayList<Usuario> getVisitantes(){
		return this.visitantes;
	}
	
	public  ArrayList<Atraccion> getAtracciones(){
		return this.atracciones;
	}
	
	public  ArrayList<Promocion> getPromociones(){
		return this.promociones;
	}
	
	public  ArrayList<Ofertable> getOfertasDiaria(){
		return this.ofertasDiaria; 
	}
		
	public void setOfertas(Usuario visitante) {

		this.promociones.sort(new ComparaTor());
		this.atracciones.sort(new ComparaTor());

		this.ofertasPrioritarias.clear();
		this.ofertasSecundarias.clear();
		this.ofertasDiaria.clear();

		for (Promocion promocion : promociones) {
						
			if (visitante.estaAtraccionMegusta(promocion)) {
				this.ofertasPrioritarias.add(promocion);
			} else {
				this.ofertasSecundarias.add(promocion);
			}
		}

		for (Atraccion atraccion : atracciones) {
			
			if (visitante.estaAtraccionMegusta(atraccion)) {
				this.ofertasPrioritarias.add(atraccion);
			} else {
				this.ofertasSecundarias.add(atraccion);
			}
		}

		for (Ofertable producto : ofertasPrioritarias) {
			if ((!ofertasDiaria.contains(producto) && producto.tieneCupo())) {
				this.ofertasDiaria.add(producto);
			}
		}
		for (Ofertable producto : ofertasSecundarias) {
			if ((!ofertasDiaria.contains(producto) && producto.tieneCupo())) {
				this.ofertasDiaria.add(producto);
			}
		}
	}

	public void hacerOfertas()  {
		
		Scanner escaner = new Scanner(System.in);
		int respuesta = 0;
		UsuarioDAOImpl usu = (UsuarioDAOImpl) DAOFactory.getUsuarioDAO();
		PromocionDAOImpl pro = (PromocionDAOImpl) DAOFactory.getPromocionDAO();
		AtraccionDAOImpl atra = (AtraccionDAOImpl) DAOFactory.getAtraccionDAO();
		ItineraioDAOImpl itiDAO = DAOFactory.getItinerarioDaoImpl();
		
		for (Usuario visitante : visitantes) {
			this.setOfertas(visitante);
			System.out.println("                  **************************************************");
			System.out.println("                     Hola " + visitante.getNombre() + " Estas son tus sugerencias diarias ");
			System.out.println("                  **************************************************");		
			
			for (Ofertable producto : this.ofertasDiaria) {
					if (       visitante.getDineroDisponible() < producto.getCosto()
							|| visitante.getTiempoDisponible() < producto.getTiempo()
							|| visitante.atraccionIncluidaEnPromocionComprada(producto) || (!producto.tieneCupo())
							|| visitante.getproductosComprados().contains(producto)) {

						continue;
					}
					
					System.out.println("\n �"+visitante.getNombre() + " queres comprar?" + producto+"\n");
					System.out.print(" 1 para comprar, cualquier otro numero para seguir>>");
					respuesta = escaner.nextInt();
					
					if (respuesta == 1) {
						visitante.comprarProducto(producto);
						usu.actualizarDatos(visitante);
						itiDAO.insertarEnItinerario(producto, visitante);
						
						if(producto instanceof Atraccion) {
							atra.actualizarDatos((Atraccion)producto);
						}else {	
							pro.actualizarAtraccionesDeLasPromociones((Promocion)producto);
						}			
					}
				}
				System.out.println("\n" + visitante.getNombre() + " terminaste de comprar gasto: " +getGastoTotal(visitante)
				+ " recorrido: " + getTiempoTotalARecorrer(visitante));
				imprimirFactura(visitante);
			}
		escaner.close();
	}
	

/////////////////////calcular total a pagar para usuario y tiempo del recorrido//////////////////
	
	private double getGastoTotal(Usuario visitante) {
		double total = 0;

		for (Ofertable producto : visitante.getproductosComprados()) {
			total += producto.getCosto();
		}
		return total;
	}

	private double getTiempoTotalARecorrer(Usuario visitante) {
		double total = 0;

		for (Ofertable producto : visitante.getproductosComprados()) {
			total += producto.getTiempo();
		}
		return total;

	}
	
////////////////////// Crear facturas para visitantes/////////////////////////////////////////	

	private void imprimirFactura(Usuario visitante) {	

		try{
			PrintWriter writer = new PrintWriter("Factura" + visitante.getNombre() + ".txt", "UTF-8");
			
			writer.println("\n"
					+ "      *                                     *\r\n"
					+ "{o)xxx|===============-    *    -===============|xxx(o}\r\n"
					+ "      *                                     *");
			writer.println(" \n            Nombre visitante: " + visitante.getNombre());
			writer.println("            monto a pagar: $" + getGastoTotal(visitante));
			writer.println("            Duracion del reccorido: " + getTiempoTotalARecorrer(visitante) + "Hs");
			writer.println("\n"
					+ "      *                                     *\r\n"
					+ "{o)xxx|===============-    *    -===============|xxx(o}\r\n"
					+ "      *                                     *");
			
			
			
			writer.println("\n                DETALLES DE LA COMPRA\n");
			
			writer.println(visitante.getproductosComprados());	
			writer.println("\n"
					+ "      *                                     *\r\n"
					+ "{o)xxx|===============-    *    -===============|xxx(o}\r\n"
					+ "      *                                     *");
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		System.out.println("\n                ------------------------------------------------------------");
		System.out.println("                  Gracias por tu compra "+visitante.getNombre()+" Disfruta el viaje!!!");
		System.out.println("               ------------------------------------------------------------\n\n\n");

	}
	
////////////////////////////Carga de archivos //////////////////////////////////////////

	public void cargarUsuarios() {
		UsuarioDAOImpl usu = (UsuarioDAOImpl) DAOFactory.getUsuarioDAO();
		visitantes.addAll(usu.encontrarTodos());
	}

	public void cargarAtracciones() {
		AtraccionDAOImpl a =(AtraccionDAOImpl) DAOFactory.getAtraccionDAO();
		this.atracciones.addAll(a.encontrarTodos());

	}
	
	public void cargarPromociones() {
		PromocionDAOImpl promo =(PromocionDAOImpl) DAOFactory.getPromocionDAO();
		this.promociones.addAll(promo.encontrarTodos()); 	
	}
	
}
