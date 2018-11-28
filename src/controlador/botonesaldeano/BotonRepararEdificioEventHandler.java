package controlador.botonesaldeano;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import modelo.edificios.Edificio;
import modelo.edificios.plazacentral.PlazaCentralEnConstruccionException;
import modelo.excepciones.AldeanoEstaOcupadoException;
import modelo.excepciones.CuartelCreandoseException;
import modelo.excepciones.OroInsuficienteException;
import modelo.excepciones.YaEstanReparandoEsteEdificioException;
import modelo.juego.Juego;
import modelo.unidades.aldeano.Aldeano;

public class BotonRepararEdificioEventHandler implements EventHandler<ActionEvent> {

    private Juego juego;
    private Aldeano aldeano;
    private Edificio edificio;

    public BotonRepararEdificioEventHandler(Juego juego, Aldeano aldeano, Edificio edificio) {
        this.juego = juego;
        this.aldeano = aldeano;
        this.edificio = edificio;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try{
            juego.repararEdificio(aldeano,edificio);
        }catch(AldeanoEstaOcupadoException e) {
            alert.setTitle("Error al reparar edificio");
            alert.setContentText("El aldeano esta ocupado");
            alert.show();
        }catch (YaEstanReparandoEsteEdificioException e){
            alert.setTitle("Error al reparar edificio");
            alert.setContentText("Este edificio ya esta siendo reparado por un aldeano");
            alert.show();
        }catch (PlazaCentralEnConstruccionException e){  //Tendria que juntar estas dos ultimas excepciones en una sola
        alert.setTitle("Error al reparar edificio");
        alert.setContentText("El edificio se esta creando");
        alert.show();
        }catch (CuartelCreandoseException e){
            alert.setTitle("Error al reparar edificio");
            alert.setContentText("El edificio se esta creando");
            alert.show();
        }
    }


}