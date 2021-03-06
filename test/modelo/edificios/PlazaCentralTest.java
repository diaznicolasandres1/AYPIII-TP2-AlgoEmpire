package modelo.edificios;

import junit.framework.Assert;
import modelo.excepciones.*;
import modelo.juego.Oro;
import modelo.edificios.plazacentral.PlazaCentral;
import modelo.mapa.Mapa;
import modelo.mapa.Posicion;
import modelo.unidades.aldeano.Aldeano;

import org.junit.Test;

public class PlazaCentralTest {

    @Test
    public void test01CreacionDePlazaCentral() {
        Oro oro = new Oro(500);
        PlazaCentral plaza = new PlazaCentral(oro);
        Assert.assertEquals(plaza.getVida(), 450);
    }

    @Test
    public void test02PlazaCentralEnConstruccionNoRecibeDanio() {
        Oro oro = new Oro(500);
        PlazaCentral plaza = new PlazaCentral(oro);
        try {
            plaza.recibirDanio(50);
        } catch (Exception e) {
            Assert.assertEquals(plaza.getVida(), 450);
        }
    }

    @Test
    public void test03PlazaCentralConstruidaRecibeDanio() {

        Oro oro = new Oro(500);
        PlazaCentral plaza = new PlazaCentral(oro);

        plaza.finalizarTurno();
        plaza.finalizarTurno();
        plaza.finalizarTurno();
        /* Avanzo 3 turnos y como esta creada recibe daño */
        plaza.recibirDanio(50);

        Assert.assertEquals(plaza.getVida(), 400);
    }

    @Test
    public void test04PlazaCentralRecibirDanioYRepararse() {

        Oro oro = new Oro(500);
        PlazaCentral plaza = new PlazaCentral(oro);
        plaza.finalizarTurno();
        plaza.finalizarTurno();
        plaza.finalizarTurno();

        plaza.reducirVida(50);
        plaza.incrementarVida();
        Assert.assertEquals(plaza.getVida(), 425);
    }

    @Test(expected = EdificioTieneVidaMaximaException.class)
    public void test05PlazaCentralRecibirDanioYRepararseVariasVecesNoSuperaVidaMaxima() {

        Oro oro = new Oro(500);
        PlazaCentral plaza = new PlazaCentral(oro);
        plaza.finalizarTurno();
        plaza.finalizarTurno();
        plaza.finalizarTurno();

        plaza.recibirDanio(50);

        plaza.incrementarVida();
        plaza.incrementarVida();
        plaza.incrementarVida();
        plaza.incrementarVida();
        plaza.incrementarVida();
    }

    @Test
    public void test06CrearAldeanoDesdePlazaCentral() {

        Oro oro = new Oro(500);
        PlazaCentral plaza = new PlazaCentral(oro);
        plaza.finalizarTurno();
        plaza.finalizarTurno();
        plaza.finalizarTurno();

        Aldeano aldeano = plaza.crearAldeanoDesdePlaza();

        Assert.assertEquals(aldeano.getVida(), 50);
    }

    @Test
    public void test07CrearPlazaRestaOro() {

        Oro oro = new Oro(500);
        PlazaCentral plaza = new PlazaCentral(oro);

        Assert.assertEquals(oro.getOro(), 400);
    }

    @Test(expected = OroInsuficienteException.class)
    public void test08CrearPlazaConOroInsuficiente() {
        Oro oro = new Oro(50);
        PlazaCentral plaza = new PlazaCentral(oro);
    }

    @Test(expected = NoHayLugarSuficenteParaColocarEdificioException.class)
    public void test09plazaCentralIntentaColocarseFueraDelRangoDelMapaPositivoLanzaExcepcion() {

        Mapa mapa = new Mapa(20, 20);
        Oro oro = new Oro(500);
        PlazaCentral plaza = new PlazaCentral(oro);

        plaza.colocarseEn(mapa, 100, 100);
    }

    @Test(expected = NoHayLugarSuficenteParaColocarEdificioException.class)
    public void test10plazaCentralIntentaColocarseFueraDelRangoDelMapaNegativoLanzaExcepcion() {

        Mapa mapa = new Mapa(20, 20);
        Oro oro = new Oro(500);
        PlazaCentral plaza = new PlazaCentral(oro);

        plaza.colocarseEn(mapa, -1000, -1000);
    }

    @Test(expected = NoHayLugarSuficenteParaColocarEdificioException.class)
    public void test11plazaCentralIntentaColocarseFueraDelRangoDelMapaNuloLanzaExcepcion() {

        Mapa mapa = new Mapa(20, 20);
        Oro oro = new Oro(500);
        PlazaCentral plaza = new PlazaCentral(oro);

        plaza.colocarseEn(mapa, 0, 0);
    }

    @Test(expected = NoHayLugarSuficenteParaColocarEdificioException.class)
    public void test12plazaCentralSeColocaEnMapaYSeIntentaColocarOtraPlazaEnMismoLugarLanzaExcepcion() {

        Mapa mapa = new Mapa(20, 20);
        Oro oro = new Oro(500);
        PlazaCentral unaPlaza = new PlazaCentral(oro);
        PlazaCentral otraPlaza = new PlazaCentral(oro);

        unaPlaza.colocarseEn(mapa, 10, 10);

        otraPlaza.colocarseEn(mapa, 10, 10);
    }

    @Test(expected = CasilleroOcupadoException.class)
    public void test13plazaCentralSeColocaYSeDescolocaYSeColocaUnidadesEnElMismoLugarLanzaExcepcion() {

        Mapa mapa = new Mapa(20, 20);
        Oro oro = new Oro(10000);
        PlazaCentral plaza = new PlazaCentral(oro);

        plaza.colocarseEn(mapa, 10, 10);
        plaza.descolocarseDe(mapa);
        mapa.colocarUnidad(new Aldeano(oro), 10, 10);
        mapa.colocarUnidad(new Aldeano(oro), 10, 11);
        mapa.colocarUnidad(new Aldeano(oro), 11, 10);
        mapa.colocarUnidad(new Aldeano(oro), 11, 11);

        mapa.colocarUnidad(new Aldeano(oro), 11, 10);
    }

    @Test(expected = EdificioSiendoReparadoException.class)
    public void test15plazaSeIntentaRepararPorDosAldeanosDiferentesLanzaExcepcion() {

        Oro oro = new Oro(1000);
        PlazaCentral plaza = new PlazaCentral(oro);
        plaza.finalizarTurno();
        plaza.finalizarTurno();
        plaza.finalizarTurno();

        plaza.reducirVida(100);
        plaza.repararse(new Aldeano(oro));

        plaza.repararse(new Aldeano(oro));
    }

    @Test
    public void test16plazaCalculaDistanciaHaciaOtraPosicionDevuelveValorCorrecto() {

        Oro oro = new Oro(2000);
        PlazaCentral plaza = new PlazaCentral(oro);
        Mapa mapa = new Mapa(20, 20);
        Posicion posicion = new Posicion(15, 15);

        plaza.colocarseEn(mapa, 10, 10);

        Assert.assertEquals(4, plaza.calcularDistanciaA(posicion));
    }

}

