package edificios;

import unidades.Aldeano;

public class PlazaCentral extends Edificio {

		EstadoPlazaCentral estado = new EstadoPlazaCentralEnConstruccion();
	private int tamanio;

		public PlazaCentral () {
			vida = 450;
			costo = 100;
			tamanio = 4;
		}
		
		public void repararse() {
			estado.repararse(this);
		}		
		
		public void recibirDanio(int valorDanio) {
			estado.recibirDanio(this, valorDanio);
			
		}
		
		public Aldeano crearAldeanoDesdePlaza() {
			return estado.crearAldeano();
			
		}
		
		/**/
		
		public void terminoDeCrearse() {
			estado = new EstadoPlazaCentralCreada();
		}
		
		public void avanzarTurno() {
			estado.avanzarTurno(this);
		}
		
}
