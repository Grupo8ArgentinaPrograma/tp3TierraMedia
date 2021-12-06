package model;

public class PromoPorcentual extends Promocion {

	private int descuento;
	
	public PromoPorcentual(String nombre, Atraccion[] paquete, String tipo, int descuento) {
		super(nombre, paquete, tipo,descuento);
		this.descuento = descuento;
		super.setCosto(getCosto());
		if (descuento < 0 || descuento > 100) {
			throw new Error("El descuento no puede ser negativo");
		}
		if (descuento > 100) {
			throw new Error("El descuento no puede ser superior al 100%");
		}
	}

	public PromoPorcentual(String nombre, Atraccion[] paquete, String tipo, int descuento, int id) {
		super(nombre, paquete, tipo,descuento,id);
		this.descuento = descuento;
		super.setCosto(getCosto());
		if (descuento < 0 || descuento > 100) {
			throw new Error("El descuento no puede ser negativo");
		}
		if (descuento > 100) {
			throw new Error("El descuento no puede ser superior al 100%");
		}
	}
	
	@Override
	public void ocuparLugar() {
		for (Atraccion a : super.getItinerario()) {
			a.ocuparLugar();
		}
	}

	private double descuento() {
		return super.getCosto() * (descuento * 0.01);
	}

	@Override
	public double getCosto() {

		if (super.getCosto() - descuento() < 0) {
			throw new Error("El costo no puede ser negativo");
		}
		return super.getCosto() - descuento();
	}

	@Override
	public String toString() {
		return super.toString() + " Atracciones incluidas en la Promocion: " + super.getAtraccionesIncluidas() + "\n";
	}

	@Override
	public boolean tieneCupo() {
		return super.tieneCupo();
	}
}