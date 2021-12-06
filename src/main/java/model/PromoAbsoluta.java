package model;

public class PromoAbsoluta extends Promocion {

	private double costo;
	
	public PromoAbsoluta(String nombre, Atraccion[]paquete,String tipo, double costo) {
		super(nombre,paquete ,tipo);
		this.costo = costo;
		super.setCosto(costo);
		if( costo < 0 ) {
			throw new Error("El costo no puede ser negativo");
		}
	}
	
	public PromoAbsoluta(String nombre, Atraccion[]paquete,String tipo, double costo,int id) {
		super(nombre,paquete ,tipo);
		super.setId(id);
		this.costo = costo;
		super.setCosto(costo);
		if( costo < 0 ) {
			throw new Error("El costo no puede ser negativo");
		}
	}
	
	@Override
	public double getCosto() {
		return this.costo;
	}

	@Override
	public void ocuparLugar() {
		for (Atraccion a : super.getItinerario()) {
			a.ocuparLugar();
		}
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
