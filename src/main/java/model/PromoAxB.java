package model;

public class PromoAxB extends Promocion {

	Atraccion atracciongratis = super.getItinerario()[super.getItinerario().length - 1];

	public PromoAxB(String nombre, Atraccion[] paquete, String tipo) {
		super(nombre, paquete, tipo);
		super.setCosto(getCosto());
	}
	
	public PromoAxB(String nombre, Atraccion[] paquete, String tipo,int descuento,int id) {
		super(nombre, paquete, tipo,descuento,id);
		super.setCosto(getCosto());
	}

	@Override
	public void ocuparLugar() {
		for (Atraccion a : super.getItinerario()) {
			a.ocuparLugar();
		}
	}

	private double descuento() {
		if (atracciongratis.getCosto() < 0) {
			throw new Error("El valor del descuneto no puede ser negativo");
		}
		return atracciongratis.getCosto();
	}

	@Override
	public double getCosto() {

		if (super.getCosto() - descuento() < 0) {
			throw new Error("El costo no puede ser negativo");
		}
		return super.getCosto() - descuento();
	}

	@Override
	public boolean tieneCupo() {
		return super.tieneCupo();
	}

	@Override
	public String toString() {
		return super.toString() + " Atracciones incluidas en la Promocion: " + super.getAtraccionesIncluidas() + "\n";
	}
}