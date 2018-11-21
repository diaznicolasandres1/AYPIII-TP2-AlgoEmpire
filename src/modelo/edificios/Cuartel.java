package modelo.edificios;

import modelo.Oro;
import modelo.unidades.arquero.Arquero;
import modelo.unidades.espadachin.Espadachin;

import java.util.ArrayList;

public class Cuartel extends Edificio {

    Oro oro;
	EstadoCuartel estado = new EstadoCuartelEnConstruccion();
	
	public Cuartel(Oro nuevoOro) {
		this.vidaMaxima = 250;
		this.vida = 250;
		this.reparacion = 50;
		this.tamanio = 4;
		this.oro = nuevoOro;
		this.oro.restarOro(50);
		this.posiciones = new ArrayList<>();
	}
	
	
	/*Los estados se encargan de realizar la accion*/
	
	public void repararse() {
		estado.repararse(this);
	}	
	
	public void recibirDanioCuartel(int valorDanio) {
		estado.recibirDanio(this, valorDanio);
		
	}
	public void terminoDeCrearse() {
		estado = new EstadoCuartelConstruido();
	}
	
	public void avanzarTurno() {
		estado.avanzarTurno(this);
	}
	
	public Arquero crearArqueroDesdeCuartel() {
		return estado.crearArquero(oro);
		
	}
	public Espadachin crearEspadachinDesdeCuartel() {
		return estado.crearEspadachin(oro);
	}
	

}
