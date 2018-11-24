package modelo.unidades.arquero;

import modelo.excepciones.ArqueroYaFueUtilizadoEnEsteTurnoException;
import modelo.mapa.Mapa;
import modelo.mapa.Posicion;
import modelo.unidades.Colocable;

public class EstadoArqueroOcupado implements EstadoArquero {

	@Override
	public void avanzarTurno(Arquero arquero) {
        arquero.estarDisponible();
	}

	@Override
    public void atacar(Colocable colocable, Arquero arquero) {
        throw new ArqueroYaFueUtilizadoEnEsteTurnoException();
	}

	@Override
    public void moverArqueroDesdeHacia(Arquero arquero, Posicion origen, Posicion destino, Mapa mapa, int distanciaDeMovimiento) {
        throw new ArqueroYaFueUtilizadoEnEsteTurnoException();
	}
}
