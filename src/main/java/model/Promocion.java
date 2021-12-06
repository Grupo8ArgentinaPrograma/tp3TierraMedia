package model;

import java.util.ArrayList;

public abstract class Promocion extends Ofertable {

	private Atraccion[] itinerario;
	private ArrayList<String> atraccionesIncluidas;
	private int id;
	private int descuento;
	
	public Promocion(String nombre, Atraccion[] paquete, String tipo) {
		super(nombre, tipo);
		this.itinerario = paquete;
		this.atraccionesIncluidas = new ArrayList<String>();
		super.setTiempo(getTiempoRecorrido());
		this.setAtraccionesIncluidas();
	}
	
	public Promocion(String nombre, Atraccion[] paquete, String tipo, int descuento) {
		super(nombre, tipo);
		this.setDescuento(descuento);
		this.itinerario = paquete;
		this.atraccionesIncluidas = new ArrayList<String>();
		this.setAtraccionesIncluidas();
		super.setTiempo(getTiempoRecorrido());
	}
	
	public Promocion(String nombre, Atraccion[] paquete, String tipo, int descuento, int id) {
		super(nombre, tipo);
		this.setDescuento(descuento);
		this.itinerario = paquete;
		this.atraccionesIncluidas = new ArrayList<String>();
		this.setAtraccionesIncluidas();
		this.id = id;
		super.setTiempo(getTiempoRecorrido());
	}
	
	@Override
	public double getCosto() {
		double total = 0;
		for (Atraccion a : itinerario) {
			total += a.getCosto();
		}
		
		if(total < 0) {
			throw new Error("El costo no puede ser negativo");
		}	
		return total;
	}

	@Override
	public double getTiempoRecorrido() {
		double total = 0;
		for (Atraccion a : itinerario) {
			total += a.getTiempoRecorrido();
		}

		if(total < 0) {
			throw new Error("Tiempo de recorrido no puede ser negativo");
		}	
		return total;
	}

	public Atraccion[] getItinerario() {
		return this.itinerario;
	}

	@Override
	public void ocuparLugar() {
		for (Atraccion a : itinerario) {
			a.ocuparLugar();
		}
	}

	private void  setAtraccionesIncluidas() {
		for (Atraccion a : itinerario) {
			if(atraccionesIncluidas.contains(a)) {
				continue;
			}
			atraccionesIncluidas.add(a.getNombre());
		}
	}
	
	public ArrayList<String> getAtraccionesIncluidas(){
		return atraccionesIncluidas;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setItinerario(Atraccion[] itinerario) {
		this.itinerario = itinerario;
	}

	public int getDescuento() {
		return descuento;
	}
	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	@Override
	public boolean tieneCupo() {
		boolean valor = true;
		for (Atraccion a : this.getItinerario()) {
			if (!a.tieneCupo()) {
				valor = false;
				break;
			}
		}
		return valor;
	}
}
