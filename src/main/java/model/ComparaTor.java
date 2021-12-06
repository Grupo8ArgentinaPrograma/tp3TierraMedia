package model;

import java.util.Comparator;

public class ComparaTor implements Comparator<Ofertable> {

	//@Override
	public int compare(Ofertable uno, Ofertable dos) {
		Double tiempoPrimero = uno.getTiempoRecorrido();
		Double tiempoSegundo = dos.getTiempoRecorrido();
		Double costoPrimero = uno.getCosto();
		Double costoSegundo = dos.getCosto();

		if (costoPrimero.compareTo(costoSegundo) == 0) {
			return -tiempoPrimero.compareTo(tiempoSegundo);
		}
		return -costoPrimero.compareTo(costoSegundo);
	}
} 
