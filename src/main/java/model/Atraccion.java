package model;

public class Atraccion extends Ofertable {
	private int cupo;

	public Atraccion(String nombre, double costo, double tiempo, int cupo, String tipo) {
		super(nombre, tipo, costo, tiempo);
		this.cupo = cupo;
		if (cupo < 0) {
			throw new Error("Los valores ingresados no puede ser negativo");
		}
	}
	
	public Atraccion(int id,String nombre, double costo, double tiempo, int cupo, String tipo) {
		super(id,nombre, tipo, costo, tiempo);
		this.cupo = cupo;
		if (cupo < 0) {
			throw new Error("Los valores ingresados no puede ser negativo");
		}
	}
	
	@Override
	public boolean tieneCupo() {
		return cupo > 0;
	}

	public int getCupo() {
		return this.cupo;
	}

	@Override
	public void ocuparLugar() {
		this.cupo--;
		if (getCupo() < 0) {
			throw new Error("No hay mas cupo");
		}	
	}

	public double getCosto() {
		return super.getCosto();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}