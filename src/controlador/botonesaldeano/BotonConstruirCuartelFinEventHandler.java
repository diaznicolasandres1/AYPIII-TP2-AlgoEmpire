package controlador.botonesaldeano;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import modelo.excepciones.*;
import modelo.juego.Juego;
import modelo.unidades.aldeano.Aldeano;
import vista.ContenedorPrincipal;

public class BotonConstruirCuartelFinEventHandler implements EventHandler<ActionEvent> {
    private  ContenedorPrincipal contenedor;
    private Aldeano constructor;
    private Juego juego;
    private int fila;
    private int columna;

    public BotonConstruirCuartelFinEventHandler(Juego juego, Aldeano constructor, int fila, int columna, ContenedorPrincipal contenedor){
        this.constructor = constructor;
        this.contenedor = contenedor;
        this.juego = juego;
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error al construir Cuartel");
        try {
            juego.construirCuartel(constructor, fila, columna);
        } catch (ColocableSeleccionadoException | UnidadYaFueUtilizadaEnEsteTurnoException e) {
            alert.setContentText(e.getCause().getMessage());
            alert.show();
        } catch (ConstruccionFueraDeRangoException e) {
            alert.setContentText("La posicion donde se pretende construir el edificio no esta proxima al aldeano");
            alert.show();
        } catch (OroInsuficienteException e) {
            alert.setContentText("No se posee oro suficiente para construir cuartel");
            alert.show();
        } catch (CasilleroOcupadoException e) {
            alert.setContentText("El o los casilleros para construir el cuartel se encuentran ocupados");
            alert.show();
        }
        contenedor.dibujarMapaConCasilleroHandler();
    }
}
