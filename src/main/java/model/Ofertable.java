package model;

public abstract class Ofertable {

	private int id;
	private String nombre;
	private String tipo;
	private double costo;
	private double tiempo;

	public Ofertable(String nombre, String tipo, double costo, double tiempo) {
		this(nombre, tipo);
		this.costo = costo;
		this.tiempo = tiempo;

		if (costo < 0 || tiempo < 0) {
			throw new Error("Los valores ingresados no puede ser negativo");
		}
	}

	public Ofertable(int id,String nombre, String tipo, double costo, double tiempo) {
		this(nombre, tipo, costo, tiempo);
		this.setId(id);
		
	}
	
	public Ofertable(String nombre, String tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
	}

	abstract protected void ocuparLugar();

	abstract public boolean tieneCupo();

	public double getTiempo() {
		return this.tiempo;
	}

	public double getCosto() {
		return this.costo;
	}

	protected double getTiempoRecorrido() {
		return this.tiempo;
	}

	public String getTipo() {
		return this.tipo;
	}

	public String getNombre() {
		return this.nombre;
	}

	protected void setCosto(double costo) {
		if( costo < 0  ) {
			throw new Error("El costo no puede ser negativo");
		}
		this.costo = costo;
	}

	protected void setTiempo(double tiempo) {

		this.tiempo = tiempo;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "\n Nombre: " + nombre + " -- " + " Tipo: " + tipo + " -- " + " Costo: $" + costo + " -- " + " Tiempo:"
				+ tiempo + " Hs" + "\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ofertable other = (Ofertable) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}	
}
